package com.ho_bot.JMSmith.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class LogUtil {
	
	private static final String prefix = ChatColor.AQUA + "[JMSmith] "+ChatColor.GRAY;

    public static void info(String msg) {
        Bukkit.getConsoleSender().sendMessage(prefix + msg);
    }

}
