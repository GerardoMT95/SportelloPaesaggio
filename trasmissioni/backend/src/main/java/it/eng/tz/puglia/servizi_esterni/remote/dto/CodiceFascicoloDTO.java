package it.eng.tz.puglia.servizi_esterni.remote.dto;

import java.io.Serializable;

/**
 * DTO for table paesaggio_codice_fascicolo
 */
public class CodiceFascicoloDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private String codiceEnte;
	private int anno;
	private String prefisso;
	private int seriale;
	
	
	public CodiceFascicoloDTO() { }
	
	public CodiceFascicoloDTO(String codiceEnte, int anno, String prefisso, int seriale) {
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

	public int getSeriale() {
		return seriale;
	}

	public void setSeriale(int seriale) {
		this.seriale = seriale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
		result = prime * result + ((codiceEnte == null) ? 0 : codiceEnte.hashCode());
		result = prime * result + ((prefisso == null) ? 0 : prefisso.hashCode());
		result = prime * result + seriale;
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
		CodiceFascicoloDTO other = (CodiceFascicoloDTO) obj;
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
		if (seriale != other.seriale)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodiceFascicoloDTO [codiceEnte=" + codiceEnte + ", anno=" + anno + ", prefisso=" + prefisso	+ ", seriale=" + seriale + "]";
	}
	
}