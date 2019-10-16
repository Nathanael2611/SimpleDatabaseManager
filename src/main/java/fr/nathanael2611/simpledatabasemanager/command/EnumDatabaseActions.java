package fr.nathanael2611.simpledatabasemanager.command;

/**
 * Contain all actions that can be applied on databases
 */
public enum EnumDatabaseActions {

    SET_STRING("setString"),
    SET_INTEGER("setInteger"),
    SET_DOUBLE("setDouble"),
    SET_FLOAT("setFloat"),
    GET("get"),
    REMOVE("remove");

    /* the database-action name */
    public final String actionName;

    /**
     * Enum-Constructor
     */
    EnumDatabaseActions(String actionName){
        this.actionName = actionName;
    }

    /**
     * @return all databases names as String array
     */
    public static String[] getActionsNames(){
        String[] names = new String[values().length];
        int i = 0;
        for(EnumDatabaseActions action : values())
        {
            names[i] = action.actionName;
            i++;
        }
        return names;
    }

}
