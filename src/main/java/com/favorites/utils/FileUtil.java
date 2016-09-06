package com.favorites.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
*@ClassName: FileUtil
*@Description: 
*@author YY 
*@date 2016年8月26日  上午10:26:17
*@version 1.0
*/
public class FileUtil {

	/**
	 * 获取文件类型
	 * @param fileName
	 * @return
	 */
	public static String getFileExtName(String fileName) {
        if (fileName!=null ) {
            int i = fileName.lastIndexOf('.');
            if (i>-1) {
                return fileName.substring(i+1).toLowerCase();
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
	
	/**
	 * 上传文件
	 * @param file
	 * @param path 路径+文件名
	 * @throws Exception
	 */
	public static void uploadFile(byte[] file, String path) throws Exception {
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path)));
		out.write(file);
		out.flush();
		out.close();	
	}

}