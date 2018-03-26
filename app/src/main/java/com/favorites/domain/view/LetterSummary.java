package com.favorites.domain.view;

import com.favorites.utils.DateUtils;

import java.io.Serializable;

/**
 * Created by DingYS on 2017/3/8.
 */
public class LetterSummary implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long sendUserId;
    private String sendUserName;
    private String profilePicture;
    private String content;
    private String createTime;
    private Long pid;
    private String type;

    public LetterSummary(LetterView view){
        this.id = view.getId();
        this.sendUserId = view.getSendUserId();
        this.sendUserName = view.getSendUserName();
        this.profilePicture = view.getProfilePicture();
        this.content = view.getContent();
        this.createTime = DateUtils.getTimeFormatText(view.getCreateTime());
        this.pid = view.getPid();
        this.type = view.getType();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
