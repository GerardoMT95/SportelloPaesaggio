package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.IstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.SchedaTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.IstrPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.RichiestaAsrDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.RuoloPraticaEnum;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoAttivita;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;
import it.eng.tz.puglia.util.list.ListUtil;

public class FascicoloDto implements Serializable
{
	private static final long serialVersionUID = -5003922262126468740L;
	// COLUMN id
	@NotNull
	private UUID id;
	// COLUMN codice_pratica_appptr
	@NotNull
	@NotBlank
	private String codicePraticaAppptr;
	// COLUMN ente_delegato
	@NotNull
	@NotBlank
	private String enteDelegato;

	// COLUMN in_sanatoria
	private Boolean isInSanatoria;
	// COLUMN tipo_procedimento
	@NotNull(message = "Il campo 'tipo procedimento' deve avere un valore compreso fra 1 e 6 non può essere nullo")
	@Min(1)
	@Max(6)
	private int tipoProcedimento;
	// COLUMN stato
	private AttivitaDaEspletare attivitaDaEspletare;
	// COLUMN data_creazione
	@NotNull
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date dataCreazione;
	// COLUMN oggetto
	@NotNull(message = "Il campo oggetto non può essere lasciato vuoto")
	@NotBlank(message = "Il campo oggetto non può essere lasciato vuoto")
	private String oggetto;
	// COLUMN user_id
	private String userId;
	// COLUMN data_modifica
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date dataModifica;
	private Integer privacyId;
	private String privacyText;
	@NotNull
	private IstanzaDto istanza;
	@NotNull
	private SchedaTecnicaDto schedaTecnica;
	@NotNull
	private AllegatiDto allegati;

	private String numeroProtocollo;
	private Timestamp dataProtocollo;
//	private Date dataProtocollo;

	private Timestamp dataPresentazione;
	private Timestamp dataTrasmissioneProvvedimentoFinale;
//	private Date dataPresentazione;
//	private Date dataTrasmissioneProvvedimentoFinale;

	@JsonProperty(access = Access.READ_ONLY)
	private Boolean validatoIstnza;
	@JsonProperty(access = Access.READ_ONLY)
	private Boolean validatoSchedaTecnica;
	@JsonProperty(access = Access.READ_ONLY)
	private Boolean validatoAllegati;
	@JsonProperty(access = Access.READ_ONLY)
	private Boolean validatoRichiedente;
	// solo da BE verso FE
	private List<ComuniCompetenzaDTO> comuniCompetenza;

	// COLUMN tipo_selezione_localizzazione
	private String tipoSelezioneLocalizzazione;
	// COLUMN has_shape
	private Boolean hasShape;
	// sono istanza e scheda tecnica e vengono popolati solo se stato non
	// COMPILA_DOMANDA
	private List<AllegatiDTO> documentiSottoscritti;

	private StatoSeduta statoSedutaCommissione;
	private StatoRelazione statoRelazioneEnte;
	private StatoParere statoParereSoprintendenza;
	private StatoAttivita statoAttivita;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<RichiestaAsrDTO> storicoASR;

	// COLUMN ultimo_stato_valido
	private AttivitaDaEspletare ultimoStatoValido;

	private Boolean rup; // indica se io come funzionario sono il rup sulla pratica... (ex // IstrPraticaDto)
	private Boolean showVCL; //indica se va mostrato il verbale di commissione locale 

	// COLUMN data_inizio_lavorazione
	private Timestamp dataInizioLavorazione;
	// COLUMN data_trasmissione_verbale
	private Timestamp dataTrasmissioneVerbale;
	// COLUMN data_trasmissione_relazione
	private Timestamp dataTrasmissioneRelazione;
	// COLUMN data_trasmissione_parere
	private Timestamp dataTrasmissioneParere;
	//COLUMN esonero_oneri
	private Boolean esoneroOneri;
	//COLUMN esonero_bollo
	private Boolean esoneroBollo;
	
	private String denominazioneRup; 
	private String usernameRup;
	private String denominazioneFunzionario; 
	private String usernameFunzionario;
	private Map<String,BigDecimal> totPagamenti;
	
	//verso FE in caso di elenco per ETI o SBAP
	private String descrEnteDelegato;

	
	//solo verso Fe per il datatable in instruttoria
	private List<String> descrComuniIntervento;
	
	//solo verso fe per attivare azione di assegnazione
	private String usernameAssegnatarioOrganizzazione;
	private String denominazioneAssegnatarioOrganizzazione;
	
	private String ruoloPratica;
	private String owner;
	private boolean isCurrentUserOwner;
	private boolean isCurrentUserRichiedente;
	private String codiceSegreto;
	private String codiceTrasmissione;
	
	public List<String> getDescrComuniIntervento() {
		return descrComuniIntervento;
	}
	public void setDescrComuniIntervento(List<String> descrComuniIntervento) {
		this.descrComuniIntervento = descrComuniIntervento;
	}
	public StatoAttivita getStatoAttivita() {
		return statoAttivita;
	}
	public void setStatoAttivita(StatoAttivita statoAttivita) {
		this.statoAttivita = statoAttivita;
	}
	public Map<String, BigDecimal> getTotPagamenti() {
		return totPagamenti;
	}
	public void setTotPagamenti(Map<String, BigDecimal> totPagamenti) {
		this.totPagamenti = totPagamenti;
	}
	public String getDenominazioneRup() {
		return denominazioneRup;
	}
	public void setDenominazioneRup(String denominazioneRup) {
		this.denominazioneRup = denominazioneRup;
	}
	public String getUsernameRup() {
		return usernameRup;
	}
	public void setUsernameRup(String usernameRup) {
		this.usernameRup = usernameRup;
	}
	public String getDenominazioneFunzionario() {
		return denominazioneFunzionario;
	}
	public void setDenominazioneFunzionario(String denominazioneFunzionario) {
		this.denominazioneFunzionario = denominazioneFunzionario;
	}
	public String getUsernameFunzionario() {
		return usernameFunzionario;
	}
	public void setUsernameFunzionario(String usernameFunzionario) {
		this.usernameFunzionario = usernameFunzionario;
	}
	public AttivitaDaEspletare getUltimoStatoValido()
	{
		return ultimoStatoValido;
	}
	public void setUltimoStatoValido(AttivitaDaEspletare ultimoStatoValido)
	{
		this.ultimoStatoValido = ultimoStatoValido;
	}

	public List<AllegatiDTO> getDocumentiSottoscritti()
	{
		return documentiSottoscritti;
	}
	public void setDocumentiSottoscritti(List<AllegatiDTO> documentiSottoscritti)
	{
		this.documentiSottoscritti = documentiSottoscritti;
	}

	public String getPrivacyText()
	{
		return privacyText;
	}
	public void setPrivacyText(String privacyText)
	{
		this.privacyText = privacyText;
	}

	public Boolean getRup()
	{
		return rup;
	}
	public void setRup(Boolean rup)
	{
		this.rup = rup;
	}

	public Boolean getShowVCL()
	{
		return showVCL;
	}
	public void setShowVCL(Boolean showVCL)
	{
		this.showVCL = showVCL;
	}
	
	public Timestamp getDataPresentazione()
	{
		return dataPresentazione;
	}
	public void setDataPresentazione(Timestamp dataPresentazione)
	{
		this.dataPresentazione = dataPresentazione;
	}

	public Timestamp getDataTrasmissioneProvvedimentoFinale()
	{
		return dataTrasmissioneProvvedimentoFinale;
	}
	public void setDataTrasmissioneProvvedimentoFinale(Timestamp dataTrasmissioneProvvedimentoFinale)
	{
		this.dataTrasmissioneProvvedimentoFinale = dataTrasmissioneProvvedimentoFinale;
	}
//	public Date getDataPresentazione()
//	{
//		return dataPresentazione;
//	}
//	public void setDataPresentazione(Date dataPresentazione)
//	{
//		this.dataPresentazione = dataPresentazione;
//	}
//	
//	public Date getDataTrasmissioneProvvedimentoFinale()
//	{
//		return dataTrasmissioneProvvedimentoFinale;
//	}
//	public void setDataTrasmissioneProvvedimentoFinale(Date dataTrasmissioneProvvedimentoFinale)
//	{
//		this.dataTrasmissioneProvvedimentoFinale = dataTrasmissioneProvvedimentoFinale;
//	}

	public Boolean getHasShape()
	{
		return hasShape;
	}
	public void setHasShape(Boolean hasShape)
	{
		this.hasShape = hasShape;
	}

	public String getTipoSelezioneLocalizzazione()
	{
		return tipoSelezioneLocalizzazione;
	}
	public void setTipoSelezioneLocalizzazione(String tipoSelezioneLocalizzazione)
	{
		this.tipoSelezioneLocalizzazione = tipoSelezioneLocalizzazione;
	}

	public Boolean getIsInSanatoria()
	{
		return isInSanatoria;
	}
	public void setIsInSanatoria(Boolean isInSanatoria)
	{
		this.isInSanatoria = isInSanatoria;
	}

	public AttivitaDaEspletare getAttivitaDaEspletare()
	{
		return attivitaDaEspletare;
	}
	public void setAttivitaDaEspletare(AttivitaDaEspletare attivitaDaEspletare)
	{
		this.attivitaDaEspletare = attivitaDaEspletare;
	}

	public List<ComuniCompetenzaDTO> getComuniCompetenza()
	{
		return comuniCompetenza;
	}
	public void setComuniCompetenza(List<ComuniCompetenzaDTO> comuniCompetenza)
	{
		this.comuniCompetenza = comuniCompetenza;
	}

	public UUID getId()
	{
		return id;
	}
	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getCodicePraticaAppptr()
	{
		return codicePraticaAppptr;
	}
	public void setCodicePraticaAppptr(String codicePraticaAppptr)
	{
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getEnteDelegato()
	{
		return enteDelegato;
	}
	public void setEnteDelegato(String enteDelegato)
	{
		this.enteDelegato = enteDelegato;
	}

	public int getTipoProcedimento()
	{
		return tipoProcedimento;
	}
	public void setTipoProcedimento(int tipoProcedimento)
	{
		this.tipoProcedimento = tipoProcedimento;
	}

	public Date getDataCreazione()
	{
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione)
	{
		this.dataCreazione = dataCreazione;
	}

	public String getOggetto()
	{
		return oggetto;
	}
	public void setOggetto(String oggetto)
	{
		this.oggetto = oggetto;
	}

	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Date getDataModifica()
	{
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica)
	{
		this.dataModifica = dataModifica;
	}

	public Boolean getValidatoIstnza()
	{
		return validatoIstnza;
	}
	public void setValidatoIstnza(Boolean validatoIstnza)
	{
		this.validatoIstnza = validatoIstnza;
	}

	public Boolean getValidatoSchedaTecnica()
	{
		return validatoSchedaTecnica;
	}
	public void setValidatoSchedaTecnica(Boolean validatoSchedaTecnica)
	{
		this.validatoSchedaTecnica = validatoSchedaTecnica;
	}

	public Boolean getValidatoAllegati()
	{
		return validatoAllegati;
	}
	public void setValidatoAllegati(Boolean validatoAllegati)
	{
		this.validatoAllegati = validatoAllegati;
	}

	public Boolean getValidatoRichiedente()
	{
	    return validatoRichiedente;
	}
	public void setValidatoRichiedente(Boolean validatoRichiedente)
	{
	    this.validatoRichiedente = validatoRichiedente;
	}
	
	public String getNumeroProtocollo()
	{
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo)
	{
		this.numeroProtocollo = numeroProtocollo;
	}

	public Timestamp getDataProtocollo()
	{
		return dataProtocollo;
	}
	public void setDataProtocollo(Timestamp dataProtocollo)
	{
		this.dataProtocollo = dataProtocollo;
	}
//	public Date getDataProtocollo()
//	{
//		return dataProtocollo;
//	}
//	public void setDataProtocollo(Date dataProtocollo)
//	{
//		this.dataProtocollo = dataProtocollo;
//	}

	public IstanzaDto getIstanza()
	{
		return istanza;
	}
	public void setIstanza(IstanzaDto istanza)
	{
		this.istanza = istanza;
	}

	public SchedaTecnicaDto getSchedaTecnica()
	{
		return schedaTecnica;
	}
	public void setSchedaTecnica(SchedaTecnicaDto schedaTecnica)
	{
		this.schedaTecnica = schedaTecnica;
	}

	public Integer getPrivacyId()
	{
		return privacyId;
	}
	public void setPrivacyId(Integer privacyId)
	{
		this.privacyId = privacyId;
	}

	public AllegatiDto getAllegati()
	{
		return allegati;
	}
	public void setAllegati(AllegatiDto allegati)
	{
		this.allegati = allegati;
	}

	public StatoSeduta getStatoSedutaCommissione()
	{
		return statoSedutaCommissione;
	}
	public void setStatoSedutaCommissione(StatoSeduta statoSedutaCommissione)
	{
		this.statoSedutaCommissione = statoSedutaCommissione;
	}

	public StatoRelazione getStatoRelazioneEnte()
	{
		return statoRelazioneEnte;
	}
	public void setStatoRelazioneEnte(StatoRelazione statoRelazioneEnte)
	{
		this.statoRelazioneEnte = statoRelazioneEnte;
	}
	
	public StatoParere getStatoParereSoprintendenza()
	{
		return statoParereSoprintendenza;
	}
	public void setStatoParereSoprintendenza(StatoParere statoParereSoprintendenza)
	{
		this.statoParereSoprintendenza = statoParereSoprintendenza;
	}

	public List<RichiestaAsrDTO> getStoricoASR()
	{
		return storicoASR;
	}
	public void setStoricoASR(List<RichiestaAsrDTO> storicoSAR)
	{
		this.storicoASR = storicoSAR;
	}

	public Timestamp getDataInizioLavorazione()
	{
		return dataInizioLavorazione;
	}
	public void setDataInizioLavorazione(Timestamp dataInizioLavorazione)
	{
		this.dataInizioLavorazione = dataInizioLavorazione;
	}

	public Timestamp getDataTrasmissioneVerbale()
	{
		return dataTrasmissioneVerbale;
	}
	public void setDataTrasmissioneVerbale(Timestamp dataTrasmissioneVerbale)
	{
		this.dataTrasmissioneVerbale = dataTrasmissioneVerbale;
	}

	public Timestamp getDataTrasmissioneRelazione()
	{
		return dataTrasmissioneRelazione;
	}
	public void setDataTrasmissioneRelazione(Timestamp dataTrasmissioneRelazione)
	{
		this.dataTrasmissioneRelazione = dataTrasmissioneRelazione;
	}

	public Timestamp getDataTrasmissioneParere()
	{
		return dataTrasmissioneParere;
	}
	public void setDataTrasmissioneParere(Timestamp dataTrasmissioneParere)
	{
		this.dataTrasmissioneParere = dataTrasmissioneParere;
	}
	public String getCodiceTrasmissione() {
		return codiceTrasmissione;
	}
	public void setCodiceTrasmissione(String codiceTrasmissione) {
		this.codiceTrasmissione = codiceTrasmissione;
	}
//	public Date getDataInizioLavorazione()
//	{
//		return dataInizioLavorazione;
//	}
//	public void setDataInizioLavorazione(Date dataInizioLavorazione)
//	{
//		this.dataInizioLavorazione = dataInizioLavorazione;
//	}
//	
//	public Date getDataTrasmissioneVerbale()
//	{
//		return dataTrasmissioneVerbale;
//	}
//	public void setDataTrasmissioneVerbale(Date dataTrasmissioneVerbale)
//	{
//		this.dataTrasmissioneVerbale = dataTrasmissioneVerbale;
//	}
//	
//	public Date getDataTrasmissioneRelazione()
//	{
//		return dataTrasmissioneRelazione;
//	}
//	public void setDataTrasmissioneRelazione(Date dataTrasmissioneRelazione)
//	{
//		this.dataTrasmissioneRelazione = dataTrasmissioneRelazione;
//	}
//	
//	public Date getDataTrasmissioneParere()
//	{
//		return dataTrasmissioneParere;
//	}
//	public void setDataTrasmissioneParere(Date dataTrasmissioneParere)
//	{
//		this.dataTrasmissioneParere = dataTrasmissioneParere;
//	}

	public void setFascicoloByPratica(PraticaDTO praticaDTO)
	{
		this.id = praticaDTO.getId();
		this.codicePraticaAppptr = praticaDTO.getCodicePraticaAppptr();
		this.enteDelegato = praticaDTO.getEnteDelegato();
		this.isInSanatoria = praticaDTO.getInSanatoria();
		this.tipoProcedimento = praticaDTO.getTipoProcedimento();
		this.attivitaDaEspletare = praticaDTO.getStato();
		this.dataCreazione = praticaDTO.getDataCreazione();
		this.oggetto = praticaDTO.getOggetto();
		this.userId = praticaDTO.getUserId();
		this.dataModifica = praticaDTO.getDataModifica();
		this.privacyId = praticaDTO.getPrivacyId();
		this.validatoIstnza = praticaDTO.getValidazioneIstanza();
		this.validatoSchedaTecnica = praticaDTO.getValidazioneSchedaTecnica();
		this.validatoAllegati = praticaDTO.getValidazioneAllegati();
		this.validatoRichiedente = praticaDTO.getValidazioneRichiedente();
		this.tipoSelezioneLocalizzazione = praticaDTO.getTipoSelezioneLocalizzazione();
		this.numeroProtocollo = praticaDTO.getNumeroProtocollo();
		this.dataProtocollo = praticaDTO.getDataProtocollo();
		this.dataPresentazione = praticaDTO.getDataPresentazione();
		this.dataTrasmissioneProvvedimentoFinale = praticaDTO.getDataTrasmissioneProvvedimentoFinale();
		this.statoParereSoprintendenza = praticaDTO.getStatoParereSoprintendenza();
		this.statoRelazioneEnte = praticaDTO.getStatoRelazioneEnte();
		this.statoSedutaCommissione = praticaDTO.getStatoSedutaCommissione();
		this.setDataInizioLavorazione(praticaDTO.getDataInizioLavorazione());
		this.setDataTrasmissioneVerbale(praticaDTO.getDataTrasmissioneVerbale());
		this.setDataTrasmissioneRelazione(praticaDTO.getDataTrasmissioneRelazione());
		this.setDataTrasmissioneParere(praticaDTO.getDataTrasmissioneParere());
		this.setEsoneroBollo(praticaDTO.getEsoneroBollo());
		this.setEsoneroOneri(praticaDTO.getEsoneroOneri());
		this.setRuoloPratica(praticaDTO.getRuoloPratica());
		this.setOwner(praticaDTO.getOwner());
		this.setCodiceTrasmissione(praticaDTO.getCodiceTrasmissione());
		this.codiceSegreto = praticaDTO.getCodiceSegreto();
	}
	
	public void setFascicoloByIstrPratica(IstrPraticaDTO praticaDTO)
	{
		this.setFascicoloByPratica(praticaDTO);
		this.setRup(praticaDTO.getRup());
		this.setDenominazioneRup(praticaDTO.getDenominazioneRup()); 
		this.setUsernameRup(praticaDTO.getUsernameRup());
		this.setDenominazioneFunzionario(praticaDTO.getDenominazioneFunzionario()); 
		this.setUsernameFunzionario(praticaDTO.getUsernameFunzionario());
		this.setUsernameAssegnatarioOrganizzazione(praticaDTO.getUsernameAssegnatarioOrganizzazione());
		this.setDenominazioneAssegnatarioOrganizzazione(praticaDTO.getDenominazioneAssegnatarioOrganizzazione());
	}

	@JsonIgnore
	public PraticaDTO getPraticaByFascicolo()
	{
		PraticaDTO praticaDTO = new PraticaDTO();
		praticaDTO.setId(this.id);
		praticaDTO.setCodicePraticaAppptr(this.codicePraticaAppptr);
		praticaDTO.setEnteDelegato(this.enteDelegato);
		praticaDTO.setInSanatoria(this.isInSanatoria);
		praticaDTO.setTipoProcedimento(this.tipoProcedimento);
		praticaDTO.setStato(this.attivitaDaEspletare);
		praticaDTO.setDataCreazione(this.dataCreazione);
		praticaDTO.setOggetto(this.oggetto);
		praticaDTO.setUserId(this.userId);
		praticaDTO.setDataModifica(this.dataModifica);
		praticaDTO.setPrivacyId(this.privacyId);
		praticaDTO.setTipoSelezioneLocalizzazione(this.tipoSelezioneLocalizzazione);
		praticaDTO.setHasShape(this.hasShape);
		praticaDTO.setNumeroProtocollo(this.numeroProtocollo);
		praticaDTO.setDataProtocollo(this.dataProtocollo);
		praticaDTO.setDataPresentazione(this.dataPresentazione);
		praticaDTO.setDataTrasmissioneProvvedimentoFinale(this.dataTrasmissioneProvvedimentoFinale);
		praticaDTO.setStatoParereSoprintendenza(statoParereSoprintendenza);
		praticaDTO.setStatoRelazioneEnte(statoRelazioneEnte);
		praticaDTO.setStatoSedutaCommissione(statoSedutaCommissione);
		praticaDTO.setDataInizioLavorazione(dataInizioLavorazione);
		praticaDTO.setDataTrasmissioneVerbale(dataTrasmissioneVerbale);
		praticaDTO.setDataTrasmissioneRelazione(dataTrasmissioneRelazione);
		praticaDTO.setDataTrasmissioneParere(dataTrasmissioneParere);
		praticaDTO.setRuoloPratica(ruoloPratica);
		praticaDTO.setCodiceTrasmissione(codiceTrasmissione);

		return praticaDTO;
	}
	/**
	 * @return the esoneroOneri
	 */
	public Boolean getEsoneroOneri() {
		return esoneroOneri;
	}
	/**
	 * @param esoneroOneri the esoneroOneri to set
	 */
	public void setEsoneroOneri(Boolean esoneroOneri) {
		this.esoneroOneri = esoneroOneri;
	}
	/**
	 * @return the esoneroBollo
	 */
	public Boolean getEsoneroBollo() {
		return esoneroBollo;
	}
	/**
	 * @param esoneroBollo the esoneroBollo to set
	 */
	public void setEsoneroBollo(Boolean esoneroBollo) {
		this.esoneroBollo = esoneroBollo;
	}
	/**
	 * @return the descrEnteDelegato
	 */
	public String getDescrEnteDelegato() {
		return descrEnteDelegato;
	}
	/**
	 * @param descrEnteDelegato the descrEnteDelegato to set
	 */
	public void setDescrEnteDelegato(String descrEnteDelegato) {
		this.descrEnteDelegato = descrEnteDelegato;
	}
	/**
	 * @return the usernameAssegnatarioOrganizzazione
	 */
	public String getUsernameAssegnatarioOrganizzazione() {
		return usernameAssegnatarioOrganizzazione;
	}
	/**
	 * @param usernameAssegnatarioOrganizzazione the usernameAssegnatarioOrganizzazione to set
	 */
	public void setUsernameAssegnatarioOrganizzazione(String usernameAssegnatarioOrganizzazione) {
		this.usernameAssegnatarioOrganizzazione = usernameAssegnatarioOrganizzazione;
	}
	/**
	 * @return the denominazioneAssegnatarioOrganizzazione
	 */
	public String getDenominazioneAssegnatarioOrganizzazione() {
		return denominazioneAssegnatarioOrganizzazione;
	}
	/**
	 * @param denominazioneAssegnatarioOrganizzazione the denominazioneAssegnatarioOrganizzazione to set
	 */
	public void setDenominazioneAssegnatarioOrganizzazione(String denominazioneAssegnatarioOrganizzazione) {
		this.denominazioneAssegnatarioOrganizzazione = denominazioneAssegnatarioOrganizzazione;
	}
	
	public String getRuoloPratica()
	{
	    return ruoloPratica;
	}
	public void setRuoloPratica(String ruoloPratica)
	{
	    this.ruoloPratica = ruoloPratica;
	}
	
	public boolean isCurrentUserOwner() {
		return isCurrentUserOwner;
	}
	public void setCurrentUserOwner(boolean isCurrentUserOwner) {
		this.isCurrentUserOwner = isCurrentUserOwner;
	}
	
	public boolean isCurrentUserRichiedente() {
		return isCurrentUserRichiedente;
	}
	public void setCurrentUserRichiedente(boolean isCurrentUserRichiedente) {
		this.isCurrentUserRichiedente = isCurrentUserRichiedente;
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * restituisce il primo delegato corrente attivo se ruoloPratica=DE 
	 * @author acolaianni
	 *
	 * @return
	 */
	@JsonIgnore
	public PraticaDelegatoDTO getDelegatoCorrente() {
		PraticaDelegatoDTO ret=null;
		if(this.getRuoloPratica().equals(RuoloPraticaEnum.DELEGATO.getCodice()) && 
				ListUtil.isNotEmpty(this.getIstanza().getDelegatoPratica())) {
			for(PraticaDelegatoDTO delegato:this.getIstanza().getDelegatoPratica()) {
				if(delegato.getDelgatoCorrente()!=null && delegato.getDelgatoCorrente()==true) {
					ret=delegato;
					break;
				}
			}
		}
		return ret;
	}
	public String getCodiceSegreto()
	{
	    return codiceSegreto;
	}
	public void setCodiceSegreto(String codiceSegreto)
	{
	    this.codiceSegreto = codiceSegreto;
	}
	

}
