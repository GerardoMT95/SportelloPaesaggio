package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;


public class PlaceholderDocSezioneDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String placeholder_codice;
	private String template_doc_sezione_nome;
	private String	template_doc_nome;
	
	
	public String getPlaceholder_codice() {
		return placeholder_codice;
	}
	public void setPlaceholder_codice(String placeholder_codice) {
		this.placeholder_codice = placeholder_codice;
	}
	public String getTemplate_doc_sezione_nome() {
		return template_doc_sezione_nome;
	}
	public void setTemplate_doc_sezione_nome(String template_doc_sezione_nome) {
		this.template_doc_sezione_nome = template_doc_sezione_nome;
	}
	public String getTemplate_doc_nome() {
		return template_doc_nome;
	}
	public void setTemplate_doc_nome(String template_doc_nome) {
		this.template_doc_nome = template_doc_nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placeholder_codice == null) ? 0 : placeholder_codice.hashCode());
		result = prime * result + ((template_doc_nome == null) ? 0 : template_doc_nome.hashCode());
		result = prime * result + ((template_doc_sezione_nome == null) ? 0 : template_doc_sezione_nome.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaceholderDocSezioneDTO other = (PlaceholderDocSezioneDTO) obj;
		if (placeholder_codice == null) {
			if (other.placeholder_codice != null)
				return false;
		} else if (!placeholder_codice.equals(other.placeholder_codice))
			return false;
		if (template_doc_nome == null) {
			if (other.template_doc_nome != null)
				return false;
		} else if (!template_doc_nome.equals(other.template_doc_nome))
			return false;
		if (template_doc_sezione_nome == null) {
			if (other.template_doc_sezione_nome != null)
				return false;
		} else if (!template_doc_sezione_nome.equals(other.template_doc_sezione_nome))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PlaceholderDocSezioneDTO [placeholder_codice=" + placeholder_codice + ", template_doc_sezione_nome="
				+ template_doc_sezione_nome + ", template_doc_nome=" + template_doc_nome + "]";
	}
		
}