package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportProvinciaProcedimentoDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = 2063330444330714681L;
	private String nome;
	private Long count;
	private String procedimento;
	/**
	 * @return the nome
	 */
	public String getNome() {
		return this.nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(final String nome) {
		this.nome = nome;
	}
	/**
	 * @return the count
	 */
	public Long getCount() {
		return this.count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(final Long count) {
		this.count = count;
	}
	/**
	 * @return the procedimento
	 */
	public String getProcedimento() {
		return this.procedimento;
	}
	/**
	 * @param procedimento the procedimento to set
	 */
	public void setProcedimento(final String procedimento) {
		this.procedimento = procedimento;
	}
	
}
