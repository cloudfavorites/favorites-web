package com.favorites.domain.view;

/**
 * @Description:
 * @Auth: yuyang
 * @Date: 2017/1/19 14:18
 * @Version: 1.0
 **/
public class IndexCollectorView {
    //收藏文章最多的用户
    private Long mostCollectUser;
    //被关注最多的用户
    private Long mostFollowedUser;
    //文章被赞最多的用户
    private Long mostPraisedUser;
    //文章被评论最多的用户
    private Long mostCommentedUser;
    //最受欢迎的用户
    private Long mostPopularUser;
    //近一个月最活跃用户
    private Long mostActiveUser;

    public Long getMostCollectUser() {
        return mostCollectUser;
    }

    public void setMostCollectUser(Long mostCollectUser) {
        this.mostCollectUser = mostCollectUser;
    }

    public Long getMostFollowedUser() {
        return mostFollowedUser;
    }

    public void setMostFollowedUser(Long mostFollowedUser) {
        this.mostFollowedUser = mostFollowedUser;
    }

    public Long getMostPraisedUser() {
        return mostPraisedUser;
    }

    public void setMostPraisedUser(Long mostPraisedUser) {
        this.mostPraisedUser = mostPraisedUser;
    }

    public Long getMostCommentedUser() {
        return mostCommentedUser;
    }

    public void setMostCommentedUser(Long mostCommentedUser) {
        this.mostCommentedUser = mostCommentedUser;
    }

    public Long getMostPopularUser() {
        return mostPopularUser;
    }

    public void setMostPopularUser(Long mostPopularUser) {
        this.mostPopularUser = mostPopularUser;
    }

    public Long getMostActiveUser() {
        return mostActiveUser;
    }

    public void setMostActiveUser(Long mostActiveUser) {
        this.mostActiveUser = mostActiveUser;
    }
}
