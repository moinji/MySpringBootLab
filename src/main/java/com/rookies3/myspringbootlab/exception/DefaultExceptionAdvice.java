package com.rookies3.myspringbootlab.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DefaultExceptionAdvice {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getCode().getHttpStatus().value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(e.getCode().getHttpStatus())
                .body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(400)
                .message("입력값 검증에 실패했습니다.")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    public static class ErrorResponse {
        private int status;
        private String message;
        private LocalDateTime timestamp;
        private Map<String, String> errors;
        
        public ErrorResponse() {}
        
        private ErrorResponse(Builder builder) {
            this.status = builder.status;
            this.message = builder.message;
            this.timestamp = builder.timestamp;
            this.errors = builder.errors;
        }
        
        public static Builder builder() {
            return new Builder();
        }
        
        public static class Builder {
            private int status;
            private String message;
            private LocalDateTime timestamp;
            private Map<String, String> errors;
            
            public Builder status(int status) {
                this.status = status;
                return this;
            }
            
            public Builder message(String message) {
                this.message = message;
                return this;
            }
            
            public Builder timestamp(LocalDateTime timestamp) {
                this.timestamp = timestamp;
                return this;
            }
            
            public Builder errors(Map<String, String> errors) {
                this.errors = errors;
                return this;
            }
            
            public ErrorResponse build() {
                return new ErrorResponse(this);
            }
        }
        
        public int getStatus() { return status; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public Map<String, String> getErrors() { return errors; }
        
        public void setStatus(int status) { this.status = status; }
        public void setMessage(String message) { this.message = message; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        public void setErrors(Map<String, String> errors) { this.errors = errors; }
    }
}