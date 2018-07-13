package com.favorites.util;

import com.favorites.domain.Praise;
import org.junit.Test;

public class TestString {

    @Test
    public void testRealUrl() throws Exception {
        //collect.setUrl(collect.getUrl().substring(0,collect.getUrl().indexOf("?")));
        String url="http://jartto.wang/2018/03/29/audition-of-f2e/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io";
        if(url.contains("?")){
            url=url.substring(0,url.indexOf("?"));
        }
        System.out.println(url);
    }
}
