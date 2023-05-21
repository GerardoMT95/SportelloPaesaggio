package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.tno_pptr_strumenti_urbanistici
 */
public class TnoPptrStrumentiUrbanisticiDTO implements Serializable{

    private static final long serialVersionUID = 2192628772L;
    //COLUMN id
    private long id;
    //COLUMN istat_6_prov
    private String istat6Prov;
    //COLUMN tipo_strumento
    private Integer tipoStrumento;
    //COLUMN stato
    private String stato;
    //COLUMN atto
    private String atto;
    //COLUMN data_atto
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataAtto;
    //COLUMN utente_modifica
    private String utenteModifica;
    //COLUMN data_modifica
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataModifica;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getIstat6Prov(){
        return this.istat6Prov;
    }

    public void setIstat6Prov(String istat6Prov){
        this.istat6Prov = istat6Prov;
    }

    public Integer getTipoStrumento(){
        return this.tipoStrumento;
    }

    public void setTipoStrumento(Integer tipoStrumento){
        this.tipoStrumento = tipoStrumento;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public String getAtto(){
        return this.atto;
    }

    public void setAtto(String atto){
        this.atto = atto;
    }

    public Date getDataAtto(){
        return this.dataAtto;
    }

    public void setDataAtto(Date dataAtto){
        this.dataAtto = dataAtto;
    }

    public String getUtenteModifica(){
        return this.utenteModifica;
    }

    public void setUtenteModifica(String utenteModifica){
        this.utenteModifica = utenteModifica;
    }

    public Date getDataModifica(){
        return this.dataModifica;
    }

    public void setDataModifica(Date dataModifica){
        this.dataModifica = dataModifica;
    }

	public static String generaVincoloArt38(TnoPptrStrumentiUrbanisticiDTO dto, String nomeComune) {
		if (dto==null) {
			return "Per questo comune non sono disponibili informazioni in merito all'art.38 delle NTA del PPTR.";
		}
		else {
			if (dto.getStato().equalsIgnoreCase("SI")) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				return "Il "+nomeComune+" ha precisato, di intesa con il Ministero e la Regione, ai sensi dell'art.38 delle NTA del PPTR, "
					 + "la delimitazione e la rappresentazione in scala idonea di dette aree in data " + formatter.format(dto.getDataAtto()) + " con " + dto.getAtto()+".";
			}
			else if (dto.getStato().equalsIgnoreCase("NO")) {
				return "Il "+nomeComune+" non ha precisato, di intesa con il Ministero e la Regione, ai sensi dell'art.38 delle NTA del PPTR, "
				 	 + "la delimitazione e la rappresentazione in scala idonea di dette aree.";
			}
			else 
				return "ERRORE! Stato vincolo non riconosciuto";
		}
	}

	public static String generaVincoloArt100(TnoPptrStrumentiUrbanisticiDTO dto) {
		if (dto==null) {
			return "Per questo comune non sono disponibili informazioni in merito all'adeguatezza e alla conformità al PPTR dello strumento urbanistico generale vigente.";
		}
		else {
			if (dto.getStato().equalsIgnoreCase("Valutato conforme")) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				return "Lo strumento urbanistico generale vigente di questo Comune è adeguato e conforme al PPTR "
					 + "ai sensi dell'art.100 in data " + formatter.format(dto.getDataAtto()) + " con " + dto.getAtto()+".";
			}
			else if (dto.getStato().equalsIgnoreCase("Non adeguato")) {
				return "Lo strumento urbanistico generale vigente di questo Comune è non adeguato e non conforme al PPTR.";
			}
			else 
				return "ERRORE! Stato vincolo non riconosciuto";
		}
	}
    
    

}
