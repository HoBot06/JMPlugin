package com.ho_bot.JMPrison.File;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.ho_bot.JMPrison.ProxyMain;
import com.velocitypowered.api.proxy.Player;

public class PrisonPlayerFile {
	
	public enum DataType{
		prison("Prison"), 
		server("Server"), 
		loc("Location");
		
		final private String name; 
		public String getName() { 
			return name; 
		} 
		private DataType(String name){ 
			this.name = name; 
		} 
	}
	
	public void createPlayerPrison(Player player) {
		try {
			Path folderPath = ProxyMain.inst.dataDir.resolve("player");
			if (!Files.exists(folderPath)) {
	        	try {
					Files.createDirectories(folderPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
			
	        Path filePath = ProxyMain.inst.dataDir.resolve("player//"+player.getUniqueId()+".yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) {
	            file.createNewFile();
	            //System.out.println("[JMP] time.yml 파일 생성");
	            
	            // Write list to YAML file
	            try (FileWriter writer = new FileWriter(file, true)) { // true: 내용을 추가
	                writer.write(DataType.prison+": 0");
	                //System.out.println("[JMP] 초기값 설정완료");
	            }
	        }
	        
	        //System.out.println("[JMP] time 파일이 로드되었습니다:");
	        //System.out.println(list);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void setPlayerPrison(Player player, int time) {
		try {
			Path folderPath = ProxyMain.inst.dataDir.resolve("player");
			if (!Files.exists(folderPath)) return;
			
	        Path filePath = ProxyMain.inst.dataDir.resolve("player//"+player.getUniqueId()+".yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) return;
	        
	        int Time = time;
	        String Server = null;
	        String Loc = null;
	        
	        if(getPlayer(player, DataType.server)!=null) {
	        	Server = (String) getPlayer(player, DataType.server);
	        	//System.out.println("[JMP] " + Server);
	        }
	        if(getPlayer(player, DataType.loc)!=null) {
	        	Loc = (String) getPlayer(player, DataType.loc);
	        	//System.out.println("[JMP] " + Loc);
	        }
	        
	        try (FileWriter writer = new FileWriter(file, false)) { // true: 내용을 추가
                writer.write(DataType.prison+": "+Time+"\n");
                writer.write(DataType.server+": "+Server+"\n");
                writer.write(DataType.loc+": "+Loc+"\n");
                //System.out.println("[JMP] 시간 설정완료");
            }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void setPlayerPrison(Player player, String server, String loc) {
		try {
			Path folderPath = ProxyMain.inst.dataDir.resolve("player");
			if (!Files.exists(folderPath)) return;
			
	        Path filePath = ProxyMain.inst.dataDir.resolve("player//"+player.getUniqueId()+".yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) return;
	        
	        int Time = 0;
	        String Server = server;
	        String Loc = loc;
	        
	        if(getPlayer(player, DataType.prison)!=null) Time = (int) getPlayer(player, DataType.prison);
	        
	        try (FileWriter writer = new FileWriter(file, false)) { // true: 내용을 추가
                writer.write(DataType.prison+": "+Time+"\n");
                writer.write(DataType.server+": "+Server+"\n");
                writer.write(DataType.loc+": "+Loc+"\n");
                //System.out.println("[JMP] 시간 설정완료");
            }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public Object getPlayer(Player player, DataType datatype) {
		try {
			Path folderPath = ProxyMain.inst.dataDir.resolve("player");
			if (!Files.exists(folderPath)) {
				//System.out.println("1");
				return null;
			}
			
	        Path filePath = ProxyMain.inst.dataDir.resolve("player//"+player.getUniqueId()+".yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) {
	        	//System.out.println("2");
	        	return null;
	        }
	        // 읽기
	        Map<String, Object> list = new HashMap<>();
	        Yaml yaml = new Yaml();
	        try (FileReader reader = new FileReader(file)) {
	        	list = yaml.load(reader);
	        }
	        //System.out.println(list);
	        return list.get(datatype.name());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return null;
	}

}
