package com.edylle.pathologicalreports.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import com.edylle.pathologicalreports.exception.ImageFormatException;

public final class FilesUtils {

	public static String saveImage(String context, String path, MultipartFile file) throws ImageFormatException, IOException {
		if (file == null || file.isEmpty()) {
			return null;
		}
		if (!isImageValid(file)) {
			throw new ImageFormatException();
		}

		String extension = getExtensionFrom(file);
		String fileName = addUnderlinesBetweenWords(extension, file.getOriginalFilename().split("\\.")[0]);

		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(Paths.get(path, fileName).toString().toLowerCase())));
		stream.write(file.getBytes());
		stream.close();

		return context.concat(fileName);
	}

	public static boolean isImageValid(MultipartFile arquivo) {
		return arquivo != null && !arquivo.isEmpty() && arquivo.getContentType().contains("image");
	}

	public static String getExtensionFrom(MultipartFile file) {
		return ".".concat(file.getOriginalFilename().split("\\.")[1]);
	}

	public static String addUnderlinesBetweenWords(String extension, String... words) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < words.length; i++) {

			sb.append(addUnderlinesBetweenWords(words[i]));

			if (i != words.length - 1) {
				sb.append("_");
			}
		}

		sb.append(extension);
		return sb.toString();
	}

	public static String addUnderlinesBetweenWords(String text) {
		String[] words = text.split("\\s+");
		String result = "";
		for (int i = 0; i < words.length; i++) {

			if (i == words.length - 1) {
				result += words[i].replaceAll("[^\\w]", "");
			} else {
				result += words[i].replaceAll("[^\\w]", "").concat("_");
			}
		}

		return result;
	}

}
