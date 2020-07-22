package me.ronthecookie.dzmc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;

public class MoneyblockListener implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null)
			return;
		Player p = e.getPlayer();
		FileConfiguration config = dz.getConfig();
		double amount = config.getDouble("moneyblocks." + e.getClickedBlock().getType().name());
		if (amount == 0)
			return;
		if (!p.isSneaking() && e.getAction() == Action.LEFT_CLICK_BLOCK)
			e.setCancelled(true); // so they dont break it
		dz.getEco().depositPlayer(p, amount);
		p.sendActionBar(ChatColor.GREEN + "+ $" + String.valueOf(amount));
	}
}
