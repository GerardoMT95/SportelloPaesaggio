package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.TabelleAssegnamentoFascicoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_WhereClauseGenerator;

/**
 * Search for .... TabelleAssegnamentoFascicoli
 */
public class TabelleAssegnamentoFascicoliSearch extends Trasmissioni_WhereClauseGenerator<TabelleAssegnamentoFascicoli> {

	private static final long serialVersionUID = 6693642738L;

	
	private boolean giaAssegnato;
	private String codice;
	private Boolean rup;			// indica se includere nella logica anche l'assegnazione del RUP (solo per ED e REG)
	
	
	public TabelleAssegnamentoFascicoliSearch() { }

	public TabelleAssegnamentoFascicoliSearch(boolean giaAssegnato, String codice, Boolean rup) {
		this.giaAssegnato = giaAssegnato;
		this.codice = codice;
		this.rup = rup;
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
	
	public Boolean getRup() {
		return rup;
	}
	public void setRup(Boolean rup) {
		this.rup = rup;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + (giaAssegnato ? 1231 : 1237);
		result = prime * result + ((rup == null) ? 0 : rup.hashCode());
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
		if (rup == null) {
			if (other.rup != null)
				return false;
		} else if (!rup.equals(other.rup))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TabelleAssegnamentoFascicoliSearch [giaAssegnato=" + giaAssegnato + ", codice=" + codice + ", rup="	+ rup + "]";
	}

	@Override
	protected void generateWhereCriteria() {
	}

}