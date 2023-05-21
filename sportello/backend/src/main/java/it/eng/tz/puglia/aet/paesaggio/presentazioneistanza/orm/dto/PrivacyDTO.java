package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.*;

/**
 * DTO for table presentazione_istanza.privacy
 */
public class PrivacyDTO implements Serializable{

    private static final long serialVersionUID = 5811876610L;
    //COLUMN id
    private int id;
    //COLUMN testo
    private String testo;
    //COLUMN data_inizio
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataInizio;
    //COLUMN data_fine
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataFine;
    @JsonIgnore
    private String userInserted;
    @JsonIgnore
    private String userUpdated;

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTesto(){
        return this.testo;
    }

    public void setTesto(String testo){
        this.testo = testo;
    }

    public Date getDataInizio(){
        return this.dataInizio;
    }

    public void setDataInizio(Date dataInizio){
        this.dataInizio = dataInizio;
    }

    public Date getDataFine(){
        return this.dataFine;
    }

    public void setDataFine(Date dataFine){
        this.dataFine = dataFine;
    }

    public PrivacyDTO(Integer id) {
    	this.setId(id);
    }
    public PrivacyDTO() {}

	/**
	 * @return the userInserted
	 */
	public String getUserInserted() {
		return userInserted;
	}

	/**
	 * @param userInserted the userInserted to set
	 */
	public void setUserInserted(String userInserted) {
		this.userInserted = userInserted;
	}

	/**
	 * @return the userUpdated
	 */
	public String getUserUpdated() {
		return userUpdated;
	}

	/**
	 * @param userUpdated the userUpdated to set
	 */
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
    
    

}
