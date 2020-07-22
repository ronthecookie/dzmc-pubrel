package me.ronthecookie.dzmc;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.WarpNotFoundException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.ess3.api.InvalidWorldException;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;

public class EtcListener implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();

	private void ciUpdate(Player p) throws InterruptedException, ExecutionException {
		LuckPerms lp = dz.getLp();
		User lpu = lp.getUserManager().getUser(p.getUniqueId());

		lpu.getNodes().forEach(node -> {
			if (!node.getKey().equalsIgnoreCase("group.ci"))
				return;
			lpu.data().remove(node);
			lpu.data().add(Node.builder("group.default").build());
		});

		lp.getUserManager().saveUser(lpu).get();
	}

	@EventHandler
	public void onStartGame(PlayerInteractEvent e) throws WarpNotFoundException, InvalidWorldException {
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null)
			return;
		if (e.getClickedBlock().getLocation().equals(dz.getConfig().get("start-button"))) {
			Essentials ess = dz.getEss();

			Player p = e.getPlayer();

			p.teleport(ess.getWarps().getWarp("cd"));
		}

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
			throws WarpNotFoundException, InvalidWorldException, InterruptedException, ExecutionException {
		Player p = e.getPlayer();
		if (!e.getPlayer().hasPlayedBefore()) {
			p.teleport(p.getLocation().getWorld().getSpawnLocation());
			return;
		}

		ciUpdate(p);

		LuckPerms lp = dz.getLp();
		Essentials ess = dz.getEss();

		User lpu = lp.getUserManager().getUser(p.getUniqueId());
		p.teleport(ess.getWarps().getWarp(lpu.getPrimaryGroup()));
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent e)
			throws WarpNotFoundException, InvalidWorldException, InterruptedException, ExecutionException {
		Player p = e.getPlayer();
		ciUpdate(p);

		LuckPerms lp = dz.getLp();
		Essentials ess = dz.getEss();

		User lpu = lp.getUserManager().getUser(p.getUniqueId());
		try {
			e.setRespawnLocation(ess.getWarps().getWarp(lpu.getPrimaryGroup()));
		} catch (WarpNotFoundException _wnfe) {
			dz.getServer().broadcast(ChatColor.translateAlternateColorCodes('&',
					"&c[dzmc]&b Missing warp " + lpu.getPrimaryGroup() + " so I cannot spawn " + p.getName() + "."),
					"dangerzone.missing-warp");
		}
		List<String> kitGroups = dz.getConfig().getStringList("respawn-kit-groups");
		// TODO: this is a hack!! fix
		if (kitGroups.contains(lpu.getPrimaryGroup().toLowerCase())) {
			new BukkitRunnable() {

				@Override
				public void run() {
					dz.getServer().dispatchCommand(dz.getServer().getConsoleSender(),
							"kit " + lpu.getPrimaryGroup() + " " + p.getName());
				}
			}.runTaskLater(dz, 2);
		}
		List<String> secondarykitGroups = dz.getConfig().getStringList("respawn-kit-secondary-groups");
		for (String grp : secondarykitGroups) {
			if (lpu.getDistinctNodes().stream().anyMatch(node -> node.getKey().equalsIgnoreCase("group." + grp))) {
				new BukkitRunnable() {

					@Override
					public void run() {
						dz.getServer().dispatchCommand(dz.getServer().getConsoleSender(),
								"kit " + grp + " " + p.getName());
					}
				}.runTaskLater(dz, 2);
			}
		}

	}

	// @EventHandler(priority = EventPriority.HIGHEST)
	// public void joshNo(AsyncPlayerPreLoginEvent e) {
	// if (e.getPlayerProfile().getName().equalsIgnoreCase("joshplays05")) {
	// e.setKickMessage("Josh is fat and may not enter.");
	// e.setLoginResult(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
	// } else if (e.getPlayerProfile().getName().equalsIgnoreCase("ronthecookie")) {
	// e.setLoginResult(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.ALLOWED);
	// }
	// }
}