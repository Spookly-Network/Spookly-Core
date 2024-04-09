package de.nehlen.spookly.phase;

public class GamePhaseNotRegisteredException extends RuntimeException {
    public GamePhaseNotRegisteredException(String message) {
        super(message);
    }
}
