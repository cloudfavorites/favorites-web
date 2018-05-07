package com.favorites.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.favorites.domain.view.CollectSummary;

public interface NoticeService {

	void saveNotice (String collectId, String type, Long userId, String operId);

	List<CollectSummary> getNoticeCollects (String type, Long userId, Pageable pageable);

}
