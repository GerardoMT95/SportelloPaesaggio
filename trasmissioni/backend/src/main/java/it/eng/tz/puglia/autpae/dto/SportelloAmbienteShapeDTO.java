package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * tabella procedimenti_ambientali.shape solo parte di interesse
 * @author Adriano Colaianni
 * @date 26 lug 2022
 */
public class SportelloAmbienteShapeDTO  implements Serializable
{
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String nameFile;
	private String cmisId;
	private Date dateCreate;
	private String hash;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public String getCmisId() {
		return cmisId;
	}
	public void setCmisId(String cmisId) {
		this.cmisId = cmisId;
	}
	public Date getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
			
}
