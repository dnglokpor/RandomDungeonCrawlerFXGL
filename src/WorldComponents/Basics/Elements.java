package WorldComponents.Basics;

/** Elements class package:
 * contains the definition of the base elements of this world. the 4 elements are Air, Earth, Fire
 * and Water. some world objects will be given an element as elemental attributes. elements have
 * interactions between each other thus elemental attributes will also rule the interaction between
 * objects with elemental attributes. elements will be based on an base Element object. Element will
 * be built using an enum that identify each element by its name to make things convenient.
 */
public class Elements {

    // manual enum that associates name to values
    public static final int AIR = 0;
    public static final int EARTH = 1;
    public static final int WATER = 2;
    public static final int FIRE = 3;

    /** Element object:
     * this object will be the base that all 4 elements inherit. this sets an elemental id that will
     * be used for calculating weakness and strength between elements. these are the interaction rules:
     * (1) each element is weak to itself;
     * (2) AIR > EARTH > WATER > FIRE > AIR;
     * (3) AIR < FIRE < WATER < EARTH < AIR.
     */
    public static abstract class Element{
        // attributes
        private final String name;
        private final int elementalID;

        /** constructor:
         * expects a name and an id. for best readability, use constants defined at the top of the
         * WorldComponents.Basics.Elements package.
         * @param name
         * @param id
         */
        public Element(String name, int id){
            this.name = name;
            this.elementalID = id;
        }

        // getter
        /**
         * return value of name
         */
        public String name(){
            return this.name;
        }
        /**
         * return value of elementalID
         */
        public int elementalId(){
            return this.elementalID;
        }
        /**
         * return elementalID of element weak to
         */
        public int weakness(){
            if(this.elementalID == AIR) // if its AIR a.k.a 0
                return FIRE; // wrap around and print 3 a.k.a FIRE
            else
                // else just return element right before this one
                return elementalID - 1;
        }
        /**
         * return elementalID of element effective on
         */
        public int effectiveness(){
            if(this.elementalID == FIRE) // if its FIRE a.k.a 3
                return AIR; // wrap around and print 0 a.k.a AIR
            else
                // else just return element right after this one
                return elementalID + 1;
        }

        // other methods
        /**
         * returns a boolean that is true if
         * this.elementalID == other.elementalID or this.weakness() == other.elementalID.
         * otherwise returns false
         * @param other
         * @return a boolean
         */
        public boolean weakTo(Element other){
            return (this.elementalID == other.elementalID) ||
                    (this.weakness() == other.elementalID);
        }
        /**
         * returns a boolean that is true if
         * this.effectiveness() == other.elementalID. otherwise returns false
         * @param other
         * @return a boolean
         */
        public boolean effectiveOn(Element other){
            return (this.effectiveness() == other.elementalID);
        }
    }

    /** Air object:
     * object that represents the Air element. inherits ElementalCore.
     */
    public static class Air extends Element{
        /** constructor:
         * expects no parameters.
         */
        public Air(){
            super("Air", AIR);
        }
    }

    /** Earth object:
     * object that represents the Earth element. inherits ElementalCore.
     */
    public static class Earth extends Element{
        /** constructor:
         * expects no parameters.
         */
        public Earth(){
            super("Earth", EARTH);
        }
    }

    /** Water object:
     * object that represents the Water element. inherits ElementalCore.
     */
    public static class Water extends Element{
        /** constructor:
         * expects no parameters.
         */
        public Water(){
            super("Water", WATER);
        }
    }

    /** Fire object:
     * object that represents the Fire element. inherits ElementalCore.
     */
    public static class Fire extends Element{
        /** constructor:
         * expects no parameters.
         */
        public Fire(){
            super("Fire", FIRE);
        }
    }
}
