package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateDeserializer;
import it.eng.tz.puglia.util.json.DateSerializer;

/**
 * Request bean per avvio pagamento
 * @author Antonio La Gatta
 * @date 8 lug 2021
 */
public class AvviaPagamentoRequestBean implements Serializable {

	/**
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 */
	private static final long serialVersionUID = 2454863913194376997L;
	public AvviaPagamentoRequestBean() {
	}
	
	private double importoDiProgetto;
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)
	private java.util.Date dataScadenza;
	/**
	 * @return the importoDiProgetto
	 */
	public double getImportoDiProgetto() {
		return importoDiProgetto;
	}
	/**
	 * @param importoDiProgetto the importoDiProgetto to set
	 */
	public void setImportoDiProgetto(final double importoDiProgetto) {
		this.importoDiProgetto = importoDiProgetto;
	}
	/**
	 * @return the dataScadenza
	 */
	public java.util.Date getDataScadenza() {
		return dataScadenza;
	}
	/**
	 * @param dataScadenza the dataScadenza to set
	 */
	public void setDataScadenza(final java.util.Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

}
