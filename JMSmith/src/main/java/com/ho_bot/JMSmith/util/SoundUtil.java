package com.ho_bot.JMSmith.util;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {
	
	public static void SoundP(Player player, Sound sound, float volume, float pitch) {
		player.playSound(player.getLocation(), sound, volume, pitch);
	}

	public static void SoundPL(Player player, Location loc, Sound sound, float volume, float pitch) {
		player.playSound(loc, sound, volume, pitch);
	}

}
