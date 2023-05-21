package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoInterventoDTO;

public class TipoInterventoDto implements Serializable
{
	private static final long serialVersionUID = -531718185926488558L;
	
	private String tipologiaDiIntervento;
	private String inParticolareAgliArtt;
	@JsonDeserialize(using=SqlDateDeserializer.class)
	@JsonSerialize(using=DateSerializer.class)
	private Date dataApprovazione;
	private String con;

	public TipoInterventoDto()
	{
		super();
	}
	public TipoInterventoDto(TipoInterventoDTO entity)
	{
		this.tipologiaDiIntervento = entity.getCodice();
		this.con = entity.getCon();
		this.dataApprovazione = entity.getDataApprovazione();
		this.inParticolareAgliArtt = entity.getArtt();
	}

	public String getTipologiaDiIntervento()
	{
		return tipologiaDiIntervento;
	}
	public void setTipologiaDiIntervento(String tipologiaDiIntervento)
	{
		this.tipologiaDiIntervento = tipologiaDiIntervento;
	}

	public String getInParticolareAgliArtt()
	{
		return inParticolareAgliArtt;
	}
	public void setInParticolareAgliArtt(String inParticolareAgliArtt)
	{
		this.inParticolareAgliArtt = inParticolareAgliArtt;
	}

	public Date getDataApprovazione()
	{
		return dataApprovazione;
	}
	public void setDataApprovazione(Date dataApprovazione)
	{
		this.dataApprovazione = dataApprovazione;
	}
	
	public String getCon()
	{
		return con;
	}
	public void setCon(String con)
	{
		this.con = con;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataApprovazione == null) ? 0 : dataApprovazione.hashCode());
		result = prime * result + ((con == null) ? 0 : con.hashCode());
		result = prime * result + ((inParticolareAgliArtt == null) ? 0 : inParticolareAgliArtt.hashCode());
		result = prime * result + ((tipologiaDiIntervento == null) ? 0 : tipologiaDiIntervento.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoInterventoDto other = (TipoInterventoDto) obj;
		if (dataApprovazione == null)
		{
			if (other.dataApprovazione != null)
				return false;
		} else if (!dataApprovazione.equals(other.dataApprovazione))
			return false;
		if (con == null)
		{
			if (other.con != null)
				return false;
		} else if (!con.equals(other.con))
			return false;
		if (inParticolareAgliArtt == null)
		{
			if (other.inParticolareAgliArtt != null)
				return false;
		} else if (!inParticolareAgliArtt.equals(other.inParticolareAgliArtt))
			return false;
		if (tipologiaDiIntervento == null)
		{
			if (other.tipologiaDiIntervento != null)
				return false;
		} else if (!tipologiaDiIntervento.equals(other.tipologiaDiIntervento))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "TipoIntervento [descrizione=" + con + ", tipologiaDiIntervento=" + tipologiaDiIntervento
				+ ", inParticolareAgliArtt=" + inParticolareAgliArtt + ", approvatoData=" + dataApprovazione + "]";
	}
	
}
