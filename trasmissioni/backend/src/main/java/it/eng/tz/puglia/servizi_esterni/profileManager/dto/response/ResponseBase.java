package it.eng.tz.puglia.servizi_esterni.profileManager.dto.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponseBase<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  descrizioneEsito;
	private Integer codiceEsito;
	private Integer numeroTotaleOggetti;
	private Integer numeroOggettiRestituiti;

  //private Payload payload;
	private T 		payload;

	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public ResponseBase() {
	}

	public ResponseBase(String descrizioneEsito, Integer codiceEsito, Integer numeroTotaleOggetti,
			Integer numeroOggettiRestituiti, T payload, Map<String, Object> additionalProperties) {
		super();
		this.descrizioneEsito = descrizioneEsito;
		this.codiceEsito = codiceEsito;
		this.numeroTotaleOggetti = numeroTotaleOggetti;
		this.numeroOggettiRestituiti = numeroOggettiRestituiti;
		this.payload = payload;
		this.additionalProperties = additionalProperties;
	}

	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}

	public Integer getCodiceEsito() {
		return codiceEsito;
	}

	public void setCodiceEsito(Integer codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public Integer getNumeroTotaleOggetti() {
		return numeroTotaleOggetti;
	}

	public void setNumeroTotaleOggetti(Integer numeroTotaleOggetti) {
		this.numeroTotaleOggetti = numeroTotaleOggetti;
	}

	public Integer getNumeroOggettiRestituiti() {
		return numeroOggettiRestituiti;
	}

	public void setNumeroOggettiRestituiti(Integer numeroOggettiRestituiti) {
		this.numeroOggettiRestituiti = numeroOggettiRestituiti;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalProperties == null) ? 0 : additionalProperties.hashCode());
		result = prime * result + ((codiceEsito == null) ? 0 : codiceEsito.hashCode());
		result = prime * result + ((descrizioneEsito == null) ? 0 : descrizioneEsito.hashCode());
		result = prime * result + ((numeroOggettiRestituiti == null) ? 0 : numeroOggettiRestituiti.hashCode());
		result = prime * result + ((numeroTotaleOggetti == null) ? 0 : numeroTotaleOggetti.hashCode());
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
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
		@SuppressWarnings("unchecked")
		ResponseBase<T> other = (ResponseBase<T>) obj;
		if (additionalProperties == null) {
			if (other.additionalProperties != null)
				return false;
		} else if (!additionalProperties.equals(other.additionalProperties))
			return false;
		if (codiceEsito == null) {
			if (other.codiceEsito != null)
				return false;
		} else if (!codiceEsito.equals(other.codiceEsito))
			return false;
		if (descrizioneEsito == null) {
			if (other.descrizioneEsito != null)
				return false;
		} else if (!descrizioneEsito.equals(other.descrizioneEsito))
			return false;
		if (numeroOggettiRestituiti == null) {
			if (other.numeroOggettiRestituiti != null)
				return false;
		} else if (!numeroOggettiRestituiti.equals(other.numeroOggettiRestituiti))
			return false;
		if (numeroTotaleOggetti == null) {
			if (other.numeroTotaleOggetti != null)
				return false;
		} else if (!numeroTotaleOggetti.equals(other.numeroTotaleOggetti))
			return false;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseLoggedUser [descrizioneEsito=" + descrizioneEsito + ", codiceEsito=" + codiceEsito
				+ ", numeroTotaleOggetti=" + numeroTotaleOggetti + ", numeroOggettiRestituiti="
				+ numeroOggettiRestituiti + ", payload=" + payload + ", additionalProperties=" + additionalProperties
				+ "]";
	}
	
}
