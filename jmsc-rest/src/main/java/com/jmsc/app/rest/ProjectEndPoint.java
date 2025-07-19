/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.ProjectDTO;
import com.jmsc.app.service.ProjectService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/projects")
@Api(value = "APIs to handle project's data")
public class ProjectEndPoint {
	
	@Autowired
	ProjectService projectService;
	
	
	
	@PostMapping("/create_or_update")
	public ResponseEntity<ProjectDTO> addSite(@RequestBody ProjectDTO projectDTO){
		ProjectDTO project = projectService.createOrUpdateProject(projectDTO);
		return ResponseEntity.ok(project);
	}
	
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<ProjectDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<ProjectDTO> all = projectService.getAllProjects(clientId);
		return ResponseEntity.ok(all);
	}
}
