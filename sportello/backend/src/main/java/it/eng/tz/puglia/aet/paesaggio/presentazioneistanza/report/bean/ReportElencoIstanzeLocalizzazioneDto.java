package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;
import java.util.List;

public class ReportElencoIstanzeLocalizzazioneDto implements Serializable{

	
	private static final long serialVersionUID = -15127006789657402L;
	
	private Boolean regione;
	private Boolean provincia;
	private Boolean comune;
	private List<String> comuni;
	private List<String> province;
	private List<String> comuniParticelleCatastali;
	/**
	 * @return the regione
	 */
	public Boolean getRegione() {
		return this.regione;
	}
	/**
	 * @param regione the regione to set
	 */
	public void setRegione(final Boolean regione) {
		this.regione = regione;
	}
	/**
	 * @return the provincia
	 */
	public Boolean getProvincia() {
		return this.provincia;
	}
	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(final Boolean provincia) {
		this.provincia = provincia;
	}
	/**
	 * @return the comune
	 */
	public Boolean getComune() {
		return this.comune;
	}
	/**
	 * @param comune the comune to set
	 */
	public void setComune(final Boolean comune) {
		this.comune = comune;
	}
	/**
	 * @return the comuni
	 */
	public List<String> getComuni() {
		return this.comuni;
	}
	/**
	 * @param comuni the comuni to set
	 */
	public void setComuni(final List<String> comuni) {
		this.comuni = comuni;
	}
	/**
	 * @return the province
	 */
	public List<String> getProvince() {
		return this.province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(final List<String> province) {
		this.province = province;
	}
	/**
	 * @return the comuniParticelleCatastali
	 */
	public List<String> getComuniParticelleCatastali() {
		return this.comuniParticelleCatastali;
	}
	/**
	 * @param comuniParticelleCatastali the comuniParticelleCatastali to set
	 */
	public void setComuniParticelleCatastali(final List<String> comuniParticelleCatastali) {
		this.comuniParticelleCatastali = comuniParticelleCatastali;
	}
	
}
