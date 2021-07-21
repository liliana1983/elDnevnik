package com.iktpreobuka.elektronskidnevnik.services;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface FileHandler {
	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes)throws IOException;



	File downloadLogs();
}
