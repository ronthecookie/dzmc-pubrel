package me.ronthecookie.dzmc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandManager;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Syntax;

@CommandAlias("radio|chaos|ia|isd|mtf|scd|sd|sru|staff|sc")
public class RadioCommand extends BaseCommand implements Listener {
	private Dangerzone dz = Dangerzone.getInstance();

	@CatchUnknown
	@Syntax("<message>")
	public void onDefault(CommandSender sender, String msg) {
		String label = CommandManager.getCurrentCommandOperationContext().getCommandLabel();
		if (label.equalsIgnoreCase("sc"))
			label = "staff";
		Radio rad = Radio.valueOf(label.toUpperCase());
		if (!sender.hasPermission(rad.perm)) {
			sender.sendMessage(ChatColor.RED + "No permission for that radio.");
			return;
		}
		dz.getServer().broadcast(
				ChatColor.translateAlternateColorCodes('&', rad.prefix + sender.getName() + ": ") + msg, rad.perm);
	}
}

enum Radio {
	RADIO("&2[Radio] &a", "scpf"), CHAOS("&8[Radio] &7"), IA("&3[IA] &b"), ISD("&5[ISD] &d"), MTF("&9[MTF] &b"),
	SCD("&6[ScD] &e"), SD("&8[SD] &7"), SRU("&4[SRU] &c"), STAFF("&4[Staff] &c", "dangerzone.staffchat");

	String prefix;
	String perm;

	Radio(String prefix, String perm) {
		this.prefix = prefix;
		this.perm = "dangerzone.radios." + perm;
	}

	Radio(String prefix) {
		this.prefix = prefix;
		this.perm = "dangerzone.radios." + this.name().toLowerCase();
	}
}
