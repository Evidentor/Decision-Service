package net.dimjasevic.karlo.fer.evidentor.decision_service.decision;

import io.grpc.stub.StreamObserver;
import net.dimjasevic.karlo.fer.evidentor.decision_service.dmn.DmnConfiguration;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.CheckAccessRequest;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.CheckAccessResponse;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomAccessDecisionServiceTest {
    private RoomAccessDecisionService service;
    private StreamObserver<CheckAccessResponse> responseObserver;
    private final DmnConfiguration dmnConfiguration = DmnConfiguration.create(
            "Decision_1lgk6e3", "RoomAccessDecision.dmn"
    );

    public final List<CheckAccessResponse> responseObserverResponses = new LinkedList<>();
    public boolean responseObserverError = false;
    public boolean responseObserverCompleted = false;

    @BeforeEach
    void setUp() {
        DmnEngine engine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
        service = new RoomAccessDecisionService(engine, dmnConfiguration);
        responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(CheckAccessResponse checkAccessResponse) {
                responseObserverResponses.add(checkAccessResponse);
            }

            @Override
            public void onError(Throwable throwable) {responseObserverError = true;}

            @Override
            public void onCompleted() {responseObserverCompleted = true;}
        };
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCheckAccess() {
        // GIVEN
        CheckAccessRequest request = CheckAccessRequest.newBuilder()
                .setDeviceId(1)
                .setRoomId(1)
                .setCardId("00-11-22-33")
                .build();

        // WHEN
        service.checkAccess(request, responseObserver);

        // THEN
        assertTrue(responseObserverCompleted);
        assertFalse(responseObserverError);
        assertEquals(1, responseObserverResponses.size());
        assertTrue(responseObserverResponses.get(0).getAccessGranted());
        assertFalse(responseObserverResponses.get(0).hasReason());
    }
}