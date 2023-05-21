package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportMediaDocumentazioneProdottaDto implements Serializable{
	
	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 */
	private static final long serialVersionUID = 3316412978997272889L;
	
	private String nomeFamiglia;
	private String nomeProcedimento;
	private Double avgNumeroAmministrativa;
	private Double avgNumeroProgettuale;
	private Double avgNumeroCds;
	private Double avgNumeroIstruttoria;
	/**
	 * @return the nomeFamiglia
	 */
	public String getNomeFamiglia() {
		return this.nomeFamiglia;
	}
	/**
	 * @param nomeFamiglia the nomeFamiglia to set
	 */
	public void setNomeFamiglia(final String nomeFamiglia) {
		this.nomeFamiglia = nomeFamiglia;
	}
	/**
	 * @return the nomeProcedimento
	 */
	public String getNomeProcedimento() {
		return this.nomeProcedimento;
	}
	/**
	 * @param nomeProcedimento the nomeProcedimento to set
	 */
	public void setNomeProcedimento(final String nomeProcedimento) {
		this.nomeProcedimento = nomeProcedimento;
	}
	/**
	 * @return the avgNumeroAmministrativa
	 */
	public Double getAvgNumeroAmministrativa() {
		return this.avgNumeroAmministrativa;
	}
	/**
	 * @param avgNumeroAmministrativa the avgNumeroAmministrativa to set
	 */
	public void setAvgNumeroAmministrativa(final Double avgNumeroAmministrativa) {
		this.avgNumeroAmministrativa = avgNumeroAmministrativa;
	}
	/**
	 * @return the avgNumeroProgettuale
	 */
	public Double getAvgNumeroProgettuale() {
		return this.avgNumeroProgettuale;
	}
	/**
	 * @param avgNumeroProgettuale the avgNumeroProgettuale to set
	 */
	public void setAvgNumeroProgettuale(final Double avgNumeroProgettuale) {
		this.avgNumeroProgettuale = avgNumeroProgettuale;
	}
	/**
	 * @return the avgNumeroCds
	 */
	public Double getAvgNumeroCds() {
		return this.avgNumeroCds;
	}
	/**
	 * @param avgNumeroCds the avgNumeroCds to set
	 */
	public void setAvgNumeroCds(final Double avgNumeroCds) {
		this.avgNumeroCds = avgNumeroCds;
	}
	/**
	 * @return the avgNumeroIstruttoria
	 */
	public Double getAvgNumeroIstruttoria() {
		return this.avgNumeroIstruttoria;
	}
	/**
	 * @param avgNumeroIstruttoria the avgNumeroIstruttoria to set
	 */
	public void setAvgNumeroIstruttoria(final Double avgNumeroIstruttoria) {
		this.avgNumeroIstruttoria = avgNumeroIstruttoria;
	}

}
