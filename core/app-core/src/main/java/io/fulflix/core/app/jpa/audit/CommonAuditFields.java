package io.fulflix.core.app.jpa.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonAuditFields {

    public static final String DEFAULT_CONDITION = "is_deleted = false";

    @Getter
    @LastModifiedBy
    private Long updatedBy;

    @Getter
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @JsonProperty("isDeleted")
    private boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void delete() {
        isDeleted = true;
    }

}
