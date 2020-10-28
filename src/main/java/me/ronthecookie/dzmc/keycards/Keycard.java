package me.ronthecookie.dzmc.keycards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum Keycard {
	/* SCPF */ CD(0, KeycardGroup.SCPF), L1(1, KeycardGroup.SCPF), L2(2, KeycardGroup.SCPF), L3(3, KeycardGroup.SCPF),
	/* SCPF */ L4(4, KeycardGroup.SCPF), /* CI */ CI(1, KeycardGroup.CI), CICMDER(2, KeycardGroup.CI),
	/* IGNORE */ OMNI(100, KeycardGroup.IGNORE);

	public final int level;
	public final String displayName;
	public final KeycardGroup group;

	Keycard(int level, KeycardGroup group) {
		this.level = level;
		this.group = group;
		this.displayName = this.name();
	}

	public static void giveToPlayer(Keycard kc, Player p) {
		if (kc.group != KeycardGroup.SCPF)
			throw new IllegalArgumentException("only SCPF keycards can be given");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
				"kit L" + kc.level + " " + p.getName());
	}

	public static Keycard itemOf(ItemStack item) {
		if (item == null || item.getLore() == null || item.getLore().isEmpty())
			return Keycard.CD;
		try {
			return Keycard.valueOf(Keycard.decodeColors(item.getLore().get(0)));
		} catch (IllegalArgumentException e) {
			return Keycard.CD;
		}
	}

	public static Keycard materialOf(Material material) {
		switch (material) {
			case BIRCH_BUTTON:
				return Keycard.L1;
			case DARK_OAK_BUTTON:
				return Keycard.L2;
			case JUNGLE_BUTTON:
				return Keycard.L3;
			case STONE_BUTTON:
				return Keycard.CICMDER;
			case SPRUCE_BUTTON:
				return Keycard.L4;
			case OAK_BUTTON:
				return Keycard.CI;
			case LEVER:
				return Keycard.OMNI;
			case ACACIA_BUTTON:
				return Keycard.CD;
			default:
				return null;
		}
	}

	public static Keycard scpfLevelOf(int level) {
		switch (level) {
			case 1:
				return Keycard.L1;
			case 2:
				return Keycard.L2;
			case 3:
				return Keycard.L3;
			case 4:
				return Keycard.L4;
			case 0:
				return Keycard.CD;
			default:
				return null;
		}
	}

	protected static String encodeColors(String str) {
		String newStr = "";
		for (char chr : str.toCharArray()) {
			newStr += ChatColor.COLOR_CHAR;
			newStr += chr;
		}
		return newStr;
	}

	protected static String decodeColors(String str) {
		return str.replaceAll("§", "");
	}
}