package de.nehlen.spookly.phase;

public interface GamePhase {
    void startPhase();
    void endPhase();
    void counter(Integer counter);
    Integer counter();
}
