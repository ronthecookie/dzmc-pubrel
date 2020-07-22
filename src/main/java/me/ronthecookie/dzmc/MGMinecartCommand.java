package me.ronthecookie.dzmc;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;

@CommandAlias("mgmc")
@CommandPermission("dangerzone.mgmc")
public class MGMinecartCommand extends BaseCommand implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();
	public static final String INVENTORY_TITLE = ChatColor.DARK_RED + "Sell CD armor for $1/piece!";
	public final String MGCART_META = "dangerzone:mg-cart";

	HashMap<UUID, ItemStack[]> oldInvs = new HashMap<>();

	@EventHandler
	public void onMount(EntityMountEvent e) {
		Entity eplayer = e.getEntity();
		Entity ecart = e.getMount();
		if (eplayer.getType() != EntityType.PLAYER || ecart.getType() != EntityType.MINECART)
			return;
		Player p = (Player) eplayer;
		RideableMinecart cart = (RideableMinecart) ecart;
		if (!cart.hasMetadata(MGCART_META))
			return;
		oldInvs.put(p.getUniqueId(), p.getInventory().getContents());
		p.getInventory().clear();
		dz.getServer().dispatchCommand(dz.getServer().getConsoleSender(), "crackshot give " + p.getName() + " m123");
	}

	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		Entity eplayer = e.getEntity();
		Entity ecart = e.getDismounted();
		if (eplayer.getType() != EntityType.PLAYER || ecart.getType() != EntityType.MINECART)
			return;
		Player p = (Player) eplayer;
		RideableMinecart cart = (RideableMinecart) ecart;
		if (!cart.hasMetadata(MGCART_META))
			return;
		if (!oldInvs.containsKey(p.getUniqueId()))
			return;
		p.getInventory().clear();
		for (ItemStack is : oldInvs.get(p.getUniqueId())) {
			if (is != null)
				p.getInventory().addItem(is); // might be able to just call ...oldinvs.get instead of loop
		}
		oldInvs.remove(p.getUniqueId());

	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (!oldInvs.containsKey(p.getUniqueId()))
			return;
		e.setCancelled(true);
	}

	@Default
	@CatchUnknown
	public void onDefault(CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.getVehicle() == null || p.getVehicle().getType() != EntityType.MINECART) {
				sender.sendMessage("ur not in minecart");
			} else {
				RideableMinecart cart = (RideableMinecart) p.getVehicle();
				cart.setMetadata(MGCART_META, new FixedMetadataValue(dz, true));
			}
		} else {
			sender.sendMessage("player only");
		}
	}

}
