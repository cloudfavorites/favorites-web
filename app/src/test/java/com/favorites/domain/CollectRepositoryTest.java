package com.favorites.domain;

import java.util.ArrayList;
import java.util.List;

import com.favorites.domain.enums.IsDelete;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.favorites.domain.view.CollectView;
import com.favorites.repository.CollectRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectRepositoryTest {

	@Autowired
	private CollectRepository collectRepository;

	@Test
	public void test() throws Exception {
	/*	List<CollectView> views=collectRepository.findByUserId(1l);
	    for(CollectView view:views){
	    	System.out.println("collect title ：" +view.getTitle());
	    }	*/
		collectRepository.deleteById(3l);
	}
	
	
	@Test
	public void testFindView() throws Exception {
	 /*   Page<CollectView> views=collectRepository.findByUserId(1l,new PageRequest(0, 10, Direction.ASC, "title"));
	    for(CollectView view:views){
	    	System.out.println("collect title==" +view.getTitle());
	    }*/
	}

	
	@Test
	public void testFindAllView() throws Exception {
	    Page<CollectView> views=collectRepository.findExploreView(1l, new PageRequest(0, 10, Direction.ASC, "title"));
	    for(CollectView view:views){
	    	System.out.print("   collect title==" +view.getTitle());
	    	System.out.print("   FavoriteName==" +view.getFavoriteName());
	    	System.out.print("   UserName==" +view.getUserName());
	    	System.out.println("   Url==" +view.getUrl());
	    }
	}
	
	
	@Test
	public void testFindViewByUserId() throws Exception {
	    Page<CollectView> views=collectRepository.findViewByUserId(2l,new PageRequest(0, 10, Direction.ASC, "title"));
	    for(CollectView view:views){
	    	System.out.print("   collect title==" +view.getTitle());
	    	System.out.print("   FavoriteName==" +view.getFavoriteName());
	    	System.out.print("   UserName==" +view.getUserName());
	    	System.out.println("   Url==" +view.getUrl());
	    }
	}
	
	@Test
	public void testFindViewByUserIdAndFollows() throws Exception {
		List<Long> userIds=new ArrayList<Long>();
		userIds.add(2l);
		userIds.add(3l);
	    Page<CollectView> views=collectRepository.findViewByUserIdAndFollows(2l,userIds,new PageRequest(0, 10, Direction.ASC, "title"));
	    for(CollectView view:views){
	    	System.out.print("   collect title==" +view.getTitle());
	    	System.out.print("   FavoriteName==" +view.getFavoriteName());
	    	System.out.print("   UserName==" +view.getUserName());
	    	System.out.println("   Url==" +view.getUrl());
	    }
	}


	@Test
	public void  Testcount(){
		Long count=collectRepository.countByFavoritesIdAndIsDelete(4L, IsDelete.NO);
		System.out.println("+++++++++++++++++++++++++++++++++++++ count:"+count);
	}

}