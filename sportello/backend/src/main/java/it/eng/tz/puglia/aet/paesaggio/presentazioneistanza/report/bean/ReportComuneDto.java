package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportComuneDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = -6219342481038807321L;
	
	private String nome;
	private Long count;
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

	
}
