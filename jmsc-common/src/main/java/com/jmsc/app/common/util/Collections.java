/**
 * 
 */
package com.jmsc.app.common.util;

import java.util.List;

/**
 * @author Chandan
 *
 */
public class Collections {
	
	public static final boolean isNotNullOrEmpty(List list) {
		return !isNullOrEmpty(list);
	}
	
	
	public static final boolean isNullOrEmpty(List list) {
		if(list == null || list.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static final boolean isNull(List list) {
		if(list == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public static final boolean isEmpty(List list) {
		if(list != null && list.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
}
