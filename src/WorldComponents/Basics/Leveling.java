package WorldComponents.Basics;

import Customs.Exceptions.NegativeValueArgumentException;

/** Leveling package:
 * contains the level gauge objects that can be used to keep track of the
 * progression level of something (eg: unit level, adventurer rank).
 * it is autonomous meaning it only expects progression points input to
 * keep track of itself.
 */
public class Leveling {

    /** Level object:
     * this class represent the level of something. a level has a current value and a progression
     * to next level. progression is centesimal meaning every 100 progression point the level is
     * raised by a unit automatically. designed for inheritance.
     */
    public static class Level{

        // attributes
        protected int level;
        protected int progression;

        /** constructor:
         * expect a value to start counting the level at.
         * @param initValue
         */
        public Level(int initValue){
            this.level = initValue;
            // update progression based on current
            this.progression = ((initValue - 1)  * 100);
        }

        // getter
        /**
         * return the current value of the level.
         * @return an int
         */
        public int current(){
            return this.level;
        }

        /**
         * return the current amount of progression.
         * @return an int.
         */
        public int currentProg(){
            return this.progression;
        }

        // setters
        /**
         * adds "amount" of progression points to the current "progression". update level if needed and
         * return a true if there was a level up.
         * @param amount the amount of progression points earned.
         * @return true if the level attribute went up; false else wise.
         */
        public boolean earn(int amount) throws NegativeValueArgumentException {
            if(amount < 0){
                throw new NegativeValueArgumentException(amount + " can not be negative.");
            }
            // else
            this.progression += amount; // update progression
            int newLevel = (this.progression % 100 + 1);
            boolean levelUp = newLevel > this.level;
            if (levelUp){
                this.level = newLevel;
            }
            return levelUp;
        }
    }

    /** Rank object:
     * a Rank is a Level subclass that includes a maximum level which means that a Rank will stop earning
     * experience once the maximum level is reached. for this, it overrides the inherited earn() method.
     */
    public static class Rank extends Level{

        // attributes:
        protected final int max;

        /** constructor:
         * expects an initial value and a max value
         * @param initial the initial level of the rank.
         * @param max the maximum level the rank can reach.
         */
        public Rank(int initial, int max){
            super(initial); // build underlying level
            this.max = max; // init max rank
        }

        // getters

        /**
         * return max rank.
         * @return an int.
         */
        public int max(){
            return this.max;
        }

        // setters
        @Override
        /**
         * same as the super version of the method but stops the earning once max level is
         * reached.
         * @param amount
         * @return true if the level attribute went up; false else wise.
         */
        public boolean earn(int amount) throws NegativeValueArgumentException{
            boolean levelUp = false; // false by default
            if(this.level < this.max) { // update progression if level less than max
                levelUp = super.earn(amount);
            }
            return levelUp;
        }
    }

    /** ExplorerRank object:
     * the explorer rank is a rank that gives a letter to an explorer depending on how deep in the dungeon
     * they have ventured and managed to come back alive. each floor will completely explorer will raise
     * the exRank.level by a unit and every 5 levels, the letter attributed to them will be upgraded.
     * possible letters are F, E, D, C, B, A, S, X which means an explorer that makes it to the 40th
     * level will be an X rank explorer. the exRank.level attribute can be used to record the number of
     * floors completely explored by the explorer.
     */
    public static class ExplorerRank extends Level{
        // attributes
        private int rankID; // will be used to index the ranks string

        /** constructor:
         * expects an initial value and a maximum value. by default set rankID to initial div 5.
         * @param initial the starting value of the rank.
         */
        public ExplorerRank(int initial){
            super(initial); // set Level object up
            this.rankID = initial / 5; // 0 -> F, 1 -> E, 2 -> D etc...
        }

        // getters
        /**
         * @return current letter rank.
         */
        public char rank(){
            final String ranks = "FEDCBASX"; // each rank is just one letter.
            return ranks.charAt(this.rankID); // 0 -> F, 1 -> E, 2 -> D etc...
        }

        // setters
        /**
         * should be called every time a new floor was completely completed, giving the adventurer a 100
         * explorer rank points. earning is completed by calling super method (discarding return value).
         * each 5th level ups, rankID will be increased.
         * @param amount the amount earned. must be exactly a 100 and will be changed to a 100.
         * @return true whenever rankID is increased. Else returns false.
         */
        @Override
        public boolean earn(int amount) throws NegativeValueArgumentException{
            if(amount != 100)
                amount = 100; // makes sure the amount is correct
            super.earn(amount); // call the overrun method to calculate new level and discard return
            boolean rankUp = false; // flag
            if((this.level / (int) 5) != this.rankID){ // the rankID changed
                this.rankID++; // rankID can only changed by 1 as amount is always exactly 100
                rankUp = true;
            }
            return rankUp;
        }
    }
}
