package com.favorites.service;

import com.favorites.domain.Collect;
import com.favorites.domain.view.CollectSummary;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CollectService {
	
	public List<CollectSummary> getCollects(String type, Long userId, Pageable pageable,Long favoritesId,Long specUserId);
	
	public void saveCollect(Collect collect);
	
	public void updateCollect(Collect newCollect);
	
	public boolean checkCollect(Collect collect);
	
	public void importHtml(Map<String, String> map,Long favoritesId,Long userId,String type);
	
	public StringBuilder exportToHtml(long favoritesId);
	
	public List<CollectSummary> searchMy(Long userId,String key,Pageable pageable);
	
	public List<CollectSummary> searchOther(Long userId,String key,Pageable pageable);
	
	public void otherCollect(Collect collect);
	
	public void like(Long userId,long id);

}
