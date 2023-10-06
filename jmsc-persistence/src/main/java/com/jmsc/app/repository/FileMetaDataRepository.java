/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.FileMetaData;

/**
 * @author anuhr
 *
 */
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Long> {
	
	List<FileMetaData> findAllByClientIdAndDirectoryIdAndSystemPath(Long clientId, Long directoryId, String systemPath);

}
