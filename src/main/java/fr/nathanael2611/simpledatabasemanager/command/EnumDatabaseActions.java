package fr.nathanael2611.simpledatabasemanager.command;

public enum EnumDatabaseActions {

    SET_STRING("setString"),
    SET_INTEGER("setInteger"),
    SET_DOUBLE("setDouble"),
    SET_FLOAT("setFloat"),
    SET_BOOLEAN("setBoolean"),

    GET_STRING("getString"),
    GET_INTEGER("getInteger"),
    GET_DOUBLE("getDouble"),
    GET_FLOAT("getFloat"),
    GET_BOOLEAN("getBoolean"),

    REMOVE_STRING("removeString"),
    REMOVE_INTEGER("removeInteger"),
    REMOVE_DOUBLE("removeDouble"),
    REMOVE_FLOAT("removeFloat"),
    REMOVE_BOOLEAN("removeBoolean");

    public final String actionName;

    EnumDatabaseActions(String actionName){
        this.actionName = actionName;
    }


    public static String[] getActionsNames(){
        String[] names = new String[values().length];
        int i = 0;
        for(EnumDatabaseActions actions : values()){
            names[i] = actions.actionName;
            i++;
        }
        return names;
    }
}
