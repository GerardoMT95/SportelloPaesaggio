package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istruttoria;

import java.io.Serializable;
import java.util.Date;

public class ProtocolloDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1544740868589366606L;

	private String numeroProtocollo;
	private Date dataProtocollo;
	private String codiceFascicolo;
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getCodiceFascicolo() {
		return codiceFascicolo;
	}
	public void setCodiceFascicolo(String codiceFascicolo) {
		this.codiceFascicolo = codiceFascicolo;
	}

}
