package me.ronthecookie.dzmc;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class AlertTask extends BukkitRunnable {
	private static final String[] messages = { "&bWant to be a staff? Join our discord server with &e/discord!",
			"&bYou can earn money in the kitchen and by becoming a janitor!", "&bSupport the server with &e/vote",
			// "&bWant to reduce time on grinding for resources? &e/buy!", much p2w
			"&bButterfly clicking is not permitted!",
			"&bSell Class D clothes to the janitor and earn some quick money!",
			"&bYou can buy illegal stuff at the back of the spawn. Be careful though, don't let the guards see you!",
			"&bYour support would mean a lot to us, &e/buy &bnow and support the server.",
			"&bHacking of any kind is &cNOT ALLOWED!", "&bThere are multiple shops outside to buy CI gear from.",
			// "&bThis is still a &6&lBETA&r&b test so if you find a bug please report it to
			// us on our &e/discord&b!."

	};

	public void run() {
		Random rng = new Random();
		int choice = rng.nextInt(messages.length);
		String message = messages[choice];
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6Tip&7]&f " + message));
	}
}
