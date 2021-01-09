package WorldComponents.Basics;

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
            this.progression = 0 + ((initValue - 1)  * 100);
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
         * @param amount
         * @return true if the level attribute went up; false else wise.
         */
        public boolean earn(int amount){
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
        private final int max;

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

        // other
        @Override
        /**
         * same as the super version of the method but stops the earning once max level is
         * reached.
         * @param amount
         * @return true if the level attribute went up; false else wise.
         */
        public boolean earn(int amount){
            boolean levelUp = false; // false by default
            if(this.level < this.max) { // update progression if level less than max
                this.progression += amount; // update progression
                int newLevel = (this.progression % 100 + 1); // estimate changes in level
                levelUp = newLevel > this.level; // if estimates higher than previous value
                if (levelUp) {
                    this.level = newLevel; // update level value
                }
            }
            return levelUp;
        }
    }
}

