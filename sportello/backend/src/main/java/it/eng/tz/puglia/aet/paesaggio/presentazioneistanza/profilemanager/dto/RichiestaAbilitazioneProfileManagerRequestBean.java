package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;
import java.util.List;

public class RichiestaAbilitazioneProfileManagerRequestBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3230116508377819104L;

	/**
	 * @author Antonio La Gatta
	 * @date 3 lug 2019
	 */
	

	public RichiestaAbilitazioneProfileManagerRequestBean() {
	}

	private List<String> codiceGruppo;
	private String codiceApplicazione;

	/**
	 * @return the codiceGruppo
	 */
	public List<String> getCodiceGruppo() {
		return codiceGruppo;
	}
	/**
	 * @param codiceGruppo the codiceGruppo to set
	 */
	public void setCodiceGruppo(List<String> codiceGruppo) {
		this.codiceGruppo = codiceGruppo;
	}
	/**
	 * @return the codiceApplicazione
	 */
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	/**
	 * @param codiceApplicazione the codiceApplicazione to set
	 */
	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}
	@Override
	public String toString() {
		return "RichiestaAbilitazioneProfileManagerRequestBean [codiceGruppo=" + codiceGruppo + ", codiceApplicazione="
				+ codiceApplicazione + "]";
	}


}
