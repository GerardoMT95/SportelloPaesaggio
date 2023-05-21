package it.eng.tz.puglia.geo.dto;

import java.io.Serializable;
import java.util.Date;

import org.postgis.jts.JtsGeometry;

public class GeoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4380541440817549496L;
	private long oid;
	private JtsGeometry geometry;
	private long idFascicolo;
	private String userId;
	private Date dateCreated;
	private Date dateUpdated;
	private String updateUserId;
	
	
	/**
	 * 1 se è un poligono 0 tracciato a mano o da shape file
	 * 0 se è un poligono corrispondente ad una particella
	 */
	private Integer isCustom;
	
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Integer getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(Integer isCustom) {
		this.isCustom = isCustom;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public JtsGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(JtsGeometry geometry) {
		this.geometry = geometry;
	}

	public long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	
}
