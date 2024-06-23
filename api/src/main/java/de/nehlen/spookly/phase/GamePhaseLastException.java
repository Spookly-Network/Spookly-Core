package de.nehlen.spookly.phase;

/**
 * Exception thrown when attempting to continue to the next phase but there is no next phase.
 */
public class GamePhaseLastException extends RuntimeException {

    public GamePhaseLastException(String message) {
        super(message);
    }
}
