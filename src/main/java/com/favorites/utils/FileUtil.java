package com.favorites.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

/**
*@ClassName: FileUtil
*@Description: 
*@author YY 
*@date 2016年8月26日  上午10:26:17
*@version 1.0
*/
public class FileUtil {
	protected static Logger logger = Logger.getLogger(FileUtil.class);
	private static TrackerClient  trackerClient;  
    private static TrackerServer  trackerServer;  
    private static StorageServer  storageServer;  
    private static StorageClient1 storageClient1;
    
    static {         
        try {  
            String classPath = new File(FileUtil.class.getResource("/").getFile()).getCanonicalPath();	
            String configFilePath = classPath + File.separator + "application.properties";            
            ClientGlobal.init(configFilePath);               
            trackerClient = new TrackerClient();  
            trackerServer = trackerClient.getConnection();             
            storageClient1 = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {   
        	logger.error("fastdfs init failed, ", e); 
        }  
    } 
    
	public static String uploadFile(MultipartFile file,String groupServer) throws Exception{ 
        byte[] fileBuff = file.getBytes();  
        String fileUrl = storageClient1.upload_file1(groupServer, fileBuff, getFileExtName(file.getOriginalFilename()), null);
        return fileUrl;
	}

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

}