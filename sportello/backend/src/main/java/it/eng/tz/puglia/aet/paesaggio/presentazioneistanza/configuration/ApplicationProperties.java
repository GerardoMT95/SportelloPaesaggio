package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ApplicationProperties {

	@Value("${geo.layer.editing.description}") 
	private String layerEdDes;
	@Value("${geo.layer.editing.descrizioneEstesa}")
	private String layerEdDesEst;
	@Value("${geo.layer.editing.coloreArea}")
	private String layerEdCol;
	@Value("${geo.layer.publishing.description}")
	private String layerPubDes;
	@Value("${geo.layer.publishing.descrizioneEstesa}")
	private String layerPubDesEst;
	@Value("${geo.layer.publishing.coloreArea}")
	private String layerPubCol;
	@Value("${spring.application.name}")
	private String codiceApplicazione;
	@Value("${pm.codice.applicazione}")
	private String codiceApplicazioneProfile;
	@Value("${rubrica.riferimento.applicazione}")
	private String rubricaRiferimentoApplicazione;
	
	public String getRubricaRiferimentoApplicazione() {
		return rubricaRiferimentoApplicazione;
	}
	public void setRubricaRiferimentoApplicazione(String rubricaRiferimentoApplicazione) {
		this.rubricaRiferimentoApplicazione = rubricaRiferimentoApplicazione;
	}
	public String getLayerEditingDes() {
		return layerEdDes;
	}
	public String getLayerEditingDesEst() {
		return layerEdDesEst;
	}
	public String getLayerEditingCol() {
		return layerEdCol;
	}
	public String getLayerPublishedDes() {
		return layerPubDes;
	}
	public String getLayerPublishedDesEst() {
		return layerPubDesEst;
	}
	public String getLayerPublishedCol() {
		return layerPubCol;
	}
	public String getCodiceApplicazioneProfile() {
		return codiceApplicazioneProfile;
	}
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
}