package com.iktpreobuka.elektronskidnevnik.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping(path = "/class")
public class ClassController {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
}
