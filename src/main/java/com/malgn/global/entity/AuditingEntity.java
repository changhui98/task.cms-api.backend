package com.malgn.global.entity;

import com.malgn.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@MappedSuperclass
public abstract class AuditingEntity extends BaseEntity{

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50)
    protected String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    protected String lastModifiedBy;

    @Column(name = "deleted_by", length = 50)
    protected String deletedBy;

    public void deleteBy(Member member) {
        this.deletedBy = member.getUsername();
    }
}
