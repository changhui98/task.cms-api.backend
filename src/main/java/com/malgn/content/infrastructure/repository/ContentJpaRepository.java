package com.malgn.content.infrastructure.repository;

import com.malgn.content.domain.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentJpaRepository extends JpaRepository<Content,Long> {

}
