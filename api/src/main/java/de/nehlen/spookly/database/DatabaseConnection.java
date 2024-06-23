package de.nehlen.spookly.database;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

/**
 * Interface representing a connection to a MongoDB database in the Spookly system.
 */
public interface DatabaseConnection {

    /**
     * Gets a collection from the database with the specified name and document class.
     *
     * @param <TDocument> the type of the documents in the collection.
     * @param name        the name of the collection.
     * @param clazz       the class of the documents in the collection.
     * @return the collection, or null if the collection does not exist.
     */
    @Nullable
    <TDocument> MongoCollection<TDocument> getCollection(String name, Class<TDocument> clazz);

    /**
     * Gets a collection from the database with the specified name.
     *
     * @param name the name of the collection.
     * @return the collection, or null if the collection does not exist.
     */
    @Nullable
    MongoCollection<Document> getCollection(String name);

    /**
     * Gets the MongoDB client.
     *
     * @return the MongoDB client, or null if the client is not available.
     */
    @Nullable
    MongoClient getMongoClient();

    /**
     * Gets the MongoDB database.
     *
     * @return the MongoDB database, or null if the database is not available.
     */
    @Nullable
    MongoDatabase getMongoDatabase();
}
