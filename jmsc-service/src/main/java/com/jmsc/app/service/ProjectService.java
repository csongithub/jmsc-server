/**
 * 
 */
package com.jmsc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.ProjectDTO;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.Project;
import com.jmsc.app.repository.ProjectRepository;

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
}
