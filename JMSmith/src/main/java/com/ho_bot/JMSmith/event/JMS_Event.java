package com.ho_bot.JMSmith.event;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.ho_bot.JMSmith.inv.SmithInv;
import com.ho_bot.JMSmith.main.JMSmith;
import com.ho_bot.JMSmith.util.VarUtil;

public class JMS_Event implements Listener {
	
	private SmithInv jmsI = new SmithInv();
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent event) {
		jmsI.EventSmith(event);
	}
	
	@EventHandler
	public void PlayerArmorChange(PlayerArmorChangeEvent event) {
		Player player = event.getPlayer();
		ItemStack[] itemlist = player.getInventory().getArmorContents();
		NamespacedKey key = NamespacedKey.fromString("upgrade", JMSmith.inst);
		double health = 0;
		for(ItemStack item : itemlist) {
			if(item==null) continue;
			if(item.getItemMeta().getPersistentDataContainer().has(key)) {
				int level = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
				health += VarUtil.ab_armorMap.get(level).armor_health;
			}
		}
		if(health > 0) {
			double max_h = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_h+health);
		}
		else {
			if(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() > 20) {
				player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
			}
		}
	}
	
	@EventHandler
	public void Damage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand() == null) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			NamespacedKey key = NamespacedKey.fromString("upgrade", JMSmith.inst);
			double damage = 0;
			if(item.getItemMeta().getPersistentDataContainer().has(key)) {
				int level = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
				damage += VarUtil.ab_weaponMap.get(level).weapon_damage;
			}
			if(damage > 0) {
				event.setDamage(event.getDamage()+damage);
			}
		}
	}

}
