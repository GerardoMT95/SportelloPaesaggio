package it.eng.tz.puglia.servizi_esterni.cms.bean;

import java.io.Serializable;

/**
 * Cms upload file response bean
 * @author Antonio La Gatta
 * @date 12 nov 2018
 */
public class CmsResponseBean implements Serializable{

	private static final long serialVersionUID = -5232270962756879902L;


	private String id;
	private String fileName;
	private String filePathName;
	private String contentType;
	private long size;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the filePathName
	 */
	public String getFilePathName() {
		return filePathName;
	}
	/**
	 * @param filePathName the filePathName to set
	 */
	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CmsDownloadResponseBean [id=" + id + ", fileName=" + fileName + ", filePathName=" + filePathName
				+ ", contentType=" + contentType + ", size=" + size + "]";
	}
	
	
}
