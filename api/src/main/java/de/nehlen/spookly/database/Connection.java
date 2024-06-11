package de.nehlen.spookly.database;

import com.zaxxer.hikari.HikariDataSource;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Consumer;

public interface Connection extends Closeable {
    void execute(String query, Object... args);
    void executeUpdate(String query, Consumer<Integer> callback, Object... args);
    void executeQuery(String query, Consumer<ResultSet> callback, Object... args);
    void executeAsync(final String query, final Object... args);
    void executeUpdateAsync(final String query, final Consumer<Integer> callback, final Object... args);
    void executeQueryAsync(final String query, final Consumer<ResultSet> callback, final Object... args);
    Object get(String query, String arg, String selection);
    void addArguments(PreparedStatement preparedStatement, Object... args);
    void close();
    HikariDataSource getHikariDataSource();
}
