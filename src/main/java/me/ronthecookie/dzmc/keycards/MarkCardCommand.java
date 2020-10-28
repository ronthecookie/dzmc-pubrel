package me.ronthecookie.dzmc.keycards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import net.md_5.bungee.api.ChatColor;

@CommandAlias("markcard")
@CommandPermission("dangerzone.markcard")
public class MarkCardCommand extends BaseCommand {
	// private Dangerzone dz = Dangerzone.getInstance();

	@Default
	@CatchUnknown
	@Syntax("<keycard type | 'read' | 'remove'>")
	public void onDefault(CommandSender sender, String kcStr) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			ItemStack is = p.getInventory().getItemInMainHand();
			if (kcStr.equalsIgnoreCase("read")) {
				if (is.getLore() == null || is.getLore().size() < 1) {
					p.sendMessage(ChatColor.RED + "Not a keycard.");
				} else {
					p.sendMessage(ChatColor.GREEN + "it is: " + Keycard.decodeColors(is.getLore().get(0)));
				}
			} else if (kcStr.equalsIgnoreCase("remove")) {
				List<String> lores = is.getLore();
				if (lores == null) {
					p.sendMessage(ChatColor.RED + "Not a keycard.");
				} else {
					lores.set(0, "");
					is.setLore(lores);
					p.getInventory().setItemInMainHand(is);
				}
			} else {
				Keycard kc = Keycard.valueOf(kcStr.toUpperCase());
				ArrayList<String> newLore = new ArrayList<>();
				newLore.add(Keycard.encodeColors(kc.name()));
				is.setLore(newLore);
				p.getInventory().setItemInMainHand(is);
				p.sendMessage(ChatColor.GREEN + "updated item");
			}
		} else {
			sender.sendMessage("player only");
		}
	}

}
