package elice.chargingstationbackend.business.controller;

import elice.chargingstationbackend.business.entity.ApprovalRequest;
import elice.chargingstationbackend.business.service.ApprovalRequestService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/approval-requests")
@RequiredArgsConstructor
public class ApprovalRequestController {

    private final ApprovalRequestService approvalRequestService;

    @PostMapping("/{ownerId}")
    public ResponseEntity<ApprovalRequest> createApprovalRequest(
        @PathVariable Long ownerId, @RequestBody Map<String, String> requestBody) {
        String requestType = requestBody.get("requestType");
        String businessCertificate = requestBody.get("businessCertificate");
        String identityProof = requestBody.get("identityProof");

        ApprovalRequest approvalRequest = approvalRequestService.createApprovalRequest(
            ownerId, requestType, businessCertificate, identityProof
        );
        return ResponseEntity.ok(approvalRequest);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<ApprovalRequest> updateApprovalRequest(
        @PathVariable Long requestId, @RequestBody ApprovalRequest updatedRequest) {
        ApprovalRequest approvalRequest = approvalRequestService.updateApprovalRequest(requestId, updatedRequest);
        return ResponseEntity.ok(approvalRequest);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<ApprovalRequest>> getApprovalRequestsByOwnerId(@PathVariable Long ownerId) {
        List<ApprovalRequest> requests = approvalRequestService.getApprovalRequestsByOwnerId(ownerId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<ApprovalRequest> approveRequest(@PathVariable Long requestId) {
        ApprovalRequest approvedRequest = approvalRequestService.approveRequest(requestId);
        return ResponseEntity.ok(approvedRequest);
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<ApprovalRequest> rejectRequest(@PathVariable Long requestId) {
        ApprovalRequest rejectedRequest = approvalRequestService.rejectRequest(requestId);
        return ResponseEntity.ok(rejectedRequest);
    }
}
