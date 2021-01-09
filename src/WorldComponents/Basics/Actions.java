package WorldComponents.Basics;

import WorldComponents.Basics.Elements.*;

import org.jetbrains.annotations.NotNull;

/** Actions package:
 * actions represents possible actions that hostiles and explorers can do in battle. this includes
 * any skill that they will use during their turn in the battle system. actions will be based on
 * the Action object that will provide characteristics of all actions: a name, an elemental
 * attribute, a category, a target, a damage multiplier, an accuracy factor, a cooldown timer,
 * a duration and a description. categories and targets will be defined as enums.
 */
public class Actions{

    /** Category enum:
     * categories are two-fold: PHYSICAL and SPECIAL. This only matters to the battle system as it
     * is used to determine whether an action will use physical-related stats (ATK, DEF) or
     * special-related stats (MAGI, RES).
     */
    public static enum Category{
        PHYSICAL, SPECIAL;
    }

    /** Target enum:
     * targets define the target of an action. actions can target an opponent (OPPT), an ally (ALLY)
     * or the unit itself (SELF) or all opponents (OPPX) or all allies - itself included (ALLX). this
     * allows the battle system to know the targets of an action.
     */
    public static enum Target{
        OPPT, ALLY, SELF, OPPX, ALLX;
    }

    /** Action object:
     * base action object that provides action to units in a battle. most attributes can never be
     * modified but the cooldown timer can. it is designed to be inherited by the definitive
     * action classes that follow.
     */
    public static abstract class Action{
        // final attributes
        protected final String name;
        protected final Element attribute;
        protected final Category catg;
        protected final Target target;
        protected final float multiplier;
        protected final int accuracy;
        protected final int cooldown;
        protected final int duration;
        protected final String description;

        /** constructor:
         * expects a name, an elemental attribute, a category, a target a damage multiplier, an
         * accuracy factor, a cooldown value, a duration value and a description. the elemental
         * attribute can be set to "null" as not all actions will need and elemental attribute.
         * @param description a text description of the action.
         * @param name the name of the action.
         * @param acc the accuracy value of the action.
         * @param attr the elemental attribute of the action.
         * @param catg category of the action. possible values are PHYSICAL or SPECIAL.
         * @param coold the cooldown time of the action.
         * @param tgt the target of the action. possible values are OPPT, OPPX, ALLY, ALLX and SELF.
         * @param multx the multiplier value of the action.
         * @param dur the number of battle turns before an action's effects disappear.
         */
        public Action(String name, Element attr, Category catg, Target tgt,  float multx,
                      int acc, int coold, int dur, String description)
        {
            this.name = name;
            this.attribute = attr;
            this.catg = catg;
            this.target = tgt;
            this.multiplier = multx;
            this.accuracy = acc;
            this.cooldown = coold;
            this.duration = dur;
            this.description = description;
        }

        // getters
        /**
         * @return the name
         */
        public String name(){
            return this.name;
        }
        /**
         * @return the elemental attribute
         */
        public Element attribute(){
            return this.attribute;
        }
        /**
         * @return the action category
         */
        public Category category(){
            return this.catg;
        }
        /**
         * @return the target
         */
        public Target target(){
            return this.target;
        }
        /**
         * @return the damage multiplier
         */
        public float multiplier(){
            return this.multiplier;
        }
        /**
         * @return the accuracy of the action
         */
        public int accuracy(){
            return this.accuracy;
        }
        /**
         * @return the cooldown time
         */
        public int timer(){
            return this.cooldown;
        }
        /**
         * @return the duration
         */
        public int duration(){
            return this.duration;
        }
        /**
         * @return the description
         */
        public String describe(){
            return this.description;
        }

        // other methods
        /**
         * returns a copy of itself. this method has to be overwritten by
         * the derived classes of Action.
         */
        public abstract Action clone();
    }

    /** Agress object:
     * this object represents actions that will deal damage to a target.
     * it derives from the Action class and has no own attributes.
     */
    public static class Agress extends Action{

        /** constructor:
         * expects a name, an elemental attribute, a category, a target a damage multiplier, an
         * accuracy factor, a cooldown value, a duration value and a description.
         * @param description a text description of the action.
         * @param name the name of the action.
         * @param acc the accuracy value of the action.
         * @param attr the elemental attribute of the action.
         * @param catg category of the action. possible values are PHYSICAL or SPECIAL.
         * @param coold the cooldown time of the action.
         * @param tgt the target of the action. possible values are OPPT, OPPX, ALLY, ALLX and SELF.
         * @param multx the multiplier value of the action.
         * @param dur the number of battle turns before an action's effects disappear.
         */
        public Agress(String name, Element attr, Category catg, Target tgt,  float multx,
                      int acc, int coold, int dur, String description)
        {
            // build the underlying Action
            super(name, attr, catg, tgt, multx, acc, coold, dur, description);
        }

        // other methods
        /**
         * returns a copy of itself. this is a required override of the base abstract method.
         */
        public Agress clone(){
            return new Agress(this.name, this.attribute, this.catg, this.target, this.multiplier,
                    this.accuracy, this.cooldown, this.duration, this.description);
        }
    }

    /** Buff object:
     * buffs are actions that raise the current value of non-HP stats of the target. they derive
     * from the base Action class but add to their list of attributes a list of stats. this
     * allows buffs to include healing Actions too. the stats list can never change. a buff
     * lasts for duration number of turns.
     */
    public static class Buff extends Action{
        // attribute
        private final String[] stats_to_buff;

        /** constructor:
         * expects a name, an elemental attribute, a category, a target a damage multiplier, an
         * accuracy factor, a cooldown value, a duration value and a description.
         * * @param description a text description of the action.
         * @param description a text description of the action.
         * @param name the name of the action.
         * @param acc the accuracy value of the action.
         * @param attr the elemental attribute of the action.
         * @param catg category of the action. possible values are PHYSICAL or SPECIAL.
         * @param coold the cooldown time of the action.
         * @param tgt the target of the action. possible values are OPPT, OPPX, ALLY, ALLX and SELF.
         * @param multx the multiplier value of the action.
         * @param dur the number of battle turns before an action's effects disappear.
         * @param stats a list of stats that will be modified by this action.
         */
        public Buff(String name, Element attr, Category catg, Target tgt, float multx,
                    int acc, int coold, int dur, @NotNull String[] stats, String description)
        {
            // build underlying Action
            super(name, attr, catg, tgt, multx, acc, coold, dur, description);
            this.stats_to_buff = stats.clone();
        }

        // getters
        /**
         * return the list of stats to buff string
         */
        public String[] toBuff(){
            return this.stats_to_buff;
        }

        // other methods
        /**
         * returns a copy of itself. this is a required override of the base abstract method.
         */
        public Buff clone(){
            return new Buff(this.name, this.attribute, this.catg, this.target, this.multiplier,
                    this.accuracy, this.cooldown, this.duration, this.stats_to_buff,
                    this.description);
        }
    }

    /**Nerf object:
     * nerfs are actions that lower the current value of non-HP stats of the target. they
     * derive from the base Action class but add to their list of attributes a list of stats.
     * this allows nerfs to include HP reducing actions like insta-kills. the stats list can
     * never change. nerfs lasts a duration number of turns.
     */
    public static class Nerf extends Action{

        // attribute
        private final String[] stats_to_nerf;

        /**constructor:
         * expects a name, an elemental attribute, a category, a target a damage multiplier, an
         * accuracy factor, a cooldown value, a duration value and a description.
         * * @param description a text description of the action.
         * @param description a text description of the action.
         * @param name the name of the action.
         * @param acc the accuracy value of the action.
         * @param attr the elemental attribute of the action.
         * @param catg category of the action. possible values are PHYSICAL or SPECIAL.
         * @param coold the cooldown time of the action.
         * @param tgt the target of the action. possible values are OPPT, OPPX, ALLY, ALLX and SELF.
         * @param multx the multiplier value of the action.
         * @param dur the number of battle turns before an action's effects disappear.
         * @param stats a list of stats that will be modified by this action.
         */
        public Nerf(String name, Element attr, Category catg, Target tgt, float multx,
                    int acc, int coold, int dur, @NotNull String[] stats, String description)
        {
            // build underlying Action
            super(name, attr, catg, tgt, multx, acc, coold, dur, description);
            this.stats_to_nerf = stats.clone();
        }

        // getters
        /**
         * return the stat to buff string
         */
        public String[] toNerf(){
            return this.stats_to_nerf;
        }

        // other methods
        /**
         * returns a copy of itself. this is a required override of the
         * base abstract method.
         */
        public Nerf clone(){
            return new Nerf(this.name, this.attribute, this.catg, this.target, this.multiplier,
                    this.accuracy, this.cooldown, this.duration, this.stats_to_nerf,
                    this.description);
        }
    }
}
