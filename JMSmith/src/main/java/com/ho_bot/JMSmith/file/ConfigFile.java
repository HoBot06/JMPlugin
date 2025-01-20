package com.ho_bot.JMSmith.file;

import org.bukkit.Bukkit;

import com.ho_bot.JMSmith.main.JMSmith;

public class ConfigFile {
	
	public void reloadConfig() {
		Bukkit.getLogger().info(JMSmith.inst.getConfig().getConfigurationSection("방어구")+"");
		Bukkit.getLogger().info(JMSmith.inst.getConfig().getConfigurationSection("무기")+"");
		
	}

}
