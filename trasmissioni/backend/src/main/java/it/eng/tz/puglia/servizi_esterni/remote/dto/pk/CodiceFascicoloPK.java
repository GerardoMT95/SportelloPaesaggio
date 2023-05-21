package it.eng.tz.puglia.servizi_esterni.remote.dto.pk;

import java.io.Serializable;

public class CodiceFascicoloPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private String codiceEnte;
	private int anno;
	private String prefisso;
	
	
	public CodiceFascicoloPK() { }

	public CodiceFascicoloPK(String codiceEnte, int anno, String prefisso) {
		this.codiceEnte = codiceEnte;
		this.anno = anno;
		this.prefisso = prefisso;
	}
	
	
	public String getCodiceEnte() {
		return codiceEnte;
	}
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public String getPrefisso() {
		return prefisso;
	}
	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
		result = prime * result + ((codiceEnte == null) ? 0 : codiceEnte.hashCode());
		result = prime * result + ((prefisso == null) ? 0 : prefisso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodiceFascicoloPK other = (CodiceFascicoloPK) obj;
		if (anno != other.anno)
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
		return true;
	}

	@Override
	public String toString() {
		return "CodiceFascicoloPK [codiceEnte=" + codiceEnte + ", anno=" + anno + ", prefisso=" + prefisso + "]";
	}
	
}