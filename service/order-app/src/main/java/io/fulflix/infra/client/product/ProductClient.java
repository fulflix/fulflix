package io.fulflix.infra.client.product;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.fulflix.core.app.feign.FeignClientErrorDecoder;
import io.fulflix.core.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import io.fulflix.order.api.dto.ReduceStockRequest;
import io.fulflix.order.api.dto.RestoreStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ProductClient.PRODUCT_APP_CLIENT, configuration = {
    FulflixPrincipalRequestHeaderInterceptor.class,
    FeignClientErrorDecoder.class
})
public interface ProductClient extends ProductService {

    String PRODUCT_APP_CLIENT = "product-app";
    String PRODUCT_FOR_ADMIN_BY_ID_URI = "/product/admin/{id}";
    String PRODUCT_FOR_COMPANY_BY_ID_URI = "/product/hub/{id}";
    String REDUCE_STOCK_URI = "/product/{id}/reduce-stock";
    String RESTORE_STOCK_URI = "/product/{id}/restore-stock";

    // 상품 조회 API 호출 (마스터 관리자)
    @GetMapping(
        path = PRODUCT_FOR_ADMIN_BY_ID_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    ProductDetailResponse getProductForAdminById(@PathVariable Long id);

    // 상품 조회 API 호출 (생산/수령 업체)
    @GetMapping(
        path = PRODUCT_FOR_COMPANY_BY_ID_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    ProductResponse getProductForCompanyById(@PathVariable Long id);

    // 재고 감소 API 호출
    @PutMapping(
        path = REDUCE_STOCK_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    void reduceStock(@PathVariable Long id, @RequestBody ReduceStockRequest reduceStockRequest);

    // 재고 복원 API 호출
    @PutMapping(
        path = RESTORE_STOCK_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    void restoreStock(@PathVariable Long id, @RequestBody RestoreStockRequest restoreStockRequest);

}
