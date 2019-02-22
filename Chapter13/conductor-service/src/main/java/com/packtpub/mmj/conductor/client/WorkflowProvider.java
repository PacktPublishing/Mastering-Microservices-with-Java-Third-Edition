package com.packtpub.mmj.conductor.client;

import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.packtpub.mmj.conductor.domain.valueobject.Event;

public interface WorkflowProvider {

  void startWorkflow(Event event, boolean isEvent);

  Task getInProgressTask(String workflowId, String taskReferenceName);

  void updateTask(TaskResult taskResult, String taskType);

  Task getTaskDetails(String taskId);

}
