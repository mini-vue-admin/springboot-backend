package i.g.sbl.sky.basic.exception;

import i.g.sbl.sky.basic.cons.BusinessCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(String message) {
        super(BusinessCode.AUTHENTICATION_ERROR, message);
    }

    public AuthenticationException(String message, Exception e) {
        super(BusinessCode.AUTHENTICATION_ERROR, message, e);
    }
}
