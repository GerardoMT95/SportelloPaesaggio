package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table configurazione
 */
public class ConfigurazioneSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1L;
    //COLUMN id
    private String id;
    //COLUMN chiave
    private String chiave;
    //COLUMN valore
    private String valore;
    //COLUMN start_date
    private String startDate;
    //COLUMN end_date
    private String endDate;
    //COLUMN user_create
    private String userCreate;
    //COLUMN user_update
    private String userUpdate;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
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

    public String getStartDate(){
        return this.startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public void setEndDate(String endDate){
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
