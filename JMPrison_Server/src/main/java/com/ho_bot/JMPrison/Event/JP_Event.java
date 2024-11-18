package com.ho_bot.JMPrison.Event;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.ho_bot.JMPrison.ServerMain;
import com.ho_bot.JMPrison.File.PlayerFile;
import com.ho_bot.JMPrison.Util.EtcUtil;
import com.ho_bot.JMPrison.Util.PrisonUtil;
import com.ho_bot.JMPrison.Util.VarUtil;
import com.ho_bot.JMPrison.Util.packetUtil;

import io.lumine.mythic.bukkit.events.MythicMobInteractEvent;

public class JP_Event implements Listener{
	
	public static ServerMain plugin;

	public static void setPlugin(ServerMain MainPlugin)
    {
        plugin = MainPlugin;
    }
	
	private PlayerFile playerF = new PlayerFile();
	private PrisonUtil prisonU = new PrisonUtil();
	private packetUtil packetU = new packetUtil();
	private EtcUtil etc = new EtcUtil();
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {
		if(VarUtil.prisonname.equalsIgnoreCase(VarUtil.servername)) {
			UUID uuid = event.getPlayer().getUniqueId();
			if(VarUtil.prison_players.contains(uuid)) return;
			if(playerF.getPrison(uuid)<=0) return;
			playerF.setPrison(uuid, playerF.getPrison(uuid));
			prisonU.sendPrison(event.getPlayer(), 0, false);
			return;
		}
		else {
			if(event.getPlayer().isOp()) return;
			new BukkitRunnable() {
				@Override
				public void run() {
					packetU.sendPacket(event.getPlayer(), Arrays.asList("hasPrison", event.getPlayer().getUniqueId().toString()));
				}
			}.runTaskLater(ServerMain.inst, 10L);
			return;
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if(!VarUtil.prison_players.contains(event.getPlayer().getUniqueId())) return;
		UUID uuid = event.getPlayer().getUniqueId();
		VarUtil.prison_players.remove(uuid);
	}
	
	@EventHandler
    public void PrisonBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(!VarUtil.prison_players.contains(p.getUniqueId())) return;
        if(e.getBlock().getType() != Material.DEEPSLATE_COAL_ORE) return;
        
        e.setCancelled(true);
        
        Location location = e.getBlock().getLocation();
        if(prisonU.isSpawnBlock(location)) {
        	e.getBlock().setType(Material.AIR);
        	prisonU.SpawnBlock(location);
        	playerF.miusPrison(p.getUniqueId(), 1);
        	return;
        }
        location.getWorld().dropItemNaturally(location, new ItemStack(Material.COAL));
    }
	
	@EventHandler
	public void onBlockBreak(PlayerInteractEvent event) {
		if(VarUtil.click_==null) return;
		if(!VarUtil.click_.containsKey(event.getPlayer().getUniqueId())) return;
		if(event.getAction()==Action.LEFT_CLICK_BLOCK) {
			VarUtil.click_left.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
			event.getPlayer().sendMessage(event.getClickedBlock().getLocation() + " : LeftLocation");
		}
		if(event.getAction()==Action.RIGHT_CLICK_BLOCK) {
			VarUtil.click_right.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
			event.getPlayer().sendMessage(event.getClickedBlock().getLocation() + " : RightLocation");
		}
	}
    @EventHandler
    public void PrisonNPCRightClick(MythicMobInteractEvent e) {
        Player p = e.getPlayer();
        if(!VarUtil.prison_players.contains(p.getUniqueId())) return;

        int amount = getHasItemStackAmount(p, new ItemStack(Material.COAL));
        for (int i = 0; i < amount; i++) {
            p.getInventory().removeItem(new ItemStack(Material.COAL));
        }

        playerF.miusPrison(p.getUniqueId(), amount);

        etc.sendSoundTitle(p, "\uE020", "석탄을 제출하여 " + amount + "초가 감소 되었습니다.", Sound.BLOCK_NOTE_BLOCK_BELL, 1.0F, 15, 35, 15);
    }
    
    private int getHasItemStackAmount(Player p, ItemStack item) {
        int itemCount = 0;
        ItemStack offhand = p.getInventory().getItemInOffHand();

        for (ItemStack playerItem : p.getInventory().getContents()) {
            if (playerItem != null && playerItem.isSimilar(item)) {
                itemCount += playerItem.getAmount();
            }
        }
        if (offhand.getType() != Material.AIR && offhand.isSimilar(item)) itemCount -= offhand.getAmount();

        return itemCount;
    }

}
