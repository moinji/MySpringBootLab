package mylab.common.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionAdvice {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException e) {
        ErrorObject errorObject = e.getErrorObject();
        return ResponseEntity.status(errorObject.getStatus()).body(errorObject);
    }
}