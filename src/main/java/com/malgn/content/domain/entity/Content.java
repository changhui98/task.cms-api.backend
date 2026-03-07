package com.malgn.content.domain.entity;

import com.malgn.global.entity.AuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "contents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    public static Content of(String title, String description, String username) {
        Content content = new Content();
        content.title = title;
        content.description = description;
        content.createdBy = username;
        content.lastModifiedBy = username;
        return content;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void update(String title, String description, String username) {
        if (title != null) {
            this.title = title;
        }

        if (description != null) {
            this.description = description;
        }

        this.lastModifiedBy = username;
    }

    public boolean isOwner(String username) {
        return this.createdBy.equalsIgnoreCase(username);
    }

}
