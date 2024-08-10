package i.g.sbl.sky.config.web.filter;

import i.g.sbl.sky.basic.cons.system.Status;
import i.g.sbl.sky.basic.exception.AuthenticationException;
import i.g.sbl.sky.basic.model.DetailedUser;
import i.g.sbl.sky.basic.model.UserContext;
import i.g.sbl.sky.basic.utils.JwtUtils;
import i.g.sbl.sky.entity.system.User;
import i.g.sbl.sky.service.system.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Component
@Order(2)
public class JwtAuthInterceptor implements HandlerInterceptor {
    private static final String[] WHITE_LIST = new String[]{"/swagger-ui", "/v3/api-docs", "/login"};

    @Value("${auth.enabled:true}")
    private boolean authEnabled;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!authEnabled) {
            UserContext.setUser(DetailedUser.ANONYMOUSE);
            return true;
        }
        String requestURI = request.getRequestURI();
        for (String path : WHITE_LIST) {
            if (requestURI.startsWith(path)) {
                UserContext.setUser(DetailedUser.ANONYMOUSE);
                return true;
            }
        }

        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            throw new AuthenticationException("Authorization header is required");
        }
        String token = authorization.substring("Bearer ".length());

        String username = jwtUtils.decode(token);
        Optional<DetailedUser> user = userService.getDetailedUserByUsername(username);
        user.ifPresentOrElse(u -> {
            if (u.getStatus() == Status.disabled) {
                throw new AuthenticationException("User disabled");
            }
            UserContext.setUser(u);
        }, () -> {
            throw new AuthenticationException("User not found");
        });
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContext.clear();
    }
}
