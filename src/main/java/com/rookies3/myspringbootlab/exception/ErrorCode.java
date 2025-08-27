package com.rookies3.myspringbootlab.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "도서를 찾을 수 없습니다."),
    BOOK_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "B002", "도서 상세 정보를 찾을 수 없습니다."),
    ISBN_DUPLICATE(HttpStatus.CONFLICT, "B003", "이미 존재하는 ISBN입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "V001", "입력값 검증에 실패했습니다.");
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    
    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}