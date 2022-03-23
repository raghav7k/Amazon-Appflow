package com.appflow.appflow.Repository;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.appflow.AppflowClient;
import software.amazon.awssdk.services.appflow.model.*;

import java.util.Collections;

@Repository
public class S3AppflowRepository {

    public Object createS3Flow(JSONObject body) {
        AppflowClient appflowClient = AppflowClient.builder().region(Region.US_WEST_2).build();

        S3InputFormatConfig s3InputFormatConfig = S3InputFormatConfig.builder()
                .s3InputFileType(S3InputFileType.CSV).build();

        S3SourceProperties s3SourceProperties = S3SourceProperties.builder()
                //.bucketName("aws-rag-src")
                .bucketName(body.getAsString("source"))
                .s3InputFormatConfig(s3InputFormatConfig)
                //.bucketPrefix("cust")
                .bucketPrefix(body.getAsString("prefix"))
                .build();

        SourceConnectorProperties sourceConnectorProperties = SourceConnectorProperties.builder()
                .s3(s3SourceProperties)
                .build();

        SourceFlowConfig sourceFlowConfig = SourceFlowConfig.builder().
                connectorType(ConnectorType.S3)
                .sourceConnectorProperties(sourceConnectorProperties)
                .build();

        S3DestinationProperties s3DestinationProperties = S3DestinationProperties.builder()
                //.bucketName("aws-rag-dest")
                .bucketName(body.getAsString("destination"))
                .s3OutputFormatConfig(
                        S3OutputFormatConfig.builder()
                                .fileType(FileType.CSV)
                                .build())
                .build();

        DestinationConnectorProperties destinationConnectorProperties = DestinationConnectorProperties.builder()
                .s3(s3DestinationProperties)
                .build();

        DestinationFlowConfig destinationFlowConfig = DestinationFlowConfig.builder()
                .connectorType(ConnectorType.S3)
                .destinationConnectorProperties(destinationConnectorProperties)
                .build();

        TriggerConfig triggerConfig = TriggerConfig.builder()
                .triggerType(TriggerType.ON_DEMAND)
                .build();

        Task mapType = Task.builder()
                .taskType(TaskType.MAP_ALL)
                // .sourceFields("CUSTOMERID", "CUSTOMERNAME", "EMAIL", "CITY", "COUNTRY", "TERRITORY", "CONTACTFIRSTNAME", "CONTACTLASTNAME")
                .sourceFields()
                .taskProperties(Collections.emptyMap())
                .build();

        CreateFlowRequest createFlowRequest = CreateFlowRequest.builder()
                //.flowName("S3toS3")
                .flowName(body.getAsString("flowName"))
                .sourceFlowConfig(sourceFlowConfig)
                //.description("Transferring the data from S3 to S3")
                .description(body.getAsString("description"))
                .destinationFlowConfigList(destinationFlowConfig)
                .triggerConfig(triggerConfig)
                .tasks(mapType)
                .build();

        try {
            CreateFlowResponse response = appflowClient.createFlow(createFlowRequest);
            System.out.println(response);
            return "Created the flow successfully";
        } catch (Exception e) {
            return "Failed to create the flow";
        }
    }
}
