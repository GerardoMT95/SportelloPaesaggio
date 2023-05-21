package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table common.utente_attribute
 */
public class UtenteAttributeDTO implements Serializable{

    private static final long serialVersionUID = 2015438309L;
    //COLUMN applicazione_id
    private int applicazioneId;
    //COLUMN utente_id
    private long utenteId;
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN birth_date
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date birthDate;
    //COLUMN birth_place
    private String birthPlace;
    //COLUMN phone_number
    private String phoneNumber;
    //COLUMN mobile_number
    private String mobileNumber;
    //COLUMN last_richiesta_abilitazione
    private Long lastRichiestaAbilitazione;
    //COLUMN create_time
    private Timestamp createTime;

    public int getApplicazioneId(){
        return this.applicazioneId;
    }

    public void setApplicazioneId(int applicazioneId){
        this.applicazioneId = applicazioneId;
    }

    public long getUtenteId(){
        return this.utenteId;
    }

    public void setUtenteId(long utenteId){
        this.utenteId = utenteId;
    }

    public String getPec(){
        return this.pec;
    }

    public void setPec(String pec){
        this.pec = pec;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public Date getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    public String getBirthPlace(){
        return this.birthPlace;
    }

    public void setBirthPlace(String birthPlace){
        this.birthPlace = birthPlace;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber(){
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber){
        this.mobileNumber = mobileNumber;
    }

    public Long getLastRichiestaAbilitazione(){
        return this.lastRichiestaAbilitazione;
    }

    public void setLastRichiestaAbilitazione(Long lastRichiestaAbilitazione){
        this.lastRichiestaAbilitazione = lastRichiestaAbilitazione;
    }

    public Timestamp getCreateTime(){
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime){
        this.createTime = createTime;
    }


}
