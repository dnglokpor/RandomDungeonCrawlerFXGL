package WorldComponents.Basics;

import java.util.Hashtable;

import WorldComponents.Basics.Leveling.Rank;
import WorldComponents.Basics.Actions.*;
import Customs.Utilities.JsonIO;

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
    public static abstract class Job{
        // internal constants: could eventually change as implementation happens.
        private final int MAX_RANK = 50;
        // attributes
        protected final String name;
        protected final String description;
        protected final Rank rank;
        protected final float[] devTable; // sum of the table has to be 1.0
        protected Hashtable<Integer, Action> learnable; // actions are learned at specific ranks

        /** constructor:
         * expects the name of the Job and the stats development table. other properties will be
         * internally initialized.
         * @param name the name of the Job.
         * @param devTable an array of floats that represent by how much to increase the current stat.
         * @param description a text description of the Job.
         */
        public Job(String name, String description, float[] devTable){
            this.name = name;
            this.description = description;
            this.rank = new Rank(1, MAX_RANK);
            // the development table has to be Defines.STATS.length long
            if(devTable.length < Defines.STATS.length)
                System.out.println("[WARNING] This development table is too short!");
            this.devTable = devTable.clone();
            // internally
            this.learnable = new Hashtable<Integer, Action>();
        }

        // getters
        /**
         * @return the job's name.
         */
        public String name(){
            return this.name;
        }
        /**
         * @return the job's description.
         */
        public String describe(){
            return this.description;
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
        public Hashtable<Integer, Action> learnable(){
            return this.learnable;
        }

        // setters
        /**
         * increases the rank of the job by the amount of mastery provided.
         * @param mastery the amount of Job experience earned.
         * @return a boolean that is true when the rank levels up but false otherwise.
         */
        public boolean master(int mastery){
            return this.rank.earn(mastery);
        }
    }

    /** Fighter object:
     * this job represents units focus on developing their muscles resulting on better physical stats (ATK, DEF).
     * their learnset includes hyper physical buffing and damaging techniques that boost their performances in
     * battle. high levels of mastery also yield element infused techniques to deal even more damage.
     */
    public static class Fighter extends Job{

        /** constructor:
         * pass in the general attributes of a fighter.
         */
        public Fighter() throws Exception{
            // create the Job characteristics
            super("Fighter",
                "Sturdy explorers who take pride on how resistant they are. Their high physicals" +
                            "coupled with their mastery of close range weaponry allows them to prevail in battle" +
                            " of toughness. They can also add elementals to their moves to deal extra damage.",
                new float[]{/*HP*/.2f, /*ATK*/.2f, /*DEF*/.2f, /*MAGI*/.1f, /*RES*/.1f, /*SPD*/.1f, /*LUCK*/.1f}
            );
            // fill in the actions one by one to build the custom class learnable set.
            this.learnable.put(3, JsonIO.loadAction("warcry"));
            this.learnable.put(5, JsonIO.loadAction("cleave"));
            this.learnable.put(8, JsonIO.loadAction("cure"));
            this.learnable.put(10, JsonIO.loadAction("bash"));
            this.learnable.put(13, JsonIO.loadAction("burningblade"));
        }
    }

    // TODO Caster custom Job class
    // TODO Survivalist custom Job class
}