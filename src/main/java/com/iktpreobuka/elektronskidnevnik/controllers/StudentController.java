package com.iktpreobuka.elektronskidnevnik.controllers;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.GuardianEntity;
import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.StudentDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.GuardianRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;
import com.iktpreobuka.elektronskidnevnik.util.RestError;

@RestController
@RequestMapping(path="/user/student")
public class StudentController {
@Autowired
StudentRepository studentRepository;
@Autowired
ClassesRepository classesRepository;
@Autowired
GuardianRepository guardianRepository;
private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

@Autowired
RoleRepository roleRepository;
@Secured("ROLE_ADMIN")
	@PostMapping(value = "/addStudent")
	public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDTO newStudent, @RequestParam Integer roleId,@RequestParam Integer guardianId,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		StudentEntity student = new StudentEntity();
		student.setName(newStudent.getName());
		student.setLastName(newStudent.getLastName());
		student.setPassword(Encryption.getPassEncoded(newStudent.getPassword()));
		student.setPassword(Encryption.getPassEncoded(newStudent.getConfirmPassword()));
		student.setUsername(newStudent.getUsername());
		RoleEntity rola = roleRepository.findById(roleId).get();
		student.setRole(rola);
		if(guardianRepository.existsById(guardianId))
			 guardianRepository.findById(guardianId);
		GuardianEntity guardian =guardianRepository.findById(guardianId).get();
		student.setGuardian(guardian);
			guardianRepository.save(guardian);
		studentRepository.save(student);
		logger.info("student created");
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
private String createErrorMessage(BindingResult result) {
	return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
}
@Secured("ROLE_ADMIN")
@PutMapping(value = "/{studentId}/class/{classId}")
public ResponseEntity<?> addClass(@PathVariable Integer studentId, @PathVariable Integer classId) {
	if (studentRepository.existsById(studentId)) {
		if (classesRepository.existsById(classId)) {
			StudentEntity student = studentRepository.findById(studentId).get();
			student.setEnrolledClass(classesRepository.findById(classId).get());
			studentRepository.save(student);
			logger.info("student add in class");
			return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
		}
		return new ResponseEntity<RestError>(new RestError(1, "Class not found."), HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<RestError>(new RestError(5, "Student not found."), HttpStatus.NOT_FOUND);
}
@Secured("ROLE_ADMIN")
@DeleteMapping(value="/deleteStudent/{studentId}")
public ResponseEntity<?> deleteStudent(@PathVariable Integer studentId){
if(studentRepository.existsById(studentId))
	 studentRepository.findById(studentId).get();
StudentEntity delStudent=studentRepository.findById(studentId).get();
	studentRepository.delete(delStudent);
	logger.info("student deleted");
	return new ResponseEntity<>(delStudent,HttpStatus.OK);

}
}