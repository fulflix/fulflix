package io.fulflix.user.config;

import io.fulflix.core.app.jpa.audit.CommonAuditFields;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@MappedSuperclass
public abstract class UserAuditable extends CommonAuditFields {

    private Long createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void applyUserCreated(Long currentUser) {
        createdBy = currentUser;
    }

}
