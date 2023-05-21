package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class UtenteAttributeBean implements Serializable {

	/**
	 * @author Simone Verna
	 * @date 26 ago 2022
	 */
	private static final long serialVersionUID = 1032586683093409302L;
	private String pec;
	private String mail;
	private String phoneNumber;
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
