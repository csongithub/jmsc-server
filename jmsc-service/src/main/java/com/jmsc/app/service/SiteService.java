/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.SiteDTO;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.Bid;
import com.jmsc.app.entity.Site;
import com.jmsc.app.repository.BidRepository;
import com.jmsc.app.repository.SiteRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class SiteService {
	
	
	@Autowired
	private SiteRepository repository;
	
	public SiteDTO createSite(SiteDTO siteDTO) {
		log.debug("Inside createSite method");
		
		if(siteDTO.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(Strings.isNullOrEmpty(siteDTO.getSiteName()) 
				|| Strings.isNullOrEmpty(siteDTO.getDisplayName())) {
			throw new RuntimeException("Incorrect Site or Display name");
		}
		
		siteDTO.toUppercase();
	
		if(siteDTO.getId() == null) {
			Optional<Site> optional = repository.findBySiteNameOrDisplayName(siteDTO.getSiteName(), siteDTO.getDisplayName());
			if(optional.isPresent()) {
				throw new RuntimeException("Site already exists with same Site Name or Display Name");
			}
		}
		
		Site site = ObjectMapperUtil.map(siteDTO, Site.class);
		
		
		Site entity = repository.save(site);

		if(site.getBidLinkageId() != null) {
			BidRepository bidRepo = ServiceLocator.getService(BidRepository.class);
			Optional<Bid> optionalBid = bidRepo.findById(site.getBidLinkageId());
			if(optionalBid.isPresent()) {
				Bid bid = optionalBid.get();
				bid.setSiteId(entity.getId());
				
				bidRepo.save(bid);
			}
		}
		
		SiteDTO newSite = ObjectMapperUtil.map(entity, SiteDTO.class);
		
		return newSite;
	}
	
	
	public List<SiteDTO> getAllSites(Long clientId){
		List<Site> all = repository.findByClientId(clientId);
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<SiteDTO>();
		}
		
		List<SiteDTO> allDTO = ObjectMapperUtil.mapAll(all, SiteDTO.class);
		return allDTO;
	}

}
