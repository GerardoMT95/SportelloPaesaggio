package it.eng.tz.puglia.servizi_esterni.remote.search;

import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_CodiceFascicolo;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.allegato_fascicolo
 */
public class CodiceFascicoloSearch extends WhereClauseGenerator<Common_Paesaggio_CodiceFascicolo> {

	private static final long serialVersionUID = 6693642738L;
	
	private String  codiceEnte;
	private Integer anno;
	private String  prefisso;
	private Integer seriale;
	
	
	public CodiceFascicoloSearch() { }

	public CodiceFascicoloSearch(String codiceEnte, Integer anno, String prefisso, Integer seriale) {
		this.codiceEnte = codiceEnte;
		this.anno = anno;
		this.prefisso = prefisso;
		this.seriale = seriale;
	}

	
	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getPrefisso() {
		return prefisso;
	}
	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}
	public Integer getSeriale() {
		return seriale;
	}
	public void setSeriale(Integer seriale) {
		this.seriale = seriale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((anno == null) ? 0 : anno.hashCode());
		result = prime * result + ((codiceEnte == null) ? 0 : codiceEnte.hashCode());
		result = prime * result + ((prefisso == null) ? 0 : prefisso.hashCode());
		result = prime * result + ((seriale == null) ? 0 : seriale.hashCode());
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
		CodiceFascicoloSearch other = (CodiceFascicoloSearch) obj;
		if (anno == null) {
			if (other.anno != null)
				return false;
		} else if (!anno.equals(other.anno))
			return false;
		if (codiceEnte == null) {
			if (other.codiceEnte != null)
				return false;
		} else if (!codiceEnte.equals(other.codiceEnte))
			return false;
		if (prefisso == null) {
			if (other.prefisso != null)
				return false;
		} else if (!prefisso.equals(other.prefisso))
			return false;
		if (seriale == null) {
			if (other.seriale != null)
				return false;
		} else if (!seriale.equals(other.seriale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodiceFascicoloSearch [codiceEnte=" + codiceEnte + ", anno=" + anno + ", prefisso=" + prefisso + ", seriale=" + seriale + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(!StringUtil.isEmpty(codiceEnte))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_CodiceFascicolo.codice_ente.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName());
			parameters.put(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName(), codiceEnte);
			separator = " and ";
		}
		if(anno!=null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_CodiceFascicolo.anno.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_CodiceFascicolo.anno.columnName());
			parameters.put(Common_Paesaggio_CodiceFascicolo.anno.columnName(), anno);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(prefisso))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_CodiceFascicolo.prefisso.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_CodiceFascicolo.prefisso.columnName());
			parameters.put(Common_Paesaggio_CodiceFascicolo.prefisso.columnName(), prefisso);
			separator = " and ";
		}
		if(seriale != null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_CodiceFascicolo.seriale.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_CodiceFascicolo.seriale.columnName());
			parameters.put(Common_Paesaggio_CodiceFascicolo.seriale.columnName(), seriale);
			separator = " and ";
		}
	}

}