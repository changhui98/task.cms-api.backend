package com.malgn.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_date", nullable = false)
    protected LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    protected LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    protected LocalDateTime deletedDate;

}
