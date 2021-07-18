package com.iktpreobuka.elektronskidnevnik.controllers;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;


@RestController
@RequestMapping(path = "/subject")
public class SubjectController {
@Autowired
SubjectRepository subjectRepository;
@Autowired
TeacherRepository teacherRepository;
	
private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/addSubject")
	public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectDTO newSubject,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		SubjectEntity subject = new SubjectEntity();
		subject.setName(newSubject.getName());
		subject.setHoursPerWeek(newSubject.getHoursPerWeek());
		subjectRepository.save(subject);
		return new ResponseEntity<>(subject, HttpStatus.CREATED);
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(path="/addTeacherForSubject")
	public ResponseEntity<?> addTeacherForSubject(@RequestParam Integer teacherId,@RequestParam Integer subjectId){
		if(teacherRepository.existsById(teacherId))
	}
}
