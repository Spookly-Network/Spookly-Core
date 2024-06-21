package de.nehlen.spookly.database;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

public interface DatabaseConnection {
    @Nullable
    <TDocument> MongoCollection<TDocument> getCollection(String var1, Class<TDocument> var2);

    @Nullable
    MongoCollection<Document> getCollection(String var1);

    @Nullable
    MongoClient getMongoClient();

    @Nullable
    MongoDatabase getMongoDatabase();
}
