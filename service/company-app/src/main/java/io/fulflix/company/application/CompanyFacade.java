package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.company.api.dto.CompanyResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyFacade {

    private final List<CompanyRetrieveStrategy> companyRetrieveStrategies;
    // AdminCompanyRetrieveStrategy
    // HubAdminCompanyRetrieveStrategy
    // ..
    // ..
    // ..
    // ..

    public Page<CompanyResponse> getAllCompaniesForHub(
        String query,
        int page,
        int size,
        String sortBy,
        String sortDirection,
        Long currentUser,
        Role role // MASTER_ADMIN
    ) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
            ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return companyRetrieveStrategies.stream()
            .filter(it -> it.isMatched(role)) // MASTER_ADMIN
            .findAny()
            .orElseThrow()
            .retrieveCompanies(query, sortedPageable, currentUser, role);
    }

}
