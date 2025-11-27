/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ListDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4963198208707063135L;
	
	private String listName = null;
	
	private List<Item> list = new ArrayList<Item>();
	
	
	public static void sortByLevel(ListDTO list) {
		java.util.Collections.sort(list.getList(), new Comparator<Item>() {
	        public int compare(Item item1, Item item2) {
	            return item1.getLabel().compareTo(item2.getLabel());
	        }
	    });
	}

}
