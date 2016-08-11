package com.favorites.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	@Column(nullable = true,length = 65535,columnDefinition="Text")
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
	private Long creteTime;
	@Column(nullable = false)
	private Long lastModifyTime;
	private String collectTime;

	public Collect() {
		super();
	}


}