package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

/**
 * DTO for table autpae.particelle_catastali
 */
public class ParticelleCatastaliDTO implements Serializable{

    private static final long serialVersionUID = 3702998506L;
    
    //COLUMN pratica_id
    private Long praticaId;
    //COLUMN comune_id
    private long comuneId;
    //COLUMN id
    private int id;
    //COLUMN livello
    private String livello;
    //COLUMN sezione
    private String sezione;
    //COLUMN foglio
    private String foglio;
    //COLUMN particella
    private String particella;
    //COLUMN sub
    private String sub;
    //COLUMN cod_cat
    private String codCat;
    //COLUMN oid
    private Long oid;
    //COLUMN note
    private String note;
    //COLUMN descr_sezione
    private String descrSezione;
    //COLUMN tipo_selezione_localizzazione di fascicolo
    private String tipoSelezione;
    //COLUMN stato di fascicolo
    private String stato;

    
    public String getDescrSezione() {
		return descrSezione;
	}

	public void setDescrSezione(String descrSezione) {
		this.descrSezione = descrSezione;
	}

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

	public Long getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(Long praticaId){
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

	public String getTipoSelezione() {
		return tipoSelezione;
	}

	public void setTipoSelezione(String tipoSelezione) {
		this.tipoSelezione = tipoSelezione;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return "ParticelleCatastaliDTO [praticaId=" + praticaId + ", comuneId=" + comuneId + ", id=" + id + ", livello="
				+ livello + ", sezione=" + sezione + ", foglio=" + foglio + ", particella=" + particella + ", sub="
				+ sub + ", codCat=" + codCat + ", oid=" + oid + ", note=" + note + ", descrSezione=" + descrSezione
				+ ", tipoSelezione=" + tipoSelezione + ", stato=" + stato + "]";
	}

}