package com.iktpreobuka.elektronskidnevnik.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.iktpreobuka.elektronskidnevnik.util.Validation;

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
	public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectDTO newSubject, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		SubjectEntity subject = new SubjectEntity();
		subject.setName(newSubject.getName());
		subject.setHoursPerWeek(newSubject.getHoursPerWeek());
		subjectRepository.save(subject);
		logger.info("subject created");
		return new ResponseEntity<>(subject, HttpStatus.CREATED);
	}

	

	@Secured("ROLE_ADMIN")
	@PutMapping(path = "/addTeacherForSubject")
	public ResponseEntity<?> addTeacherForSubject(@RequestParam Integer teacherId, @RequestParam Integer subjectId) {
		if (subjectRepository.existsById(subjectId)) {
			if (teacherRepository.existsById(teacherId)) {
				teacherRepository.findById(teacherId);
				subjectRepository.findById(subjectId);
				TeacherEntity teacher = teacherRepository.findById(teacherId).get();
				SubjectEntity subject = subjectRepository.findById(subjectId).get();
				subject.getTeacher().add(teacher);
				teacher.getSubject().add(subject);
				teacherRepository.save(teacher);
				subjectRepository.save(subject);
				logger.info("Teacher connected with Subject");
				return new ResponseEntity<>(subject, HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<RestError>(new RestError(5, "teacher or subject not found"), HttpStatus.NOT_FOUND);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/addSubjectToClass")
	public ResponseEntity<?> addSubjectToClass(@RequestParam Integer subjectId, @RequestParam Integer classId) {
		if (subjectRepository.existsById(subjectId)) {
			if (classesRepository.existsById(classId)) {
				subjectRepository.findById(subjectId);
				classesRepository.findById(classId);
				SubjectEntity subject = subjectRepository.findById(subjectId).get();
				ClassesEntity classes = classesRepository.findById(classId).get();
				subject.getClasses().add(classes);
				classes.getListOfSubjects().add(subject);
				subjectRepository.save(subject);
				classesRepository.save(classes);
				logger.info("Subject and class connected");
				return new ResponseEntity<>(classes, HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<RestError>(new RestError(6, "class or subject not found"), HttpStatus.NOT_FOUND);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping(path = "/deleteSubject")
	public ResponseEntity<?> delSubject(@RequestParam Integer Id) {
		if (subjectRepository.existsById(Id)) {
			SubjectEntity subject = subjectRepository.findById(Id).get();
			subjectRepository.delete(subject);
			logger.info("Subject deleted");
			return new ResponseEntity<>(subject, HttpStatus.OK);
		}

		return new ResponseEntity<RestError>(new RestError(11, "Subject under that id doesnt exist"),
				HttpStatus.BAD_REQUEST);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/changeSubject")
	public ResponseEntity<?> changedSubject(@RequestParam Integer subjectId,
			@Valid @RequestBody SubjectDTO changedSubject, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		if (subjectRepository.existsById(subjectId)) {
			SubjectEntity subject = subjectRepository.findById(subjectId).get();
			subject.setName(Validation.setIfNotNull(subject.getName(), changedSubject.getName()));
			subject.setHoursPerWeek(
					Validation.setIfNotNull(subject.getHoursPerWeek(), changedSubject.getHoursPerWeek()));
			logger.info("subject changed" + subject.getName() + " " + subject.getHoursPerWeek());
			return new ResponseEntity<>(subject, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<RestError>(new RestError(11, "subject under given id doesnt exist"),
				HttpStatus.BAD_REQUEST);
	}
	@Secured({"ROLE_ADMIN","ROLE_HEADMASTER"})
		@GetMapping(value="/")
		public ResponseEntity<?> getAllSubjects(){
		logger.info("All Subjects listed");
		return new ResponseEntity<>(subjectRepository.findAll(),HttpStatus.OK);
	}
	}

