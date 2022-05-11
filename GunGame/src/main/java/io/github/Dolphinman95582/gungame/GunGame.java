package io.github.Dolphinman95582.gungame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
//import org.bukkit.plugin.PluginManager;
//import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public final class GunGame extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		getLogger().info("GunGame is working!");
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	List<String> igns = new ArrayList<String>();
	List<Location> locs = new ArrayList<Location>();
	List<Player> players = new ArrayList<Player>();
	List<Integer> scores = new ArrayList<Integer>();
	List<PotionType> arrows = new ArrayList<PotionType>();
	List<Location> plocs = new ArrayList<Location>();
	List<Integer> difference = new ArrayList<Integer>();
	
	public static GunGame plugin;
	public static FileConfiguration config;
    public final Table<Player, String, Long> cooldowns = HashBasedTable.create();
	
	boolean currentGame = false;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		World world = Bukkit.getServer().getWorld("world");
		
		//all spawning locations
		
		int sX =  224;
		int sY = 0;
		int sZ = -113;
		int eX = 175;
		int eY = 20;
		int eZ = -64;
		
		Location lobby = new Location(world, eX, eY, eZ);
		
		arrows.add(PotionType.SLOWNESS);
		arrows.add(PotionType.INSTANT_DAMAGE);
		arrows.add(PotionType.POISON);
		arrows.add(PotionType.WEAKNESS);
		arrows.add(PotionType.SLOW_FALLING);

		if (sender instanceof  Player) {
    		String lowerCmd = cmd.getName().toLowerCase();
    		//Inventory inv = player.getInventory();
    		switch (lowerCmd){
    			case "join":
    				if(!currentGame) {
	    				boolean newName = true;
	    				for(int i = 0; i < igns.size(); i++) {
	    					if (player.getDisplayName().equals(igns.get(i))) {
	    						newName = false;
	    					}
	    				}
	    				if(igns.size() < 8 && newName) {
	    					igns.add(player.getDisplayName());
	    					player.sendMessage(ChatColor.GREEN + "You have been added to the queue");
	    				}
	    				else {
	    					player.sendMessage(ChatColor.DARK_RED + "You can not be added to the queue");
	    				}
	    				break;
    				}
    				else if (currentGame)
    					player.sendMessage(ChatColor.DARK_RED + "there is a game already happening");
    				break;
    			case "begin":
    				if(!currentGame) {
    					locs.clear();
    					for(int x = sX; x > eX; x--) {
        					for(int y = sY; y < eY; y++) {
        						for(int z = sZ; z < eZ; z++) {
        							Location search = new Location(world, x, y, z);
        							if(search.getBlock().getType() == Material.RED_CONCRETE) {
        								Location found = new Location(world, x, y + 1, z);
        								locs.add(found);
        							}
        						}
        					}
        				}
	    				Collections.shuffle(locs);
	    				for(int i = 0; i < locs.size(); i++) {
	    					getLogger().info(Integer.toString(locs.get(i).getBlockY()));
	    				}
	    				for(int i = 0; i < igns.size(); i++) {
	    					Player tplayer = getServer().getPlayer(igns.get(i));
	    					tplayer.teleport(locs.get(i));
	    					tplayer.sendMessage(ChatColor.DARK_GREEN + "You have been teleported to the arena");
	    					players.add(tplayer);
	    					scores.add(0);
	    				}
	    				for(int i = 0; i < igns.size(); i++) {
	    					Kits kit = new Kits(scores.get(i), players.get(i), i);
	    					kit.changeInv();
	    				}
						player.sendMessage(ChatColor.AQUA + "Admin: " + ChatColor.GRAY + "You have teleported all queued players");
	    				igns.clear();
	    				currentGame = true;
	    				break;
    				}
    				else if (currentGame)
    					player.sendMessage(ChatColor.DARK_RED + "there is a game already happening");
    				break;
    					
    			case "leave":
    				if (!currentGame) {
	    				boolean appears = false;
	    				for(int i = 0; i < igns.size(); i++) {
	    					if(player.getDisplayName().equals(igns.get(i))) {
	    						igns.remove(i);
	    						player.sendMessage(ChatColor.RED + "You have left the queue");
	    						appears = true;
	    						break;
	    					}
	    				}
	    				if(appears == false) {
	        				player.sendMessage(ChatColor.DARK_RED + "You can not leave a queue that you are not in");
	    				}
    				}
    				else if (currentGame) {
    					for(int i = 0; i < players.size(); i++) {
    						if(player.getDisplayName().equals(players.get(i).getDisplayName())) {
	    						players.remove(i);
	    						scores.remove(i);
	    						player.teleport(lobby);
	    						player.sendMessage(ChatColor.RED + "You have left the game");
	    						for (PotionEffect effect : player.getActivePotionEffects())
	    					        player.removePotionEffect(effect.getType());
	    						player.getInventory().clear();
	    					}
        				}
    				}
    				break;
    			case "end":
    				for(int i = 0; i < players.size(); i++) {
    					players.get(i).teleport(lobby);
    					players.get(i).getInventory().clear();
    					for (PotionEffect effect : players.get(i).getActivePotionEffects())
    				        players.get(i).removePotionEffect(effect.getType());
    				}
    				players.clear();
    				scores.clear();
    				currentGame = false;
    				player.sendMessage(ChatColor.AQUA + "Admin: " + ChatColor.GRAY + "You have ended the game");
    				break;
    		}
    		
		}
		return true;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		try {
			Player killer = (Player) event.getEntity().getKiller();
			Entity entity = event.getEntity();
			for(int i = 0; i < players.size(); i++) {
				if (entity instanceof Zombie && killer.equals(players.get(i))) {
					scores.set(i, scores.get(i) + 1);
					Kits kit = new Kits(scores.get(i), players.get(i), i);
					kit.changeInv();
					killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 3));
					killer.sendMessage(ChatColor.DARK_PURPLE + "you have killed " + scores.get(i) + " zombies");
				}
			}
		}
		catch(Exception e){
				
		}
	}
	
	@EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        final Player p = event.getPlayer();
        final Block b = event.getBlock();
        final ItemStack blockItem = new ItemStack(b.getType());
        if(b.getType() == Material.COBWEB || b.getType() == Material.OAK_DOOR){
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
            {
                @Override
                public void run()
                {
                    b.setType(Material.AIR);
                    p.sendMessage("removed item");
 
                }
            }, 3*20L);
        }
        if(b.getType() == Material.BEDROCK) {
        	for(int i = 0; i < players.size(); i++) {
    			if(p == players.get(i)) {
    				p.getInventory().setItemInMainHand(blockItem);
    				b.setType(Material.AIR);
    			}
    		}
        }
    }
	
	@EventHandler
	public void PlayerBucketEvent(PlayerBucketEmptyEvent event) {
		final Player p = event.getPlayer();
        final Block b = event.getBlock();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
        {
            @Override
            public void run()
            {
                b.setType(Material.AIR);
                p.sendMessage("removed item");
 
                }
            }, 3*20L);
	}
	
	@EventHandler
	public void OnPlayerShoot(EntityShootBowEvent event) {
		try {
			Player player = (Player) event.getEntity();
			for(int i = 0; i < players.size(); i++) {
				if(scores.get(i) == 4 && players.get(i) == player) {
					double random = Math.random() * 5;
					ItemStack arrow = new ItemStack(Material.TIPPED_ARROW, 1);
					PotionMeta meta = (PotionMeta) arrow.getItemMeta();
					meta.setBasePotionData(new PotionData(arrows.get((int) random)));
					arrow.setItemMeta(meta);
					player.getInventory().addItem(arrow);
				}
			}
		}
		catch(Exception e){
			
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnArrowPickup(PlayerPickupArrowEvent event) {
		event.getItem().remove();
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
	    try {
	    	final Player player = e.getPlayer();
	    	final ItemStack item = new ItemStack(e.getItem());
	    	final ItemStack bedrock = new ItemStack(Material.BEDROCK);
		    if ((item.getType() == Material.NETHERITE_HOE) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
		    	player.launchProjectile(Fireball.class, player.getLocation().getDirection());
		    	player.getInventory().setItemInMainHand(bedrock);
		    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		        {
		            @Override
		            public void run()
		            {
		            	for(int i = 0; i < players.size(); i++) {
		            		if(scores.get(i) == 11 && players.get(i) == player) {
		    	            	player.getInventory().setItemInMainHand(item);	
		            		}
		            	}
		            }
		        }, 1*20L);
		    }
		    if (item.getType() == Material.WOLF_SPAWN_EGG && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
		    	Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
		    	wolf.setOwner(player);
		    	player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
		    	e.setCancelled(true);
		    }
	    }
	    catch(Exception ex) {
	    	
	    }
	}
	
	@EventHandler
	public void onHungerDeplete(FoodLevelChangeEvent e) {
		e.setCancelled(true);
		e.getEntity().setFoodLevel(20);
		//turn keepinv on and natural regen off
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		try {
			event.getDrops().clear();
			Player killer = (Player) event.getEntity().getKiller();
			for(int i = 0; i < players.size(); i++) {
				if (killer.equals(players.get(i)) && !killer.equals(event.getEntity())) {
					scores.set(i, scores.get(i) + 1);
					Kits kit = new Kits(scores.get(i), players.get(i), i);
					kit.changeInv();
					killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 3));
					//killer.sendMessage("you killed a player");
					//make new method in kits.java for chat message about each kit
				}
			}
		}
		catch(Exception e){
			
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player victim = (Player) event.getPlayer();
		difference.clear();
		plocs.clear();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
        {
            @Override
            public void run()
            {
            	for(int i = 0; i < players.size(); i++) {
        			if (victim.equals(players.get(i))) {
        				Kits kit = new Kits(scores.get(i), victim, i);
        				kit.changeInv();
        			}
        		}
            }
        }, 1L);
		try {
			for(int i = 0; i < players.size(); i++) {
				if (victim.equals(players.get(i))) {
					
					for(int p = 0; p < players.size(); p++) {
						if(!players.get(p).equals(victim)) {
							plocs.add(players.get(p).getLocation());
						}
					}
					
					for(int rl = 0; rl < locs.size(); rl++) {
						int averageX = 0;
						int averageZ = 0;
						int rlX = (int) locs.get(rl).getX();
						int rlZ = (int) locs.get(rl).getZ();
						for(int pl = 0; pl < plocs.size(); pl++) {
							int plX = (int) plocs.get(pl).getX();
							int plZ = (int) plocs.get(pl).getZ();
							averageX += Math.abs(plX - rlX);
							averageZ += Math.abs(plZ - rlZ);
						}
						averageX = averageX / plocs.size();
						averageZ = averageZ / plocs.size();
						int d = (int) Math.sqrt(Math.pow(averageX, 2) + Math.pow(averageZ, 2));
						difference.add(d);
					}
					
					int lowest = 0;
					int index = 0;
					for(int f = 0; f < difference.size(); f++) {
						if(lowest < difference.get(f)) {
							lowest = difference.get(f);
							index = f;
						}
					}
					event.setRespawnLocation(locs.get(index));
				}
			}
		}
		catch(Exception e){
			event.setRespawnLocation(locs.get(0));
			getLogger().info("could not teleport correctly");
		}
	}
	
	@EventHandler
	public void OnEntityDamage(EntityDamageEvent event) {
		if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void OnPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
}
