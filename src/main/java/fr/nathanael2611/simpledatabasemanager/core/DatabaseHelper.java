package fr.nathanael2611.simpledatabasemanager.core;

import fr.nathanael2611.simpledatabasemanager.util.Helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple helper to manage your databases
 *
 * @author Nathanael2611
 */
public class DatabaseHelper {

    /**
     * The database
     */
    private Database db;

    public DatabaseHelper(Database db){
        this.db = db;
    }

    public Map.Entry getNumberEntry(HashMap<String, ? extends Number> map, String getType){
        if(map.isEmpty())return Helpers.createEntry("empty_map", 0);
        Map.Entry<String, Number> max = null;
        for(Map.Entry<String, ? extends Number> entry : map.entrySet()){
            if(max == null)max = (Map.Entry<String, Number>) entry;
            else {
                if(getType.equalsIgnoreCase("min")) {
                    if (max.getValue().doubleValue() < entry.getValue().doubleValue())
                        max = (Map.Entry<String, Number>) entry;
                }else if(getType.equalsIgnoreCase("max")){
                    if (max.getValue().doubleValue() > entry.getValue().doubleValue())
                        max = (Map.Entry<String, Number>) entry;
                }
            }
        }
        return max;
    }

    /**
     * get the max integer entry in the database
     */
    public Map.Entry getMaxIntegerEntry(){
        return getNumberEntry(db.INTEGERS, "max");
    }

    /**
     * get the max double entry in the database
     */
    public Map.Entry getMaxDoubleEntry(){
        return getNumberEntry(db.DOUBLES, "max");
    }

    /**
     * get the max float entry in the database
     */
    public Map.Entry getMaxFloatEntry(){
        return getNumberEntry(db.FLOATS, "max");
    }


    /**
     * get the min integer entry in the database
     */
    public Map.Entry getMinIntegerEntry(){
        return getNumberEntry(db.INTEGERS, "min");
    }

    /**
     * get the min double entry in the database
     */
    public Map.Entry getMinDoubleEntry(){
        return getNumberEntry(db.DOUBLES, "min");
    }

    /**
     * get the min float entry in the database
     */
    public Map.Entry getMinFloatEntry(){
        return getNumberEntry(db.FLOATS, "min");
    }


    /**
     * Increment an integer in the database
     */
    public void incrementInteger(String key, int value){
        db.setInteger(
                key,
                db.getInteger(key) + value
        );
    }

    /**
     * Decrement an integer in the database
     */
    public void decrementInteger(String key, int value){
        incrementInteger(key, -value);
    }

    /**
     * Increment a double in the database
     */
    public void incrementDouble(String key, double value){
        db.setDouble(
                key,
                db.getDouble(key) + value
        );
    }

    /**
     * Decrement a double in the database
     */
    public void decrementDouble(String key, double value){
        incrementDouble(key, -value);
    }

    /**
     * Increment a float in the database
     */
    public void incrementFloat(String key, float value){
        db.setFloat(
                key,
                db.getFloat(key) + value
        );
    }

    /**
     * Decrement a float in the database
     */
    public void decrementFloat(String key, float value){
        incrementFloat(key, -value);
    }

    /**
     * Revert a boolean (true -> false OR false -> true)
     */
    public void revertBoolean(String key){
        db.setBoolean(key, !db.getBoolean(key));
    }


}