package WorldComponents.Basics;

import WorldComponents.Basics.Defines.*;
import WorldComponents.Basics.Stats.*;
import Customs.Exceptions.WrongSizeStatsArrayException;

/** Collectibles package:
 * this source contains all the objects that determine an obtainable and stackable objects in
 * the game world. the Item class will be inherited by the other defined objects (gear, ...).
 */
public class Collectibles {

    /** Item object:
     * class that defines the basic concept of an item. it provides a name, a string description of
     * the item and also an integer value. a graphic description of items will be provided later
     * (image ref).
     */
    public static class Item{

        // attributes
        protected final String name;
        protected final String description;
        protected final int price;

        /** constructor:
         * expects a name and a description.
         */
        public Item(String name, String description, int value){
            this.name = name;
            this.description = description;
            this.price = value;
        }

        // copy constructor
        /**
         * construct a new Item that has exactly the same attributes as
         * the passed "model" Item.
         */
        public Item(Item model){
            this.name = model.name;
            this.description = model.description;
            this.price = model.price;
        }

        // getters
        /**
         * returns item's name.
         */
        public String name(){
            return this.name;
        }
        /**
         * returns item's description
         */
        public String describe(){
            return this.description;
        }
        /**
         * returns item's cost.
         */
        public int cost(){
            /* returns item value */
            return this.price;
        }
    }

    /** Gear object:
     * this class represents items that can be equipped; thus it adds to
     * the normal name, description and price fields of items, a type,
     * a set of stats and a slot requirement indicator.
     * types will be defined as an enum. requirements can be found in
     * the Defines.Constants package.
     */
    public static class Gear extends Item{

        // attributes
        private final GearType type;
        private final StatSet stats;
        private final GearReq req;

        /** constructor:
         * expects a name, a description and a price but also an array of initial stat values.
         * @param name the name of the gear.
         * @param description a small text description of the gear.
         * @param initStats an array of initial values for each stat of the gear.
         * @param price the value of the gear.
         * @param type the type of the gear.
         */
        public Gear(String name, String description, int price, GearType type, int[] initStats,
                    GearReq req) throws WrongSizeStatsArrayException
        {
            super(name, description, price); // construct item part
            // init gear attributes
            this.type = type;
            this.stats = new StatSet(initStats);
            this.req = req;
        }

        // getters
        /**
         * returns the type of the gear
         */
        public GearType type(){
            return this.type;
        }
        /**
         * returns the requirements of the gear
         */
        public GearReq reqs(){
            return this.req;
        }
        /**
         * returns the Statset object of the gear
         */
        public StatSet stats(){
            return this.stats;
        }
    }
}
