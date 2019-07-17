package fr.nathanael2611.simpledatabasemanager.core;

import javax.annotation.PreDestroy;

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
}
