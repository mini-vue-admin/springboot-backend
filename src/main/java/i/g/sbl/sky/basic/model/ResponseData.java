package i.g.sbl.sky.basic.model;

import i.g.sbl.sky.basic.cons.ResponseCode;
import i.g.sbl.sky.basic.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(ResponseCode.OK, null, data);
    }

    public static ResponseData<Void> success() {
        return success(null);
    }

    public static ResponseData<Void> failure(String msg) {
        return new ResponseData<>(ResponseCode.FAILURE, msg, null);
    }

    public static ResponseData<Void> failure(Exception e) {
        if (e instanceof BusinessException) {
            return new ResponseData<>(((BusinessException) e).getCode(), e.getMessage(), null);
        } else {
            return failure(e.getMessage());
        }
    }
}
