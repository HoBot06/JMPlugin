package com.ho_bot.JMPrison.Util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ho_bot.JMPrison.ProxyMain;
import com.ho_bot.JMPrison.File.PrisonPlayerFile;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class sendJail {
	
	public void gotoJail(Player player, RegisteredServer server) {
		try {
    		//System.out.println("전송 " + server.getServerInfo().getName());
            
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            
            try {
                out.writeUTF("gotojail");
                out.writeUTF(player.getUniqueId().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.sendPluginMessage(ProxyMain.Id, b.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void hasJail(Player player, RegisteredServer server) {
		try {
    		//System.out.println("jail전송 " + server.getServerInfo().getName());
            
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            
            try {
                out.writeUTF("hasjail");
                out.writeUTF(player.getUniqueId().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(RegisteredServer ser : ProxyMain.inst.proxy.getAllServers()) {
            	ser.sendPluginMessage(ProxyMain.Id, b.toByteArray());
            }
            server.sendPluginMessage(ProxyMain.Id, b.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void quitJail(Player player, RegisteredServer server, String loc) {
		try {
    		//System.out.println("위치전송 " + server.getServerInfo().getName());
            
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            
            try {
                out.writeUTF("quitjail");
                out.writeUTF(player.getUniqueId().toString());
                out.writeUTF(loc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(RegisteredServer ser : ProxyMain.inst.proxy.getAllServers()) {
            	ser.sendPluginMessage(ProxyMain.Id, b.toByteArray());
            }
            server.sendPluginMessage(ProxyMain.Id, b.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
