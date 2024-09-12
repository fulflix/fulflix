package io.fulflix.user.repo.model;

import static io.fulflix.common.app.jpa.audit.CommonAuditFields.DEFAULT_CONDITION;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import io.fulflix.common.web.principal.Role;
import io.fulflix.user.config.UserAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(
    schema = "user_db",
    name = "p_users",
    uniqueConstraints = @UniqueConstraint(
        name = "UK_USERNAME", columnNames = "username"
    )
)
@SQLRestriction(DEFAULT_CONDITION)
@NoArgsConstructor(access = PROTECTED)
public class User extends UserAuditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(STRING)
    private Role role;

    private User(String username, String password, String name, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static User of(String username, String password, String name, Role role) {
        return new User(username, password, name, role);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User user) {
            return Objects.equals(id, user.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
