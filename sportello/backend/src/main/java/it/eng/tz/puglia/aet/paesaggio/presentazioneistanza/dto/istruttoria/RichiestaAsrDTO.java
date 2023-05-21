package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StoricoRichiesta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SospensioneArchiviazioneAttivazioneDTO;

public class RichiestaAsrDTO extends SospensioneArchiviazioneAttivazioneDTO
{
	private static final long serialVersionUID = -2471532761529481523L;
	
	private List<AllegatiDTO> allegati;
	
	public RichiestaAsrDTO() 
	{ 
		super(); 
	}
	public RichiestaAsrDTO(UUID idPratica, StoricoRichiesta type, String usernameUtenteCreazione)
	{
		super(idPratica, type, usernameUtenteCreazione);
	}
	public RichiestaAsrDTO(UUID idPratica, String usernameUtenteCreazione)
	{
		super(idPratica, null, usernameUtenteCreazione);
	}
	public RichiestaAsrDTO(SospensioneArchiviazioneAttivazioneDTO other)
	{
		setId(other.getId());
		setCodicePratica(other.getCodicePratica());
		setData(other.getData());
		setDraft(other.getDraft());
		setIdPratica(other.getIdPratica());
		setNote(other.getNote());
		setType(other.getType());
		setUsernameUtenteCreazione(other.getUsernameUtenteCreazione());
	}

	public List<AllegatiDTO> getAllegati()
	{
		return allegati;
	}
	public void setAllegati(List<AllegatiDTO> allegati)
	{
		this.allegati = allegati;
	}
	
	
}
