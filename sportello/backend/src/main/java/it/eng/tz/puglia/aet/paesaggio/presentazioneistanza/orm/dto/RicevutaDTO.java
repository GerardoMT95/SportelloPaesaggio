package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoErrore;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoRicevuta;
import it.eng.tz.puglia.util.json.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * DTO for table presentazione_istanza.ricevuta
 */
public class RicevutaDTO implements Serializable{

    private static final long serialVersionUID = 6963892781L;
    //COLUMN id
    private long id;
    //COLUMN id_corrispondenza
    private Long idCorrispondenza;
    //COLUMN id_destinatario
    private Long idDestinatario;
    //COLUMN tipo_ricevura
    private TipoRicevuta tipoRicevura;
    //COLUMN errore
    private TipoErrore errore;
    //COLUMN descrizione_errore
    private String descrizioneErrore;
    //COLUMN id_cms_eml
    private String idCmsEml;
    //COLUMN id_cms_dati_cert
    private String idCmsDatiCert;
    //COLUMN id_cms_smime
    private String idCmsSmime;
    //COLUMN data
    private Timestamp data;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
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

    public TipoRicevuta getTipoRicevuta(){
        return this.tipoRicevura;
    }

    public void setTipoRicevuta(TipoRicevuta tipoRicevura){
        this.tipoRicevura = tipoRicevura;
    }

    public TipoErrore getErrore(){
        return this.errore;
    }

    public void setErrore(TipoErrore errore){
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

    public Timestamp getData(){
        return this.data;
    }

    public void setData(Timestamp data){
        this.data = data;
    }


}
