package i.g.sbl.sky.basic.model;

import java.util.Optional;

public class UserContext {
    private static final ThreadLocal<DetailedUser> context = new ThreadLocal<DetailedUser>();

    public static Optional<DetailedUser> getUser() {
        return Optional.ofNullable(context.get());
    }

    public static void setUser(DetailedUser user) {
        context.set(user);
    }

    public static void clear() {
        context.remove();
    }
}
