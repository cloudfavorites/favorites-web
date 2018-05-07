package com.favorites.service;

import com.favorites.domain.Collect;
import com.favorites.domain.view.CollectSummary;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CollectService {

	List<CollectSummary> getCollects (String type, Long userId, Pageable pageable, Long favoritesId, Long specUserId);

	void saveCollect (Collect collect);

	void updateCollect (Collect newCollect);

	boolean checkCollect (Collect collect);

	void importHtml (Map<String, String> map, Long favoritesId, Long userId, String type);

	StringBuilder exportToHtml (Long favoritesId);

	List<CollectSummary> searchMy (Long userId, String key, Pageable pageable);

	List<CollectSummary> searchOther (Long userId, String key, Pageable pageable);

	void otherCollect (Collect collect);

	void like (Long userId, long id);

}
