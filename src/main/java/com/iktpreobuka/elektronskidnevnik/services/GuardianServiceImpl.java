package com.iktpreobuka.elektronskidnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.GuardianEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GuardianDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.GuardianRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;

@Service
public class GuardianServiceImpl implements GuardianService {
	@Autowired
	GuardianRepository guardianRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override

	public GuardianEntity newGuardian(GuardianDTO newGuardian) {
		GuardianEntity guardian = new GuardianEntity();
		guardian.setName(newGuardian.getName());
		guardian.setLastName(newGuardian.getLastName());
		guardian.setPassword(Encryption.getPassEncoded(newGuardian.getPassword()));
		guardian.setPassword(Encryption.getPassEncoded(newGuardian.getConfirmPassword()));
		guardian.setUsername(newGuardian.getUsername());
		guardian.setEmail(newGuardian.getEmail());
		guardian.setRole(roleRepository.findByName("ROLE_GUARDIAN"));
		
				guardianRepository.save(guardian);
				return guardian;
	}
	/*@Override
	public GuardianEntity changeGuardian(Integer guardianId, GuardianEntity updateGuardian) {
		GuardianEntity guardian = guardianRepository.findById(guardianId).get();
		guardian.setName(Validation.setIfNotNull(guardian.getName(), updateGuardian.getName()));
		guardian.setLastName(Validation.setIfNotNull(guardian.getLastName(), updateGuardian.getLastName()));
		guardian.setUsername(Validation.setIfNotNull(guardian.getUsername(), updateGuardian.getUsername()));
		guardian.setPassword(Validation.setIfNotNull(Encryption.getPassEncoded(guardian.getPassword()),
				Encryption.getPassEncoded(updateGuardian.getPassword())));
		guardian.setEmail(Validation.setIfNotNull(guardian.getEmail(), updateGuardian.getEmail()));
		 guardianRepository.save(guardian);
		return guardian;*/
	}

