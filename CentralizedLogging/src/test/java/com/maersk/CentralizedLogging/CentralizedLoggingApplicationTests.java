package com.maersk.CentralizedLogging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.maersk.CentralizedLogging.model.CustomLog;

@SpringBootTest
class CentralizedLoggingApplicationTests {
	
	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testAppendToFile() {
	List<CustomLog> myList1=Arrays.asList(
			new CustomLog(244556677,44556698,3,"26/04/2023 17:33:58","Exception in Thread Main","Microservice One",1),
			new CustomLog(244556680,44556699,3,"26/04/2023 17:43:58","Null Pointer Exception","Microservice Two",2)
									);
	ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8809/api/centralizedLogging/v1/append-to-file", myList1, String.class);
	   assertEquals(HttpStatus.OK, response.getStatusCode());
	   assertEquals("Log written to file successfully.",response.getBody());
	}

	
	@Test
	public void testPublishToKafka() {
	List<CustomLog> myList1=Arrays.asList(
			new CustomLog(244556677,44556798,3,"26/04/2023 17:33:58","Exception in Thread Main","Microservice Three",1),
			new CustomLog(244556680,44556799,3,"26/04/2023 17:43:58","Null Pointer Exception","Microservice Four",2)
									);
	ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8809/api/centralizedLogging/v1/publish-to-kafka", myList1, String.class);
	   assertEquals(HttpStatus.OK, response.getStatusCode());
	   assertEquals("Log written to Kafka topic successfully.",response.getBody());
	}
}
