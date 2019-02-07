package com.packtpub.mmj.conductor.client;

import com.netflix.conductor.common.metadata.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Ideally you would like to use the Conductor Client APIs (preferred)
 * instead of using the REST calls to Conductor Server
 */
@Component
public class ConductorServerProvider {

  @Autowired
  private RestTemplate restTemplate;
  
  @Value("${conductor.server.uri}")
  private String conductorURI;

  public static final Logger LOGGER = LoggerFactory.getLogger(ConductorServerProvider.class);

  public ResponseEntity<String> getTaskDefs() {
    final ResponseEntity<String> response = restTemplate.exchange(
        conductorURI.concat("/metadata/taskdefs"), HttpMethod.GET, null, String.class);
    LOGGER.info("getTaskDefs response: {}", response.getBody());
    return response;
  }

  public ResponseEntity<String> getWorkflowDefs() {
    final ResponseEntity<String> response = restTemplate.exchange(
        conductorURI.concat("/metadata/workflow"), HttpMethod.GET, null, String.class);
    LOGGER.info("getWorkflowDefs response: {}", response.getBody());
    return response;
  }


  public ResponseEntity<String> createEventHandler(EventHandler eventHandler) {
    final ResponseEntity<String> response = restTemplate.exchange(
        conductorURI.concat("event"), HttpMethod.POST, new HttpEntity<EventHandler>(eventHandler), String.class);
    LOGGER.info("getTaskDefs response: {}", response.getBody());
    return response;
    
  }  
  
}
