package com.iktpreobuka.elektronskidnevnik.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.ClassDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskidnevnik.services.ClassesService;
import com.iktpreobuka.elektronskidnevnik.util.ClassesValidator;
import com.iktpreobuka.elektronskidnevnik.util.RestError;
import com.iktpreobuka.elektronskidnevnik.util.Validation;

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
	
	@Autowired
	ClassesValidator classesValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(classesValidator);
	}
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	ClassesRepository classesRepository;

	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/addClass", method = RequestMethod.POST)
	public ResponseEntity<?> addClass(@Valid @RequestBody ClassDTO newClass, @RequestParam Integer headMasterId,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		classesService.addClassWithHeadMAster(newClass, headMasterId);
		logger.info("class created");
		return new ResponseEntity<>(newClass, HttpStatus.CREATED);

	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/{classId}/addStudent", method = RequestMethod.PUT)
	public ResponseEntity<?> addOneToClass(@Valid @RequestParam Integer studentId, @PathVariable Integer classId) {
		classesService.addOneStudent(studentId, classId);
		logger.info("student added to class");
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/deleteClass/{classId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClass(@PathVariable Integer classId) {
		if (classesRepository.existsById(classId)) {
			ClassesEntity delClass = classesRepository.findById(classId).get();
			classesRepository.delete(delClass);
			logger.info("class deleted");
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
					logger.info("headMaster of the class added");
					return new ResponseEntity<>(headMaster, HttpStatus.ACCEPTED);
				}
			}
		}
		return new ResponseEntity<RestError>(
				new RestError(5, "Teacher with this ID or class with this ID doesnt Exist"), HttpStatus.BAD_REQUEST);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/addTeachersToClass")
	public ResponseEntity<?> addTeacherToClass(@RequestParam Integer classId, @RequestParam Integer teacherId) {
		if (classesRepository.existsById(classId)) {
			if (teacherRepository.existsById(teacherId)) {
				classesRepository.findById(classId).get();
				ClassesEntity classes = classesRepository.findById(classId).get();
				teacherRepository.findById(teacherId).get();
				TeacherEntity teacher = teacherRepository.findById(teacherId).get();
				teacher.getClasses().add(classes);
				classes.getTeacher().add(teacher);
				teacherRepository.save(teacher);
				classesRepository.save(classes);
				logger.info("teacher added in class");
 return new ResponseEntity<>(classes,HttpStatus.OK);
			}
		}return new ResponseEntity<RestError>(new RestError(5,"Teacher with given id or class with given id doesnt exist"),HttpStatus.BAD_REQUEST);
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
