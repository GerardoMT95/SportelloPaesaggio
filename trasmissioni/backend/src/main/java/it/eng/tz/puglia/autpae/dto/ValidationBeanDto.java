/**
 * 
 */
package it.eng.tz.puglia.autpae.dto;

import java.util.List;

import it.eng.tz.puglia.util.list.ListUtil;

/**
 * @author Adriano Colaianni
 * @date 10 mag 2021
 */
public class ValidationBeanDto {
	
	private List<SelectOptionDto> richiedenteError;
	private List<SelectOptionDto> fascicoloError;
	private List<SelectOptionDto> interventoError;
	private List<SelectOptionDto> localizzazioneError;
	private List<SelectOptionDto> provvedimentoError;
	private List<SelectOptionDto> allegatiError;
	
	public List<SelectOptionDto> getRichiedenteError() {
		return richiedenteError;
	}
	public void setRichiedenteError(List<SelectOptionDto> richiedenteError) {
		this.richiedenteError = richiedenteError;
	}
	public List<SelectOptionDto> getFascicoloError() {
		return fascicoloError;
	}
	public void setFascicoloError(List<SelectOptionDto> fascicoloError) {
		this.fascicoloError = fascicoloError;
	}
	public List<SelectOptionDto> getInterventoError() {
		return interventoError;
	}
	public void setInterventoError(List<SelectOptionDto> interventoError) {
		this.interventoError = interventoError;
	}
	public List<SelectOptionDto> getLocalizzazioneError() {
		return localizzazioneError;
	}
	public void setLocalizzazioneError(List<SelectOptionDto> localizzazioneError) {
		this.localizzazioneError = localizzazioneError;
	}
	public List<SelectOptionDto> getProvvedimentoError() {
		return provvedimentoError;
	}
	public void setProvvedimentoError(List<SelectOptionDto> provvedimentoError) {
		this.provvedimentoError = provvedimentoError;
	}
	public List<SelectOptionDto> getAllegatiError() {
		return allegatiError;
	}
	public void setAllegatiError(List<SelectOptionDto> allegatiError) {
		this.allegatiError = allegatiError;
	}
	
	public boolean isValid() {
		return ListUtil.isEmpty(this.allegatiError) &&
				ListUtil.isEmpty(this.fascicoloError) &&
				ListUtil.isEmpty(this.localizzazioneError) &&
				ListUtil.isEmpty(this.richiedenteError) &&
				ListUtil.isEmpty(this.provvedimentoError) &&
				ListUtil.isEmpty(this.allegatiError); 
	}

}
