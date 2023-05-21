package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;

/**
 * DTO for table presentazione_istanza.template_destinatario
 */
public class TemplateDestinatarioDTO implements Serializable{

    private static final long serialVersionUID = 8975756649L;
    //COLUMN id
    private long id;
    //COLUMN id_organizzazione
    private Integer idOrganizzazione;
    //COLUMN codice_template
    private String codiceTemplate;
    //COLUMN email
    private String email;
    //COLUMN pec
    private String pec;
    //COLUMN denominazione
    private String denominazione;
    //COLUMN tipo
    private String tipo;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public Integer getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(Integer idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getCodiceTemplate(){
        return this.codiceTemplate;
    }

    public void setCodiceTemplate(String codiceTemplate){
        this.codiceTemplate = codiceTemplate;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPec(){
        return this.pec;
    }

    public void setPec(String pec){
        this.pec = pec;
    }

    public String getDenominazione(){
        return this.denominazione;
    }

    public void setDenominazione(String denominazione){
        this.denominazione = denominazione;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public DestinatarioDTO toDestinatarioDTO()
    {
    	DestinatarioDTO other = new DestinatarioDTO();
    	other.setEmail(this.pec != null ? this.pec : this.email);
    	other.setNome(this.denominazione);
    	other.setPec(this.pec != null);
    	other.setTipo(TipoDestinatario.valueOf(this.tipo));
    	return other;
    }


}
