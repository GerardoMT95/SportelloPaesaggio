package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.util.List;


/**
 * Dto per configurazione cds
 * @author Antonio La Gatta
 * @date 16 mar 2022
 */
public class CdsGetConfigurazioneDto extends CdsSaveConfigurazioneDto{

	/**
	 * @author Antonio La Gatta
	 * @date 16 mar 2022
	 */
	private static final long serialVersionUID = -4230291815440583673L;
	
	private List<SelectParentItemDto> attivitaSelezionabili;
	private List<SelectParentItemDto> azioniSelezionabili;
	private List<SelectParentItemDto> tipoSelezionabili;
	/**
	 * @return the attivitaSelezionabili
	 */
	public List<SelectParentItemDto> getAttivitaSelezionabili() {
		return this.attivitaSelezionabili;
	}
	/**
	 * @param attivitaSelezionabili the attivitaSelezionabili to set
	 */
	public void setAttivitaSelezionabili(final List<SelectParentItemDto> attivitaSelezionabili) {
		this.attivitaSelezionabili = attivitaSelezionabili;
	}
	/**
	 * @return the azioniSelezionabili
	 */
	public List<SelectParentItemDto> getAzioniSelezionabili() {
		return this.azioniSelezionabili;
	}
	/**
	 * @param azioniSelezionabili the azioniSelezionabili to set
	 */
	public void setAzioniSelezionabili(final List<SelectParentItemDto> azioniSelezionabili) {
		this.azioniSelezionabili = azioniSelezionabili;
	}
	/**
	 * @return the tipoSelezionabili
	 */
	public List<SelectParentItemDto> getTipoSelezionabili() {
		return this.tipoSelezionabili;
	}
	/**
	 * @param tipoSelezionabili the tipoSelezionabili to set
	 */
	public void setTipoSelezionabili(final List<SelectParentItemDto> tipoSelezionabili) {
		this.tipoSelezionabili = tipoSelezionabili;
	}

}
