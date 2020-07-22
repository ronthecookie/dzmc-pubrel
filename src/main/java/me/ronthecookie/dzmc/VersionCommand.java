package me.ronthecookie.dzmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;

@CommandAlias("dangerzone|dz")
public class VersionCommand extends BaseCommand {
	// private Dangerzone dz = Dangerzone.getInstance();

	@Default
	public void onDefault(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "Dangerzone plugin v1-1.15.2 by ronthecookie. https://ronthecookie.me");
	}
}
