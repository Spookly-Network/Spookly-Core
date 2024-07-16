package de.spookly.phase;

/**
 * Interface representing the manager for game phases in the Spookly system.
 */
public interface GamePhaseManager {

    /**
     * Gets the current active game phase.
     *
     * @return the current game phase.
     */
    GamePhase getCurrentPhase();

    /**
     * Registers a game phase with a specified name.
     *
     * @param name      the name of the game phase.
     * @param gamePhase the game phase to register.
     */
    void registerPhase(String name, GamePhase gamePhase);

    /**
     * Starts a game phase with a specified name and initial counter value.
     *
     * @param name  the name of the game phase to start.
     * @param count the initial counter value.
     * @throws GamePhaseNotRegisteredException if the game phase is not registered.
     */
    void startPhase(String name, int count) throws GamePhaseNotRegisteredException;

    /**
     * Ends a game phase with a specified name.
     *
     * @param name the name of the game phase to end.
     * @throws GamePhaseNotRegisteredException if the game phase is not registered.
     */
    void endPhase(String name) throws GamePhaseNotRegisteredException;

    /**
     * Continues to the next game phase.
     *
     * @throws GamePhaseLastException if there is no next game phase.
     */
    void continueToNextPhase() throws GamePhaseLastException;
}

