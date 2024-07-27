package i.g.sbl.sky.basic.exception;

import i.g.sbl.sky.basic.cons.ResponseCode;

public class PermissionForbiddenException extends BusinessException {

    public PermissionForbiddenException(String message) {
        super(ResponseCode.PERMISSION_FORBIDDEN_ERROR, message);
    }

    public PermissionForbiddenException(String message, Exception e) {
        super(ResponseCode.PERMISSION_FORBIDDEN_ERROR, message, e);
    }
}
