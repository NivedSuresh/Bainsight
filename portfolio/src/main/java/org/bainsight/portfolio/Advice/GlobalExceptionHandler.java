package org.bainsight.portfolio.Advice;


import org.exchange.library.Advice.Error;
import org.exchange.library.Advice.ErrorResponse;
import org.exchange.library.Exception.Authentication.BadBindException;
import org.exchange.library.Exception.GlobalException;
import org.exchange.library.Mapper.ValidationErrorMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {


        if(e instanceof GlobalException exception)
        {
            return ResponseEntity.status(exception.getStatus()).body(ErrorResponse.builder()
                    .errorCode(exception.getErrorCode())
                    .message(e.getMessage())
                    .build());
        }


        else if(e instanceof HttpRequestMethodNotSupportedException || e instanceof MissingRequestValueException)
        {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ErrorResponse.builder()
                    .errorCode(Error.INVALID_REQUEST_METHOD_OR_VALUE)
                    .message("Invalid request made!")
                    .build());
        }

        else if (e instanceof BindException b)
        {

            BindingResult result = b.getBindingResult();

            BadBindException badBindException = ValidationErrorMapper.fetchFirstError(result);
            return ResponseEntity.status(badBindException.getStatus()).body(ErrorResponse.builder()
                     .errorCode(badBindException.getErrorCode())
                     .message(badBindException.getMessage())
                     .build());

        }


        if(e instanceof DataAccessException){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.builder()
                    .errorCode(Error.DATABASE_INTERACTION_FAILED)
                    .message("This service is unavailable right now, please try again later!")
                    .build());
        }
        /* Todo: IMPLEMENT LOGGING */


        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.builder()
                .errorCode(Error.SERVICE_UNAVAILABLE)
                .message("This service is unavailable right now, please try again later!")
                .build());
    }
}
