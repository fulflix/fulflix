package io.fulflix.order.application.validator;

import feign.FeignException;
import io.fulflix.infra.client.company.CompanyClient;
import io.fulflix.infra.client.company.CompanyDetailResponse;
import io.fulflix.infra.client.company.CompanyResponse;
import io.fulflix.infra.client.exception.CompanyErrorCode;
import io.fulflix.infra.client.exception.CompanyException;
import io.fulflix.infra.client.exception.ProductErrorCode;
import io.fulflix.infra.client.exception.ProductException;
import io.fulflix.infra.client.product.ProductClient;
import io.fulflix.infra.client.product.ProductDetailResponse;
import io.fulflix.infra.client.product.ProductResponse;
import io.fulflix.order.api.dto.ReduceStockRequest;
import io.fulflix.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final ProductClient productClient;
    private final CompanyClient companyClient;

    public ProductDetailResponse checkProductExistForAdmin(Long productId) {
        log.info("상품 존재 확인 - productId: {}", productId);
        try {
            ProductDetailResponse productResponse = productClient.getProductForAdminById(productId);
            if (productResponse == null || productResponse.getIsDeleted()) {
                throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
            }
            return productResponse;
        } catch (FeignException e) {
            log.error("FeignClient 호출 실패 - productId: {}", productId, e);
            throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public ProductResponse checkProductExistForCompany(Long productId) {
        log.info("상품 존재 확인 요청 - productId: {}", productId);
        try {
            ProductResponse productResponse = productClient.getProductForCompanyById(productId);
            log.info("상품 응답 결과: {}", productResponse);
            if (productResponse == null) {
                log.error("상품이 존재하지 않음 - productId: {}", productId);
                throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
            }
            return productResponse;
        } catch (FeignException e) {
            log.error("FeignClient 호출 실패 - productId: {}", productId, e);
            throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public void checkCompanyExistForAdmin(Long companyId, String companyType) {
        log.info("업체 존재 확인 - productId: {} {}", companyId, companyType);
        try {
            CompanyDetailResponse companyResponse = companyClient.getCompanyForAdminById(companyId);
            if (companyResponse == null) {
                throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
            }
            if (!companyResponse.getCompanyType().equals(companyType)) {
                throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
            }
        } catch (FeignException e) {
            log.error("FeignClient 호출 실패 - companyId: {}", companyId, e);
            throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
        }
    }

    public void checkCompanyExistForCompany(Long companyId, String companyType) {
        try {
            CompanyResponse companyResponse = companyClient.getCompanyForCompanyById(companyId);
            if (companyResponse == null) {
                throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
            }
            if (!companyResponse.getCompanyType().equals(companyType)) {
                throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
            }
        } catch (FeignException e) {
            throw new CompanyException(CompanyErrorCode.COMPANY_NOT_FOUND);
        }
    }

    public OrderStatus validateStockAvailabilityForAdmin(ProductDetailResponse productResponse, int orderQuantity) {
        log.info("상품 재고: {}, 주문 수량: {}", productResponse.getStockQuantity(), orderQuantity);
        if (productResponse.getStockQuantity() >= orderQuantity) {
            return OrderStatus.SUCCESS;
        } else {
            return OrderStatus.FAIL;
        }
    }

    public OrderStatus validateStockAvailabilityForCompany(ProductResponse productResponse, int orderQuantity) {
        if (productResponse.getStockQuantity() >= orderQuantity) {
            return OrderStatus.SUCCESS;
        } else {
            return OrderStatus.FAIL;
        }
    }

    public void reduceStock(Long productId, int orderQuantity) {
        log.info("재고 감소 요청 - 상품 ID: {}, 감소량: {}", productId, orderQuantity);
        try {
            ReduceStockRequest reduceStockRequest = new ReduceStockRequest(orderQuantity);
            productClient.reduceStock(productId, reduceStockRequest);
        } catch (FeignException e) {
            log.error("재고 감소 실패 - 상품 ID: {}", productId, e);
            throw new ProductException(ProductErrorCode.STOCK_UPDATE_FAILED);
        }
    }

}
