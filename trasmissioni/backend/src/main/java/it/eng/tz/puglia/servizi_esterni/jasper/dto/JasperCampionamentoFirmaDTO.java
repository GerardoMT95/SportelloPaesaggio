package it.eng.tz.puglia.servizi_esterni.jasper.dto;

public class JasperCampionamentoFirmaDTO {
	
	private String titolo_dirigente;
	private String dirigente;
	
	
	public JasperCampionamentoFirmaDTO() { }
	
	
	public String getTitolo_dirigente() {
		return titolo_dirigente;
	}
	public void setTitolo_dirigente(String titolo_dirigente) {
		this.titolo_dirigente = titolo_dirigente;
	}
	public String getDirigente() {
		return dirigente;
	}
	public void setDirigente(String dirigente) {
		this.dirigente = dirigente;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dirigente == null) ? 0 : dirigente.hashCode());
		result = prime * result + ((titolo_dirigente == null) ? 0 : titolo_dirigente.hashCode());
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
		JasperCampionamentoFirmaDTO other = (JasperCampionamentoFirmaDTO) obj;
		if (dirigente == null) {
			if (other.dirigente != null)
				return false;
		} else if (!dirigente.equals(other.dirigente))
			return false;
		if (titolo_dirigente == null) {
			if (other.titolo_dirigente != null)
				return false;
		} else if (!titolo_dirigente.equals(other.titolo_dirigente))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "JasperCampionamentoFirmaDTO [titolo_dirigente=" + titolo_dirigente + ", dirigente=" + dirigente + "]";
	}
	
}