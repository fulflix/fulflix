package io.fulflix.product.application;

import io.fulflix.core.web.principal.Role;
import io.fulflix.product.api.dto.RegisterProductRequest;

public interface ProductRegisterStrategy {
    void registerProduct(RegisterProductRequest registerProductRequest, Long currentUser, Role role);
    boolean isMatched(Role role);
}
