package com.biblio.virtual.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

	private final Cloudinary cloudinary;

	public CloudinaryService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	public String uploadFile(MultipartFile file) {
		try {
			// Se sube el archivo y se obtiene la respuesta
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			// Retornamos URL segura (https)
			return uploadResult.get("secure_url").toString();
		} catch (IOException e) {
			throw new RuntimeException("Error al subir imagen a Cloudinary", e);
		}
	}
}