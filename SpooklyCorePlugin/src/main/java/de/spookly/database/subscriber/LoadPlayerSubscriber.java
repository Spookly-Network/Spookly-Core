package de.spookly.database.subscriber;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;

import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import de.spookly.player.SpooklyOfflinePlayer;
import de.spookly.player.SpooklyOfflinePlayerImpl;
import de.spookly.punishment.Punishment;
import de.spookly.punishment.PunishmentImpl;
import de.spookly.punishment.PunishmentType;

public class LoadPlayerSubscriber implements Subscriber<Document> {

    private boolean foundEntry = false;
    private final Consumer<SpooklyOfflinePlayer> callback;

    public LoadPlayerSubscriber(final Consumer<SpooklyOfflinePlayer> callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    public void onNext(Document document) {
        SpooklyOfflinePlayer offlinePlayer = SpooklyOfflinePlayerImpl.builder(document.get("uuid", UUID.class))
                .name(document.getString("name"))
                .texture(document.getString("texture"))
                .points(document.getInteger("points"))
                .firstPlayed(convertToInstant(document.get("firstPlayed")))
                .lastPlayed(convertToInstant(document.get("lastPlayed")))
                .punishments(document.getList("punishments", org.bson.Document.class).stream().map(doc -> {
                    return (Punishment) new PunishmentImpl(
                            doc.get("uuid", UUID.class),
                            null,
                            PunishmentType.valueOf(doc.getString("type")),
                            convertToInstant(doc.get("expiry")),
                            doc.getString("reason"),
                            doc.get("creator", UUID.class),
                            doc.get("lastUpdater", UUID.class),
                            convertToInstant(doc.get("createdAt")),
                            convertToInstant(doc.get("updatedAt"))
                    );
                }).toList())
                .build();
        this.foundEntry = true;
        callback.accept(offlinePlayer);
    }

    @Override
    public void onError(Throwable throwable) {
        callback.accept(null);
    }

    @Override
    public void onComplete() {
        if (!this.foundEntry)
            callback.accept(null);
    }

    private Instant convertToInstant(Object value) {
        if (value instanceof Instant) {
            return (Instant) value;
        } else if (value instanceof Date) {
            return ((Date) value).toInstant();
        } else if (value instanceof String) {
            return Instant.parse((String) value);
        } else {
            return null;
        }
    }
}
