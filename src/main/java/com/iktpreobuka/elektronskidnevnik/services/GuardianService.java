package com.iktpreobuka.elektronskidnevnik.services;

import com.iktpreobuka.elektronskidnevnik.entities.GuardianEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GuardianDTO;

public interface GuardianService {

	public GuardianEntity newGuardian(GuardianDTO newGuardian);

}
