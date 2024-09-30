package i.g.sbl.sky.basic.exception;

import i.g.sbl.sky.basic.cons.BusinessCode;

public class PermissionForbiddenException extends BusinessException {

    public PermissionForbiddenException(String message) {
        super(BusinessCode.PERMISSION_FORBIDDEN_ERROR, message);
    }

    public PermissionForbiddenException(String message, Exception e) {
        super(BusinessCode.PERMISSION_FORBIDDEN_ERROR, message, e);
    }
}
