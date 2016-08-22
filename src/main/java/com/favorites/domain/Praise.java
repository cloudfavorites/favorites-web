package com.favorites.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 点赞
 * 
 * @author DingYS
 * 
 */
@Entity
public class Praise extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(optional = false)
	private Collect collect;
	@Column(nullable = false)
	private Long praiseId;
	@Column(nullable = false)
	private Long createTime;

	public Praise() {
		super();
	}
	
	public Praise(Collect collect, Long praiseId, Long createTime) {
		super();
		this.collect = collect;
		this.praiseId = praiseId;
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collect getCollect() {
		return collect;
	}

	public Long getPraiseId() {
		return praiseId;
	}

	public void setPraiseId(Long praiseId) {
		this.praiseId = praiseId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}