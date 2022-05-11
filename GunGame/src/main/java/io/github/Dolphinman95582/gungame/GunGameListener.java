package io.github.Dolphinman95582.gungame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GunGameListener implements Listener{
	
	private List<Player> players = new ArrayList<Player>();
	//private String hello = "";
	
	//Constructor
		public GunGameListener(GunGame plugin){
			this.players.clear();
			//this.players.addAll(plugin.getPlayers());
			//this.hello = plugin.getHello();
		}
		
		@EventHandler
		public void onPlayerDamage(EntityDamageByEntityEvent event) {
			//List<Player> players = plugin.getPlayers();
			//Player damager = (Player) event.getDamager();
			Entity entity = event.getEntity();
			/*for(int i = 0; i < players.size(); i++) {
				if (entity instanceof Zombie && damager.equals(players.get(i))) {
					damager.sendMessage(ChatColor.DARK_PURPLE + "you hit a zombie");
				}
			}*/
			/*if (entity instanceof Zombie) {
				damager.sendMessage(players.get(0).getDisplayName());
			}
			*/
			//damager.sendMessage(players.get(0).getDisplayName() + " this shit aint working fam");
			if (entity instanceof Player) {
				entity.sendMessage(Integer.toString(players.size()));
				//entity.sendMessage(hello);
			}
		}
}