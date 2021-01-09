package WorldComponents.Basics;

import WorldComponents.Basics.Leveling.Rank;
import WorldComponents.Basics.Actions.*;

/** Jobs package:
 * jobs are vocations that adventurers can pursue. following such vocation means developing their body
 * to better the actions that the job requires. each job comes with a set of actions that the explorer
 * can learn as they get better at their job. the mastery of the job is measured by its rank attribute.
 * complete mastery can be achieved by reaching the maximum rank of the job.
 * this package contains the definition of the basic Job class on which all the jobs available will be
 * built.
 */
public class Jobs {

    /** Job object:
     * object that provides the base properties of a job such as a name, a rank a stat development
     * table and and a the list of actions they unlock as they learn. except for the list of actions,
     * all those are immutable properties. this class will be inherited by preset subclasses that
     * define actual jobs.
     */
    public static class Job{
        // internal constants: could eventually change as implementation happens.
        private final int MAX_RANK = 10;
        private final int NUM_ACTIONS = 5;
        // attributes
        protected final String name;
        protected final Rank rank;
        protected final float[] devTable;
        protected Action[] learnable;

        /** constructor:
         * expects the name of the Job and the stats development table. other properties will be
         * internally initialized.
         * @param name the name of the Job.
         * @param devTable an array of
         */
        public Job(String name, float[] devTable){
            this.name = name;
            this.rank = new Rank(1, MAX_RANK);
            this.devTable = devTable.clone();
            // internally
            this.learnable = new Action[NUM_ACTIONS];
        }

        // getters
        /**
         * @return the job's name.
         */
        public String name(){
            return this.name;
        }
        /**
         * @return the job's rank.
         */
        public Rank rank(){
            return this.rank;
        }
        /**
         * @return the job' stats development table.
         */
        public float[] devTable(){
            return this.devTable;
        }
        /**
         * @return the job's action list.
         */
        public Action[] learnable(){
            return this.learnable;
        }
    }

    // TODO create at least one custom Job subclass eg: fighter
}