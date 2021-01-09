package WorldComponents.Basics;

import java.util.Hashtable;

import Customs.Exceptions.NegativeValueArgumentException;
import Customs.Exceptions.WrongSizeStatsArrayException;

/** Stats package:
 * this package contains the definition of a stat. making this its own class allows the manipulation
 * of stats data on units and gears that will use it easier as the stats are autonomous instead
 * of manually updated. this package also contains the definition of the StatSet which is the set
 * of all the possible stats that can be owned by something.
 */
public class Stats {

    /** Stat object:
     * object that represent a single stat of a unit. this allows each stat to be its own entity
     * thus enabling easy modification of its attributes. a stat is a combination of a current
     * value (referenced by "cur") and a maximum value (referenced by "max") recorded in a
     * Hashtable. This allow quick get() operations to retrieve the values.
     */
    public static class Stat{

        // attributes
        private int current;
        private int max;

        /** constructor:
         * expects an initial value to set the stat at.
         * @param initial the initial value of a stat
         */
        public Stat(int initial){
            this.max = initial;
            this.current = initial;
        }

        // getter
        /**
         * return current value of stat.
         */
        public int current(){
            return this.current;
        }
        /**
         * return max value of stat.
         */
        public int max(){
            return this.max;
        }

        // setters
        /**
         * raise the value of both max and current values of the stat by "amount". may be used
         * for level up evolution. "amount" cannot be negative.
         * @param amount the amount to increase by
         */
        public void develop(int amount) throws NegativeValueArgumentException {
            if(!(amount < 0))
                this.max += amount;
            else
                throw new NegativeValueArgumentException(amount + " can't be negative.");
        }
        /**
         * reduce the current value of stat by the "amount". if "amount" is higher than the current
         * value, current becomes "0". "amount" cannot be negative.
         * @param amount the amount to lower by
         */
        public void lowerBy(int amount) throws NegativeValueArgumentException {
            if(amount > 0){
                if(this.current > amount)
                    this.current -= amount;
                else // to avoid negative current values
                    this.current = 0;
            }else
                throw new NegativeValueArgumentException(amount + " can't be negative.");
        }
        /** raise the current value of stat by the "amount". the current value can potentially exceed
         * the maximum so no control is put on that. "amount" must be positive.
         * @param amount the amount to raise by
         */
        public void raiseBy(int amount) throws NegativeValueArgumentException {
            if(amount > 0){
                // the current can go over the max
                this.current += amount;
            }else
                throw new NegativeValueArgumentException(amount + " can't be negative.");
        }
        /**
         * reset the current value of the stat to its max value. this is done by setting the
         * current equal to the max.
         */
        public void reset(){
            this.current = this.max;
        }
    }

    /**
     * this class represent the complete set of stats that any unit of the game world and equipment
     * pieces must possess to be able to interact to have battle characteristics. It uses the "Stat"
     * object defined earlier as its value in the internal HashTable<Stats, Stat>. the complete stat
     * set contains the 7 stat characteristics that this rpg-like features: HP, ATK, DEF, MAGI, RES,
     * SPD and LUCK. these are defined as elements of a String[] in WordComponents.Basics.Defines.STATS.
     */
    public static class StatSet{

        // attributes
        private Hashtable<String, Stat> set;

        /** constructor:
         * requires an int array with initial values for all stats defined in Defines.STATS
         * @param initValues initial values of each stat
         * @throws WrongSizeStatsArrayException when initValues < Defines.STATS.length
         */
        public StatSet(int[] initValues) throws WrongSizeStatsArrayException{
            this.set = new Hashtable<String, Stat>(); // construct the set
            // create the stats set while making sure the "initValues" is
            // the right size
            try{
                for(int i = 0; i < Defines.STATS.length; i++){
                    this.set.put(Defines.STATS[i], new Stat(initValues[i]));
                }
            }catch (ArrayIndexOutOfBoundsException err){
                throw new WrongSizeStatsArrayException(
                        "passed initial stats array should be " + Defines.STATS.length + " long.", err);
            }
        }

        // getters
        /**
         * returns the set of stats of the StatSet object.
         */
        public Hashtable<String, Stat> getSet(){
            return this.set;
        }
        /**
         * returns the Stat specified by stat.
         * @param stat the string key of the stat sought.
         */
        public Stat getStat(String stat){
            return this.set.get(stat);
        }

        // setters
        /**
         * reset all the stats of the set to their max value. this can be useful for example when
         * healing the user at an inn.
         */
        public void resetAll(){
            for(String stat : Defines.STATS){
                this.getStat(stat).reset();
            }
        }
        /**
         * reset all the stats of the set but the "HP" to their max value. This can be used at the
         * end of a fight to remove buffs and nerfs that way the stats are at their normal values for
         * next fight.
         */
        public void cleanseAll(){
            for(String stat : Defines.STATS){
                if (!stat.equals("HP")) // skip the "HP" reset
                    this.getStat(stat).reset();
            }
        }
    }
}
