package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaProvvedimentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaTitoliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PareriEAttiAssensoDTO;
import it.eng.tz.puglia.util.string.StringUtil;

public class TipologiaDettaglioDto extends PraticaChild
{
	private static final long serialVersionUID = -6454411993947198685L;
	
	private String tipologia;
    private String rialisciatoDa;
    private String noProtocollo;
    @JsonDeserialize(using=SqlDateDeserializer.class)
    @JsonSerialize(using=DateSerializer.class)
    private Date dataRilascio;
    private String intestinario;
    
    
    public TipologiaDettaglioDto()
    {
    	super();
    }

    public TipologiaDettaglioDto(LeggittimitaTitoliDTO leggittimitaTitoli)
    {
    	this.tipologia     = leggittimitaTitoli.getLegTitDenominazione();
    	this.rialisciatoDa = leggittimitaTitoli.getLegTitRilasciatoDa();
    	this.noProtocollo  = leggittimitaTitoli.getLegTitNumProtocollo();
    	this.dataRilascio  = leggittimitaTitoli.getLegTitDataRilascio();
    	this.intestinario  = leggittimitaTitoli.getLegTitIntestatario();
    }
    
    public TipologiaDettaglioDto(LeggittimitaProvvedimentiDTO leggittimitaProvvedimenti)
    {
    	this.tipologia     = leggittimitaProvvedimenti.getLegProvvDenominazione();
    	this.rialisciatoDa = leggittimitaProvvedimenti.getLegProvvRilasciatoDa();
    	this.noProtocollo  = leggittimitaProvvedimenti.getLegProvvNumProtocollo();
    	this.dataRilascio  = leggittimitaProvvedimenti.getLegProvvDataRilascio();
    	this.intestinario  = leggittimitaProvvedimenti.getLegProvvIntestatario();
    }

    public TipologiaDettaglioDto(PareriEAttiAssensoDTO pareriEAttiAssenso)
    {
    	this.tipologia     = pareriEAttiAssenso.getTipologiaAtto();
    	this.rialisciatoDa = pareriEAttiAssenso.getAutoritaRilascio();
    	this.noProtocollo  = pareriEAttiAssenso.getProtocollo();
    	this.dataRilascio  = pareriEAttiAssenso.getDataRilascio();
    	this.intestinario  = pareriEAttiAssenso.getIntestatarioAtto();
    }
    
	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getRialisciatoDa() {
		return rialisciatoDa;
	}

	public void setRialisciatoDa(String rialisciatoDa) {
		this.rialisciatoDa = rialisciatoDa;
	}

	public String getNoProtocollo() {
		return noProtocollo;
	}

	public void setNoProtocollo(String noProtocollo) {
		this.noProtocollo = noProtocollo;
	}

	public Date getDataRilascio() {
		return dataRilascio;
	}

	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}

	public String getIntestinario() {
		return intestinario;
	}

	public void setIntestinario(String intestinario) {
		this.intestinario = intestinario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataRilascio == null) ? 0 : dataRilascio.hashCode());
		result = prime * result + ((intestinario == null) ? 0 : intestinario.hashCode());
		result = prime * result + ((noProtocollo == null) ? 0 : noProtocollo.hashCode());
		result = prime * result + ((rialisciatoDa == null) ? 0 : rialisciatoDa.hashCode());
		result = prime * result + ((tipologia == null) ? 0 : tipologia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipologiaDettaglioDto other = (TipologiaDettaglioDto) obj;
		if (dataRilascio == null) {
			if (other.dataRilascio != null)
				return false;
		} else if (!dataRilascio.equals(other.dataRilascio))
			return false;
		if (intestinario == null) {
			if (other.intestinario != null)
				return false;
		} else if (!intestinario.equals(other.intestinario))
			return false;
		if (noProtocollo == null) {
			if (other.noProtocollo != null)
				return false;
		} else if (!noProtocollo.equals(other.noProtocollo))
			return false;
		if (rialisciatoDa == null) {
			if (other.rialisciatoDa != null)
				return false;
		} else if (!rialisciatoDa.equals(other.rialisciatoDa))
			return false;
		if (tipologia == null) {
			if (other.tipologia != null)
				return false;
		} else if (!tipologia.equals(other.tipologia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipologiaDettaglioDto [tipologia=" + tipologia + ", rialisciatoDa=" + rialisciatoDa + ", noProtocollo="
				+ noProtocollo + ", dataRilascio=" + dataRilascio + ", intestinario=" + intestinario + "]";
	}
	
	/**
	 * metodo per restituire un digest per controllare eventuali valori duplicati in lista
	 * @author acolaianni
	 *
	 * @return
	 */
	public String digest() {
		return StringUtil.concateneString(
				this.tipologia.strip().toLowerCase(), 
				this.rialisciatoDa.strip().toLowerCase(),
				this.noProtocollo.strip().toLowerCase(), 
				this.dataRilascio.toString(), 
				this.intestinario.strip().toLowerCase());
	}
	
}