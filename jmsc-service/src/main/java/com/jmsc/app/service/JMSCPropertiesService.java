/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.config.jmsc.JmscProperties;

/**
 * @author Chandan
 *
 */
@Service
public class JMSCPropertiesService {
	
	@Autowired
	private JmscProperties properties;
	
	
	public List<String> getBanks(){
		String[] banksArray = properties.getBanksName().split(",");
		List<String> banks = new ArrayList<String>();
		for(String bank: banksArray) {
			banks.add(bank);
		}
		return banks;
	}
	
	
	public List<String> getBanksBranch(){
		String[] branchArray = properties.getBanksBranch().split(",");
		List<String> branches = new ArrayList<String>();
		for(String branch: branchArray) {
			branches.add(branch);
		}
		return branches;
	}
	
	
	
	public List<String> getPostOffice(){
		String[] postOfficeArray = properties.getPostOfficeBranch().split(",");
		List<String> pos = new ArrayList<String>();
		for(String po: postOfficeArray) {
			pos.add(po);
		}
		return pos;
	}

}
