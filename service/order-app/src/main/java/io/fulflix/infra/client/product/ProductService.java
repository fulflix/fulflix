package io.fulflix.infra.client.product;

import io.fulflix.order.api.dto.ReduceStockRequest;

public interface ProductService {
    ProductResponse getProductById(Long id);
    void reduceStock(Long id, ReduceStockRequest reduceStockRequest);
}
