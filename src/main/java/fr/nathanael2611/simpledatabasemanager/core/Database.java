package fr.nathanael2611.simpledatabasemanager.core;

/**
 * The Database class.
 * Can be serialized to nbt.
 * Can be deserialized from nbt.
 *
 * @author Nathanael2611
 */
public class Database extends DatabaseReadOnly{

    /**
     * A empty constructor, for deserialize an nbt after
     */
    public Database(){}

    /**
     * Constructor for create an empty database with a custom name
     */
    public Database(String id){
        this.id = id;
    }

    /**
     * Define a string with a specific key
     */
    public void setString(String key, String value){
        STRINGS.put(key, value);
        save();
    }

    /**
     * Remove a string with a specific key
     */
    public void removeString(String key) {
        STRINGS.remove(key);
        save();
    }

    /**
     * Define an integer with a specific key
     */
    public void setInteger(String key, int value){
        INTEGERS.put(key, value);
        save();
    }

    /**
     * Remove an integer with a specific key
     */
    public void removeInteger(String key) {
        INTEGERS.remove(key);
        save();
    }

    /**
     * Define a double with a specific key
     */
    public void setDouble(String key, double value){
        DOUBLES.put(key, value);
        save();
    }

    /**
     * Remove a double with a specific key
     */
    public void removeDouble(String key) {
        DOUBLES.remove(key);
        save();
    }

    /**
     * Define a float with a specific key
     */
    public void setFloat(String key, float value){
        FLOATS.put(key, value);
        save();
    }

    /**
     * Remove a float with a specific key
     */
    public void removeFloat(String key) {
        FLOATS.remove(key);
        save();
    }

    /**
     * Set a boolean with a specific key
     */
    public void setBoolean(String key, boolean value){
        BOOLEANS.put(key, value);
        save();
    }

    /**
     * Remove a boolean with a specific key
     */
    public void removeBoolean(String key) {
        BOOLEANS.remove(key);
        save();
    }

    /**
     * Convert database to read-only one
     */
    public DatabaseReadOnly toReadOnly(){
        DatabaseReadOnly readOnly = new DatabaseReadOnly();
        readOnly.deserializeNBT(this.serializeNBT());
        return readOnly;
    }

    /* The database helper instance */
    private DatabaseHelper helper = new DatabaseHelper(this);

    /**
     * Return the DatabaseHelper
     */
    public DatabaseHelper getHelper(){
        return helper;
    }

}
