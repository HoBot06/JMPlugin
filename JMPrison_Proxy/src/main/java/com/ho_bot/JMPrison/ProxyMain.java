package com.ho_bot.JMPrison;

import java.lang.System.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.plugin.Plugin;

@Plugin(
	id = "jmprison_proxy",
	name = "JMPrison_Proxy",
	version = "1.0.0"
)

public class ProxyMain {

	@Inject
	private Logger logger;
	
	@Subscribe
	public void onProxy(PluginMessageEvent event) {
		
	}
}
