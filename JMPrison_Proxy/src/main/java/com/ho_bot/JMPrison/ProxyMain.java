package com.ho_bot.JMPrison;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.ho_bot.JMPrison.File.PrisonPlayerFile;
import com.ho_bot.JMPrison.File.ServerNameFile;
import com.ho_bot.JMPrison.File.PrisonPlayerFile.DataType;
import com.ho_bot.JMPrison.Util.sendJail;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;

@Plugin(
	id = "jmprison_proxy",
	name = "JMPrison_Proxy",
	version = "1.0.0",
	authors = {"Ho_Bot"}
)

public class ProxyMain {
	
	public static final MinecraftChannelIdentifier Id = MinecraftChannelIdentifier.from("jmprison:prison");
	public static ProxyMain inst;
	public final Logger logger;
	public final ProxyServer proxy;
	public final Path dataDir;

	private ServerNameFile snf = new ServerNameFile();
	private PrisonPlayerFile ppf = new PrisonPlayerFile();
	private sendJail sendJ = new sendJail();
	
    @Inject
    public ProxyMain(ProxyServer proxy, @DataDirectory Path dataDir, Logger logger) {
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
        
        snf.reloadServerName();
    }
	
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    	proxy.getChannelRegistrar().register(Id);
    	logger.info("[JMP] 채널 연결");
    }
    
    @Subscribe
    public void onJoin(LoginEvent event) {
    	Player player = event.getPlayer();
    	ppf.createPlayerPrison(player);
    }
    
	@Subscribe
	public void onPluginMessage(PluginMessageEvent event) {
		if(!(event.getSource() instanceof ServerConnection)) return;
        final String channel = "jmprison:prison";
        if (!event.getIdentifier().getId().equalsIgnoreCase(channel)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String receivedMessage = in.readUTF();
        
        if(receivedMessage.equalsIgnoreCase("isPrison")) {
        	String uuid = in.readUTF();
        	Player player = proxy.getPlayer(UUID.fromString(uuid)).get();
        	String servername = in.readUTF();
        	String loc = in.readUTF();
        	//logger.info(servername + " :: " + loc);
        	if(player.getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(snf.prisonName)) {
        		ppf.setPlayerPrison(player, servername, loc);
        		sendJ.gotoJail(player, proxy.getServer(snf.prisonName).get());
        		return;
        	}
        	player.createConnectionRequest(proxy.getServer(snf.prisonName).get()).connect().thenAccept(result -> {
                if (result.isSuccessful()) {
                	ppf.setPlayerPrison(player, servername, loc);
                	sendJ.gotoJail(player, proxy.getServer(snf.prisonName).get());
            		//logger.info("[JMP] 감옥 이동 : " + player.getUsername());
                } else {
                	logger.info("[JMP] 감옥 가기 오류 : " + player.getUsername());
                }
            });
        	//logger.info("[JMP] 감옥 서버이동 : " + player.getUsername());
        }
        
        if(receivedMessage.equalsIgnoreCase("hasPrison")) {
        	String uuid = in.readUTF();
        	Player player = proxy.getPlayer(UUID.fromString(uuid)).get();
        	if(ppf.getPlayer(player, DataType.prison)==null) return;
        	if((int) ppf.getPlayer(player, DataType.prison)>0) {
        		if(player.getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(snf.prisonName)) {
            		return;
            	}
            	player.createConnectionRequest(proxy.getServer(snf.prisonName).get()).connect().thenAccept(result -> {
                    if (result.isSuccessful()) {
                		//logger.info("[JMP] 감옥22 이동 : " + player.getUsername());
                    } else {
                    	logger.info("[JMP] 감옥 가기 오류 : " + player.getUsername());
                    }
                });
            	//logger.info("[JMP] 감옥 서버이동 : " + player.getUsername());
        	}
        }
        
        if(receivedMessage.equalsIgnoreCase("returnPrison")) {
        	String uuid = in.readUTF();
        	Player player = proxy.getPlayer(UUID.fromString(uuid)).get();
        	int time = Integer.parseInt(in.readUTF());
        	ppf.setPlayerPrison(player, time);
        	//logger.info("[JMP] 감옥 시간 설정 : " + player.getUsername());
        }
        
        if(receivedMessage.equalsIgnoreCase("quitPrison")) {
        	String uuid = in.readUTF();
        	Player player = proxy.getPlayer(UUID.fromString(uuid)).get();
        	String server = (String) ppf.getPlayer(player, DataType.server);
        	String loc = (String) ppf.getPlayer(player, DataType.loc);
        	if(player.getCurrentServer().get().getServerInfo().getName().equalsIgnoreCase(server)) {
        		ppf.setPlayerPrison(player, 0);
        		sendJ.quitJail(player, player.getCurrentServer().get().getServer(), loc);
        		return;
        	}
        	player.createConnectionRequest(proxy.getServer(server).get()).connect().thenAccept(result -> {
                if (result.isSuccessful()) {
                	ppf.setPlayerPrison(player, 0);
                	sendJ.quitJail(player, proxy.getServer(server).get(), loc);
            		//logger.info("[JMP] 감옥 출소 : " + player.getUsername());
                } else {
                	logger.info("[JMP] 감옥 출소 오류 : " + player.getUsername());
                }
            });
        	//logger.info("[JMP] 감옥출소 서버이동 : " + player.getUsername());
        	//logger.info("[JMP] 감옥 시간 설정 : " + player.getUsername());
        }
	}
}
