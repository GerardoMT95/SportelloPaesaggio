package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;
import java.util.List;

public class ReportElencoIstanzeInterventoDto implements Serializable {

	
	private static final long serialVersionUID = 840196884209002558L;
	
	private String id;
	private String label;
	private String valore;
	private List<ReportElencoIstanzeInterventoDto> figli;
	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
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
	/**
	 * @return the figli
	 */
	public List<ReportElencoIstanzeInterventoDto> getFigli() {
		return this.figli;
	}
	/**
	 * @param figli the figli to set
	 */
	public void setFigli(final List<ReportElencoIstanzeInterventoDto> figli) {
		this.figli = figli;
	}
	
}
