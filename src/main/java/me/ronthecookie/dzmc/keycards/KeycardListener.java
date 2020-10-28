package me.ronthecookie.dzmc.keycards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class KeycardListener implements Listener {
	// private Dangerzone dz = Dangerzone.getInstance();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null)
			return;

		Keycard requiredKeycard = Keycard.materialOf(e.getClickedBlock().getType());
		Keycard playerKeycard = Keycard.itemOf(e.getItem());

		Player p = e.getPlayer();
		if (requiredKeycard == null)
			return;

		boolean groupPass = playerKeycard.group == KeycardGroup.IGNORE || playerKeycard.group == requiredKeycard.group;

		if (playerKeycard.level >= requiredKeycard.level && groupPass) {
			p.sendActionBar(ChatColor.GREEN + "Access granted");
		} else {
			e.setCancelled(true);
			p.sendActionBar(ChatColor.RED + "Access denied" + ChatColor.GRAY + " (need " + requiredKeycard.displayName
					+ " or above)");
		}
	}
}
