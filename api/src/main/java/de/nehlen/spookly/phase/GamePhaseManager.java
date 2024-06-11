package de.nehlen.spookly.phase;

public interface GamePhaseManager {
    GamePhase getCurrentPhase();
    void registerPhase(String name, GamePhase gamePhase);
    void startPhase(String name, int count) throws GamePhaseNotRegisteredException;
    void endPhase(String name) throws GamePhaseNotRegisteredException;
    void continueToNextPhase() throws GamePhaseLastException;
}
