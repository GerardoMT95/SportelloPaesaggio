package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

@RequiredDependsOn.List({
	@RequiredDependsOn(field = "diAvereTitoloAltroSpec", dependsOn="titolarita", dependsOnValue="9", message="Il campo 'diAvereTitoloAltroSpec' non può essere nullo o lasciato vuoto"),
	@RequiredDependsOn(field = "diAvereTitoloRappSpec", dependsOn="titolarita", dependsOnValue="5", message="Il campo 'diAvereTitoloRappSpec' non può essere nullo o lasciato vuoto"),
	@RequiredDependsOn(field = "allegato", dependsOn="titolaritaEsclusivaIntervento", dependsOnValue="N", message="La presenza dell'allegato è obbligatoria")
})
public class DichiarazioniDto implements Serializable
{
	private static final long serialVersionUID = 8057315983205942087L;
	@NotNull(message="Il campo 'titolarita' non può essere nullo")
	@Min(value=1, message="Valore del campo 'titolarita' non ammesso")
	@Max(value=9, message="Valore del campo 'titolarita' non ammesso")
	private Integer titolarita; // presa da ruolo_referente 4=proprietario esclusivo
	private String diAvereTitoloRappSpec;
	private String diAvereTitoloAltroSpec;
	private String titolaritaEsclusivaIntervento; // 'S'i 'N'o
	/* tabella altre_dichiarazioni_rich: da validare manualmente in quanto dipende da 'tipoProcedimento' */
	private Boolean inCasoDiVariante; //
	private String numero;
	private String da;
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date inData;

	private List<String> art136;
	private List<String> art142; 
	private Boolean art134;  

	/* tabella disclaimer_pratica */
	private List<Integer> altreOpzioni; 
	
	private AllegatiDTO allegato;

	public Integer getTitolarita()
	{
		return titolarita;
	}
	public void setTitolarita(Integer titolarita)
	{
		this.titolarita = titolarita;
	}

	public String getDiAvereTitoloRappSpec()
	{
		return diAvereTitoloRappSpec;
	}
	public void setDiAvereTitoloRappSpec(String diAvereTitoloRappSpec)
	{
		this.diAvereTitoloRappSpec = diAvereTitoloRappSpec;
	}

	public String getDiAvereTitoloAltroSpec()
	{
		return diAvereTitoloAltroSpec;
	}
	public void setDiAvereTitoloAltroSpec(String diAvereTitoloAltroSpec)
	{
		this.diAvereTitoloAltroSpec = diAvereTitoloAltroSpec;
	}

	public String getTitolaritaEsclusivaIntervento()
	{
		return titolaritaEsclusivaIntervento;
	}
	public void setTitolaritaEsclusivaIntervento(String titolaritaEsclusivaIntervento)
	{
		this.titolaritaEsclusivaIntervento = titolaritaEsclusivaIntervento;
	}

	public Boolean getInCasoDiVariante()
	{
		return inCasoDiVariante;
	}
	public void setInCasoDiVariante(Boolean inCasoDiVariante)
	{
		this.inCasoDiVariante = inCasoDiVariante;
	}

	public String getNumero()
	{
		return numero;
	}
	public void setNumero(String numero)
	{
		this.numero = numero;
	}

	public String getDa()
	{
		return da;
	}
	public void setDa(String da)
	{
		this.da = da;
	}

	public Date getInData()
	{
		return inData;
	}
	public void setInData(Date inData)
	{
		this.inData = inData;
	}

	public List<Integer> getAltreOpzioni()
	{
		return altreOpzioni;
	}
	public void setAltreOpzioni(List<Integer> altreOpzioni)
	{
		this.altreOpzioni = altreOpzioni;
	}

	public List<String> getArt136()
	{
		return art136;
	}
	public void setArt136(List<String> art136)
	{
		this.art136 = art136;
	}

	public List<String> getArt142()
	{
		return art142;
	}
	public void setArt142(List<String> art142)
	{
		this.art142 = art142;
	}

	public Boolean getArt134()
	{
		return art134;
	}
	public void setArt134(Boolean art134)
	{
		this.art134 = art134;
	}

	public AllegatiDTO getAllegato()
	{
		return allegato;
	}
	public void setAllegato(AllegatiDTO allegato)
	{
		this.allegato = allegato;
	}

}
