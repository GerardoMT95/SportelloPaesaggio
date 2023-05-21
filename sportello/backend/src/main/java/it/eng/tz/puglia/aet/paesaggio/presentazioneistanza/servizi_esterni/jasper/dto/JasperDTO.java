package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RichiedenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperInterventoInfoDto;


public class JasperDTO {
	
	private UUID idFascicolo;
	private String protocollo;
	private Boolean sanatoria;
	private String codicePratica;
	private String ufficio;
	private String provvedimentoNumero;
	private Date   provvedimentoDataAutorizzazione;
	private String provvedimentoEsito;
	private String provvedimentoRup;
	private List<RichiedenteDTO> richiedenteInfo;
	private List<JasperInterventoInfoDto> interventoInfo;
	private List<TipologicaDTO> listaDestinatari;
	private List<JasperLocalizzazioneInterventoDTO> listaLocalizzazioni;
	private List<JasperAllegatoDTO> listaAllegatiLocalizzazione;
	private List<JasperAllegatoDTO> listaAllegatiProvvedimento;
	private List<JasperAllegatoDTO> listaAllegatiAmministrativi;
	private List<JasperAllegatoDTO> listaAllegatiTecnici;
	private Map<String,Object> sezioniDinamiche;


	public JasperDTO() {
		this.listaDestinatari = new ArrayList<>();
		this.richiedenteInfo = new ArrayList<>();
		this.interventoInfo = new ArrayList<>();
		this.listaLocalizzazioni = new ArrayList<>();
		this.listaAllegatiLocalizzazione = new ArrayList<>();
		this.listaAllegatiProvvedimento = new ArrayList<>();
		this.listaAllegatiAmministrativi = new ArrayList<>();
		this.listaAllegatiTecnici = new ArrayList<>();
	}


	public UUID getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(UUID idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	public Boolean getSanatoria() {
		return sanatoria;
	}
	public void setSanatoria(Boolean sanatoria) {
		this.sanatoria = sanatoria;
	}
	public String getCodicePratica() {
		return codicePratica;
	}
	public void setCodicePratica(String codicePratica) {
		this.codicePratica = codicePratica;
	}
	public String getUfficio() {
		return ufficio;
	}
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	public String getProvvedimentoNumero() {
		return provvedimentoNumero;
	}
	public void setProvvedimentoNumero(String provvedimentoNumero) {
		this.provvedimentoNumero = provvedimentoNumero;
	}
	public Date getProvvedimentoDataAutorizzazione() {
		return provvedimentoDataAutorizzazione;
	}
	public void setProvvedimentoDataAutorizzazione(Date provvedimentoDataAutorizzazione) {
		this.provvedimentoDataAutorizzazione = provvedimentoDataAutorizzazione;
	}
	public String getProvvedimentoEsito() {
		return provvedimentoEsito;
	}
	public void setProvvedimentoEsito(String provvedimentoEsito) {
		this.provvedimentoEsito = provvedimentoEsito;
	}
	public String getProvvedimentoRup() {
		return provvedimentoRup;
	}
	public void setProvvedimentoRup(String provvedimentoRup) {
		this.provvedimentoRup = provvedimentoRup;
	}
	public List<RichiedenteDTO> getRichiedenteInfo() {
		return richiedenteInfo;
	}
	public void setRichiedenteInfo(List<RichiedenteDTO> richiedenteInfo) {
		this.richiedenteInfo = richiedenteInfo;
	}
	public List<JasperInterventoInfoDto> getInterventoInfo() {
		return interventoInfo;
	}
	public void setInterventoInfo(List<JasperInterventoInfoDto> interventoInfo) {
		this.interventoInfo = interventoInfo;
	}
	public List<TipologicaDTO> getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(List<TipologicaDTO> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
	public List<JasperLocalizzazioneInterventoDTO> getListaLocalizzazioni() {
		return listaLocalizzazioni;
	}
	public void setListaLocalizzazioni(List<JasperLocalizzazioneInterventoDTO> listaLocalizzazioni) {
		this.listaLocalizzazioni = listaLocalizzazioni;
	}
	public List<JasperAllegatoDTO> getListaAllegatiLocalizzazione() {
		return listaAllegatiLocalizzazione;
	}
	public void setListaAllegatiLocalizzazione(List<JasperAllegatoDTO> listaAllegatiLocalizzazione) {
		this.listaAllegatiLocalizzazione = listaAllegatiLocalizzazione;
	}
	public List<JasperAllegatoDTO> getListaAllegatiProvvedimento() {
		return listaAllegatiProvvedimento;
	}
	public void setListaAllegatiProvvedimento(List<JasperAllegatoDTO> listaAllegatiProvvedimento) {
		this.listaAllegatiProvvedimento = listaAllegatiProvvedimento;
	}
	public List<JasperAllegatoDTO> getListaAllegatiAmministrativi() {
		return listaAllegatiAmministrativi;
	}
	public void setListaAllegatiAmministrativi(List<JasperAllegatoDTO> listaAllegatiAmministrativi) {
		this.listaAllegatiAmministrativi = listaAllegatiAmministrativi;
	}
	public List<JasperAllegatoDTO> getListaAllegatiTecnici() {
		return listaAllegatiTecnici;
	}
	public void setListaAllegatiTecnici(List<JasperAllegatoDTO> listaAllegatiTecnici) {
		this.listaAllegatiTecnici = listaAllegatiTecnici;
	}
	public Map<String, Object> getSezioniDinamiche() {
		return sezioniDinamiche;
	}
	public void setSezioniDinamiche(Map<String, Object> sezioniDinamiche) {
		this.sezioniDinamiche = sezioniDinamiche;
	}

	@Override
	public String toString() {
		return "JasperDTO [idFascicolo=" + idFascicolo + ", protocollo=" + protocollo + ", sanatoria=" + sanatoria
				+ ", codicePratica=" + codicePratica + ", ufficio=" + ufficio + ", provvedimentoNumero="
				+ provvedimentoNumero + ", provvedimentoDataAutorizzazione=" + provvedimentoDataAutorizzazione
				+ ", provvedimentoEsito=" + provvedimentoEsito + ", provvedimentoRup=" + provvedimentoRup
				+ ", richiedenteInfo=" + richiedenteInfo + ", interventoInfo=" + interventoInfo + ", listaDestinatari="
				+ listaDestinatari + ", listaLocalizzazioni=" + listaLocalizzazioni + ", listaAllegatiLocalizzazione="
				+ listaAllegatiLocalizzazione + ", listaAllegatiProvvedimento=" + listaAllegatiProvvedimento
				+ ", listaAllegatiAmministrativi=" + listaAllegatiAmministrativi + ", listaAllegatiTecnici="
				+ listaAllegatiTecnici + ", sezioniDinamiche=" + sezioniDinamiche + "]";
	}

}