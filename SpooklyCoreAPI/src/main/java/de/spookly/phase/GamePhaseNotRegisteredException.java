package de.spookly.phase;

/**
 * Exception thrown when attempting to start or end a game phase that is not registered.
 */
public class GamePhaseNotRegisteredException extends RuntimeException {
    public GamePhaseNotRegisteredException(String message) {
        super(message);
    }
}
