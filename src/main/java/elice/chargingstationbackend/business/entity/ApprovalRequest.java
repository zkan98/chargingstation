package elice.chargingstationbackend.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class ApprovalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private BusinessOwner businessOwner;

    private String requestType;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private LocalDateTime timestamp;

    private String businessCertificatePath;

    private String identityProofPath;


    public void updateApprovalRequest(BusinessOwner businessOwner, String requestType, ApprovalStatus status, LocalDateTime timestamp,
        String businessCertificate, String identityProof) {
        this.businessOwner = businessOwner;
        this.requestType = requestType;
        this.status = status;
        this.timestamp = timestamp;
        this.businessCertificatePath = businessCertificate;
        this.identityProofPath = identityProof;
    }
    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }}
