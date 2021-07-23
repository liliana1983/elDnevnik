package com.iktpreobuka.elektronskidnevnik.services;

import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;

public interface EmailService {

	void sendSimpleMessage(StudentEntity student) throws Exception;
}
