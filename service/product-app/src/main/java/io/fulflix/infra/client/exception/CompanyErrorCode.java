package io.fulflix.infra.client.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CompanyErrorCode {
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 업체를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    CompanyErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}