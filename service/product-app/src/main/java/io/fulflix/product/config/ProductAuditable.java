package io.fulflix.product.config;

import io.fulflix.common.app.jpa.audit.CommonAuditFields;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class ProductAuditable extends CommonAuditFields {
    private Long createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void applyProductCreated(Long currentUser) {
        createdBy = currentUser;
    }
}
