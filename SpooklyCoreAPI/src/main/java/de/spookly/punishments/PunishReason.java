package de.spookly.punishments;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public enum PunishReason {

    HACKING("general.punishments.ban.hacking", 9999, TimeUnit.DAYS);

    private final String translationKey;
    private final Integer duration;
    private final TimeUnit durationUnit ;
}
