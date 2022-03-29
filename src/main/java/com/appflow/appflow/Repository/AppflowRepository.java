package com.appflow.appflow.Repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.appflow.AppflowClient;
import software.amazon.awssdk.services.appflow.model.ListFlowsRequest;
import software.amazon.awssdk.services.appflow.model.ListFlowsResponse;
import software.amazon.awssdk.services.appflow.model.StartFlowRequest;
import software.amazon.awssdk.services.appflow.model.StartFlowResponse;

@Repository
public class AppflowRepository {
    public AppflowRepository() {}

    public ListFlowsResponse listFlows() {
        AppflowClient appflowClient = AppflowClient.create();

        ListFlowsRequest listFlowsRequest = ListFlowsRequest.builder()
                        .build();

        ListFlowsResponse response = appflowClient.listFlows(listFlowsRequest);
        System.out.println(response);
        return response;
    }

    public Object startFlow(String flowName) {
        AppflowClient appflowClient = AppflowClient.create();

        StartFlowRequest startFlowRequest = StartFlowRequest.builder()
                .flowName(flowName)
                .build();

        StartFlowResponse startFlowResponse = appflowClient.startFlow(startFlowRequest);
        System.out.println(startFlowResponse);
        return "started the flow";
    }

}
