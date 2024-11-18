package com.ho_bot.JMPrison.Util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.ho_bot.JMPrison.ServerMain;
import com.ho_bot.JMPrison.File.PlayerFile;
import com.ho_bot.JMPrison.File.RoomFile;
import com.ho_bot.JMPrison.class_.SoloRoom;
import com.ho_bot.JMPrison.class_.SpawnBlock;

public class PrisonUtil {
	
	private PlayerFile playerF = new PlayerFile();
	private RoomFile roomF = new RoomFile();
	private Random rand = new Random();
	private packetUtil packetU = new packetUtil();
	private EtcUtil etc = new EtcUtil();
	
	public void sendPrison(Player player) {
		packetU.sendPacket(player, Arrays.asList("isPrison", player.getUniqueId().toString(), VarUtil.servername, etc.locString(player.getLocation())));
	}
	
	public void sendPrison(Player player, int time, boolean invClear) {
		if(!enterSolo(player.getUniqueId())) {
			player.teleport(VarUtil.prison_loc);
		}
		//player.sendMessage("감옥시간 추가됨");
		if(!VarUtil.prison_players.contains(player.getUniqueId())) {
			if(invClear) {
				playerF.setInventory(player.getUniqueId());
				new BukkitRunnable() {
					
					@Override
					public void run() {
						player.getInventory().clear();
						
						ItemStack item = new ItemStack(Material.IRON_PICKAXE);
						ItemMeta item_m = item.getItemMeta();
						item_m.setUnbreakable(true);
						item_m.setDisplayName(ChatColor.WHITE+"[ 노력의 산물 ]");
						item_m.setLore(Arrays.asList(ChatColor.GRAY+"이것을 위해", ChatColor.GRAY+"어느 한사람의 하루가 사라졌다"));
						item.setItemMeta(item_m);
						
						player.getInventory().addItem(item);
						
					}
				}.runTaskLater(ServerMain.inst, 20L);
			}
			VarUtil.prison_players.add(player.getUniqueId());
		}
		playerF.addPrison(player.getUniqueId(), time);
	}
	
	public boolean enterSolo(UUID playeruuid) {
		if(VarUtil.solo_prison==null) return false;
		for(Entry<SoloRoom, UUID> entry : VarUtil.solo_prison.entrySet()) {
			if(playeruuid.equals(entry.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	public SoloRoom getSolo(UUID playeruuid) {
		if(VarUtil.solo_prison==null) return null;
		for(Entry<SoloRoom, UUID> entry : VarUtil.solo_prison.entrySet()) {
			if(playeruuid.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public SoloRoom getShuffleRoom() {
		
		List<SoloRoom> sololist = roomF.getRoomList();
		Collections.shuffle(sololist);
		
		if(VarUtil.solo_prison.containsKey(sololist.get(0))) {
			return getShuffleRoom();
		}
		return sololist.get(0);
		
	}
	
	public void SpawnBlock(Location loc) {
		for(Entry<Integer, SpawnBlock> entry : roomF.getSpawnList().entrySet()) {
			if(etc.isInLoc(entry.getValue().loc1, entry.getValue().loc2, loc)) {
				loc.getWorld().setBlockData(getRandomLocationBetween(entry.getValue().loc1, entry.getValue().loc2), Material.DEEPSLATE_COAL_ORE.createBlockData());
			}
		}
	}
	
	public Location getRandomLocationBetween(Location loc1, Location loc2) {
        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());

        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());

        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        int randomX = getRandomInRange(minX, maxX);
        int randomY = getRandomInRange(minY+1, maxY);
        int randomZ = getRandomInRange(minZ, maxZ);

        return new Location(loc1.getWorld(), randomX, randomY, randomZ);
    }

    private int getRandomInRange(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
	
	public boolean isSpawnBlock(Location loc) {
		for(Entry<Integer, SpawnBlock> entry : roomF.getSpawnList().entrySet()) {
			if(etc.isInLoc(entry.getValue().loc1, entry.getValue().loc2, loc)) {
				return true;
			}
		}
		return false;
	}

}
