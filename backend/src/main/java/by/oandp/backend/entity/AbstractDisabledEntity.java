package by.oandp.backend.entity;

import by.oandp.backend.entity.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AbstractDisabledEntity extends HasIdAndVersionAbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonIgnore
    private UserEntity creator;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier_id", nullable = false)
    @JsonIgnore
    private UserEntity modifier;

    @Column(name = "modified_on", nullable = false)
    private LocalDateTime modifiedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remover_id")
    @JsonIgnore
    private UserEntity remover;

    @Column(name = "remove_on", nullable = true)
    private LocalDateTime removeOn;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @PrePersist
    private void _prePersist() {
        prePersist();
    }

    protected void prePersist() {
        setCreatedOn(LocalDateTime.now());
        setModifiedOn(LocalDateTime.now());
    }

    @PreUpdate
    private void _preUpdate() {
        preUpdate();
    }

    protected void preUpdate() {
        setModifiedOn(LocalDateTime.now());
    }
}
