package com.ho_bot.main;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class JMChat_Server extends JavaPlugin implements PluginMessageListener{
	
	public static JMChat_Server inst;
	
	private final String msg = "msg";
	public final String Id = "jmchat:chat";
	
	@Override
	public void onEnable() {
		inst = this;
		
		Bukkit.getConsoleSender().sendMessage("JMC_Server online");
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "jmchat:chat");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "jmchat:chat", this);
	}
	
	@Override
	public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
		// 데이터 읽기
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String receivedMessage = in.readUTF();  // 메시지 읽기
		
		if(channel.equalsIgnoreCase(Id)) {
			if(receivedMessage.equalsIgnoreCase(msg)) {
				String p_message = in.readUTF();
				Bukkit.broadcastMessage(p_message);
			}
		}
	}

}
