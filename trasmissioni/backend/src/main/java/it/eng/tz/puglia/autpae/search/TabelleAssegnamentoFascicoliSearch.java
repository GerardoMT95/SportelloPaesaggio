package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.TabelleAssegnamentoFascicoli;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for .... TabelleAssegnamentoFascicoli
 */
public class TabelleAssegnamentoFascicoliSearch extends WhereClauseGenerator<TabelleAssegnamentoFascicoli> {

	private static final long serialVersionUID = 6693642738L;

	
	private boolean giaAssegnato;
	private String codice;

	
	public TabelleAssegnamentoFascicoliSearch() { }

	public TabelleAssegnamentoFascicoliSearch(boolean giaAssegnato, String codice) {
		this.giaAssegnato = giaAssegnato;
		this.codice = codice;
	}


	public boolean isGiaAssegnato() {
		return giaAssegnato;
	}
	public void setGiaAssegnato(boolean giaAssegnato) {
		this.giaAssegnato = giaAssegnato;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + (giaAssegnato ? 1231 : 1237);
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
		TabelleAssegnamentoFascicoliSearch other = (TabelleAssegnamentoFascicoliSearch) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (giaAssegnato != other.giaAssegnato)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TabelleAssegnamentoFascicoliSearch [giaAssegnato=" + giaAssegnato + ", codice=" + codice + "]";
	}

	@Override
	protected void generateWhereCriteria() {
	}

}