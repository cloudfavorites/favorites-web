package com.favorites.web;

import com.favorites.domain.result.Response;
import com.favorites.service.LookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenzhimin on 2017/2/14.
 */
@RestController
@RequestMapping("/lookRecord")
public class LookRecordController extends BaseController{

    @Autowired
    private LookRecordService lookRecordService;

    /**
     * @author chenzhimin
     * @date 2017年1月23日
     * @param collectId 收藏ID
     * @return
     */
    @RequestMapping(value="/save/{collectId}")
    public Response saveLookRecord(@PathVariable("collectId") long collectId) {
        lookRecordService.saveLookRecord(this.getUserId(),collectId);
        return result();
    }

    /**
     * @author chenzhimin
     * @date 2017年1月23日
     * @param collectId 收藏ID
     * @return
     */
    @RequestMapping(value="/delete/{collectId}")
    public Response deleteLookRecord(@PathVariable("collectId") long collectId) {
        lookRecordService.deleteLookRecord(this.getUserId(),collectId);
        return result();
    }

    /**
     * @author chenzhimin
     * @date 2017年1月23日
     * @return
     */
    @RequestMapping(value="/deleteAll")
    public Response deleteAll() {
        lookRecordService.deleteLookRecordByUserID(this.getUserId());
        return result();
    }
}
