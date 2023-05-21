/**
 * 
 */
package it.eng.tz.puglia.autpae.utility;

import java.io.File;

/**
 * Classe di comodo per passaggio di file ed eventuali attributi tra metodi
 * @author Adriano Colaianni
 * @date 17 ott 2021
 */
public class FileWrapper {
	private File file;
	private String nomeFile;
	private String contentType;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public FileWrapper(File file, String nomeFile, String contentType) {
		super();
		this.file = file;
		this.nomeFile = nomeFile;
		this.contentType = contentType;
	}
	
}
