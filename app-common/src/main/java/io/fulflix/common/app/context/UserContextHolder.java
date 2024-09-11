package io.fulflix.common.app.context;

import java.util.Optional;

public class UserContextHolder {

    private static final ThreadLocal<Optional<String>> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(String userId) {
        currentUser.set(Optional.ofNullable(userId));
    }

    public static Long getCurrentUser() {
        return currentUser.get()
            .map(Long::parseLong)
            .orElse(0L);
    }

    public static void clear() {
        currentUser.remove();
    }

}
