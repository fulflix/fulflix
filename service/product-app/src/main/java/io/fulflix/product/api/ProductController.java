package io.fulflix.product.api;

import io.fulflix.core.app.context.annotation.CurrentUser;
import io.fulflix.core.app.context.annotation.CurrentUserRole;
import io.fulflix.core.web.principal.Role;
import io.fulflix.product.api.dto.*;
import io.fulflix.product.application.ProductService;
import io.fulflix.product.application.ProductFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.fulflix.core.web.utils.ResponseEntityUtils.created;
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
    private final ProductFacade productFacade;
    private final ProductService productService;

    // 상품 등록 (마스터 관리자, 허브 업체)
    @PostMapping
    public ResponseEntity<Void> registerProduct(
            @Valid @RequestBody RegisterProductRequest registerProductRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        productFacade.registerProduct(registerProductRequest, currentUser, role);
        return created("/product");
    }

    // 상품 삭제 (마스터 관리자)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        productService.deleteProduct(id, currentUser, role);
        return ResponseEntity.noContent().build();
    }

    // 상품 전체 조회 및 검색 (마스터 관리자)
    @GetMapping("/admin")
    public ResponseEntity<Page<ProductDetailResponse>> getAllProductsForAdmin(
            @RequestParam(required = false, defaultValue = "") String product, // 상품명
            @RequestParam(required = false, defaultValue = "0") Integer stockQuantity, // 재고
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 페이징 및 정렬
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        Page<ProductDetailResponse> products = productService.getAllProductsForAdmin(product, stockQuantity, pageable, currentUser, role);
        return ResponseEntity.ok(products);
    }

    // 상품 전체 조회 및 검색 (허브 관리자, 허브 업체, 허브 배송 담당자, 업체 배송 담당자)
    @GetMapping("/hub")
    public ResponseEntity<Page<ProductResponse>> getAllProductsForHubUsers(
            @RequestParam(required = false, defaultValue = "") String product, // 상품명
            @RequestParam(required = false, defaultValue = "0") Integer stockQuantity, // 재고
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 페이징 및 정렬
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        Page<ProductResponse> products = productService.getAllProductsForHub(product, stockQuantity, pageable, currentUser, role);
        return ResponseEntity.ok(products);
    }

    // 단일 상품 조회 (마스터 관리자)
    @GetMapping("/admin/{id}")
    public ResponseEntity<ProductDetailResponse> getProductForAdmin(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        ProductDetailResponse product = productService.getProductForAdmin(id, currentUser, role);
        return ResponseEntity.ok(product);
    }

    // 단일 상품 조회 (허브 관리자, 허브 업체, 허브 배송 담당자, 업체 배송 담당자)
    @GetMapping("/hub/{id}")
    public ResponseEntity<ProductResponse> getProductForHub(
            @PathVariable Long id,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        ProductResponse product = productService.getProductForHub(id, currentUser, role);
        return ResponseEntity.ok(product);
    }

    // 상품 수정 (마스터 관리자, 허브 업체)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest updateProductRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        productFacade.updateProduct(id, updateProductRequest, currentUser, role);
        return ResponseEntity.noContent().build();
    }

    // 주문 생성 시, 재고 감소
    @PutMapping("/{id}/reduce-stock")
    public ResponseEntity<Void> reduceStock(
            @PathVariable Long id,
            @RequestBody ReduceStockRequest reduceStockRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        productService.reduceStock(id, currentUser, reduceStockRequest, role);
        return ResponseEntity.noContent().build();
    }

    // 주문 취소 시, 재고 복원
    @PutMapping("/{id}/restore-stock")
    public ResponseEntity<Void> restoreStock(
            @PathVariable Long id,
            @RequestBody RestoreStockRequest restoreStockRequest,
            @CurrentUser Long currentUser,
            @CurrentUserRole Role role
    ) {
        productService.restoreStock(id, restoreStockRequest, currentUser, role);
        return ResponseEntity.noContent().build();
    }
}
