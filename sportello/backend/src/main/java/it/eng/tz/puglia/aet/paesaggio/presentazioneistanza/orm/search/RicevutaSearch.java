package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.ricevuta
 */
public class RicevutaSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1613941597L;
    //COLUMN id
    private Long id;
    //COLUMN id_corrispondenza
    private Long idCorrispondenza;
    //COLUMN id_destinatario
    private Long idDestinatario;
    //COLUMN tipo_ricevura
    private String tipoRicevura;
    //COLUMN errore
    private String errore;
    //COLUMN descrizione_errore
    private String descrizioneErrore;
    //COLUMN id_cms_eml
    private String idCmsEml;
    //COLUMN id_cms_dati_cert
    private String idCmsDatiCert;
    //COLUMN id_cms_smime
    private String idCmsSmime;
    //COLUMN data
    private String data;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getIdCorrispondenza(){
        return this.idCorrispondenza;
    }

    public void setIdCorrispondenza(Long idCorrispondenza){
        this.idCorrispondenza = idCorrispondenza;
    }

    public Long getIdDestinatario(){
        return this.idDestinatario;
    }

    public void setIdDestinatario(Long idDestinatario){
        this.idDestinatario = idDestinatario;
    }

    public String getTipoRicevura(){
        return this.tipoRicevura;
    }

    public void setTipoRicevura(String tipoRicevura){
        this.tipoRicevura = tipoRicevura;
    }

    public String getErrore(){
        return this.errore;
    }

    public void setErrore(String errore){
        this.errore = errore;
    }

    public String getDescrizioneErrore(){
        return this.descrizioneErrore;
    }

    public void setDescrizioneErrore(String descrizioneErrore){
        this.descrizioneErrore = descrizioneErrore;
    }

    public String getIdCmsEml(){
        return this.idCmsEml;
    }

    public void setIdCmsEml(String idCmsEml){
        this.idCmsEml = idCmsEml;
    }

    public String getIdCmsDatiCert(){
        return this.idCmsDatiCert;
    }

    public void setIdCmsDatiCert(String idCmsDatiCert){
        this.idCmsDatiCert = idCmsDatiCert;
    }

    public String getIdCmsSmime(){
        return this.idCmsSmime;
    }

    public void setIdCmsSmime(String idCmsSmime){
        this.idCmsSmime = idCmsSmime;
    }

    public String getData(){
        return this.data;
    }

    public void setData(String data){
        this.data = data;
    }


}
