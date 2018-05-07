package com.favorites.service;

import com.favorites.domain.view.CollectSummary;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 浏览记录service接口
 * Created by chenzhimin on 2017/1/12.
 */
public interface LookRecordService {

	void saveLookRecord (Long userId, Long collectId);

	void deleteLookRecord (Long userId, Long collectId);

	void deleteLookRecordByUserID (Long userId);

	List<CollectSummary> getLookRecords (Long userId, Pageable pageable);

}
