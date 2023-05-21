package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DTO for table presentazione_istanza.disclaimer
 */
public class DisclaimerDTO implements Serializable {

	private static final long serialVersionUID = 2378454102L;
	// COLUMN id
	private int id;
	// COLUMN testo
	private String testo;
	// COLUMN tipo_procedimento
	private int tipoProcedimento;
	// COLUMN tipo_referente
	private String tipoReferente;
	// COLUMN data_inizio
	@JsonIgnore
	private Date dataInizio;
	// COLUMN data_fine
	@JsonIgnore
	private Date dataFine;
	// COLUMN user_updated
	@JsonIgnore
	private String userUpdated;
	// COLUMN user_inserted
	@JsonIgnore
	private String userInserted;
	// COLUMN ordine
	private Integer ordine;
	// COLUMN required
	private Boolean required;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTesto() {
		return this.testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getTipoProcedimento() {
		return this.tipoProcedimento;
	}

	public void setTipoProcedimento(int tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public String getTipoReferente() {
		return this.tipoReferente;
	}

	public void setTipoReferente(String tipoReferente) {
		this.tipoReferente = tipoReferente;
	}

	/**
	 * @return the dataInizio
	 */
	public Date getDataInizio() {
		return dataInizio;
	}

	/**
	 * @param dataInizio the dataInizio to set
	 */
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	/**
	 * @return the dataFine
	 */
	public Date getDataFine() {
		return dataFine;
	}

	/**
	 * @param dataFine the dataFine to set
	 */
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	/**
	 * @return the userUpdated
	 */
	public String getUserUpdated() {
		return userUpdated;
	}

	/**
	 * @param userUpdated the userUpdated to set
	 */
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}

	/**
	 * @return the userInserted
	 */
	public String getUserInserted() {
		return userInserted;
	}

	/**
	 * @param userInserted the userInserted to set
	 */
	public void setUserInserted(String userInserted) {
		this.userInserted = userInserted;
	}

	/**
	 * @return the ordine
	 */
	public Integer getOrdine() {
		return ordine;
	}

	/**
	 * @param ordine the ordine to set
	 */
	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	/**
	 * @return the required
	 */
	public Boolean getRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(Boolean required) {
		this.required = required;
	}

	@Override
	public String toString() {
		return "DisclaimerDTO [id=" + id + ", testo=" + testo + ", tipoProcedimento=" + tipoProcedimento
				+ ", tipoReferente=" + tipoReferente + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine
				+ ", userUpdated=" + userUpdated + ", userInserted=" + userInserted + ", ordine=" + ordine
				+ ", required=" + required + "]";
	}

}
