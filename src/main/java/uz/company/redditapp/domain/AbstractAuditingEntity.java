package uz.company.redditapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity extends SimpleEntity implements Serializable {

    private static final long serialVersionUId = 3L;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    protected boolean deleted = false;

    @Column(name = "created_by")
    @CreatedBy
    Long creatorId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    protected User createdBy;

    @Column(name = "created_at")
    @CreatedDate
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_by")
    @CreatedBy
    Long updaterId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    protected User updatedBy;

    @Column(name = "updated_at")
    @CreatedDate
    LocalDateTime updatedAt = LocalDateTime.now();


}
