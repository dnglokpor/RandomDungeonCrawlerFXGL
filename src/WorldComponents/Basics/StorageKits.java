package WorldComponents.Basics;

import java.util.Arrays;

import Customs.Exceptions.NegativeValueArgumentException;
import WorldComponents.Basics.Collectibles.Item;

/** StorageKits package:
 * this package contains the definition of multiple in-game elements that will be able to continually
 * store items like bags, chests and wallets.
 */
public class StorageKits {

    /** Bag object:
     * a bag is the item and explorer has on them and that allows them to store the items they find
     * during their adventure. a bag is made of slots that can each contains exactly one item. its
     * maximum capacity is BAG_SIZE items. the bag object is autonomous meaning it is able to self
     * manage its contents and provide specific data on its characteristics. since the bags contents
     * is an array, there will be lots of shuffling operations involved with managing the bag so to
     * help we add a inStock attribute that stores how many items are currently contained.
     */

    public static class Bag{
       // attributes
       public static final int BAG_SIZE = 50;
       private Item[] contents;
       private int inStock;

        /** constructor:
         * by default a bag is empty so no argument is expected. the list is built but nothing is
         * added to it. each slot will be initialized to null to show they're empty.
         */
        public Bag(){
            this.contents = new Item[BAG_SIZE];
            Arrays.fill(this.contents, null); // empty bag
            this.inStock = 0; // no item stored
        }

        // getters
        /**
         * get the contents array of the bag.
         */
        public Item[] contents(){
            return this.contents;
        }
        /**
         * get the number of items in stock.
         */
        public int stockSize(){
            return this.inStock;
        }
        /**
         * @return true if inStock == BAG_SIZE. return false if that is not the case.
         */
        public boolean isFull(){
            return this.inStock == BAG_SIZE;
        }

        // management

        /**
         * searches the contents of the bag for an item of the same name as provided as argument. the
         * search is case sensitive and stops at the first occurrence of such an item.
         * @param itemName the name of the item to look for.
         * @return the index of the item if it was found. a "-1" is returned if no occurrences were found.
         */
        public int nameSearch(String itemName){
            int index = -1; // failure flag
            if(this.inStock != 0){ // search occurs only if the bag contains something
                index = 0; // init for search
                boolean found = false; // search flag
                while((index < this.inStock) && !found){ // search loop
                    found = this.contents[index].name().equals(itemName);
                    if(!found)
                        index++; // check next item if not found
                }
            }
            return index;
        }
        /**
         * stores an item in the bag if there is space. the item is checked against each non empty slots.
         * if any slot has an item of the same name, the new item is inserted right after it. but if no
         * items of the same name are found, the item is inserted in the first available free slot.once
         * storing is complete, the inStock attribute is updated and a true is returned.
         * @param item the Item object to store inside the bag.
         * @return true if storing was successful; false if it failed because the bag was full.
         */
        public boolean store(Item item){
            if(this.isFull()){
                return false; // bag is full so we can't add anything
            }
            // there is space
            int i = 0;
            boolean sameItem = false; // search flag
            while(i < this.inStock && !sameItem){ // if inStock == 0 (empty bag) the search doesn't happen
                if(this.contents[i] != null) { // precautionary test
                    sameItem = this.contents[i].name().equals(item.name()); // compare names
                    if(sameItem){ // item by the same name found
                        // starting by the last non-empty slot we shuffle everything by one slot down
                        for(int j = this.inStock - 1; j > i; j--){
                            this.contents[j+1] = this.contents[j];
                        }
                        // then we store the item at the right place
                        this.contents[i+1] = item;
                        this.inStock++; // update number of items in bag
                        item = null; // delete old reference to the object
                    }
                }
                if(item != null) { // we didn't store it in the loop
                    // so we store it at the first empty slot instead and update inStock
                    this.contents[this.inStock++] = item;
                }
            }
            return true; // return successful storing
        }

        /**
         * takes the item identified by its index out of the array. the array is shuffled around to
         * fill the empty slot and the inStock attribute is reduced by one unit. of course if the bag
         * is empty, nothing will be taken out.
         * @param index the index of the item to take out in the this.contents.
         * @return the item to be taken out. if the bag is empty, returns null.
         */
        public Item takeOut(int index){
            if(this.inStock == 0){
                return null;
            }
            // there are items in the bag
            Item removed = this.contents[index]; // copy the reference
            for(int i = index; i < this.inStock; i++){
                this.contents[i] = this.contents[i+1]; // shuffle up
            }
            this.inStock--; // update number of items
            this.contents[this.inStock] = null; // delete last reference
            return removed;
        }
    }

    /** Wallet object:
     * a wallet is an container that the explorer uses to store their money - in game world currency.
     * an explorer is automatically awarded a wallet of infinite size. the wallet can provide
     * information about itself and manage its balance.
     */
    public static class Wallet{
        // attributes
        int balance;

        /** constructor:
         * expects no parameters. set the initial balance at 0.
         */
        public Wallet(){
            this.balance = 0;
        }

        // getters
        /**
         * @return the current balance of the Wallet object.
         */
        public int balance(){
            return this.balance;
        }
        /**
         * checks to see if the Wallet can afford the sum requested. sum has to be positive.
         * @param sum the amount of in-game currency to check for.
         * @return true if the balance is higher or equal to the sum. false if not.
         * @throws NegativeValueArgumentException when sum < 0.
         */
        public boolean hasEnough(int sum) throws NegativeValueArgumentException {
            if(sum < 0)
                throw new NegativeValueArgumentException("the sum cannot be negative");
            return this.balance >= sum;
        }

        // setters
        /**
         * adds the passed sum to the balance.
         * @param sum the amount to deposit.
         * @throws NegativeValueArgumentException when sum < 0.
         */
        public void deposit(int sum)throws NegativeValueArgumentException {
            if(sum < 0)
                throw new NegativeValueArgumentException("the sum cannot be negative");
            this.balance += sum;
        }
        /**
         * takes the requested sum out of the balance of the Wallet.
         * @param sum the amount to withdraw.
         * @return true if the sum has been withdrawn. false if the balance doesn't cover it.
         * @throws NegativeValueArgumentException when sum < 0.
         */
        public boolean withdraw(int sum) throws NegativeValueArgumentException{
           if(this.hasEnough(sum)){
               this.balance -= sum;
           }
           return this.hasEnough(sum);
        }

    }
}
