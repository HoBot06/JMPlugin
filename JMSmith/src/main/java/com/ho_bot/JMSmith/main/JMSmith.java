package com.ho_bot.JMSmith.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ho_bot.JMSmith.cmd.JMS_Cmd;
import com.ho_bot.JMSmith.event.JMS_Event;

public class JMSmith extends JavaPlugin {
	
	public static JMSmith inst;
	
	@Override
	public void onEnable() {
		inst = this;
		Bukkit.getConsoleSender().sendMessage("JMSmith v1.0 Enable");
		
        getServer().getPluginManager().registerEvents(new JMS_Event(), this);
        
        getCommand("JMS").setExecutor(new JMS_Cmd());
        getCommand("JMS").setTabCompleter(new JMS_Cmd());
        
        getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("JMSmith v1.0 Disable");
	}

}
