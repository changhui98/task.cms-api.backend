package com.malgn.content.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "content_views",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_content_views_content_user", columnNames = {"content_id",
            "username"})
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt;

    public static ContentView of(Long contentId, String username) {
        ContentView contentView = new ContentView();
        contentView.contentId = contentId;
        contentView.username = username;
        contentView.viewedAt = LocalDateTime.now();
        return contentView;
    }

    public void updateViewedAt() {
        this.viewedAt = LocalDateTime.now();
    }

    public boolean canIncreaseViewCount() {
        return this.viewedAt.isBefore(LocalDateTime.now().minusHours(24));
    }

}
