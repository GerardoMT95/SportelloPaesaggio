package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean;

import java.io.Serializable;

public class ProvvedimentoBean implements Serializable{
	
	/**
	 * @author Simone Verna
	 * @date 13 set 2022
	 */
	private static final long serialVersionUID = -144478299413498221L;
	private String idPratica;
	private long idCorrispondenza;
	
	public String getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}
	public long getIdCorrispondenza() {
		return idCorrispondenza;
	}
	public void setIdCorrispondenza(long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}
}
