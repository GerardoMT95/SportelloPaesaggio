package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportOneriIstruttoriDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = 180367069040112117L;

	private Double onere;
	private String tipoProcedimento;
	/**
	 * @return the onere
	 */
	public Double getOnere() {
		return this.onere;
	}
	/**
	 * @param onere the onere to set
	 */
	public void setOnere(final Double onere) {
		this.onere = onere;
	}
	/**
	 * @return the tipoProcedimento
	 */
	public String getTipoProcedimento() {
		return this.tipoProcedimento;
	}
	/**
	 * @param tipoProcedimento the tipoProcedimento to set
	 */
	public void setTipoProcedimento(final String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
}
