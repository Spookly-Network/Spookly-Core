package de.nehlen.spookly.team.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public interface TeamDisplay {
    void setIcon(Component icon);
    Component getIcon();

    void setPrefix(Component prefix);
    Component getPrefix();

    void setColor(TextColor color);
    TextColor getColor();
}
