package io.fulflix.infra.client.company;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static io.fulflix.infra.client.company.CompanyClient.COMPANY_APP_CLIENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = COMPANY_APP_CLIENT)
public interface CompanyClient {
    String COMPANY_APP_CLIENT = "company-app";
    String COMPANY_BY_ID_URI = "/company/admin/{id}";

    @GetMapping(
            path = COMPANY_BY_ID_URI,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    CompanyDetailResponse getCompanyByIdForAdmin(@PathVariable Long id);
}
