package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportDocumentazioneProdottaDto implements Serializable{
	
	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 */
	private static final long serialVersionUID = 3316412978997272889L;
	
	private Long numeroAmministrativa;
	private Long numeroProgettuale;
	private Long numeroCds;
	private Long numeroIstruttoria;
	/**
	 * @return the numeroAmministrativa
	 */
	public Long getNumeroAmministrativa() {
		return this.numeroAmministrativa;
	}
	/**
	 * @param numeroAmministrativa the numeroAmministrativa to set
	 */
	public void setNumeroAmministrativa(final Long numeroAmministrativa) {
		this.numeroAmministrativa = numeroAmministrativa;
	}
	/**
	 * @return the numeroProgettuale
	 */
	public Long getNumeroProgettuale() {
		return this.numeroProgettuale;
	}
	/**
	 * @param numeroProgettuale the numeroProgettuale to set
	 */
	public void setNumeroProgettuale(final Long numeroProgettuale) {
		this.numeroProgettuale = numeroProgettuale;
	}
	/**
	 * @return the numeroCds
	 */
	public Long getNumeroCds() {
		return this.numeroCds;
	}
	/**
	 * @param numeroCds the numeroCds to set
	 */
	public void setNumeroCds(final Long numeroCds) {
		this.numeroCds = numeroCds;
	}
	/**
	 * @return the numeroIstruttoria
	 */
	public Long getNumeroIstruttoria() {
		return this.numeroIstruttoria;
	}
	/**
	 * @param numeroIstruttoria the numeroIstruttoria to set
	 */
	public void setNumeroIstruttoria(final Long numeroIstruttoria) {
		this.numeroIstruttoria = numeroIstruttoria;
	}

}
