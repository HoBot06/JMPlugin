package com.ho_bot.JMPrison.Command;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ho_bot.JMPrison.ServerMain;
import com.ho_bot.JMPrison.File.ConfigFile;
import com.ho_bot.JMPrison.File.RoomFile;
import com.ho_bot.JMPrison.Util.EtcUtil;
import com.ho_bot.JMPrison.Util.PrisonUtil;
import com.ho_bot.JMPrison.Util.VarUtil;
import com.ho_bot.JMPrison.Util.packetUtil;

import static com.ho_bot.JMPrison.Util.NameUtil.*;

public class JP_Cmd implements TabExecutor{
	
	private packetUtil packetU = new packetUtil();
	private PrisonUtil prisonU = new PrisonUtil();
	private ConfigFile configF = new ConfigFile();
	private RoomFile roomF = new RoomFile();
	//private EtcUtil etc = new EtcUtil();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull  String label, @NotNull String[] args) {
		if(!(sender instanceof Player p)) return false;
        if(!p.isOp()) return false;
		if(label.equalsIgnoreCase("JMPrison")) {
			if(args[0].equalsIgnoreCase("test")) {
				sender.sendMessage("보냈습니다");
				packetU.sendPacket((Player) sender, Arrays.asList(args[1]));
				return true;
			}
			if(args[0].equalsIgnoreCase("감옥위치설정")) {
				ServerMain.inst.getConfig().set(PrisonLoc_config, p.getLocation());
				ServerMain.inst.saveConfig();
				configF.reloadConfig();
				return true;
			}
			if(args[0].equalsIgnoreCase("감옥보내기")) {
				Player player = Bukkit.getPlayer(args[1]);
				if(args.length>=3&& args[2]!=null) {
					prisonU.sendPrison(player, Integer.parseInt(args[2])*60, true);
					sender.sendMessage(Integer.parseInt(args[2])*60+"분 독방전송 완료");
					return true;
				}
				sender.sendMessage("감옥 전송 완료");
				prisonU.sendPrison(player);
			}
			if(args[0].equalsIgnoreCase("블럭채킹")) {
				if(VarUtil.click_.containsKey(p.getUniqueId())) {
					VarUtil.click_.remove(p.getUniqueId());
					p.sendMessage("OFF");
				}
				else {
					VarUtil.click_.put(p.getUniqueId(), true);
					p.sendMessage("ON");
				}
			}
			if(args[0].equalsIgnoreCase("스폰블럭")) {
				if(VarUtil.click_left.containsKey(p.getUniqueId())&&VarUtil.click_right.containsKey(p.getUniqueId())) {
					roomF.createSpawnBlock(args[1], VarUtil.click_left.get(p.getUniqueId()), VarUtil.click_right.get(p.getUniqueId()));
					p.sendMessage("위치생성");
				}
			}
		}
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {
		if(args.length==1) {
			return Arrays.asList("test", "감옥위치설정", "감옥보내기", "블럭채킹", "스폰블럭");
		}
		return null;
	}

}
