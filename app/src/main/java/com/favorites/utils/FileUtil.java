package com.favorites.utils;

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
	 * @param fileBytes
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadFile(byte[] fileBytes, String filePath, String fileName) throws Exception {	
		File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(fileBytes);
        out.flush();
        out.close();
	}

}