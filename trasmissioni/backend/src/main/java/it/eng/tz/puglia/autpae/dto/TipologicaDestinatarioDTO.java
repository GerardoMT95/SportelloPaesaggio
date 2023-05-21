package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;

public class TipologicaDestinatarioDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	@NotBlank(message="Campo denominazione richiesto")
	private String nome;
	@Email(message="Campo di tipo indirizzo email")
	@NotEmpty(message="Campo email richiesto")
	private String email;
	private boolean pec;
	private TipoDestinatario tipo;	  // TODO: non so se conviene prevedere già in questa classe o in qualche costruttore il controllo "null=default" (che credo sia TO)
		
	public TipologicaDestinatarioDTO() { }
	
	public TipologicaDestinatarioDTO(String email, String nome, TipoDestinatario tipo) {
		this.email = email;
		this.nome  = nome;
		this.tipo  = tipo;
	}

	public TipologicaDestinatarioDTO(String email, boolean pec, String nome, TipoDestinatario tipo) {
		this.pec   = pec;
		this.nome  = nome;
		this.tipo  = tipo;
		this.email = email;
	}
	
	public TipologicaDestinatarioDTO(TipologicaDTO tipologica, TipoDestinatario tipo) {
		this.nome  = tipologica.getLabel();
		this.email = tipologica.getCodice();		
		this.pec   = false;
		this.tipo  = tipo;
	}
	
//	public TipologicaDestinatarioDTO(TemplateDestinatarioDTO destinatario) {
//								this.pec   = (StringUtil.isNotEmpty(destinatario.getPec()) ? true : false);
//								this.nome  = destinatario.getDenominazione();
//								this.tipo  = destinatario.getTipo();
//		if (this.isPec()==true)	this.email = destinatario.getPec();
//		else					this.email = destinatario.getEmail();
//	}
	
	public TipologicaDestinatarioDTO(DestinatarioDTO destinatario) {
		this.nome  = destinatario.getNome();
		this.tipo  = destinatario.getTipo();
		this.email = destinatario.getEmail();
		//this.pec   = destinatario.getPec()!=null ? destinatario.getPec() : false;
		this.pec = destinatario.isPec();
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

	public TipoDestinatario getTipo() {
		return tipo;
	}

	public void setTipo(TipoDestinatario tipo) {
		this.tipo = tipo;
	}

	public boolean isPec() {
		return pec;
	}

	public void setPec(boolean pec) {
		this.pec = pec;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (pec ? 1231 : 1237);
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		TipologicaDestinatarioDTO other = (TipologicaDestinatarioDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pec != other.pec)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipologicaDestinatarioDTO [nome=" + nome + ", email=" + email + ", pec=" + pec + ", tipo=" + tipo + "]";
	}

}