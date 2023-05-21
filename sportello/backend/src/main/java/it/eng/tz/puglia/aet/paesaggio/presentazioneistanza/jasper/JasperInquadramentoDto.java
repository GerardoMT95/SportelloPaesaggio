package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

public class JasperInquadramentoDto {
	
	private Integer destinazioneUso;
    private String  destinazioneUsoSpecifica;
	private Integer contestoPaesaggInterv;
    private String  contestoPaesaggIntervSpecifica;
	private Integer morfologiaConPaesag;
    private String  morfologiaConPaesagSpecifica;
    
	public JasperInquadramentoDto() {
		super();
	}
	public Integer getDestinazioneUso() {
		return destinazioneUso;
	}
	public void setDestinazioneUso(Integer destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}
	public String getDestinazioneUsoSpecifica() {
		return destinazioneUsoSpecifica;
	}
	public void setDestinazioneUsoSpecifica(String destinazioneUsoSpecifica) {
		this.destinazioneUsoSpecifica = destinazioneUsoSpecifica;
	}
	public Integer getContestoPaesaggInterv() {
		return contestoPaesaggInterv;
	}
	public void setContestoPaesaggInterv(Integer contestoPaesaggInterv) {
		this.contestoPaesaggInterv = contestoPaesaggInterv;
	}
	public String getContestoPaesaggIntervSpecifica() {
		return contestoPaesaggIntervSpecifica;
	}
	public void setContestoPaesaggIntervSpecifica(String contestoPaesaggIntervSpecifica) {
		this.contestoPaesaggIntervSpecifica = contestoPaesaggIntervSpecifica;
	}
	public Integer getMorfologiaConPaesag() {
		return morfologiaConPaesag;
	}
	public void setMorfologiaConPaesag(Integer morfologiaConPaesag) {
		this.morfologiaConPaesag = morfologiaConPaesag;
	}
	public String getMorfologiaConPaesagSpecifica() {
		return morfologiaConPaesagSpecifica;
	}
	public void setMorfologiaConPaesagSpecifica(String morfologiaConPaesagSpecifica) {
		this.morfologiaConPaesagSpecifica = morfologiaConPaesagSpecifica;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contestoPaesaggInterv == null) ? 0 : contestoPaesaggInterv.hashCode());
		result = prime * result
				+ ((contestoPaesaggIntervSpecifica == null) ? 0 : contestoPaesaggIntervSpecifica.hashCode());
		result = prime * result + ((destinazioneUso == null) ? 0 : destinazioneUso.hashCode());
		result = prime * result + ((destinazioneUsoSpecifica == null) ? 0 : destinazioneUsoSpecifica.hashCode());
		result = prime * result + ((morfologiaConPaesag == null) ? 0 : morfologiaConPaesag.hashCode());
		result = prime * result
				+ ((morfologiaConPaesagSpecifica == null) ? 0 : morfologiaConPaesagSpecifica.hashCode());
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
		JasperInquadramentoDto other = (JasperInquadramentoDto) obj;
		if (contestoPaesaggInterv == null) {
			if (other.contestoPaesaggInterv != null)
				return false;
		} else if (!contestoPaesaggInterv.equals(other.contestoPaesaggInterv))
			return false;
		if (contestoPaesaggIntervSpecifica == null) {
			if (other.contestoPaesaggIntervSpecifica != null)
				return false;
		} else if (!contestoPaesaggIntervSpecifica.equals(other.contestoPaesaggIntervSpecifica))
			return false;
		if (destinazioneUso == null) {
			if (other.destinazioneUso != null)
				return false;
		} else if (!destinazioneUso.equals(other.destinazioneUso))
			return false;
		if (destinazioneUsoSpecifica == null) {
			if (other.destinazioneUsoSpecifica != null)
				return false;
		} else if (!destinazioneUsoSpecifica.equals(other.destinazioneUsoSpecifica))
			return false;
		if (morfologiaConPaesag == null) {
			if (other.morfologiaConPaesag != null)
				return false;
		} else if (!morfologiaConPaesag.equals(other.morfologiaConPaesag))
			return false;
		if (morfologiaConPaesagSpecifica == null) {
			if (other.morfologiaConPaesagSpecifica != null)
				return false;
		} else if (!morfologiaConPaesagSpecifica.equals(other.morfologiaConPaesagSpecifica))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JasperInquadramentoDto [destinazioneUso=" + destinazioneUso + ", destinazioneUsoSpecifica="
				+ destinazioneUsoSpecifica + ", contestoPaesaggInterv=" + contestoPaesaggInterv
				+ ", contestoPaesaggIntervSpecifica=" + contestoPaesaggIntervSpecifica + ", morfologiaConPaesag="
				+ morfologiaConPaesag + ", morfologiaConPaesagSpecifica=" + morfologiaConPaesagSpecifica + "]";
	}

}
