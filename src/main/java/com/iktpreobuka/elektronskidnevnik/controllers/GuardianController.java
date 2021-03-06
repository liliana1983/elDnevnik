package com.iktpreobuka.elektronskidnevnik.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.GuardianEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GuardianDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.GuardianRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.services.GuardianService;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;
import com.iktpreobuka.elektronskidnevnik.util.RestError;
import com.iktpreobuka.elektronskidnevnik.util.Validation;

@RestController
@RequestMapping(path = "/user/guardian")
public class GuardianController {
	@Autowired
	GuardianRepository guardianRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	GuardianService guardianService;
	@Autowired
	StudentRepository studentRepository;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/addGuardian")
	public ResponseEntity<?> createGuardian(@Valid @RequestBody GuardianDTO newGuardian,BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		GuardianEntity guardian = guardianService.newGuardian(newGuardian);
		logger.info("guardian created");
		return new ResponseEntity<>(guardian, HttpStatus.CREATED);
	}


	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/")
	public ResponseEntity<?> deleteGuardian(@RequestParam Integer guardianId) {
		GuardianEntity guardian = guardianRepository.findById(guardianId).get();
		guardianRepository.delete(guardian);
		logger.info("guardian deleted");
		return new ResponseEntity<>(new RestError(2, "Guardian deleted"), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/connect")
	public ResponseEntity<?> connectGuardianAndKids(@Valid @RequestParam Integer kidId,
			@RequestParam Integer guardianId) {
		if (guardianRepository.existsById(guardianId)) {
			GuardianEntity guardian = guardianRepository.findById(guardianId).get();
			if (studentRepository.existsById(kidId)) {
				StudentEntity kid = studentRepository.findById(kidId).get();
				kid.setGuardian(guardian);
				studentRepository.save(kid);
				logger.info("child and guardian are connected");
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/updateGuardian")
	public ResponseEntity<?> updateGuardian(@RequestParam Integer guardianId,
			 @RequestBody GuardianEntity updateGuardian, BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		if (guardianRepository.existsById(guardianId)) {
			if(updateGuardian.getPassword() !=null) {
			GuardianEntity guardian = guardianRepository.findById(guardianId).get();
			guardian.setName(Validation.setIfNotNull(guardian.getName(), updateGuardian.getName()));
			guardian.setLastName(Validation.setIfNotNull(guardian.getLastName(), updateGuardian.getLastName()));
			guardian.setUsername(Validation.setIfNotNull(guardian.getUsername(), updateGuardian.getUsername()));
			guardian.setPassword(Validation.setIfNotNull(guardian.getPassword(),
					Encryption.getPassEncoded(updateGuardian.getPassword())));
			guardian.setEmail(Validation.setIfNotNull(guardian.getEmail(), updateGuardian.getEmail()));
			guardianRepository.save(guardian);
			logger.info("Guardian updated");
			return new ResponseEntity<>(guardian, HttpStatus.OK);
		}else {
			GuardianEntity guardian = guardianRepository.findById(guardianId).get();
		guardian.setName(Validation.setIfNotNull(guardian.getName(), updateGuardian.getName()));
		guardian.setLastName(Validation.setIfNotNull(guardian.getLastName(), updateGuardian.getLastName()));
		guardian.setUsername(Validation.setIfNotNull(guardian.getUsername(), updateGuardian.getUsername()));
		guardian.setEmail(Validation.setIfNotNull(guardian.getEmail(), updateGuardian.getEmail()));
		guardianRepository.save(guardian);
		logger.info("Guardian updated");
		return new ResponseEntity<>(guardian, HttpStatus.OK);}
			
		}
		return new ResponseEntity<RestError>(new RestError(10, "Guardian with this Id doesnt exist!"),
				HttpStatus.BAD_REQUEST);

	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/")
	public ResponseEntity<?> getAllGuardians() {
		logger.info("All guardians listed");
		return new ResponseEntity<>(guardianRepository.findAll(), HttpStatus.OK);
	}
}
