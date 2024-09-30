package i.g.sbl.sky.basic.exception;

import i.g.sbl.sky.basic.cons.BusinessCode;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super(BusinessCode.NOT_FOUND_ERROR, "Resource not found");
    }

    public NotFoundException(String message) {
        super(BusinessCode.NOT_FOUND_ERROR, message);
    }

    public NotFoundException(String message, Exception e) {
        super(BusinessCode.NOT_FOUND_ERROR, message, e);
    }
}
