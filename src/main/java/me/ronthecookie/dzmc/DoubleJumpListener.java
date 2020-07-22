package me.ronthecookie.dzmc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener {
	// private Dangerzone dz = Dangerzone.getInstance();

	private HashMap<UUID, Boolean> doubleJumping = new HashMap<>();
	private final List<GameMode> allowedGMs = Arrays.asList(new GameMode[] { GameMode.SURVIVAL, GameMode.ADVENTURE });

	@EventHandler
	public void onFlyToggle(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (!this.doubleJumping.containsKey(p.getUniqueId()) || !p.hasPermission("dangerzone.doublejump")
				|| p.isFlying())
			return;
		p.setAllowFlight(false);
		p.setFlying(false);
		e.setCancelled(true);
		doubleJumping.remove(p.getUniqueId());
		p.setVelocity(p.getLocation().getDirection().multiply(1.3));
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (this.doubleJumping.containsKey(p.getUniqueId()) || !this.allowedGMs.contains(p.getGameMode())
				|| p.isFlying() || !p.hasPermission("dangerzone.doublejump"))
			return;

		if (p.getLocation().getBlock().getRelative(BlockFace.DOWN, 2).getType() == Material.AIR
				&& !p.hasPermission("dangerzone.infinijump"))
			return;
		doubleJumping.put(p.getUniqueId(), true);
		p.setAllowFlight(true);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		p.setAllowFlight(false);
		doubleJumping.remove(p.getUniqueId());
	}
}