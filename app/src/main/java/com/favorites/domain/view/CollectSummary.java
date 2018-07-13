package com.favorites.domain.view;

import java.io.Serializable;

public class CollectSummary  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private String profilePicture;
	private Long favoritesId;
	private String url;
	private String title;
	private String description;
	private String logoUrl;
	private String type;
	private String remark;
	private Long lastModifyTime;
	private String userName;
	private String favoriteName;
	private String collectTime;
	private String newFavorites;
	private Long praiseCount;
	private Long commentCount;
	private boolean isPraise;

	public CollectSummary(){

	}

	public CollectSummary(CollectView view) {
		this.id = view.getId();
		this.userId = view.getUserId();
		this.profilePicture = view.getProfilePicture();
		this.favoritesId = view.getFavoritesId();
		this.favoriteName=view.getFavoriteName();
		this.url = view.getUrl();
		this.title = view.getTitle();
		this.description = view.getDescription();
		this.logoUrl = view.getLogoUrl();
		this.type = view.getType();
		this.remark = view.getRemark();
		this.lastModifyTime = view.getLastModifyTime();
		this.userName = view.getUserName();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public Long getFavoritesId() {
		return favoritesId;
	}
	public void setFavoritesId(Long favoritesId) {
		this.favoritesId = favoritesId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFavoriteName() {
		return favoriteName;
	}
	public void setFavoriteName(String favoriteName) {
		this.favoriteName = favoriteName;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getNewFavorites() {
		return newFavorites;
	}
	public void setNewFavorites(String newFavorites) {
		this.newFavorites = newFavorites;
	}
	public Long getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Long praiseCount) {
		this.praiseCount = praiseCount;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	public boolean isPraise() {
		return isPraise;
	}
	public void setPraise(boolean isPraise) {
		this.isPraise = isPraise;
	}
	


}