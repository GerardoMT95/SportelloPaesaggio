package it.eng.tz.puglia.autpae.generated.orm.dto;

import java.io.Serializable;
import java.sql.*;

/**
 * DTO for table configurazione
 */
public class ConfigurazioneDTO implements Serializable{

    private static final long serialVersionUID = 1120499683L;
    //COLUMN id
    private long id;
    //COLUMN chiave
    private String chiave;
    //COLUMN valore
    private String valore;
    //COLUMN start_date
    private Timestamp startDate;
    //COLUMN end_date
    private Timestamp endDate;
    //COLUMN user_create
    private String userCreate;
    //COLUMN user_update
    private String userUpdate;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getChiave(){
        return this.chiave;
    }

    public void setChiave(String chiave){
        this.chiave = chiave;
    }

    public String getValore(){
        return this.valore;
    }

    public void setValore(String valore){
        this.valore = valore;
    }

    public Timestamp getStartDate(){
        return this.startDate;
    }

    public void setStartDate(Timestamp startDate){
        this.startDate = startDate;
    }

    public Timestamp getEndDate(){
        return this.endDate;
    }

    public void setEndDate(Timestamp endDate){
        this.endDate = endDate;
    }

    public String getUserCreate(){
        return this.userCreate;
    }

    public void setUserCreate(String userCreate){
        this.userCreate = userCreate;
    }

    public String getUserUpdate(){
        return this.userUpdate;
    }

    public void setUserUpdate(String userUpdate){
        this.userUpdate = userUpdate;
    }


}
