package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pratica
 */
public class PraticaSearch extends BaseSearchRequestBean
{
	private static final long serialVersionUID = 3536964320L;
	// COLUMN id
	private UUID id;
	// COLUMN codice_pratica_appptr
	private String codicePraticaAppptr;
	// COLUMN ente_delegato
	private String enteDelegato;
	// COLUMN in_sanatoria
	private String inSanatoria;
	// COLUMN tipo_procedimento
	private String tipoProcedimento;
	// COLUMN stato
	private String stato;
	// COLUMN data_creazione
	private String dataCreazione;
	// COLUMN oggetto
	private String oggetto;
	// COLUMN user_id
	private String userId;
	// COLUMN data_modifica
	private String dataModifica;

	private Boolean inIstruttoria;
	private Long idSeduta;
	private Boolean esaminato;
	
	@JsonIgnore
	private String username;
	private Boolean editable;
	
	private String codiceFiscale;
	
	private Boolean escludiAccertamento;
	
	//Filtri per permettere la rierca delle pratiche associabili ad una deterinata seduta
	@JsonIgnore
	private Boolean praticheSeduta;
	@JsonIgnore
	private Date rangeRicercaDa;
	@JsonIgnore
	private Date rangeRicercaA;
	
	//aggiunti per effettuare filtro a seconda dell'organizzazione e ruolo chiamante...
	//non utilizzabili da FE....
	@JsonIgnore
	boolean ricercaPubblica;
	@JsonIgnore
	private List<Integer> comuniIntervento;
	@JsonIgnore
	private List<AttivitaDaEspletare> statiAmmessi; 
	private String userAssegnatario;
	@JsonIgnore
	private Integer organizzazioneAutenticata;
	@JsonIgnore
	private List<Integer> entiDelegatiRiferimento; //utilizzato per le Commissioni locali per ricercare su n enti delegati a cui possono fare riferimento...
	@JsonIgnore
	private Boolean nonAssegnate; //serve solo per il counter delle inassegnate....
	
	private Long comune;
	// COLUMN like codice_pratica_appptr 
	private String likeCodicePraticaAppptr;
	//-----------------------------------------
	// COLUMN owner
	private String owner;

	private List<String> codiciTrasmissione; //serve per calcolare visibilità di accesso al dettaglio
	
	public PraticaSearch()
	{
		super();
	}
	
	public PraticaSearch(TabelleAssegnamentoFascicoliSearch other)
	{
		setPage(other.getPage());
		setLimit(other.getLimit());
		nonAssegnate = !other.isGiaAssegnato();
		codicePraticaAppptr = other.getCodice();
	}
	
	public boolean isRicercaPubblica() {
		return ricercaPubblica;
	}
	public Boolean getNonAssegnate() {
		return nonAssegnate;
	}
	public void setNonAssegnate(Boolean nonAssegnate) {
		this.nonAssegnate = nonAssegnate;
	}
	public List<Integer> getEntiDelegatiRiferimento() {
		return entiDelegatiRiferimento;
	}
	public void setEntiDelegatiRiferimento(List<Integer> entiDelegatiRiferimento) {
		this.entiDelegatiRiferimento = entiDelegatiRiferimento;
	}
	public Integer getOrganizzazioneAutenticata() {
		return organizzazioneAutenticata;
	}
	public void setOrganizzazioneAutenticata(Integer organizzazioneAutenticata) {
		this.organizzazioneAutenticata = organizzazioneAutenticata;
	}
	public List<Integer> getComuniIntervento() {
		return comuniIntervento;
	}
	public void setComuniIntervento(List<Integer> comuniIntervento) {
		this.comuniIntervento = comuniIntervento;
	}
	public List<AttivitaDaEspletare> getStatiAmmessi() {
		return statiAmmessi;
	}
	public void setStatiAmmessi(List<AttivitaDaEspletare> statiAmmessi) {
		this.statiAmmessi = statiAmmessi;
	}
	public String getUserAssegnatario() {
		return userAssegnatario;
	}
	public void setUserAssegnatario(String userAssegnatario) {
		this.userAssegnatario = userAssegnatario;
	}
	public void setRicercaPubblica(boolean ricercaPubblica) {
		this.ricercaPubblica = ricercaPubblica;
	}
	public UUID getId()
	{
		return this.id;
	}
	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getCodicePraticaAppptr()
	{
		return this.codicePraticaAppptr;
	}
	public void setCodicePraticaAppptr(String codicePraticaAppptr)
	{
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getEnteDelegato()
	{
		return this.enteDelegato;
	}
	public void setEnteDelegato(String enteDelegato)
	{
		this.enteDelegato = enteDelegato;
	}

	public String getInSanatoria()
	{
		return this.inSanatoria;
	}
	public void setInSanatoria(String inSanatoria)
	{
		this.inSanatoria = inSanatoria;
	}

	public String getTipoProcedimento()
	{
		return this.tipoProcedimento;
	}
	public void setTipoProcedimento(String tipoProcedimento)
	{
		this.tipoProcedimento = tipoProcedimento;
	}

	@JsonGetter("attivitaDaEspletare")
	public String getStato()
	{
		return this.stato;
	}
	@JsonSetter("attivitaDaEspletare")
	public void setStato(String stato)
	{
		this.stato = stato;
	}

	public String getDataCreazione()
	{
		return this.dataCreazione;
	}
	public void setDataCreazione(String dataCreazione)
	{
		this.dataCreazione = dataCreazione;
	}

	public String getOggetto()
	{
		return this.oggetto;
	}
	public void setOggetto(String oggetto)
	{
		this.oggetto = oggetto;
	}

	public String getUserId()
	{
		return this.userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getDataModifica()
	{
		return this.dataModifica;
	}
	public void setDataModifica(String dataModifica)
	{
		this.dataModifica = dataModifica;
	}

	public Boolean getInIstruttoria()
	{
		return inIstruttoria;
	}
	public void setInIstruttoria(Boolean inIstruttoria)
	{
		this.inIstruttoria = inIstruttoria;
	}
	public Long getIdSeduta()
	{
		return idSeduta;
	}
	public void setIdSeduta(Long idSeduta)
	{
		this.idSeduta = idSeduta;
	}
	
	public Boolean getEsaminato()
	{
		return esaminato;
	}
	public void setEsaminato(Boolean esaminato)
	{
		this.esaminato = esaminato;
	}
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public Boolean getEditable()
	{
		return editable;
	}
	public void setEditable(Boolean editable)
	{
		this.editable = editable;
	}
	
	public Boolean getPraticheSeduta()
	{
		return praticheSeduta;
	}
	public void setPraticheSeduta(Boolean praticheSeduta)
	{
		this.praticheSeduta = praticheSeduta;
	}
	
	public Date getRangeRicercaDa()
	{
		return rangeRicercaDa;
	}
	public void setRangeRicercaDa(Date rangeRicercaDa)
	{
		this.rangeRicercaDa = rangeRicercaDa;
	}
	
	public Date getRangeRicercaA()
	{
		return rangeRicercaA;
	}
	public void setRangeRicercaA(Date rangeRicercaA)
	{
		this.rangeRicercaA = rangeRicercaA;
	}
	
	public Boolean getEscludiAccertamento()
	{
		return escludiAccertamento;
	}
	public void setEscludiAccertamento(Boolean escludiAccertamento)
	{
		this.escludiAccertamento = escludiAccertamento;
	}
	public Long getComune()
	{
		return comune;
	}
	public void setComune(Long comune)
	{
		this.comune = comune;
	}

	public String getLikeCodicePraticaAppptr() {
		return likeCodicePraticaAppptr;
	}

	public void setLikeCodicePraticaAppptr(String likeCodicePraticaAppptr) {
		this.likeCodicePraticaAppptr = likeCodicePraticaAppptr;
	}

	public List<String> getCodiciTrasmissione() {
		return codiciTrasmissione;
	}

	public void setCodiciTrasmissione(List<String> codiciTrasmissione) {
		this.codiciTrasmissione = codiciTrasmissione;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getOwner()
	{
		return this.owner;
	}
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	

}
