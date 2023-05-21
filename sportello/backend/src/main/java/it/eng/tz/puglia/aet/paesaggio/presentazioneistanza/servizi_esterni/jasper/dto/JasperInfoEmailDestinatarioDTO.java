package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto;

import java.io.Serializable;

public class JasperInfoEmailDestinatarioDTO implements Serializable {

	private static final long serialVersionUID = 4982170498229217272L;

	private String email;
	private String nome;		// facoltativo
	private String tipo;		// facoltativo
	
	
	public JasperInfoEmailDestinatarioDTO() { }
	public JasperInfoEmailDestinatarioDTO(String email) {
		this.email = email;
	}
	public JasperInfoEmailDestinatarioDTO(String email, String nome) {
		this.email = email;
		this.nome = nome;
	}
	public JasperInfoEmailDestinatarioDTO(String tipo, String email, String nome) {
		this.tipo = tipo;
		this.email = email;
		this.nome = nome;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "JasperInfoEmailDestinatarioDTO [tipo=" + tipo + ", email=" + email + ", nome=" + nome + "]";
	}
	
}