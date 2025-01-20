package com.ho_bot.JMSmith.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
	
	public static boolean hasItem(Player player, ItemStack item, int amo) {
		ItemStack hasI = item.clone();
		int hasA = 0;
		for(ItemStack i : player.getInventory().getStorageContents()) {
			if(i == null) continue;
			hasI.setAmount(i.getAmount());
			if(i.equals(hasI)) {
				hasA += i.getAmount();
			}
		}
		if(amo <= hasA) {
			return true;
		}
		return false;
	}
	
	public static void removeItem(Player player, ItemStack item, int amo) {
		int _amo = amo;
		ItemStack _item = item.clone();
		for(ItemStack i : player.getInventory().getStorageContents()) {
			if(i == null) continue;
			if(_amo <= 0) return;
			_item.setAmount(i.getAmount());
			if(i.equals(_item)) {
				if(i.getAmount() >= _amo) {
					i.setAmount(i.getAmount() - _amo);
					_amo = 0;
				}
				else {
					_amo -= i.getAmount();
					i.setAmount(0);
				}
			}
		}
	}

}
