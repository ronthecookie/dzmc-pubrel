package me.ronthecookie.dzmc;

import com.earth2me.essentials.commands.WarpNotFoundException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.ess3.api.InvalidWorldException;

@CommandAlias("casino")
public class CasinoCommand extends BaseCommand {
	private Dangerzone dz = Dangerzone.getInstance();

	@Default
	public void onDefault(CommandSender sender) throws WarpNotFoundException, InvalidWorldException {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			dz.getEco().withdrawPlayer(p, 100);
			p.sendMessage(ChatColor.RED + "-$100 Casino entrance fee");
			p.teleport(dz.getEss().getWarps().getWarp("casino"));
		} else {
			sender.sendMessage("player only");

		}
	}
}
