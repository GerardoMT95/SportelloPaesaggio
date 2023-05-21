package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportIstruttoriDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = 180367069040112117L;
	

	private String istruttore;
	private Long countRDP;
	private Long countIstruttore;
	/**
	 * @return the istruttore
	 */
	public String getIstruttore() {
		return this.istruttore;
	}
	/**
	 * @param istruttore the istruttore to set
	 */
	public void setIstruttore(final String istruttore) {
		this.istruttore = istruttore;
	}
	/**
	 * @return the countRDP
	 */
	public Long getCountRDP() {
		return this.countRDP;
	}
	/**
	 * @param countRDP the countRDP to set
	 */
	public void setCountRDP(final Long countRDP) {
		this.countRDP = countRDP;
	}
	/**
	 * @return the countIstruttore
	 */
	public Long getCountIstruttore() {
		return this.countIstruttore;
	}
	/**
	 * @param countIstruttore the countIstruttore to set
	 */
	public void setCountIstruttore(final Long countIstruttore) {
		this.countIstruttore = countIstruttore;
	}
		
}
