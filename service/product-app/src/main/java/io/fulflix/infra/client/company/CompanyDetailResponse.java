package io.fulflix.infra.client.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDetailResponse {

    private Long id;
    private Long hubId;
    private String companyName;
    private String companyType;
    private String companyAddress;
    private Boolean isDeleted;
    private LocalDateTime updatedAt;
    private Long updatedBy;

}
