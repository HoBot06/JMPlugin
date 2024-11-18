package com.ho_bot.JMPrison.File;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Charsets;
import com.ho_bot.JMPrison.ServerMain;
import com.ho_bot.JMPrison.Util.YmlUtil;
import com.ho_bot.JMPrison.class_.SoloRoom;
import com.ho_bot.JMPrison.class_.SpawnBlock;

public class RoomFile {
	
	/*public void createRoom() {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "room//room_config.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		YmlUtil.SetYml(file, yml);
	}*/
	
	public void setRoom(String code, Location loc) {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "room//room_config.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		yml.set("Data."+code, loc);
		YmlUtil.SetYml(file, yml);
	}
	
	public List<SoloRoom> getRoomList() {
		List<SoloRoom> loc_map = new ArrayList<>();
		try {
			File file = new File(ServerMain.inst.getDataFolder() + File.separator + "room//room_config.yml");
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
			FileReader f_read = new FileReader(file, Charsets.UTF_8);
			Map<String, Object> map = new Yaml().load(f_read);
			if(map.containsKey("Data")) {
				Map<String, Object> list_map = (Map<String, Object>) map.get("Data");
				for(Entry<String, Object> entry : list_map.entrySet()) {
					SoloRoom soloroom = new SoloRoom(entry.getKey(), yml.getLocation("Data."+entry.getKey()));
					loc_map.add(soloroom);
				}
			}
		} catch (Exception e) {
		}
		return loc_map;
	}
	
	public void createSpawnBlock(String code, Location loc1, Location loc2) {
		File file = new File(ServerMain.inst.getDataFolder() + File.separator + "room//sblock_config.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		yml.set("blocklist."+code+".loc1",loc1);
		yml.set("blocklist."+code+".loc2",loc2);
		YmlUtil.SetYml(file, yml);
	}
	
	public HashMap<Integer, SpawnBlock> getSpawnList() {
		HashMap<Integer, SpawnBlock> spawnlist = new HashMap<>();
		try {
			File file = new File(ServerMain.inst.getDataFolder() + File.separator + "room//sblock_config.yml");
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
			FileReader f_read = new FileReader(file, Charsets.UTF_8);
			Map<String, Object> map = new Yaml().load(f_read);
			if(map.containsKey("blocklist")) {
				Map<String, Object> list_map = (Map<String, Object>) map.get("blocklist");
				for(Entry<String, Object> entry : list_map.entrySet()) {
					SpawnBlock spawnblock = new SpawnBlock(yml.getLocation("blocklist."+entry.getKey()+".loc1"), yml.getLocation("blocklist."+entry.getKey()+".loc2"));
					spawnlist.put(Integer.parseInt(entry.getKey()), spawnblock);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spawnlist;
	}

}
