package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;


/**
 * DTO for table presentazione_istanza.relazione_ente
 */
public class RelazioneEnteDTO implements Serializable{

    private static final long serialVersionUID = 3536865725L;
    //COLUMN id_relazione_ente
    private long idRelazioneEnte;
    //COLUMN id_pratica
    private UUID idPratica;
    //COLUMN numero_protocollo_ente
    private String numeroProtocolloEnte;
    //COLUMN nominativo_istruttore
    private String nominativoIstruttore;
    //COLUMN dettaglio_relazione
    private String dettaglioRelazione;
    //COLUMN nota_interna
    private String notaInterna;
    
    private Long idCorrispondenza;
    
    private DettaglioCorrispondenzaDTO dettaglioCorrispondenza;

    private List<AllegatiDTO> grigliaAllegati;
    
    public long getIdRelazioneEnte(){
        return this.idRelazioneEnte;
    }

    public void setIdRelazioneEnte(long idRelazioneEnte){
        this.idRelazioneEnte = idRelazioneEnte;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
        this.idPratica = idPratica;
    }

    public String getNumeroProtocolloEnte(){
        return this.numeroProtocolloEnte;
    }

    public void setNumeroProtocolloEnte(String numeroProtocolloEnte){
        this.numeroProtocolloEnte = numeroProtocolloEnte;
    }

    public String getNominativoIstruttore(){
        return this.nominativoIstruttore;
    }

    public void setNominativoIstruttore(String nominativoIstruttore){
        this.nominativoIstruttore = nominativoIstruttore;
    }

    public String getDettaglioRelazione(){
        return this.dettaglioRelazione;
    }

    public void setDettaglioRelazione(String dettaglioRelazione){
        this.dettaglioRelazione = dettaglioRelazione;
    }

    public String getNotaInterna(){
        return this.notaInterna;
    }

    public void setNotaInterna(String notaInterna){
        this.notaInterna = notaInterna;
    }

	public List<AllegatiDTO> getGrigliaAllegati() {
		return grigliaAllegati;
	}

	public void setGrigliaAllegati(List<AllegatiDTO> grigliaAllegati) {
		this.grigliaAllegati = grigliaAllegati;
	}

	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}

	public void setIdCorrispondenza(long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}

	public DettaglioCorrispondenzaDTO getDettaglioCorrispondenza() {
		return dettaglioCorrispondenza;
	}

	public void setDettaglioCorrispondenza(DettaglioCorrispondenzaDTO dettaglioCorrispondenza) {
		this.dettaglioCorrispondenza = dettaglioCorrispondenza;
	}




}
