package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;

/**
 * Bean request for cms upload services
 * @author Antonio La Gatta
 * @date 12 nov 2018
 */
@Deprecated
public class AlfrescoUploadInputDTO implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 10 nov 2018
	 */
	
	private static final long serialVersionUID = -8458026966018830607L;
	@Value("${spring.application.name}")
	private static String codiceApplicazione ;
	
	private String fileNameOnDisk;
	private String fileName;
	private String pathCms;
	private String contentType;
	private long size;
	private String applicationName = codiceApplicazione; //ALG 2019-01-17 issue #2173: utente admin alfresco
	
	
	public AlfrescoUploadInputDTO() {}
	


	public AlfrescoUploadInputDTO(String fileNameOnDisk, String fileName, String pathCms, String contentType, long size, String applicationName) {
		this.fileNameOnDisk = fileNameOnDisk;
		this.fileName = fileName;
		this.pathCms = pathCms;
		this.contentType = contentType;
		this.size = size;
		this.applicationName = applicationName;
	}



	/**
	 * @return the fileNameOnDisk
	 */
	public String getFileNameOnDisk() {
		return fileNameOnDisk;
	}
	/**
	 * @param fileNameOnDisk the fileNameOnDisk to set
	 */
	public void setFileNameOnDisk(String fileNameOnDisk) {
		this.fileNameOnDisk = fileNameOnDisk;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the pathCms
	 */
	public String getPathCms() {
		return pathCms;
	}
	/**
	 * @param pathCms the pathCms to set
	 */
	public void setPathCms(String pathCms) {
		this.pathCms = pathCms;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}
	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}
	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CmsUploadRequestBean [fileNameOnDisk=" + fileNameOnDisk + ", fileName=" + fileName + ", pathCms="
				+ pathCms + ", contentType=" + contentType + ", size=" + size + ", applicationName=" + applicationName
				+ "]";
	}
	
}
	
	