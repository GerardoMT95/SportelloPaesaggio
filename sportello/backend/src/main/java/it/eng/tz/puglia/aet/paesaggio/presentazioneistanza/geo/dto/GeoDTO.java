package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.dto;

import java.util.Date;
import java.util.UUID;

import org.postgis.jts.JtsGeometry;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

public class GeoDTO  extends BaseSearchRequestBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4380541440817549496L;
	private long oid;
	private JtsGeometry geometry;
	private UUID idFascicolo;
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

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
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

	public UUID getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(UUID idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	
}
