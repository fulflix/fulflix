package io.fulflix.hub.hub.domain;


import io.fulflix.common.app.jpa.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


import static io.fulflix.common.app.jpa.audit.CommonAuditFields.DEFAULT_CONDITION;

@Entity
@Table(name = "p_hubs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
//@SQLDelete(sql = "UPDATE p_hubs SET is_deleted = true WHERE id = ?")
@SQLRestriction(DEFAULT_CONDITION) //@SQLRestriction("is_deleted = false")
public class Hub extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

}
