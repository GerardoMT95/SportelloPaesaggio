package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn;

@RequiredDependsOn.List({
	@RequiredDependsOn(field="descrizioneAltro", dependsOn="titolaritaInQualitaDi", dependsOnValue="9", message="Il campo 'descrizioneAltro' è obbligatorio se selezionata l'opzione 'altro')"),
	@RequiredDependsOn(field="descrizioneAltro", dependsOn="titolaritaInQualitaDi", dependsOnValue="9", message="Il campo 'descrizioneAltro' è obbligatorio se selezionata l'opzione 'rappresentante legale della Ditta, Società, Associazione o Ente Pubblico')")
})
public class AltroTitolareDto extends RichiedenteDto
{
	private static final long serialVersionUID = -2345509015168605579L;
	@NotNull(message = "Il campo 'titolaritaInQualitaDi' non può essere nullo")
	private Integer titolaritaInQualitaDi;
	private String descrizioneAltro;
	
	public Integer getTitolaritaInQualitaDi()
	{
		return titolaritaInQualitaDi;
	}
	public void setTitolaritaInQualitaDi(Integer titolaritaInQualitaDi)
	{
		this.titolaritaInQualitaDi = titolaritaInQualitaDi;
	}

	public String getDescrizioneAltro()
	{
		return descrizioneAltro;
	}
	public void setDescrizioneAltro(String descrizioneAltro)
	{
		this.descrizioneAltro = descrizioneAltro;
	}

	@JsonIgnore
	public String getIndirizzoEmail()
	{
		return super.getIndirizzoEmail();
	}
	
	public void fromEntity(ReferentiDTO referenteDTO)
	{
		super.fromEntity(referenteDTO);
		this.setTitolaritaInQualitaDi(referenteDTO.getTitolaritaId());
		this.setDescrizioneAltro(referenteDTO.getSpecificaTitolarita());
	}
	public void toEntity(ReferentiDTO referenteDTO, String tipoReferente)
	{
		super.toEntity(referenteDTO, tipoReferente);
		referenteDTO.setTitolaritaId(this.getTitolaritaInQualitaDi());
		referenteDTO.setSpecificaTitolarita(this.getDescrizioneAltro());
	}

}
