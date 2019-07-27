package com.exercise.movie.shared.domain;

import com.exercise.movie.list.MovieList;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.Column;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<U> implements Serializable {
  private static final long serialVersionUID = 1L;

  @CreatedBy
  @Column(name = "created_by")
  protected U createdBy;

  @CreatedDate
  @Column(name = "created_date")
  protected Date createdDate;

  @LastModifiedBy
  @Column(name = "last_modified_by")
  protected U lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  protected Date lastModifiedDate;

  public BaseEntity<U> createdBy(U createdBy) {
    this.createdBy = createdBy;
    return this;
  }
}
