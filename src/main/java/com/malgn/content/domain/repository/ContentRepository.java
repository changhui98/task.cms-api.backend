package com.malgn.content.domain.repository;

import com.malgn.content.domain.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentRepository {

    Content save(Content content);

    Page<Content> findAllContents(Pageable pageable);
}
