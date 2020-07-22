package me.ronthecookie.dzmc;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;

public class ISDCuffs extends BukkitRunnable implements Listener {
	// private Dangerzone dz = Dangerzone.getInstance();

	// cuffer, cuffed
	@Getter
	private HashMap<Player, Player> cuffed = new HashMap<>();

	@EventHandler
	public void onClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() != EntityType.PLAYER || e.getHand() != EquipmentSlot.HAND)
			return;
		Player clicker = e.getPlayer();
		Player clicked = (Player) e.getRightClicked();
		if (clicker.getInventory().getItemInMainHand().getType() != Material.BIRCH_FENCE_GATE)
			return;

		if (!clicker.hasPermission("dangerzone.cuffs")) {
			clicker.sendActionBar(ChatColor.RED + "No permission to use ISD cuffs");
			clicker.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			return;
		}
		if (clicked.hasPermission("dangerzone.uncuffable")) {
			clicker.sendActionBar(ChatColor.RED + "Target is uncuffable");
			return;
		}
		if (clicked.hasMetadata("NPC")) {
			clicker.sendActionBar(ChatColor.RED + "It's a NPC u idot ~ron");
			return;
		}

		if (cuffed.containsKey(clicker) && cuffed.get(clicker).equals(clicked)) {
			cuffed.remove(clicker);
			clicker.sendActionBar(ChatColor.AQUA + "Uncuffed " + ChatColor.YELLOW + clicked.getName());
			clicked.sendActionBar(ChatColor.AQUA + "Uncuffed by " + ChatColor.YELLOW + clicker.getName());
		} else {
			cuffed.put(clicker, clicked);
			clicker.sendActionBar(ChatColor.AQUA + "Cuffed " + ChatColor.YELLOW + clicked.getName());
			clicked.sendActionBar(ChatColor.AQUA + "Cuffed by " + ChatColor.YELLOW + clicker.getName());
		}
	}

	public void run() {
		this.cuffed.forEach((cuffer, cuffed) -> {
			if (cuffer.getInventory().getItemInMainHand().getType() != Material.BIRCH_FENCE_GATE) {
				this.cuffed.remove(cuffer);
				return;
			}
			Location pos = cuffer.getLocation().add(cuffer.getLocation().getDirection().setY(0).multiply(1.2));
			// pos.setYaw(cuffed.getLocation().getYaw());
			// pos.setPitch(cuffed.getLocation().getPitch());
			cuffed.teleport(pos);
		});
	}
}
