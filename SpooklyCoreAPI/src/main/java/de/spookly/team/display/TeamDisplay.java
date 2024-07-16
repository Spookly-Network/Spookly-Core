package de.spookly.team.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

/**
 * Interface representing the display properties of a team in the Spookly system.
 */
public interface TeamDisplay {

    /**
     * Sets the icon for the team display.
     *
     * @param icon the icon component.
     */
    void setIcon(Component icon);

    /**
     * Gets the icon for the team display.
     *
     * @return the icon component.
     */
    Component getIcon();

    /**
     * Sets the prefix for the team display.
     *
     * @param prefix the prefix component.
     */
    void setPrefix(Component prefix);

    /**
     * Gets the prefix for the team display.
     *
     * @return the prefix component.
     */
    Component getPrefix();

    /**
     * Sets the color for the team display.
     *
     * @param color the text color.
     */
    void setColor(TextColor color);

    /**
     * Gets the color for the team display.
     *
     * @return the text color.
     */
    TextColor getColor();
}
