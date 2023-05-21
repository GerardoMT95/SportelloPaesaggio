package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SedutaCommissioneDTO;

public class SedutaCommissioneInputDTO extends SedutaCommissioneDTO
{
	private static final long serialVersionUID = 7662674015827788147L;
	
	@NotNull
	@NotEmpty
	private List<UUID> pratiche;
	
	public List<UUID> getPratiche()
	{
		return pratiche;
	}
	public void setPratiche(List<UUID> pratiche)
	{
		this.pratiche = pratiche;
	}
	
}
