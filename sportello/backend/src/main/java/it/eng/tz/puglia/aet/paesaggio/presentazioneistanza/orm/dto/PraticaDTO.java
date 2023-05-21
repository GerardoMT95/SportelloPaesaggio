package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * DTO for table presentazione_istanza.pratica
 */
public class PraticaDTO implements Serializable
{
		
	private static final long serialVersionUID = 9603928076L;
	// COLUMN id
	private UUID id;
	// COLUMN codice_pratica_appptr
	private String codicePraticaAppptr;
	// COLUMN ente_delegato
	@NotBlank(message = "L'ente delegato non puo' essere vuoto")
	private String enteDelegato;
	// COLUMN in_sanatoria
	private Boolean inSanatoria;
	// COLUMN tipo_procedimento
	private int tipoProcedimento;
	// COLUMN stato
	private AttivitaDaEspletare stato;
	// COLUMN data_creazione
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date dataCreazione;
	// COLUMN oggetto
	@NotBlank(message = "L'oggetto non puo' essere vuoto")
	private String oggetto;
	// COLUMN user_id
	private String userId;
	// COLUMN data_modifica
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date dataModifica;
	// COLUMN privacy_id
	private Integer privacyId;
	// COLUMN validazione_istanza
	private Boolean validazioneIstanza;
	// COLUMN validazione_scheda_tecnica
	private Boolean validazioneSchedaTecnica;
	// COLUMN validazione_allegati
	private Boolean validazioneAllegati;
	//COLUMN validazione_richiedente
	private Boolean validazioneRichiedente;
	// COLUMN tipo_selezione_localizzazione
	private String tipoSelezioneLocalizzazione;
	//valori ammessi: PTC=selezione particelle SHPD=Shape editing SHPF shape file
	// COLUMN has_shape
	private Boolean hasShape;
	//COLUMN stato_seduta_commissione
	private StatoSeduta statoSedutaCommissione;
	//COLUMN stato_relazione_ente
	private StatoRelazione statoRelazioneEnte;
	//COLUMN stato_parere_soprintendenza
	private StatoParere statoParereSoprintendenza;
	
	private String numeroProtocollo;
	
	private Timestamp dataProtocollo;
	// COLUMN data_presentazione
	private Timestamp dataPresentazione;
	// COLUMN data_trasmissione:provvedimento_finale
	private Timestamp dataTrasmissioneProvvedimentoFinale;
	//COLUMN data_inizio_lavorazione
	private Timestamp dataInizioLavorazione;
	//COLUMN data_trasmissione_verbale
	private Timestamp dataTrasmissioneVerbale;
	//COLUMN data_trasmissione_relazione
	private Timestamp dataTrasmissioneRelazione;
	//COLUMN data_trasmissione_parere
	private Timestamp dataTrasmissioneParere;
	// COLUMN ultimo_stato_valido
	private AttivitaDaEspletare ultimoStatoValido;
	//COLUMN codice_trasmissione
	private String codiceTrasmissione;
	//COLUMN t_pratica_id
	private Long tPraticaId;//id pratica utilizzato per la migrazione
	//COLUMN ruolo_pratica
	@NotBlank(message = "Il ruolo non puo' essere vuoto")
	private String ruoloPratica;
	//COLUMN codice_segreto
	private String codiceSegreto;
	//COLUMN owner
	private String owner;
	//COLUMN user_updated
	private String userUpdated;
	//COLUMN esonero_oneri
	private Boolean esoneroOneri;
	//COLUMN esonero_bollo
	private Boolean esoneroBollo;
		
	
	public Long gettPraticaId() {
		return tPraticaId;
	}
	public void settPraticaId(Long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}
	public String getCodiceTrasmissione() {
		return codiceTrasmissione;
	}
	public void setCodiceTrasmissione(String codiceTrasmissione) {
		this.codiceTrasmissione = codiceTrasmissione;
	}

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

	public Integer getPrivacyId()
	{
		return privacyId;
	}

	public void setPrivacyId(Integer privacyId)
	{
		this.privacyId = privacyId;
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

	@JsonGetter("isInSanatoria")
	public Boolean getInSanatoria()
	{
		return this.inSanatoria;
	}
	@JsonSetter("isInSanatoria")
	public void setInSanatoria(Boolean inSanatoria)
	{
		this.inSanatoria = inSanatoria;
	}

	public int getTipoProcedimento()
	{
		return this.tipoProcedimento;
	}
	public void setTipoProcedimento(int tipoProcedimento)
	{
		this.tipoProcedimento = tipoProcedimento;
	}

	@JsonGetter("attivitaDaEspletare")
	public AttivitaDaEspletare getStato()
	{
		return this.stato;
	}
	@JsonSetter("attivitaDaEspletare")
	public void setStato(AttivitaDaEspletare stato)
	{
		this.stato = stato;
	}

	public Date getDataCreazione()
	{
		return this.dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione)
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

	public Date getDataModifica()
	{
		return this.dataModifica;
	}
	public void setDataModifica(Date dataModifica)
	{
		this.dataModifica = dataModifica;
	}

	public Boolean getValidazioneIstanza()
	{
		return validazioneIstanza;
	}
	public void setValidazioneIstanza(Boolean validazioneIstanza)
	{
		this.validazioneIstanza = validazioneIstanza;
	}

	public Boolean getValidazioneSchedaTecnica()
	{
		return validazioneSchedaTecnica;
	}
	public void setValidazioneSchedaTecnica(Boolean validazioneSchedaTecnica)
	{
		this.validazioneSchedaTecnica = validazioneSchedaTecnica;
	}

	public Boolean getValidazioneAllegati()
	{
		return validazioneAllegati;
	}
	public void setValidazioneAllegati(Boolean validazioneAllegati)
	{
		this.validazioneAllegati = validazioneAllegati;
	}

	public Boolean getValidazioneRichiedente()
	{
	    return validazioneRichiedente;
	}
	public void setValidazioneRichiedente(Boolean validazioneRichiedente)
	{
	    this.validazioneRichiedente = validazioneRichiedente;
	}
	
	public boolean diMiaCompetenza()
	{
		String userNameMocked = MDC.get(UserUtil.MDC_USERNAME_MOCKED);
		if(StringUtil.isNotEmpty(userNameMocked)) {
			return this.getOwner().equals(userNameMocked);
		}
		return this.getOwner().equals(SecurityUtil.getUsername());
	}

	public String getNumeroProtocollo()
	{
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo)
	{
		this.numeroProtocollo = numeroProtocollo;
	}

	public AttivitaDaEspletare getUltimoStatoValido()
	{
		return ultimoStatoValido;
	}
	public void setUltimoStatoValido(AttivitaDaEspletare ultimoStatoValido)
	{
		this.ultimoStatoValido = ultimoStatoValido;
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
	
	public Timestamp getDataProtocollo()
	{
		return dataProtocollo;
	}
	public void setDataProtocollo(Timestamp dataProtocollo)
	{
		this.dataProtocollo = dataProtocollo;
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
	/**
	 * @return the ruoloPratica
	 */
	public String getRuoloPratica() {
		return ruoloPratica;
	}
	/**
	 * @param ruoloPratica the ruoloPratica to set
	 */
	public void setRuoloPratica(String ruoloPratica) {
		this.ruoloPratica = ruoloPratica;
	}
	/**
	 * @return the codiceSegreto
	 */
	public String getCodiceSegreto() {
		return codiceSegreto;
	}
	/**
	 * @param codiceSegreto the codiceSegreto to set
	 */
	public void setCodiceSegreto(String codiceSegreto) {
		this.codiceSegreto = codiceSegreto;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the userUpdated
	 */
	public String getUserUpdated() {
		return userUpdated;
	}
	/**
	 * @param userUpdated the userUpdated to set
	 */
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
