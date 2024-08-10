package i.g.sbl.sky.config.ioc;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

@Configuration
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Get Bean from application context
     *
     * @param beanClass - class of the Bean
     * @param <T>       - type of bean
     * @return - object bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        Objects.requireNonNull(context, "ApplicationContext can not be null");
        return context.getBean(beanClass);
    }

    public static Optional<HttpServletRequest> getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(servletRequestAttributes).map(ServletRequestAttributes::getRequest);
    }
}
