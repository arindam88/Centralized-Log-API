package com.maersk.CentralizedLogging.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.maersk.CentralizedLogging.model.CustomLog;

@Repository
public interface CustomLogRepository extends JpaRepository<CustomLog,Long> {
	
	List<CustomLog> findByApplicationId(long applicationId);
	List<CustomLog> findBySeverity(int severity);

}
