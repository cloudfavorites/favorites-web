package com.favorites.service;

import com.favorites.domain.Feedback;

/**
 * Created by chenzhimin on 2017/2/23.
 */
public interface FeedbackService {

    public void saveFeeddback(Feedback feedback,Long userId);
}
