package i.g.sbl.sky.basic.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.Supplier;

@Slf4j
public class RetryUtils {

    /**
     * 重试直到返回值不为null或者重试次数耗光
     *
     * @param title
     * @param supplier
     * @param times
     * @param <T>
     * @return
     */
    public static <T> T doRetry(String title, Supplier<T> supplier, int times) {
        do {
            T data = supplier.get();
            if (data != null) {
                return data;
            }
            times--;
            log.warn("方法[{}]执行失败，剩余重试次数：{}", title, times);
        } while (times > 0);
        throw new RuntimeException(STR."重试次数超出最大限制: \{title}");
    }

    /**
     * 重试直到没有触发异常或者重试次数耗光
     *
     * @param title
     * @param callable
     * @param times
     * @param <T>
     * @return
     */
    public static <T> T doRetry(String title, Callable<T> callable, int times) {
        do {
            try {
                return callable.call();
            } catch (Exception e) {
                times--;
                log.warn("方法[{}]执行失败，剩余重试次数：{}", title, times);
            }
        } while (times > 0);
        throw new RuntimeException(STR."重试次数超出最大限制: \{title}");
    }

    /**
     * 间隔指定秒数重试执行方法，直到间隔时间总和超时
     * <p>
     * 该方法会立即执行一次，然后再计算间隔时间，举例：doTimeout("", supplier, 1, 2) 最多会执行3次<br/>
     * <code>
     * supplier() 等待1秒 <br/>
     * supplier() 等待1秒 <br/>
     * supplier() 结束 <br/>
     * </code>
     * </p>
     *
     * @param title
     * @param supplier
     * @param second
     * @param timeout
     * @param <T>
     * @return
     * @throws TimeoutException
     */
    public static <T> T doTimeout(String title, Supplier<T> supplier, int second, int timeout) throws TimeoutException, InterruptedException {
        do {
            T data = supplier.get();
            if (data == null) {
                Thread.sleep(second * 1000L);
            } else {
                return data;
            }
            timeout -= second;
        } while (timeout > 0);
        throw new TimeoutException(STR."执行操作超时: \{title}");
    }

    public <T> T doTimeout(String title, Supplier<T> supplier, int second) throws Throwable {
        try {
            return CompletableFuture.supplyAsync(supplier).get(second, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            // 获取封装的异常
            throw e.getCause();
        }
    }
}
