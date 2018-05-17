package com.favorites.web;

import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Letter;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.domain.view.LetterSummary;
import com.favorites.service.LetterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by DingYS on 2017/3/7.
 * 私信Controller
 */
@RestController
@RequestMapping("/letter")
public class LetterController extends BaseController {

    @Resource
    private LetterService letterService;

    /**
     * 发送私信
     * @param letter
     * @return
     */
    @RequestMapping("/sendLetter")
    @LoggerManage(description = "发送私信")
    public Response sendLetter(Letter letter){
        try {
            letter.setSendUserId(getUserId());
            letterService.sendLetter(letter);
        }catch (Exception e){
            logger.error("发送私信异常：",e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }

    /**
     * 私信列表获取
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getLetterList")
    @LoggerManage(description = "获取私信列表")
    public List<LetterSummary> getLetterList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "size", defaultValue = "15") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size,sort);
        List<LetterSummary> letterList = letterService.findLetter(getUserId(),pageable);
        return letterList;
    }
}
