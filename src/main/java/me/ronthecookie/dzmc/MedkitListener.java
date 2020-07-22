package me.ronthecookie.dzmc;

import java.util.HashMap;
import java.util.UUID;

import com.destroystokyo.paper.Title;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MedkitListener implements Listener {
	// private Dangerzone dz = Dangerzone.getInstance();

	private HashMap<UUID, Long> cooldown = new HashMap<>();

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Player clicker = e.getPlayer();
		if (e.getHand() != EquipmentSlot.HAND || !(e.getRightClicked() instanceof Player)
				|| clicker.getInventory().getItemInMainHand().getType() != Material.RED_WOOL)
			return;
		Player clicked = (Player) e.getRightClicked();
		if (this.cooldown.containsKey(clicked.getUniqueId())
				&& (System.currentTimeMillis() - this.cooldown.get(clicked.getUniqueId())) < (1000 * 69)) {
			clicker.sendActionBar(ChatColor.RED + clicked.getName() + " is on cooldown!");
			return;
		}
		// TODO: make it stop the slowness based on when player finishes healing instead
		// of this crap
		if (clicker.getGameMode() != GameMode.CREATIVE) {
			ItemStack mh = clicker.getInventory().getItemInMainHand();
			if (mh.getAmount() == 1)
				clicker.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			else
				mh.setAmount(mh.getAmount() - 1);
		}

		clicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 100, false, false, false));
		clicked.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 2, false, false, false));

		clicker.sendActionBar(ChatColor.GREEN + "Healing " + clicked.getName());
		clicked.sendTitle(Title.builder().title("").subtitle(ChatColor.GREEN + "Healing...").stay(5 * 20).build());

		cooldown.put(clicked.getUniqueId(), System.currentTimeMillis());
	}
}
