package net.dimjasevic.karlo.fer.evidentor.decision_service.api.v1.service;

import io.grpc.stub.StreamObserver;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.CheckAccessRequest;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.CheckAccessResponse;
import net.dimjasevic.karlo.fer.evidentor.domain.proto.DecisionServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DecisionService extends DecisionServiceGrpc.DecisionServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionService.class);

    @Override
    public void checkAccess(CheckAccessRequest request, StreamObserver<CheckAccessResponse> responseObserver) {
        LOGGER.info(
                "Check access: deviceId={}, cardId={}, roomId={}",
                request.getDeviceId(), request.getCardId(), request.getRoomId()
        );

        // TODO: Camunda processing

        // Send the response
        CheckAccessResponse response = CheckAccessResponse.newBuilder().setAccessGranted(true).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
