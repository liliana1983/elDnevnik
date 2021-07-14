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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.GuardianEntity;
import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GuardianDTO;
import com.iktpreobuka.elektronskidnevnik.entities.dto.UserDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.GuardianRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;
import com.iktpreobuka.elektronskidnevnik.util.RestError;

@RestController
@RequestMapping(path = "/user/guardian")
public class GuardianController {
@Autowired
GuardianRepository guardianRepository;
@Autowired
RoleRepository roleRepository;
private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/addGuardian")
	public ResponseEntity<?> createGuardian(@Valid @RequestBody GuardianDTO newGuardian, @RequestParam Integer roleId,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		GuardianEntity guardian = new GuardianEntity();
		guardian.setName(newGuardian.getName());
		guardian.setLastName(newGuardian.getLastName());
		guardian.setPassword(Encryption.getPassEncoded(newGuardian.getPassword()));
		guardian.setPassword(Encryption.getPassEncoded(newGuardian.getConfirmPassword()));
		guardian.setUsername(newGuardian.getUsername());
		guardian.setEmail(newGuardian.getEmail());
		RoleEntity rola = roleRepository.findById(roleId).get();
		guardian.setRole(rola);
		guardianRepository.save(guardian);
		return new ResponseEntity<>(guardian, HttpStatus.CREATED);
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.DELETE, value="/")
		public ResponseEntity<?> deleteGuardian(@RequestParam Integer guardianId){
	GuardianEntity guardian= guardianRepository.findById(guardianId).get();
	guardianRepository.delete(guardian);
	return new ResponseEntity<>(new RestError(2,"Guardian deleted"),HttpStatus.OK);
	}
	}

