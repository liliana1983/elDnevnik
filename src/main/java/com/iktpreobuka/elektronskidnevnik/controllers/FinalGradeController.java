package com.iktpreobuka.elektronskidnevnik.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/finalGrade")
public class FinalGradeController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
}
