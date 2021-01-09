package WorldComponents.Mobs;

import java.util.Arrays;

import WorldComponents.Basics.Defines;
import WorldComponents.Basics.Collectibles.Item;
import WorldComponents.Basics.Unit;
import Customs.Exceptions.WrongSizeStatsArrayException;
import Customs.Exceptions.NegativeValueArgumentException;

/** Hostiles package:
 * this source contains the definition of all the hostile mobs of the dungeon. it is based on the
 * WorldComponents.Basics.Unit object (inherit) and adds more attributes to make a variety of different
 * dungeon hostile dwellers.
 */
public class Hostiles {

    /** Monster object:
     * this class represents regular hostile mobs that can be encountered while exploring the dungeon floors.
     * they inherits units attributes but also initialize their own carried attribute.
     */
    public static class Monster extends Unit{
        // attributes
        protected Item[] carried;

        // constructor
        /**
         * expects a name, a level value, a stats set, and a list of loot items.
         * @param initStats initial values of the unit stats.
         * @param level initial level of the unit.
         * @param loot list of items that carried to be dropped on death.
         * @param name the name of the monster.
         */
        public Monster(String name, int level, int[] initStats,
                       Item[] loot) throws WrongSizeStatsArrayException
        {
            super(name, level, initStats); // build unit
            // deep copy the loot
            this.carried = loot.clone();
        }

        // getters
        /**
         * returns the reference to the list of items the monster carries.
         * @return a WorldBasics.Collectibles.Item[3]
         */
        public Item[] carried(){
            return this.carried;
        }
    }

    /** Boss object:
     * this class represents a boss monster, guardian of stratum exit staircases. a boss monster is distinguished
     * from other monsters by multiple forms; each form enhancing some of their stats. thus they add to the
     * monster configuration a number of forms, a list of stats to enhance. the number of forms will be used
     * as an enhancement factor to simplify building data.
     */
    public static class Boss extends Monster{
        // attributes
        int forms;
        String[] statsToEnhance;

        // constructor
        /**
         * expects a name, a level value, a stats set, and a list of loot items to drop on death; plus a number
         * of forms and a string array of stats to enhance.
         * @param initStats initial values of the unit stats.
         * @param level initial level of the unit.
         * @param loot list of items that carried to be dropped on death.
         * @param name the name of the monster.
         * @param forms the number of forms the boss has.
         * @param statsList the list of the stats that will be enhanced.
         */
        public Boss(String name, int level, int[] initStats,  Item[] loot, int forms, String[] statsList) throws
                WrongSizeStatsArrayException
        {
            // build the monster
            super(name, level, initStats, loot);
            // add Boss attributes
            this.forms = forms;
            this.statsToEnhance = statsList.clone();
        }

        // getters
        /**
         * @return the remaining number of forms.
         */
        public int forms(){
            return this.forms;
        }
        /**
         * @return the list of stats that will be enhanced each form-change phase.
         */
        public String[] toEnhance(){
            return this.statsToEnhance;
        }

        // setters
        /**
         * change to the next form. develop the stats in statsToEnhance and reinitialize all its stats to
         * get rid of stat changes that might have occurred. also reduce the number of forms remaining by 1.
         */
        public void morph() throws NegativeValueArgumentException {
            // get array of all stats string identifiers
            for (String stat : Defines.STATS) {
                // first we check if the stat must be developed meaning the stat is in the
                // this.statsToEnhance array.
                if (Arrays.asList(this.statsToEnhance).contains(stat)){
                    // if it is then we develop that stat first by adding the remaining number of forms. this
                    // way the boss gets stronger each iteration but by a diminishing factor.
                    this.stats.getStat(stat).develop(forms);
                }
                // then we reset the stat nevertheless
                this.stats.getStat(stat).reset();
            }
            this.forms--; // update remaining forms
        }
    }
}

