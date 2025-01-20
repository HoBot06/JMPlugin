package com.ho_bot.JMSmith.util;

import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.ho_bot.JMSmith.smith.Ability;
import com.ho_bot.JMSmith.smith.Chance;

public class VarUtil {
	
	public static HashMap<Integer, List<String>> weaponMap = new HashMap<>();
	public static HashMap<Integer, List<String>> armorMap = new HashMap<>();
	
	public static HashMap<Integer, Ability> ab_weaponMap = new HashMap<>();
	public static HashMap<Integer, Ability> ab_armorMap = new HashMap<>();
	
	public static HashMap<Integer, Chance> chance_weaponMap = new HashMap<>();
	public static HashMap<Integer, Chance> chance_armorMap = new HashMap<>();
	
	public static HashMap<String, ItemStack> matMap = new HashMap<>();

}
