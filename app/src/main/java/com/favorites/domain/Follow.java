package com.favorites.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.favorites.domain.enums.FollowStatus;


/**
 * 关注
 * 
 * @author DingYS
 * 
 */
@Entity
public class Follow extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Long followId;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FollowStatus status;
	@Column(nullable = false)
	private Long createTime;
	@Column(nullable = false)
	private Long lastModifyTime;
	@Transient
	private String name;

	public Follow() {
		super();
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

	public Long getFollowId() {
		return followId;
	}

	public void setFollowId(Long followId) {
		this.followId = followId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public FollowStatus getStatus() {
		return status;
	}

	public void setStatus(FollowStatus status) {
		this.status = status;
	}

	public Long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}