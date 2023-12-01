/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.FileMetaData;
import com.jmsc.app.entity.NoDataFileMetaData;

/**
 * @author anuhr
 *
 */
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Long> {
	
	List<NoDataFileMetaData> findAllByClientIdAndDirectoryIdAndSystemPath(Long clientId, Long directoryId, String systemPath);
	
	Optional<FileMetaData> findByClientIdAndDirectoryIdAndId(Long clientId, Long directoryId, Long id);
	
	List<NoDataFileMetaData> findAllByClientId(Long clientId);
	
	List<NoDataFileMetaData> findAllByClientIdAndSystemPathAndFileName(Long clientId, String systemPath, String fileName);
}
