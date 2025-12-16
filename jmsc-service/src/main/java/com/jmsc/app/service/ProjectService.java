/**
 * 
 */
package com.jmsc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.ProjectDTO;
import com.jmsc.app.common.dto.accounting.Item;
import com.jmsc.app.common.dto.accounting.ListDTO;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.Project;
import com.jmsc.app.repository.accounting.ProjectRepository;

/**
 * @author anuhr
 *
 */
@Service
public class ProjectService extends AbstractService{
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	public ProjectDTO createOrUpdateProject(ProjectDTO projectDTO) {
		if(isNull(projectDTO.getClientId()))
			throw new RuntimeException("Insufficient Data");
		
		Project project  = ObjectMapperUtil.map(projectDTO, Project.class);
		
		project = projectRepository.save(project);
		
		ProjectDTO savedProject = ObjectMapperUtil.map(project, ProjectDTO.class);
		
		return savedProject;
	}
	
	
	
	public List<ProjectDTO> getAllProjects(Long clientId){
		if(isNull(clientId))
			throw new RuntimeException("Invalid Request");
		
		List<Project> projects = projectRepository.findAllByClientId(clientId);
		
		List<ProjectDTO> all = ObjectMapperUtil.mapAll(projects, ProjectDTO.class);
		
		return all;
	}
	
	
	public ListDTO getProjectList(Long clientId){
		if(isNull(clientId))
			throw new RuntimeException("Invalid Request");
		
		ListDTO list = new ListDTO();
		list.setListName("Projects");
		
		List<Project> projects = projectRepository.findAllByClientId(clientId);
		
		if(Collections.isEmpty(projects))
			return list;
		
		projects.forEach(c ->  {
			Item item  = new Item();
			item.setLabel(c.getNickName());
			item.setValue(c.getId());
			list.getList().add(item);
		});
		
		ListDTO.sortByLevel(list);
		
		return list;
	}
}
