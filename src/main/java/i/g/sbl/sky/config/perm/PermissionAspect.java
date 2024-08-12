package i.g.sbl.sky.config.perm;

import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.exception.PermissionForbiddenException;
import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class PermissionAspect {


    @Pointcut("@within(i.g.sbl.sky.config.perm.Permission)||@annotation(i.g.sbl.sky.config.perm.Permission)")
    private void permissionCutPoint() {
    }

    @Before(value = "permissionCutPoint()")
    public void checkPermission(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Permission annotation = method.getDeclaredAnnotation(Permission.class);
        if (annotation == null) {
            Class<?> declaringClass = method.getDeclaringClass();
            annotation = declaringClass.getDeclaredAnnotation(Permission.class);
        }
        if (annotation == null) {
            throw new IllegalStateException("Can not find available permission annotation");
        }
        if (annotation.value().length == 0) {
            throw new IllegalStateException(STR."Can not find available role defined in annotation on method: \{method.toGenericString()}");
        }

        Optional<DetailedUser> user = UserContext.getUser();
        if (user.isPresent()) {
            DetailedUser userDetails = user.get();
            List<String> roles = userDetails.getRoles();
            String[] accessibleRoles = annotation.value();
            boolean noneMatch = Arrays.stream(accessibleRoles).noneMatch(roles::contains);
            if (noneMatch) {
                throw new PermissionForbiddenException(STR."User[\{userDetails.getUsername()}] has no permitted roles: \{String.join(", ", accessibleRoles)}");
            }
        } else {
            throw new AuthenticationException("User not authorized");
        }
    }
}