package Customs.Utilities;

import org.json.simple.JSONArray;

/** Helpers package:
 * collection of helper functions.
 */
public class Helpers {
    /**
     * converts a JSONArray object to an int[JSONArray.size()] and returns it.
     * @param ja a non null JSONArray object.
     * @return an int array of the same size as ja.
     */
    public static int[] jsonArrayToIntArray(JSONArray ja){
        int[] converted = new int[ja.size()];
        int i = 0;
        for(Object o : ja){
            converted[i++] = (int) o;
        }
        return converted;
    }

    /**
     * converts a JSONArray object to an String[JSONArray.size()] and returns it.
     * @param ja a non null JSONArray object.
     * @return a String array of the same size as ja.
     */
    public static String[] jsonArrayToStringArray(JSONArray ja){
        String[] converted = new String[ja.size()];
        int i = 0;
        for(Object o : ja){
            converted[i++] = (String) o;
        }
        return converted;
    }
}
