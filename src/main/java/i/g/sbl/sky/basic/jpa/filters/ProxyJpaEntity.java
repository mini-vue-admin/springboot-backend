package i.g.sbl.sky.basic.jpa.filters;

import lombok.SneakyThrows;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Arrays;

public interface ProxyJpaEntity<T> {

    public T getOriginInstance();

    public String getCallbackField();

    static <T> ProxyJpaEntity<T> getProxyJpaEntity(Class<T> clazz, T instance) {

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setInterfaces(new Class[]{ProxyJpaEntity.class});

        enhancer.setUseCache(true);
        enhancer.setCallback(new ProxyJpaEntityInterceptor(clazz, instance));
        return (ProxyJpaEntity<T>) enhancer.create();
    }

    class ProxyJpaEntityInterceptor<T> implements MethodInterceptor {

        private final T instance;
        private final Class<T> clazz;
        private Method curMethod;
        private String curField;

        @SneakyThrows
        public ProxyJpaEntityInterceptor(Class<T> clazz, T instance) {
            if (instance == null) {
                this.instance = clazz.newInstance();
            } else {
                this.instance = instance;
            }
            this.clazz = clazz;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (method.getDeclaringClass() == Object.class
                    || method.getDeclaringClass() == ProxyJpaEntityInterceptor.class) {
                //ignore
            } else if (method.getDeclaringClass() == ProxyJpaEntity.class) {
                if (method == ProxyJpaEntity.class.getDeclaredMethod("getOriginInstance")) {
                    return this.instance;
                } else if (method == ProxyJpaEntity.class.getDeclaredMethod("getCallbackField")) {
                    return this.curField;
                } else {
                    throw new IllegalStateException("Can not call method: " + method);
                }
            } else {
                this.curMethod = method;
                BeanInfo info = Introspector.getBeanInfo(method.getDeclaringClass());
                Arrays.stream(info.getPropertyDescriptors())
                        .filter(pd -> method.equals(pd.getReadMethod()))
                        .findAny()
                        .map(it -> this.curField = it.getDisplayName())
                        .orElseThrow(
                                () -> new RuntimeException("Can not find field from method: " + method.getName()));
            }
            return proxy.invokeSuper(obj, args);
        }
    }
}
