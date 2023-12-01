/**
 * 
 */
package com.jmsc.app.entity;

import java.util.Date;

/**
 * @author anuhr
 *
 */
public interface NoDataFileMetaData {
	
	public Long getClientId();
	
	public Long getId();
	
	public Long getDirectoryId();
	
	public String getSystemPath();
	
	public String getFileName();
	
	public String getFileSize();
	
	public String getFileType();
	
	public String getFilePath();
	
	public String getCreatedBy();
	
	public String getUpdatedBy();
	
	public String getStatus();
	
	public String getDescription();
	
	public String getContentType();
	
	public Date getCreatedTimestamp();
	
	public Date getUpdatedTimestamp();

}
