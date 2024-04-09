package de.nehlen.spookly.database;

public interface Schema {
    String getSchemaName();
    void createSchema();
}
