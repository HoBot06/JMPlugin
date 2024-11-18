package com.ho_bot.JMPrison.File;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import com.ho_bot.JMPrison.ServerMain;
import com.ho_bot.JMPrison.Util.VarUtil;

import static com.ho_bot.JMPrison.Util.NameUtil.*;

public class ConfigFile {
	
	private Configuration getConfig() {
		return ServerMain.inst.getConfig();
	}
	
	private void saveConfig() {
		ServerMain.inst.saveConfig();
	}
	
	public void reloadConfig()
	{
		ServerMain.inst.reloadConfig();
		VarUtil.prison_loc = getConfig().getLocation(PrisonLoc_config);
		VarUtil.servername = getConfig().getString(ServerName);
		VarUtil.prisonname = getConfig().getString(PrisonName);
		saveConfig();
	}
}
