package com.ho_bot.JMSmith.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

public class GuiUtil {
	
	public void Stack(String Display, Material material, int STACK, List<String> lore, int loc, Inventory inv) {
		
		ItemStack item = new ItemStack(material);
		item.setAmount(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.displayName(Component.text(Display));
		item_Meta.lore(_lore(lore));
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
	
	public void Stack(String Display, Material material, boolean unbreakable, int STACK, List<String> lore, int loc, Inventory inv) {
		
		ItemStack item = new ItemStack(material);
		item.setAmount(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.displayName(Component.text(Display));
		item_Meta.lore(_lore(lore));
		item_Meta.setUnbreakable(unbreakable);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
	
	public void Stack(ItemStack item, int loc, Inventory inv) {
		inv.setItem(loc, item);
	}
	
	private List<Component> _lore(List<String> list) {
		if(list == null) return null;
		List<Component> lore = new ArrayList<>();
		for(String s : list) {
			lore.add(Component.text(s));
		}
		return lore;
	}

}
