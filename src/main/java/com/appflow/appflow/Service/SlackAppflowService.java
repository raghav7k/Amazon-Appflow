package com.appflow.appflow.Service;

import com.appflow.appflow.Repository.SlackAppflowRepository;
import org.springframework.stereotype.Service;

@Service
public class SlackAppflowService {

    private final SlackAppflowRepository slackAppflowRepository;
    public SlackAppflowService(SlackAppflowRepository slackAppflowRepository) {

        this.slackAppflowRepository = slackAppflowRepository;
    }

    public Object createSlackFlow() {
        return slackAppflowRepository.createSlackFlow();
    }
}
