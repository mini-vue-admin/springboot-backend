package i.g.sbl.sky.config.auditlog;

import i.g.sbl.sky.basic.cons.system.LogLevel;
import i.g.sbl.sky.basic.cons.system.LogType;

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

    LogLevel level() default LogLevel.normal;

    LogType type() default LogType.sys;

    String parameters() default "";

    String result() default "";
}
