package i.g.sbl.sky.basic.utils;

import lombok.SneakyThrows;

import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class RetryUtils {

    @SneakyThrows
    public static <T> T doRetry(String title, Supplier<T> supplier, int times)  {
        do {
            T data = supplier.get();
            System.out.println("time");
            if (data != null) {
                return data;
            }
            times--;
        } while (times > 0);
        throw new RuntimeException(STR."重试次数超出最大限制: \{title}");
    }

    @SneakyThrows
    public static <T> T doTimeout(String title, Supplier<T> supplier, int second, int timeout) throws TimeoutException {
        do {
            T data = supplier.get();
            System.out.println("time");
            if (data == null) {
                Thread.sleep(second * 1000L);
            } else {
                return data;
            }
            timeout -= second;
        } while (timeout > 0);
        throw new TimeoutException(STR."执行操作超时: \{title}");
    }

    @SneakyThrows
    public static void main(String[] args) {
        doRetry("retry", () -> null, 3);
        doTimeout("time", () -> null, 1, 2);
    }
}
