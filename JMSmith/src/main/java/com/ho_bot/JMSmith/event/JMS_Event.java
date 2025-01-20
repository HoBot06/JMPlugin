package com.ho_bot.JMSmith.event;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.ho_bot.JMSmith.inv.SmithInv;

public class JMS_Event implements Listener {
	
	private SmithInv jmsI = new SmithInv();
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent event) {
		jmsI.EventSmith(event);
	}
	
	@EventHandler
	public void PlayerArmorChange(PlayerArmorChangeEvent event) {
		event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
	}
	
	@EventHandler
	public void Damage(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand() == null) return;
			ItemStack item = player.getInventory().getItemInMainHand();
		}
	}

}
