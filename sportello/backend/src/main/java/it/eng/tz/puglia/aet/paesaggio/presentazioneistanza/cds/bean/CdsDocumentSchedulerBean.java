package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.bean;

import java.io.Serializable;

import it.eng.tz.puglia.cds.bean.ConferenzaApiDocumentazioneDto;

/**
 * Bean per inviare documentazione a cds
 * @author Antonio La Gatta
 * @date 1 dic 2021
 */
public class CdsDocumentSchedulerBean implements Serializable{

	
	/**
	 * @author Antonio La Gatta
	 * @date 1 dic 2021
	 */
	private static final long serialVersionUID = -1265710157860891557L;
	
	
	private long idConferenza;
	private ConferenzaApiDocumentazioneDto documento;
	/**
	 * @return the idConferenza
	 */
	public long getIdConferenza() {
		return this.idConferenza;
	}
	/**
	 * @param idConferenza the idConferenza to set
	 */
	public void setIdConferenza(final long idConferenza) {
		this.idConferenza = idConferenza;
	}
	/**
	 * @return the documento
	 */
	public ConferenzaApiDocumentazioneDto getDocumento() {
		return this.documento;
	}
	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(final ConferenzaApiDocumentazioneDto documento) {
		this.documento = documento;
	}
	
}
