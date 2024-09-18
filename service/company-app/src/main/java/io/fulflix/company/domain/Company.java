package io.fulflix.company.domain;

import io.fulflix.core.app.jpa.audit.Auditable;
import io.fulflix.company.api.dto.UpdateCompanyRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "p_company")
public class Company extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hub_id", nullable = false)
    private Long hubId;

    @Column(name = "owner_Id", nullable = false)
    private Long ownerId;

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;

    @Column(name = "company_address", nullable = false)
    private String companyAddress;

    // ownerId(업체) 설정
    public void assignOwnerId(Long newOwnerId) {
        this.ownerId = newOwnerId;
    }

    // hubId 업데이트
    public void updateHubId(UpdateCompanyRequest updateCompanyRequest) {
        this.hubId = updateCompanyRequest.getHubId();
    }

    // companyName 업데이트
    public void updateCompanyName(UpdateCompanyRequest updateCompanyRequest) {
        this.companyName = updateCompanyRequest.getCompanyName();
    }

    // companyAddress 업데이트
    public void updateCompanyAddress(UpdateCompanyRequest updateCompanyRequest) {
        this.companyAddress = updateCompanyRequest.getCompanyAddress();
    }
}
