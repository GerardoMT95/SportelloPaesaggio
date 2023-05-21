package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.leggittimita
 */
public class LeggittimitaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1903642481L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN leg_urb_tit_edilizio
    private String legUrbTitEdilizio;
    //COLUMN leg_urb_privo_specifica
    private String legUrbPrivoSpecifica;
    //COLUMN leg_paesag_immobile
    private String legPaesagImmobile;
    //COLUMN leg_pae_tipo_vincolo
    private String legPaeTipoVincolo;
    //COLUMN leg_pae_data_intervento
    private String legPaeDataIntervento;
    //COLUMN leg_pae_data_vincolo
    private String legPaeDataVincolo;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getLegUrbTitEdilizio(){
        return this.legUrbTitEdilizio;
    }

    public void setLegUrbTitEdilizio(String legUrbTitEdilizio){
        this.legUrbTitEdilizio = legUrbTitEdilizio;
    }

    public String getLegUrbPrivoSpecifica(){
        return this.legUrbPrivoSpecifica;
    }

    public void setLegUrbPrivoSpecifica(String legUrbPrivoSpecifica){
        this.legUrbPrivoSpecifica = legUrbPrivoSpecifica;
    }

    public String getLegPaesagImmobile(){
        return this.legPaesagImmobile;
    }

    public void setLegPaesagImmobile(String legPaesagImmobile){
        this.legPaesagImmobile = legPaesagImmobile;
    }

    public String getLegPaeTipoVincolo(){
        return this.legPaeTipoVincolo;
    }

    public void setLegPaeTipoVincolo(String legPaeTipoVincolo){
        this.legPaeTipoVincolo = legPaeTipoVincolo;
    }

    public String getLegPaeDataIntervento(){
        return this.legPaeDataIntervento;
    }

    public void setLegPaeDataIntervento(String legPaeDataIntervento){
        this.legPaeDataIntervento = legPaeDataIntervento;
    }

    public String getLegPaeDataVincolo(){
        return this.legPaeDataVincolo;
    }

    public void setLegPaeDataVincolo(String legPaeDataVincolo){
        this.legPaeDataVincolo = legPaeDataVincolo;
    }


}
