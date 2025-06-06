package net.dimjasevic.karlo.fer.evidentor.decision_service.decision;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.dimjasevic.karlo.fer.evidentor.decision_service.dmn.DmnConfiguration;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.CheckAccessRequest;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.CheckAccessResponse;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.DecisionServiceGrpc;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class RoomAccessDecisionService extends DecisionServiceGrpc.DecisionServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomAccessDecisionService.class);

    private DmnEngine dmnEngine;
    private DmnConfiguration dmnConfiguration;

    @Override
    public void checkAccess(CheckAccessRequest request, StreamObserver<CheckAccessResponse> responseObserver) {
        LOGGER.info(
                "Check access: deviceId={}, cardId={}, roomId={}",
                request.getDeviceId(), request.getCardId(), request.getRoomId()
        );

        // Check database - TODO: Call database
        boolean userExists = true;
        boolean hasRoomAccess = true;

        // Prepare camunda input parameters
        // TODO: Extract this into a separate class, since this is gRPC service
        Map<String, Object> variables = new HashMap<>();
        variables.put("userExists", userExists);
        variables.put("hasRoomAccess", hasRoomAccess);

        // Execute DMN
        DmnDecision decision = dmnEngine.parseDecision(
                dmnConfiguration.getRoomAccessDecisionId(),
                getClass().getResourceAsStream(String.format("/%s", dmnConfiguration.getRoomAccessDecisionName()))
        );
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        boolean accessGranted = result.getSingleResult().getSingleEntry();

        // Send the response
        CheckAccessResponse response = CheckAccessResponse.newBuilder().setAccessGranted(accessGranted).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
