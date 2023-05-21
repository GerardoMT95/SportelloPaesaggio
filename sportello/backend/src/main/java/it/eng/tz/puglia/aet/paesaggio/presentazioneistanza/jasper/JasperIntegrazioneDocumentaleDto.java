package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperIntegrazioneDocumentaleDto
{
	private String codiceFascicolo;
	private List<JasperListAllegatoDto> elencoAllegati;
	private List<JasperAllegatoDto> documentazioneAmministrativa;
	private List<JasperAllegatoDto> documentazioneTecnica;
	
	public String getCodiceFascicolo()
	{
		return codiceFascicolo;
	}
	public void setCodiceFascicolo(String codiceFascicolo)
	{
		this.codiceFascicolo = codiceFascicolo;
	}
	
	public List<JasperAllegatoDto> getDocumentazioneAmministrativa()
	{
		return documentazioneAmministrativa;
	}
	public void setDocumentazioneAmministrativa(List<JasperAllegatoDto> documentazioneAmministrativa)
	{
		this.documentazioneAmministrativa = documentazioneAmministrativa;
	}
	
	public List<JasperAllegatoDto> getDocumentazioneTecnica()
	{
		return documentazioneTecnica;
	}
	public void setDocumentazioneTecnica(List<JasperAllegatoDto> documentazioneTecnica)
	{
		this.documentazioneTecnica = documentazioneTecnica;
	}
	/**
	 * @return the elencoAllegati
	 */
	public List<JasperListAllegatoDto> getElencoAllegati() {
		return elencoAllegati;
	}
	/**
	 * @param elencoAllegati the elencoAllegati to set
	 */
	public void setElencoAllegati(List<JasperListAllegatoDto> elencoAllegati) {
		this.elencoAllegati = elencoAllegati;
	}
	
}
