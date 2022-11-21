package com.paymybuddy.paymybuddybackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorObject.setMessage(exception.getMessage());
//        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorObject> handleInternalAuthenticationServiceException( InternalAuthenticationServiceException ex) {
//        ErrorObject errorObject = new ErrorObject();
//        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
//        errorObject.setMessage(ex.getMessage());
//        errorObject.setTimestamp(System.currentTimeMillis());
//        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(InternalAuthenticationServiceException.class)
//    public ResponseEntity<String> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
//        ResponseEntity<String> response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        return response;
//    }


}
