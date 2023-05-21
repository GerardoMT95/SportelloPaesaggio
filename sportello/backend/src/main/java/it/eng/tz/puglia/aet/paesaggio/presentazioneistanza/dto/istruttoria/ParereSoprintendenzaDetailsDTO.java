package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParereSoprintendenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.Contains;

public class ParereSoprintendenzaDetailsDTO extends ParereSoprintendenzaDTO
{
	private static final long serialVersionUID = 8750647114942837533L;

	@Contains(field="type", values="904", message="Allegato 'parere MIBAC (pubblico)' è obbligatorio")
	private List<AllegatiDTO> allegati;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DettaglioCorrispondenzaDTO comunicazione;
	
	public ParereSoprintendenzaDetailsDTO()
	{
		super();
	}
	public ParereSoprintendenzaDetailsDTO(ParereSoprintendenzaDTO other)
	{
		setId(other.getId());
		setIdPratica(other.getIdPratica());
		setNominativoIstruttore(other.getNominativoIstruttore());
		setEsitoParere(other.getEsitoParere());
		setNote(other.getNote());
		setNumeroProtocollo(other.getNumeroProtocollo());
		setUsernameUtenteCreazione(other.getUsernameUtenteCreazione());
		setDataInserimento(other.getDataInserimento());
		setDettaglioPrescrizione(other.getDettaglioPrescrizione());
		setIdCorrispondenza(other.getIdCorrispondenza());
	}
	
	public List<AllegatiDTO> getAllegati()
	{
		return allegati;
	}
	public void setAllegati(List<AllegatiDTO> allegati)
	{
		this.allegati = allegati;
	}
	
	public DettaglioCorrispondenzaDTO getComunicazione()
	{
		return comunicazione;
	}
	public void setComunicazione(DettaglioCorrispondenzaDTO comunicazione)
	{
		this.comunicazione = comunicazione;
	}
	
}
