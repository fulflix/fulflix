package io.fulflix.common.app.jpa.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Getter
@MappedSuperclass
public abstract class Auditable extends CommonAuditFields {

    @CreatedBy
    @Column(nullable = false, updatable = false)
    protected Long createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
