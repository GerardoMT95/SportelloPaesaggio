package it.eng.tz.puglia.servizi_esterni.remote.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnteSerializer;

public class EnteWIstatDTO extends EnteDTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String istat;

	public String getIstat() {
		return istat;
	}

	public void setIstat(String istat) {
		this.istat = istat;
	}
	
}
