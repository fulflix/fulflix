package io.fulflix.infra.client.company;

import io.fulflix.core.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static io.fulflix.infra.client.company.CompanyClient.COMPANY_APP_CLIENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = COMPANY_APP_CLIENT, configuration = FulflixPrincipalRequestHeaderInterceptor.class)
public interface CompanyClient {
    String COMPANY_APP_CLIENT = "company-app";
    String COMPANY_BY_ID_FOR_ADMIN_URI = "/company/admin/{id}";
    String COMPANY_BY_ID_FOR_HUB_URI = "/company/hub/{id}";

    @GetMapping(
            path = COMPANY_BY_ID_FOR_ADMIN_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    CompanyDetailResponse getCompanyByIdForAdmin(@PathVariable Long id);

    @GetMapping(
            path = COMPANY_BY_ID_FOR_HUB_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    CompanyResponse getCompanyByIdForHub(@PathVariable Long id);
}
