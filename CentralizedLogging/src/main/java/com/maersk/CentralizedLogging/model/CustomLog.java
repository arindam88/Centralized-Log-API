package com.maersk.CentralizedLogging.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="customlog")
public class CustomLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="application_id")
	private long applicationId;
	@Id
	@Column(name="trace_id")
	private long traceId;
	@Column(name="severity")
	private int severity;
	@Column(name="timestamp")
	private String timestamp;
	@Column(name="message")
	private String message;
	@Column(name="component_name")
	private String componentName;
	@Column(name="request_id")
	private int requestId;
	

}
