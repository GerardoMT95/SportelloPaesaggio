package it.eng.tz.puglia.autpae.enumeratori;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;

public enum AllowedMimeType {

	PDF("application/pdf", ".pdf"), 
	RTF("application/rtf", ".rtf"), 
	TXT("text/plain", ".txt"),
	PPT("application/vnd.ms-powerpoint", ".ppt"),
	XLS("application/vnd.ms-excel", ".xls"),
	XLS_TIKA("application/x-tika-msoffice", ".xls"),
	XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx"),
	XLSX_TIKA("application/x-tika-ooxml", ".xlsx"),
	DOC("application/msword", ".doc"),
	DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx"),
	PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx"),
	RAR("application/x-rar-compressed", ".rar"), 
	ZIP("application/zip", ".zip"),
	X7Z("application/x-7z-compressed", ".7z"),
	PNG("image/png", ".png"), 
	JPG("image/jpeg", ".jpg"),
	JPEG("image/jpeg", ".jpeg");

	private String mimeType;
	private String windowsExtension;

	public String getMimeType() {
		return mimeType;
	}
	
	public String getExtension() {
		return windowsExtension;
	}

	private AllowedMimeType(String mimeType, String extension) {
		this.mimeType = mimeType;
		this.windowsExtension = extension;
	}
	
	public static AllowedMimeType getMimeType(String mymeType) {
		Optional<AllowedMimeType> result = Arrays.stream(AllowedMimeType.values())
				.filter(el -> el.getMimeType().equalsIgnoreCase(mymeType)).findFirst();
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}	
	}
	
	/**
	 * utilizzato per effettuare test sui file ammessi non modificabili (PDF,JPEG,PNG)
	 * @param mymeType 
	 * @return
	 */
	public static boolean isMimeTypeSolaLettura(String mymeType) {
		Optional<AllowedMimeType> result = 
				List.of(AllowedMimeType.JPEG,AllowedMimeType.PDF,AllowedMimeType.PNG).stream()
				.filter(el -> el.getMimeType().equalsIgnoreCase(mymeType)).findFirst();
		if (result.isPresent()) {
			return true;
		} else {
			return false;
		}	
	}
	
	public static String detectMimeType(final InputStream data) throws IOException {
		try (TikaInputStream tikaIS = TikaInputStream.get(data)) {
			final Metadata metadata = new Metadata();
			return new DefaultDetector(MimeTypes.getDefaultMimeTypes()).detect(tikaIS, metadata).toString();
		} finally {
		}
	}
}
