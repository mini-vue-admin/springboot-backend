package i.g.sbl.sky.basic.exception;

import i.g.sbl.sky.basic.cons.ResponseCode;

public class NotFoundException extends BusinessException{
    public NotFoundException() {
        super(ResponseCode.NOT_FOUND_ERROR, "Resource not found");
    }

    public NotFoundException(String message) {
        super(ResponseCode.NOT_FOUND_ERROR, message);
    }

    public NotFoundException(String message, Exception e) {
        super(ResponseCode.NOT_FOUND_ERROR, message, e);
    }
}
