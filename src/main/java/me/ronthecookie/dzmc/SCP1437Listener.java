package me.ronthecookie.dzmc;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class SCP1437Listener implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();

	private HashMap<UUID, Long> cooldown = new HashMap<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock().getType() != Material.BLACK_CONCRETE_POWDER
				|| e.getHand() != EquipmentSlot.HAND)
			return;
		Player p = e.getPlayer();
		if (this.cooldown.containsKey(p.getUniqueId())
				&& (System.currentTimeMillis() - this.cooldown.get(p.getUniqueId())) < (1000 * 15)) {
			return;
		}

		Random rng = new Random();
		SCPAction act = SCPAction.values()[rng.nextInt(SCPAction.values().length)];
		if (act.actionType == SCPAT.MSG) {
			p.sendMessage(ChatColor.DARK_RED + act.action);
		} else {
			dz.getServer().dispatchCommand(dz.getServer().getConsoleSender(), String.format(act.action, p.getName()));
		}
		cooldown.put(p.getUniqueId(), System.currentTimeMillis());
	}
}

enum SCPAT {
	MSG, CMD;
}

enum SCPAction {
	GAP(SCPAT.CMD, "minecraft:give %s minecraft:golden_apple 1"),
	STEAK(SCPAT.CMD, "minecraft:give %s minecraft:cooked_beef 1"),
	BOOK(SCPAT.CMD, "minecraft:give %s minecraft:writeable_book 1"), AK(SCPAT.CMD, "shot give %s ak-47"),
	MEAN(SCPAT.MSG, "Leave my room or there will be consequences..."),
	MEAN2(SCPAT.MSG, "Stop using my generosity to your advantage..."),
	MEAN3(SCPAT.MSG, "Go away and never come back..."), MEAN4(SCPAT.MSG, "You don't deserve all of this stuff..."),
	MEAN5(SCPAT.MSG, "I think I should stop being so nice to everyone...");

	public SCPAT actionType;
	public String action;

	SCPAction(SCPAT at, String action) {
		this.action = action;
		this.actionType = at;
	}
}