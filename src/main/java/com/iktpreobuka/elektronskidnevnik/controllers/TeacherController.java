package com.iktpreobuka.elektronskidnevnik.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.TeacherDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.UserRepository;
import com.iktpreobuka.elektronskidnevnik.services.TeacherService;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;
import com.iktpreobuka.elektronskidnevnik.util.RestError;
import com.iktpreobuka.elektronskidnevnik.util.UserCustomValidator;
import com.iktpreobuka.elektronskidnevnik.util.Validation;

@RestController
@RequestMapping(path = "/user/teacher")
public class TeacherController {
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	TeacherService teacherService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ClassesRepository classesRepository;

	@Autowired
	private UserCustomValidator userCustomValidator;
	
	@Autowired
	private Validator[] usernameValidator;

	@InitBinder("user")
	protected void initUserBinder(WebDataBinder binder) {
		binder.addValidators(userCustomValidator);
	}

	@InitBinder("username")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(usernameValidator);
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllTeachers() {
		List<TeacherEntity> teachers = new ArrayList<>();
		teachers = teacherService.findAllTeachers();
		return new ResponseEntity<>(teachers, HttpStatus.FOUND);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/addTeacher")
	public ResponseEntity<?> createTeacher(@Valid @RequestBody TeacherDTO newTeacher, @RequestParam Integer roleId,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		TeacherEntity teacher = new TeacherEntity();
		teacher.setName(newTeacher.getName());
		teacher.setLastName(newTeacher.getLastName());
		teacher.setPassword(Encryption.getPassEncoded(newTeacher.getPassword()));
		teacher.setPassword(Encryption.getPassEncoded(newTeacher.getConfirmPassword()));
		teacher.setUsername(newTeacher.getUsername());
		RoleEntity rola = roleRepository.findById(roleId).get();
		teacher.setRole(rola);
		teacherRepository.save(teacher);
		logger.info(teacher.toString(), "teacher added");
		return new ResponseEntity<>(teacher, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping(value = "/delClasAndTeacher")
	public ResponseEntity<?> delClass(@RequestParam Integer classId, @RequestParam Integer teacherId) {
		if (classesRepository.existsById(classId)) {
			if (teacherRepository.existsById(teacherId)) {
				teacherRepository.findById(teacherId).get();
				TeacherEntity headMaster = teacherRepository.findById(teacherId).get();
				classesRepository.findById(classId).get();
				ClassesEntity classes = classesRepository.findById(classId).get();
				teacherRepository.save(headMaster);
				classesRepository.save(classes);
				logger.info("class and teacher deleted");
				return new ResponseEntity<>(headMaster, HttpStatus.OK);
			}
		}
		return new ResponseEntity<RestError>(new RestError(6, "teacher or class with that id doesnt exist"),
				HttpStatus.OK);
	}
	@Secured("ROLE_ADMIN")
	@PutMapping(value="/changeTeacher")
	public ResponseEntity<?> changeTeacher (@RequestParam Integer teacherId,@Valid@RequestBody TeacherDTO changedTeacher, BindingResult result ){
		if(result.hasErrors())
			 return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		if(teacherRepository.existsById(teacherId)) {
			TeacherEntity teacher= teacherRepository.findById(teacherId).get();
			teacher.setName(Validation.setIfNotNull(teacher.getName(),changedTeacher.getName()));
			teacher.setLastName(Validation.setIfNotNull(teacher.getLastName(),changedTeacher.getLastName()));
			teacher.setUsername(Validation.setIfNotNull(teacher.getUsername(),changedTeacher.getUsername()));
			teacher.setPassword(Validation.setIfNotNull(teacher.getPassword(),changedTeacher.getPassword()));
			teacher.setPassword(Validation.setIfNotNull(teacher.getPassword(), changedTeacher.getConfirmPassword()));
			return new ResponseEntity<>(teacher,HttpStatus.OK);
		}
		return new ResponseEntity<RestError>(new RestError(10,"Teacher with this Id doesnt exist!"),HttpStatus.BAD_REQUEST);
	}
}