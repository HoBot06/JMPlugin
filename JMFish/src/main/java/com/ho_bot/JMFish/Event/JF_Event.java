package com.ho_bot.JMFish.Event;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import com.ho_bot.JMFish.JF_Main;
public class JF_Event implements Listener{
	
	public static JF_Main plugin;

	public static void setPlugin(JF_Main MainPlugin)
    {
        plugin = MainPlugin;
    }
	
	public Random rand = new Random();
	
	@EventHandler
	public void onFish(PlayerFishEvent event) {
		if(event.getCaught() != null) {
			if(event.getState() == State.CAUGHT_FISH) {
				Item item = (Item) event.getCaught();
				item.setItemStack(new ItemStack(getFish()));
			}
		}
	}
	
	private Material getFish() {
		int r = rand.nextInt(4);
		if(r==0) return Material.SALMON;
		else if(r==1) return Material.COD;
		else if(r==2) return Material.PUFFERFISH;
		else return Material.TROPICAL_FISH;
		
	}

}
