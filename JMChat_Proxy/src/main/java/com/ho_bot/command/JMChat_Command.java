package com.ho_bot.command;

import com.ho_bot.file.ProFile;
import com.ho_bot.main.JMChat_Proxy;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class JMChat_Command implements SimpleCommand{
	
	private ProFile proF = new ProFile();

	@Override
	public void execute(Invocation invocation) {
		CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();
        

        if(args[0].equalsIgnoreCase("치지직")) {
        	source.sendMessage(Component.text("칭호 적용", NamedTextColor.WHITE));
        	proF.setPlayerProfile(JMChat_Proxy.inst.proxy.getPlayer(args[1]).get(), ":chzzh:");
        }
		if(args[0].equalsIgnoreCase("숲")) {
			source.sendMessage(Component.text("칭호 적용", NamedTextColor.WHITE));
			proF.setPlayerProfile(JMChat_Proxy.inst.proxy.getPlayer(args[1]).get(), ":soop:");
		}
		if(args[0].equalsIgnoreCase("시청자")) {
			source.sendMessage(Component.text("칭호 적용", NamedTextColor.WHITE));
			proF.setPlayerProfile(JMChat_Proxy.inst.proxy.getPlayer(args[1]).get(), ":viewer:");
		}
		if(args[0].equalsIgnoreCase("어드민")) {
			source.sendMessage(Component.text("칭호 적용", NamedTextColor.WHITE));
			proF.setPlayerProfile(JMChat_Proxy.inst.proxy.getPlayer(args[1]).get(), ":admin:");
		}
		
	}

}
