package fr.nathanael2611.simpledatabasemanager.core;

/**
 * Simply a read-only database.
 *
 * @author Nathanael2611
 */
public class DatabaseReadOnly extends Database {

    public void printNoPermMessage(){
        System.err.println("Cannot write read-only databases");
    }

    @Override
    public void setBoolean(String key, boolean value) {
        printNoPermMessage();
    }

    @Override
    public void setDouble(String key, double value) {
        printNoPermMessage();
    }

    @Override
    public void setFloat(String key, float value) {
        printNoPermMessage();
    }

    @Override
    public void setInteger(String key, int value) {
        printNoPermMessage();
    }

    @Override
    public void setString(String key, String value) {
        printNoPermMessage();
    }

    @Override
    public String[] getAllIntegerEntry() {
        return super.getAllIntegerEntry();
    }

    @Override
    public String[] getAllBooleanEntry() {
        return super.getAllBooleanEntry();
    }

    @Override
    public String[] getAllFloatEntry() {
        return super.getAllFloatEntry();
    }

    @Override
    public String[] getAllDoubleEntry() {
        return super.getAllDoubleEntry();
    }

    @Override
    public String[] getAllStringEntry() {
        return super.getAllStringEntry();
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public boolean getBoolean(String key) {
        return super.getBoolean(key);
    }

    @Override
    public float getFloat(String key) {
        return super.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return super.getDouble(key);
    }

    @Override
    public int getInteger(String key) {
        return super.getInteger(key);
    }

    @Override
    public String getString(String key) {
        return super.getString(key);
    }

}
