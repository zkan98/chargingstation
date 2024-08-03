package elice.chargingstationbackend.business.controller;

import elice.chargingstationbackend.business.entity.ApprovalRequest;
import elice.chargingstationbackend.business.service.ApprovalRequestService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/approval-requests")
@RequiredArgsConstructor
public class ApprovalRequestController {

    private final ApprovalRequestService approvalRequestService;

    @PostMapping
    public ResponseEntity<ApprovalRequest> createApprovalRequest(
        @RequestParam Long ownerId,
        @RequestParam String requestType,
        @RequestParam("businessCertificate") MultipartFile businessCertificate,
        @RequestParam("identityProof") MultipartFile identityProof) throws IOException {

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
