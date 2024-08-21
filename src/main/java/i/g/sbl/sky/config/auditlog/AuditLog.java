package i.g.sbl.sky.config.auditlog;

import i.g.sbl.sky.basic.cons.system.Level;
import i.g.sbl.sky.basic.cons.system.Type;

import java.lang.annotation.*;

/**
 * 审计日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 操作日志描述
     */
    String value();

    Level level() default Level.normal;

    Type type() default Type.system_operate;

    String parameters() default "";

    String result() default "";
}
