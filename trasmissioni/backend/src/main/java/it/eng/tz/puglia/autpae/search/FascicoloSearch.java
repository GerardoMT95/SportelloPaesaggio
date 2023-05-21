package it.eng.tz.puglia.autpae.search;

import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.StatoRegistrazione;
import it.eng.tz.puglia.autpae.enumeratori.TipoIntervento;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.autpae.utility.Stringhe;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.fascicolo
 */
public class FascicoloSearch extends WhereClauseGenerator<Fascicolo>
{	
	private static final long serialVersionUID = 2507186942316677656L;
	
//	private String organizzazioneLoggato;			 // in formato "XXXX_id" (oppure "ADMIN")
//	private List<Integer> entiDiCompetenza;
	private boolean ricercaPubblica = false;
	
	private Long id;
	private String ufficio;							 // è il gruppo creazione in formato "XXXX_id_Y"
	private Integer orgCreazione;					 // è l'id di "pae_org" del gruppo creazione
	private TipoProcedimento tipoProcedimento; 		 // 3															fabio lopez mettere il pezzo di query
	private String oggettoIntervento;
	private String comuneIntervento;				 // campo Comuni Intervento (mi arriva il "codice istat")
	private Boolean sanatoria;
	private String codiceInternoEnte;  				 // 10
	private String numeroInternoEnte;  				 // 12
	private String protocollo;          			 // 11
	private Date dataProtocolloDa;   				 // 13
	private Date dataProtocolloA;    				 //	13
	private String note;
	private StatoFascicolo stato; 					 // 7
	private StatoFascicolo statoPrecedente;
	private String codice; 							 // 1
	private String numeroProvvedimento;
	private Date dataRilascioAutorizzazioneDa;  	 // 4
	private Date dataRilascioAutorizzazioneA;		 // 4
	private EsitoProvvedimento esito; 				 // 6
	private String rup;
	private TipoIntervento tipologiaIntervento; 	 // 2
	private Character interventoDettaglio;
	private Character interventoSpecifica;
	private Date dataCreazioneDa;
	private Date dataCreazioneA;
	private Date dataUltimaModificaDa;
	private Date dataUltimaModificaA;
	private Date dataTrasmissioneDa;
	private Date dataTrasmissioneA;
	private Date dataCampionamentoDa;
	private Date dataCampionamentoA;
	private Date dataVerificaDa;
	private Date dataVerificaA;
	private String usernameUtenteCreazione;
	private String usernameUtenteUltimaModifica;
	private String usernameUtenteTrasmissione;
	private String denominazioneUtenteTrasmissione;
	private String emailUtenteTrasmissione;
	private StatoRegistrazione statoRegistrazione;	 // 8   // non c'è nel DB
	private EsitoVerifica esitoVerifica;			 // 9
	private EsitoVerifica esitoVerificaPrecedente;
	private Date dataDeliberaDa;
	private Date dataDeliberaA;
	private String deliberaNum;
	private String oggettoDelibera;
	private String infoDeliberePrecedenti;
	private String descrizioneIntervento;
	private Boolean deleted;
	private Date modificabileFino;
	//gestiti dalla prepareForSearch;
	private Ruoli ruolo;
	private Gruppi gruppo;
	private List<Integer> entiDiCompetenza;
	private String organizzazioneLoggato; 
	private Integer idOrganizzazioneLoggato;	
	private String usernameLoggato;
	private String codiceTrasmissione; //pari al codice ma senza like
	
	
	public FascicoloSearch() { }
	
	
	
	public Ruoli getRuolo() {
		return ruolo;
	}



	public void setRuolo(Ruoli ruolo) {
		this.ruolo = ruolo;
	}



	public Gruppi getGruppo() {
		return gruppo;
	}



	public void setGruppo(Gruppi gruppo) {
		this.gruppo = gruppo;
	}



	public List<Integer> getEntiDiCompetenza() {
		return entiDiCompetenza;
	}



	public void setEntiDiCompetenza(List<Integer> entiDiCompetenza) {
		this.entiDiCompetenza = entiDiCompetenza;
	}



	public String getOrganizzazioneLoggato() {
		return organizzazioneLoggato;
	}



	public void setOrganizzazioneLoggato(String organizzazioneLoggato) {
		this.organizzazioneLoggato = organizzazioneLoggato;
	}



	public boolean isRicercaPubblica() {
		return ricercaPubblica;
	}

	public void setRicercaPubblica(boolean ricercaPubblica) {
		this.ricercaPubblica = ricercaPubblica;
	}
	
	public String getComuneIntervento() {
		return comuneIntervento;
	}
	
	public void setComuneIntervento(String comuneIntervento) {
		this.comuneIntervento = comuneIntervento;
	}
	
	public TipoIntervento getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setTipologiaIntervento(TipoIntervento tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}

//	public String getOrganizzazioneLoggato() {
//		return organizzazioneLoggato;
//	}
//
//	public void setOrganizzazioneLoggato(String organizzazioneLoggato) {
//		this.organizzazioneLoggato = organizzazioneLoggato;
//	}
//
//	public List<Integer> getEntiDiCompetenza() {
//		return entiDiCompetenza;
//	}
//
//	public void setEntiDiCompetenza(List<Integer> entiDiCompetenza) {
//		this.entiDiCompetenza = entiDiCompetenza;
//	}
	public StatoFascicolo getStatoPrecedente() {
		return statoPrecedente;
	}

	public String getDenominazioneUtenteTrasmissione() {
		return denominazioneUtenteTrasmissione;
	}

	public void setDenominazioneUtenteTrasmissione(String denominazioneUtenteTrasmissione) {
		this.denominazioneUtenteTrasmissione = denominazioneUtenteTrasmissione;
	}

	public String getEmailUtenteTrasmissione() {
		return emailUtenteTrasmissione;
	}

	public void setEmailUtenteTrasmissione(String emailUtenteTrasmissione) {
		this.emailUtenteTrasmissione = emailUtenteTrasmissione;
	}

	public void setStatoPrecedente(StatoFascicolo statoPrecedente) {
		this.statoPrecedente = statoPrecedente;
	}

	public EsitoVerifica getEsitoVerificaPrecedente() {
		return esitoVerificaPrecedente;
	}

	public void setEsitoVerificaPrecedente(EsitoVerifica esitoVerificaPrecedente) {
		this.esitoVerificaPrecedente = esitoVerificaPrecedente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public Integer getOrgCreazione() {
		return orgCreazione;
	}

	public void setOrgCreazione(Integer orgCreazione) {
		this.orgCreazione = orgCreazione;
	}

	public TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public String getOggettoIntervento() {
		return oggettoIntervento;
	}

	public void setOggettoIntervento(String oggettoIntervento) {
		this.oggettoIntervento = oggettoIntervento;
	}

	public Boolean getSanatoria() {
		return sanatoria;
	}

	public void setSanatoria(Boolean sanatoria) {
		this.sanatoria = sanatoria;
	}

	public String getCodiceInternoEnte() {
		return codiceInternoEnte;
	}

	public void setCodiceInternoEnte(String codiceInternoEnte) {
		this.codiceInternoEnte = codiceInternoEnte;
	}

	public String getNumeroInternoEnte() {
		return numeroInternoEnte;
	}

	public void setNumeroInternoEnte(String numeroInternoEnte) {
		this.numeroInternoEnte = numeroInternoEnte;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Date getDataProtocolloDa() {
		return dataProtocolloDa;
	}

	public void setDataProtocolloDa(Date dataProtocolloDa) {
		this.dataProtocolloDa = dataProtocolloDa;
	}

	public Date getDataProtocolloA() {
		return dataProtocolloA;
	}

	public void setDataProtocolloA(Date dataProtocolloA) {
		this.dataProtocolloA = dataProtocolloA;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public StatoFascicolo getStato() {
		return stato;
	}

	public void setStato(StatoFascicolo stato) {
		this.stato = stato;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}

	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}

	public Date getDataRilascioAutorizzazioneDa() {
		return dataRilascioAutorizzazioneDa;
	}

	public void setDataRilascioAutorizzazioneDa(Date dataRilascioAutorizzazioneDa) {
		this.dataRilascioAutorizzazioneDa = dataRilascioAutorizzazioneDa;
	}

	public Date getDataRilascioAutorizzazioneA() {
		return dataRilascioAutorizzazioneA;
	}

	public void setDataRilascioAutorizzazioneA(Date dataRilascioAutorizzazioneA) {
		this.dataRilascioAutorizzazioneA = dataRilascioAutorizzazioneA;
	}

	public EsitoProvvedimento getEsito() {
		return esito;
	}

	public void setEsito(EsitoProvvedimento esito) {
		this.esito = esito;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public Character getInterventoDettaglio() {
		return interventoDettaglio;
	}

	public void setInterventoDettaglio(Character interventoDettaglio) {
		this.interventoDettaglio = interventoDettaglio;
	}

	public Character getInterventoSpecifica() {
		return interventoSpecifica;
	}

	public void setInterventoSpecifica(Character interventoSpecifica) {
		this.interventoSpecifica = interventoSpecifica;
	}

	public Date getDataCreazioneDa() {
		return dataCreazioneDa;
	}

	public void setDataCreazioneDa(Date dataCreazioneDa) {
		this.dataCreazioneDa = dataCreazioneDa;
	}

	public Date getDataCreazioneA() {
		return dataCreazioneA;
	}

	public void setDataCreazioneA(Date dataCreazioneA) {
		this.dataCreazioneA = dataCreazioneA;
	}

	public Date getDataUltimaModificaDa() {
		return dataUltimaModificaDa;
	}

	public void setDataUltimaModificaDa(Date dataUltimaModificaDa) {
		this.dataUltimaModificaDa = dataUltimaModificaDa;
	}

	public Date getDataUltimaModificaA() {
		return dataUltimaModificaA;
	}

	public void setDataUltimaModificaA(Date dataUltimaModificaA) {
		this.dataUltimaModificaA = dataUltimaModificaA;
	}

	public Date getDataTrasmissioneDa() {
		return dataTrasmissioneDa;
	}

	public void setDataTrasmissioneDa(Date dataTrasmissioneDa) {
		this.dataTrasmissioneDa = dataTrasmissioneDa;
	}

	public Date getDataTrasmissioneA() {
		return dataTrasmissioneA;
	}

	public void setDataTrasmissioneA(Date dataTrasmissioneA) {
		this.dataTrasmissioneA = dataTrasmissioneA;
	}

	public Date getDataCampionamentoDa() {
		return dataCampionamentoDa;
	}

	public void setDataCampionamentoDa(Date dataCampionamentoDa) {
		this.dataCampionamentoDa = dataCampionamentoDa;
	}

	public Date getDataCampionamentoA() {
		return dataCampionamentoA;
	}

	public void setDataCampionamentoA(Date dataCampionamentoA) {
		this.dataCampionamentoA = dataCampionamentoA;
	}

	public Date getDataVerificaDa() {
		return dataVerificaDa;
	}

	public void setDataVerificaDa(Date dataVerificaDa) {
		this.dataVerificaDa = dataVerificaDa;
	}

	public Date getDataVerificaA() {
		return dataVerificaA;
	}

	public void setDataVerificaA(Date dataVerificaA) {
		this.dataVerificaA = dataVerificaA;
	}

	public String getUsernameUtenteCreazione() {
		return usernameUtenteCreazione;
	}

	public void setUsernameUtenteCreazione(String usernameUtenteCreazione) {
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}

	public String getUsernameUtenteUltimaModifica() {
		return usernameUtenteUltimaModifica;
	}

	public void setUsernameUtenteUltimaModifica(String usernameUtenteUltimaModifica) {
		this.usernameUtenteUltimaModifica = usernameUtenteUltimaModifica;
	}

	public String getUsernameUtenteTrasmissione() {
		return usernameUtenteTrasmissione;
	}

	public void setUsernameUtenteTrasmissione(String usernameUtenteTrasmissione) {
		this.usernameUtenteTrasmissione = usernameUtenteTrasmissione;
	}

	public StatoRegistrazione getStatoRegistrazione() {
		return statoRegistrazione;
	}

	public void setStatoRegistrazione(StatoRegistrazione statoRegistrazione) {
		this.statoRegistrazione = statoRegistrazione;
	}

	public EsitoVerifica getEsitoVerifica() {
		return esitoVerifica;
	}

	public void setEsitoVerifica(EsitoVerifica esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}

	public Date getDataDeliberaDa() {
		return dataDeliberaDa;
	}

	public void setDataDeliberaDa(Date dataDeliberaDa) {
		this.dataDeliberaDa = dataDeliberaDa;
	}

	public Date getDataDeliberaA() {
		return dataDeliberaA;
	}

	public void setDataDeliberaA(Date dataDeliberaA) {
		this.dataDeliberaA = dataDeliberaA;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getModificabileFino() {
		return modificabileFino;
	}

	public void setModificabileFino(Date modificabileFino) {
		this.modificabileFino = modificabileFino;
	}

	
	@Override
	public void generateWhereCriteria()
	{
		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(Fascicolo.id.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.id.columnName());
			parameters.put(Fascicolo.id.columnName(), id);
			separator = " and ";
		}
		if(deleted != null)
		{
			sql.append(separator)
			   .append(Fascicolo.deleted.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.deleted.columnName());
			parameters.put(Fascicolo.deleted.columnName(), deleted);
			separator = " and ";
		}
		if(tipoProcedimento!=null)
		{
			sql.append(separator)
			   .append(Fascicolo.tipo_procedimento.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.tipo_procedimento.columnName());
			parameters.put(Fascicolo.tipo_procedimento.columnName(), tipoProcedimento.name());
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(codice))
		{
			sql.append(separator)
			   .append(Fascicolo.codice.getCompleteName())
			   .append(" LIKE :")
			   .append(Fascicolo.codice.columnName());
			parameters.put(Fascicolo.codice.columnName(), StringUtil.convertRightLike(codice.toUpperCase()));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(oggettoIntervento))
		{
			sql.append(separator)
			   .append(Fascicolo.oggetto_intervento.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.oggetto_intervento.columnName());
			parameters.put(Fascicolo.oggetto_intervento.columnName(), oggettoIntervento);
			separator = " and ";
		}
		if(sanatoria != null)
		{
			sql.append(separator)
			   .append(Fascicolo.sanatoria.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.sanatoria.columnName());
			parameters.put(Fascicolo.sanatoria.columnName(), sanatoria);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(codiceInternoEnte))
		{
			sql.append(separator)
			   .append(Fascicolo.codice_interno_ente.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.codice_interno_ente.columnName());
			parameters.put(Fascicolo.codice_interno_ente.columnName(), codiceInternoEnte);
			separator = " and ";
		}
		if(statoPrecedente != null)
		{
			sql.append(separator)
			   .append(Fascicolo.stato_precedente.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.stato_precedente.columnName());
			parameters.put(Fascicolo.stato_precedente.columnName(), statoPrecedente.name());
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(numeroInternoEnte))
		{
			sql.append(separator)
			   .append(Fascicolo.numero_interno_ente.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.numero_interno_ente.columnName());
			parameters.put(Fascicolo.numero_interno_ente.columnName(), numeroInternoEnte);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(protocollo))
		{
			sql.append(separator)
			   .append("lower(")
			   .append(Fascicolo.protocollo.getCompleteName())
			   .append("::text) like :")
			   .append(Fascicolo.protocollo.columnName());
			parameters.put(Fascicolo.protocollo.columnName(), StringUtil.convertRightLike(protocollo.toLowerCase()));
			separator = " and ";
		}
		if(dataProtocolloDa != null && dataProtocolloA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_protocollo.getCompleteName())
			   .append(" between :dataProtocolloDa and :dataProtocolloA");
			parameters.put("dataProtocolloDa", dataProtocolloDa);
			parameters.put("dataProtocolloA", DateUtil.endOfDay(dataProtocolloA));
			separator = " and ";
		}
		else if(dataProtocolloDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_protocollo.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_protocollo.columnName());
			parameters.put(Fascicolo.data_protocollo.columnName(), dataProtocolloDa);
			separator = " and ";
		}
		else if(dataProtocolloA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_protocollo.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_protocollo.columnName());
			parameters.put(Fascicolo.data_protocollo.columnName(), DateUtil.endOfDay(dataProtocolloA));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(note))
		{
			sql.append(separator)
			   .append("lower(")
			   .append(Fascicolo.note.getCompleteName())
			   .append("::text) like :")
			   .append(Fascicolo.note.columnName());
			parameters.put(Fascicolo.note.columnName(), StringUtil.convertLike(note.toLowerCase()));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(numeroProvvedimento))
		{
			sql.append(separator)
			   .append(Fascicolo.numero_provvedimento.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.numero_provvedimento.columnName());
			parameters.put(Fascicolo.numero_provvedimento.columnName(), numeroProvvedimento);
			separator = " and ";
		}
		if(dataRilascioAutorizzazioneDa != null && dataRilascioAutorizzazioneA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_rilascio_autorizzazione.getCompleteName())
			   .append(" between :dataRilascioDa and :dataRilascioA");
			parameters.put("dataRilascioDa", dataRilascioAutorizzazioneDa);
			parameters.put("dataRilascioA", DateUtil.endOfDay(dataRilascioAutorizzazioneA));
			separator = " and ";
		}
		else if(dataRilascioAutorizzazioneDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_rilascio_autorizzazione.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_rilascio_autorizzazione.columnName());
			parameters.put(Fascicolo.data_rilascio_autorizzazione.columnName(), dataRilascioAutorizzazioneDa);
			separator = " and ";
		}
		else if(dataRilascioAutorizzazioneA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_rilascio_autorizzazione.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_rilascio_autorizzazione.columnName());
			parameters.put(Fascicolo.data_rilascio_autorizzazione.columnName(), DateUtil.endOfDay(dataRilascioAutorizzazioneA));
			separator = " and ";
		}
		if(esito != null)
		{
			sql.append(separator)
			   .append(Fascicolo.esito.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.esito.columnName());
			parameters.put(Fascicolo.esito.columnName(), esito.name());
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(rup))
		{
			sql.append(separator)
			   .append("lower(")
			   .append(Fascicolo.rup.getCompleteName())
			   .append("::text) like :")
			   .append(Fascicolo.rup.columnName());
			parameters.put(Fascicolo.rup.columnName(), StringUtil.convertRightLike(rup.toLowerCase()));
			separator = " and ";
		}
		if(interventoSpecifica != null)
		{
			sql.append(separator)
			   .append(Fascicolo.intervento_specifica.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.intervento_specifica.columnName());
			parameters.put(Fascicolo.intervento_specifica.columnName(), interventoSpecifica);
			separator = " and ";
		}
		if(interventoDettaglio != null)
		{
			sql.append(separator)
			   .append(Fascicolo.intervento_dettaglio.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.intervento_dettaglio.columnName());
			parameters.put(Fascicolo.intervento_dettaglio.columnName(), interventoDettaglio);
			separator = " and ";
		}
		if(dataCreazioneDa != null && dataCreazioneA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_creazione.getCompleteName())
			   .append(" between :dataCreazioneDa and :");
			parameters.put("dataCreazioneDa", dataCreazioneDa);
			parameters.put("dataCreazioneA", DateUtil.endOfDay(dataCreazioneA));
			separator = " and ";
		}
		else if(dataCreazioneDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_creazione.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_creazione.columnName());
			parameters.put(Fascicolo.data_creazione.columnName(), dataCreazioneDa);
			separator = " and ";
		}
		else if(dataCreazioneA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_creazione.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_creazione.columnName());
			parameters.put(Fascicolo.data_creazione.columnName(), DateUtil.endOfDay(dataCreazioneA));
			separator = " and ";
		}
		if(dataTrasmissioneDa != null && dataTrasmissioneA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_trasmissione.getCompleteName())
			   .append(" between :dataTrasmissioneDa and :dataTrasmissioneA");
			parameters.put("dataTrasmissioneDa", dataTrasmissioneDa);
			parameters.put("dataCreazioneA", DateUtil.endOfDay(dataTrasmissioneA));
			separator = " and ";
		}
		else if(dataTrasmissioneDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_trasmissione.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_trasmissione.columnName());
			parameters.put(Fascicolo.data_trasmissione.columnName(), dataTrasmissioneDa);
			separator = " and ";
		}
		else if(dataTrasmissioneA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_trasmissione.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_trasmissione.columnName());
			parameters.put(Fascicolo.data_trasmissione.columnName(), DateUtil.endOfDay(dataTrasmissioneA));
			separator = " and ";
		}
		if(dataCampionamentoDa != null && dataCampionamentoA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_campionamento.getCompleteName())
			   .append(" between :dataCampionamentoDa and :dataCampionamentoA");
			parameters.put("dataCampionamentoDa", dataCampionamentoDa);
			parameters.put("dataCampionamentoA", DateUtil.endOfDay(dataCampionamentoA));
			separator = " and ";
		}
		else if(dataCampionamentoDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_campionamento.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_campionamento.columnName());
			parameters.put(Fascicolo.data_campionamento.columnName(), dataCampionamentoDa);
			separator = " and ";
		}
		else if(dataCampionamentoA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_campionamento.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_campionamento.columnName());
			parameters.put(Fascicolo.data_campionamento.columnName(), DateUtil.endOfDay(dataCampionamentoA));
			separator = " and ";
		}
		if(dataVerificaDa != null && dataVerificaA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_verifica.getCompleteName())
			   .append(" between :dataVerificaDa and :dataVerificaA");
			parameters.put("dataVerificaDa", dataVerificaDa);
			parameters.put("dataVerificaA", DateUtil.endOfDay(dataVerificaA));
			separator = " and ";
		}
		else if(dataVerificaDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_verifica.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_verifica.columnName());
			parameters.put(Fascicolo.data_verifica.columnName(), dataVerificaDa);
			separator = " and ";
		}
		else if(dataVerificaA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_verifica.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_verifica.columnName());
			parameters.put(Fascicolo.data_verifica.columnName(), DateUtil.endOfDay(dataVerificaA));
			separator = " and ";
		}
		if(dataUltimaModificaDa != null && dataUltimaModificaA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_ultima_modifica.getCompleteName())
			   .append(" between :dataUltimaModificaDa and :dataUltimaModificaA");
			parameters.put("dataUltimaModificaDa", dataUltimaModificaDa);
			parameters.put("dataUltimaModificaA", DateUtil.endOfDay(dataUltimaModificaA));
			separator = " and ";
		}
		else if(dataUltimaModificaDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_ultima_modifica.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_ultima_modifica.columnName());
			parameters.put(Fascicolo.data_ultima_modifica.columnName(), dataUltimaModificaDa);
			separator = " and ";
		}
		else if(dataUltimaModificaA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_ultima_modifica.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_ultima_modifica.columnName());
			parameters.put(Fascicolo.data_ultima_modifica.columnName(), DateUtil.endOfDay(dataUltimaModificaA));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(usernameUtenteCreazione))
		{
			sql.append(separator)
			   .append(Fascicolo.username_utente_creazione.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.username_utente_creazione.columnName());
			parameters.put(Fascicolo.username_utente_creazione.columnName(), usernameUtenteCreazione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(usernameUtenteUltimaModifica))
		{
			sql.append(separator)
			   .append(Fascicolo.username_utente_ultima_modifica.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.username_utente_ultima_modifica.columnName());
			parameters.put(Fascicolo.username_utente_ultima_modifica.columnName(), usernameUtenteUltimaModifica);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(usernameUtenteTrasmissione))
		{
			sql.append(separator)
			   .append(Fascicolo.username_utente_trasmissione.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.username_utente_trasmissione.columnName());
			parameters.put(Fascicolo.username_utente_trasmissione.columnName(), usernameUtenteTrasmissione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(denominazioneUtenteTrasmissione))
		{
			sql.append(separator)
			   .append(Fascicolo.denominazione_utente_trasmissione.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.denominazione_utente_trasmissione.columnName());
			parameters.put(Fascicolo.denominazione_utente_trasmissione.columnName(), denominazioneUtenteTrasmissione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(emailUtenteTrasmissione))
		{
			sql.append(separator)
			   .append(Fascicolo.email_utente_trasmissione.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.email_utente_trasmissione.columnName());
			parameters.put(Fascicolo.email_utente_trasmissione.columnName(), emailUtenteTrasmissione);
			separator = " and ";
		}
		if(esitoVerifica != null)
		{
			sql.append(separator)
			   .append(Fascicolo.esito_verifica.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.esito_verifica.columnName());
			parameters.put(Fascicolo.esito_verifica.columnName(), esitoVerifica.name());
			separator = " and ";
		}
		if(esitoVerificaPrecedente != null)
		{
			sql.append(separator)
			   .append(Fascicolo.esito_verifica_precedente.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.esito_verifica_precedente.columnName());
			parameters.put(Fascicolo.esito_verifica_precedente.columnName(), esitoVerificaPrecedente.name());
			separator = " and ";
		}
		if(dataDeliberaDa != null && dataDeliberaA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_delibera.getCompleteName())
			   .append(" between :dataDeliberaDa and :dataDeliberaA");
			parameters.put("dataDeliberaDa", dataDeliberaDa);
			parameters.put("dataDeliberaA", DateUtil.endOfDay(dataDeliberaA));
			separator = " and ";
		}
		else if(dataDeliberaDa != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_delibera.getCompleteName())
			   .append(" >= :")
			   .append(Fascicolo.data_delibera.columnName());
			parameters.put(Fascicolo.data_delibera.columnName(), dataDeliberaDa);
			separator = " and ";
		}
		else if(dataDeliberaA != null)
		{
			sql.append(separator)
			   .append(Fascicolo.data_delibera.getCompleteName())
			   .append(" <= :")
			   .append(Fascicolo.data_delibera.columnName());
			parameters.put(Fascicolo.data_delibera.columnName(), DateUtil.endOfDay(dataDeliberaA));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(deliberaNum))
		{
			sql.append(separator)
			   .append(Fascicolo.delibera_num.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.delibera_num.columnName());
			parameters.put(Fascicolo.delibera_num.columnName(), deliberaNum);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(oggettoDelibera))
		{
			sql.append(separator)
			   .append(Fascicolo.oggetto_delibera.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.oggetto_delibera.columnName());
			parameters.put(Fascicolo.oggetto_delibera.columnName(), oggettoDelibera);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(infoDeliberePrecedenti))
		{
			sql.append(separator)
			   .append(Fascicolo.info_delibere_precedenti.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.info_delibere_precedenti.columnName());
			parameters.put(Fascicolo.info_delibere_precedenti.columnName(), infoDeliberePrecedenti);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(descrizioneIntervento))
		{
			sql.append(separator)
			   .append(Fascicolo.descrizione_intervento.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.descrizione_intervento.columnName());
			parameters.put(Fascicolo.descrizione_intervento.columnName(), descrizioneIntervento);
			separator = " and ";
		}
		if(statoRegistrazione != null)
		{
			if (statoRegistrazione == StatoRegistrazione.SELECTED)
			{
				sql.append(separator)
				.append("( ")
				.append(Fascicolo.stato.getCompleteName())
				.append(" = ")
				.append(Stringhe.apicizza(StatoFascicolo.SELECTED.name()))
				.append(" OR ")
				.append(Fascicolo.stato.getCompleteName())
				.append(" = ")
				.append(Stringhe.apicizza(StatoFascicolo.TRANSMITTED.name()))
				.append(" )");
				separator = " and ";
			}
			else
			{
				sql.append(separator)
				.append(Fascicolo.stato.getCompleteName())
				.append(" = :")
				.append(Fascicolo.stato.columnName());
				parameters.put(Fascicolo.stato.columnName(), statoRegistrazione.name());
				separator = " and ";
			}
		}
		if(stato!=null)
		{
			sql.append(separator)
			   .append(Fascicolo.stato.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.stato.columnName());
			parameters.put(Fascicolo.stato.columnName(), stato.name());
			separator = " and ";
		}
		if(ufficio!=null)
		{
			sql.append(separator)
			   .append(Fascicolo.ufficio.getCompleteName())
			   .append(" LIKE :")
			   .append(Fascicolo.ufficio.columnName());
			parameters.put(Fascicolo.ufficio.columnName(), StringUtil.convertRightLike(ufficio));
			separator = " and ";
		}
		if(orgCreazione!=null)
		{
			sql.append(separator)
			   .append(Fascicolo.org_creazione.getCompleteName())
			   .append(" = :")
			   .append(Fascicolo.org_creazione.columnName());
			parameters.put(Fascicolo.org_creazione.columnName(), orgCreazione);
			separator = " and ";
		}
		if (ricercaPubblica==true) {	// utente non loggato --> organizzazioneLoggato=null
			sql .append(" AND ( ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.TRANSMITTED.name()))
				.append(" or ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.SELECTED   .name()))
				.append(" or ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.FINISHED   .name()))
				.append(" or ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.ON_MODIFY  .name()))
				.append(" )");
			sql .append(" AND ( ")
				.append(Fascicolo.esito.getCompleteName()+" != "+Stringhe.apicizza(EsitoProvvedimento.NON_AUTORIZZATO.name()))
				.append(" or ")
				.append(Fascicolo.esito.getCompleteName()+" IS NULL ")
				.append(" )");
		}
		if(StringUtil.isNotEmpty(codiceTrasmissione))
		{
			sql.append(separator)
			   .append(Fascicolo.codice.getCompleteName())
			   .append(" = :codiceTrasmissione ");
			parameters.put("codiceTrasmissione", codiceTrasmissione);
			separator = " and ";
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// il resto della WHERE CLAUSE è stato inserito direttamente nella repository (problema con @Autowired nei DTO)
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//		if (!StringUtil.isEmpty(funzionario)) {
//			sql .append(" AND ")
//				.append(AssegnamentoFascicolo.username_funzionario.getCompleteName() + " = :usernameFunzionario");
//			parameters.put("usernameFunzionario", funzionario);
//		}
		
//		if(organizzazioneLoggato!=null) {
//			sql.append(separator)
//			   .append(" ( ")
//				   .append(" ( ")
//				   .append(Fascicolo.stato.getCompleteName())
//				   .append(" = ")
//				   .append("'"+StatoFascicolo.WORKING.name()+"'")
//				   .append(" and ")
//				   .append(Fascicolo.ufficio.getCompleteName())
//				   .append(" LIKE ")
//				   .append("'"+StringUtil.convertRightLike(organizzazioneLoggato)+"'")
//				   .append(" ) ")
//				   .append(" OR ")
//				   .append(" ( ")
//				   .append(Fascicolo.stato.getCompleteName())
//				   .append(" != ")
//				   .append("'"+StatoFascicolo.WORKING.name()+"'")
//				   .append(" ) ")
//			   .append(" ) ");
//		}
		
//		if(entiDiCompetenza!=null)
//		{
//			sql.append(" OR ( ")
//			   .append(Fascicolo.stato.getCompleteName())
//			   .append(" = ")
//			   .append("'"+StatoFascicolo.WORKING.name()+"'")
//			   .append(separator)
//			   .append(LocalizzazioneIntervento.comune_id.getCompleteName())
//			   .append(" IN (:entiDiCompetenza) ")
//			   .append(" )");
//			parameters.put("entiDiCompetenza", entiDiCompetenza);
//			separator = " and ";
//		}
		
//		if (ricercaPubblica==true) // se NON setto lo stato
//		{
//			sql.append(separator)
//			   .append(Fascicolo.stato.getCompleteName())
//			   .append(" != ")
//			   .append("'"+StatoFascicolo.WORKING.name()+"'");
//			separator = " and ";
//		}
		
// ..... and (
//			 	  (stato = S1 and vincolo)
//			   OR (stato = S1 and vincolo)
//			   OR (stato = S1 and vincolo)
//			   OR (stato = S1 and vincolo)
//			   OR (stato = S1 and vincolo)
//    		 )
	}



	public Integer getIdOrganizzazioneLoggato() {
		return idOrganizzazioneLoggato;
	}



	public void setIdOrganizzazioneLoggato(Integer idOrganizzazioneLoggato) {
		this.idOrganizzazioneLoggato = idOrganizzazioneLoggato;
	}



	public String getUsernameLoggato() {
		return usernameLoggato;
	}



	public void setUsernameLoggato(String usernameLoggato) {
		this.usernameLoggato = usernameLoggato;
	}



	public String getCodiceTrasmissione() {
		return codiceTrasmissione;
	}



	public void setCodiceTrasmissione(String codiceTrasmissione) {
		this.codiceTrasmissione = codiceTrasmissione;
	}
	
	
}