package com.appflow.appflow.Controller;

import com.appflow.appflow.Repository.S3AppflowRepository;
import com.appflow.appflow.Service.AppflowService;
import com.appflow.appflow.Repository.DatabseFlowRepository;
import com.appflow.appflow.Service.SlackAppflowService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/appflow")

@RestController
public class AppflowController {


    private final AppflowService appflowService;
    private final SlackAppflowService slackAppflowService;

    public AppflowController(AppflowService appflowService,
                             SlackAppflowService slackAppflowService, DatabseFlowRepository jsonWork, S3AppflowRepository s3AppflowRepository) {

        this.appflowService = appflowService;
        this.slackAppflowService = slackAppflowService;
    }
    @PostMapping("s3")
    public Object createS3Flow(@RequestBody String requstBody) {
        return appflowService.createS3Flow(requstBody);
    }

    @GetMapping()
    public Object listAppflows() {
        return appflowService.listFlows();
    }

    @PutMapping("{flowName}")
    public Object startFlow(@PathVariable("flowName") String flowName) {
        return appflowService.startFlow(flowName);
    }

    @PostMapping("slack")
    public Object createSlackFlow() {
        return slackAppflowService.createSlackFlow();
    }

    @PostMapping("salesforce")
    public Object createSalesforceflow(@RequestBody String requestBody) {
        return appflowService.createSalesforceFlow(requestBody);
    }

    @PostMapping("database")
    public Object createDatabaseFlow(@RequestBody String requstBody) {
        return appflowService.createDatabaseFlow(requstBody);
    }


}
