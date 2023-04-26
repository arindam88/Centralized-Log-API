package com.maersk.CentralizedLogging.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maersk.CentralizedLogging.dao.repo.CustomLogRepository;
import com.maersk.CentralizedLogging.model.CustomLog;

@Service
public class CustomLogService {
	
	@Value("${app.configFilePathlocal}")
	private String configFilePathlocal;
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
    public CustomLogService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
	
	@Autowired
	CustomLogRepository customLogRepo;

	public void publishToKafka(List<CustomLog> logList) {
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	    for(CustomLog logger: logList)
    	{
        kafkaTemplate.send("my-logger-topic", gson.toJson(logger));
    	}
		
	}

	public void appendToFile(List<CustomLog> logList) throws IOException {
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        File file = new File(configFilePathlocal);
        FileWriter writer = new FileWriter(file,true);
    	for(CustomLog logger: logList)
    	{
    	writer.write(gson.toJson(logger));
        writer.write("\n");
    	}
        writer.close();
		
	}

	public void save(List<CustomLog> logList) {
		for(CustomLog logger: logList)
		{
			customLogRepo.save(logger);
		}
	}

}
