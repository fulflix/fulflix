package io.fulflix.infra.client.product;

import io.fulflix.order.api.dto.ReduceStockRequest;

public interface ProductService {
    ProductDetailResponse getProductForAdminById(Long id);
    ProductResponse getProductForCompanyById(Long id);
    void reduceStock(Long id, ReduceStockRequest reduceStockRequest);
}
