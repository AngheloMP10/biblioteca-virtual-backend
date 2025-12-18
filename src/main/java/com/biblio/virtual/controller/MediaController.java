package com.biblio.virtual.controller;

import com.biblio.virtual.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/media")
public class MediaController {

	private final CloudinaryService cloudinaryService;

	public MediaController(CloudinaryService cloudinaryService) {
		this.cloudinaryService = cloudinaryService;
	}

	@PostMapping("/upload")
	public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
		String url = cloudinaryService.uploadFile(file);
		// JSON simple: { "url": "https://..." }
		return ResponseEntity.ok(Collections.singletonMap("url", url));
	}
}