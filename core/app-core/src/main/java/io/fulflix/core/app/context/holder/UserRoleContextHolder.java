package io.fulflix.core.app.context.holder;

import io.fulflix.common.web.principal.Role;

public class UserRoleContextHolder {

    private static final ThreadLocal<Role> currentUserRole = new ThreadLocal<>();

    public static void setCurrentUserRole(String userRoleName) {
        Role role = Role.valueOfRoleName(userRoleName);
        currentUserRole.set(role);
    }

    public static Role getCurrentUserRole() {
        return currentUserRole.get();
    }

    public static void clear() {
        currentUserRole.remove();
    }

}
