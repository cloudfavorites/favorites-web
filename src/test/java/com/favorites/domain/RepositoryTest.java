package com.favorites.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

	@Autowired
	private PraiseRepository praiseRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void testPraise() throws Exception {
		long count=praiseRepository.countByCollectId(1l);
		System.out.println("count===="+count);
		Praise praise=praiseRepository.findByUserIdAndCollectId(1l, 1l);
		System.out.println("exists===="+praise);

	}
	
	
	@Test
	public void testComment() throws Exception {
		long count=commentRepository.countByCollectId(1l);
		System.out.println("count===="+count);
	    
	}
	

}