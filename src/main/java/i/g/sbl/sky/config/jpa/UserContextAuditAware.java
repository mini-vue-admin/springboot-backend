package i.g.sbl.sky.config.jpa;

import i.g.sbl.sky.basic.model.UserContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserContextAuditAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(UserContext.getUser().getUsername());
    }
}
