package WorldComponents.Basics;

import Customs.Exceptions.NullArgumentException;
import Customs.Exceptions.UndefinedKeyException;
import WorldComponents.Basics.Defines.*;
import WorldComponents.Basics.Collectibles.*;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Hashtable;

/** Body class
 * this class represents an adventurer body with the 4 parts they can equip gear like weapons and
 * armor on. Each part is uniquely determined by a Defines.BodySlot key and it holds a
 * Collectibles.Gear objects as value. a Body is instantiated empty but can be set up anytime.
 */
public class Body{
    // attributes
    protected Hashtable<BodySlot, Gear> slots;

    /** constructor
     * initialises the body with nothing on it; represented by null values. empty slot on the body
     * will always be null. this means Body objects will overwrite the property of hashtables to
     * return null when undefined keys by throwing an error.
     */
    public Body(){
        // create the slots table
        this.slots = new Hashtable<BodySlot, Gear>();
        this.slots.put(BodySlot.LHAND, null); // left hand
        this.slots.put(BodySlot.RHAND, null); // right hand
        this.slots.put(BodySlot.ARMOR, null); // body protection armor
        this.slots.put(BodySlot.WEARABLE, null); // accessory
    }
    /** copy constructor:
     * makes a new Body object with the same equipment as the model.
     * @param model the model Body to clone.
     * @throws UndefinedKeyException only of the original Body was flawed.
     */
    public Body(Body model) throws UndefinedKeyException {
       this.slots = new Hashtable<BodySlot, Gear>();
       this.slots.put(BodySlot.LHAND, model.wearsAt(BodySlot.LHAND));
       this.slots.put(BodySlot.RHAND, model.wearsAt(BodySlot.RHAND));
       this.slots.put(BodySlot.ARMOR, model.wearsAt(BodySlot.ARMOR));
       this.slots.put(BodySlot.WEARABLE, model.wearsAt(BodySlot.WEARABLE));
    }

    // getters
    /**
     * return the equipment piece in the specified "slot". possible "slot" values can be found in
     * the Defines.BodySlot enum. if nothing is equipped on that slot a null is returned.
     * if the slot requested doesn't exist the custom
     * defines.Constants.UndefinedKeyException is thrown.
     * @param slot the appropriate body slot to put the gear on.
     */
    public Gear wearsAt(BodySlot slot) throws UndefinedKeyException {
        if(!this.slots.containsKey(slot)){
            throw new UndefinedKeyException(slot + " is not a valid key!"); // key not found
        }
        // else key was found so we return the gear
        return this.slots.get(slot);
    }

    /**
     * @param slot the BodySlot slot of the Body to check for equipment.
     * @return true if the slot has a value of null.
     */
    public boolean slotIsEmpty(BodySlot slot) throws UndefinedKeyException{
        if(!this.slots.containsKey(slot)){
            throw new UndefinedKeyException(slot + " is not a valid key!"); // key not found
        }
        // else key exist and we can return
        return (this.wearsAt(slot) == null);
    }

    // setters
    /**
     * takes off the gear that is currently equipped on the specified slot. does nothing if the slot
     * is empty a.k.a the current gear is null.
     * @param slot the slot to empty. the slot argument must be a valid BodySlot value.
     * @return the object that was in that slot or null if it was empty.
     */
    public Gear takeOff(@NotNull BodySlot slot) throws UndefinedKeyException{
        if(this.slots.contains(slot)){ // slot argument is valid
            Gear old = this.slots.get(slot); // either get gear or null
            this.slots.replace(slot, old, null); // empty the slot
            return old;
        }else
            throw new UndefinedKeyException(slot + " is not a valid key!");
    }
    /**
     * put on the specified gear. the gear's BodyReq attributes will be used to determine where the
     * specified gear must go. this will also take off anything on the body that coincides with the
     * new gear to equip.
     * @param newGear the Gear object that must be equipped. can't be null. to empty a slot, use takeOff().
     * @return a list of all the gears that had to be taken off the body to put newGear on.
     */
    public Gear[] putOn(Gear newGear) throws UndefinedKeyException, NullArgumentException{
        if(newGear == null){
           throw new NullArgumentException("cannot equip a null object.");
        }else if(!Arrays.asList(
                new GearReq[]{GearReq.ONE_HAND, GearReq.TWO_HAND, GearReq.BODY, GearReq.ACCESSORY}
                ).contains(newGear.reqs())){ //
            throw new UndefinedKeyException(newGear.reqs() + " is not a valid slot.");
        } else{
            // we have a valid equipable gear piece so we equip. up to two items only can be removed
            // by a new equip. array is initialized with null values in case nothing is removed. the
            // default case will equip newGear.reqs() with values BODY and ACCESSORY.
            Gear[] removed = new Gear[]{null, null};
            // equip algorithm
            switch (newGear.reqs()){
                case ONE_HAND:
                    if(this.slotIsEmpty(BodySlot.RHAND)){ // RHAND is free so we equip there
                        this.slots.replace(BodySlot.RHAND, null, newGear);
                    }else{ //RHAND was not free so we equip at LHAND instead
                        removed[0] = takeOff(BodySlot.LHAND); // unequips what was at LHAND
                        this.slots.replace(BodySlot.LHAND, null, newGear);
                    }
                    break;
                case TWO_HAND:
                    // a two handed needs both hands to be free so we empty both hands
                    removed[0] = this.takeOff(BodySlot.RHAND);
                    removed[1] = this.takeOff(BodySlot.LHAND);
                    // then equip in the RHAND
                    this.slots.replace(BodySlot.RHAND, null, newGear);
                    break;
                case BODY: // the body armor
                    removed[0] = this.takeOff(BodySlot.ARMOR); // remove old armor
                    this.slots.replace(BodySlot.ARMOR, null, newGear);
                    break;
                default: // the accessory
                    removed[0] = this.takeOff(BodySlot.WEARABLE); // remove old armor
                    this.slots.replace(BodySlot.WEARABLE, null, newGear);
                    break;
            }
            return removed; // return the list of removed gear
        }
    }
}
