package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import it.eng.tz.puglia.util.json.*;

/**
 * DTO for table presentazione_istanza.ruolo_pratica
 */
public class RuoloPraticaDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    //COLUMN id
    private String id;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN delegato
    private Boolean delegato;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	/**
	 * @return the delegato
	 */
	public Boolean getDelegato() {
		return delegato;
	}
	/**
	 * @param delegato the delegato to set
	 */
	public void setDelegato(Boolean delegato) {
		this.delegato = delegato;
	}
    
    
}
