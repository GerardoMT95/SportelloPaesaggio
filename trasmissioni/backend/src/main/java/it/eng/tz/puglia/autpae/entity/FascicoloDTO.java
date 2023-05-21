package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import it.eng.tz.puglia.autpae.dto.FascicoloTabDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;

public class FascicoloDTO  extends FascicoloPublicDto implements Serializable {
	
	private static final long serialVersionUID = 7545873978918968471L;
	
	private Boolean sanatoria;
	private StatoFascicolo statoPrecedente;
	@Size(max=500)
	private String interventoDettaglio; // campo "dettagliare" del Tab Intervento // anche per PARERI
	@Size(max=500)
	private String interventoSpecifica; // campo "specificare" del Tab Intervento
	private Date dataDelibera; // PARERI e PUTT --> in quest'ultimo caso è chiamata "Data Attivazione" o "Data di Presentazione"
	@Size(max=100)
	private String deliberaNum; // PARERI
	@Size(max=1000)
	private String oggettoDelibera; // PARERI
	@Size(max=1000)
	private String infoDeliberePrecedenti; // PARERI
	@Size(max=1000)
	private String descrizioneIntervento; // PARERI
												// no
	private EsitoVerifica esitoVerificaPrecedente; // riferito al Tab Esito [Verifica] ... attivo dopo il campionamento
	private String note; // in fase di creazione
	
	private int versFascicolo;
	private int versRichiedente;
	private int versIntervento;
	private int versLocalizzazione;
	private int versAllegati;
	private int versProvvedimento;

	private Date dataCreazione;
	private Date dataUltimaModifica;
	private Date dataTrasmissione;
	private Date dataCampionamento;
	private Date dataVerifica;
	private String usernameUtenteCreazione;
	private String usernameUtenteUltimaModifica;
	private String usernameUtenteTrasmissione;
	private String denominazioneUtenteTrasmissione;
	private String emailUtenteTrasmissione;

	private InformazioniDTO infoComplete; // campo json che fotografa il fascicolo al momento della TRASMISSIONE
	private boolean deleted;
	private Date modificabileFino;
	private String codicePraticaAppptr;
	private Long tPraticaId;
	private boolean statoTrasmissione;

	
	public FascicoloDTO() {
	}

	public FascicoloDTO(FascicoloBaseDto fascicoloBaseDTO) {
		this.setTipoProcedimento(fascicoloBaseDTO.getTipoProcedimento());
		this.setOggettoIntervento(fascicoloBaseDTO.getOggettoIntervento());
	}
	
	public FascicoloDTO(FascicoloDTO fascicoloDTO) {
		this.setId(fascicoloDTO.getId());
		this.setUfficio(fascicoloDTO.getUfficio());
		this.setOrgCreazione(fascicoloDTO.getOrgCreazione());
		this.setTipoProcedimento(fascicoloDTO.getTipoProcedimento());
		this.setOggettoIntervento(fascicoloDTO.getOggettoIntervento());
		this.setSanatoria(fascicoloDTO.getSanatoria());
		this.setCodiceInternoEnte(fascicoloDTO.getCodiceInternoEnte());
		this.setNumeroInternoEnte(fascicoloDTO.getNumeroInternoEnte());
		this.setProtocollo(fascicoloDTO.getProtocollo());
		this.setDataProtocollo(fascicoloDTO.getDataProtocollo());
		this.note = fascicoloDTO.getNote();
		this.setStato(fascicoloDTO.getStato());
		this.statoPrecedente = fascicoloDTO.getStatoPrecedente();
		this.setCodice(fascicoloDTO.getCodice());
		this.setNumeroProvvedimento(fascicoloDTO.getNumeroProvvedimento());
		this.setDataRilascioAutorizzazione(fascicoloDTO.getDataRilascioAutorizzazione());
		this.setEsito(fascicoloDTO.getEsito());
		this.setRup(fascicoloDTO.getRup());
		this.setEsitoDataRilascioAutorizzazione(fascicoloDTO.getEsitoDataRilascioAutorizzazione());
		this.setEsitoNumeroProvvedimento(fascicoloDTO.getEsitoNumeroProvvedimento());
		this.interventoDettaglio = fascicoloDTO.getInterventoDettaglio();
		this.interventoSpecifica = fascicoloDTO.getInterventoSpecifica();
		this.dataCreazione = fascicoloDTO.getDataCreazione();
		this.dataUltimaModifica = fascicoloDTO.getDataUltimaModifica();
		this.dataTrasmissione = fascicoloDTO.getDataTrasmissione();
		this.dataCampionamento = fascicoloDTO.getDataCampionamento();
		this.dataVerifica = fascicoloDTO.getDataVerifica();
		this.usernameUtenteCreazione = fascicoloDTO.getUsernameUtenteCreazione();
		this.usernameUtenteUltimaModifica = fascicoloDTO.getUsernameUtenteUltimaModifica();
		this.usernameUtenteTrasmissione = fascicoloDTO.getUsernameUtenteTrasmissione();
		this.denominazioneUtenteTrasmissione = fascicoloDTO.getDenominazioneUtenteTrasmissione();
		this.emailUtenteTrasmissione = fascicoloDTO.getEmailUtenteTrasmissione();
		this.setEsitoVerifica(fascicoloDTO.getEsitoVerifica());
		this.esitoVerificaPrecedente = fascicoloDTO.getEsitoVerificaPrecedente();
		this.setStatoRegistrazione(fascicoloDTO.getStatoRegistrazione());
		this.dataDelibera = fascicoloDTO.getDataDelibera();
		this.deliberaNum = fascicoloDTO.getDeliberaNum();
		this.oggettoDelibera = fascicoloDTO.getOggettoDelibera();
		this.infoDeliberePrecedenti = fascicoloDTO.getInfoDeliberePrecedenti();
		this.descrizioneIntervento = fascicoloDTO.getDescrizioneIntervento();
		this.versFascicolo = fascicoloDTO.getVersFascicolo();
		this.versRichiedente = fascicoloDTO.getVersRichiedente();
		this.versIntervento = fascicoloDTO.getVersIntervento();
		this.versLocalizzazione = fascicoloDTO.getVersLocalizzazione();
		this.versAllegati = fascicoloDTO.getVersAllegati();
		this.versProvvedimento = fascicoloDTO.getVersProvvedimento();
		this.infoComplete = fascicoloDTO.getInfoComplete();
		this.deleted = fascicoloDTO.isDeleted();
		this.modificabileFino = fascicoloDTO.getModificabileFino();
		this.setTipoSelezioneLocalizzazione(fascicoloDTO.getTipoSelezioneLocalizzazione());
		this.setHasShape(fascicoloDTO.isHasShape());
		this.statoTrasmissione = fascicoloDTO.isStatoTrasmissione();
	}

	public FascicoloDTO(FascicoloTabDTO fascicoloTabDTO) {
		this.setId(fascicoloTabDTO.getId());
		this.setUfficio(fascicoloTabDTO.getUfficio());
		this.setOrgCreazione(fascicoloTabDTO.getOrgCreazione());
		this.setTipoProcedimento(fascicoloTabDTO.getTipoProcedimento());
		this.setOggettoIntervento(fascicoloTabDTO.getOggettoIntervento());
		this.setSanatoria(fascicoloTabDTO.getSanatoria());
		this.setCodiceInternoEnte(fascicoloTabDTO.getCodiceInternoEnte());
		this.setNumeroInternoEnte(fascicoloTabDTO.getNumeroInternoEnte());
		this.setProtocollo(fascicoloTabDTO.getProtocollo());
		this.setDataProtocollo(fascicoloTabDTO.getDataProtocollo());
		this.note = fascicoloTabDTO.getNote();
		this.setStato(fascicoloTabDTO.getStato());
		this.statoPrecedente = fascicoloTabDTO.getStatoPrecedente();
		this.setCodice(fascicoloTabDTO.getCodice());
		this.setNumeroProvvedimento(fascicoloTabDTO.getNumeroProvvedimento());
		this.setDataRilascioAutorizzazione(fascicoloTabDTO.getDataRilascioAutorizzazione());
		this.setEsito(fascicoloTabDTO.getEsito());
		this.setRup(fascicoloTabDTO.getRup());
		this.setEsitoDataRilascioAutorizzazione(fascicoloTabDTO.getEsitoDataRilascioAutorizzazione());
		this.setEsitoNumeroProvvedimento(fascicoloTabDTO.getEsitoNumeroProvvedimento());
		this.interventoDettaglio = fascicoloTabDTO.getInterventoDettaglio();
		this.interventoSpecifica = fascicoloTabDTO.getInterventoSpecifica();
		this.dataCreazione = fascicoloTabDTO.getDataCreazione();
		this.dataUltimaModifica = fascicoloTabDTO.getDataUltimaModifica();
		this.dataTrasmissione = fascicoloTabDTO.getDataTrasmissione();
		this.dataCampionamento = fascicoloTabDTO.getDataCampionamento();
		this.dataVerifica = fascicoloTabDTO.getDataVerifica();
		this.usernameUtenteCreazione = fascicoloTabDTO.getUsernameUtenteCreazione();
		this.usernameUtenteUltimaModifica = fascicoloTabDTO.getUsernameUtenteUltimaModifica();
		this.usernameUtenteTrasmissione = fascicoloTabDTO.getUsernameUtenteTrasmissione();
		this.denominazioneUtenteTrasmissione = fascicoloTabDTO.getDenominazioneUtenteTrasmissione();
		this.emailUtenteTrasmissione = fascicoloTabDTO.getEmailUtenteTrasmissione();
		this.setEsitoVerifica(fascicoloTabDTO.getEsitoVerifica());
		this.esitoVerificaPrecedente = fascicoloTabDTO.getEsitoVerificaPrecedente();
		this.setStatoRegistrazione(fascicoloTabDTO.getStatoRegistrazione());
		this.dataDelibera = fascicoloTabDTO.getDataDelibera();
		this.deliberaNum = fascicoloTabDTO.getDeliberaNum();
		this.oggettoDelibera = fascicoloTabDTO.getOggettoDelibera();
		this.infoDeliberePrecedenti = fascicoloTabDTO.getInfoDeliberePrecedenti();
		this.descrizioneIntervento = fascicoloTabDTO.getDescrizioneIntervento();
		this.versFascicolo = fascicoloTabDTO.getVersFascicolo();
		this.versRichiedente = fascicoloTabDTO.getVersRichiedente();
		this.versIntervento = fascicoloTabDTO.getVersIntervento();
		this.versLocalizzazione = fascicoloTabDTO.getVersLocalizzazione();
		this.versAllegati = fascicoloTabDTO.getVersAllegati();
		this.versProvvedimento = fascicoloTabDTO.getVersProvvedimento();
		this.infoComplete = fascicoloTabDTO.getInfoComplete();
		this.deleted = fascicoloTabDTO.isDeleted();
		this.modificabileFino = fascicoloTabDTO.getModificabileFino();
		this.setHasShape(fascicoloTabDTO.isHasShape());
		this.setTipoSelezioneLocalizzazione(fascicoloTabDTO.getTipoSelezioneLocalizzazione());
		this.statoTrasmissione = fascicoloTabDTO.isStatoTrasmissione();
	}

	
	public Boolean getSanatoria() {
		return sanatoria;
	}

	public void setSanatoria(Boolean sanatoria) {
		this.sanatoria = sanatoria;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	public StatoFascicolo getStatoPrecedente() {
		return statoPrecedente;
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

	
	public String getInterventoDettaglio() {
		return interventoDettaglio;
	}

	public void setInterventoDettaglio(String interventoDettaglio) {
		this.interventoDettaglio = interventoDettaglio;
	}

	public String getInterventoSpecifica() {
		return interventoSpecifica;
	}

	public void setInterventoSpecifica(String interventoSpecifica) {
		this.interventoSpecifica = interventoSpecifica;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}

	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}

	public Date getDataCampionamento() {
		return dataCampionamento;
	}

	public void setDataCampionamento(Date dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}

	public Date getDataVerifica() {
		return dataVerifica;
	}

	public void setDataVerifica(Date dataVerifica) {
		this.dataVerifica = dataVerifica;
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

	public int getVersFascicolo() {
		return versFascicolo;
	}

	public void setVersFascicolo(int versFascicolo) {
		this.versFascicolo = versFascicolo;
	}

	public int getVersRichiedente() {
		return versRichiedente;
	}

	public void setVersRichiedente(int versRichiedente) {
		this.versRichiedente = versRichiedente;
	}

	public int getVersIntervento() {
		return versIntervento;
	}

	public void setVersIntervento(int versIntervento) {
		this.versIntervento = versIntervento;
	}

	public int getVersLocalizzazione() {
		return versLocalizzazione;
	}

	public void setVersLocalizzazione(int versLocalizzazione) {
		this.versLocalizzazione = versLocalizzazione;
	}

	public int getVersAllegati() {
		return versAllegati;
	}

	public void setVersAllegati(int versAllegati) {
		this.versAllegati = versAllegati;
	}

	public int getVersProvvedimento() {
		return versProvvedimento;
	}

	public void setVersProvvedimento(int versProvvedimento) {
		this.versProvvedimento = versProvvedimento;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getModificabileFino() {
		return modificabileFino;
	}

	public void setModificabileFino(Date modificabileFino) {
		this.modificabileFino = modificabileFino;
	}

	public InformazioniDTO getInfoComplete() {
		return infoComplete;
	}

	public void setInfoComplete(InformazioniDTO infoComplete) {
		this.infoComplete = infoComplete;
	}
	
	public boolean isStatoTrasmissione() {
		return statoTrasmissione;
	}

	public void setStatoTrasmissione(boolean statoTrasmissione) {
		this.statoTrasmissione = statoTrasmissione;
	}


		
	

	@Override
	public String toString() {
		return "FascicoloDTO [id=" + this.getId() + ", sanatoria=" + sanatoria + ", statoPrecedente=" + statoPrecedente
				+ ", interventoDettaglio=" + interventoDettaglio + ", interventoSpecifica=" + interventoSpecifica
				+ ", dataDelibera=" + dataDelibera + ", deliberaNum=" + deliberaNum + ", oggettoDelibera="
				+ oggettoDelibera + ", infoDeliberePrecedenti=" + infoDeliberePrecedenti + ", descrizioneIntervento="
				+ descrizioneIntervento + ", esitoVerificaPrecedente=" + esitoVerificaPrecedente + ", note=" + note
				+ ", versFascicolo=" + versFascicolo
				+ ", versRichiedente=" + versRichiedente + ", versIntervento=" + versIntervento
				+ ", versLocalizzazione=" + versLocalizzazione + ", versAllegati=" + versAllegati
				+ ", versProvvedimento=" + versProvvedimento + ", dataCreazione=" + dataCreazione
				+ ", dataUltimaModifica=" + dataUltimaModifica + ", dataTrasmissione=" + dataTrasmissione
				+ ", dataCampionamento=" + dataCampionamento + ", dataVerifica=" + dataVerifica
				+ ", usernameUtenteCreazione=" + usernameUtenteCreazione + ", usernameUtenteUltimaModifica="
				+ usernameUtenteUltimaModifica + ", usernameUtenteTrasmissione=" + usernameUtenteTrasmissione
				+ ", denominazioneUtenteTrasmissione=" + denominazioneUtenteTrasmissione + ", emailUtenteTrasmissione="
				+ emailUtenteTrasmissione + ", infoComplete=" + infoComplete + ", deleted=" + deleted
				+ ", modificabileFino=" + modificabileFino + ", statoTrasmissione=" + statoTrasmissione + "]";
	}

	public Long gettPraticaId() {
		return tPraticaId;
	}

	public void settPraticaId(Long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	
}