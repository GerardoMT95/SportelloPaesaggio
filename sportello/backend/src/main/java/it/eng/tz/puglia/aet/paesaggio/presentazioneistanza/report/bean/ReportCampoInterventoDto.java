package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

/**
 * Dto per report su campo intervento
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
public class ReportCampoInterventoDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 */
	private static final long serialVersionUID = -9165445063428431233L;
	
	
	private String tipoProcedimento;
	private String famigliaProcedimento;
	private String valore;
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
	/**
	 * @return the famigliaProcedimento
	 */
	public String getFamigliaProcedimento() {
		return this.famigliaProcedimento;
	}
	/**
	 * @param famigliaProcedimento the famigliaProcedimento to set
	 */
	public void setFamigliaProcedimento(final String famigliaProcedimento) {
		this.famigliaProcedimento = famigliaProcedimento;
	}
	/**
	 * @return the valore
	 */
	public String getValore() {
		return this.valore;
	}
	/**
	 * @param valore the valore to set
	 */
	public void setValore(final String valore) {
		this.valore = valore;
	}
	
}
