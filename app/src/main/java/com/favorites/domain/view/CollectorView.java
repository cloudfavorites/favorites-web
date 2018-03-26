package com.favorites.domain.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Description:
 * @Auth: yuyang
 * @Date: 2017/1/17 12:25
 * @Version: 1.0
 **/
@Entity
public class CollectorView {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="userId",length=20)
    private Long userId;
    @Column(name="counts",length=20)
    private Long counts;

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

    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }
}
