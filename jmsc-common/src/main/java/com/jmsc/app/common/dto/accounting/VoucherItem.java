/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
public class VoucherItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 502492788845488827L;

	private String date;
	
	private String item;
	
	private Double amount;
	
	/**
	 * Food & Grocery, 
	 * Milk & Vegetables,
	 * Drinking Water,
	 * House Rent,
	 * House Hold Item,
	 * Mobile Recharge, 
	 * Personal Grooming,
	 * Site Material,
	 * Site Equipments Purchage,
	 * Machine Rent,
	 * Petrol,
	 * Diesel,
	 * Personal Advance,
	 * Bike Maintenance,
	 * Car Maintenance,
	 * 
	 */
	private String group;
	
	private Long creditorId;
	
	private Long ledgerId;
}
