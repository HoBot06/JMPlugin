package com.ho_bot.JMSmith.file;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.ho_bot.JMSmith.main.JMSmith;
import com.ho_bot.JMSmith.smith.Ability;
import com.ho_bot.JMSmith.smith.Chance;
import com.ho_bot.JMSmith.util.VarUtil;

public class ConfigFile {
	
	private FileConfiguration config() {
		return JMSmith.inst.getConfig();
	}
	
	public void reloadConfig() {
		VarUtil.armorMap.clear();
		VarUtil.weaponMap.clear();
		
		Set<String> armorList = config().getConfigurationSection("방어구").getKeys(false);
		Set<String> weaponList = config().getConfigurationSection("무기").getKeys(false);
		
		for(String s : armorList) {
			int level = Integer.parseInt(s.replace("강", ""));
			VarUtil.armorMap.put(level, config().getStringList("방어구."+s+".재료"));
			VarUtil.ab_armorMap.put(level, getAbility("방어구."+s+".능력치"));
			int success = config().getInt("방어구."+s+".확률.성공");
			int failed = config().getInt("방어구."+s+".확률.하락");
			int destroy = config().getInt("방어구."+s+".확률.파괴");
			Chance ch = new Chance(success, failed, destroy);
			VarUtil.chance_armorMap.put(level, ch);
		}
		for(String s : weaponList) {
			int level = Integer.parseInt(s.replace("강", ""));
			VarUtil.weaponMap.put(level, config().getStringList("무기."+s+".재료"));
			VarUtil.ab_weaponMap.put(level, getAbility("무기."+s+".능력치"));
			int success = config().getInt("무기."+s+".확률.성공");
			int failed = config().getInt("무기."+s+".확률.하락");
			int destroy = config().getInt("무기."+s+".확률.파괴");
			Chance ch = new Chance(success, failed, destroy);
			VarUtil.chance_weaponMap.put(level, ch);
		}
	}
	
	public Ability getAbility(String path) {
		Set<String> abilityList = config().getConfigurationSection(path).getKeys(false);
		int weapon_damage = 0;
		int armor_health = 0;
		for(String s : abilityList) {
			if(s.equalsIgnoreCase("공격력")) {
				weapon_damage = config().getInt(path+"."+s);
			}
			if(s.equalsIgnoreCase("체력")) {
				armor_health = config().getInt(path+"."+s);
			}
		}
		Ability ab = new Ability(weapon_damage, armor_health);
		return ab;
	}

}
