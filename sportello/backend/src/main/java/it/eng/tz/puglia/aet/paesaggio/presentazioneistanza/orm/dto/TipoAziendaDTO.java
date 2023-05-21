package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;

public class TipoAziendaDTO implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 */
	private static final long serialVersionUID = 6631062076630757368L;
	
	private String id;
	private String nome;
	private boolean privato;
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
	 * @return the nome
	 */
	public String getNome() {
		return this.nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(final String nome) {
		this.nome = nome;
	}
	/**
	 * @return the privato
	 */
	public boolean isPrivato() {
		return this.privato;
	}
	/**
	 * @param privato the privato to set
	 */
	public void setPrivato(final boolean privato) {
		this.privato = privato;
	}

}
