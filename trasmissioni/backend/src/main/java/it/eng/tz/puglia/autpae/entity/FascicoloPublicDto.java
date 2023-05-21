package it.eng.tz.puglia.autpae.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.StatoRegistrazione;
import it.eng.tz.puglia.autpae.enumeratori.TipoLocalizzazione;

/**
 * superclasse del FascicoloDTO con i soli dati per la parte pubblica, alcuni attributi sono nella vista v_fascicolo_public
 * @author acolaianni
 *
 */
public class FascicoloPublicDto extends FascicoloBaseDto {
	private Long id; // per lo sportello è -1
	private String codice; // in fase di creazione (auto-generato)
	private String ufficio; // è il gruppo creazione in formato "XXXX_id_Y"
	private Integer orgCreazione; // è l'id di "pae_org" del gruppo creazione
	private StatoFascicolo stato; // anche chiamato "stato pratica" in analisi
	//private String oggettoIntervento; // in fase di creazione
	@Size(max=100)
	private String codiceInternoEnte; // in fase di creazione //è il codice_pratica_appptr in caso di applicazione=pae_pres_ist
	@Size(max=100)
	private String numeroInternoEnte; // in fase di creazione
	@Size(max=255)
	private String protocollo; // internoEnte // in fase di creazione // non è il numero di protocollo della
								// ricevuta di trasmissione!
	private Date dataProtocollo;// internoEnte // in fase di creazione // non è la data di protocollo della
								// ricevuta di trasmissione!
	
	//private TipoProcedimento tipoProcedimento;
	@Size(max=255)
	private String rup; // riferito al Tab Provvedimento
	@NotNull()
	private EsitoProvvedimento esito; // riferito al Tab Provvedimento
	@Size(max=100)
	private String numeroProvvedimento; // riferito al Tab Provvedimento
	private Date dataRilascioAutorizzazione; // riferito al Tab Provvedimento // data provvedimento (conclusivo) ???????

	
	private Date esitoDataRilascioAutorizzazione; // riferito al Tab Esito [Verifica] ... attivo dopo il campionamento
	private EsitoVerifica esitoVerifica; // riferito al Tab Esito [Verifica] ... attivo dopo il campionamento
	
	//da qui in giu' non sono nella vista v_fascicolo_public
	private String esitoNumeroProvvedimento; // riferito al Tab Esito [Verifica] ... attivo dopo il campionamento
	private List<String> comuni; // Solo per il FE: lista dei nomi dei comuni attualmente associati
	private boolean hasShape;
	@NotNull
	private TipoLocalizzazione tipoSelezioneLocalizzazione;
	private StatoRegistrazione statoRegistrazione; // non c'è nel DB
	private String applicazione; //indica l'applicazione di provenienza dell'autorizzazione paesaggistica (autpae o pae_pres_ist)
	private String groupCanAccess; //indica eventuale gruppo (a cui utente loggato appartiene) che puo' accedere al dettaglio della pratica
		
	
	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
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

	public StatoFascicolo getStato() {
		return stato;
	}

	public void setStato(StatoFascicolo stato) {
		this.stato = stato;
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

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getRup() {
		return rup;
	}

	public void setRup(String rup) {
		this.rup = rup;
	}

	public EsitoProvvedimento getEsito() {
		return esito;
	}

	public void setEsito(EsitoProvvedimento esito) {
		this.esito = esito;
	}

	public String getEsitoNumeroProvvedimento() {
		return esitoNumeroProvvedimento;
	}

	public void setEsitoNumeroProvvedimento(String esitoNumeroProvvedimento) {
		this.esitoNumeroProvvedimento = esitoNumeroProvvedimento;
	}

	public Date getEsitoDataRilascioAutorizzazione() {
		return esitoDataRilascioAutorizzazione;
	}

	public void setEsitoDataRilascioAutorizzazione(Date esitoDataRilascioAutorizzazione) {
		this.esitoDataRilascioAutorizzazione = esitoDataRilascioAutorizzazione;
	}

	public EsitoVerifica getEsitoVerifica() {
		return esitoVerifica;
	}

	public void setEsitoVerifica(EsitoVerifica esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}

	public List<String> getComuni() {
		return comuni;
	}

	public void setComuni(List<String> comuni) {
		this.comuni = comuni;
	}

	public boolean isHasShape() {
		return hasShape;
	}

	public void setHasShape(boolean hasShape) {
		this.hasShape = hasShape;
	}

	public TipoLocalizzazione getTipoSelezioneLocalizzazione() {
		return tipoSelezioneLocalizzazione;
	}

	public void setTipoSelezioneLocalizzazione(TipoLocalizzazione tipoSelezioneLocalizzazione) {
		this.tipoSelezioneLocalizzazione = tipoSelezioneLocalizzazione;
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

	public Date getDataRilascioAutorizzazione() {
		return dataRilascioAutorizzazione;
	}

	public void setDataRilascioAutorizzazione(Date dataRilascioAutorizzazione) {
		this.dataRilascioAutorizzazione = dataRilascioAutorizzazione;
	}

	public StatoRegistrazione getStatoRegistrazione() {
		return statoRegistrazione;
	}

	public void setStatoRegistrazione(StatoRegistrazione statoRegistrazione) {
		this.statoRegistrazione = statoRegistrazione;
	}

	static public StatoRegistrazione calcolaStatoRegistrazione(StatoFascicolo sf) {
		if (sf == StatoFascicolo.FINISHED)
			return StatoRegistrazione.FINISHED;
		else if (sf == StatoFascicolo.CANCELLED)
			return StatoRegistrazione.CANCELLED;
		else if (sf == StatoFascicolo.ON_MODIFY)
			return StatoRegistrazione.ON_MODIFY;
		else if (sf == StatoFascicolo.SELECTED)
			return StatoRegistrazione.SELECTED;
		else if (sf == StatoFascicolo.TRANSMITTED)
			return StatoRegistrazione.SELECTED;
		else
			return null;
	}

	public String getGroupCanAccess() {
		return groupCanAccess;
	}

	public void setGroupCanAccess(String groupCanAccess) {
		this.groupCanAccess = groupCanAccess;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
	
}
