package de.spookly.phase;

/**
 * Interface representing a phase in a game in the Spookly system.
 */
public interface GamePhase {

    /**
     * Starts the game phase.
     */
    void startPhase();

    /**
     * Ends the game phase.
     */
    void endPhase();

    /**
     * Sets the counter for the game phase.
     *
     * @param counter the counter value to set.
     */
    void counter(Integer counter);

    /**
     * Gets the counter for the game phase.
     *
     * @return the counter value.
     */
    Integer counter();
}
