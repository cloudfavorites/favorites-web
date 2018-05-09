package com.favorites.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 消息
 * 
 * @author DingYS
 * 
 */
@Entity
public class Notice extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = true)
	private String collectId;
	@Column(nullable = false)
	private String type;
	@Column(nullable = true)
	private String operId;
	@Column(nullable = false)
	private String readed;
	@Column(nullable = false)
	private Long createTime;

	public Notice() {
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

	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getReaded() {
		return readed;
	}

	public void setReaded(String readed) {
		this.readed = readed;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}