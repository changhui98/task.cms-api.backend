package com.malgn.content.domain.repository;

import com.malgn.content.domain.entity.ContentView;
import java.util.Optional;

public interface ContentViewRepository {

    Optional<ContentView> findByContentIdAndUsername(Long contentId, String username);

    ContentView save(ContentView contentView);

}
