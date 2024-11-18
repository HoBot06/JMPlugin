package com.ho_bot.JMPrison.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import com.ho_bot.JMPrison.class_.SoloRoom;

public class VarUtil {
	
	public static HashMap<UUID, Boolean> click_ = new HashMap<>();
	public static HashMap<UUID, Location> click_left = new HashMap<>();
	public static HashMap<UUID, Location> click_right = new HashMap<>();
	public static HashMap<SoloRoom, UUID> solo_prison = new HashMap<>();
	public static List<UUID> prison_players = new ArrayList<>();
	public static Location prison_loc = null;
	public static String servername = null;
	public static String prisonname = null;
	public final static int Prison_time = 600; //초 단위

}
