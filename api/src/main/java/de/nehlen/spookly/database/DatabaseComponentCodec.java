package de.nehlen.spookly.database;

import org.bson.Document;

public interface DatabaseComponentCodec<T> {

    Document encode(T data);
    T decode(Document document);

}
