package it.eng.tz.puglia.servizi_esterni.jasper.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.entity.ResponsabileDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.utility.StringWrapper;

public class JasperDTO implements SezioniDinamiche{
	
	private String codiceApp;
	private String protocollo;
	private Boolean sanatoria;
	private List<TipologicaDTO> listaDestinatari;
	private String codicePratica;
	private String ufficio;
	private String richiedente;
	private String tipoProcedimento;
	private String oggettoIntervento;
	private List<RichiedenteDTO> richiedenteInfo;
	private List<ResponsabileDTO> responsabileInfo;
	private String provvedimentoNumero;
	private Date   provvedimentoDataAutorizzazione;
	private String provvedimentoEsito;
	private String provvedimentoRup;
	private List<JasperLocalizzazioneInterventoDTO> listaLocalizzazioni;
	private List<JasperAllegatoDTO> listaAllegatiLocalizzazione;
	private List<JasperAllegatoDTO> listaAllegatiProvvedimento;
	private List<JasperAllegatoDTO> listaAllegati;
	private String interventoTipologia;				// usato anche per PARERI
	private List<StringWrapper> interventoCaratterizzazione;
	private String interventoTempo;
	private List<StringWrapper> interventoQualificazioneA;
	private List<StringWrapper> interventoQualificazioneB;
	private String codiceTipoProcedimento;						// è il codice associato al "tipoProcedimento"
	private Date dataDelibera;						// PARERI e PUTT
	private String deliberaNum;						// PARERI
	private String oggettoDelibera;					// PARERI
	private String infoDeliberePrecedenti;			// PARERI
	private String descrizioneIntervento;			// PARERI
	private Map<String,Object> sezioniDinamiche;
	private int id_fascicolo;
	private EsitoVerifica esitoVerifica;
	private String numeroProvvedimentoEsito;
	private Date dataProvvedimentoEsito;

	
	public JasperDTO() {
		this.listaDestinatari = new ArrayList<>();
		this.richiedenteInfo = new ArrayList<>();
		this.responsabileInfo = new ArrayList<>();
		this.listaLocalizzazioni = new ArrayList<>();
		this.listaAllegatiLocalizzazione = new ArrayList<>();
		this.listaAllegatiProvvedimento = new ArrayList<>();
		this.listaAllegati = new ArrayList<>();
		this.interventoCaratterizzazione = new ArrayList<>();
		this.interventoQualificazioneA = new ArrayList<>();
		this.interventoQualificazioneB = new ArrayList<>();
	}
	
	
	
	public int getId_fascicolo() {
		return id_fascicolo;
	}



	public void setId_fascicolo(int id_fascicolo) {
		this.id_fascicolo = id_fascicolo;
	}



	public Map<String, Object> getSezioniDinamiche() {
		return sezioniDinamiche;
	}



	public void setSezioniDinamiche(Map<String, Object> sezioniDinamiche) {
		this.sezioniDinamiche = sezioniDinamiche;
	}



	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Boolean isSanatoria() {
		return sanatoria;
	}

	public Boolean getSanatoria() {
		return sanatoria;
	}
	
	public void setSanatoria(Boolean sanatoria) {
		this.sanatoria = sanatoria;
	}

	public List<TipologicaDTO> getListaDestinatari() {
		return listaDestinatari;
	}

	public void setListaDestinatari(List<TipologicaDTO> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
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

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public String getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public String getOggettoIntervento() {
		return oggettoIntervento;
	}

	public void setOggettoIntervento(String oggettoIntervento) {
		this.oggettoIntervento = oggettoIntervento;
	}

	public List<RichiedenteDTO> getRichiedenteInfo() {
		return richiedenteInfo;
	}

	public void setRichiedenteInfo(List<RichiedenteDTO> richiedenteInfo) {
		this.richiedenteInfo = richiedenteInfo;
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

	public List<JasperAllegatoDTO> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<JasperAllegatoDTO> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public String getInterventoTipologia() {
		return interventoTipologia;
	}

	public void setInterventoTipologia(String interventoTipologia) {
		this.interventoTipologia = interventoTipologia;
	}

	public List<StringWrapper> getInterventoCaratterizzazione() {
		return interventoCaratterizzazione;
	}

	public void setInterventoCaratterizzazione(List<StringWrapper> interventoCaratterizzazione) {
		this.interventoCaratterizzazione = interventoCaratterizzazione;
	}

	public String getInterventoTempo() {
		return interventoTempo;
	}

	public void setInterventoTempo(String interventoTempo) {
		this.interventoTempo = interventoTempo;
	}

	public List<StringWrapper> getInterventoQualificazioneA() {
		return interventoQualificazioneA;
	}

	public void setInterventoQualificazioneA(List<StringWrapper> interventoQualificazioneA) {
		this.interventoQualificazioneA = interventoQualificazioneA;
	}

	public List<StringWrapper> getInterventoQualificazioneB() {
		return interventoQualificazioneB;
	}

	public void setInterventoQualificazioneB(List<StringWrapper> interventoQualificazioneB) {
		this.interventoQualificazioneB = interventoQualificazioneB;
	}

	public String getCodiceTipoProcedimento() {
		return codiceTipoProcedimento;
	}

	public void setCodiceTipoProcedimento(String codiceTipoProcedimento) {
		this.codiceTipoProcedimento = codiceTipoProcedimento;
	}

	public String getCodiceApp() {
		return codiceApp;
	}

	public void setCodiceApp(String codiceApp) {
		this.codiceApp = codiceApp;
	}

	public Date getDataDelibera() {
		return dataDelibera;
	}

	public void setDataDelibera(Date dataDelibera) {
		this.dataDelibera = dataDelibera;
	}

	public String getDeliberaNum() {
		return deliberaNum;
	}

	public void setDeliberaNum(String deliberaNum) {
		this.deliberaNum = deliberaNum;
	}

	public String getOggettoDelibera() {
		return oggettoDelibera;
	}

	public void setOggettoDelibera(String oggettoDelibera) {
		this.oggettoDelibera = oggettoDelibera;
	}

	public String getInfoDeliberePrecedenti() {
		return infoDeliberePrecedenti;
	}

	public void setInfoDeliberePrecedenti(String infoDeliberePrecedenti) {
		this.infoDeliberePrecedenti = infoDeliberePrecedenti;
	}

	public String getDescrizioneIntervento() {
		return descrizioneIntervento;
	}

	public void setDescrizioneIntervento(String descrizioneIntervento) {
		this.descrizioneIntervento = descrizioneIntervento;
	}

	public List<ResponsabileDTO> getResponsabileInfo() {
		return responsabileInfo;
	}

	public void setResponsabileInfo(List<ResponsabileDTO> responsabileInfo) {
		this.responsabileInfo = responsabileInfo;
	}
	
	public EsitoVerifica getEsitoVerifica()
	{
		return esitoVerifica;
	}
	public void setEsitoVerifica(EsitoVerifica esitoVerifica)
	{
		this.esitoVerifica = esitoVerifica;
	}
	
	public String getNumeroProvvedimentoEsito()
	{
		return numeroProvvedimentoEsito;
	}
	public void setNumeroProvvedimentoEsito(String numeroProvvedimentoEsito)
	{
		this.numeroProvvedimentoEsito = numeroProvvedimentoEsito;
	}

	public Date getDataProvvedimentoEsito()
	{
		return dataProvvedimentoEsito;
	}
	public void setDataProvvedimentoEsito(Date dataProvvedimentoEsito)
	{
		this.dataProvvedimentoEsito = dataProvvedimentoEsito;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceApp == null) ? 0 : codiceApp.hashCode());
		result = prime * result + ((codicePratica == null) ? 0 : codicePratica.hashCode());
		result = prime * result + ((codiceTipoProcedimento == null) ? 0 : codiceTipoProcedimento.hashCode());
		result = prime * result + ((dataDelibera == null) ? 0 : dataDelibera.hashCode());
		result = prime * result + ((dataProvvedimentoEsito == null) ? 0 : dataProvvedimentoEsito.hashCode());
		result = prime * result + ((deliberaNum == null) ? 0 : deliberaNum.hashCode());
		result = prime * result + ((descrizioneIntervento == null) ? 0 : descrizioneIntervento.hashCode());
		result = prime * result + ((esitoVerifica == null) ? 0 : esitoVerifica.hashCode());
		result = prime * result + id_fascicolo;
		result = prime * result + ((infoDeliberePrecedenti == null) ? 0 : infoDeliberePrecedenti.hashCode());
		result = prime * result + ((interventoCaratterizzazione == null) ? 0 : interventoCaratterizzazione.hashCode());
		result = prime * result + ((interventoQualificazioneA == null) ? 0 : interventoQualificazioneA.hashCode());
		result = prime * result + ((interventoQualificazioneB == null) ? 0 : interventoQualificazioneB.hashCode());
		result = prime * result + ((interventoTempo == null) ? 0 : interventoTempo.hashCode());
		result = prime * result + ((interventoTipologia == null) ? 0 : interventoTipologia.hashCode());
		result = prime * result + ((listaAllegati == null) ? 0 : listaAllegati.hashCode());
		result = prime * result + ((listaAllegatiLocalizzazione == null) ? 0 : listaAllegatiLocalizzazione.hashCode());
		result = prime * result + ((listaAllegatiProvvedimento == null) ? 0 : listaAllegatiProvvedimento.hashCode());
		result = prime * result + ((listaDestinatari == null) ? 0 : listaDestinatari.hashCode());
		result = prime * result + ((listaLocalizzazioni == null) ? 0 : listaLocalizzazioni.hashCode());
		result = prime * result + ((numeroProvvedimentoEsito == null) ? 0 : numeroProvvedimentoEsito.hashCode());
		result = prime * result + ((oggettoDelibera == null) ? 0 : oggettoDelibera.hashCode());
		result = prime * result + ((oggettoIntervento == null) ? 0 : oggettoIntervento.hashCode());
		result = prime * result + ((protocollo == null) ? 0 : protocollo.hashCode());
		result = prime * result
				+ ((provvedimentoDataAutorizzazione == null) ? 0 : provvedimentoDataAutorizzazione.hashCode());
		result = prime * result + ((provvedimentoEsito == null) ? 0 : provvedimentoEsito.hashCode());
		result = prime * result + ((provvedimentoNumero == null) ? 0 : provvedimentoNumero.hashCode());
		result = prime * result + ((provvedimentoRup == null) ? 0 : provvedimentoRup.hashCode());
		result = prime * result + ((responsabileInfo == null) ? 0 : responsabileInfo.hashCode());
		result = prime * result + ((richiedente == null) ? 0 : richiedente.hashCode());
		result = prime * result + ((richiedenteInfo == null) ? 0 : richiedenteInfo.hashCode());
		result = prime * result + ((sanatoria == null) ? 0 : sanatoria.hashCode());
		result = prime * result + ((sezioniDinamiche == null) ? 0 : sezioniDinamiche.hashCode());
		result = prime * result + ((tipoProcedimento == null) ? 0 : tipoProcedimento.hashCode());
		result = prime * result + ((ufficio == null) ? 0 : ufficio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JasperDTO other = (JasperDTO) obj;
		if (codiceApp == null)
		{
			if (other.codiceApp != null)
				return false;
		} else if (!codiceApp.equals(other.codiceApp))
			return false;
		if (codicePratica == null)
		{
			if (other.codicePratica != null)
				return false;
		} else if (!codicePratica.equals(other.codicePratica))
			return false;
		if (codiceTipoProcedimento == null)
		{
			if (other.codiceTipoProcedimento != null)
				return false;
		} else if (!codiceTipoProcedimento.equals(other.codiceTipoProcedimento))
			return false;
		if (dataDelibera == null)
		{
			if (other.dataDelibera != null)
				return false;
		} else if (!dataDelibera.equals(other.dataDelibera))
			return false;
		if (dataProvvedimentoEsito == null)
		{
			if (other.dataProvvedimentoEsito != null)
				return false;
		} else if (!dataProvvedimentoEsito.equals(other.dataProvvedimentoEsito))
			return false;
		if (deliberaNum == null)
		{
			if (other.deliberaNum != null)
				return false;
		} else if (!deliberaNum.equals(other.deliberaNum))
			return false;
		if (descrizioneIntervento == null)
		{
			if (other.descrizioneIntervento != null)
				return false;
		} else if (!descrizioneIntervento.equals(other.descrizioneIntervento))
			return false;
		if (esitoVerifica != other.esitoVerifica)
			return false;
		if (id_fascicolo != other.id_fascicolo)
			return false;
		if (infoDeliberePrecedenti == null)
		{
			if (other.infoDeliberePrecedenti != null)
				return false;
		} else if (!infoDeliberePrecedenti.equals(other.infoDeliberePrecedenti))
			return false;
		if (interventoCaratterizzazione == null)
		{
			if (other.interventoCaratterizzazione != null)
				return false;
		} else if (!interventoCaratterizzazione.equals(other.interventoCaratterizzazione))
			return false;
		if (interventoQualificazioneA == null)
		{
			if (other.interventoQualificazioneA != null)
				return false;
		} else if (!interventoQualificazioneA.equals(other.interventoQualificazioneA))
			return false;
		if (interventoQualificazioneB == null)
		{
			if (other.interventoQualificazioneB != null)
				return false;
		} else if (!interventoQualificazioneB.equals(other.interventoQualificazioneB))
			return false;
		if (interventoTempo == null)
		{
			if (other.interventoTempo != null)
				return false;
		} else if (!interventoTempo.equals(other.interventoTempo))
			return false;
		if (interventoTipologia == null)
		{
			if (other.interventoTipologia != null)
				return false;
		} else if (!interventoTipologia.equals(other.interventoTipologia))
			return false;
		if (listaAllegati == null)
		{
			if (other.listaAllegati != null)
				return false;
		} else if (!listaAllegati.equals(other.listaAllegati))
			return false;
		if (listaAllegatiLocalizzazione == null)
		{
			if (other.listaAllegatiLocalizzazione != null)
				return false;
		} else if (!listaAllegatiLocalizzazione.equals(other.listaAllegatiLocalizzazione))
			return false;
		if (listaAllegatiProvvedimento == null)
		{
			if (other.listaAllegatiProvvedimento != null)
				return false;
		} else if (!listaAllegatiProvvedimento.equals(other.listaAllegatiProvvedimento))
			return false;
		if (listaDestinatari == null)
		{
			if (other.listaDestinatari != null)
				return false;
		} else if (!listaDestinatari.equals(other.listaDestinatari))
			return false;
		if (listaLocalizzazioni == null)
		{
			if (other.listaLocalizzazioni != null)
				return false;
		} else if (!listaLocalizzazioni.equals(other.listaLocalizzazioni))
			return false;
		if (numeroProvvedimentoEsito == null)
		{
			if (other.numeroProvvedimentoEsito != null)
				return false;
		} else if (!numeroProvvedimentoEsito.equals(other.numeroProvvedimentoEsito))
			return false;
		if (oggettoDelibera == null)
		{
			if (other.oggettoDelibera != null)
				return false;
		} else if (!oggettoDelibera.equals(other.oggettoDelibera))
			return false;
		if (oggettoIntervento == null)
		{
			if (other.oggettoIntervento != null)
				return false;
		} else if (!oggettoIntervento.equals(other.oggettoIntervento))
			return false;
		if (protocollo == null)
		{
			if (other.protocollo != null)
				return false;
		} else if (!protocollo.equals(other.protocollo))
			return false;
		if (provvedimentoDataAutorizzazione == null)
		{
			if (other.provvedimentoDataAutorizzazione != null)
				return false;
		} else if (!provvedimentoDataAutorizzazione.equals(other.provvedimentoDataAutorizzazione))
			return false;
		if (provvedimentoEsito == null)
		{
			if (other.provvedimentoEsito != null)
				return false;
		} else if (!provvedimentoEsito.equals(other.provvedimentoEsito))
			return false;
		if (provvedimentoNumero == null)
		{
			if (other.provvedimentoNumero != null)
				return false;
		} else if (!provvedimentoNumero.equals(other.provvedimentoNumero))
			return false;
		if (provvedimentoRup == null)
		{
			if (other.provvedimentoRup != null)
				return false;
		} else if (!provvedimentoRup.equals(other.provvedimentoRup))
			return false;
		if (responsabileInfo == null)
		{
			if (other.responsabileInfo != null)
				return false;
		} else if (!responsabileInfo.equals(other.responsabileInfo))
			return false;
		if (richiedente == null)
		{
			if (other.richiedente != null)
				return false;
		} else if (!richiedente.equals(other.richiedente))
			return false;
		if (richiedenteInfo == null)
		{
			if (other.richiedenteInfo != null)
				return false;
		} else if (!richiedenteInfo.equals(other.richiedenteInfo))
			return false;
		if (sanatoria == null)
		{
			if (other.sanatoria != null)
				return false;
		} else if (!sanatoria.equals(other.sanatoria))
			return false;
		if (sezioniDinamiche == null)
		{
			if (other.sezioniDinamiche != null)
				return false;
		} else if (!sezioniDinamiche.equals(other.sezioniDinamiche))
			return false;
		if (tipoProcedimento == null)
		{
			if (other.tipoProcedimento != null)
				return false;
		} else if (!tipoProcedimento.equals(other.tipoProcedimento))
			return false;
		if (ufficio == null)
		{
			if (other.ufficio != null)
				return false;
		} else if (!ufficio.equals(other.ufficio))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "JasperDTO [codiceApp=" + codiceApp + ", protocollo=" + protocollo + ", sanatoria=" + sanatoria
				+ ", listaDestinatari=" + listaDestinatari + ", codicePratica=" + codicePratica + ", ufficio=" + ufficio
				+ ", richiedente=" + richiedente + ", tipoProcedimento=" + tipoProcedimento + ", oggettoIntervento="
				+ oggettoIntervento + ", richiedenteInfo=" + richiedenteInfo + ", responsabileInfo=" + responsabileInfo
				+ ", provvedimentoNumero=" + provvedimentoNumero + ", provvedimentoDataAutorizzazione="
				+ provvedimentoDataAutorizzazione + ", provvedimentoEsito=" + provvedimentoEsito + ", provvedimentoRup="
				+ provvedimentoRup + ", listaLocalizzazioni=" + listaLocalizzazioni + ", listaAllegatiLocalizzazione="
				+ listaAllegatiLocalizzazione + ", listaAllegatiProvvedimento=" + listaAllegatiProvvedimento
				+ ", listaAllegati=" + listaAllegati + ", interventoTipologia=" + interventoTipologia
				+ ", interventoCaratterizzazione=" + interventoCaratterizzazione + ", interventoTempo="
				+ interventoTempo + ", interventoQualificazioneA=" + interventoQualificazioneA
				+ ", interventoQualificazioneB=" + interventoQualificazioneB + ", codiceTipoProcedimento="
				+ codiceTipoProcedimento + ", dataDelibera=" + dataDelibera + ", deliberaNum=" + deliberaNum
				+ ", oggettoDelibera=" + oggettoDelibera + ", infoDeliberePrecedenti=" + infoDeliberePrecedenti
				+ ", descrizioneIntervento=" + descrizioneIntervento + ", sezioniDinamiche=" + sezioniDinamiche
				+ ", id_fascicolo=" + id_fascicolo + ", esitoVerifica=" + esitoVerifica + ", numeroProvvedimentoEsito="
				+ numeroProvvedimentoEsito + ", dataProvvedimentoEsito=" + dataProvvedimentoEsito + "]";
	}
	
}