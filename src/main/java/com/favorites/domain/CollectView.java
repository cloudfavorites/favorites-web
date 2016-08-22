package com.favorites.domain;

public interface CollectView{
	Long getId();
	Long getUserId();
	String getTitle();
	String getType();
	String getUrl();
	String getLogoUrl();
	String getRemark();
	String getDescription();
	Long getLastModifyTime();
	String getUserName();
	Long getFavoriteId();
	String getFavoriteName();
}