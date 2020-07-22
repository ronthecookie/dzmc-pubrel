package me.ronthecookie.dzmc;

import java.util.concurrent.ExecutionException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.luckperms.api.LuckPerms;
// import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class CIJoinListener implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) throws InterruptedException, ExecutionException {
		if (e.getHand() != EquipmentSlot.HAND || e.getAction() != Action.RIGHT_CLICK_BLOCK
				|| e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.GRAY_GLAZED_TERRACOTTA)
			return;
		LuckPerms lp = dz.getLp();
		Player p = e.getPlayer();
		if (p.hasPermission("group.scpf")) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1, 1);
			p.sendActionBar(ChatColor.RED + "SCPF cannot join the Chaos Insurgency.");
			return;
		}
		if (p.hasPermission("group.ci")) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1, 1);
			p.sendActionBar(ChatColor.RED + "You are already in the Chaos Insurgency.");
			return;
		}
		User lpu = lp.getUserManager().getUser(p.getUniqueId());

		lpu.getDistinctNodes().forEach(node -> {
			if (node.getKey().equals("group.default")) {
				lpu.data().remove(node);
				lpu.data().add(Node.builder("group.ci").build());
			}
		});
		lp.getUserManager().saveUser(lpu).get();

		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, SoundCategory.PLAYERS, 1, 1);
		p.getInventory().clear();
		dz.getServer().dispatchCommand(dz.getServer().getConsoleSender(), "kit CI" + " " + p.getName());
		p.sendActionBar(ChatColor.GREEN + "You have joined the Chaos Insurgency!");
	}
}
