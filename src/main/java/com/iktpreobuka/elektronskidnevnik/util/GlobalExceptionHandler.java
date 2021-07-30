package com.iktpreobuka.elektronskidnevnik.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MultipartException.class)
	public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
		return "redirect:/uploadStatus";
	}
	  @ExceptionHandler(FileNotFoundException.class)
      public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException exc) {
          
          List<String> details = new ArrayList<String>();
          details.add(exc.getMessage());
          RestError err = new RestError(10, "File Not Found");
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
      }
}
