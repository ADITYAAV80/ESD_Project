package com.aditya.project.GlobalException;


import com.aditya.project.Utility.InvalidCredentialsException;
import com.aditya.project.Utility.NullFieldsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice//makes this class the global exception handler
public class ExceptionHandler {

    //this annotation makes it handle the null exceptions like this method is activated when that exception occurs and it wouldn't happen without this annotation
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    //null field exceptions - it's checked in front end as well and then in backend because double checking as a tradeoff is better
    // to make backed more secure and it makes it fast in front end as well
    @org.springframework.web.bind.annotation.ExceptionHandler(NullFieldsException.class)
    public ResponseEntity<String> handleNUllFieldException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}



