package com.favorites.domain;

import com.favorites.domain.enums.IsDelete;
import com.favorites.domain.view.IndexCollectorView;
import com.favorites.repository.CollectorRepository;
import com.favorites.service.CollectorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

/**
 * @Description: 获取收藏家测试类
 * @Auth: yuyang
 * @Date: 2017/1/18 19:56
 * @Version: 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectorRepositoryTest {

    @PersistenceUnit
    private EntityManagerFactory emf;
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private CollectorService collectorService;

    @Test
    public void test(){
        EntityManager em=emf.createEntityManager();
        String querySql = "SELECT follow_id as user_id,COUNT(1) AS counts FROM follow GROUP BY follow_id ORDER BY counts DESC LIMIT 1";
        Query query=em.createNativeQuery(querySql);
        List objecArraytList = query.getResultList();
        Object[] obj = (Object[]) objecArraytList.get(0);
        System.out.println("+++++++++++++++++++++++++++++++++++++ user_id:"+obj[0]);
        System.out.println("+++++++++++++++++++++++++++++++++++++ counts:"+obj[1]);
        em.close();
    }

    @Test
    public void getMostUser(){
        Long collectUserId = collectorRepository.getMostCollectUser();
        System.out.println("+++++++++++++++++++++++++++++++++++++ collectUserId:"+collectUserId);
        Long followedUserid = collectorRepository.getMostFollowedUser(collectUserId);
        System.out.println("+++++++++++++++++++++++++++++++++++++ followedUserid:"+followedUserid);
        String notUserIds = collectUserId+","+followedUserid;
        Long praiseUserid = collectorRepository.getMostPraisedUser(notUserIds);
        System.out.println("+++++++++++++++++++++++++++++++++++++ praiseUserid:"+praiseUserid);
        notUserIds = notUserIds+","+praiseUserid;
        Long commentUserid = collectorRepository.getMostCommentedUser(notUserIds);
        System.out.println("+++++++++++++++++++++++++++++++++++++ commentUserid:"+commentUserid);
        notUserIds = notUserIds+","+commentUserid;
        Long popularUserid = collectorRepository.getMostPopularUser(notUserIds);
        System.out.println("+++++++++++++++++++++++++++++++++++++ popularUserid:"+popularUserid);
        notUserIds = notUserIds+","+popularUserid;
        Long activeUserid = collectorRepository.getMostActiveUser(notUserIds);
        System.out.println("+++++++++++++++++++++++++++++++++++++ activeUserid:"+activeUserid);
    }

    @Test
    public void  getCollectors(){
        IndexCollectorView indexCollectorView = collectorService.getCollectors();
        System.out.println("+++++++++++++++++++++++++++++++++++++ collectors:"+indexCollectorView.getMostActiveUser());
    }


}
