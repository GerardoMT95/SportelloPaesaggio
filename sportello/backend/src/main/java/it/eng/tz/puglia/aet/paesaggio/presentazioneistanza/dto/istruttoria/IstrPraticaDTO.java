package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;

public class IstrPraticaDTO extends PraticaDTO
{
	private static final long serialVersionUID = 7034522176344685353L;
	
	private Boolean rup; //indica se l'utente autenticato è anche il rup della pratica in questione.
	private String denominazioneRup; 
	private String usernameRup;
	private String denominazioneFunzionario; 
	private String usernameFunzionario;
	//riferimento assegnazione per l'organizzazione loggata
	private String usernameAssegnatarioOrganizzazione;
	private String denominazioneAssegnatarioOrganizzazione;
	 
	public IstrPraticaDTO()
	{
		super();
	}
	public IstrPraticaDTO(PraticaDTO other)
	{
		setId(other.getId());
		setCodicePraticaAppptr(other.getCodicePraticaAppptr());
		setDataCreazione(other.getDataCreazione());
		setDataModifica(other.getDataModifica());
		setDataPresentazione(other.getDataPresentazione());
		setDataProtocollo(other.getDataProtocollo());
		setDataTrasmissioneProvvedimentoFinale(other.getDataTrasmissioneProvvedimentoFinale());
		setEnteDelegato(other.getEnteDelegato());
		setHasShape(other.getHasShape());
		setInSanatoria(other.getInSanatoria());
		setNumeroProtocollo(other.getNumeroProtocollo());
		setOggetto(other.getOggetto());
		setPrivacyId(other.getPrivacyId());
		setStato(other.getStato());
		setTipoProcedimento(other.getTipoProcedimento());
		setTipoSelezioneLocalizzazione(other.getTipoSelezioneLocalizzazione());
		setUltimoStatoValido(other.getUltimoStatoValido());
		setUserId(other.getUserId());
		setValidazioneAllegati(other.getValidazioneAllegati());
		setValidazioneIstanza(other.getValidazioneIstanza());
		setValidazioneSchedaTecnica(other.getValidazioneSchedaTecnica());
		setStatoParereSoprintendenza(other.getStatoParereSoprintendenza());
		setStatoRelazioneEnte(other.getStatoRelazioneEnte());
		setStatoSedutaCommissione(other.getStatoSedutaCommissione());
		setRup(null);
	}
	
	public Boolean getRup()
	{
		return rup;
	}
	public void setRup(Boolean rup)
	{
		this.rup = rup;
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
	

}
