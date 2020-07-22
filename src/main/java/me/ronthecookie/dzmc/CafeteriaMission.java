package me.ronthecookie.dzmc;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CafeteriaMission implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();
	private final String RAW_PORK_NAME = ChatColor.translateAlternateColorCodes('&', "&eCafeteria Pork");
	private final int WORTH_PER = 5;

	private HashMap<UUID, Long> cooldownExpiries = new HashMap<>();

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getHand() != EquipmentSlot.HAND || e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		Block b = e.getClickedBlock();
		Player p = e.getPlayer();
		ItemStack clickedWith = p.getInventory().getItemInMainHand();
		if (b.getType() == Material.PINK_SHULKER_BOX) {
			e.setCancelled(true);
			if (cooldownExpiries.containsKey(p.getUniqueId())
					&& cooldownExpiries.get(p.getUniqueId()) > System.currentTimeMillis()) {
				p.sendActionBar(ChatColor.RED + "Out of Stock! Cooking more...");
				return;
			}
			ItemStack is = new ItemStack(Material.BEEF);
			ItemMeta ism = is.getItemMeta();
			ism.setDisplayName(RAW_PORK_NAME);
			is.setItemMeta(ism);
			p.getInventory().addItem(is);
			p.sendActionBar(ChatColor.YELLOW + "Deliver the pork to the smoker.");
			cooldownExpiries.put(p.getUniqueId(), System.currentTimeMillis() + 1000 * 5);
		} else if (clickedWith.getItemMeta() != null && clickedWith.getItemMeta().hasDisplayName()
				&& clickedWith.getItemMeta().getDisplayName().equals(RAW_PORK_NAME) && b.getType() == Material.SMOKER) {
			p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			int $$ = WORTH_PER * clickedWith.getAmount();
			dz.getEco().depositPlayer(p, $$);
			p.sendActionBar(ChatColor.GREEN + String.format("+$%d - Delivered", $$));
			e.setCancelled(true);
		}

	}
}