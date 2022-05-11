package io.github.Dolphinman95582.gungame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.EquipmentSlot;
//import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Kits {
	int score = 0;
	Player player = null;
	int index = 0;
	
	List<Color> colors = new ArrayList<Color>();
	
	public Kits(int score, Player player, int index) {
		this.score = score;
		this.player = player;
		this.index = index;
		
		colors.clear();
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.LIME);
		colors.add(Color.YELLOW);
		colors.add(Color.AQUA);
		colors.add(Color.WHITE);
		colors.add(Color.PURPLE);
		colors.add(Color.GRAY);
	}
	
	public void changeInv() {
		player.getInventory().clear();
		
		for (PotionEffect effect : player.getActivePotionEffects())
	        player.removePotionEffect(effect.getType());

		List<Entity> entities = player.getWorld().getEntities();
		for (Entity entity : entities){
		    if (entity instanceof Wolf){
		        if (((Tameable) entity).isTamed()){
		            if (((Tameable) entity).getOwner().getName().equals(player.getName())){
		            	entity.remove();
		            }
		        }
		    }
		}
		
		//armor
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta metaH = (LeatherArmorMeta) helmet.getItemMeta();
		metaH.setColor(colors.get(index));
		helmet.setItemMeta(metaH);
		player.getInventory().setHelmet(helmet);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta metaC = (LeatherArmorMeta) chestplate.getItemMeta();
		metaC.setColor(colors.get(index));
		chestplate.setItemMeta(metaC);
		player.getInventory().setChestplate(chestplate);
		
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta metaL = (LeatherArmorMeta) leggings.getItemMeta();
		metaL.setColor(colors.get(index));
		leggings.setItemMeta(metaL);
		player.getInventory().setLeggings(leggings);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta metaB = (LeatherArmorMeta) boots.getItemMeta();
		metaB.setColor(colors.get(index));
		boots.setItemMeta(metaB);
		player.getInventory().setBoots(boots);
		
		//items
		switch(score) {
		case 0:
			ItemStack dSword = new ItemStack(Material.DIAMOND_SWORD);
			player.getInventory().addItem(dSword);
			break;
		case 1:
			ItemStack crossbow1 = new ItemStack(Material.CROSSBOW);
			player.getInventory().addItem(crossbow1);
			ItemStack crossbow2 = new ItemStack(Material.CROSSBOW);
			player.getInventory().addItem(crossbow2);
			ItemStack slowArrow = new ItemStack(Material.TIPPED_ARROW, 64);
			PotionMeta slowMeta = (PotionMeta) slowArrow.getItemMeta();
			slowMeta.setBasePotionData(new PotionData(PotionType.SLOWNESS));
			slowArrow.setItemMeta(slowMeta);
			player.getInventory().addItem(slowArrow);
			break;
		case 2:
			ItemStack trident = new ItemStack(Material.TRIDENT);
			trident.addEnchantment(Enchantment.RIPTIDE, 2);
			player.getInventory().addItem(trident);
			ItemStack water = new ItemStack(Material.WATER_BUCKET);
			player.getInventory().addItem(water);
			player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 99999, 0));
			break;
		case 3:
			ItemStack spider = new ItemStack(Material.SPIDER_EYE);
			ItemMeta spiderMeta = spider.getItemMeta();
			AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
			spiderMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
			spider.setItemMeta(spiderMeta);
			player.getInventory().addItem(spider);
			ItemStack cobweb = new ItemStack(Material.COBWEB, 8);
			player.getInventory().addItem(cobweb);
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
			break;
		case 4:
			ItemStack bow = new ItemStack(Material.BOW);
			player.getInventory().addItem(bow);
			ItemStack arrow = new ItemStack(Material.ARROW, 1);
			player.getInventory().addItem(arrow);
			break;
		case 5:
			ItemStack shield = new ItemStack(Material.SHIELD);
			ItemMeta shieldMeta = shield.getItemMeta();
			AttributeModifier shieldMod = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
			shieldMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, shieldMod);
			shieldMeta.setDisplayName("bonk");
			shield.setItemMeta(shieldMeta);
			player.getInventory().addItem(shield);
			break;
		case 6:
			ItemStack poppy = new ItemStack(Material.POPPY);
			ItemMeta poppyMeta = poppy.getItemMeta();
			AttributeModifier poppyModa = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1000, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
			poppyMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, poppyModa);
			AttributeModifier poppyModb = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
			poppyMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, poppyModb);
			poppy.setItemMeta(poppyMeta);
			player.getInventory().addItem(poppy);
			break;
		case 7:
			ItemStack wolf = new ItemStack(Material.WOLF_SPAWN_EGG, 8);
			player.getInventory().addItem(wolf);
			//ItemStack bone = new ItemStack(Material.BONE, 64);
			//player.getInventory().addItem(bone);
			break;
		case 8:
			ItemStack rod = new ItemStack(Material.FISHING_ROD);
			player.getInventory().setItemInOffHand(rod);
			ItemStack salmon = new ItemStack(Material.SALMON);
			ItemMeta salmonMeta = salmon.getItemMeta();
			AttributeModifier salmonMod = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
			salmonMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, salmonMod);
			salmon.setItemMeta(salmonMeta);
			salmon.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
			player.getInventory().addItem(salmon);
			break;
		case 9:
			ItemStack nAxe = new ItemStack(Material.NETHERITE_AXE);
			player.getInventory().addItem(nAxe);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 49));
			break;
		case 10:
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 0));
			break;
		case 11:
			ItemStack gun = new ItemStack(Material.NETHERITE_HOE);
			player.getInventory().addItem(gun);
			break;
		case 12:
			ItemStack wSword = new ItemStack(Material.WOODEN_SWORD);
			wSword.addEnchantment(Enchantment.KNOCKBACK, 2);
			player.getInventory().addItem(wSword);
			ItemStack door = new ItemStack(Material.OAK_DOOR, 8);
			player.getInventory().addItem(door);
			break;
		case 13:
			ItemStack blaze = new ItemStack(Material.BLAZE_ROD);
			ItemMeta blazeMeta = blaze.getItemMeta();
			AttributeModifier blazeMod = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
			blazeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, blazeMod);
			blaze.setItemMeta(blazeMeta);
			blaze.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			player.getInventory().addItem(blaze);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
			break;
		case 14:
			ItemStack nSword = new ItemStack(Material.NETHERITE_SWORD);
			player.getInventory().addItem(nSword);
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 0));
			ItemStack crown = new ItemStack(Material.GOLDEN_HELMET);
			player.getInventory().setHelmet(crown);
			break;
		}
	}
}
