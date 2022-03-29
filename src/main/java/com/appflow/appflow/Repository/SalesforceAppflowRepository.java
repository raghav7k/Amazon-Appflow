package com.appflow.appflow.Repository;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.appflow.AppflowClient;
import software.amazon.awssdk.services.appflow.model.*;

import java.util.Collections;

@Repository
public class SalesforceAppflowRepository {

    public Object createSalesForceConnection(String accessToken, String refreshToken) {
        AppflowClient appflowClient = AppflowClient.create();


        SalesforceConnectorProfileProperties salesforceConnectorProfileProperties = SalesforceConnectorProfileProperties.builder()
                .instanceUrl("https://teradata.my.salesforce.com")
                .build();

        SalesforceConnectorProfileCredentials salesforceConnectorProfileCredentials = SalesforceConnectorProfileCredentials.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();

        ConnectorProfileProperties connectorProfileProperties = ConnectorProfileProperties.builder()
                .salesforce(salesforceConnectorProfileProperties)
                .build();


        ConnectorProfileCredentials connectorProfileCredentials = ConnectorProfileCredentials.builder()
                .salesforce(salesforceConnectorProfileCredentials)
                .build();

        ConnectorProfileConfig connectorProfileConfig = ConnectorProfileConfig.builder()
                .connectorProfileCredentials(connectorProfileCredentials)
                .connectorProfileProperties(connectorProfileProperties)
                .build();

        CreateConnectorProfileRequest createConnectorProfileRequest = CreateConnectorProfileRequest.builder()
                .connectorType(ConnectorType.SALESFORCE)
                .connectorProfileConfig(connectorProfileConfig)
                .build();

        return appflowClient.createConnectorProfile(createConnectorProfileRequest);

    }

    public Object createSalesforceFlow(JSONObject body) {

        AppflowClient appflowClient = AppflowClient.create();


        SalesforceSourceProperties sourceProperties = SalesforceSourceProperties.builder()
                // .object("pse__Proj__c")
                .object(body.getAsString("object"))
                .enableDynamicFieldUpdate(true)
                .build();

        SourceConnectorProperties sourceConnectorProperties = SourceConnectorProperties.builder()
                .salesforce(sourceProperties)
                .build();

        SourceFlowConfig sourceFlowConfig = SourceFlowConfig.builder()
                .connectorType(ConnectorType.SALESFORCE)
                .sourceConnectorProperties(sourceConnectorProperties)
                .connectorProfileName("TeradataSalesforce")
                .build();


        S3DestinationProperties s3DestinationProperties = S3DestinationProperties.builder()
                // .bucketName("aws-rag-dest")
                .bucketName(body.getAsString("destination"))
                .build();

        DestinationConnectorProperties destinationConnectorProperties = DestinationConnectorProperties.builder()
                .s3(s3DestinationProperties)
                .build();

        DestinationFlowConfig destinationFlowConfig = DestinationFlowConfig.builder()
                .connectorType(ConnectorType.S3)
                .destinationConnectorProperties(destinationConnectorProperties)
                .build();

        CreateFlowRequest createFlowRequest = CreateFlowRequest.builder()
                //.flowName("SalesforceToS3")
                //.description("Transferring the project data from salesforce to S3")
                .flowName(body.getAsString("flowName"))
                .description(body.getAsString("description"))
                .destinationFlowConfigList(destinationFlowConfig)
                .sourceFlowConfig(sourceFlowConfig)
                .triggerConfig(TriggerConfig.builder().triggerType(TriggerType.ON_DEMAND).build())
                .tasks(Task.builder().sourceFields().taskType(TaskType.MAP_ALL).taskProperties(Collections.emptyMap()).build())
                .build();

        try {
            appflowClient.createFlow(createFlowRequest);
            return "Created the flow successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to create the flow";
        }
    }
}
