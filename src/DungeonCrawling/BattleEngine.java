package DungeonCrawling;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Customs.Exceptions.NullActionArgumentException;
import Customs.Utilities.JsonIO;
import WorldComponents.Basics.Defines.BattleType;
import WorldComponents.Mobs.Hostiles.Monster;
import WorldComponents.Mobs.Hostiles.Boss;
import Customs.Utilities.JsonIO.*;

/** BattleEngine package:
 * this package contains methods that will be used to compute a fight between an explorer and a group of
 * monsters. battles are automatic meaning once they start they end by the defeat of one party. the flow
 * of the battle is ruled by the engine. It determines the order of actions of the parties involved and
 * what actions they perform everytime it is their turn to act. it applies the effect of the actions and
 * replace them in the fight order list. at the end of the fight, it awards experience and loot to the
 * wining explorer. the actual happens in BattleEngine.encounter() method but other helpers and setters
 * will be defined here as well.
 */
public class BattleEngine {
    // attributes
    private static final int MAX_NUM_OF_MONSTERS = 5; // how many monsters can attack at the same time
    private static final int CHANCES_OF_ADDITIONAL = 10; // the chances of spawning one more monster per danger level
    private static int dangerLevel = 1;
    private static BattleType battleType = BattleType.MONSTER;

    // setters
    /**
     * set the danger level of the floor. the danger level raise the chances of meeting more monsters and
     * reduces the chances of escaping.
     * @param lvl the value to set the danger level to.
     */
    public static void setDangerLevel(int lvl){
        if(lvl > 1){
            dangerLevel = lvl;
        }
    }
    /**
     * set the battle type to either MONSTER or BOSS. this changes how the battle algorithm will process the
     * passed arguments and automate the battle.
     * @param battleType can be set to either MONSTER or BOSS.
     */
    public static void setBattleType(BattleType battleType){
        BattleEngine.battleType = battleType;
    }

    // battle related methods

    /**
     * creates the list of opponents from the passed list of possible opponents name. the arguments
     * can not be null.
     * @param hostiles a String array that contains the name of all the hostiles that can be spawned.
     * @param chances the chances to meet each of the monsters.
     * @return a list of monsters. list can be a singleton.
     * @throws Exception if monster creation from JSON file failed for reasons.
     */
    public static ArrayList<Monster> monsterParty(String[] hostiles, int[] chances) throws Exception {
        // check validity of passed arguments
        if(hostiles == null || chances == null){
            throw new NullActionArgumentException("arguments may not be null");
        }
        // else we proceed and set return up
        ArrayList<Monster> spawned = new ArrayList<Monster>();
        // spawn monsters
        if (battleType == BattleType.MONSTER) { // multiple monsters with numbers to be randomly determined
            int partySize = ThreadLocalRandom.current().nextInt(MAX_NUM_OF_MONSTERS) + 1; // define a party size
            if (partySize < MAX_NUM_OF_MONSTERS) { // danger level
                if (ThreadLocalRandom.current().nextInt(100) < dangerLevel * CHANCES_OF_ADDITIONAL) {
                    partySize++; // add one more monster to the party because of the danger level.
                }
            }
            int monsterIndex = 0; // monster to spawn from hostiles array
            int herdSize; // how many of this particular monster
            while (spawned.size() < partySize) {
                // choose an index
                monsterIndex = ThreadLocalRandom.current().nextInt(hostiles.length);
                if (ThreadLocalRandom.current().nextInt(100) < chances[monsterIndex]) {
                    // we can meet this monster so check how many there are
                    herdSize = ThreadLocalRandom.current().nextInt(partySize + 1);
                    for (int i = 0; i < herdSize; i++)
                        spawned.add(JsonIO.loadHostile(hostiles[monsterIndex])); // and spawn those
                    // update the party size
                    partySize -= herdSize;
                }
            }
        } else { // supposing BattleType.BOSS. passed monster (hostiles[0]) must be spawned and returned.
            spawned.add(JsonIO.loadHostile(hostiles[0]));
        }
        // return
        return spawned;
    }
}
