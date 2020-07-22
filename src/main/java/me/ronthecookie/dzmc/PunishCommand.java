package me.ronthecookie.dzmc;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;

@CommandAlias("punish|pun")
public class PunishCommand extends BaseCommand implements Listener {
	// private Dangerzone dz = Dangerzone.getInstance();

	@Default
	@Syntax("<player>")
	@CommandPermission("dangerzone.punish")
	public void onDefault(CommandSender sender, String name) {
		if (Bukkit.getPlayer(name) == null) {
			sender.sendMessage(ChatColor.RED + "Player not found.");
			return;
		}
		sender.sendMessage(ChatColor.GREEN + "Punishments:");
		for (Punishment pun : Punishment.values()) {
			sender.sendMessage(new ComponentBuilder().append(pun.display).color(ChatColor.RED)
					.event(new ClickEvent(Action.RUN_COMMAND, String.format(pun.cmd, name.toLowerCase(), pun.display)))
					.create());
		}
	}
}

enum Punishment {
	CHAT_OFFENCE("Major Chat Offence. (1 hour ban.)", "/ban %s 1h %s"),
	MALICOUS_HACKS("Malicious Hacks (30 Day ban)", "/ban %s 30d %s"),
	NONMALICOUS_HACKS("Non-Malicious Hacks (14 Day ban)", "/ban %s 14d %s"),
	BAN_EVASION("Ban Evasion (Permanent Ban)", "/ban %s %s"),
	SWEARING("Swearing  Bad Language (1 Hour mute)", "/mute %s 1h %s"),
	SPAM("Spam. (30 Minute mute)", "/mute %s 30m %s"),
	STAFF_DISS("Disrespecting Staff. (1 Hour mute)", "/mute %s 1h %s"),
	WARN("Kick. (Warning)", "/kick %s You have been kicked from the server. Let this be a warning -s");

	public String display;
	public String cmd;

	Punishment(String display, String cmd) {
		this.display = display;
		this.cmd = cmd;
	}
}