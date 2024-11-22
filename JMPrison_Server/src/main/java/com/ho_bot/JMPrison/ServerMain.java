package com.ho_bot.JMPrison;

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
import com.ho_bot.JMPrison.Command.JP_Cmd;
import com.ho_bot.JMPrison.Event.JP_Event;
import com.ho_bot.JMPrison.File.ConfigFile;
import com.ho_bot.JMPrison.File.PlayerFile;
import com.ho_bot.JMPrison.File.RoomFile;
import com.ho_bot.JMPrison.Timer.Prison_Timer;
import com.ho_bot.JMPrison.Util.EtcUtil;
import com.ho_bot.JMPrison.Util.PrisonUtil;
import com.ho_bot.JMPrison.Util.VarUtil;
import com.ho_bot.JMPrison.Util.packetUtil;

import io.lumine.mythic.bukkit.utils.logging.Log;

import static com.ho_bot.JMPrison.Util.NameUtil.PrisonId;

public class ServerMain extends JavaPlugin implements PluginMessageListener{
	
	public static ServerMain inst;
	
	private final String gotoJail = "gotojail";
	private final String quitJail = "quitjail";
	
	private packetUtil packetU = new packetUtil();
	private ConfigFile configF = new ConfigFile();
	private PrisonUtil prisonU = new PrisonUtil();
	private Prison_Timer prison_T = new Prison_Timer();
	private EtcUtil etc = new EtcUtil();
	
	@Override
	public void onEnable() {
		inst = this;
		
		Bukkit.getConsoleSender().sendMessage("JMP_Server online");
		getServer().getPluginCommand("JMPrison").setExecutor(new JP_Cmd());
		
		JP_Event.setPlugin(this);
        getServer().getPluginManager().registerEvents(new JP_Event(), this);
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "jmprison:prison");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "jmprison:prison", this);
		
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        configF.reloadConfig();
        
        prison_T.runTaskTimer(this, 0, 20L);
	}
	
	@Override
	public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
		Logger log = Bukkit.getLogger();
		// 데이터 읽기
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String receivedMessage = in.readUTF();  // 메시지 읽기
		log.info(receivedMessage);
		
		if(channel.equalsIgnoreCase(PrisonId)) {
			if(receivedMessage.equalsIgnoreCase(gotoJail)) {
				String uuid = in.readUTF();
				Player Packet_p = Bukkit.getPlayer(UUID.fromString(uuid));
				if(VarUtil.prison_loc!=null) {
					prisonU.sendPrison(Packet_p, VarUtil.Prison_time, true);
					//Packet_p.sendMessage("감옥감");
					return;
				}
				else {
					packetU.sendPacket(Packet_p, Arrays.asList("isPrison", uuid));
					log.info("[알림] 감옥 가기 오류");
					return;
				}
			}
			if(receivedMessage.equalsIgnoreCase(quitJail)) {
				String uuid = in.readUTF();
				Player Packet_p = Bukkit.getPlayer(UUID.fromString(uuid));
	        	String loc = in.readUTF();
	        	
				Packet_p.teleport(etc.locString(loc));
				//log.info("출 4885 소");
			}
		}
	}

}
