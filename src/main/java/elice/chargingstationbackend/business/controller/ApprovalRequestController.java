package elice.chargingstationbackend.business.controller;

import elice.chargingstationbackend.business.entity.ApprovalRequest;
import elice.chargingstationbackend.business.service.ApprovalRequestService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController // 사업자 승인 요청을 처리하는 컨트롤러입니다.
@RequestMapping("/api/approval-requests")
@RequiredArgsConstructor
public class ApprovalRequestController {

    private final ApprovalRequestService approvalRequestService;

    @PostMapping // 새로운 승인 요청을 생성합니다.
    public ResponseEntity<ApprovalRequest> createApprovalRequest(
        @RequestParam Long ownerId,
        @RequestParam("businessCertificate") MultipartFile businessCertificate,
        @RequestParam("identityProof") MultipartFile identityProof) throws IOException {

        ApprovalRequest approvalRequest = approvalRequestService.createApprovalRequest(
            ownerId, businessCertificate, identityProof
        );
        return ResponseEntity.ok(approvalRequest);
    }

    @PutMapping("/{requestId}") // 기존의 승인 요청을 업데이트합니다.
    public ResponseEntity<ApprovalRequest> updateApprovalRequest(
        @PathVariable Long requestId, @RequestBody ApprovalRequest updatedRequest) {
        ApprovalRequest approvalRequest = approvalRequestService.updateApprovalRequest(requestId, updatedRequest);
        return ResponseEntity.ok(approvalRequest);
    }

    @GetMapping("/{ownerId}") // 요청자 ID로 승인 요청을 조회합니다.
    public ResponseEntity<List<ApprovalRequest>> getApprovalRequestsByOwnerId(@PathVariable Long ownerId) {
        List<ApprovalRequest> requests = approvalRequestService.getApprovalRequestsByOwnerId(ownerId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/approve/{requestId}") // 승인 요청을 승인합니다.
    public ResponseEntity<ApprovalRequest> approveRequest(@PathVariable Long requestId) {
        ApprovalRequest approvedRequest = approvalRequestService.approveRequest(requestId);
        return ResponseEntity.ok(approvedRequest);
    }

    @PostMapping("/reject/{requestId}") // 승인 요청을 거부합니다.
    public ResponseEntity<ApprovalRequest> rejectRequest(@PathVariable Long requestId) {
        ApprovalRequest rejectedRequest = approvalRequestService.rejectRequest(requestId);
        return ResponseEntity.ok(rejectedRequest);
    }
}
