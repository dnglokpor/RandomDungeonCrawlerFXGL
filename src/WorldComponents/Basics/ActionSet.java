package WorldComponents.Basics;

import WorldComponents.Basics.Actions.*;
import Customs.Exceptions.NullActionArgumentException;

/**ActionSet object:
 * an action set is a set of actions that a unit have at his disposal at all time. this allows a unit
 * to fight in a battle. The action set is empty by default. it has to be set up for particular units
 * like hostiles when they are instantiated. the set is made of 3 separate actions: the basic action
 * which the unit can perform at all times, the skill action that the unit can perform only when this
 * is ready and the critical action that the unit performs once they reach a critical state.
 */
public class ActionSet{
    // attributes
    private Action basic;
    private Action skill;
    private Action critical;

    /**constructor:
     * by default an action is empty.
     */
    public ActionSet(){
        this.basic = null;
        this.skill = null;
        this.critical = null;
    }

    // getter
    /**
     * return the basic Action
     */
    public Action basic(){
        return this.basic;
    }
    /**
     * return the skill Action.
     */
    public Action skill(){
        return this.skill;
    }
    /**
     * return the critical action.
     */
    public Action critical(){
        return this.critical;
    }

    // setters
    /**
     * set the basic Action. this returns the previous action that was assigned to this.basic.
     * if no actions were previously assigned, then this returns null.
     * @param newAction the action to set as basic.
     */
    public Action setBasic(Action newAction) throws NullActionArgumentException {
        if(newAction != null) {
            Action old = this.basic; // reassign pointer of old action
            this.basic = newAction.clone();
            return old;
        }else
            throw new NullActionArgumentException("Setting actions require non null arguments.");
    }
    /**
     * set the skill Action. this returns the previous action that was assigned to this.skill.
     * if no actions were previously assigned, then this returns null.
     * @param newAction the action to set as skill.
     */
    public Action setSkill(Action newAction) throws NullActionArgumentException {
        if(newAction != null) {
            Action old = this.skill; // reassign pointer of old action
            this.skill = newAction.clone();
            return old;
        }else
            throw new NullActionArgumentException("Setting actions require non null arguments.");
    }
    /**
     * set the critical Action. this returns the previous action that was assigned to this.critical.
     * if no actions were previously assigned, then this returns null.
     * @param newAction the action to set as critical.
     */
    public Action setCritical(Action newAction) throws NullActionArgumentException {
        if(newAction != null) {
            Action old = this.critical; // reassign pointer of old action
            this.critical = newAction.clone();
            return old;
        }else
            throw new NullActionArgumentException("Setting actions require non null arguments.");
    }
}