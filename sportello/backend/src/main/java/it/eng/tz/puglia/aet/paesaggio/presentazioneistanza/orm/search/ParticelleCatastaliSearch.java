package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.particelle_catastali
 */
public class ParticelleCatastaliSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 5147865120L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN comune_id
    private String comuneId;
    //COLUMN id
    private String id;
    //COLUMN livello
    private String livello;
    //COLUMN sezione
    private String sezione;
    //COLUMN foglio
    private String foglio;
    //COLUMN particella
    private String particella;
    //COLUMN sub
    private String sub;
    //COLUMN cod_cat
    private String codCat;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getComuneId(){
        return this.comuneId;
    }

    public void setComuneId(String comuneId){
        this.comuneId = comuneId;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getLivello(){
        return this.livello;
    }

    public void setLivello(String livello){
        this.livello = livello;
    }

    public String getSezione(){
        return this.sezione;
    }

    public void setSezione(String sezione){
        this.sezione = sezione;
    }

    public String getFoglio(){
        return this.foglio;
    }

    public void setFoglio(String foglio){
        this.foglio = foglio;
    }

    public String getParticella(){
        return this.particella;
    }

    public void setParticella(String particella){
        this.particella = particella;
    }

    public String getSub(){
        return this.sub;
    }

    public void setSub(String sub){
        this.sub = sub;
    }

    public String getCodCat(){
        return this.codCat;
    }

    public void setCodCat(String codCat){
        this.codCat = codCat;
    }


}
