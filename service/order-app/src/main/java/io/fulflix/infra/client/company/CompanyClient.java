package io.fulflix.infra.client.company;

import io.fulflix.common.app.feign.FulflixPrincipalRequestHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = CompanyClient.COMPANY_APP_CLIENT, configuration = FulflixPrincipalRequestHeaderInterceptor.class)
public interface CompanyClient extends CompanyService {
    String COMPANY_APP_CLIENT = "company-app";
    String COMPANY_FOR_ADMIN_BY_ID_URI = "/company/admin/{id}";
    String COMPANY_FOR_COMPANY_BY_ID_URI = "/company/hub/{id}";

    @GetMapping(
            path = COMPANY_FOR_ADMIN_BY_ID_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    CompanyDetailResponse getCompanyForAdminById(@PathVariable Long id);

    @GetMapping(
            path = COMPANY_FOR_COMPANY_BY_ID_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    CompanyResponse getCompanyForCompanyById(@PathVariable Long id);
}
