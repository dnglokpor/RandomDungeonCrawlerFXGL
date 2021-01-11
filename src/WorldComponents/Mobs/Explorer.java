package WorldComponents.Mobs;

import WorldComponents.Basics.Body;
import WorldComponents.Basics.Jobs.Job;
import WorldComponents.Basics.StorageKits.Bag;
import WorldComponents.Basics.StorageKits.Wallet;
import WorldComponents.Basics.Unit;
import WorldComponents.Basics.Leveling.ExplorerRank;
import Customs.Exceptions.WrongSizeStatsArrayException;
import Customs.Exceptions.NullActionArgumentException;

/** Explorer object:
 * explorers are the core of this game as they are the people who challenge the dungeon in their quest for
 * adventure and treasures. this is the unit that players will be controlling while playing the game and as
 * such it inherits from the Unit class and adds to it a Body, a Job, a Bag, and a Wallet. To record more
 * info about the player it also has a linked explorer Card that saves data on dungeon exploration and
 * achievements.
 */
public class Explorer extends Unit{
    // attributes
    Body body;
    Job job;
    Bag bag;
    Wallet wallet;
    ExplorerRank rank;

    /**
     * expects a name, an initial level, a list of init values for all stats and a Job object.
     * @param name the name of the explorer.
     * @param initLevel the level to start the explorer's experience at.
     * @param stats array of initial stats for all base units' stats.
     * @param job a Job object set to the job the explorer follows.
     * @throws WrongSizeStatsArrayException when the stat list is too short.
     * @throws NullActionArgumentException when the job doesn't define a basic action a.k.a action at lvl 1.
     */
    public Explorer(String name, int initLevel, int[] stats, Job job) throws Exception {
        // init unit properties
        super(name, initLevel, stats);
        this.body = new Body();
        this.job = job;
        this.bag = new Bag();
        this.wallet = new Wallet();
        this.rank = new ExplorerRank(0);

        // set basic action to match the job's
        this.setJobBasic();
    }

    // getters
    /**
     * @return the explorer's body.
     */
    public Body body(){ return this.body; }
    /**
     * @return the explorer's job.
     */
    public Job job(){ return this.job; }
    /**
     * @return the explorer's bag.
     */
    public Bag bag(){ return this.bag; }
    /**
     * @return the explorer's wallet.
     */
    public Wallet wallet(){ return this.wallet; }
    /**
     * @return the explorer's job.
     */
    public ExplorerRank rank(){ return this.rank; }

    // setters
    /**
     * sets the basic action from the current job as the explorer's basic action.
     */
    public void setJobBasic() throws NullActionArgumentException {
        this.abilities.setBasic(this.job.learnedAt(1));
    }
    /**
     * call when wanting to change the job of an explorer altogether.
     * @param newJob the Job object to switch to.
     * @return the old Job.
     */
    public Job changeJob(Job newJob) throws NullActionArgumentException {
        Job old = this.job; // save old reference
        this.job = newJob; // point to new reference
        this.setJobBasic(); // update basic ability
        return old;
    }

}
