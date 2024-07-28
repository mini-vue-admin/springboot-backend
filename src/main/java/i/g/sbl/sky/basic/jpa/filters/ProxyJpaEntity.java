package i.g.sbl.sky.basic.jpa.filters;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

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

    static class ProxyJpaEntityInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return null;
        }
    }
}
