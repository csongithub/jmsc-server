/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jmsc.app.common.enums.EFileType;
import com.sun.istack.NotNull;

import lombok.Data;
/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "FILE_META_DATA")
public class FileMetaData extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3437416531057732059L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "DIR_ID")
	private Long directoryId;
	
	@NotNull
	@Column(name = "SYS_PATH")
	private String systemPath;
	
	@NotNull
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_SIZE")
	private String fileSize;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "FILE_TYPE")
	private EFileType fileType;
	
	@Column(name = "FILE_PATH")
	private String filePath;
	
	@NotNull
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@NotNull
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileMetaData other = (FileMetaData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
