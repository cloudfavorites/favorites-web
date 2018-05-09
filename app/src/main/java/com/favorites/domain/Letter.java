package com.favorites.domain;

import com.favorites.domain.enums.LetterType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 私信
 * 
 * @author DingYS
 * 
 */
@Entity
public class Letter extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long sendUserId;
	@Column(nullable = false, length = 65535, columnDefinition = "Text")
	private String content;
	@Column(nullable = false)
	private Long receiveUserId;
	@Column(nullable = true)
	private Long pid;
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LetterType type;
	@Column(nullable = false)
	private Long createTime;
	@Transient
	private String sendType;


	public Letter() {
		super();
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public LetterType getType() {
		return type;
	}

	public void setType(LetterType type) {
		this.type = type;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}