package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.util.json.*;

/**
 * DTO for table presentazione_istanza.tipo_procedimento
 */
public class TipoProcedimentoDTO implements Serializable{

    private static final long serialVersionUID = 2751571733L;
    //COLUMN id
    private int id;
    //COLUMN codice
    private String codice;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN invia_email
    @JsonIgnore
    private boolean inviaEmail;
    //COLUMN sanatoria
    private boolean sanatoria;
    //COLUMN abilitato_presentazione
    private Boolean abilitatoPresentazione;
    //COLUMN accertamento
    private Boolean accertamento;
    //COLUMN descr_stampa
    @JsonIgnore
    private String descrStampa;
    //COLUMN descr_stampa_titolo
    @JsonIgnore
    private String descrStampaTitolo;
    //COLUMN descr_stampa_sottotitolo
    @JsonIgnore
    private String descrStampaSottoTitolo;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public boolean getInviaEmail(){
        return this.inviaEmail;
    }

    public void setInviaEmail(boolean inviaEmail){
        this.inviaEmail = inviaEmail;
    }

    public boolean getSanatoria(){
        return this.sanatoria;
    }

    public void setSanatoria(boolean sanatoria){
        this.sanatoria = sanatoria;
    }

    public Boolean getAbilitatoPresentazione(){
        return this.abilitatoPresentazione;
    }

    public void setAbilitatoPresentazione(Boolean abilitatoPresentazione){
        this.abilitatoPresentazione = abilitatoPresentazione;
    }

	/**
	 * @return the accertamento
	 */
	public Boolean getAccertamento() {
		return accertamento;
	}

	/**
	 * @param accertamento the accertamento to set
	 */
	public void setAccertamento(Boolean accertamento) {
		this.accertamento = accertamento;
	}

	/**
	 * @return the descrStampa
	 */
	public String getDescrStampa() {
		return descrStampa;
	}

	/**
	 * @param descrStampa the descrStampa to set
	 */
	public void setDescrStampa(String descrStampa) {
		this.descrStampa = descrStampa;
	}

	/**
	 * @return the descrStampaTitolo
	 */
	public String getDescrStampaTitolo() {
		return descrStampaTitolo;
	}

	/**
	 * @param descrStampaTitolo the descrStampaTitolo to set
	 */
	public void setDescrStampaTitolo(String descrStampaTitolo) {
		this.descrStampaTitolo = descrStampaTitolo;
	}

	/**
	 * @return the descrStampaSottoTitolo
	 */
	public String getDescrStampaSottoTitolo() {
		return descrStampaSottoTitolo;
	}

	/**
	 * @param descrStampaSottoTitolo the descrStampaSottoTitolo to set
	 */
	public void setDescrStampaSottoTitolo(String descrStampaSottoTitolo) {
		this.descrStampaSottoTitolo = descrStampaSottoTitolo;
	}


}
