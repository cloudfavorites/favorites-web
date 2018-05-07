package com.favorites.service;

import com.favorites.domain.Letter;
import com.favorites.domain.view.LetterSummary;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by DingYS on 2017/3/8.
 */
public interface LetterService {

	void sendLetter (Letter letter);

	List<LetterSummary> findLetter (Long userId, Pageable pageable);
}
