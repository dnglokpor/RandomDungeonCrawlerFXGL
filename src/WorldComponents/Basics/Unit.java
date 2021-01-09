package WorldComponents.Basics;

import WorldComponents.Basics.Stats.*;
import WorldComponents.Basics.Leveling.*;
import Customs.Exceptions.WrongSizeStatsArrayException;

/** Unit class
 * class of living organisms. this is a base class that will be inherited by any job classes. It provides
 * them all with a name and life stats of a mob: HP, ATK, DEF, MAG, RES, SPD, AGI and LUCK. adds to those
 * a level attribute and a set of abilities. it also provides flags that can quickly indicate when a unit
 * is low on HP and when a unit dies.
 */
public class Unit{
    // internal data
    protected String name;
    protected boolean isAlive;
    protected boolean isCritical;
    protected Level level;
    protected StatSet stats;
    protected ActionSet abilities;

    /** constructor
     * expects a name, a starting level, initial stats.
     * @param name the name of the unit.
     * @param initStats an array of integer values to init the stat set at.
     * @param level the starting level of the unit.
     */
    public Unit(String name, int level, int[] initStats) throws WrongSizeStatsArrayException {
        this.name = name;
        this.level = new Level(level);
        this.stats = new StatSet(initStats);
        this.abilities = new ActionSet(); // build the action set
        this.isAlive = true; // alive by default
        this.isCritical = false; // not critical by default
    }

    // getters
    /**
     * return the unit's name
     */
    public String name(){
        return this.name;
    }
    /**
     * return the unit's level
     */
    public Level level(){
        return this.level;
    }
    /**
     * return the unit's StatSet object.
     */
    public StatSet stats(){
        return this.stats;
    }
    /**
     * return the unit ActionSet object.
     */
    public ActionSet abilities(){
        return this.abilities;
    }
    /**
     * return the unit's isAlive flag value.
     */
    public boolean isAlive(){
        return this.isAlive;
    }
    /**
     * return the unit's isCritical flag value.
     */
    public boolean isCritical(){
        return this.isCritical;
    }

    // setters
    /**
     * this method update the unit's isAlive and isCritical flags value. this method should be called
     * after action that targets the unit.
     */
    public void healthCheck(){
        int currentHP = this.stats.getStat("HP").current(); // get unit current HP
        int maxHP = this.stats.getStat("HP").max(); // get unit max HP
        int criticalHP = maxHP / 4; // integer division
        isCritical = currentHP <= criticalHP;
        isAlive = currentHP == 0;
    }
}
