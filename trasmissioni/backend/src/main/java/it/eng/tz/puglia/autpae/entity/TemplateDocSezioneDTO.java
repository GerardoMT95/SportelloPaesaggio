package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.autpae.enumeratori.TipoSezioneDoc;


public class TemplateDocSezioneDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String templateDocnome;
	private String valore;
	private Long idAllegato;
	private TipoSezioneDoc tipoSezione;
	//non in tabella
	private AllegatoDTO allegato;
	
	
	private List<PlaceholderDTO> placeholders;


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTemplateDocnome() {
		return templateDocnome;
	}
	public void setTemplateDocnome(String templateDocnome) {
		this.templateDocnome = templateDocnome;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public Long getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}
	public TipoSezioneDoc getTipoSezione() {
		return tipoSezione;
	}
	public void setTipoSezione(TipoSezioneDoc tipoSezione) {
		this.tipoSezione = tipoSezione;
	}
	public AllegatoDTO getAllegato() {
		return allegato;
	}
	public void setAllegato(AllegatoDTO allegato) {
		this.allegato = allegato;
	}
	public List<PlaceholderDTO> getPlaceholders() {
		return placeholders;
	}
	public void setPlaceholders(List<PlaceholderDTO> placeholders) {
		this.placeholders = placeholders;
	}

	@Override
	public String toString() {
		return "TemplateDocSezioneDTO [nome=" + nome + ", templateDocnome=" + templateDocnome + ", valore=" + valore
				+ ", idAllegato=" + idAllegato + ", tipoSezione=" + tipoSezione + ", allegato=" + allegato
				+ ", placeholders=" + placeholders + "]";
	}
	
}