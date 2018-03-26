package com.favorites.web;

import com.favorites.domain.Feedback;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenzhimin on 2017/2/23.
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends BaseController{
    @Autowired
    private FeedbackService feedbackService;

    /**
     * @author chenzhimin
     * @date 2017年1月23日
     * @return
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public Response save(Feedback feedback) {
        try {
              feedbackService.saveFeeddback(feedback,getUserId());
        } catch (Exception e) {
            logger.error("feedback failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }
}
