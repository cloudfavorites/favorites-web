package com.favorites.repository.impl;

import com.favorites.domain.view.CollectorView;
import com.favorites.repository.BaseNativeSqlRepository;
import com.favorites.repository.CollectorRepository;
import com.favorites.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 获取收藏家
 * @Auth: yuyang
 * @Date: 2017/1/18 19:35
 * @Version: 1.0
 **/
@Service
public class CollectorRepositoryImpl extends BaseNativeSqlRepository implements CollectorRepository {

    /**
     * 收藏文章最多的用户
     * @return
     */
    @Override
    public Long getMostCollectUser() {
        String querySql = "SELECT c.user_id ,COUNT(1) AS counts FROM collect c WHERE type='PUBLIC' AND is_delete='NO' GROUP BY c.user_id ORDER BY counts DESC LIMIT 1";
        List<Object[]> objecArraytList = sqlArrayList(querySql);
        Object[] obj =  objecArraytList.get(0);
        return Long.valueOf(obj[0].toString());
    }

    /**
     * 被关注最多的用户
     * @param notUserId
     * @return
     */
    @Override
    public Long getMostFollowedUser(Long notUserId) {
        String querySql = "SELECT id,follow_id as user_id,COUNT(1) AS counts FROM follow \n" +
                "WHERE status='FOLLOW' and follow_id != " + notUserId +
                " GROUP BY follow_id ORDER BY counts DESC LIMIT 1";
        CollectorView cv = new CollectorView();
        List<CollectorView> list = sqlObjectList(querySql,cv);
        Long userId = list.get(0).getUserId();
        return  userId;
    }

    /**
     * 文章被赞最多的用户
     * @param notUserIds
     * @return
     */
    @Override
    public Long getMostPraisedUser(String notUserIds) {
        String querySql = "SELECT c.user_id,SUM(p.counts) as counts FROM collect c LEFT JOIN \n" +
                "(SELECT collect_id,COUNT(1) as counts FROM praise GROUP BY collect_id)p \n" +
                "ON c.id=p.collect_id WHERE c.type='PUBLIC' AND c.is_delete='NO' AND c.user_id NOT IN (" + notUserIds +") \n" +
                "GROUP BY c.user_id ORDER BY counts DESC LIMIT 1";
        List<Object[]> objecArraytList = sqlArrayList(querySql);
        Object[] obj =  objecArraytList.get(0);
        return Long.valueOf(obj[0].toString());
    }

    /**
     * 文章被评论最多的用户
     * @param notUserIds
     * @return
     */
    @Override
    public Long getMostCommentedUser(String notUserIds) {
        String querySql="SELECT c.user_id,SUM(p.counts) as counts FROM collect c LEFT JOIN \n" +
                "(SELECT collect_id,COUNT(1) as counts FROM `comment` GROUP BY collect_id)p \n" +
                "ON c.id=p.collect_id WHERE c.type='PUBLIC' AND c.is_delete='NO' AND c.user_id NOT IN (" + notUserIds +") \n" +
                "GROUP BY c.user_id ORDER BY counts DESC LIMIT 1";
        List<Object[]> objecArraytList = sqlArrayList(querySql);
        Object[] obj =  objecArraytList.get(0);
        return Long.valueOf(obj[0].toString());
    }

    /**
     * 最受欢迎的用户
     * @param notUserIds
     * @return
     */
    @Override
    public Long getMostPopularUser(String notUserIds) {
        String querySql = "SELECT u.user_id,SUM(u.counts) as counts FROM\n" +
                "(SELECT c.user_id,COUNT(1) as counts FROM collect c LEFT JOIN notice n ON c.id=n.collect_id WHERE c.type='PUBLIC' AND c.is_delete='NO' GROUP BY c.user_id\n" +
                "UNION ALL\n" +
                "SELECT follow_id,COUNT(1) AS counts FROM follow GROUP BY follow_id)u\n" +
                "WHERE u.user_id NOT IN (" + notUserIds + ")\n" +
                "GROUP BY u.user_id ORDER BY counts DESC LIMIT 1";
        List<Object[]> objecArraytList = sqlArrayList(querySql);
        Object[] obj =  objecArraytList.get(0);
        return Long.valueOf(obj[0].toString());
    }

    /**
     * 近一个月最活跃用户
     * @param notUserIds
     * @return
     */
    @Override
    public Long getMostActiveUser(String notUserIds) {
        long nowTime = DateUtils.getCurrentTime();
        long lastMonth = DateUtils.getLastMonthTime();
        String querySql = "SELECT u.user_id,SUM(u.counts) as counts FROM\n" +
                "(SELECT user_id,COUNT(1) as counts FROM collect WHERE create_time>" + lastMonth + " AND create_time<" + nowTime + " AND type='PUBLIC' AND is_delete='NO' GROUP BY user_id\n" +
                "UNION ALL\n" +
                "SELECT user_id,COUNT(1) as counts FROM `comment` WHERE create_time>" + lastMonth + " AND create_time<" + nowTime + " GROUP BY user_id\n" +
                "UNION ALL\n" +
                "SELECT user_id,COUNT(1) as counts FROM praise WHERE create_time>" + lastMonth + " AND create_time<" + nowTime + " GROUP BY user_id\n" +
                "UNION ALL\n" +
                "SELECT user_id,COUNT(1) as counts FROM follow WHERE create_time>" + lastMonth + " AND create_time<" + nowTime + " GROUP BY user_id)u\n" +
                "WHERE u.user_id NOT IN (" + notUserIds + ")\n" +
                "GROUP BY u.user_id ORDER BY counts DESC LIMIT 1";
        List<Object[]> objecArraytList = sqlArrayList(querySql);
        Object[] obj =  objecArraytList.get(0);
        return Long.valueOf(obj[0].toString());
    }
}
