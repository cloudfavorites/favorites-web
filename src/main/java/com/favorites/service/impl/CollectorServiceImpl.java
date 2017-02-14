package com.favorites.service.impl;

import com.favorites.domain.view.IndexCollectorView;
import com.favorites.repository.CollectorRepository;
import com.favorites.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 获取收藏家
 * @Auth: yuyang
 * @Date: 2017/1/19 14:14
 * @Version: 1.0
 **/
@Service
public class CollectorServiceImpl implements CollectorService {

    @Autowired
    private CollectorRepository collectorRepository;


    /**
     * 获取收藏家
     * @return
     */
    @Override
    public IndexCollectorView getCollectors() {
        IndexCollectorView indexCollectorView = new IndexCollectorView();
        Long mostCollectUser = collectorRepository.getMostCollectUser();
        indexCollectorView.setMostCollectUser(mostCollectUser);
        Long mostFollowedUser = collectorRepository.getMostFollowedUser(mostCollectUser);
        indexCollectorView.setMostFollowedUser(mostFollowedUser);
        String notUserIds = mostCollectUser+","+mostFollowedUser;
        Long mostPraisedUser = collectorRepository.getMostPraisedUser(notUserIds);
        indexCollectorView.setMostPraisedUser(mostPraisedUser);
        notUserIds += ","+mostPraisedUser;
        Long mostCommentedUser = collectorRepository.getMostCommentedUser(notUserIds);
        indexCollectorView.setMostCommentedUser(mostCommentedUser);
        notUserIds += ","+ mostCommentedUser;
        Long mostPopularUser = collectorRepository.getMostPopularUser(notUserIds);
        indexCollectorView.setMostPopularUser(mostPopularUser);
        notUserIds += ","+ mostPopularUser;
        Long mostActiveUser = collectorRepository.getMostActiveUser(notUserIds);
        indexCollectorView.setMostActiveUser(mostActiveUser);
        return indexCollectorView;
    }
}
