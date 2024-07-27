package i.g.sbl.sky.config.web;

import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.exception.PermissionForbiddenException;
import i.g.sbl.sky.basic.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice {


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseData<Void> handleAuthenticationException(AuthenticationException e) {
        log.error("Global exception catch", e);
        return ResponseData.failure(e);
    }


    @ExceptionHandler(PermissionForbiddenException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseData<Void> handlePermissionForbiddenException(PermissionForbiddenException e) {
        log.error("Global exception catch", e);
        return ResponseData.failure(e);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<Void> handleException(Exception e) {
        log.error("Global exception catch", e);
        return ResponseData.failure(e);
    }
}
