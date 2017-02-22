package com.favorites.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 属性设置
 * 
 * @author DingYS
 * 
 */
@Entity
public class Config extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private String defaultFavorties;
	@Column(nullable = false)
	private String defaultCollectType;
	@Column(nullable = false)
	private String defaultModel;
	@Column(nullable = false)
	private Long createTime;
	@Column(nullable = false)
	private Long lastModifyTime;
	@Column(nullable = false)
	private String clearCollect;
	@Transient
	private String collectTypeName;
	@Transient
	private String modelName;
	@Transient
	private String clearName;

	public Config() {
		super();
	}

	public String getClearCollect() {
		return clearCollect;
	}

	public void setClearCollect(String clearCollect) {
		this.clearCollect = clearCollect;
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

	public String getDefaultFavorties() {
		return defaultFavorties;
	}

	public void setDefaultFavorties(String defaultFavorties) {
		this.defaultFavorties = defaultFavorties;
	}

	public String getDefaultCollectType() {
		return defaultCollectType;
	}

	public void setDefaultCollectType(String defaultCollectType) {
		this.defaultCollectType = defaultCollectType;
	}

	public String getDefaultModel() {
		return defaultModel;
	}

	public void setDefaultModel(String defaultModel) {
		this.defaultModel = defaultModel;
	}

	public Long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCollectTypeName() {
		return defaultCollectType.equals("private")?"私密":"公开";
	}

	public void setCollectTypeName(String collectTypeName) {
		this.collectTypeName = collectTypeName;
	}

	public String getModelName() {
		return defaultModel.equals("simple")?"简单":"专业";
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getClearName() {
		if(clearCollect!=null&&clearCollect.equals("auto")){
			return "自动";
		}else{
			return "手动";
		}
	}

	public void setClearName(String clearName) {
		this.clearName = clearName;
	}

}