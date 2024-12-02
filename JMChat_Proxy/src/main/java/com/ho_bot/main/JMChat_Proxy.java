package com.ho_bot.main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.ho_bot.command.JMChat_Command;
import com.ho_bot.file.ProFile;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;

@Plugin(
	id = "jmchat_proxy",
	name = "JMChat_Proxy",
	version = "1.0.0",
	authors = {"Ho_Bot"}
)

public class JMChat_Proxy {
	
	public static final MinecraftChannelIdentifier Id = MinecraftChannelIdentifier.from("jmchat:chat");
	public static JMChat_Proxy inst;
	public final Logger logger;
	public final ProxyServer proxy;
	public final Path dataDir;
	
	private ProFile prof = new ProFile();
	
    @Inject
    public JMChat_Proxy(ProxyServer proxy, @DataDirectory Path dataDir, Logger logger) {
    	if (!Files.exists(dataDir)) {
        	try {
				Files.createDirectories(dataDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	System.out.println("online");
    	inst = this;
        this.proxy = proxy;
        this.logger = logger;
        this.dataDir = dataDir;
    }
    
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    	proxy.getChannelRegistrar().register(Id);
    	logger.info("[JMC] 채널 연결");
    	
    	CommandManager commandManager = proxy.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder("칭호")
            .plugin(this)
            .build();
        
        SimpleCommand simpleCommand = new JMChat_Command();

        commandManager.register(commandMeta, simpleCommand);
    }
    
    @Subscribe
    public void onJoin(LoginEvent event) {
    	Player player = event.getPlayer();
    	prof.createPlayerProfile(player);
    }
    
    @Subscribe
    public void onChat(PlayerChatEvent event) {
    	ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        
        try {
            out.writeUTF("msg");
            out.writeUTF(prof.getPlayerProfile(event.getPlayer())+" "+event.getPlayer().getUsername()+" "+event.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(RegisteredServer ser : this.proxy.getAllServers()) {
        	ser.sendPluginMessage(Id, b.toByteArray());
        }
    }
}
