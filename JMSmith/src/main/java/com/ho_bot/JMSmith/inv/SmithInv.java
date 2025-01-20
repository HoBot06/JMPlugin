package com.ho_bot.JMSmith.inv;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ho_bot.JMSmith.util.GuiUtil;

import net.kyori.adventure.text.Component;

public class SmithInv {
	
	private GuiUtil GU = new GuiUtil();
	
	public void OpenSmith(Player player) {
		Inventory inv = Bukkit.createInventory(null, 45, Component.text("[강화]"));
		
		List<Integer> list = Arrays.asList(13, 28, 29, 30, 31, 32, 33, 34, 40);
		for(int i = 0; i < 45; i++) {
			if(list.contains(i)) continue;
			GU.Stack(" ", Material.YELLOW_STAINED_GLASS_PANE, 1, null, i, inv);
		}
		
		player.openInventory(inv);
	}
	
	public void OpenSmith(Player player, ItemStack item) {
		Inventory inv = Bukkit.createInventory(null, 45, Component.text("[강화]"));
		
		List<Integer> list = Arrays.asList(13, 28, 29, 30, 31, 32, 33, 34, 40);
		for(int i = 0; i < 45; i++) {
			if(list.contains(i)) continue;
			GU.Stack(" ", Material.YELLOW_STAINED_GLASS_PANE, 1, null, i, inv);
		}
		
		GU.Stack(item, 13, inv);
		
		player.openInventory(inv);
	}
	
	public void EventSmith(InventoryClickEvent event) {
		if(event.getView().getTopInventory() == null) return;
		if(!event.getView().title().equals(Component.text("[강화]"))) return;
		if(event.getClickedInventory().getType() == InventoryType.PLAYER) {
			if(event.getCurrentItem() == null) return;
			if(isCanUp(event.getCurrentItem())) {
				OpenSmith((Player) event.getWhoClicked(), event.getCurrentItem());
			}
		}
		if(event.getClickedInventory().getType() == InventoryType.CHEST) {
			if(event.getSlot() == 40) {
				//강화
			}
		}
		
		event.setCancelled(true);
	}
	
	private boolean isCanUp(ItemStack item) {
		if(item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.NETHERITE_SWORD) return true; 
		if(item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS) return true;
		if(item.getType() == Material.NETHERITE_HELMET || item.getType() == Material.NETHERITE_CHESTPLATE || item.getType() == Material.NETHERITE_LEGGINGS || item.getType() == Material.NETHERITE_BOOTS) return true;
		return false;
	}

}
