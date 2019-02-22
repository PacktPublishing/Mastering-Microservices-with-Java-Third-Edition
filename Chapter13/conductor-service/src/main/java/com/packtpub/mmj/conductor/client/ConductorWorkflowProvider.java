package com.packtpub.mmj.conductor.client;

import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.packtpub.mmj.conductor.common.Constants;
import com.packtpub.mmj.conductor.domain.valueobject.Event;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConductorWorkflowProvider implements WorkflowProvider {

  public static final Logger LOGGER = LoggerFactory.getLogger(ConductorWorkflowProvider.class);

  @Autowired
  private WorkflowClient wfClient;
  @Autowired
  private TaskClient taskClient;

  @Override
  public void startWorkflow(final Event event, boolean isEvent) {
    LOGGER.info("startWorkflow method with parameter event [event: {}, isEvent: {}]", event,
        isEvent);
    final Map<String, Object> inputParamMap = new HashMap<>();
    LOGGER.info("Starting workflow... for correlationId: {}", event.getUserID());
    StartWorkflowRequest req = new StartWorkflowRequest();
    req.setVersion(1);
    req.setCorrelationId(event.getUserID());
    if (isEvent) {
      req.setName(Constants.EVENT_WF);
      inputParamMap.put(Constants.PAYLOAD, event);
    } else {
      req.setName(Constants.SAMPLE_WF);
      inputParamMap.put(Constants.EVENT, event);
    }
    req.setInput(inputParamMap);
    wfClient.startWorkflow(req);
    LOGGER.info("Workflow invocation complete... for correlationId: {}", event.getUserID());
  }

  @Override
  public Task getInProgressTask(String workflowId, String taskReferenceName) {
    return taskClient.getPendingTaskForWorkflow(workflowId, taskReferenceName);
  }

  @Override
  public void updateTask(TaskResult taskResult, String taskType) {
    taskClient.updateTask(taskResult, taskType);
  }

  @Override
  public Task getTaskDetails(String taskId) {
    return taskClient.getTaskDetails(taskId);
  }
}
