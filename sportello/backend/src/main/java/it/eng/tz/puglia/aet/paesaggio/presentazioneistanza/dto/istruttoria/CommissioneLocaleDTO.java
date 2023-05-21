package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;

public class CommissioneLocaleDTO extends PaesaggioOrganizzazioneDTO
{
	private static final long serialVersionUID = -2842132710664912234L;

	private String email;
	@JsonProperty(access=Access.READ_ONLY)
	private List<PlainNumberValueLabel> entiLabelValue;
	private List<Long> organizzazioniAssociate;
	private List<PlainStringValueLabel> users;
	
	public CommissioneLocaleDTO()
	{
		super();
	}
	public CommissioneLocaleDTO(PaesaggioOrganizzazioneDTO other)
	{
		setId(other.getId());
		setEnteId(other.getEnteId());
		setDenominazione(other.getDenominazione());
		setCodiceCivilia(other.getCodiceCivilia());
		setRiferimentoOrganizzazione(other.getRiferimentoOrganizzazione());
		setTipoOrganizzazione(other.getTipoOrganizzazione());
		setTipoOrganizzazioneSpecifica(other.getTipoOrganizzazioneSpecifica());
		setDataCreazione(other.getDataCreazione());
		setDataTermine(other.getDataTermine());
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public List<PlainNumberValueLabel> getEntiLabelValue()
	{
		return entiLabelValue;
	}
	public void setEntiLabelValue(List<PlainNumberValueLabel> entiLabelValue)
	{
		this.entiLabelValue = entiLabelValue;
	}
	
	public List<Long> getOrganizzazioniAssociate()
	{
		return organizzazioniAssociate;
	}
	public void setOrganizzazioniAssociate(List<Long> organizzazioniAssociate)
	{
		this.organizzazioniAssociate = organizzazioniAssociate;
	}
	
	public List<PlainStringValueLabel> getUsers()
	{
		return users;
	}
	public void setUsers(List<PlainStringValueLabel> users)
	{
		this.users = users;
	}
	
}
