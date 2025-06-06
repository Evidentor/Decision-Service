package net.dimjasevic.karlo.fer.evidentor.decision_service.dmn;

import lombok.Getter;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class DmnConfiguration {

    @Value("${dmn.decision.room-access.id}")
    private String roomAccessDecisionId;

    @Value("${dmn.decision.room-access.name}")
    private String roomAccessDecisionName;

    @Bean
    public DmnEngine dmnEngine() {
        return DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
    }

    public static DmnConfiguration create(String roomAccessDecisionId, String roomAccessDecisionName) {
        DmnConfiguration configuration = new DmnConfiguration();
        configuration.roomAccessDecisionId = roomAccessDecisionId;
        configuration.roomAccessDecisionName = roomAccessDecisionName;
        return configuration;
    }
}
