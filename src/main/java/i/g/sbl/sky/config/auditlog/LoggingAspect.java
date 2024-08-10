package i.g.sbl.sky.config.auditlog;

import i.g.sbl.sky.basic.cons.system.LogResultStatus;
import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.model.UserContext;
import i.g.sbl.sky.basic.utils.SpelUtils;
import i.g.sbl.sky.config.ioc.SpringBeanUtil;
import i.g.sbl.sky.entity.system.Log;
import i.g.sbl.sky.service.system.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Autowired
    private LogService logService;


    @Pointcut("@annotation(i.g.sbl.sky.config.auditlog.AuditLog)")
    private void auditLogPointCut() {
    }

    @Around(value = "auditLogPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Log log = new Log();
        UserContext.getUser().ifPresentOrElse(user -> {
            log.setNickname(user.getNickname());
            log.setUsername(user.getUsername());
        }, () -> {
            throw new AuthenticationException("User not authenticated");
        });
        SpringBeanUtil.getRequest().ifPresent(request -> {
            log.setRequestUri(request.getRequestURI());
        });

        // 获取日志注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuditLog logAnnotation = method.getAnnotation(AuditLog.class);

        log.setMsg(logAnnotation.value());
        log.setLevel(logAnnotation.level());
        log.setType(logAnnotation.type());

        if (StringUtils.hasText(logAnnotation.parameters())) {
            String parameterText = SpelUtils.parseSPEL(logAnnotation.parameters(), joinPoint.getArgs());
            log.setParameters(StringUtils.truncate(parameterText, 1024));
        }
        try {
            Object result = joinPoint.proceed();
            log.setResultStatus(LogResultStatus.success);
            if (StringUtils.hasText(logAnnotation.result())) {
                String resultText = SpelUtils.parseSPEL(logAnnotation.result(), result);
                log.setResult(StringUtils.truncate(resultText, 1024));
            }
            return result;
        } catch (Exception e) {
            log.setResultStatus(LogResultStatus.failed);
            log.setFailedReason(StringUtils.truncate(e.getMessage(), 1024));
            throw e;
        } finally {
            saveAuditLog(log);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAuditLog(Log log) {
        try {
            logService.create(log);
        } catch (Exception e) {
            LoggingAspect.log.error("Save audit log error", e);
        }
    }
}