package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table presentazione_istanza.template_doc
 */
public class TemplateDocDTO implements Serializable{

    private static final long serialVersionUID = 3325460618L;
    //COLUMN id_organizzazione
    private int idOrganizzazione;
    //COLUMN nome
    private String nome;
    //COLUMN descrizione
    private String descrizione;
    
    //non in db ma solo per Dto verso FE
    private List<TemplateDocSezioniDTO> sezioni;
    

    public List<TemplateDocSezioniDTO> getSezioni() {
		return sezioni;
	}

	public void setSezioni(List<TemplateDocSezioniDTO> sezioni) {
		this.sezioni = sezioni;
	}

	public int getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(int idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }


}
