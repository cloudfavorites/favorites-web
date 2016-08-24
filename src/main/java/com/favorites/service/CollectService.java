package com.favorites.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.favorites.domain.Collect;
import com.favorites.domain.CollectSummary;

public interface CollectService {
	
	public List<CollectSummary>  getCollects(String type,Long userId,Pageable pageable);
	
	public void saveCollect(Collect collect);
	
	public void updateCollect(Collect newCollect);
	
	public boolean checkCollect(Collect collect);
	
	public void importHtml(List<String> urlList,Long favoritesId,Long userId);

}
