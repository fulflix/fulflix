package io.fulflix.core.web.exception.response;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalErrorCode {
    MISSING_PATH_VARIABLE(BAD_REQUEST, "잘못된 요청입니다. Path URI를 확인해 주세요."),
    MISSING_SERVLET_REQUEST_PARAMETER(BAD_REQUEST, "잘못된 요청입니다. Query Parameter를 확인해 주세요."),
    METHOD_ARGUMENT_TYPE_MISMATCH(BAD_REQUEST, "요청 값의 자료형이 잘못되었습니다."),
    METHOD_ARGUMENT_NOT_VALID(BAD_REQUEST, "잘못된 요청입니다. 요청 값을 확인해 주세요."),
    HTTP_MESSAGE_NOT_READABLE(BAD_REQUEST, "요청 필드의 값이 잘못되었습니다."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(BAD_REQUEST, "응답 형식을 처리할 수 없습니다. 잠시 후 다시 시도해 주세요."),
    NO_RESOURCE_FOUND(NOT_FOUND, "요청하신 API를 찾을 수 없습니다. URL을 확인해 주세요."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(METHOD_NOT_ALLOWED, "지원하지 않는 요청 Method입니다."),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 요청입니다. Content-Type을 확인해 주세요."),
    UNEXPECTED_ERROR(INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다."),
    ;

    public final HttpStatus status;
    public final String message;

    GlobalErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static GlobalErrorCode valueOf(Exception exception) {
        String errorCodeName = convertExceptionClassNameToErrorCodeName(exception);
        try {
            return valueOf(errorCodeName);
        } catch (IllegalArgumentException e) {
            return GlobalErrorCode.UNEXPECTED_ERROR;
        }
    }

    private static String convertExceptionClassNameToErrorCodeName(Exception exception) {
        String className = extractExceptionClassName(exception);
        return convertToErrorCodeName(className);
    }

    private static String extractExceptionClassName(Exception exception) {
        return exception.getClass().getSimpleName();
    }

    private static String convertToErrorCodeName(String className) {
        String exceptKeyword = "_EXCEPTION";
        return convertToConstants(className).replace(exceptKeyword, "");
    }

    private static String convertToConstants(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";

        return value.replaceAll(regex, replacement)
            .toUpperCase();
    }
}
