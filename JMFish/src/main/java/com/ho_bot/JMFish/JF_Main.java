package com.ho_bot.JMFish;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ho_bot.JMFish.Event.JF_Event;

public class JF_Main extends JavaPlugin{
	
	public static JF_Main inst;
	
	@Override
	public void onEnable() {
		inst = this;
		Bukkit.getConsoleSender().sendMessage("JMFish v1.0 Enable");
		
		JF_Event.setPlugin(this);
        getServer().getPluginManager().registerEvents(new JF_Event(), this);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("JMFish v1.0 Disable");
	}

}
