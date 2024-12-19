/**
 * 
 */
package com.jmsc.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author anuhr
 *
 */
public class MoveFiles {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		 String sourceFolderPath = "C:\\Users\\anuhr\\OneDrive\\Pictures\\AnamikaIphone\\";
	     String targetFolderPath = "D:\\Iphone\\Auto Moved";
	     String targetFolderPathDuplicateNames = "D:\\Iphone\\Auto Moved\\DuplicateNamesFiles";
	     int fileCount = 0;
	     while(true) {
	    	 try {
	    		 fileCount = 0;
	             File sourceFolder = new File(sourceFolderPath);

	             if (sourceFolder.exists() && sourceFolder.isDirectory()) {
	                 File[] files = sourceFolder.listFiles();

	                 if (files != null) {
	                     for (File file : files) {
	                  
	                             Path sourcePath = file.toPath();
	                            
	                             File copiedFile = new File(targetFolderPath + File.separator + file.getName());
	                             Path targetPath = copiedFile.toPath();

	                             if(Files.exists(targetPath)) {
	                            	copiedFile = new File(targetFolderPathDuplicateNames + File.separator + file.getName());
	                            	targetPath = copiedFile.toPath();
	                             }
	                             
	                             Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
	                             
	                             fileCount++;
	                             System.out.println("File moved successfully: " + file.getName());
	                     }
	                 }
	             } else {
	                 System.out.println("Source folder not found: " + sourceFolderPath);
	             }
	         } catch (IOException e) {
	             System.out.println("Error moving file: " + e.getMessage());
	         }
	    	 System.out.println("***********************************");
	    	 System.out.println(fileCount + " Files Copied !");
	    	 System.out.println("Sleeping for one minute...");
	    	 Thread.sleep(60000l);
	     }
	}
}
