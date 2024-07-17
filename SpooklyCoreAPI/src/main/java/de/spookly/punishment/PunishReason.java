package de.spookly.punishment;

import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PunishReason {

    HACKING("general.punishments.ban.hacking", 9999, TimeUnit.DAYS);

    private final String translationKey;
    private final Integer duration;
    private final TimeUnit durationUnit ;
}
