package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperDomandaIstanzaDto extends JasperAbstractDomandaDto
{
	private List<JasperDichiarazioniDto> dichiarazioni;
	private List<JasperAltroTitolareDto> altriTitolari;
	private List<JasperDichiarazioniArticoliDto> dichiarazioniArticoli;
	private List<StringWrapper> documentazioneAmministrativa;
	private List<JasperClausoleEsoneriDto> clausoleEsoneri;
	private String idFascicolo;

	
	public List<JasperDichiarazioniDto> getDichiarazioni()
	{
		return dichiarazioni;
	}
	public void setDichiarazioni(List<JasperDichiarazioniDto> dichiarazioni)
	{
		this.dichiarazioni = dichiarazioni;
	}
	
	public List<JasperAltroTitolareDto> getAltriTitolari()
	{
		return altriTitolari;
	}
	public void setAltriTitolari(List<JasperAltroTitolareDto> altriTitolari)
	{
		this.altriTitolari = altriTitolari;
	}
	
	public List<JasperDichiarazioniArticoliDto> getDichiarazioniArticoli()
	{
		return dichiarazioniArticoli;
	}
	public void setDichiarazioniArticoli(List<JasperDichiarazioniArticoliDto> dichiarazioniArticoli)
	{
		this.dichiarazioniArticoli = dichiarazioniArticoli;
	}
	
	public List<StringWrapper> getDocumentazioneAmministrativa()
	{
		return documentazioneAmministrativa;
	}
	public void setDocumentazioneAmministrativa(List<StringWrapper> documentazioneAmministrativa)
	{
		this.documentazioneAmministrativa = documentazioneAmministrativa;
	}
	/**
	 * @return the clausoleEsoneri
	 */
	public List<JasperClausoleEsoneriDto> getClausoleEsoneri() {
		return clausoleEsoneri;
	}
	/**
	 * @param clausoleEsoneri the clausoleEsoneri to set
	 */
	public void setClausoleEsoneri(List<JasperClausoleEsoneriDto> clausoleEsoneri) {
		this.clausoleEsoneri = clausoleEsoneri;
	}
	
	public String getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	
	
}
