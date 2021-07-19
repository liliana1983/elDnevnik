package com.iktpreobuka.elektronskidnevnik.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.SubjectDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskidnevnik.util.RestError;


@RestController
@RequestMapping(path = "/subject")
public class SubjectController {
@Autowired
SubjectRepository subjectRepository;
@Autowired
TeacherRepository teacherRepository;
@Autowired
ClassesRepository classesRepository;
	
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
		if(subjectRepository.existsById(subjectId)) {
		if(teacherRepository.existsById(teacherId)){
				teacherRepository.findById(teacherId);
				subjectRepository.findById(subjectId);
				TeacherEntity teacher= teacherRepository.findById(teacherId).get();
				SubjectEntity subject= subjectRepository.findById(subjectId).get();
				subject.getTeacher().add(teacher);
				teacher.getSubject().add(subject);
				teacherRepository.save(teacher);
				subjectRepository.save(subject);
				return new ResponseEntity<>(subject,HttpStatus.CREATED);
			}
		}return new ResponseEntity<RestError>(new RestError(5,"teacher or subject not found"),HttpStatus.NOT_FOUND);
	}
	@Secured("ROLE_ADMIN")
	@PutMapping(value="/addSubjectToClass")
	public ResponseEntity<?> addSubjectToClass(@RequestParam Integer subjectId,@RequestParam Integer classId){
		if(subjectRepository.existsById(subjectId)) {
			if(classesRepository.existsById(classId)) {
				subjectRepository.findById(subjectId);
				classesRepository.findById(classId);
				SubjectEntity subject= subjectRepository.findById(subjectId).get();
				ClassesEntity classes=classesRepository.findById(classId).get();
				subject.getClasses().add(classes);
				classes.getListOfSubjects().add(subject);
				subjectRepository.save(subject);
				classesRepository.save(classes);
				return new ResponseEntity<>(classes,HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<RestError>(new RestError(6,"class or subject not found"),HttpStatus.NOT_FOUND);
	}
}
