package io.fulflix.product.application;

import io.fulflix.common.web.principal.Role;
import io.fulflix.product.api.dto.RegisterProductRequest;
import io.fulflix.product.exception.ProductErrorCode;
import io.fulflix.product.exception.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFacade {
    private final List<ProductRegisterStrategy> productRegisterStrategies;

    // 상품 등록
    @Transactional
    public void registerProduct(RegisterProductRequest registerProductRequest, Long currentUser, Role role) {
        productRegisterStrategies.stream()
                .filter(strategy -> strategy.isMatched(role))
                .findAny()
                .orElseThrow(() -> new ProductException(ProductErrorCode.UNAUTHORIZED_ACCESS))
                .registerProduct(registerProductRequest, currentUser, role);
    }
}
