package com.maersk.CentralizedLogging.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maersk.CentralizedLogging.model.CustomLog;
import com.maersk.CentralizedLogging.service.CustomLogService;

@RestController
@RequestMapping("/api/centralizedLogging/v1")
public class CustomLogController {

	@Value("${app.configFilePathlocal}")
	private String configFilePathlocal;

	
	@Autowired
	CustomLogService customLogService;
	
	//API to write multiple logs at a time to a Flat File
	 @PostMapping("/append-to-file")
	    public ResponseEntity<String> appendToFile(@RequestBody List<CustomLog> logList) {
	        try {
	        	customLogService.appendToFile(logList);
	        	customLogService.save(logList);
	            return new ResponseEntity<>("Log written to file successfully.",HttpStatus.OK);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<>("Error in writing log to file.",HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        catch (DataAccessException e) {
	        	return new ResponseEntity<>("Error connecting DB.",HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	//API to write multiple logs at a time to a Kafka Topic
	 @PostMapping("/publish-to-kafka")
	    public ResponseEntity<String> publishToKafka(@RequestBody List<CustomLog> logList) {
		try {
		 customLogService.publishToKafka(logList);
		 customLogService.save(logList);
		return new ResponseEntity<>("Log written to Kafka topic successfully.",HttpStatus.OK);
	    }
		catch (DataAccessException e) {
        	return new ResponseEntity<>("Error connecting DB.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>("Error in writing log to Kafka topic.",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		}
	 
	 
}
