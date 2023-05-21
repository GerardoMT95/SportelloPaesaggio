package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;

/**
 * bean di scambio con autpae per i destinatari della trasmissione
 * @author acolaianni
 *
 */
public class TipologicaDestinarioDto {

	private String nome;
	private String email;
	private boolean pec;
	private TipoDestinatario tipo;
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the pec
	 */
	public boolean isPec() {
		return pec;
	}
	/**
	 * @param pec the pec to set
	 */
	public void setPec(boolean pec) {
		this.pec = pec;
	}
	/**
	 * @return the tipo
	 */
	public TipoDestinatario getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoDestinatario tipo) {
		this.tipo = tipo;
	}
	
	
	
}
