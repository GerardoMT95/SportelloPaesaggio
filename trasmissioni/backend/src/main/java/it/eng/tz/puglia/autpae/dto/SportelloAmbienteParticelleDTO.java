package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;

public class SportelloAmbienteParticelleDTO  implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long idComune;
	private String nomeComune;
	private String sezione;
	private String nomeSezione;
	private String foglio;
	private String particella;
	private String note;
	private Long oid;
	private String sub;
	public Long getIdComune() {
		return idComune;
	}
	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}
	public String getNomeComune() {
		return nomeComune;
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getNomeSezione() {
		return nomeSezione;
	}
	public void setNomeSezione(String nomeSezione) {
		this.nomeSezione = nomeSezione;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
	
		
}
