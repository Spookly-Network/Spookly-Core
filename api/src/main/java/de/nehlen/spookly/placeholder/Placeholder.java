package de.nehlen.spookly.placeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Placeholder {
    private String key;
    private PlaceholderManager.PlaceholderReplaceHandler handler;
    private PlaceholderContext.PlaceholderType type;
}

