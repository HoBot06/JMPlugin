package com.ho_bot.JMPrison.File;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Charsets;
import com.ho_bot.JMPrison.ServerMain;
import com.ho_bot.JMPrison.Util.YmlUtil;

public class PlayerFile {
	
	public void createPrison(UUID playeruuid) {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "playerdata//prison//"+playeruuid.toString()+".yml");
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	public void setPrison(UUID playeruuid, int time) {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "playerdata//prison//"+playeruuid.toString()+".yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		yml.set("감옥시간", time);
		YmlUtil.SetYml(file, yml);
	}
	
	public int getPrison(UUID playeruuid) {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "playerdata//prison//"+playeruuid.toString()+".yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		if(yml.contains("감옥시간")) return yml.getInt("감옥시간");
		return 0;
	}
	
	public void addPrison(UUID playeruuid, int time) {
		setPrison(playeruuid, time+getPrison(playeruuid));
	}
	
	public void miusPrison(UUID playeruuid, int time) {
		setPrison(playeruuid, getPrison(playeruuid)-time);
	}
	
	public void setInventory(UUID playeruuid) {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "playerdata//prison//"+playeruuid.toString()+".yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		HashMap<Integer, ItemStack> itemlist = getItemList(playeruuid);
		for(Entry<Integer, ItemStack> entry : itemlist.entrySet()) {
			yml.set("아이템."+entry.getKey(), entry.getValue());
		}
		YmlUtil.SetYml(file, yml);
	}
	
	public HashMap<Integer, ItemStack> getInventory(UUID playeruuid) {
		HashMap<Integer, ItemStack> itemlist = new HashMap<>();
		try {
			File file = new File(ServerMain.inst.getDataFolder() + File.separator + "playerdata//prison//"+playeruuid.toString()+".yml");
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
			FileReader f_read = new FileReader(file, Charsets.UTF_8);
			Map<String, Object> item_map = new Yaml().load(f_read);
			if(item_map.containsKey("아이템")) {
				Map<String, Object> itemlist_map = (Map<String, Object>) item_map.get("아이템");
				for(Entry<String, Object> entry : itemlist_map.entrySet()) {
					ItemStack item = yml.getItemStack("아이템."+entry.getKey());
					itemlist.put(Integer.parseInt(entry.getKey()), item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemlist;
	}
	
	public void clearInventory(UUID playeruuid) {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "playerdata//prison//"+playeruuid.toString()+".yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		yml.set("아이템", "");
		YmlUtil.SetYml(file, yml);
	}
	
	private HashMap<Integer, ItemStack> getItemList(UUID playeruuid) {
		HashMap<Integer, ItemStack> itemlist = new HashMap<>();
		try {
			int count = 0;
			for(ItemStack item : Bukkit.getPlayer(playeruuid).getInventory().getContents()) {
				if(item!=null) {
					itemlist.put(count, item);
				}
				count++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemlist;
	}

}
