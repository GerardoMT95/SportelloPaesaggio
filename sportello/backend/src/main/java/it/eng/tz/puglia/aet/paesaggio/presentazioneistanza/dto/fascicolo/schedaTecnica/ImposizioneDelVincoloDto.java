package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

public class ImposizioneDelVincoloDto implements Serializable
{
	private static final long serialVersionUID = 2830669138157967471L;
	
	private String tipologiaVincolo;
    @JsonDeserialize(using=SqlDateDeserializer.class)
    @JsonSerialize(using=DateSerializer.class)
	private Date dataIntervento;
    @JsonDeserialize(using=SqlDateDeserializer.class)
    @JsonSerialize(using=DateSerializer.class)
    private Date dataImposizioneVincolo;
    
    
    public ImposizioneDelVincoloDto() { }
    
    
	public ImposizioneDelVincoloDto(String legPaeTipoVincolo, Date legPaeDataIntervento, Date legPaeDataVincolo) {
		this.tipologiaVincolo = legPaeTipoVincolo;
		this.dataIntervento=legPaeDataIntervento;
		this.dataImposizioneVincolo = legPaeDataVincolo;
	}


	public String getTipologiaVincolo() {
		return tipologiaVincolo;
	}
	public void setTipologiaVincolo(String tipologiaVincolo) {
		this.tipologiaVincolo = tipologiaVincolo;
	}
	public Date getDataIntervento() {
		return dataIntervento;
	}
	public void setDataIntervento(Date dataIntervento) {
		this.dataIntervento = dataIntervento;
	}
	public Date getDataImposizioneVincolo() {
		return dataImposizioneVincolo;
	}
	public void setDataImposizioneVincolo(Date dataImposizioneVincolo) {
		this.dataImposizioneVincolo = dataImposizioneVincolo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataImposizioneVincolo == null) ? 0 : dataImposizioneVincolo.hashCode());
		result = prime * result + ((dataIntervento == null) ? 0 : dataIntervento.hashCode());
		result = prime * result + ((tipologiaVincolo == null) ? 0 : tipologiaVincolo.hashCode());
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
		ImposizioneDelVincoloDto other = (ImposizioneDelVincoloDto) obj;
		if (dataImposizioneVincolo == null) {
			if (other.dataImposizioneVincolo != null)
				return false;
		} else if (!dataImposizioneVincolo.equals(other.dataImposizioneVincolo))
			return false;
		if (dataIntervento == null) {
			if (other.dataIntervento != null)
				return false;
		} else if (!dataIntervento.equals(other.dataIntervento))
			return false;
		if (tipologiaVincolo == null) {
			if (other.tipologiaVincolo != null)
				return false;
		} else if (!tipologiaVincolo.equals(other.tipologiaVincolo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ImposizioneDelVincoloDto [tipologiaVincolo=" + tipologiaVincolo + ", dataIntervento=" + dataIntervento
				+ ", dataImposizioneVincolo=" + dataImposizioneVincolo + "]";
	}
	
}