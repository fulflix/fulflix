package io.fulflix.company.repo;

import io.fulflix.company.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String companyName);
    Optional<Company> findByIdAndIsDeletedFalse(Long id);
    
    // 마스터 관리자용 전체 검색
    Page<Company> findByCompanyNameContaining(String companyName, Pageable pageable);

    // 허브 관리자용 전체 조회 & 검색
    Page<Company> findByHubIdAndCompanyNameContainingAndIsDeletedFalse(Long hubId, String companyName, Pageable pageable);
    Page<Company> findByHubIdAndIsDeletedFalse(Long hubId, Pageable pageable);

    // 허브 업체용 전체 조회 & 검색
    Page<Company> findByOwnerIdAndCompanyNameContainingAndIsDeletedFalse(Long ownerId, String companyName, Pageable pageable);
    Page<Company> findByOwnerIdAndIsDeletedFalse(Long ownerId, Pageable pageable);
}
