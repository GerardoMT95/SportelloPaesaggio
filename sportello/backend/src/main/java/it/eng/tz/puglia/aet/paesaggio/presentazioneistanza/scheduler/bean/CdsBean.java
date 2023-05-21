package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean;

import java.io.Serializable;
import java.util.UUID;

public class CdsBean implements Serializable{
	private static final long serialVersionUID = -8599227634460970763L;
	private String id;
	private UUID idPratica;
	private String codiceBase;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UUID getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(UUID idPratica) {
		this.idPratica = idPratica;
	}
	public String getCodiceBase() {
		return codiceBase;
	}
	public void setCodiceBase(String codiceBase) {
		this.codiceBase = codiceBase;
	}
}
