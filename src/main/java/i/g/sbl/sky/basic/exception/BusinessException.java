package i.g.sbl.sky.basic.exception;

import i.g.sbl.sky.basic.cons.ResponseCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException {
    private int code;


    public BusinessException() {
        this.code = ResponseCode.FAILURE;
    }

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = ResponseCode.FAILURE;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResponseCode.FAILURE;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.code = ResponseCode.FAILURE;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = ResponseCode.FAILURE;
    }
}
