package com.exercise.movie.shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.Column;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Audited
@JsonIgnoreProperties({"createdDate", "lastModifiedDate"})
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<U> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Version
  @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
  protected Long version = 0L;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  protected U createdBy;

  @LastModifiedBy
  @Column(name = "last_modified_by")
  protected U lastModifiedBy;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  protected LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  protected LocalDateTime lastModifiedDate;

  @PrePersist
  public void prePersistVersion() {
    createdDate = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdateVersion() {
    lastModifiedDate = LocalDateTime.now();
    version += 1;
  }

  public BaseEntity<U> createdBy(U createdBy) {
    this.createdBy = createdBy;
    return this;
  }
}
