package de.spookly.database;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.InstantCodec;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jetbrains.annotations.Nullable;

import de.spookly.SpooklyCorePlugin;

public class MongoDatabaseConfiguration implements DatabaseConnection {

    private SpooklyCorePlugin plugin;

    private final String connectionString;
    private final String username;
    private final String password;
    private final String databaseName;
    private final String collectionPrefix;

    @Getter @Nullable
    private MongoClient mongoClient;
    @Getter @Nullable
    private MongoDatabase mongoDatabase;

    public MongoDatabaseConfiguration(final SpooklyCorePlugin plugin) {
        this.plugin = plugin;

        this.connectionString = plugin.getDatabaseConfiguration().getOrSetDefault("connection.string", "mongodb://%s:%s@localhost:27017");
        this.username = plugin.getDatabaseConfiguration().getOrSetDefault("connection.auth.username", "username");
        this.password = plugin.getDatabaseConfiguration().getOrSetDefault("connection.auth.password", "admin");
        this.databaseName = plugin.getDatabaseConfiguration().getOrSetDefault("connection.database", "spookly");
        this.collectionPrefix = plugin.getDatabaseConfiguration().getOrSetDefault("collectionPrefix", "spookly_");
    }

    public void connect() {
        ConnectionString connectionString = new ConnectionString(String.format(this.connectionString, username, password));
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecs = CodecRegistries.fromCodecs(new InstantCodec());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry,
                codecs);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();

        try {
            this.mongoClient = MongoClients.create(clientSettings);
            this.mongoDatabase = mongoClient.getDatabase(databaseName);
        } catch (Exception ex) {
            plugin.getLogger().warning("Failed to connect to MongoDB database,");
            plugin.getLogger().warning("some things will not work as expected: " + ex.getMessage());
        }
    }

    @Nullable
    public <TDocument> MongoCollection<TDocument> getCollection(String var1, Class<TDocument> var2) {
        String collectionName = collectionPrefix + var1;

        if (mongoDatabase == null) {
            plugin.getLogger().severe("Could not load MongoDB database collection " + collectionName);
            plugin.getLogger().severe("MongoDB database is not connected or initialized yet.");
            return null;
        }

        return this.mongoDatabase.getCollection(collectionName, var2);
    }

    @Nullable
    public MongoCollection<Document> getCollection(String var1) {
        String collectionName = collectionPrefix + var1;

        if (mongoDatabase == null) {
            plugin.getLogger().severe("Could not load MongoDB database collection " + collectionName);
            plugin.getLogger().severe("MongoDB database is not connected or initialized yet.");
            return null;
        }

        return this.mongoDatabase.getCollection(collectionName);
    }
}
