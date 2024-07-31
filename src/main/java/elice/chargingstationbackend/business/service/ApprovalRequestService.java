package elice.chargingstationbackend.business.service;

import elice.chargingstationbackend.business.entity.ApprovalRequest;
import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.exception.BusinessOwnerNotFoundException;
import elice.chargingstationbackend.business.exception.RequestNotFoundException;
import elice.chargingstationbackend.business.repository.ApprovalRequestRepository;
import elice.chargingstationbackend.business.repository.BusinessOwnerRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovalRequestService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final EmailService emailService;


    public ApprovalRequest createApprovalRequest(
        Long ownerId, String requestType, String businessCertificate, String identityProof) {

        BusinessOwner businessOwner = businessOwnerRepository.findById(ownerId)
            .orElseThrow(() -> new BusinessOwnerNotFoundException(ownerId));

        ApprovalRequest approvalRequest = new ApprovalRequest();
        approvalRequest.updateApprovalRequest(
            businessOwner, requestType, "Pending", LocalDateTime.now(),
            businessCertificate, identityProof
        );

        return approvalRequestRepository.save(approvalRequest);
    }
    public List<ApprovalRequest> getApprovalRequestsByOwnerId(Long ownerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(ownerId)
            .orElseThrow(() -> new BusinessOwnerNotFoundException(ownerId));

        return approvalRequestRepository.findByBusinessOwner(businessOwner);
    }


    public ApprovalRequest updateApprovalRequest(Long requestId, ApprovalRequest updatedRequest) {
        ApprovalRequest existingRequest = approvalRequestRepository.findById(requestId)
            .orElseThrow(() -> new RequestNotFoundException(requestId));

        existingRequest.updateApprovalRequest(
            updatedRequest.getBusinessOwner(), updatedRequest.getRequestType(), updatedRequest.getStatus(),
            updatedRequest.getTimestamp(), updatedRequest.getBusinessCertificate(), updatedRequest.getIdentityProof()
        );

        return approvalRequestRepository.save(existingRequest);
    }

    public ApprovalRequest approveRequest(Long requestId) {
        ApprovalRequest request = approvalRequestRepository.findById(requestId)
            .orElseThrow(() -> new RequestNotFoundException(requestId));

        request.setStatus("Approved");
        ApprovalRequest approvedRequest = approvalRequestRepository.save(request);

        sendApprovalEmail(approvedRequest);

        return approvedRequest;
    }

    public ApprovalRequest rejectRequest(Long requestId) {
        ApprovalRequest request = approvalRequestRepository.findById(requestId)
            .orElseThrow(() -> new RequestNotFoundException(requestId));

        request.setStatus("Rejected");
        ApprovalRequest rejectedRequest = approvalRequestRepository.save(request);

        sendRejectionEmail(rejectedRequest);

        return rejectedRequest;
    }

    private void sendApprovalEmail(ApprovalRequest request) {
        String to = request.getBusinessOwner().getOwnerEmail();
        String subject = "승인 요청 승인";
        String text = "귀하의 승인 요청이 승인되었습니다.";
        emailService.sendSimpleMessage(to, subject, text);
    }

    private void sendRejectionEmail(ApprovalRequest request) {
        String to = request.getBusinessOwner().getOwnerEmail();
        String subject = "승인 요청 거부";
        String text = "귀하의 승인 요청이 거부되었습니다.";
        emailService.sendSimpleMessage(to, subject, text);
    }
}
