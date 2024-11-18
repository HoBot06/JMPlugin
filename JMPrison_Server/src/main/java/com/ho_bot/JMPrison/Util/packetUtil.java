package com.ho_bot.JMPrison.Util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import org.bukkit.entity.Player;

import com.ho_bot.JMPrison.ServerMain;

import static com.ho_bot.JMPrison.Util.NameUtil.*;

public class packetUtil {
	
	public void sendPacket(Player player, List<String> msg) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        
        try {
        	for(String s : msg) {
        		out.writeUTF(s);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
		player.sendPluginMessage(ServerMain.inst, PrisonId, b.toByteArray());
	}

}
