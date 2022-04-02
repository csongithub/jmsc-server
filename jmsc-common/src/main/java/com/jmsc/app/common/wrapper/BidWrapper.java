/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;

import com.jmsc.app.common.dto.BidCostDTO;
import com.jmsc.app.common.dto.BidDTO;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class BidWrapper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1568901718448354791L;

	
	private BidDTO bid;
	
	private BidCostDTO bidCost;
}
