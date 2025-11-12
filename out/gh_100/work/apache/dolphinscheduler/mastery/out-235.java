package org.apache.dolphinscheduler.api.controller;

import org.apache.dolphinscheduler.api.enums.ExecuteType;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.service.ExecutorService;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.dao.entity.User;
import io.swagger.annotations.*;
import org.apache.dolphinscheduler.common.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.Map;

@Api(tags = "PROCESS_INSTANCE_EXECUTOR_TAG", position = 1)
@RestController
@RequestMapping("projects/{projectName}/executors")
public class ExecutorController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorController.class);

    @Autowired
    private ExecutorService execService;

    @ApiOperation(value = "startProcessInstance", notes = "RUN_PROCESS_INSTANCE_NOTES")
    @PostMapping(value = "start-process-instance")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({ @ApiImplicitParam(required = true, name = "processDefinitionId", value = "PROCESS_DEFINITION_ID", dataType = "Int", example = "100"), @ApiImplicitParam(required = true, name = "scheduleTime", value = "SCHEDULE_TIME", dataType = "String"), @ApiImplicitParam(required = true, name = "failureStrategy", value = "FAILURE_STRATEGY", dataType = "FailureStrategy"), @ApiImplicitParam(dataType = "String", name = "startNodeList", value = "START_NODE_LIST"), @ApiImplicitParam(name = "taskDependType", value = "TASK_DEPEND_TYPE", dataType = "TaskDependType"), @ApiImplicitParam(name = "execType", value = "COMMAND_TYPE", dataType = "CommandType"), @ApiImplicitParam(required = true, name = "warningType", value = "WARNING_TYPE", dataType = "WarningType"), @ApiImplicitParam(required = true, dataType = "Int", example = "100", name = "warningGroupId", value = "WARNING_GROUP_ID"), @ApiImplicitParam(dataType = "String", name = "receivers", value = "RECEIVERS"), @ApiImplicitParam(dataType = "String", name = "receiversCc", value = "RECEIVERS_CC"), @ApiImplicitParam(name = "runMode", value = "RUN_MODE", dataType = "RunMode"), @ApiImplicitParam(required = true, name = "processInstancePriority", value = "PROCESS_INSTANCE_PRIORITY", dataType = "Priority"), @ApiImplicitParam(dataType = "String", name = "workerGroup", value = "WORKER_GROUP", example = "default"), @ApiImplicitParam(dataType = "Int", example = "100", name = "timeout", value = "TIMEOUT") })
    public Result startProcessInstance(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @ApiParam(name = "projectName", value = "PROJECT_NAME", required = true) @PathVariable String projectName, @RequestParam(value = "processDefinitionId") int processDefinitionId, @RequestParam(value = "scheduleTime", required = false) String scheduleTime, @RequestParam(required = true, value = "failureStrategy") FailureStrategy failureStrategy, @RequestParam(required = false, value = "startNodeList") String startNodeList, @RequestParam(required = false, value = "taskDependType") TaskDependType taskDependType, @RequestParam(required = false, value = "execType") CommandType execType, @RequestParam(required = true, value = "warningType") WarningType warningType, @RequestParam(required = false, value = "warningGroupId") int warningGroupId, @RequestParam(required = false, value = "receivers") String receivers, @RequestParam(required = false, value = "receiversCc") String receiversCc, @RequestParam(required = false, value = "runMode") RunMode runMode, @RequestParam(required = false, value = "processInstancePriority") Priority processInstancePriority, @RequestParam(required = false, value = "workerGroup", defaultValue = "default") String workerGroup, @RequestParam(required = false, value = "timeout") Integer timeout) {
        try {
            logger.info("login user {}, start process instance, project name: {}, process definition id: {}, schedule time: {}, " + "failure policy: {}, node name: {}, node dep: {}, notify type: {}, " + "notify group id: {},receivers:{},receiversCc:{}, run mode: {},process instance priority:{}, workerGroup: {}, timeout: {}", loginUser.getUserName(), projectName, processDefinitionId, scheduleTime, failureStrategy, startNodeList, taskDependType, warningType, workerGroup, receivers, receiversCc, runMode, processInstancePriority, workerGroup, timeout);
            if (timeout == null) {
                timeout = Constants.MAX_TASK_TIMEOUT;
            }
            Map<String, Object> result = execService.execProcessInstance(loginUser, projectName, processDefinitionId, scheduleTime, execType, failureStrategy, startNodeList, taskDependType, warningType, warningGroupId, receivers, receiversCc, runMode, processInstancePriority, workerGroup, timeout);
            return returnDataList(result);
        } catch (Exception e) {
            logger.error(Status.START_PROCESS_INSTANCE_ERROR.getMsg(), e);
            return error(Status.START_PROCESS_INSTANCE_ERROR.getCode(), Status.START_PROCESS_INSTANCE_ERROR.getMsg());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "execute", notes = "EXECUTE_ACTION_TO_PROCESS_INSTANCE_NOTES")
    @ApiImplicitParams({ @ApiImplicitParam(required = true, dataType = "Int", example = "100", name = "processInstanceId", value = "PROCESS_INSTANCE_ID"), @ApiImplicitParam(required = true, name = "executeType", value = "EXECUTE_TYPE", dataType = "ExecuteType") })
    @PostMapping(value = "/execute")
    public Result execute(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @ApiParam(name = "projectName", value = "PROJECT_NAME", required = true) @PathVariable String projectName, @RequestParam("processInstanceId") Integer processInstanceId, @RequestParam("executeType") ExecuteType executeType) {
        try {
            logger.info("execute command, login user: {}, project:{}, process instance id:{}, execute type:{}", loginUser.getUserName(), projectName, processInstanceId, executeType);
            Map<String, Object> result = execService.execute(loginUser, projectName, processInstanceId, executeType);
            return returnDataList(result);
        } catch (Exception e) {
            logger.error(Status.EXECUTE_PROCESS_INSTANCE_ERROR.getMsg(), e);
            return error(Status.EXECUTE_PROCESS_INSTANCE_ERROR.getCode(), Status.EXECUTE_PROCESS_INSTANCE_ERROR.getMsg());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "startCheckProcessDefinition", notes = "START_CHECK_PROCESS_DEFINITION_NOTES")
    @ApiImplicitParams({ @ApiImplicitParam(required = true, name = "processDefinitionId", value = "PROCESS_DEFINITION_ID", dataType = "Int", example = "100") })
    @PostMapping(value = "/start-check")
    public Result startCheckProcessDefinition(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @RequestParam(value = "processDefinitionId") int processDefinitionId) {
        logger.info("login user {}, check process definition {}", loginUser.getUserName(), processDefinitionId);
        try {
            Map<String, Object> result = execService.startCheckByProcessDefinedId(processDefinitionId);
            return returnDataList(result);
        } catch (Exception e) {
            logger.error(Status.CHECK_PROCESS_DEFINITION_ERROR.getMsg(), e);
            return error(Status.CHECK_PROCESS_DEFINITION_ERROR.getCode(), Status.CHECK_PROCESS_DEFINITION_ERROR.getMsg());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiIgnore
    @ApiOperation(value = "getReceiverCc", notes = "GET_RECEIVER_CC_NOTES")
    @ApiImplicitParams({ @ApiImplicitParam(required = true, name = "processDefinitionId", value = "PROCESS_DEFINITION_ID", dataType = "Int", example = "100"), @ApiImplicitParam(required = true, dataType = "Int", example = "100", name = "processInstanceId", value = "PROCESS_INSTANCE_ID") })
    @GetMapping(value = "/get-receiver-cc")
    public Result getReceiverCc(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser, @RequestParam(value = "processDefinitionId", required = false) Integer processDefinitionId, @RequestParam(required = false, value = "processInstanceId") Integer processInstanceId) {
        logger.info("login user {}, get process definition receiver and cc", loginUser.getUserName());
        try {
            Map<String, Object> result = execService.getReceiverCc(processDefinitionId, processInstanceId);
            return returnDataList(result);
        } catch (Exception e) {
            logger.error(Status.QUERY_RECIPIENTS_AND_COPYERS_BY_PROCESS_DEFINITION_ERROR.getMsg(), e);
            return error(Status.QUERY_RECIPIENTS_AND_COPYERS_BY_PROCESS_DEFINITION_ERROR.getCode(), Status.QUERY_RECIPIENTS_AND_COPYERS_BY_PROCESS_DEFINITION_ERROR.getMsg());
        }
    }
}
