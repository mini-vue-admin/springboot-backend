package i.g.sbl.sky.basic.model;

public class UserContext {
    private static final ThreadLocal<DetailedUser> context = new ThreadLocal<DetailedUser>();

    public static DetailedUser getUser() {
        return context.get();
    }

    public static void setUser(DetailedUser user) {
        context.set(user);
    }

    public static void clear() {
        context.remove();
    }
}
