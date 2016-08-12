package com.favorites.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Collect extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Long favoritesId;
	@Column(nullable = false)
	private String url;
	@Column(nullable = false)
	private String title;
	@Column(nullable = true, length = 65535, columnDefinition = "Text")
	private String description;
	@Column(nullable = true)
	private String logoUrl;
	@Column(nullable = true)
	private String charset;
	@Column(nullable = true)
	private String type;
	@Column(nullable = true)
	private String remark;
	@Column(nullable = false)
	private String isDelete;
	@Column(nullable = false)
	private Long CreateTime;
	@Column(nullable = false)
	private Long lastModifyTime;
	@Transient
	private String collectTime;
	@Transient
	private String newFavorites;

	public Collect() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getFavoritesId() {
		return favoritesId;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public String getCharset() {
		return charset;
	}

	public String getType() {
		return type;
	}

	public String getRemark() {
		return remark;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public Long getLastModifyTime() {
		return lastModifyTime;
	}

	public String getCollectTime() {
		return collectTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setFavoritesId(Long favoritesId) {
		this.favoritesId = favoritesId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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

}