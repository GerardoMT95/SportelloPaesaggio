package it.eng.tz.puglia.autpae.search;

import org.hsqldb.lib.StringUtil;

import it.eng.tz.puglia.autpae.dbMapping.ParticelleCatastali;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table autpae.particelle_catastali
 */
public class ParticelleCatastaliSearch extends WhereClauseGenerator<ParticelleCatastali>{

    private static final long serialVersionUID = 5147865120L;
    
    //COLUMN pratica_id
    private Long praticaId;
    //COLUMN comune_id
    private Long comuneId;
    //COLUMN id
    private Integer id;
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

    
	public Long getPraticaId() {
		return praticaId;
	}

	public void setPraticaId(Long praticaId) {
		this.praticaId = praticaId;
	}

	public Long getComuneId() {
		return comuneId;
	}

	public void setComuneId(Long comuneId) {
		this.comuneId = comuneId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLivello() {
		return livello;
	}

	public void setLivello(String livello) {
		this.livello = livello;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getCodCat() {
		return codCat;
	}

	public void setCodCat(String codCat) {
		this.codCat = codCat;
	}

	@Override
	public String toString() {
		return "ParticelleCatastaliSearch [praticaId=" + praticaId + ", comuneId=" + comuneId + ", id=" + id
				+ ", livello=" + livello + ", sezione=" + sezione + ", foglio=" + foglio + ", particella=" + particella
				+ ", sub=" + sub + ", codCat=" + codCat + "]";
	}

	@Override
	protected void generateWhereCriteria() { 
		String separatore = " where ";
		if(praticaId != null)
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.pratica_id.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.pratica_id.columnName());
			parameters.put(ParticelleCatastali.pratica_id.columnName(), praticaId);
			separatore = " and ";
		}
		if(comuneId != null)
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.comune_id.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.comune_id.columnName());
			parameters.put(ParticelleCatastali.comune_id.columnName(), comuneId);
			separatore = " and ";
		}
		if(id != null)
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.id.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.id.columnName());
			parameters.put(ParticelleCatastali.id.columnName(), id);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(livello))
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.livello.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.livello.columnName());
			parameters.put(ParticelleCatastali.livello.columnName(), livello);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(sezione))
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.sezione.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.sezione.columnName());
			parameters.put(ParticelleCatastali.sezione.columnName(), sezione);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(foglio))
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.foglio.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.foglio.columnName());
			parameters.put(ParticelleCatastali.foglio.columnName(), foglio);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(particella))
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.particella.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.particella.columnName());
			parameters.put(ParticelleCatastali.particella.columnName(), particella);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(sub))
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.sub.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.sub.columnName());
			parameters.put(ParticelleCatastali.sub.columnName(), sub);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(codCat))
		{
			sql.append(separatore)
			   .append(ParticelleCatastali.cod_cat.getCompleteName())
			   .append(" = :")
			   .append(ParticelleCatastali.cod_cat.columnName());
			parameters.put(ParticelleCatastali.cod_cat.columnName(), codCat);
			separatore = " and ";
		}
	}

}