package i.g.sbl.sky.basic.model;

import i.g.sbl.sky.basic.cons.BusinessCode;
import i.g.sbl.sky.basic.exception.BusinessException;
import i.g.sbl.sky.basic.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> ResponseData<T> success(Optional<T> data) {
        T item = data.orElseThrow(NotFoundException::new);
        return new ResponseData<>(BusinessCode.OK, null, item);
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(BusinessCode.OK, null, data);
    }

    public static ResponseData<Void> success() {
        return success((Void) null);
    }

    public static ResponseData<Void> failure(String msg) {
        return new ResponseData<>(BusinessCode.FAILURE, msg, null);
    }


    public static <T> ResponseData<T> failure(String msg, T data) {
        return new ResponseData<>(BusinessCode.FAILURE, msg, data);
    }

    public static ResponseData<Void> failure(Exception e) {
        if (e instanceof BusinessException) {
            return new ResponseData<>(((BusinessException) e).getCode(), e.getMessage(), null);
        } else {
            return failure(e.getMessage());
        }
    }
}
