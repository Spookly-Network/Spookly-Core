package de.nehlen.spookly.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.nehlen.spookly.SpooklyCorePlugin;
import lombok.Getter;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.logging.Level;

public class ConnectionImpl implements Connection {
    private final SpooklyCorePlugin plugin;
    @Getter private final HikariDataSource hikariDataSource;

    public ConnectionImpl(final SpooklyCorePlugin plugin) {
        this.plugin = plugin;
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl((String) plugin.getDatabaseConfiguration().getOrSetDefault("database.url", "jdbc:mysql://localhost:3306/database"));
        hikariConfig.setUsername((String) plugin.getDatabaseConfiguration().getOrSetDefault("database.username", "username"));
        hikariConfig.setPassword((String) plugin.getDatabaseConfiguration().getOrSetDefault("database.password", "password"));
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        plugin.getDatabaseConfiguration().save();
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public void execute(String query, Object... args) {
        try(final java.sql.Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, args);
                preparedStatement.execute();
                hikariDataSource.getConnection().close();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void executeUpdate(String query, Consumer<Integer> callback, Object... args) {
        try(final java.sql.Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, args);
                callback.accept(preparedStatement.executeUpdate());
                hikariDataSource.getConnection().close();
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void executeQuery(String query, Consumer<ResultSet> callback, Object... args) {
        try(final java.sql.Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, args);
                callback.accept(preparedStatement.executeQuery());
                hikariDataSource.getConnection().close();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void executeAsync(final String query, final Object... args) {
        new Thread(() -> {
            try(final java.sql.Connection connection = hikariDataSource.getConnection()) {
                try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    addArguments(preparedStatement, args);
                    preparedStatement.execute();
                    hikariDataSource.getConnection().close();
                } catch(SQLException exception) {
                    exception.printStackTrace();
                }
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public void executeUpdateAsync(final String query, final Consumer<Integer> callback, final Object... args) {
        new Thread(() -> {
            try(final java.sql.Connection connection = hikariDataSource.getConnection()) {
                try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    addArguments(preparedStatement, args);
                    callback.accept(preparedStatement.executeUpdate());
                    hikariDataSource.getConnection().close();
                }
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public void executeQueryAsync(final String query, final Consumer<ResultSet> callback, final Object... args) {
        new Thread(() -> {
            try(final java.sql.Connection connection = hikariDataSource.getConnection()) {
                try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    addArguments(preparedStatement, args);
                    plugin.getLogger().log(Level.INFO, String.format("Executing SQL-Query: %s", preparedStatement.toString()));
                    callback.accept(preparedStatement.executeQuery());
                    hikariDataSource.getConnection().close();
                } catch(SQLException exception) {
                    exception.printStackTrace();
                }
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public Object get(String query, String arg, String selection) {
        try(final java.sql.Connection connection = hikariDataSource.getConnection()) {
            try(final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                addArguments(preparedStatement, arg);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    hikariDataSource.getConnection().close();
                    if(resultSet.next()) return resultSet.getObject(selection);
                } catch(SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void addArguments(PreparedStatement preparedStatement, Object... args) {
        try {
            int position = 1;
            for(final Object arg : args) {
                preparedStatement.setObject(position, arg);
                position++;
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            hikariDataSource.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
