package com.iktpreobuka.elektronskidnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	JavaMailSender emailSender;

	@Override
	public void sendSimpleMessage(StudentEntity student) throws Exception {
		// TODO Auto-generated method stub
		/*
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(student.getGuardian().getEmail());
		message.setSubject("your child just got a new grade");
		message.setText(student.toString()+ " just received new grade "+ grade.toString()+ " from subject " + subject.toString());
		emailSender.send(message);

	}*/
	}
}
