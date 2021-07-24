package com.iktpreobuka.elektronskidnevnik.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String string) {
		// TODO Auto-generated constructor stub
	}

	private String message;
}