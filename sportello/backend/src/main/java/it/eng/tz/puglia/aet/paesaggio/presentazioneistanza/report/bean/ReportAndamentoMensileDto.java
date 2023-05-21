package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportAndamentoMensileDto implements Serializable{
	
	/**
	 * @author Antonio La Gatta
	 * @date 17 mag 2022
	 */
	private static final long serialVersionUID = 2382369844676036438L;
	
	
	private int mese;
	private String meseChar;
	private int anno;
	private Long daAssegnare;
	private Long daAvviare;
	private Long avviate;
	private Long concluse;
	private Long archiviate;
	/**
	 * @return the mese
	 */
	public int getMese() {
		return this.mese;
	}
	/**
	 * @param mese the mese to set
	 */
	public void setMese(final int mese) {
		this.mese = mese;
	}
	/**
	 * @return the meseChar
	 */
	public String getMeseChar() {
		return this.meseChar;
	}
	/**
	 * @param meseChar the meseChar to set
	 */
	public void setMeseChar(final String meseChar) {
		this.meseChar = meseChar;
	}
	/**
	 * @return the anno
	 */
	public int getAnno() {
		return this.anno;
	}
	/**
	 * @param anno the anno to set
	 */
	public void setAnno(final int anno) {
		this.anno = anno;
	}
	/**
	 * @return the daAssegnare
	 */
	public Long getDaAssegnare() {
		return this.daAssegnare;
	}
	/**
	 * @param daAssegnare the daAssegnare to set
	 */
	public void setDaAssegnare(final Long daAssegnare) {
		this.daAssegnare = daAssegnare;
	}
	/**
	 * @return the daAvviare
	 */
	public Long getDaAvviare() {
		return this.daAvviare;
	}
	/**
	 * @param daAvviare the daAvviare to set
	 */
	public void setDaAvviare(final Long daAvviare) {
		this.daAvviare = daAvviare;
	}
	/**
	 * @return the avviate
	 */
	public Long getAvviate() {
		return this.avviate;
	}
	/**
	 * @param avviate the avviate to set
	 */
	public void setAvviate(final Long avviate) {
		this.avviate = avviate;
	}
	/**
	 * @return the concluse
	 */
	public Long getConcluse() {
		return this.concluse;
	}
	/**
	 * @param concluse the concluse to set
	 */
	public void setConcluse(final Long concluse) {
		this.concluse = concluse;
	}
	/**
	 * @return the archiviate
	 */
	public Long getArchiviate() {
		return this.archiviate;
	}
	/**
	 * @param archiviate the archiviate to set
	 */
	public void setArchiviate(final Long archiviate) {
		this.archiviate = archiviate;
	}

}
