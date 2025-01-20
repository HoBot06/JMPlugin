package com.ho_bot.JMSmith.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ho_bot.JMSmith.cmd.JMS_Cmd;
import com.ho_bot.JMSmith.event.JMS_Event;
import com.ho_bot.JMSmith.file.ConfigFile;
import com.ho_bot.JMSmith.file.ItemFile;

public class JMSmith extends JavaPlugin {
	
	public static JMSmith inst;
	
	private ConfigFile configF = new ConfigFile();
	private ItemFile itemF = new ItemFile();
	
	@Override
	public void onEnable() {
		inst = this;
		Bukkit.getConsoleSender().sendMessage("JMSmith v1.0 Enable");
		
        getServer().getPluginManager().registerEvents(new JMS_Event(), this);
        
        getCommand("JMS").setExecutor(new JMS_Cmd());
        getCommand("JMS").setTabCompleter(new JMS_Cmd());
        
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        itemF.reloadItems();
        configF.reloadConfig();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("JMSmith v1.0 Disable");
	}

}
