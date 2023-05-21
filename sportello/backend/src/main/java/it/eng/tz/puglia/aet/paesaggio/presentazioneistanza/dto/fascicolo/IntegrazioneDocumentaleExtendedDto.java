package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;

public class IntegrazioneDocumentaleExtendedDto extends IntegrazioneDTO
{
	private static final long serialVersionUID = 8436380936632716262L;
	
	private AllegatiDto allegati;
	private AllegatiDTO documentoIntegrazione;
	
	public IntegrazioneDocumentaleExtendedDto() 
	{
		super();
	}
	
	public IntegrazioneDocumentaleExtendedDto(IntegrazioneDTO dto)
	{
		this.setId(dto.getId());
		this.setDescrizione(dto.getDescrizione());
		this.setNote(dto.getNote());
		this.setPraticaId(dto.getPraticaId());
		this.setRichiestaIntegrazione(dto.getRichiestaIntegrazione());
		this.setStato(dto.getStato());
		this.setDataCaricamento(dto.getDataCaricamento());
		this.setDataCreazione(dto.getDataCreazione());
		this.setUsernameUtenteCreazione(dto.getUsernameUtenteCreazione());
	}

	public AllegatiDto getAllegati()
	{
		return allegati;
	}
	public void setAllegati(AllegatiDto allegati)
	{
		this.allegati = allegati;
	}

	public AllegatiDTO getDocumentoIntegrazione()
	{
		return documentoIntegrazione;
	}
	public void setDocumentoIntegrazione(AllegatiDTO documentoIntegrazione)
	{
		this.documentoIntegrazione = documentoIntegrazione;
	}
	
	
}
