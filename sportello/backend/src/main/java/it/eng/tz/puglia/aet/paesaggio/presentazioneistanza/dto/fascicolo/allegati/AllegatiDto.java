package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati;

import java.io.Serializable;

/**
 * rappresenta la property allegati di FascicoloDto su FE
 * @author acolaianni
 *
 */
public class AllegatiDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1151828583490192920L;

	private DocumentazioneAmministrativaDto documentazioneAmministrativa;
	private DocumentazioneTecnicaDto documentazioneTecnica;
	public DocumentazioneAmministrativaDto getDocumentazioneAmministrativa() {
		return documentazioneAmministrativa;
	}
	public void setDocumentazioneAmministrativa(DocumentazioneAmministrativaDto documentazioneAmministrativa) {
		this.documentazioneAmministrativa = documentazioneAmministrativa;
	}
	public DocumentazioneTecnicaDto getDocumentazioneTecnica() {
		return documentazioneTecnica;
	}
	public void setDocumentazioneTecnica(DocumentazioneTecnicaDto documentazioneTecnica) {
		this.documentazioneTecnica = documentazioneTecnica;
	}
	
	
}
