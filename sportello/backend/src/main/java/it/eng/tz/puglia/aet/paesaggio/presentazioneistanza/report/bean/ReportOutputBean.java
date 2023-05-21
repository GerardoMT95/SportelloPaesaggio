package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

/**
 * Bean risultato report
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
public class ReportOutputBean implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 */
	private static final long serialVersionUID = -2496854870378422099L;
	
	public ReportOutputBean(final String fileName, final String pathFile) {
		super();
		this.fileName = fileName;
		this.pathFile = pathFile;
	}
	
	public ReportOutputBean() {
		super();
	}
	
	private String fileName;
	private String pathFile;
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return this.fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(final String fileName) {
		this.fileName = fileName;
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
