/**
 * 
 */
package it.eng.tz.puglia.autpae.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;

/**
 * @author Adriano Colaianni
 * @date 6 mag 2021
 */
@ApiModel(description = "Payload per la creazione di una pratica")
public class FascicoloBaseDto {
	
	@ApiModelProperty(name = "Tipo procedimento", dataType = "String", required = true)
	@NotNull
	private TipoProcedimento tipoProcedimento;
	@Size( max=5000)
	@NotNull
	private String oggettoIntervento; // in fase di creazione
	public TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public String getOggettoIntervento() {
		return oggettoIntervento;
	}
	public void setOggettoIntervento(String oggettoIntervento) {
		this.oggettoIntervento = oggettoIntervento;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oggettoIntervento == null) ? 0 : oggettoIntervento.hashCode());
		result = prime * result + ((tipoProcedimento == null) ? 0 : tipoProcedimento.hashCode());
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
		FascicoloBaseDto other = (FascicoloBaseDto) obj;
		if (oggettoIntervento == null) {
			if (other.oggettoIntervento != null)
				return false;
		} else if (!oggettoIntervento.equals(other.oggettoIntervento))
			return false;
		if (tipoProcedimento != other.tipoProcedimento)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FascicoloBaseDto [tipoProcedimento=" + tipoProcedimento + ", oggettoIntervento=" + oggettoIntervento
				+ "]";
	}
	
	

}
