package de.nehlen.spookly.Phase;

import de.nehlen.spookly.phase.GamePhase;
import de.nehlen.spookly.phase.GamePhaseLastException;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.phase.GamePhaseNotRegisteredException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class GamePhaseManagerImpl implements GamePhaseManager {

    private Map<String, GamePhase> gamePhases = new LinkedHashMap<>();

    public void registerPhase(@NotNull String name, @NotNull GamePhase gamePhase) {
        gamePhases.put(name, gamePhase);
    }

    @Nullable
    public GamePhase getCurrentPhase() {
        if(gamePhases.isEmpty()) {
            return null;
        }
        try {
            return gamePhases.values().iterator().next();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void startPhase(@NotNull String name, @NotNull int count) {
        GamePhase gamePhase = gamePhases.get(name);
        if (gamePhase != null) {
            gamePhase.counter(count);
            gamePhase.startPhase();
        } else {
            throw new GamePhaseNotRegisteredException("Game Phase with name " + name + " is not registered.");
        }
    }

    public void endPhase(@NotNull String name) {
        GamePhase gamePhase = gamePhases.get(name);
        if (gamePhase != null) {
            gamePhase.endPhase();
        } else {
            throw new GamePhaseNotRegisteredException("Game Phase with name " + name + " is not registered.");
        }
    }

    public void continueToNextPhase() {
        Map.Entry<String, GamePhase> currentGamePhase = gamePhases.entrySet().iterator().next();
        currentGamePhase.getValue().endPhase();
        gamePhases.remove(currentGamePhase.getKey());

        if(gamePhases.isEmpty()) {
            throw new GamePhaseLastException("No Game Phase to continue to.");
        }
        Map.Entry<String, GamePhase> newCurrentPhase = gamePhases.entrySet().iterator().next();
        newCurrentPhase.getValue().startPhase();
    }
}