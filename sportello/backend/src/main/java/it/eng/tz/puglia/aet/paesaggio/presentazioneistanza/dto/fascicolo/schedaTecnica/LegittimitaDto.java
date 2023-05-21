package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaDTO;

public class LegittimitaDto extends PraticaChild
{
	private static final long serialVersionUID = 2830669138157967471L;
	
	private Integer tipoLegittimitaUrbanistica;
	private String legittimitaUrbanstica;
	private List<TipologiaDettaglioDto> legittimitaInfo;

	private Integer tipoLegittimitaPaesaggistica;
	private ImposizioneDelVincoloDto dettaglioLegittimitaPaesaggistica;
	private List<TipologiaDettaglioDto> autorizzatoPaesaggisticamenteInfo;
	
	
	public LegittimitaDto()
	{
		super();
	}
	
	public LegittimitaDto(LeggittimitaDTO leggittimita, List<TipologiaDettaglioDto> legittimitaInfo, List<TipologiaDettaglioDto> autorizzatoPaesaggisticamenteInfo)
	{
		if (leggittimita.getLegUrbTitEdilizio()==null)
			this.tipoLegittimitaUrbanistica = null;
		else if (leggittimita.getLegUrbTitEdilizio().equals("PT"))
			this.tipoLegittimitaUrbanistica = 1;
		else if (leggittimita.getLegUrbTitEdilizio().equals("DT"))
			this.tipoLegittimitaUrbanistica = 2;
		else
			this.tipoLegittimitaUrbanistica = null;
		
		this.legittimitaUrbanstica = leggittimita.getLegUrbPrivoSpecifica();
		this.legittimitaInfo = legittimitaInfo;
		this.autorizzatoPaesaggisticamenteInfo = autorizzatoPaesaggisticamenteInfo;
		
		if (leggittimita.getLegPaesagImmobile()==null)
			this.tipoLegittimitaPaesaggistica = null;
		else if (leggittimita.getLegPaesagImmobile().equals("PV"))
			this.tipoLegittimitaPaesaggistica = 1;
		else if (leggittimita.getLegPaesagImmobile().equals("AP"))
			this.tipoLegittimitaPaesaggistica = 2;
		else
			this.tipoLegittimitaPaesaggistica = null;
		
		this.dettaglioLegittimitaPaesaggistica = new ImposizioneDelVincoloDto(leggittimita.getLegPaeTipoVincolo(), 
																			  leggittimita.getLegPaeDataIntervento(), 
																			  leggittimita.getLegPaeDataVincolo());
	}

	public Integer getTipoLegittimitaUrbanistica() {
		return tipoLegittimitaUrbanistica;
	}

	public void setTipoLegittimitaUrbanistica(Integer tipoLegittimitaUrbanistica) {
		this.tipoLegittimitaUrbanistica = tipoLegittimitaUrbanistica;
	}

	public String getLegittimitaUrbanstica() {
		return legittimitaUrbanstica;
	}

	public void setLegittimitaUrbanstica(String legittimitaUrbanstica) {
		this.legittimitaUrbanstica = legittimitaUrbanstica;
	}

	public List<TipologiaDettaglioDto> getLegittimitaInfo() {
		return legittimitaInfo;
	}

	public void setLegittimitaInfo(List<TipologiaDettaglioDto> legittimitaInfo) {
		this.legittimitaInfo = legittimitaInfo;
	}

	public Integer getTipoLegittimitaPaesaggistica() {
		return tipoLegittimitaPaesaggistica;
	}

	public void setTipoLegittimitaPaesaggistica(Integer tipoLegittimitaPaesaggistica) {
		this.tipoLegittimitaPaesaggistica = tipoLegittimitaPaesaggistica;
	}

	public ImposizioneDelVincoloDto getDettaglioLegittimitaPaesaggistica() {
		return dettaglioLegittimitaPaesaggistica;
	}

	public void setDettaglioLegittimitaPaesaggistica(ImposizioneDelVincoloDto dettaglioLegittimitaPaesaggistica) {
		this.dettaglioLegittimitaPaesaggistica = dettaglioLegittimitaPaesaggistica;
	}

	public List<TipologiaDettaglioDto> getAutorizzatoPaesaggisticamenteInfo() {
		return autorizzatoPaesaggisticamenteInfo;
	}

	public void setAutorizzatoPaesaggisticamenteInfo(List<TipologiaDettaglioDto> autorizzatoPaesaggisticamenteInfo) {
		this.autorizzatoPaesaggisticamenteInfo = autorizzatoPaesaggisticamenteInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result	+ ((autorizzatoPaesaggisticamenteInfo == null) ? 0 : autorizzatoPaesaggisticamenteInfo.hashCode());
		result = prime * result	+ ((dettaglioLegittimitaPaesaggistica == null) ? 0 : dettaglioLegittimitaPaesaggistica.hashCode());
		result = prime * result + ((legittimitaInfo == null) ? 0 : legittimitaInfo.hashCode());
		result = prime * result + ((legittimitaUrbanstica == null) ? 0 : legittimitaUrbanstica.hashCode());
		result = prime * result	+ ((tipoLegittimitaPaesaggistica == null) ? 0 : tipoLegittimitaPaesaggistica.hashCode());
		result = prime * result + ((tipoLegittimitaUrbanistica == null) ? 0 : tipoLegittimitaUrbanistica.hashCode());
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
		LegittimitaDto other = (LegittimitaDto) obj;
		if (autorizzatoPaesaggisticamenteInfo == null) {
			if (other.autorizzatoPaesaggisticamenteInfo != null)
				return false;
		} else if (!autorizzatoPaesaggisticamenteInfo.equals(other.autorizzatoPaesaggisticamenteInfo))
			return false;
		if (dettaglioLegittimitaPaesaggistica == null) {
			if (other.dettaglioLegittimitaPaesaggistica != null)
				return false;
		} else if (!dettaglioLegittimitaPaesaggistica.equals(other.dettaglioLegittimitaPaesaggistica))
			return false;
		if (legittimitaInfo == null) {
			if (other.legittimitaInfo != null)
				return false;
		} else if (!legittimitaInfo.equals(other.legittimitaInfo))
			return false;
		if (legittimitaUrbanstica == null) {
			if (other.legittimitaUrbanstica != null)
				return false;
		} else if (!legittimitaUrbanstica.equals(other.legittimitaUrbanstica))
			return false;
		if (tipoLegittimitaPaesaggistica == null) {
			if (other.tipoLegittimitaPaesaggistica != null)
				return false;
		} else if (!tipoLegittimitaPaesaggistica.equals(other.tipoLegittimitaPaesaggistica))
			return false;
		if (tipoLegittimitaUrbanistica == null) {
			if (other.tipoLegittimitaUrbanistica != null)
				return false;
		} else if (!tipoLegittimitaUrbanistica.equals(other.tipoLegittimitaUrbanistica))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LegittimitaDto [tipoLegittimitaUrbanistica=" + tipoLegittimitaUrbanistica + ", legittimitaUrbanstica="
				+ legittimitaUrbanstica + ", legittimitaInfo=" + legittimitaInfo + ", tipoLegittimitaPaesaggistica="
				+ tipoLegittimitaPaesaggistica + ", dettaglioLegittimitaPaesaggistica="
				+ dettaglioLegittimitaPaesaggistica + ", autorizzatoPaesaggisticamenteInfo="
				+ autorizzatoPaesaggisticamenteInfo + "]";
	}
	
}