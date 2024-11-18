package com.ho_bot.JMPrison.Util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EtcUtil {
	
	public String colorString(String text) {
        return text.replace("&", "§");
    }
	
	public String getTimeTrans(int sec) {
        int day = sec / (24 * 3600);
        sec %= (24 * 3600);
        int hour = sec / 3600;
        sec %= 3600;
        int min = sec / 60;
        sec %= 60;

        StringBuilder time = new StringBuilder();
        if (day > 0) time.append(day).append("일 ");
        if (hour > 0) time.append(hour).append("시 ");
        if (min > 0) time.append(min).append("분 ");
        time.append(sec).append("초");

        return time.toString();
    }
	
	public String locString(Location loc) {
		return loc.getWorld().getName()+":"+loc.getX()+":"+loc.getY()+":"+loc.getZ();
	}
	
	public Location locString(String loc) {
		String[] s = loc.split(":");
		return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]));
	}
	
	public void sendSoundMessage(Player p, String msg, Sound sound, float volume) {
        p.playSound(p, sound, volume, volume);
        p.sendMessage(colorString(msg));
    }
    public void sendSoundMessage(Player p, String msg, String sound, float volume) {
        p.playSound(p, sound, volume, volume);
        p.sendMessage(colorString(msg));
    }
    public void sendSoundTitle(Player p, String title, String subtitle, Sound sound, float volume, int fadeIn, int showTime, int fadeOut) {
        p.playSound(p, sound, volume, volume);
        p.sendTitle(colorString(title), colorString(subtitle), fadeIn, showTime, fadeOut);
    }
    public void sendSoundTitle(Player p, String title, String subtitle, String sound, float volume, int fadeIn, int showTime, int fadeOut) {
        p.playSound(p, sound, volume, volume);
        p.sendTitle(colorString(title), colorString(subtitle), fadeIn, showTime, fadeOut);
    }
    
    public boolean isInLoc(Location loc1, Location loc2, Location loc) {
    	double[] Loc_X = {loc1.getX(), loc2.getX()};
		double[] Loc_Y = {loc1.getY(), loc2.getY()};
		double[] Loc_Z = {loc1.getZ(), loc2.getZ()};
		
		Arrays.sort(Loc_X);
		Arrays.sort(Loc_Y);
		Arrays.sort(Loc_Z);
		
		double P_X = loc.getX();
		double P_Y = loc.getY();
		double P_Z = loc.getZ();
		
		if((Loc_X[0] <= P_X && P_X <= Loc_X[1]) && (Loc_Y[0] <= P_Y && P_Y <= Loc_Y[1]) && (Loc_Z[0] <= P_Z && P_Z <= Loc_Z[1])) {
			return true;
		}
		return false;
    }

}
