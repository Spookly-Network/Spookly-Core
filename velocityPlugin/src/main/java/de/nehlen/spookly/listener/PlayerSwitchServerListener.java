package de.nehlen.spookly.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;

import java.util.Optional;

public class PlayerSwitchServerListener {

    @Subscribe
    public void handlePlayerServerSwitch(ServerConnectedEvent event) {
        Optional<ServerConnection> connection = event.getPlayer().getCurrentServer();
        if (connection.isPresent()) {
            SpooklyOfflinePlayer player = Spookly.getPlayer(event.getPlayer().getUniqueId());
            connection.get().sendPluginMessage(ChannelIdentifier, byteArrayDataOutput -> {

                player.t
            })
        }

    }
}
