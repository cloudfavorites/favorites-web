package com.favorites.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectSummary;

public interface CollectService {
	
	public List<CollectSummary> getCollects(String type, Long userId, Pageable pageable,Long favoritesId);
	
	public void saveCollect(Collect collect);
	
	public void updateCollect(Collect newCollect);
	
	public boolean checkCollect(Collect collect);
	
	public void importHtml(Map<String, String> map,Long favoritesId,Long userId);
	
	public StringBuilder exportToHtml(Long favoritesId);
	
	public List<CollectSummary> searchMy(Long userId,String key,Pageable pageable);
	
	public List<CollectSummary> searchOther(Long userId,String key,Pageable pageable);

}
