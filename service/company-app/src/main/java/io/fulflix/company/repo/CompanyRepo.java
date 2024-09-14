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
    Page<Company> findAllByIsDeletedFalse(Pageable pageable);
    Page<Company> findByCompanyNameContainingAndIsDeletedFalse(String keyword, Pageable pageable);
    Optional<Company> findByIdAndIsDeletedFalse(Long id);
}
