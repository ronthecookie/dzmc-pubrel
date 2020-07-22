package me.ronthecookie.dzmc;

import com.earth2me.essentials.Essentials;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.ronthecookie.dzmc.suspend.SuspendCommand;
import me.ronthecookie.dzmc.suspend.UnsuspendTask;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;

public final class Dangerzone extends JavaPlugin implements Listener {

	@Getter
	private static Dangerzone instance;

	@Getter
	private Economy eco;

	@Getter
	private LuckPerms lp;

	@Getter
	private Essentials ess;

	@Override
	public void onEnable() {
		Dangerzone.instance = this;
		// if (!this.killswitch()) {
		// this.getLogger().log(Level.SEVERE, "fuck you striker! drm ftw");
		// return;
		// }
		this.eco = this.getDepend(Economy.class);
		this.lp = this.getDepend(LuckPerms.class);
		// Essentials is weird
		this.ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

		if (this.ess == null || this.lp == null || this.eco == null)
			return;

		PaperCommandManager manager = new PaperCommandManager(this);
		ISDCuffs cuffs = new ISDCuffs();
		cuffs.runTaskTimer(this, 0, 1);

		manager.registerCommand(new RadioCommand());
		manager.registerCommand(new TrashCommand());
		manager.registerCommand(new SuspendCommand());
		manager.registerCommand(new VersionCommand());
		manager.registerCommand(new PunishCommand());
		manager.registerCommand(new CasinoCommand());
		manager.registerCommand(new MGMinecartCommand());

		new AlertTask().runTaskTimer(this, 0, 20 * 60 * 2); // 5 min
		new UnsuspendTask().runTaskTimer(this, 0, 20 * 60); // quite often = good

		// FileConfiguration config = this.getConfig();
		this.saveDefaultConfig();

		registerEvents(this);
		registerEvents(cuffs);
		registerEvents(new MoneyblockListener());
		registerEvents(new EtcListener());
		registerEvents(new CIJoinListener());
		registerEvents(new SCP1437Listener());
		registerEvents(new TrashCommand());
		registerEvents(new FriendlyFire());
		registerEvents(new SCP914Listener());
		registerEvents(new MedkitListener());
		registerEvents(new MGMinecartCommand()); // TODO dont make new instance
		registerEvents(new DoubleJumpListener());
		registerEvents(new CafeteriaMission());
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	public void registerEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

	public <T> T getDepend(Class<T> service) {
		RegisteredServiceProvider<T> rsp = Bukkit.getServicesManager().getRegistration(service);
		if (rsp == null || rsp.getProvider() == null) {
			this.getLogger().severe("Cannot load service: " + service.getName());
			Bukkit.getPluginManager().disablePlugin(this);
			return null;
		} else
			return rsp.getProvider();
	}

	// private boolean killswitch() {
	// try {
	// URL url = new URL("http://i.ronthecookie.me/dzmc_killswitch");
	// URLConnection conn = url.openConnection();
	// BufferedReader in;
	// in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	// String inputLine;

	// while ((inputLine = in.readLine()) != null)
	// System.out.println(inputLine);
	// in.close();
	// return true;
	// } catch (IOException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
}
