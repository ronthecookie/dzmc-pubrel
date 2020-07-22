package me.ronthecookie.dzmc;

import com.earth2me.essentials.commands.WarpNotFoundException;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.ess3.api.InvalidWorldException;

public class SCP914Listener implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		// TODO: check if its the birch button in scp 914 and not another one so less
		// lag and shit
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock().getType() != Material.BIRCH_BUTTON
				|| e.getHand() != EquipmentSlot.HAND)
			return;
		Player p = e.getPlayer();
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					if (p.getLocation().getBlock().getRelative(BlockFace.DOWN)
							.getType() != Material.DEAD_TUBE_CORAL_BLOCK)
						return;
					p.teleport(dz.getEss().getWarps().getWarp("SCP_914_OUT"));
					p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

				} catch (WarpNotFoundException | InvalidWorldException e) {
					e.printStackTrace();
				}
			}
		}.runTaskLater(dz, 20 * 5);
	}
}
