package com.ho_bot.JMSmith.file;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.ho_bot.JMSmith.main.JMSmith;
import com.ho_bot.JMSmith.util.LogUtil;
import com.ho_bot.JMSmith.util.VarUtil;
import com.ho_bot.JMSmith.util.YmlUtil;

public class ItemFile {
	
	private YmlUtil YU = new YmlUtil();
	
	public void reloadItems() {
		File file = new File(JMSmith.inst.getDataFolder() + File.separator + "items");
		if(!file.exists()) {
			file.mkdirs();
			return;
		}
		VarUtil.matMap.clear();
		for(File f : file.listFiles()) {
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			VarUtil.matMap.put(yml.getString("name"), yml.getItemStack("item"));
		}
		LogUtil.info(VarUtil.matMap.size() + "개의 아이템 인식됨");
	}
	
	public void setItems(String name, ItemStack item) {
		File file = new File(JMSmith.inst.getDataFolder() + File.separator + "items//"+name+".yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
		
		yml.set("name", name);
		yml.set("item", item);
		
		YU.SetYml(file, yml);
	}

}
