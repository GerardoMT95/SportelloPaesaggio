package it.eng.tz.puglia.autpae.search;

import java.util.Date;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import it.eng.tz.puglia.autpae.dbMapping.ComuniCompetenza;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table autpae.comuni_competenza
 */
public class ComuniCompetenzaSearch extends WhereClauseGenerator<ComuniCompetenza>{

    private static final long serialVersionUID = 8427839322L;
    
    //COLUMN pratica_id
    private Long praticaId;
    //COLUMN ente_id
    private Long enteId;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN cod_cat
    private String codCat;
    //COLUMN cod_istat
    private String codIstat;
    //COLUMN data_inserimento
    private Date dataInserimento;
    //COLUMN creazione
    private Boolean creazione;
    //COLUMN effettivo
    private Boolean effettivo;
    
    private List<Long> enti;

    
    public ComuniCompetenzaSearch() { }
    
    public ComuniCompetenzaSearch(long idPratica, boolean creazione) {
    	this.praticaId = idPratica; 
    	this.creazione = creazione;
    }
    
    
	public Long getPraticaId() {
		return praticaId;
	}

	public void setPraticaId(Long praticaId) {
		this.praticaId = praticaId;
	}

	public Long getEnteId() {
		return enteId;
	}

	public void setEnteId(Long enteId) {
		this.enteId = enteId;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodCat() {
		return codCat;
	}

	public void setCodCat(String codCat) {
		this.codCat = codCat;
	}

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Boolean getCreazione() {
		return creazione;
	}

	public void setCreazione(Boolean creazione) {
		this.creazione = creazione;
	}

	public Boolean getEffettivo() {
		return effettivo;
	}

	public void setEffettivo(Boolean effettivo) {
		this.effettivo = effettivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codCat == null) ? 0 : codCat.hashCode());
		result = prime * result + ((codIstat == null) ? 0 : codIstat.hashCode());
		result = prime * result + ((creazione == null) ? 0 : creazione.hashCode());
		result = prime * result + ((dataInserimento == null) ? 0 : dataInserimento.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((effettivo == null) ? 0 : effettivo.hashCode());
		result = prime * result + ((enteId == null) ? 0 : enteId.hashCode());
		result = prime * result + ((praticaId == null) ? 0 : praticaId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComuniCompetenzaSearch other = (ComuniCompetenzaSearch) obj;
		if (codCat == null) {
			if (other.codCat != null)
				return false;
		} else if (!codCat.equals(other.codCat))
			return false;
		if (codIstat == null) {
			if (other.codIstat != null)
				return false;
		} else if (!codIstat.equals(other.codIstat))
			return false;
		if (creazione == null) {
			if (other.creazione != null)
				return false;
		} else if (!creazione.equals(other.creazione))
			return false;
		if (dataInserimento == null) {
			if (other.dataInserimento != null)
				return false;
		} else if (!dataInserimento.equals(other.dataInserimento))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (effettivo == null) {
			if (other.effettivo != null)
				return false;
		} else if (!effettivo.equals(other.effettivo))
			return false;
		if (enteId == null) {
			if (other.enteId != null)
				return false;
		} else if (!enteId.equals(other.enteId))
			return false;
		if (praticaId == null) {
			if (other.praticaId != null)
				return false;
		} else if (!praticaId.equals(other.praticaId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComuniCompetenzaSearch [praticaId=" + praticaId + ", enteId=" + enteId + ", descrizione=" + descrizione
				+ ", codCat=" + codCat + ", codIstat=" + codIstat + ", dataInserimento=" + dataInserimento
				+ ", creazione=" + creazione + ", effettivo=" + effettivo + "]";
	}

	@Override
	protected void generateWhereCriteria() { 
		String separatore = " where ";
		if(praticaId != null)
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.pratica_id.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.pratica_id.columnName());
			parameters.put(ComuniCompetenza.pratica_id.columnName(), praticaId);
			separatore = " and ";
		}
		if(enteId != null)
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.ente_id.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.ente_id.columnName());
			parameters.put(ComuniCompetenza.ente_id.columnName(), enteId);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(descrizione))
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.descrizione.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.descrizione.columnName());
			parameters.put(ComuniCompetenza.descrizione.columnName(), descrizione);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(codCat))
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.cod_cat.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.cod_cat.columnName());
			parameters.put(ComuniCompetenza.cod_cat.columnName(), codCat);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(codIstat))
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.cod_istat.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.cod_istat.columnName());
			parameters.put(ComuniCompetenza.cod_istat.columnName(), codIstat);
			separatore = " and ";
		}
		if(dataInserimento != null)
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.data_inserimento.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.data_inserimento.columnName());
			parameters.put(ComuniCompetenza.data_inserimento.columnName(), dataInserimento);
			separatore = " and ";
		}
		if(creazione != null)
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.creazione.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.creazione.columnName());
			parameters.put(ComuniCompetenza.creazione.columnName(), creazione);
			separatore = " and ";
		}
		if(effettivo != null)
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.effettivo.getCompleteName())
			   .append(" = :")
			   .append(ComuniCompetenza.effettivo.columnName());
			parameters.put(ComuniCompetenza.effettivo.columnName(), effettivo);
			separatore = " and ";
		}
		if(getEnti() != null && getEnti().size()>0)
		{
			sql.append(separatore)
			   .append(ComuniCompetenza.ente_id.getCompleteName())
			   .append(" IN ( :elencoId )");
			parameters.put("elencoId", getEnti());
			separatore = " and ";
		}
	}

	public List<Long> getEnti() {
		return enti;
	}

	public void setEnti(List<Long> enti) {
		this.enti = enti;
	}

}