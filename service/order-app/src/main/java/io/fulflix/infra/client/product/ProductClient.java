package io.fulflix.infra.client.product;

import io.fulflix.common.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import io.fulflix.order.api.dto.ReduceStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = ProductClient.PRODUCT_APP_CLIENT, configuration = FulflixPrincipalRequestHeaderInterceptor.class)
public interface ProductClient extends ProductService {
    String PRODUCT_APP_CLIENT = "product-app";
    String PRODUCT_BY_ID_URI = "/product/admin/{id}";
    String REDUCE_STOCK_URI = "/product/{id}/reduce-stock";

    @GetMapping(
            path = PRODUCT_BY_ID_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    ProductResponse getProductById(@PathVariable Long id);

    // 재고 감소 API 호출
    @PutMapping(
            path = REDUCE_STOCK_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    void reduceStock(@PathVariable Long id, @RequestBody ReduceStockRequest reduceStockRequest);
}
