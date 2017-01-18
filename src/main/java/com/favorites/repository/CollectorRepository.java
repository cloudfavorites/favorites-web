package com.favorites.repository;

/**
 * @Description: 获取收藏家
 * @Auth: yuyang
 * @Date: 2017/1/18 19:34
 * @Version: 1.0
 **/
public interface CollectorRepository {

    public Long getMostCollectUser();

    public Long getMostFollowedUser();

    public Long getMostPraisedUser();

    public Long getMostCommentedUser();

    public Long getMostPopularUser();

    public Long getMostActiveUser();

}
