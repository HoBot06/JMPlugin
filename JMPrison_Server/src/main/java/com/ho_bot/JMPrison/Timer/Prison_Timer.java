package com.ho_bot.JMPrison.Timer;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.ho_bot.JMPrison.ServerMain;
import com.ho_bot.JMPrison.File.PlayerFile;
import com.ho_bot.JMPrison.Util.EtcUtil;
import com.ho_bot.JMPrison.Util.PrisonUtil;
import com.ho_bot.JMPrison.Util.VarUtil;
import com.ho_bot.JMPrison.Util.packetUtil;
import com.ho_bot.JMPrison.class_.SoloRoom;

public class Prison_Timer extends BukkitRunnable{
	
	private EtcUtil etc = new EtcUtil();
	private PlayerFile playerF = new PlayerFile();
	private packetUtil packetU = new packetUtil();
	private PrisonUtil prisonU = new PrisonUtil();

	@Override
	public void run() {
		
		if(VarUtil.prison_players==null) return;
		
		for(UUID uuid : VarUtil.prison_players) {
			Player player = Bukkit.getPlayer(uuid);
			if(playerF.getPrison(uuid) <= 0) {
				playerF.setPrison(uuid, 0);
				VarUtil.prison_players.remove(uuid);
				player.getInventory().clear();
				for(Entry<Integer, ItemStack> entry : playerF.getInventory(uuid).entrySet()) {
					player.getInventory().setItem(entry.getKey(), entry.getValue());
				}
				playerF.clearInventory(uuid);
				packetU.sendPacket(player, Arrays.asList("quitPrison", player.getUniqueId().toString()));
				continue;
			}
			if(playerF.getPrison(uuid)>=5400) {
				if(!prisonU.enterSolo(uuid)) {
					SoloRoom room = prisonU.getShuffleRoom();
					VarUtil.solo_prison.put(room, uuid);
					new BukkitRunnable() {
						@Override
						public void run() {
							player.teleport(room.loc);
						}
					}.runTaskLater(ServerMain.inst, 20L);
				}
			}
			else {
				if(prisonU.enterSolo(uuid)) {
					player.teleport(VarUtil.prison_loc);
					VarUtil.solo_prison.remove(prisonU.getSolo(uuid));
				}
			}
			player.sendActionBar(etc.colorString("                                                                                            &6[ &f감옥 &6] &f남은 시간: " + etc.getTimeTrans(playerF.getPrison(uuid))));
			playerF.miusPrison(uuid, 1);
			packetU.sendPacket(player, Arrays.asList("returnPrison", player.getUniqueId().toString(), String.valueOf(playerF.getPrison(uuid))));
		}
		
	}

}
