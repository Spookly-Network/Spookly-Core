package de.spookly.database;

import org.bson.Document;

/**
 * Interface representing a codec for encoding and decoding database components in the Spookly system.
 *
 * @param <T> the type of data to encode and decode.
 */
public interface DatabaseComponentCodec<T> {

    /**
     * Encodes the specified data into a BSON document.
     *
     * @param data the data to encode.
     * @return the encoded BSON document.
     */
    Document encode(T data);

    /**
     * Decodes the specified BSON document into data.
     *
     * @param document the BSON document to decode.
     * @return the decoded data.
     */
    T decode(Document document);
}
