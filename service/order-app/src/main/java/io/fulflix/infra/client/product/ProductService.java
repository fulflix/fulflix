package io.fulflix.infra.client.product;

import io.fulflix.order.api.dto.ReduceStockRequest;
import io.fulflix.order.api.dto.RestoreStockRequest;

public interface ProductService {
    ProductDetailResponse getProductForAdminById(Long id);
    ProductResponse getProductForCompanyById(Long id);
    void reduceStock(Long id, ReduceStockRequest reduceStockRequest);
    void restoreStock(Long id, RestoreStockRequest restoreStockRequest);
}
