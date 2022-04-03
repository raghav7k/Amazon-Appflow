package com.appflow.appflow.Repository;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.appflow.AppflowClient;
import software.amazon.awssdk.services.appflow.model.*;

import java.util.Collections;

@Repository
public class SlackAppflowRepository {


    public Object createSlackFlow() {
        AppflowClient appflowClient = AppflowClient.create();



        /*
        SlackConnectorProfileProperties slackConnectorProfileProperties = SlackConnectorProfileProperties.builder()
                .instanceUrl("https://slack-4dq4085.slack.com")
                .build();

        ConnectorProfileProperties properties = ConnectorProfileProperties.builder()
                .slack(slackConnectorProfileProperties)
                .build();


        ConnectorOAuthRequest request = ConnectorOAuthRequest.builder()
                .redirectUri("https://us-west-2.console.aws.amazon.com/appflow/oauth")
                .build();

        SlackConnectorProfileCredentials slackConnectorProfileCredentials = SlackConnectorProfileCredentials.builder()
                .accessToken("xoxp-3168984408642-3171313570388-3192844788080-8dac237ce56c8e1a18d92e27825a5e86")
                .clientId("3168984408642.3169059850546")
                .clientSecret("6842e842b56feb201ab74ddf890e35fe")
                .oAuthRequest(request).build();


        ConnectorProfileCredentials connectorProfileCredentials = ConnectorProfileCredentials.builder()
                .slack(slackConnectorProfileCredentials)
                .build();


        ConnectorProfileConfig connectorProfileConfig = ConnectorProfileConfig.builder()
                .connectorProfileProperties(properties)
                .connectorProfileCredentials(connectorProfileCredentials)
                .build();

        CreateConnectorProfileRequest createConnectorProfileRequest = CreateConnectorProfileRequest.builder()
                .connectorProfileName("slack")
                .connectorType(ConnectorType.SLACK)
                .connectionMode(ConnectionMode.PUBLIC)
                .connectorProfileConfig(connectorProfileConfig)
                .build();


        CreateConnectorProfileResponse response = appflowClient.createConnectorProfile(createConnectorProfileRequest);
        System.out.println(response);
        */




        SlackSourceProperties slackSourceProperties = SlackSourceProperties.builder().object("Conversations").build();


        SourceConnectorProperties sourceConnectorProperties = SourceConnectorProperties.builder()
                .slack(slackSourceProperties)
                .build();

        SourceFlowConfig sourceFlowConfig = SourceFlowConfig.builder()
                .connectorType(ConnectorType.SLACK)
                .connectorProfileName("slackToS3")
                .sourceConnectorProperties(sourceConnectorProperties)
                .build();

        System.out.println(sourceFlowConfig);








        S3OutputFormatConfig s3OutputFormatConfig = S3OutputFormatConfig.builder()
                .fileType(FileType.CSV)
                .build();

        S3DestinationProperties s3DestinationProperties = S3DestinationProperties.builder()
                .bucketName("aws-rag-dest")
                .s3OutputFormatConfig(s3OutputFormatConfig)
                .bucketPrefix("software-development")
                .build();

        DestinationConnectorProperties destinationConnectorProperties = DestinationConnectorProperties.builder()
                .s3(s3DestinationProperties)
                .build();


        DestinationFlowConfig destinationFlowConfig = DestinationFlowConfig.builder()
                .connectorType(ConnectorType.S3)
                .destinationConnectorProperties(destinationConnectorProperties)
                .build();


        CreateFlowRequest createFlowRequest = CreateFlowRequest.builder()
                .flowName("SL")
                .description("Transferring the data from slack to s3")
                .destinationFlowConfigList(destinationFlowConfig)
                .sourceFlowConfig(sourceFlowConfig)
                .triggerConfig(TriggerConfig.builder().triggerType(TriggerType.ON_DEMAND).build())
                .tasks(Task.builder().sourceFields().taskType(TaskType.MAP_ALL).taskProperties(Collections.emptyMap()).build())
                .build();

        try {
            CreateFlowResponse response1 = appflowClient.createFlow(createFlowRequest);
            System.out.println(response1);
        } catch ( Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return "";
    }
}
