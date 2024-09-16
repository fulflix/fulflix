package io.fulflix.product.api;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.product.api.dto.RegisterProductRequest;
import io.fulflix.product.application.ProductFacade;
import io.fulflix.product.application.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.fulflix.common.web.utils.ResponseEntityUtils.created;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(
        path = "/product",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
public class ProductController {
    private final ProductService productService;
    private final ProductFacade productFacade;

    // 상품 등록 (마스터 관리자, 허브 관리자, 허브 업체)
    @PostMapping
    public ResponseEntity<Void> registerProduct(
            @Valid @RequestBody RegisterProductRequest registerProductRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        productFacade.registerProduct(registerProductRequest, currentUser, role);
        return created("/product");
    }
}
