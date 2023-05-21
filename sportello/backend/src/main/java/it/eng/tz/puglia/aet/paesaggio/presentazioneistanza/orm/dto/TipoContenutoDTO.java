package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;

/**
 * DTO for table presentazione_istanza.tipo_contenuto
 */
public class TipoContenutoDTO implements Serializable
{

	private static final long serialVersionUID = 1L;
	// COLUMN id
	private int id;
	// COLUMN descrizione
	private String descrizione;
	// COLUMN descrizione_estesa
	private String descrizioneEstesa;
	// COLUMN sezione
	/**
	 * {@link SezioneAllegati}
	 */
	private String sezione;
	// COLUMN multiple
	private Boolean multiple;
	// COLUMN check_pagamento
	private String checkPagamento;

	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}

	public String getDescrizione()
	{
		return this.descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public String getDescrizioneEstesa()
	{
		return this.descrizioneEstesa;
	}
	public void setDescrizioneEstesa(String descrizioneEstesa)
	{
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public String getSezione()
	{
		return this.sezione;
	}
	public void setSezione(String sezione)
	{
		this.sezione = sezione;
	}

	public Boolean getMultiple()
	{
		return multiple;
	}
	public void setMultiple(Boolean multiple)
	{
		this.multiple = multiple;
	}
	public String getCheckPagamento() {
		return checkPagamento;
	}
	public void setCheckPagamento(String checkPagamento) {
		this.checkPagamento = checkPagamento;
	}
	
	
}
