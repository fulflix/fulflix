package io.fulflix.infra.client.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompanyDetailResponse {

    private Long id;
    private Long hubId;
    private Long ownerId;
    private String companyName;
    private String companyType;
    private String companyAddress;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private Long updatedBy;
    private LocalDateTime updatedAt;

}
