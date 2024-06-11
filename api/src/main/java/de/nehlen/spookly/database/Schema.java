package de.nehlen.spookly.database;

import java.sql.ResultSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public interface Schema {
    String getSchemaName();
    void createSchema();
    void set(String field, UUID identifier, Object value);
    void get(String field, UUID identifier, Consumer<ResultSet> consumer);
}
