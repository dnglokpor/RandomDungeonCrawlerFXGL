package DungeonCrawling;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import Customs.Exceptions.NullActionArgumentException;
import Customs.Exceptions.UndefinedKeyException;
import Customs.Utilities.JsonIO;
import WorldComponents.Basics.Defines.BattleType;
import WorldComponents.Basics.Unit;
import WorldComponents.Mobs.Hostiles.Monster;
import WorldComponents.Mobs.Hostiles.Boss;
import WorldComponents.Mobs.Explorer;

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
    // floor setup
    private static final int MAX_NUM_OF_MONSTERS = 5; // how many monsters can attack at the same time
    private static final int CHANCES_OF_ADDITIONAL = 10; // the chances of spawning one more monster per danger level
    private static int dangerLevel = 1; // how dangerous the floor is
    private static String[] hostilesList = null; // list of all regular monsters
    private static int[] hostilesChances = null; // chances of encounter of each
    private static String boss = null; // boss monster (singleton)
    private static Explorer player = null; // to store the player
    // battle setup
    private static ArrayList<Monster> hostiles = null;
    private static BattleType battleType = BattleType.MONSTER; // type of battle
    private static Queue<Unit> turnOrder = null;

    // setters to set all the private global variables for this class
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
    public static void setBattleType(BattleType battleType){ BattleEngine.battleType = battleType; }
    /**
     * set the list of hostile mobs that can be spawned.
     * @param hList the array of names of all the mobs that can be spawned.
     */
    public static void setHostilesList(String[] hList) { hostilesList = hList; }
    /**
     * set the chances of encounter of each hostiles on the floor.
     * @param chances an array of integers from 1 to 100 that represent chances of meeting each monster.
     */
    public static void setHostilesChances(int[] chances){ hostilesChances = chances; }
    /**
     * set the boss monster of the level. there can only be one boss monster.
     * @param bName is the name of the boss monster.
     */
    public static void setBosses(String bName){ boss = bName; }
    /**
     * set the player that is doing the exploration
     * @param p the Explorer object.
     */
    public static void setPlayer(Explorer p){ player = p; } // by reference is fine

    //getters
    /**
     * @return the player object that was set. can be used to recover the player with all the changes from battle.
     */
    public static Explorer getPlayer(){ return player; }

    // battle related methods

    /**
     * creates the list of opponents from the passed list of possible opponents name. the arguments
     * can not be null.
     * @throws Exception if monster creation from JSON file failed for reasons.
     */
    public static ArrayList<Monster> monsterParty() throws Exception {
        if (battleType == BattleType.MONSTER) { // multiple monsters with numbers to be randomly determined
            // check validity of passed arguments
            if(hostilesList == null || hostilesChances == null){
                throw new NullActionArgumentException("arguments may not be null");
            }
            // else we proceed
            int partySize = ThreadLocalRandom.current().nextInt(MAX_NUM_OF_MONSTERS) + 1; // define a party size
            if (partySize < MAX_NUM_OF_MONSTERS) { // danger level
                if (ThreadLocalRandom.current().nextInt(100) < dangerLevel * CHANCES_OF_ADDITIONAL) {
                    partySize++; // add one more monster to the party because of the danger level.
                }
            }
            int monsterIndex; // monster to spawn from hostiles array
            int herdSize; // how many of this particular monster
            while (partySize > 0) { // partySize will be reduced as spawned grows.
                // choose an index
                monsterIndex = ThreadLocalRandom.current().nextInt(hostilesList.length);
                if (ThreadLocalRandom.current().nextInt(100) < hostilesChances[monsterIndex]) {
                    // we can meet this monster so check how many there are
                    herdSize = ThreadLocalRandom.current().nextInt(partySize + 1);
                    for (int i = 0; i < herdSize; i++)
                        hostiles.add(JsonIO.loadHostile(hostilesList[monsterIndex])); // and spawn those
                    // update the party size
                    partySize -= herdSize;
                }
            }
        } else { // supposing BattleType.BOSS.
            if(boss == null){ // check for validity of information
                throw new NullActionArgumentException("argument may not be null");
            }
            // else we spawn the boss
            hostiles.add(JsonIO.loadHostile(boss));
        }
        return hostiles;
    }
}
