package WorldComponents.Basics;

/** Defines package:
* the goal of this source is to store global constants that will be used overall by sources.
*/
public class Defines {

    /* STATS */
    // enum of all possible stats in RDC
    public static  final String[] STATS = {
        "HP", "ATK", "DEF", "MAGI", "RES", "SPD", "LUCK"
    };

    /* JOBS */
    public static final  String[] JOBS = {
        "Fighter", "Caster", "Survivalist"
    };

    /* GEAR */
    // gear types

    /** GearType enum:
     * enum that record all possible types of gear that exist in the game. A GearType attribute needs
     * to be provided to each gear during its instantiation.
     */
    public enum GearType{
        DAGGER, AXE, SWORD, SPEAR, BOW, CATALYST, // weapons
        SHIELD, HEAVY, LIGHT, ROBE, // ARMORS
        RING, BRACELET, AMULET; // ACCESSORIES
    }

    /** GearRequirements enum:
     * records all the possible requirements that a gear can have. these requirements determine where
     * a gear can be equipped on a unit body.
     */
    public enum GearReq {
        ONE_HAND, TWO_HAND, BODY, ACCESSORY
    }

    /** BodySlot enum:
     * record the 4 slots the body has. this allows each gear on the body to be uniquely determined by
     * its slot value.
     */
    public enum BodySlot{
        LHAND, RHAND, ARMOR, WEARABLE
    }

    /* BATTLE */
    /** BattleType enum:
     * possibles values of a battle in the dungeon. You either face monsters or a Bosses.
     */
    public enum BattleType{
        MONSTER, BOSS
    }
}
