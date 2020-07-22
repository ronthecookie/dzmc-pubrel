package me.ronthecookie.dzmc;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FriendlyFire implements Listener {
	// private Dangerzone dz = Dangerzone.getInstance();

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		// meele
		if (e.getEntity().getType() != EntityType.PLAYER)
			return;
		Player dmger = null;
		if (e.getDamager().getType() == EntityType.PLAYER)
			dmger = (Player) e.getDamager();
		else if (e.getDamager().getType() == EntityType.SNOWBALL)
			dmger = (Player) ((Snowball) e.getDamager()).getShooter();
		else
			return;
		Player victim = (Player) e.getEntity();
		for (FFGroup grp : FFGroup.values()) {
			if (dmger.hasPermission(grp.node) && victim.hasPermission(grp.node)) {
				e.setCancelled(true);
			}
		}
	}

}

enum FFGroup {
	SCPF("dangerzone.friendly.scpf"), CI("dangerzone.friendly.ci");

	public String node;

	FFGroup(String node) {
		this.node = node;
	}
}