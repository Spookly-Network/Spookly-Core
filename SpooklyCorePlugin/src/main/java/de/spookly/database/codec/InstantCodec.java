package de.spookly.database.codec;

import java.time.Instant;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class InstantCodec implements Codec<Instant> {
    @Override
    public Instant decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return Instant.ofEpochMilli(bsonReader.readInt64());
    }

    @Override
    public void encode(BsonWriter bsonWriter, Instant instant, EncoderContext encoderContext) {
        bsonWriter.writeInt64(instant.toEpochMilli());
    }

    @Override
    public Class<Instant> getEncoderClass() {
        return Instant.class;
    }
}
