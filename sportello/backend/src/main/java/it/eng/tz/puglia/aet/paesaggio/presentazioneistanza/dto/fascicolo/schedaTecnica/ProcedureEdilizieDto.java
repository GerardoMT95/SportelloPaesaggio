package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AssoggProcedureEdilizieDTO;

public class ProcedureEdilizieDto extends PraticaChild
{
	private static final long serialVersionUID = -4190254265685393804L;
	
	private Integer tipoIntervento;
	private List<TipologicaFE> tipoStato;
	private String motivazione;
	private String detagglio;
	@JsonDeserialize(using=SqlDateDeserializer.class)
    @JsonSerialize(using=DateSerializer.class)
	private Date pressoData;
	@JsonDeserialize(using=SqlDateDeserializer.class)
    @JsonSerialize(using=DateSerializer.class)
	private Date espressoData;

	
	public ProcedureEdilizieDto()
	{
		super();
	}

	public ProcedureEdilizieDto(AssoggProcedureEdilizieDTO entity) {
		
			 if (entity.getFlagAssoggettato()==null) 	   this.tipoIntervento = null;
		else if (entity.getFlagAssoggettato().equals("N")) this.tipoIntervento = 1;
		else if (entity.getFlagAssoggettato().equals("S")) this.tipoIntervento = 2;
		else 											   this.tipoIntervento = null;
		
		this.tipoStato = new ArrayList<>();
		if (entity.getAssoggFlagPraticaInCorso()!=null && entity.getAssoggFlagPraticaInCorso().equals("S")) this.tipoStato.add(new TipologicaFE(1,""));
		if (entity.getAssoggFlagParereUrbEspr ()!=null && entity.getAssoggFlagParereUrbEspr ().equals("S")) this.tipoStato.add(new TipologicaFE(2,""));
		
		this.motivazione  = entity.getNoAssoggSpecificare();
		this.detagglio    = entity.getAssoggEntePraticaInCorso();
		this.pressoData   = entity.getAssoggDataprPraticaInCorso();
		this.espressoData = entity.getAssoggDataApprovPratica();
	}
	
	
	public Integer getTipoIntervento() {
		return tipoIntervento;
	}

	public void setTipoIntervento(Integer tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public List<TipologicaFE> getTipoStato() {
		return tipoStato;
	}

	public void setTipoStato(List<TipologicaFE> tipoStato) {
		this.tipoStato = tipoStato;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getDetagglio() {
		return detagglio;
	}

	public void setDetagglio(String detagglio) {
		this.detagglio = detagglio;
	}

	public Date getPressoData() {
		return pressoData;
	}

	public void setPressoData(Date pressoData) {
		this.pressoData = pressoData;
	}

	public Date getEspressoData() {
		return espressoData;
	}

	public void setEspressoData(Date espressoData) {
		this.espressoData = espressoData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((detagglio == null) ? 0 : detagglio.hashCode());
		result = prime * result + ((espressoData == null) ? 0 : espressoData.hashCode());
		result = prime * result + ((motivazione == null) ? 0 : motivazione.hashCode());
		result = prime * result + ((pressoData == null) ? 0 : pressoData.hashCode());
		result = prime * result + ((tipoIntervento == null) ? 0 : tipoIntervento.hashCode());
		result = prime * result + ((tipoStato == null) ? 0 : tipoStato.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcedureEdilizieDto other = (ProcedureEdilizieDto) obj;
		if (detagglio == null) {
			if (other.detagglio != null)
				return false;
		} else if (!detagglio.equals(other.detagglio))
			return false;
		if (espressoData == null) {
			if (other.espressoData != null)
				return false;
		} else if (!espressoData.equals(other.espressoData))
			return false;
		if (motivazione == null) {
			if (other.motivazione != null)
				return false;
		} else if (!motivazione.equals(other.motivazione))
			return false;
		if (pressoData == null) {
			if (other.pressoData != null)
				return false;
		} else if (!pressoData.equals(other.pressoData))
			return false;
		if (tipoIntervento == null) {
			if (other.tipoIntervento != null)
				return false;
		} else if (!tipoIntervento.equals(other.tipoIntervento))
			return false;
		if (tipoStato == null) {
			if (other.tipoStato != null)
				return false;
		} else if (!tipoStato.equals(other.tipoStato))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcedureEdilizieDto [tipoIntervento=" + tipoIntervento + ", tipoStato=" + tipoStato + ", motivazione="
				+ motivazione + ", detagglio=" + detagglio + ", pressoData=" + pressoData + ", espressoData="
				+ espressoData + "]";
	}

}