package com.ho_bot.JMPrison.Util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class YmlUtil {
	
	public static void SetYml(File file, YamlConfiguration yml) {
		try {
			yml.save(file);
		} catch (IOException e) {
		}
	}

}
