package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateTimestampDeserializer;

/**
 * Ricerca report
 * @author Antonio La Gatta
 * @date 3 mag 2022
 */
public class InsertReportBean extends BaseSearchRequestBean{
	
	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = 6081749861995505329L;

	private String tipo;
	private String formato;
	private List<String> tipoProcedimento;
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateTimestampDeserializer.class)
	private java.sql.Timestamp dataFrom;
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateTimestampDeserializer.class)
	private java.sql.Timestamp dataTo;
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return this.tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the formato
	 */
	public String getFormato() {
		return this.formato;
	}
	/**
	 * @param formato the formato to set
	 */
	public void setFormato(final String formato) {
		this.formato = formato;
	}
	/**
	 * @return the tipoProcedimento
	 */
	public List<String> getTipoProcedimento() {
		return this.tipoProcedimento;
	}
	/**
	 * @param tipoProcedimento the tipoProcedimento to set
	 */
	public void setTipoProcedimento(final List<String> tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	/**
	 * @return the dataFrom
	 */
	public java.sql.Timestamp getDataFrom() {
		return this.dataFrom;
	}
	/**
	 * @param dataFrom the dataFrom to set
	 */
	public void setDataFrom(final java.sql.Timestamp dataFrom) {
		this.dataFrom = dataFrom;
	}
	/**
	 * @return the dataTo
	 */
	public java.sql.Timestamp getDataTo() {
		return this.dataTo;
	}
	/**
	 * @param dataTo the dataTo to set
	 */
	public void setDataTo(final java.sql.Timestamp dataTo) {
		this.dataTo = dataTo;
	}
}
