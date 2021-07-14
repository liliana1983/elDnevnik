package com.iktpreobuka.elektronskidnevnik.controllers;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.TeacherDTO;
import com.iktpreobuka.elektronskidnevnik.entities.dto.UserDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.UserRepository;
import com.iktpreobuka.elektronskidnevnik.services.TeacherService;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;
import com.iktpreobuka.elektronskidnevnik.util.UserCustomValidator;

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
		return new ResponseEntity<>(teacher, HttpStatus.CREATED);
	}
	
	/*public ResponseEntity<?> changeOfferStatus(@Valid@PathVariable Integer id, @PathVariable EuOfferStatus status) {
	if (offerRepository.existsById(id)) {
		OfferEntity offer = offerRepository.findById(id).get();
		offer.setEuOfferStatus(status);
		if (offer.getEuOfferStatus().equals(EuOfferStatus.EXPIRED))
			billService.cancelBIlls(id);
		offerRepository.save(offer);
		return new ResponseEntity<>(offer,HttpStatus.OK);
	}
	return null;
}*/
}
