package com.favorites.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.favorites.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class CollectRepositoryTest {

	@Autowired
	private CollectRepository collectRepository;

	@Test
	public void test() throws Exception {
		int count=collectRepository.modifyById("public", 4l);
		Assert.assertEquals(1, count);
	}


}