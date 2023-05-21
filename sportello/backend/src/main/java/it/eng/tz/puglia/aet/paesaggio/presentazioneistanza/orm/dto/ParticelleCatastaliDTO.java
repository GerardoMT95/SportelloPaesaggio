package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn;

/**
 * DTO for table presentazione_istanza.particelle_catastali
 */
@RequiredDependsOn.List({
	@RequiredDependsOn(field="particella", dependsOn="livello", dependsOnValue="PARTICELLE", message="Il campo 'particella'è obbligatorio con livello='PARTICELLE'")
})
public class ParticelleCatastaliDTO implements Serializable{

    private static final long serialVersionUID = 3702998506L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN comune_id
    private long comuneId;
    //COLUMN id
    private int id;
    //COLUMN livello
    @NotBlank(message="Il campo 'livello' non può essere vuoto")
    private String livello;
    //COLUMN sezione
    private String sezione;
    //COLUMN foglio
    @NotBlank(message="Il campo 'foglio' non può essere vuoto")
    @Pattern(regexp="(^[0][1-9]+)|([1-9]\\d*)", message="Il campo 'foglio' può contenere solamente numeri")
    private String foglio;
    //COLUMN particella
    //@Pattern(regexp="(^[0][1-9]+)|([1-9]\\d*)", message="Il campo 'particella' può contenere solamente numeri")
    //esistono particelle in UGENTO su foglio 44 con X8
    @NotBlank(message="Il campo 'particella' non può essere vuoto")
    private String particella;
    //COLUMN sub
    //@Pattern(regexp="(^[0][1-9]+)|([1-9]\\d*)", message="Il campo 'sezione' può contenere solamente numeri")
    private String sub;
    //COLUMN cod_cat
    private String codCat;
    //COLUMN oid
    private Long oid;
    //COLUMN note
    private String note;
    //COLUMN descr_sezione
    private String descrSezione;

    
    public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

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

    public int getId(){
        return this.id;
    }

    public void setId(int id){
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

	public String getDescrSezione() {
		return descrSezione;
	}

	public void setDescrSezione(String descrSezione) {
		this.descrSezione = descrSezione;
	}


}
