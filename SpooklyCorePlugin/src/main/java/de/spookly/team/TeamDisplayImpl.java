package de.spookly.team;

import de.spookly.team.display.TeamDisplay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDisplayImpl implements TeamDisplay {
    private Component icon;
    private Component prefix;
    private TextColor color;
}
