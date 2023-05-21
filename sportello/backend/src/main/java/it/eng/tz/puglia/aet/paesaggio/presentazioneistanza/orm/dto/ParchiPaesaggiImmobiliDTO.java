package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.parchi_paesaggi_immobili
 */
public class ParchiPaesaggiImmobiliDTO implements Serializable{

    private static final long serialVersionUID = 9432013147L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN comune_id
    private long comuneId;
    //COLUMN tipo_vincolo
    private String tipoVincolo;
    //COLUMN codice
    private String codice;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN selezionato
    private String selezionato;
    //COLUMN info
    private String info;
    //COLUMN data_inserimento
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataInserimento;
    //COLUMN tipo_variazione
    private char tipoVariazione=' ';
    //COLUMN notifica
    private Boolean notifica;
    
    

    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public long getComuneId(){
        return this.comuneId;
    }

    public void setComuneId(long comuneId){
        this.comuneId = comuneId;
    }

    public String getTipoVincolo(){
        return this.tipoVincolo;
    }

    public void setTipoVincolo(String tipoVincolo){
        this.tipoVincolo = tipoVincolo;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getSelezionato(){
        return this.selezionato;
    }

    public void setSelezionato(String selezionato){
        this.selezionato = selezionato;
    }

    public String getInfo(){
        return this.info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public Date getDataInserimento(){
        return this.dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento){
        this.dataInserimento = dataInserimento;
    }

	public char getTipoVariazione() {
		return tipoVariazione;
	}

	public void setTipoVariazione(char tipoVariazione) {
		this.tipoVariazione = tipoVariazione;
	}

	public Boolean getNotifica() {
		return notifica;
	}

	public void setNotifica(Boolean notifica) {
		this.notifica = notifica;
	}


}
