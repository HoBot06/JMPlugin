package com.ho_bot.JMSmith.cmd;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ho_bot.JMSmith.file.ConfigFile;
import com.ho_bot.JMSmith.file.ItemFile;
import com.ho_bot.JMSmith.inv.SmithInv;
import com.ho_bot.JMSmith.util.SoundUtil;

public class JMS_Cmd implements TabExecutor{
	
	private SmithInv jmsI = new SmithInv();
	private ConfigFile configF = new ConfigFile();
	private ItemFile itemF = new ItemFile();
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		
		if(sender.isOp()) {
			if(args[0].equalsIgnoreCase("create")) {
				Player player = (Player) sender;
				String name = args[1];
				itemF.setItems(name, player.getInventory().getItemInMainHand());
				sender.sendMessage("[JMSmith] " + name+ "으로 아이템이 등록되었습니다");
			}
			if(args[0].equalsIgnoreCase("reload")) {
				configF.reloadConfig();
				itemF.reloadItems();
				sender.sendMessage("[JMSmith] 리로드 완료");
			}
			
			Player player = (Player) sender;
			if(args[0].equalsIgnoreCase("Open")) {
				jmsI.OpenSmith(player);
				SoundUtil.SoundP(player, Sound.UI_BUTTON_CLICK, 1f, 0.3f);
			}
		}
		return false;
	}
	
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {
		
		return null;
	}

}
