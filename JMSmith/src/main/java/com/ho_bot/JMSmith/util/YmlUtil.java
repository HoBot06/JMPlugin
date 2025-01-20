package com.ho_bot.JMSmith.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class YmlUtil {
	
	public void SetYml(File file, YamlConfiguration yml) {
		try {
			yml.save(file);
		} catch (IOException e) {
		}
	}

}
