package com.ho_bot.JMSmith.inv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.ho_bot.JMSmith.main.JMSmith;
import com.ho_bot.JMSmith.smith.Ability;
import com.ho_bot.JMSmith.smith.Chance;
import com.ho_bot.JMSmith.util.GuiUtil;
import com.ho_bot.JMSmith.util.ItemUtil;
import com.ho_bot.JMSmith.util.VarUtil;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class SmithInv {
	
	private String topLore = ChatColor.WHITE+"====[강화]====";
	private String bottomLore = ChatColor.WHITE+"============";
	
	private GuiUtil GU = new GuiUtil();
	private Random r = new Random();
	
	public void OpenSmith(Player player) {
		Inventory inv = Bukkit.createInventory(null, 45, Component.text("[강화]"));
		
		List<Integer> list = Arrays.asList(11, 15, 28, 29, 30, 31, 32, 33, 34, 40);
		for(int i = 0; i < 45; i++) {
			if(list.contains(i)) continue;
			GU.Stack(" ", Material.YELLOW_STAINED_GLASS_PANE, 1, null, i, inv);
		}
		GU.Stack(ChatColor.YELLOW+"업그레이드", Material.WHITE_STAINED_GLASS_PANE, 1, null, 40, inv);
		
		player.openInventory(inv);
	}
	
	public void OpenSmith(Player player, ItemStack item) {
		Inventory inv = Bukkit.createInventory(null, 45, Component.text("[강화]"));
		
		List<Integer> list = Arrays.asList(11, 15, 28, 29, 30, 31, 32, 33, 34, 40);
		for(int i = 0; i < 45; i++) {
			if(list.contains(i)) continue;
			GU.Stack(" ", Material.YELLOW_STAINED_GLASS_PANE, 1, null, i, inv);
		}
		
		int level = getLevel(item);
		
		GU.Stack(item, 11, inv);
		if(!VarUtil.ab_armorMap.containsKey(level+1) && !VarUtil.ab_weaponMap.containsKey(level+1)) return;
		if(isWeapon(item)) {
			GU.Stack(nextItem(item, level+1, VarUtil.ab_weaponMap.get(level+1)), 15, inv);
		}
		if(isArmor(item)) {
			GU.Stack(nextItem(item, level+1, VarUtil.ab_armorMap.get(level+1)), 15, inv);
		}
		List<String> matlist = getMat(level+1, item);
		List<String> lore = new ArrayList<>();
		lore.add("");
		int count = 28;
		for(String s : matlist) {
			String itemname = s.split(" ")[0];
			int amo = Integer.parseInt(s.split(" ")[1]);
			if(VarUtil.matMap.containsKey(itemname)) {
				ItemStack mat_item = VarUtil.matMap.get(itemname);
				mat_item.setAmount(amo);
				String name = mat_item.getI18NDisplayName();
				if(mat_item.getItemMeta().hasDisplayName()) {
					name = mat_item.getItemMeta().displayName().toString();
				}
				lore.add(ChatColor.WHITE+"재료:");
				if(ItemUtil.hasItem(player, mat_item, amo)) {
					lore.add(ChatColor.WHITE+"- " + name+"x"+amo + ChatColor.GREEN+" [보유]");
				}
				else {
					lore.add(ChatColor.WHITE+"- " + name+"x"+amo + ChatColor.RED+" [미보유]");
				}
				lore.add("");
				lore.add(ChatColor.WHITE+"확률:");
				Chance ch = new Chance(0, 0, 0);
				if(isWeapon(item)) {
					ch = VarUtil.chance_weaponMap.get(level + 1);
				}
				if(isArmor(item)) {
					ch = VarUtil.chance_armorMap.get(level + 1);
				}
				
				lore.add(ChatColor.WHITE+"- 성공: " + ch.success+"%");
				lore.add(ChatColor.WHITE+"- 하락: " + ch.down+"%");
				lore.add(ChatColor.WHITE+"- 파괴: " + ch.destroy+"%");
				
				GU.Stack(mat_item, count, inv);
				count++;
			}
		}
		GU.Stack(ChatColor.YELLOW+"업그레이드", Material.WHITE_STAINED_GLASS_PANE, 1, lore, 40, inv);
		player.openInventory(inv);
	}
	
	public void EventSmith(InventoryClickEvent event) {
		if(event.getView().getTopInventory() == null) return;
		if(!event.getView().title().equals(Component.text("[강화]"))) return;
		if(event.getClickedInventory() == null) return;
		
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		if(event.getClickedInventory().getType() == InventoryType.PLAYER) {
			if(event.getCurrentItem() != null) {
				if(isCanUp(event.getCurrentItem())) {
					OpenSmith((Player) event.getWhoClicked(), event.getCurrentItem());
				}
			}
		}
		if(event.getClickedInventory().getType() == InventoryType.CHEST) {
			if(event.getSlot() == 40) {
				if(event.getView().getTopInventory().getItem(11) != null) {
					ItemStack item = event.getView().getTopInventory().getItem(11);
					int level = getLevel(item);
					List<String> matList = getMat(level+1, item);
					//아이템 확인
					for(String s : matList) {
						String itemname = s.split(" ")[0];
						int amo = Integer.parseInt(s.split(" ")[1]);
						if(VarUtil.matMap.containsKey(itemname)) {
							ItemStack mat_item = VarUtil.matMap.get(itemname);
							mat_item.setAmount(amo);
							if(!ItemUtil.hasItem(player, mat_item, amo)) {
								player.sendMessage("재료 부족");
								return;
							}
						}
					}
					
					//아이템 소비
					for(String s : matList) {
						String itemname = s.split(" ")[0];
						int amo = Integer.parseInt(s.split(" ")[1]);
						if(VarUtil.matMap.containsKey(itemname)) {
							ItemStack mat_item = VarUtil.matMap.get(itemname);
							mat_item.setAmount(amo);
							ItemUtil.removeItem(player, mat_item, amo);
						}
					}
					
					//확률 계산
					Chance ch = new Chance(0, 0, 0);
					if(isWeapon(item)) {
						ch = VarUtil.chance_weaponMap.get(level + 1);
					}
					if(isArmor(item)) {
						ch = VarUtil.chance_armorMap.get(level + 1);
					}
					
					int rand = r.nextInt(100);
					if(rand < ch.success) {
						int count = -1;
						for(ItemStack i : player.getInventory().getStorageContents()) {
							count++;
							if(i==null) continue;
							if(i.equals(item)) {
								if(isWeapon(item)) {
									ItemStack nextItem = nextItem(item, level+1, VarUtil.ab_weaponMap.get(level+1));
									player.getInventory().setItem(count, nextItem);
									player.sendMessage("무기 강화성공");
									OpenSmith(player);
									return;
								}
								if(isArmor(item)) {
									ItemStack nextItem = nextItem(item, level+1, VarUtil.ab_armorMap.get(level+1));
									player.getInventory().setItem(count, nextItem);
									player.sendMessage("갑옷 강화성공");
									OpenSmith(player);
									return;
								}
							}
						}
					}
					rand = rand - ch.success;
					if(ch.down > 0 && rand < ch.down) {
						int count = -1;
						for(ItemStack i : player.getInventory().getStorageContents()) {
							count++;
							if(i==null) continue;
							if(i.equals(item)) {
								if(isWeapon(item)) {
									ItemStack nextItem = nextItem(item, level+1, VarUtil.ab_weaponMap.get(level-1));
									player.getInventory().setItem(count, nextItem);
									player.sendMessage("무기 하락");
									OpenSmith(player);
									return;
								}
								if(isArmor(item)) {
									ItemStack nextItem = nextItem(item, level+1, VarUtil.ab_armorMap.get(level-1));
									player.getInventory().setItem(count, nextItem);
									player.sendMessage("갑옷 하락");
									OpenSmith(player);
									return;
								}
							}
						}
					}
					rand = rand - ch.down;
					if(ch.destroy > 0 && rand < ch.destroy) {
						int count = -1;
						for(ItemStack i : player.getInventory().getStorageContents()) {
							count++;
							if(i==null) continue;
							if(i.equals(item)) {
								player.getInventory().setItem(count, null);
								player.sendMessage("파괴");
								OpenSmith(player);
								return;
							}
						}
					}
					player.sendMessage("강화 실패");
				}
			}
		}
	}
	
	private ItemStack nextItem(ItemStack item, int level, Ability ab) {
		ItemStack nextI = item.clone();
		List<String> toplore = new ArrayList<>();
		List<String> bottomlore = new ArrayList<>();
		
		List<String> lore = new ArrayList<>();
		if(nextI != null && nextI.getLore() != null) {
			int isLore = 0;
			for(String s : lore) {
				if(s.equalsIgnoreCase(topLore)) {
					isLore = 1;
					continue;
				}
				if(s.equalsIgnoreCase(bottomLore)) {
					isLore = 2;
					continue;
				}
				if(isLore == 0) {
					toplore.add(s);
				}
				else if(isLore == 2) {
					bottomlore.add(s);
				}
			}
		}
		
		if(toplore != null) {
			lore.addAll(toplore);
		}
		
		lore.add(topLore);
		lore.add(ChatColor.WHITE+""+level+"강:");
		if(ab.weapon_damage > 0) {
			lore.add(ChatColor.WHITE+"- 공격력: " + ab.weapon_damage);
		}
		if(ab.armor_health > 0) {
			lore.add(ChatColor.WHITE+"- 체력: " + ab.armor_health);
		}
		lore.add(bottomLore);
		
		if(bottomlore != null) {
			lore.addAll(bottomlore);
		}
		
		nextI.setLore(lore);
		
		ItemMeta nextM = nextI.getItemMeta();
		NamespacedKey key = NamespacedKey.fromString("upgrade", JMSmith.inst);
		nextM.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, level);
		nextI.setItemMeta(nextM);
		
		return nextI;
	}
	
	private boolean isCanUp(ItemStack item) {
		if(item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.NETHERITE_SWORD) return true; 
		if(item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS) return true;
		if(item.getType() == Material.NETHERITE_HELMET || item.getType() == Material.NETHERITE_CHESTPLATE || item.getType() == Material.NETHERITE_LEGGINGS || item.getType() == Material.NETHERITE_BOOTS) return true;
		return false;
	}
	
	private boolean isWeapon(ItemStack item) {
		if(item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.NETHERITE_SWORD) return true; 
		return false;
	}
	
	private boolean isArmor(ItemStack item) {
		if(item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS) return true;
		if(item.getType() == Material.NETHERITE_HELMET || item.getType() == Material.NETHERITE_CHESTPLATE || item.getType() == Material.NETHERITE_LEGGINGS || item.getType() == Material.NETHERITE_BOOTS) return true;
		return false;
	}
	
	private List<String> getMat(int level, ItemStack item) {
		if(item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.NETHERITE_SWORD) {
			return VarUtil.weaponMap.get(level);
		}
		if(item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.DIAMOND_BOOTS
				|| item.getType() == Material.NETHERITE_HELMET || item.getType() == Material.NETHERITE_CHESTPLATE || item.getType() == Material.NETHERITE_LEGGINGS || item.getType() == Material.NETHERITE_BOOTS) {
			return VarUtil.armorMap.get(level);
		}
		return null;
	}
	
	private int getLevel(ItemStack item) {
		int level = 0;
		if(item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer() != null) {
			NamespacedKey key = NamespacedKey.fromString("upgrade", JMSmith.inst);
			if(item.getItemMeta().getPersistentDataContainer().has(key)) {
				level = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
			}
		}
		return level;
	}

}
