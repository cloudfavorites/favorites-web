package com.favorites.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by DingYS on 2016/12/29.
 */
@Entity
public class UrlLibrary extends Entitys implements Serializable{

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = true)
    private String logoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
