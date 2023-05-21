package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class DownloadReportBean implements Serializable{
	
	private static final long serialVersionUID = 8508801028503949260L;
	private String nomeFile;
	private String pathFile;
	/**
	 * @return the nomeFile
	 */
	public String getNomeFile() {
		return this.nomeFile;
	}
	/**
	 * @param nomeFile the nomeFile to set
	 */
	public void setNomeFile(final String nomeFile) {
		this.nomeFile = nomeFile;
	}
	/**
	 * @return the pathFile
	 */
	public String getPathFile() {
		return this.pathFile;
	}
	/**
	 * @param pathFile the pathFile to set
	 */
	public void setPathFile(final String pathFile) {
		this.pathFile = pathFile;
	}
	
}
