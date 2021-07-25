package com.iktpreobuka.elektronskidnevnik.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class FileHandlerImpl implements FileHandler {
	private static String UPLOAD_FOLDER = "C:\\SpringTemp\\";
	private static String DOWNLOAD_FILE = "C:\\Users\\HP\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\elektronski_dnevnik\\logs\\spring-boot-logging.log";

	@Override
	public String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		// TODO Auto-generated method stub
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select file to upload");
			return "redirect:uploadStatus";
		}
		byte[] bytes = file.getBytes();
		Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());

		Files.write(path, bytes);
		redirectAttributes.addFlashAttribute("message",
				"File " + file.getOriginalFilename() + " successfully uploaded!");

		return "redirect:/uploadStatus";
	}

	@Override
	public File downloadLogs() {

		File file = new File(DOWNLOAD_FILE);

		return file;
	}
}
