package com.favorites.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectSummary;

public interface CollectService {
	
	public List<CollectSummary>  getCollects(String type,Long userId,Pageable pageable);
	
	public void saveCollect(Collect collect, Long userId);
	
	public boolean checkCollect(Collect collect,Long userId);
	
	public void importHtml(Map<String, String> map,Long favoritesId,Long userId);
	
	public StringBuilder exportToHtml(Long favoritesId);

}
