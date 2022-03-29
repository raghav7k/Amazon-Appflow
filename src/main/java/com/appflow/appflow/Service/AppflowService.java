package com.appflow.appflow.Service;

import com.appflow.appflow.Repository.AppflowRepository;
import com.appflow.appflow.Repository.S3AppflowRepository;
import com.appflow.appflow.Repository.SalesforceAppflowRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.appflow.model.ListFlowsResponse;

@Service
public class AppflowService {

    private final AppflowRepository appflowRepository;
    private final SalesforceAppflowRepository salesforceAppflowRepository;
    private final S3AppflowRepository s3AppflowRepository;


    public  AppflowService(AppflowRepository appflowRepository,
                           SalesforceAppflowRepository salesforceAppflowRepository,
                           S3AppflowRepository s3AppflowRepository) {

        this.appflowRepository = appflowRepository;
        this.salesforceAppflowRepository = salesforceAppflowRepository;
        this.s3AppflowRepository = s3AppflowRepository;
    }

    public Object createS3Flow (String requestBody) {
        try {
            JSONObject body = (JSONObject) new JSONParser().parse(requestBody);
            return s3AppflowRepository.createS3Flow(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object listFlows() {
        ListFlowsResponse listFlowsResponse = appflowRepository.listFlows();
        JSONArray response = new JSONArray();
        listFlowsResponse.flows().forEach( x -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ARN", x.flowArn());
            jsonObject.put("Name", x.flowName());
            jsonObject.put("Description", x.description());
            jsonObject.put("TriggerType", x.triggerType());
            jsonObject.put("CreatedAt", x.createdAt());
            jsonObject.put("flowStatus", x.flowStatus());
            jsonObject.put("sourceConnectorType", x.sourceConnectorTypeAsString());
            jsonObject.put("destinationConnectorTypeA", x.destinationConnectorTypeAsString());
            response.add(jsonObject);
        });
        return response;

    }

    public Object startFlow(String flowName) {
        return appflowRepository.startFlow(flowName);
    }

    public Object createSalesforceFlow(String requstBody) {
        try {
            JSONObject body = (JSONObject) new JSONParser().parse(requstBody);
            return salesforceAppflowRepository.createSalesforceFlow(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
