package com.packtpub.mmj.conductor.resources;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskDef.RetryLogic;
import com.netflix.conductor.common.metadata.tasks.TaskDef.TimeoutPolicy;
import com.packtpub.mmj.conductor.client.ConductorServerProvider;
import com.packtpub.mmj.conductor.client.TaskDefType;
import com.packtpub.mmj.conductor.common.Constants;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TaskDefController {

  @Autowired
  private MetadataClient metaDataClient;

  @Autowired
  private ConductorServerProvider conductorServerProvider;

  public static final Logger LOGGER = LoggerFactory.getLogger(TaskDefController.class);

  @RequestMapping(method = RequestMethod.PUT, value = "/taskdef")
  public ResponseEntity<?> createTaskDefs(
      @RequestParam(value = "taskType", required = false, defaultValue = "ALL") final TaskDefType taskDefType) {
    final List<TaskDef> taskDefList = new ArrayList<>();
    TaskDef taskDef = null;
    switch (taskDefType) {
      case TASK_SAMPLE:
        taskDef = createTaskA();
        taskDefList.add(taskDef);
        break;
      case TASK_HTTP:
        taskDef = createTaskHttp();
        taskDefList.add(taskDef);
        break;
      case ALL:
        taskDef = createTaskA();
        taskDefList.add(taskDef);
        taskDef = createTaskHttp();
        taskDefList.add(taskDef);
        break;
      default:
        return new ResponseEntity<>("Invalid TaskDef Type", HttpStatus.BAD_REQUEST);
    }
    metaDataClient.registerTaskDefs(taskDefList);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/taskdef")
  public String getTaskDefs() {
    return conductorServerProvider.getTaskDefs().getBody();
  }

  private TaskDef createTaskA() {
    final TaskDef taskDef = new TaskDef(
        Constants.TASK_SAMPLE, String.format("%s task definition", Constants.TASK_SAMPLE));
    taskDef.setResponseTimeoutSeconds(1200);
    taskDef.setRetryLogic(RetryLogic.FIXED);
    taskDef.setRetryCount(1);
    taskDef.setRetryDelaySeconds(60);
    taskDef.setTimeoutPolicy(TimeoutPolicy.TIME_OUT_WF);
    taskDef.setTimeoutSeconds(3600);
    final List<String> inputKeys = new ArrayList<>(1);
    inputKeys.add(Constants.EVENT);
    taskDef.setInputKeys(inputKeys);
    return taskDef;
  }

  private TaskDef createTaskHttp() {
    final TaskDef taskDef = new TaskDef(Constants.TASK_HTTP,
        String.format("%s task definition", Constants.TASK_HTTP));
    taskDef.setResponseTimeoutSeconds(1200);
    taskDef.setRetryLogic(RetryLogic.FIXED);
    taskDef.setRetryCount(1);
    taskDef.setRetryDelaySeconds(60);
    taskDef.setTimeoutPolicy(TimeoutPolicy.TIME_OUT_WF);
    taskDef.setTimeoutSeconds(3600);
    List<String> keys = new ArrayList<>(1);
    keys.add("taskId");
    taskDef.setInputKeys(keys);
    keys = new ArrayList<>(1);
    keys.add("lastTaskId");
    taskDef.setOutputKeys(keys);
    return taskDef;
  }

}
