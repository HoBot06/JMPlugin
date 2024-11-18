package com.ho_bot.JMPrison.File;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;

import com.google.common.base.Charsets;
import com.ho_bot.JMPrison.ProxyMain;

public class ServerNameFile {
	
	public String prisonName = null;
	
	public void reloadServerName() {
		try {
	
	        Path filePath = ProxyMain.inst.dataDir.resolve("config.yml");
	        File file = filePath.toFile();
	        
	        if (!file.exists()) {
	            file.createNewFile();
	            System.out.println("[JMP] config.yml 파일 생성");
	            
	            //파일 쓰기
	            try (FileWriter writer = new FileWriter(file, true)) { // true: 내용을 추가
	                writer.write("Prison: lobby");
	                System.out.println("[JMP] 초기값 설정완료");
	            }
	        }
	        // 읽기
	        Map<String, Object> config = new HashMap<>();
	        Yaml yaml = new Yaml();
	        try (FileReader reader = new FileReader(file)) {
	            config = yaml.load(reader);
	        }
	        prisonName = (String) config.get("Prison");
	        //System.out.println("[JMP] Config 파일이 로드되었습니다:");
	        //System.out.println(config);
	        System.out.println("[JMP] 지정된 감옥 서버:");
	        System.out.println(prisonName);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
