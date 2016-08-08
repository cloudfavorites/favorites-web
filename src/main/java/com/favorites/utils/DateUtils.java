package com.favorites.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	
	public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmssSSS";
	
    /**
     * @return
     * @author ZQ
     * @date 2015-5-21
     */
    public static String getDateSequence() {
        return new SimpleDateFormat(YYYYMMDDHHMMSS).format(new Date());
    }

}
