package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.Contains;

public class ProvvedimentoFinaleDetailsDTO extends ProvvedimentoFinaleDTO
{
	private static final long serialVersionUID = 7197312565996906579L;
	
	@NotNull(message="Allegato provvedimento finale obbligatorio")
	@NotEmpty(message="Allegato provvedimento finale obbligatorio")
	@Contains(values="951", field="type", message="Allegato provvedimento pubblico obbligatorio")
	private List<AllegatiDTO> allegati;
	
	private List<DestinatarioDTO> destinatariFissi;
	private List<DestinatarioDTO> ulterioriDestinatari;
	
	public ProvvedimentoFinaleDetailsDTO()
	{
		super();
	}
	public ProvvedimentoFinaleDetailsDTO(ProvvedimentoFinaleDTO other)
	{
		setId(other.getId());
		setIdPratica(other.getIdPratica());
		setIdCorrispondenza(other.getIdCorrispondenza());
		setNumeroProvvedimento(other.getNumeroProvvedimento());
		setDraft(other.getDraft());
		setRup(other.getRup());
		setDataRilascioAutorizzazione(other.getDataRilascioAutorizzazione());
		setEsitoProvvedimento(other.getEsitoProvvedimento());
	}
	
	public List<AllegatiDTO> getAllegati()
	{
		return allegati;
	}
	public void setAllegati(List<AllegatiDTO> allegati)
	{
		this.allegati = allegati;
	}
	
	public List<DestinatarioDTO> getDestinatariFissi()
	{
		return destinatariFissi;
	}
	public void setDestinatariFissi(List<DestinatarioDTO> destinatariFissi)
	{
		this.destinatariFissi = destinatariFissi;
	}
	
	public List<DestinatarioDTO> getUlterioriDestinatari()
	{
		return ulterioriDestinatari;
	}
	public void setUlterioriDestinatari(List<DestinatarioDTO> ulterioriDestinatari)
	{
		this.ulterioriDestinatari = ulterioriDestinatari;
	}
	

}
