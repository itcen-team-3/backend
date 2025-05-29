package com.team_3.nursing_care.common.auditor;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createDate;

    @CreatedBy
    @Column(updatable = false)
    protected Long createBy;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    @LastModifiedBy
    protected Long lastModifiedBy;

    protected Long deletedBy;
    protected Boolean isDeleted;
    protected LocalDateTime deletedDate;

    protected void softDelete(Long id) {
        isDeleted = true;
        deletedDate = LocalDateTime.now();
        deletedBy = id;
    };
}