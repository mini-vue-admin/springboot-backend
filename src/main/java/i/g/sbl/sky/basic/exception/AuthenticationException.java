package i.g.sbl.sky.basic.exception;

import i.g.sbl.sky.basic.cons.ResponseCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(String message) {
        super(ResponseCode.AUTHENTICATION_ERROR, message);
    }

    public AuthenticationException(String message, Exception e) {
        super(ResponseCode.AUTHENTICATION_ERROR, message, e);
    }
}
