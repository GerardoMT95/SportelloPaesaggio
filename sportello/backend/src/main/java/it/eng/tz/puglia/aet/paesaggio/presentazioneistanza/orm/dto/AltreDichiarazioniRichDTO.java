package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.DichiarazioniDto;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.altre_dichiarazioni_rich
 */
public class AltreDichiarazioniRichDTO implements Serializable{

    private static final long serialVersionUID = 9381247563L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN check_136
    private String check136;
    //COLUMN check_142
    private String check142;
    //COLUMN check_134
    private String check134;
    //COLUMN check_136a
    private String check136a;
    //COLUMN check_136b
    private String check136b;
    //COLUMN check_136c
    private String check136c;
    //COLUMN check_136d
    private String check136d;
    //COLUMN ente_rilascio
    private String enteRilascio;
    //COLUMN descr_aut_rilasciata
    private String descrAutRilasciata;
    //COLUMN data_rilascio
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataRilascio;
    //COLUMN is_caso_variante
    private Boolean isCasoVariante;
    //COLUMN check_142_parchi
    private String check142Parchi;

    public UUID getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(UUID praticaId){
        this.praticaId = praticaId;
    }

    public String getCheck136(){
        return this.check136;
    }

    
    public String getCheck136a() {
		return check136a;
	}

	public void setCheck136a(String check136a) {
		this.check136a = check136a;
	}

	public String getCheck136b() {
		return check136b;
	}

	public void setCheck136b(String check136b) {
		this.check136b = check136b;
	}

	public String getCheck136c() {
		return check136c;
	}

	public void setCheck136c(String check136c) {
		this.check136c = check136c;
	}

	public String getCheck136d() {
		return check136d;
	}

	public void setCheck136d(String check136d) {
		this.check136d = check136d;
	}

	public void setCheck136(String check136){
        this.check136 = check136;
    }

    public String getCheck142(){
        return this.check142;
    }

    public void setCheck142(String check142){
        this.check142 = check142;
    }

    public String getCheck134(){
        return this.check134;
    }

    public void setCheck134(String check134){
        this.check134 = check134;
    }

    
    public String getEnteRilascio(){
        return this.enteRilascio;
    }

    public void setEnteRilascio(String enteRilascio){
        this.enteRilascio = enteRilascio;
    }

    public String getDescrAutRilasciata(){
        return this.descrAutRilasciata;
    }

    public void setDescrAutRilasciata(String descrAutRilasciata){
        this.descrAutRilasciata = descrAutRilasciata;
    }

    public Date getDataRilascio(){
        return this.dataRilascio;
    }

    public void setDataRilascio(Date dataRilascio){
        this.dataRilascio = dataRilascio;
    }

    public Boolean getIsCasoVariante(){
        return this.isCasoVariante;
    }

    public void setIsCasoVariante(Boolean isCasoVariante){
        this.isCasoVariante = isCasoVariante;
    }

    public String getCheck142Parchi(){
        return this.check142Parchi;
    }

    public void setCheck142Parchi(String check142Parchi){
        this.check142Parchi = check142Parchi;
    }

    public void toDto(DichiarazioniDto out) 
    {
    	out.setInCasoDiVariante(this.getIsCasoVariante());
    	out.setDa(this.getEnteRilascio());
    	out.setInData(this.getDataRilascio());
    	out.setNumero(this.getDescrAutRilasciata());
    	List<String> art136 = new ArrayList<String>();
    	List<String> art142 = new ArrayList<String>();
    	if(check136 != null && check136.equals("S"))
    	{
    		art136.add("136");
    		if(check136a != null && check136a.equals("S"))
    			art136.add("A");
    		if(check136b != null && check136b.equals("S"))
    			art136.add("B");
    		if(check136c != null && check136c.equals("S"))
    			art136.add("C");
    		if(check136d != null && check136d.equals("S"))
    			art136.add("D");
    	}
    	if(check142 != null && check142.equals("S"))
    	{
    		art142.add("142");
    		if(check142Parchi != null && check142Parchi.equals("S"))
    			art142.add("PARCHI_E_RISERVE");
    	}
    	if(check134 != null && check134.equals("S"))
    		//out.setArt134(134);
    		out.setArt134(true);
		out.setArt136(art136);
		out.setArt142(art142);
    }
    
    public void toEntity(DichiarazioniDto out) 
    {
	if(out != null)
	{
	    this.setIsCasoVariante(out.getInCasoDiVariante());
	    this.setEnteRilascio(out.getDa());
	    this.setDataRilascio(out.getInData());
	    this.setDescrAutRilasciata(out.getNumero());
	    this.setCheck136(out.getArt136() != null && !out.getArt136().isEmpty() ? "S" : null);
	    this.setCheck142(out.getArt142() != null && !out.getArt142().isEmpty() ? "S" : null);
	    this.setCheck134(out.getArt134()!=null && out.getArt134()==true ? "S" : null);
	    
	    this.setCheck136a(out.getArt136() != null && out.getArt136().contains("A") ? "S" : null);
	    this.setCheck136b(out.getArt136() != null && out.getArt136().contains("B") ? "S" : null);
	    this.setCheck136c(out.getArt136() != null && out.getArt136().contains("C") ? "S" : null);
	    this.setCheck136d(out.getArt136() != null && out.getArt136().contains("D") ? "S" : null);
	    	
	    this.setCheck142Parchi(out.getArt142() != null && out.getArt142().contains("PARCHI_E_RISERVE") ? "S" : null);
	}
    	
    }

}
