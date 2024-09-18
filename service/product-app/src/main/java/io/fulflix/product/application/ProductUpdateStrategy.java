package io.fulflix.product.application;

import io.fulflix.core.web.principal.Role;
import io.fulflix.product.api.dto.UpdateProductRequest;

public interface ProductUpdateStrategy {
    void updateProduct(Long id, UpdateProductRequest updateProductRequest, Long currentUser, Role role);
    boolean isMatched(Role role);
}
