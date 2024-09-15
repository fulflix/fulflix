package io.fulflix.company.application;

import io.fulflix.common.web.principal.Role;

public interface CompanyDeleteStrategy {
    void deleteCompany(
            Long id,
            Long currentUser,
            Role role
    );

    boolean isMatched(Role role);
}
