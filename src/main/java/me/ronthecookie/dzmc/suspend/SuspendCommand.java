package me.ronthecookie.dzmc.suspend;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.ronthecookie.dzmc.Dangerzone;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

@CommandAlias("suspend")
public class SuspendCommand extends BaseCommand implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();

	@Default
	@Syntax("<player> <time>")
	@CommandPermission("dangerzone.suspend")
	public void onDefault(CommandSender sender, String pName, String time) {
		try {
			Player p = dz.getServer().getPlayer(pName);
			if (p == null) {
				sender.sendMessage(ChatColor.RED + pName + " isn't online.");
				return;
			}
			if (p.hasPermission("group.suspended") || p.hasPermission("dangerzone.unsuspendable")) {
				sender.sendMessage(ChatColor.RED + "Already suspended... (or unsuspendable)");
				return;
			}
			Duration dur = Duration.parse(time);
			dz.getServer().broadcast(
					ChatColor.translateAlternateColorCodes('&',
							"&f" + p.getName() + "&c has been suspended! (&f" + sender.getName() + "&c)"),
					"dangerzone.suspendalert");
			// LuckPerms touches the network and stuff so better to do it async..
			LuckPerms lp = dz.getLp();
			new BukkitRunnable() {

				@Override
				public void run() {
					User lpu = lp.getUserManager().getUser(p.getUniqueId());
					lpu.getNodes().forEach(node -> {
						if (!node.getKey().startsWith("group."))
							return;
						lpu.data().remove(node);
						lpu.data().add(Node.builder("suspended." + node.getKey()).build());
					});
					lpu.data()
							.add(Node.builder("group.suspended").expiry(dur.toMillis(), TimeUnit.MILLISECONDS).build());
					lpu.data().add(Node.builder("group.default").build());
					try {
						lp.getUserManager().saveUser(lpu).get();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.runTaskAsynchronously(dz);
		} catch (DateTimeParseException e) {
			sender.sendMessage(ChatColor.RED + "Invalid time!");
		}
	}
}
