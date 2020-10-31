package me.project.throwingmoney.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {


    /*
     * 잘못된 파라미터 입력시 발생 한다.
     * */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
//        TODO: 에러 메세지 정의
        log.error("", e);

        ErrorResponse errorResponse = ErrorResponse.messageOf(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenAlreadyExistException.class)
    protected ResponseEntity<ErrorResponse> handleTokenAlreadyExistException(TokenAlreadyExistException e) {
        log.error("Token duplication exception.", e);
        ErrorResponse errorResponse = ErrorResponse.messageOf("Please run it again in a few minutes.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
