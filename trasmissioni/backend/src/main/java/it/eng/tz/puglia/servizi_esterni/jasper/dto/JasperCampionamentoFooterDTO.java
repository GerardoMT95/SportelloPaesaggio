package it.eng.tz.puglia.servizi_esterni.jasper.dto;

public class JasperCampionamentoFooterDTO {
	
	private String indirizzoRegione;
	private String pecRegione;
	private String telRegione;

	
	public JasperCampionamentoFooterDTO() { }
	
	
	public String getIndirizzoRegione() {
		return indirizzoRegione;
	}
	public void setIndirizzoRegione(String indirizzoRegione) {
		this.indirizzoRegione = indirizzoRegione;
	}
	public String getPecRegione() {
		return pecRegione;
	}
	public void setPecRegione(String pecRegione) {
		this.pecRegione = pecRegione;
	}
	public String getTelRegione() {
		return telRegione;
	}
	public void setTelRegione(String telRegione) {
		this.telRegione = telRegione;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indirizzoRegione == null) ? 0 : indirizzoRegione.hashCode());
		result = prime * result + ((pecRegione == null) ? 0 : pecRegione.hashCode());
		result = prime * result + ((telRegione == null) ? 0 : telRegione.hashCode());
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
		JasperCampionamentoFooterDTO other = (JasperCampionamentoFooterDTO) obj;
		if (indirizzoRegione == null) {
			if (other.indirizzoRegione != null)
				return false;
		} else if (!indirizzoRegione.equals(other.indirizzoRegione))
			return false;
		if (pecRegione == null) {
			if (other.pecRegione != null)
				return false;
		} else if (!pecRegione.equals(other.pecRegione))
			return false;
		if (telRegione == null) {
			if (other.telRegione != null)
				return false;
		} else if (!telRegione.equals(other.telRegione))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "JasperCampionamentoFooterDTO [indirizzoRegione=" + indirizzoRegione + ", pecRegione=" + pecRegione
				+ ", telRegione=" + telRegione + "]";
	}
	
}