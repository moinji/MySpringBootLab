package com.rookies3.myspringbootlab.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    
    private final ErrorCode code;
    private final Object[] args;
    
    public BusinessException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
        this.args = null;
    }
    
    public BusinessException(ErrorCode code, String message) {
        super(message);
        this.code = code;
        this.args = null;
    }
    
    public BusinessException(ErrorCode code, Object... args) {
        super(code.getMessage());
        this.code = code;
        this.args = args;
    }
    
    public BusinessException(ErrorCode code, String message, Object... args) {
        super(message);
        this.code = code;
        this.args = args;
    }
}