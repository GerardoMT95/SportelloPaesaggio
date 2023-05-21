/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

/**
 * entità estratta da una serie di join su CIVILIA_CS
 * @author Adriano Colaianni
 * @date 29 apr 2021
 */
public class PptrGruppoUfficio {
	private Long gruppoId;
	private String codiceGruppo;
	private String  descrizioneGruppo;
	private String codiceUfficio;
	private Long tUfficioId;
	private String ufficioDescrizione;
	public Long getGruppoId() {
		return gruppoId;
	}
	public void setGruppoId(Long gruppoId) {
		this.gruppoId = gruppoId;
	}
	public String getCodiceGruppo() {
		return codiceGruppo;
	}
	public void setCodiceGruppo(String codiceGruppo) {
		this.codiceGruppo = codiceGruppo;
	}
	public String getDescrizioneGruppo() {
		return descrizioneGruppo;
	}
	public void setDescrizioneGruppo(String descrizioneGruppo) {
		this.descrizioneGruppo = descrizioneGruppo;
	}
	public String getCodiceUfficio() {
		return codiceUfficio;
	}
	public void setCodiceUfficio(String codiceUfficio) {
		this.codiceUfficio = codiceUfficio;
	}
	public Long gettUfficioId() {
		return tUfficioId;
	}
	public void settUfficioId(Long tUfficioId) {
		this.tUfficioId = tUfficioId;
	}
	public String getUfficioDescrizione() {
		return ufficioDescrizione;
	}
	public void setUfficioDescrizione(String ufficioDescrizione) {
		this.ufficioDescrizione = ufficioDescrizione;
	}
	@Override
	public String toString() {
		return "PptrGruppoUfficio [gruppoId=" + gruppoId + ", codiceGruppo=" + codiceGruppo + ", descrizioneGruppo="
				+ descrizioneGruppo + ", codiceUfficio=" + codiceUfficio + ", tUfficioId=" + tUfficioId
				+ ", ufficioDescrizione=" + ufficioDescrizione + "]";
	}

	
}
