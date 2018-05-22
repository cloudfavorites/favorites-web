package com.favorites.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DingYS on 2016/12/29.
 */
@Entity
public class UrlLibrary extends Entitys implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(600)")
    private String url;
    @Column(nullable = true,columnDefinition = "varchar(300)")
    private String logoUrl;
    @Column(columnDefinition="INT default 0")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
