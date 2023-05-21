package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * DTO for table presentazione_istanza.template_doc_sezioni
 */
public class TemplateDocSezioniDTO implements Serializable{

    private static final long serialVersionUID = 3946383036L;
    //COLUMN id_organizzazione
    private int idOrganizzazione;
    //COLUMN nome
    private String nome;
    //COLUMN template_doc_nome
    private String templateDocNome;
    //COLUMN valore
    private String valore;
    //COLUMN id_allegato
    private UUID idAllegato;
    //COLUMN placeholders
    private String placeholders;
    //COLUMN tipo_sezione
    private String tipoSezione;
    
    //non in db... contiene le info dei placeholders...
    private List<PlainStringValueLabel> placeholdersInfo;
    
    //non in db... pieno solo per sezioni di tipo IMAGE
    private AllegatiDTO allegato;
    

    public AllegatiDTO getAllegato() {
		return allegato;
	}

	public void setAllegato(AllegatiDTO allegato) {
		this.allegato = allegato;
	}

	public List<PlainStringValueLabel> getPlaceholdersInfo() {
		return placeholdersInfo;
	}

	public void setPlaceholdersInfo(List<PlainStringValueLabel> placeholdersInfo) {
		this.placeholdersInfo = placeholdersInfo;
	}

	public int getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(int idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTemplateDocNome(){
        return this.templateDocNome;
    }

    public void setTemplateDocNome(String templateDocNome){
        this.templateDocNome = templateDocNome;
    }

    public String getValore(){
        return this.valore;
    }

    public void setValore(String valore){
        this.valore = valore;
    }

    public UUID getIdAllegato(){
        return this.idAllegato;
    }

    public void setIdAllegato(UUID idAllegato){
        this.idAllegato = idAllegato;
    }

    public String getPlaceholders(){
        return this.placeholders;
    }

    //ogni volta che mi settano la lista dei placeholders mi carico anche le info...
    public void setPlaceholders(String placeholders){
        this.placeholders = placeholders;
        if(StringUtil.isNotEmpty(placeholders)) {
        	this.setPlaceholdersInfo(new ArrayList<>());
            String[] phs = this.getPlaceholders().split(",");
    		Arrays.asList(phs).forEach(el -> {
    			this.getPlaceholdersInfo().add(new PlainStringValueLabel(el, PlaceHolder.valueOf(el).getLegenda()));
    		});	
        }
    }

    public String getTipoSezione(){
        return this.tipoSezione;
    }

    public void setTipoSezione(String tipoSezione){
        this.tipoSezione = tipoSezione;
    }


}
