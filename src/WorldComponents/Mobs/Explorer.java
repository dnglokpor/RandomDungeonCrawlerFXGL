package WorldComponents.Mobs;

import WorldComponents.Basics.Body;
import WorldComponents.Basics.Jobs.Job;
import WorldComponents.Basics.StorageKits.Bag;
import WorldComponents.Basics.StorageKits.Wallet;
import WorldComponents.Basics.Unit;
import Customs.Exceptions.WrongSizeStatsArrayException;

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
    // TODO create explorer Card object

    public Explorer(String name, int initLevel, int[] stats, Job job) throws WrongSizeStatsArrayException {
        // init unit properties
        super(name, initLevel, stats);
        this.body = new Body();
        this.job = job;
        this.bag = new Bag();
        this.wallet = new Wallet();
        // TODO init explorer Card
    }
}
