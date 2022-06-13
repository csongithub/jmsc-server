/**
 * 
 */
package com.jmsc.app.service.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.rqrs.PostgresBackup;
import com.jmsc.app.config.datasource.PostgresConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class PostgresService {
	
	
	@Autowired
	private PostgresConfig config;
	
	public PostgresBackup startBackup() throws Throwable {
		
		System.out.println("*************************************************");
		System.out.println("*         Starting Database Backup Now");
		System.out.println("*************************************************");
		
		
		
		PostgresBackup backup = new PostgresBackup();
		
		String pg_dump = config.getPgDumpAgent();
		String outputDitectory = config.getOutputDirectory();
		String format = config.getFormat();
		
		String fileName = this.getFileName(format);
		backup.setBackupFileName(fileName);
		backup.setBackupFormat(format);
		
		String file = "";
		
		if(outputDitectory.trim().endsWith("\\"))
			file = outputDitectory + fileName;
		else
			file = outputDitectory + "\\" + fileName;
		
		File fileObject = new File(file);
		
		backup.setBackupPath(fileObject.getAbsolutePath());
		
		String host = config.getHost();
		String port = config.getPort();
		String database = config.getDatabase();
		String username = config.getUsername();
		String password = config.getPassword();
		
		
	    Process p;
	    ProcessBuilder pb;
	    pb = new ProcessBuilder(
	    		pg_dump, "--file", file, "--host", host, "--port", port, "--username", username, "--verbose", "--format=t",  "--blobs", database);
	    try {
	        final Map<String, String> env = pb.environment();
	        env.put("PGPASSWORD", password);
	        p = pb.start();
	        final BufferedReader r = new BufferedReader(
	                new InputStreamReader(p.getErrorStream()));
	        String line = r.readLine();
	        while (line != null) {
	            log.info(line);
	            line = r.readLine();
	        }
	        r.close();
	        p.waitFor();
	        if(p.exitValue() == 0) {
	        	backup.setBackupDone(true);
	        }
	        log.info("Exit Code: {}", p.exitValue());

	    } catch (IOException | InterruptedException e) {
	        System.out.println(e.getMessage());
	    }
	    return backup;
	}
	
	
	private String getFileName(String format) {
		TimeZone zonah = TimeZone.getTimeZone("GMT+1");
	    Calendar Calendario = GregorianCalendar.getInstance( zonah, new java.util.Locale("es"));
	    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	    StringBuffer date = new StringBuffer();
	    date.append(df.format(Calendario.getTime()));
	    
	    StringBuffer file = new StringBuffer("jmsc_db_").append(date.toString()).append(".").append(format);
	    System.out.println(file.toString());
	    return file.toString();
	}
	
	
	public static void main(String args[]) throws Throwable {
		PostgresService service = new PostgresService();
		service.getFileName("tar");
	}
}
