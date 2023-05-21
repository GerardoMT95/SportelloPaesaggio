package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperDelegatoDto extends JasperInformazioniPersonaliDto
{
	private List<JasperIndirizzoCompletoDto> residenteIn;
	private String indirizzoEmail;
	private String pec;
	
	public JasperDelegatoDto()
	{
		super();
	}

	/**
	 * @return the residenteIn
	 */
	public List<JasperIndirizzoCompletoDto> getResidenteIn() {
		return residenteIn;
	}

	/**
	 * @param residenteIn the residenteIn to set
	 */
	public void setResidenteIn(List<JasperIndirizzoCompletoDto> residenteIn) {
		this.residenteIn = residenteIn;
	}

	/**
	 * @return the indirizzoEmail
	 */
	public String getIndirizzoEmail() {
		return indirizzoEmail;
	}

	/**
	 * @param indirizzoEmail the indirizzoEmail to set
	 */
	public void setIndirizzoEmail(String indirizzoEmail) {
		this.indirizzoEmail = indirizzoEmail;
	}

	/**
	 * @return the pec
	 */
	public String getPec() {
		return pec;
	}

	/**
	 * @param pec the pec to set
	 */
	public void setPec(String pec) {
		this.pec = pec;
	}
	
		
}
