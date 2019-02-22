package com.packtpub.mmj.conductor.domain.service;

import com.netflix.conductor.common.metadata.events.EventHandler;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.packtpub.mmj.conductor.domain.valueobject.Event;
import org.springframework.http.ResponseEntity;

/**
 * @author Sourabh Sharma
 */
public interface ConductorService {

  void validateEvent(final Event event);

  void startApprovalWorkflow(final Event event, boolean isEvent);

  void validateEventHandler(final EventHandler eventHandler);

  ResponseEntity<String> createEventHandler(final EventHandler eventHandler);

  Task getTaskDetails(String taskId);
}
