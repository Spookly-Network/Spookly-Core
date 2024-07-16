package de.spookly.listener;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import de.spookly.SpooklyCorePlugin;
import de.spookly.player.PlayerPointsChangeEvent;
import de.spookly.player.PlayerRegisterEvent;
import de.spookly.team.PlayerJoinTeamEvent;
import de.spookly.team.PlayerQuitTeamEvent;
import de.spookly.team.TeamCreateEvent;
import de.spookly.team.TeamDestroyEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;


public class Listener implements org.bukkit.event.Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerRegister(PlayerRegisterEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onTeamCreate(TeamCreateEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onTeamDestroy(TeamDestroyEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerJoinTeam(PlayerJoinTeamEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerQuitTeam(PlayerQuitTeamEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerPointsChange(PlayerPointsChangeEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerAdvancementCriterionGrant(PlayerAdvancementCriterionGrantEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncChatEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerDamage(EntityDeathEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityExplodeEntity(EntityExplodeEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerBucketEntity(PlayerBucketEntityEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onPlayerDeepSleep(PlayerDeepSleepEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onAreaEffectCloudApply(AreaEffectCloudApplyEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onArrowBodyCountChange(ArrowBodyCountChangeEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onBatToggleSleepEvent(BatToggleSleepEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onCreeperPowerEvent(CreeperPowerEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEnderDragonChangePhase(EnderDragonChangePhaseEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityAirChange(EntityAirChangeEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityBreakDoor(EntityBreakDoorEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityBreedEvent(EntityBreedEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void  onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityCombustByBlock(EntityCombustEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        SpooklyCorePlugin.getInstance().getEventExecuter().reciveEventAction(event);
    }

}
