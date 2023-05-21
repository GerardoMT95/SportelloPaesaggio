package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateTimestampDeserializer;

/**
 * Report parameter
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
public class ReportParameterBean implements Serializable{
	
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 */
	private static final long serialVersionUID = 6780571308613762767L;
	
	private List<String> idProcedimento;
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateTimestampDeserializer.class)
	private java.sql.Timestamp dateFrom;
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateTimestampDeserializer.class)
	private java.sql.Timestamp dateTo;
	private String estensione;
	private int enteDelegato;
	private String descrizioneEnte;
	
	private boolean dirigente;

	/**
	 * @return the idProcedimento
	 */
	public List<String> getIdProcedimento() {
		return this.idProcedimento;
	}
	/**
	 * @param idProcedimento the idProcedimento to set
	 */
	public void setIdProcedimento(final List<String> idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	/**
	 * @return the dateFrom
	 */
	public java.sql.Timestamp getDateFrom() {
		return this.dateFrom;
	}
	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(final java.sql.Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return the dateTo
	 */
	public java.sql.Timestamp getDateTo() {
		return this.dateTo;
	}
	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(final java.sql.Timestamp dateTo) {
		this.dateTo = dateTo;
	}
	/**
	 * @return the dirigente
	 */
	public boolean isDirigente() {
		return this.dirigente;
	}
	/**
	 * @param dirigente the dirigente to set
	 */
	public void setDirigente(final boolean dirigente) {
		this.dirigente = dirigente;
	}
	public String getEstensione() {
		return this.estensione;
	}
	public void setEstensione(final String estensione) {
		this.estensione = estensione;
	}
	public int getEnteDelegato() {
		return this.enteDelegato;
	}
	public void setEnteDelegato(final int enteDelegato) {
		this.enteDelegato = enteDelegato;
	}
	public String getDescrizioneEnte() {
		return this.descrizioneEnte;
	}
	public void setDescrizioneEnte(final String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}

}
