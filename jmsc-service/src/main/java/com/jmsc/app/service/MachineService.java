/**
 * 
 */
package com.jmsc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.repository.MachineRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Slf4j
@Service
public class MachineService {
	
	@Autowired
	private MachineRepository repository;
	
}
