package it.eng.tz.puglia.servizi_esterni.jasper.dto;

public class JasperCampionamentoListaDestinatariDTO {
	
	private String autorita;
	private String mailPec;
	
	
	public JasperCampionamentoListaDestinatariDTO() { }
	
	public JasperCampionamentoListaDestinatariDTO(String autorita, String mailPec) { 
		this.autorita = autorita;
		this.mailPec = mailPec;
	}
	
	
	public String getAutorita() {
		return autorita;
	}
	public void setAutorita(String autorita) {
		this.autorita = autorita;
	}
	public String getMailPec() {
		return mailPec;
	}
	public void setMailPec(String mailPec) {
		this.mailPec = mailPec;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autorita == null) ? 0 : autorita.hashCode());
		result = prime * result + ((mailPec == null) ? 0 : mailPec.hashCode());
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
		JasperCampionamentoListaDestinatariDTO other = (JasperCampionamentoListaDestinatariDTO) obj;
		if (autorita == null) {
			if (other.autorita != null)
				return false;
		} else if (!autorita.equals(other.autorita))
			return false;
		if (mailPec == null) {
			if (other.mailPec != null)
				return false;
		} else if (!mailPec.equals(other.mailPec))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "JasperCampionamentoPecMailDTO [autorita=" + autorita + ", mailPec=" + mailPec + "]";
	}
	
}