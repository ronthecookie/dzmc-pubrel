package me.ronthecookie.dzmc.suspend;

import com.destroystokyo.paper.Title;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.ronthecookie.dzmc.Dangerzone;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class UnsuspendTask extends BukkitRunnable {
	private Dangerzone dz = Dangerzone.getInstance();

	public void run() {
		LuckPerms lp = dz.getLp();
		// We just iterate through all the players and find any players that shouldn't
		// be suspended anymore.
		for (Player p : dz.getServer().getOnlinePlayers()) {
			User lpu = lp.getUserManager().getUser(p.getUniqueId());
			if (p.isOp() || p.hasPermission("group.suspended"))
				continue;
			boolean wasSuspended = false;
			for (Node n : lpu.getNodes()) {
				if (n.getKey().startsWith("suspended.group.")) {
					lpu.data().add(Node.builder(n.getKey().replace("suspended.", "")).build());
					lpu.data().remove(n);
					wasSuspended = true;
				}

			}
			if (wasSuspended) {
				for (Node n : lpu.getNodes()) {
					if (n.getKey().equalsIgnoreCase("group.default") || n.getKey().equalsIgnoreCase("group.ci")) {
						// remove cd/ci
						lpu.data().remove(n);
					}
				}
				p.damage(10000); // kill em!
				p.sendTitle((new Title.Builder()).title("").subtitle(ChatColor.GREEN + "Suspension is over!").build());
				try {
					lp.getUserManager().saveUser(lpu).get();
				} catch (Exception e) {
					p.sendMessage("Unable to unsuspend!!! Report to O5..");
					e.printStackTrace();
				}
			}
		}
	}
}
