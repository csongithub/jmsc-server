package com.jmsc.app.common.wrapper;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EMDDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5167259457061864440L;
	
	/**
	 * {online, offline}
	 */
	private String emdMode;
	
	//Offline EMD Details 
	List<EMDWrapper> emdList;
	
	private OnlineDetails onlineDetails;
	
	private String comments;
}
