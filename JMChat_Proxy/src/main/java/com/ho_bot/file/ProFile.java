package com.ho_bot.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.ho_bot.main.JMChat_Proxy;
import com.velocitypowered.api.proxy.Player;

public class ProFile {
	
	public void createPlayerProfile(Player player) {
		try {
			Path folderPath = JMChat_Proxy.inst.dataDir.resolve("player");
			if (!Files.exists(folderPath)) {
	        	try {
					Files.createDirectories(folderPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
			
	        Path filePath = JMChat_Proxy.inst.dataDir.resolve("player//"+player.getUniqueId()+".yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) {
	            file.createNewFile();
	            //System.out.println("[JMP] time.yml 파일 생성");
	            
	            // Write list to YAML file
	            try (FileWriter writer = new FileWriter(file, true)) { // true: 내용을 추가
	                writer.write("profile: \":NONE:\"\n");
	                //System.out.println("[JMP] 초기값 설정완료");
	            }
	        }
	        
	        //System.out.println("[JMP] time 파일이 로드되었습니다:");
	        //System.out.println(list);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void setPlayerProfile(Player player, String profile) {
		try {
			Path folderPath = JMChat_Proxy.inst.dataDir.resolve("player");
			if (!Files.exists(folderPath)) return;
			
	        Path filePath = JMChat_Proxy.inst.dataDir.resolve("player//"+player.getUniqueId()+".yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) return;
	        
	        try (FileWriter writer = new FileWriter(file, false)) { // true: 내용을 추가
                writer.write("profile: \""+profile+"\"\n");
                //System.out.println("[JMP] 시간 설정완료");
            }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public String getPlayerProfile(Player player) {
		try {
			Path folderPath = JMChat_Proxy.inst.dataDir.resolve("player");
			if (!Files.exists(folderPath)) {
				return null;
			}
			
	        Path filePath = JMChat_Proxy.inst.dataDir.resolve("player//"+player.getUniqueId()+".yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) {
	        	return null;
	        }
	        // 읽기
	        Map<String, Object> list = new HashMap<>();
	        Yaml yaml = new Yaml();
	        try (FileReader reader = new FileReader(file)) {
	        	list = yaml.load(reader);
	        }
	        return (String) list.get("profile");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return null;
	}

}
