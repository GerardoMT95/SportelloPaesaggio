package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table common.utente_attribute
 */
public class UtenteAttributeSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9798808984L;
    //COLUMN applicazione_id
    private String applicazioneId;
    //COLUMN utente_id
    private String utenteId;
    //COLUMN pec
    private String pec;
    //COLUMN mail
    private String mail;
    //COLUMN birth_date
    private String birthDate;
    //COLUMN birth_place
    private String birthPlace;
    //COLUMN phone_number
    private String phoneNumber;
    //COLUMN mobile_number
    private String mobileNumber;
    //COLUMN last_richiesta_abilitazione
    private String lastRichiestaAbilitazione;
    //COLUMN create_time
    private String createTime;

    public String getApplicazioneId(){
        return this.applicazioneId;
    }

    public void setApplicazioneId(String applicazioneId){
        this.applicazioneId = applicazioneId;
    }

    public String getUtenteId(){
        return this.utenteId;
    }

    public void setUtenteId(String utenteId){
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

    public String getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(String birthDate){
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

    public String getLastRichiestaAbilitazione(){
        return this.lastRichiestaAbilitazione;
    }

    public void setLastRichiestaAbilitazione(String lastRichiestaAbilitazione){
        this.lastRichiestaAbilitazione = lastRichiestaAbilitazione;
    }

    public String getCreateTime(){
        return this.createTime;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }


}
