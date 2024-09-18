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
        try {
            ProductDetailResponse productResponse = productClient.getProductForAdminById(productId);
            if (productResponse == null || productResponse.isDeleted()) {
                throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
            }
            return productResponse;
        } catch (FeignException e) {
            throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public ProductResponse checkProductExistForCompany(Long productId) {
        try {
            ProductResponse productResponse = productClient.getProductForCompanyById(productId);
            if (productResponse == null) {
                throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
            }
            return productResponse;
        } catch (FeignException e) {
            throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public void checkCompanyExistForAdmin(Long companyId, String companyType) {
        try {
            CompanyDetailResponse companyResponse = companyClient.getCompanyForAdminById(companyId);
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
        try {
            ReduceStockRequest reduceStockRequest = new ReduceStockRequest(orderQuantity);
            productClient.reduceStock(productId, reduceStockRequest);
        } catch (FeignException e) {
            throw new ProductException(ProductErrorCode.STOCK_UPDATE_FAILED);
        }
    }
}
