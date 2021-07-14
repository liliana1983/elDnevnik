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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.GuardianEntity;
import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GuardianDTO;
import com.iktpreobuka.elektronskidnevnik.entities.dto.StudentDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;

@RestController
@RequestMapping(path="/user/student")
public class StudentController {
@Autowired
StudentRepository studentRepository;
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
		student.setGuardian(newStudent.getGuardian());
		studentRepository.save(student);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
private String createErrorMessage(BindingResult result) {
	return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
}

}
