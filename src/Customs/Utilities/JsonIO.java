package Customs.Utilities;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import WorldComponents.Basics.Actions.*;
import WorldComponents.Basics.Elements.*;
import WorldComponents.Basics.Collectibles.Item;
import WorldComponents.Mobs.Hostiles.Monster;
import Customs.Exceptions.UndefinedKeyException;

/** JsonIO package:
 * this package contains custom functions that allow parsing game data from .json files. this is done using
 * the JSON.simple version 1.1.1 library by "Yidong Fang".
 * data expected from json are multiple and must all be parsed separately by specific functions that are
 * defined here.
 */
public class JsonIO {
    // datafiles paths
    final static String ACTION_PATH = "./resources/JSON/Actions/";
    final static String ITEM_PATH = "./resources/JSON/Items/";

    /**
     * loads the data for the action specified by the name from the ACTION_PATH directory and makes it an Action.
     * @param actionName the action to look for.
     * @return built Action object.
     * @throws java.io.FileNotFoundException when the specified file couldn't be found
     * @throws java.io.IOException when reading the specified file failed
     * @throws org.json.simple.parser.ParseException when the json is empty or not valid.
     * @throws UndefinedKeyException when the json contains unexpected keys.
     */
    public static Action loadAction(String actionName) throws Exception {
        String filepath = ACTION_PATH + actionName + ".json"; // turn name into json filename
        // load data into memory
        Object obj = new JSONParser().parse(new FileReader(filepath));
        // cast as JSONObject
        JSONObject action = (JSONObject) obj;

        // make reference to returned object
        Action generated;
        // recover action type for calling right constructor
        String type = (String) action.get("type");
        // recover action data
        String name = (String) action.get("name");
        Element attribute = switch ((int) action.get("attribute")) {
            case -1 -> null;
            case 0 -> new Air();
            case 1 -> new Earth();
            case 2 -> new Water();
            case 3 -> new Fire();
            default -> {throw new UndefinedKeyException((int) action.get("attribute") +
                " is not a valid elemental attribute.");}
        };
        Category category = switch ((String) action.get("category")){
            case "physical" -> Category.PHYSICAL;
            case "special" -> Category.SPECIAL;
            default -> {throw new UndefinedKeyException((String) action.get("category") +
                    " is not a valid category");}
        };
        Target target = switch ((String) action.get("target")){
            case "self" -> Target.SELF;
            case "oppt" -> Target.OPPT;
            case "oppx" -> Target.OPPX;
            case "ally" -> Target.ALLY;
            case "allx" -> Target.ALLX;
            default -> {throw new UndefinedKeyException((String) action.get("target") +
                    " is not a valid target");}
        };
        float multiplier = (float) action.get("multiplier");
        int accuracy = (int) action.get("accuracy");
        int cooldown = (int) action.get("cooldown");
        int duration = (int) action.get("duration");
        String[] stargets = null; // placeholder
        if(!(type.equals("agress"))) { // only build stats list not Agress action.
            JSONArray ja = (JSONArray) action.get("stats");
            stargets = new String[ja.size()];
            int i = 0; // index for array
            for (Object o : ja) {
                stargets[i] = (String) o;
                i++; // update array position
            }
        }
        String description = (String) action.get("description");
        // build action based on its type
        generated = switch (type){
            case "agress" -> new Agress(name, attribute, category, target, multiplier, accuracy, cooldown,
                duration, description);
            case "buff" -> new Buff(name, attribute, category, target, multiplier, accuracy, cooldown,
                    duration, stargets, description);
            case "nerf" -> new Nerf(name, attribute, category, target, multiplier, accuracy, cooldown,
                    duration, stargets, description);
            default -> {throw new UndefinedKeyException(type + "is not a valid Action type.");}
        };

        // return it
        return generated;
    }

    /**
     * loads the data for the item specified by the name from the ITEM_PATH directory and makes it an Item.
     * @param itemName the item to look for.
     * @return built Item object.
     * @throws java.io.FileNotFoundException when the specified file couldn't be found
     * @throws java.io.IOException when reading the specified file failed
     * @throws org.json.simple.parser.ParseException when the json is empty or not valid.
     */
    public static Item loadItem(String itemName) throws Exception {
        String filepath = ITEM_PATH + itemName + ".json"; // turn name into json filename
        // load data into memory
        Object obj = new JSONParser().parse(new FileReader(filepath));
        // cast as JSONObject
        JSONObject item = (JSONObject) obj;
        // create Item and return it
        return new Item((String) item.get("name"), (String) item.get("description"),
            (int) item.get("value"));
    }

    /**
     * loads the data for the monster specified by the name from the MONSTER_PATH directory and makes it
     * a Monster or a Boss.
     * @param monsterName the item to look for.
     * @return built Monster object or Boss object.
     * @throws java.io.FileNotFoundException when the specified file couldn't be found
     * @throws java.io.IOException when reading the specified file failed
     * @throws org.json.simple.parser.ParseException when the json is empty or not valid.
     */
    //TODO public static Monster loadMonster(String itemName) throws Exception {


    //}
}
