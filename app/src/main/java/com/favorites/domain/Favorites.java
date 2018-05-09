package com.favorites.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 收藏夹
 * @author DingYS
 *
 */
@Entity
public class Favorites extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Long count;
	@Column(nullable = false)
	private Long createTime;
	@Column(nullable = false)
	private Long lastModifyTime;
	@Column(nullable = false)
	private Long publicCount;
	
	public Long getPublicCount() {
		return publicCount;
	}
	public void setPublicCount(Long publicCount) {
		this.publicCount = publicCount;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Override
	public String toString() {
		return "Favorites{" +
				"id=" + id +
				", userId=" + userId +
				", name='" + name + '\'' +
				", count=" + count +
				", createTime=" + createTime +
				", lastModifyTime=" + lastModifyTime +
				", publicCount=" + publicCount +
				'}';
	}
}