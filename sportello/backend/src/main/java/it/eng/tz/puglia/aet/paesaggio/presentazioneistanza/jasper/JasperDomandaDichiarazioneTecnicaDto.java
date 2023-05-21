package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperDomandaDichiarazioneTecnicaDto extends JasperAbstractDomandaDto
{
	private List<JasperInquadramentoDto> inquadramento;
	private List<JasperDescrizioneDto> descrizione;
	private List<JasperEventualiProcedimentiDto> eventualiProcedimenti;
	private List<JasperDestinazioneUrbanisticaDto> destinazioneUrbanistica;
	private List<JasperProcedureEdilizieDto> procedureEdilizie;
	private List<JasperLegittimitaDto> legittimita;
	private List<JasperPareriAttiDto> pareriAtti;
	private List<JasperPptrDto> pptr;
	private List<JasperVincolisticaDto> vincolistica;
	private List<JasperEffettiMitigazioneDto> effettiMitigazione;
	private List<JasperAllegatoDto> elencoAllegati;
	private List<JasperAllegatoDto> documentazioneAmministrativa;
	private List<JasperAllegatoDto> documentazioneTecnica;
	private List<JasperPptrTabellaDto> pptrTabella; 
	private String idFascicolo;
	
	public List<JasperInquadramentoDto> getInquadramento() {
		return inquadramento;
	}
	public void setInquadramento(List<JasperInquadramentoDto> inquadramento) {
		this.inquadramento = inquadramento;
	}
	public List<JasperDescrizioneDto> getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(List<JasperDescrizioneDto> descrizione) {
		this.descrizione = descrizione;
	}
	public List<JasperEventualiProcedimentiDto> getEventualiProcedimenti() {
		return eventualiProcedimenti;
	}
	public void setEventualiProcedimenti(List<JasperEventualiProcedimentiDto> eventualiProcedimenti) {
		this.eventualiProcedimenti = eventualiProcedimenti;
	}
	public List<JasperDestinazioneUrbanisticaDto> getDestinazioneUrbanistica() {
		return destinazioneUrbanistica;
	}
	public void setDestinazioneUrbanistica(List<JasperDestinazioneUrbanisticaDto> destinazioneUrbanistica) {
		this.destinazioneUrbanistica = destinazioneUrbanistica;
	}
	public List<JasperProcedureEdilizieDto> getProcedureEdilizie() {
		return procedureEdilizie;
	}
	public void setProcedureEdilizie(List<JasperProcedureEdilizieDto> procedureEdilizie) {
		this.procedureEdilizie = procedureEdilizie;
	}
	public List<JasperLegittimitaDto> getLegittimita() {
		return legittimita;
	}
	public void setLegittimita(List<JasperLegittimitaDto> legittimita) {
		this.legittimita = legittimita;
	}
	public List<JasperPareriAttiDto> getPareriAtti() {
		return pareriAtti;
	}
	public void setPareriAtti(List<JasperPareriAttiDto> pareriAtti) {
		this.pareriAtti = pareriAtti;
	}
	public List<JasperPptrDto> getPptr() {
		return pptr;
	}
	public void setPptr(List<JasperPptrDto> pptr) {
		this.pptr = pptr;
	}
	public List<JasperVincolisticaDto> getVincolistica() {
		return vincolistica;
	}
	public void setVincolistica(List<JasperVincolisticaDto> vincolistica) {
		this.vincolistica = vincolistica;
	}
	public List<JasperEffettiMitigazioneDto> getEffettiMitigazione() {
		return effettiMitigazione;
	}
	public void setEffettiMitigazione(List<JasperEffettiMitigazioneDto> effettiMitigazione) {
		this.effettiMitigazione = effettiMitigazione;
	}
	public List<JasperAllegatoDto> getElencoAllegati() {
		return elencoAllegati;
	}
	public void setElencoAllegati(List<JasperAllegatoDto> elencoAllegati) {
		this.elencoAllegati = elencoAllegati;
	}
	public List<JasperAllegatoDto> getDocumentazioneAmministrativa() {
		return documentazioneAmministrativa;
	}
	public void setDocumentazioneAmministrativa(List<JasperAllegatoDto> documentazioneAmministrativa) {
		this.documentazioneAmministrativa = documentazioneAmministrativa;
	}
	public List<JasperAllegatoDto> getDocumentazioneTecnica() {
		return documentazioneTecnica;
	}
	public void setDocumentazioneTecnica(List<JasperAllegatoDto> documentazioneTecnica) {
		this.documentazioneTecnica = documentazioneTecnica;
	}
	public List<JasperPptrTabellaDto> getPptrTabella() {
		return pptrTabella;
	}
	public void setPptrTabella(List<JasperPptrTabellaDto> pptrTabella) {
		this.pptrTabella = pptrTabella;
	}
	public String getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	
	
	
}
