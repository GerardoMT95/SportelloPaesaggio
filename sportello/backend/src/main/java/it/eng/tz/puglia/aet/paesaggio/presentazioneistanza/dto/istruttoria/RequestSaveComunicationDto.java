package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;

public class RequestSaveComunicationDto implements Serializable {

	private static final long serialVersionUID = 8797099681705777736L;

	long idRelazione;
	UUID idPratica;
	
	String tipoInvia;
	
	DettaglioCorrispondenzaDTO corrispondenza;

	public long getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(long idRelazione) {
		this.idRelazione = idRelazione;
	}

	public String getTipoInvia() {
		return tipoInvia;
	}

	public void setTipoInvia(String tipoInvia) {
		this.tipoInvia = tipoInvia;
	}

	public DettaglioCorrispondenzaDTO getCorrispondenza() {
		return corrispondenza;
	}

	public void setCorrispondenza(DettaglioCorrispondenzaDTO corrispondenza) {
		this.corrispondenza = corrispondenza;
	}

	public UUID getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(UUID idPratica) {
		this.idPratica = idPratica;
	}

	
}
