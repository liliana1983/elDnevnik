package com.iktpreobuka.elektronskidnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.GuardianEntity;
import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.ClassDTO;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GuardianDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskidnevnik.services.ClassesService;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;
import com.iktpreobuka.elektronskidnevnik.util.RestError;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path = "/class")
public class ClassController {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	ClassesService classesService;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	RoleRepository roleRepository;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	ClassesRepository classesRepository;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/addClass", method = RequestMethod.POST)
	public ResponseEntity<?> addClass(@Valid @RequestBody ClassesEntity newClass, @RequestParam Integer headMasterId,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		classesService.addClassWithHeadMAster(newClass, headMasterId);
		logger.info("class created");
		return new ResponseEntity<>(newClass, HttpStatus.CREATED);

	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/{classId}/addStudent", method = RequestMethod.PUT)
	public ResponseEntity<?> addOneToClass(@Valid @RequestParam Integer studentId, @PathVariable Integer classId) {
		classesService.addOneStudent(studentId, classId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/deleteClass/{classId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClass(@PathVariable Integer classId) {
		if (classesRepository.existsById(classId)) {
			ClassesEntity delClass = classesRepository.findById(classId).get();
			classesRepository.delete(delClass);
			return new ResponseEntity<>(delClass, HttpStatus.OK);

		}
		return new ResponseEntity<RestError>(new RestError(3, "this class doesnt exist"), HttpStatus.NOT_FOUND);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/addHeadMaster/{classId}")
	public ResponseEntity<?> addHeadMaster(@RequestParam Integer headMasterId, @PathVariable Integer classId,
			@RequestParam Integer roleId) {
		if (classesRepository.existsById(classId)) {
			if (teacherRepository.existsById(headMasterId)) {
				teacherRepository.findById(headMasterId).get();
				TeacherEntity headMaster = teacherRepository.findById(headMasterId).get();
				if (headMaster.getRole() != null) {
					roleRepository.findById(roleId).get();
					RoleEntity rola = roleRepository.findById(roleId).get();
					headMaster.setRole(rola);
					classesRepository.findById(classId).get();
					ClassesEntity classes = classesRepository.findById(classId).get();
					classes.setHeadMaster(headMaster);
					headMaster.setHeadOfClass(classes);
					teacherRepository.save(headMaster);
					classesRepository.save(classes);
					return new ResponseEntity<>(headMaster, HttpStatus.ACCEPTED);
				}
			}
		}
		return new ResponseEntity<RestError>(
				new RestError(5, "Teacher with this ID or class with this ID doesnt Exist"), HttpStatus.BAD_REQUEST);
	}
	/*
	 * @Secured("ROLE_ADMIN")
	 * 
	 * @PutMapping(value="addHeadMaster") public
	 */
	/*
	 * @Secured("ROLE_ADMIN")
	 * 
	 * @RequestMapping(value="/{classId}/addStudents",method=RequestMethod.PUT)
	 * public ResponseEntity<?> addListOfStudents(@Valid@RequestParam Integer
	 * studentsIds, @PathVariable Integer classId){
	 * classesService.addListOfStudents(studentsIds, classId); return new
	 * ResponseEntity<>(HttpStatus.OK);
	 */

}
