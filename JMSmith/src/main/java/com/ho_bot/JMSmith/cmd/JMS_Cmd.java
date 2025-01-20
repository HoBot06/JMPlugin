package com.ho_bot.JMSmith.cmd;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ho_bot.JMSmith.inv.SmithInv;

public class JMS_Cmd implements TabExecutor{
	
	private SmithInv jmsI = new SmithInv();
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		Player player = (Player) sender;
		if(args[0].equalsIgnoreCase("Open")) {
			jmsI.OpenSmith(player);
		}
		return false;
	}
	
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String[] args) {
		
		return null;
	}

}
