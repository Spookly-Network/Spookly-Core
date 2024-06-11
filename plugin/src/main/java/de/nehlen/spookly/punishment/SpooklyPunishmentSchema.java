package de.nehlen.spookly.punishment;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.database.Connection;
import de.nehlen.spookly.database.Schema;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.punishments.PunishmentType;
import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SpooklyPunishmentSchema implements Schema {

    @Getter
    private String schemaName = Spookly.getServer().getSchemaPrefix() + "punishments";
    private Connection connection;

    public SpooklyPunishmentSchema(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createSchema() {
        StringBuilder table = new StringBuilder();
        table.append("`uuid` VARCHAR(64) NOT NULL UNIQUE, ");
        table.append("`player_uuid` VARCHAR(64) NOT NULL, ");
        table.append("`type` TEXT NOT NULL, ");
        table.append("`until` TIMESTAMP NOT NULL, ");
        table.append("`reason` TEXT NOT NULL, ");
        table.append("`created_by` VARCHAR(64) NOT NULL, ");
        table.append("`updated_by` VARCHAR(64) NOT NULL, ");
        table.append("`last_edited_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, ");
        table.append("`created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, ");

        table.append("constraint punishment_pk primary key (uuid, player_uuid), ");
        table.append("constraint punishment_pk_2 unique (uuid), ");
        table.append("constraint punishment_player_uuid_fk foreign key (player_uuid) references spookly_player (uuid), ");
        table.append("constraint punishment_creator_uuid_fk foreign key (created_by) references spookly_player (uuid), ");
        table.append("constraint punishment_updater_uuid_fk foreign key (updated_by) references spookly_player (uuid)");
        connection.executeUpdateAsync("CREATE TABLE IF NOT EXISTS " + schemaName + " (" + table.toString() + ")", resultSet -> {
        });
    }

    @Override
    public void set(String field, UUID identifier, Object value) {
        String query = "UPDATE " + schemaName + " SET " + field + " = ? WHERE uuid = ?";
        connection.executeUpdateAsync(query, integer -> {
        }, value, identifier);
    }

    @Override
    public void get(String field, UUID identifier, Consumer<ResultSet> consumer) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String query = "SELECT " + field + " FROM " + schemaName + " WHERE uuid = ?";

        executorService.submit(() -> {
            try (final java.sql.Connection con = this.connection.getHikariDataSource().getConnection()) {
                try (final PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    connection.addArguments(preparedStatement, identifier);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            consumer.accept(resultSet);
                            connection.close();
                        } else {
                            consumer.accept(null);
                            connection.close();
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    public void addPunishment(Punishment punishment) {
        String query = "INSERT INTO " + schemaName + " (uuid, player_uuid, type, reason, until, created_by, updated_by) VALUES (?,?,?,?,?,?,?)";
        connection.executeUpdateAsync(query, integer -> {
                }, punishment.getUniqueId().toString(),
                punishment.getPlayerUniqueId().toString(),
                punishment.getType().toString(),
                punishment.getReason(),
                Timestamp.from(punishment.getExpiry()),
                punishment.getCreator().toString(),
                punishment.getCreator().toString());
    }

    public void getActivePunishments(UUID playerUuid, Consumer<Optional<List<Punishment>>> consumer) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String query = "SELECT * FROM " + schemaName + " WHERE player_uuid = ? AND until > UTC_TIMESTAMP()";

        executorService.submit(() -> {
            try (final java.sql.Connection con = this.connection.getHikariDataSource().getConnection()) {
                try (final PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    connection.addArguments(preparedStatement, playerUuid.toString());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        SpooklyCorePlugin.getInstance().getLogger().info(preparedStatement.toString());
                        if (resultSet.next()) {
                            SpooklyCorePlugin.getInstance().getLogger().info("Has punishment yeeees");
                            List<Punishment> punishments = new ArrayList<>();
                            do {
                                punishments.add(new PunishmentImpl(
                                        UUID.fromString(resultSet.getString("uuid")),
                                        UUID.fromString(resultSet.getString("player_uuid")),
                                        PunishmentType.valueOf(resultSet.getString("type")),
                                        resultSet.getTimestamp("until").toInstant(),
                                        resultSet.getString("reason"),
                                        UUID.fromString(resultSet.getString("created_by")),
                                        UUID.fromString(resultSet.getString("updated_by")),
                                        resultSet.getTimestamp("created_at").toInstant(),
                                        resultSet.getTimestamp("last_edited_at").toInstant()
                                ));
                                SpooklyCorePlugin.getInstance().getLogger().info("Created punishment");
                            } while (resultSet.next());
                            consumer.accept(Optional.of(punishments));
                            connection.close();
                        } else {
                            consumer.accept(Optional.empty());
                            connection.close();
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
        executorService.shutdown();
    }


}
