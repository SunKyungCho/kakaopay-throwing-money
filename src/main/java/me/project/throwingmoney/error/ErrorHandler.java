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
        ErrorResponse errorResponse = ErrorResponse.messageOf(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     *
     * 유효한 토큰이 또 생성되었는 경우 예외처리
     * */
    @ExceptionHandler(TokenAlreadyExistException.class)
    protected ResponseEntity<ErrorResponse> handleTokenAlreadyExistException(TokenAlreadyExistException e) {
        log.error("Token duplication exception.", e);
        ErrorResponse errorResponse = ErrorResponse.messageOf("Please run it again in a few minutes.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     * 뿌린건에 대한 조회는 7일동안만 가능
     * */
    @ExceptionHandler(StateTokenExpireException.class)
    protected ResponseEntity<ErrorResponse> handleStateTokenExpireException(StateTokenExpireException e) {
        log.info(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.messageOf("Read state token is expired. You can view it within 7 days");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
    * 토큰 정보 만료
    * */
    @ExceptionHandler(TokenExpireException.class)
    protected ResponseEntity<ErrorResponse> handleTokenExpireException(TokenExpireException e) {
        log.error("Token Expired: " + e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.messageOf(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     * 저장 되어 있지 않은 토큰 정보
     * */
    @ExceptionHandler(ThrowingMoneyNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleThrowMoneyNotFoundException(ThrowingMoneyNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.messageOf(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     * 토큰이 중복으로 생성된 경우
     * */
    @ExceptionHandler(TokenDuplicationException.class)
    protected ResponseEntity<ErrorResponse> handleTokenDuplicationException(TokenDuplicationException e) {
        ErrorResponse errorResponse = ErrorResponse.messageOf(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
     * 뿌려진 돈을 모두 가져가고 남은 것이 없는 경우
     * */
    @ExceptionHandler(EmptyMoneyException.class)
    protected ResponseEntity<ErrorResponse> handleTokenDuplicationException(EmptyMoneyException e) {
        ErrorResponse errorResponse = ErrorResponse.messageOf(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
