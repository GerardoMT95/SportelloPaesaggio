package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SedutaCommissioneDTO;

public class SedutaCommissioneExtendedDTO extends SedutaCommissioneInputDTO
{
	private static final long serialVersionUID = -118723108754035490L;
	
	private List<PraticaDTO> praticheDetails;
	private List<AllegatiCommissioneLocaleDTO> allegati;
	
	public SedutaCommissioneExtendedDTO()
	{
		super();
	}
	
	public SedutaCommissioneExtendedDTO(SedutaCommissioneDTO other)
	{
		setId(other.getId());
		setDataCreazione(other.getDataCreazione());
		setDataSeduta(other.getDataSeduta());
		setStato(other.getStato());
		setIdOrganizzazione(other.getIdOrganizzazione());
		setNomeSeduta(other.getNomeSeduta());
		setPubblica(other.getPubblica());
		setUsernameUtenteCreazione(other.getUsernameUtenteCreazione());
	}
	
	
	
	public List<PraticaDTO> getPraticheDetails()
	{
		return praticheDetails;
	}
	public void setPraticheDetails(List<PraticaDTO> praticheDetails)
	{
		this.praticheDetails = praticheDetails;
	}

	public List<AllegatiCommissioneLocaleDTO> getAllegati()
	{
		return allegati;
	}
	public void setAllegati(List<AllegatiCommissioneLocaleDTO> allegati)
	{
		this.allegati = allegati;
	}
	
	
	
}
