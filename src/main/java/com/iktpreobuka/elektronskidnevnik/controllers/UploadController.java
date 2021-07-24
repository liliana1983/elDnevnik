package com.iktpreobuka.elektronskidnevnik.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iktpreobuka.elektronskidnevnik.services.FileHandler;

@Controller
@RequestMapping(path = "/")
public class UploadController {
	@Autowired
	private FileHandler fileHandler;

private final String EXTERNAL_FILE_PATH="C:\\Downloads";
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "upload";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws Exception {
		String result = null;
		try {
			result = fileHandler.singleFileUpload(file, redirectAttributes);
			logger.info("file uploaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/downloadFile")
	public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request) throws FileNotFoundException {
	
		File file = fileHandler.downloadLogs();
		String contentType = null;
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
