package com.favorites.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.favorites.domain.Follow;
import com.favorites.domain.FollowRepository;
import com.favorites.domain.result.ExceptionMsg;
import com.favorites.domain.result.Response;
import com.favorites.utils.DateUtils;

@RestController
@RequestMapping("/follow")
public class FollowController extends BaseController{
	
	@Autowired
	private FollowRepository followRepository;
	
	/**
	 * 关注&取消关注
	 * @return
	 */
	@RequestMapping("/changeFollowStatus")
	public Response changeFollowStatus(String status,Long userId){
		logger.info("status:" + status + "----userId:" + userId);
		try {
			Follow follow = followRepository.findByUserIdAndFollowId(getUserId(), userId);
			if(null != follow){
				followRepository.updateStatusById(status, DateUtils.getCurrentTime(), follow.getId());
			}else{
				follow = new Follow();
				follow.setFollowId(userId);
				follow.setUserId(getUserId());
				follow.setStatus(status);
				follow.setCreateTime(DateUtils.getCurrentTime());
				follow.setLastModifyTime(DateUtils.getCurrentTime());
				followRepository.save(follow);
			}
		} catch (Exception e) {
			logger.error("异常：",e);
			return result(ExceptionMsg.FAILED);
		}
		return result();
	}

}
