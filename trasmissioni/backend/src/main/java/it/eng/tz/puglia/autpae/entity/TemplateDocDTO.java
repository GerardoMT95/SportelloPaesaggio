package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.List;



public class TemplateDocDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private String descrizione;
	private String nome;
	
	private List<TemplateDocSezioneDTO> sezioni;
	
	public TemplateDocDTO() { }

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<TemplateDocSezioneDTO> getSezioni() {
		return sezioni;
	}

	public void setSezioni(List<TemplateDocSezioneDTO> sezioni) {
		this.sezioni = sezioni;
	}

	@Override
	public String toString() {
		return "TemplateDocDTO [descrizione=" + descrizione + ", nome=" + nome + ", sezioni=" + sezioni + "]";
	}
	
		

}
