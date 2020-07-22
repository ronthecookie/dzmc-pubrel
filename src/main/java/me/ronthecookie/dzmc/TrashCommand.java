package me.ronthecookie.dzmc;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;

@CommandAlias("trash")
public class TrashCommand extends BaseCommand implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();
	public static final String INVENTORY_TITLE = ChatColor.DARK_RED + "Sell CD armor for $1/piece!";

	@Default
	public void onDefault(CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, INVENTORY_TITLE);
			p.openInventory(inv);
		} else {
			sender.sendMessage("player only");
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (!e.getView().getTitle().equals(INVENTORY_TITLE))
			return;
		Inventory inv = e.getInventory();
		Player p = (Player) e.getPlayer();
		List<Material> worth = Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
				Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS);
		int $$ = 0;
		for (ItemStack i : inv.getContents()) {
			if (i != null && worth.contains(i.getType()))
				$$++;
		}
		dz.getEco().depositPlayer(p, $$);
		p.sendMessage(ChatColor.GREEN + "Sold item(s) for $" + $$ + ".");
		inv.clear();
	}
}
