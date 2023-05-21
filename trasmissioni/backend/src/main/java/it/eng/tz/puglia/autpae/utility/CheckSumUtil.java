package it.eng.tz.puglia.autpae.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.multipart.MultipartFile;

public class CheckSumUtil {
	final static public String HASH_AET="SHA-256"; 

	private CheckSumUtil() {
	}

	public static String getCheckSum(MultipartFile file) throws IOException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(HASH_AET);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		try (InputStream is = file.getInputStream();
				DigestInputStream dis = new DigestInputStream(is, md)) {
			while (dis.read() != -1) {
			}
			md = dis.getMessageDigest();
		} catch (Exception e) {
			return null;
		}

		StringBuilder checksum = new StringBuilder();
		for (byte b : md.digest()) {
			checksum.append(String.format("%02x", b));
		}
		return checksum.toString();
	}
	
	public static String getCheckSum(File file) throws IOException {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance(HASH_AET);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	
		try (InputStream is = Files.newInputStream(Paths.get(file.getPath()));
				DigestInputStream dis = new DigestInputStream(is, md)) {
			while (dis.read() != -1) {
				// reading DigestInputStream
			}
			md = dis.getMessageDigest();
		}
		// bytes to hex
		StringBuilder checksum = new StringBuilder();
		for (byte b : md.digest()) {
			checksum.append(String.format("%02x", b));
		}
		return checksum.toString();
	}
}