package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.util.List;


/**
 * Dto per configurazione cds
 * @author Antonio La Gatta
 * @date 16 mar 2022
 */
public class CdsSaveConfigurazioneDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 16 mar 2022
	 */
	private static final long serialVersionUID = -4230291815440583673L;
	
	private List<SelectParentItemDto> attivita;
	private List<SelectParentItemDto> azioni;
	private List<SelectParentItemDto> tipo;

	/**
	 * @return the attivita
	 */
	public List<SelectParentItemDto> getAttivita() {
		return this.attivita;
	}
	/**
	 * @param attivita the attivita to set
	 */
	public void setAttivita(final List<SelectParentItemDto> attivita) {
		this.attivita = attivita;
	}
	/**
	 * @return the azioni
	 */
	public List<SelectParentItemDto> getAzioni() {
		return this.azioni;
	}
	/**
	 * @param azioni the azioni to set
	 */
	public void setAzioni(final List<SelectParentItemDto> azioni) {
		this.azioni = azioni;
	}
	/**
	 * @return the tipo
	 */
	public List<SelectParentItemDto> getTipo() {
		return this.tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(final List<SelectParentItemDto> tipo) {
		this.tipo = tipo;
	}
}
