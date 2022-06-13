/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PostgresBackup implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1231099072340870084L;
	
	private boolean backupDone;
	
	private String backupFileName;
	
	private String backupFormat;
	
	private String backupPath;
	
	private String backupRemotePath;
}
