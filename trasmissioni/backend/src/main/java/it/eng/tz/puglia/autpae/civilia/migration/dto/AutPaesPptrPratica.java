/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
//TNO_PPTR_PRATICA
public class AutPaesPptrPratica {
	private String jbpUname;

	// @Column(name="JBP_ID")
	private long jbpId;

	// @Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApPptr;

	// @Column(name="CODICE_PRATICA_ENTEDELEGATO")
	private String codicePraticaEnteDelegato;

	// @Column(name="TNO_PPTR_TIPOPROCEDIMENTO_ID")
	// FK sulla tabella TNO_PPTR_TIPOPROCEDIMENTO per indicare il tipo di
	// procedimento
	/**
	 *  1	AUTORIZZAZIONE PAESAGGISTICA art. 146, D.Lgs. 42/2004 - art. 90, NTA PPTR (ORDINARIA)
		2	AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010 - art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)
		3	ACCERTAMENTO DI COMPATIBILITA’ PAESAGGISTICA Artt. 167 e 181, D.Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)
		4	ACCERTAMENTO DI COMPATIBILITA'  PAESAGGISTICA art. 91, NTA PPTR  [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)
		5	AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017 – ART 90, NTA PPTR PER INTERVENTI ED OPERE DI LIEVE ENTITA' SOGGETTI AL PROCEDIMENTO AUTORIZZATORIO SEMPLIFICATO A NORMA DELL' ART 146 c.9 DEL D.LGS. 42/2004
		6	ACCERTAMENTO DI COMPATIBILITA'  PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)
	 */
	private long tipoProcedimentoId;

	// @Column(name="T_PRATICA_DESCRIZIONE")
	private String tPraticaDescrizione;

	// @Column(name="ENTEDELEGATO")
	private String enteDelegato;

	// @Column(name="UFFICIO")
	private String ufficio;

	// 0 se pratica attiva
	// 1 se pratica cancellata
	// @Column(name="ACTIVE")
	private String active;

	// 0 procedimento completo
	// 1 solo procediment finale
	// @Column(name="SOLOTRASMISSIONE")
	private long soloTrasmissione;

	// @Column(name="NOTE")
	private String note;

	// 0 se non checcato
	// 1 se ceccato
	// @Column(name="PROVVEDIMENTO_SANATORIA")
	private String provvedimentoInSanatoria;

	// @Column(name="PROG")
	private long prog;

	public String getJbpUname() {
		return jbpUname;
	}

	public void setJbpUname(String jbpUname) {
		this.jbpUname = jbpUname;
	}

	public long getJbpId() {
		return jbpId;
	}

	public void setJbpId(long jbpId) {
		this.jbpId = jbpId;
	}

	public String getCodicePraticaApPptr() {
		return codicePraticaApPptr;
	}

	public void setCodicePraticaApPptr(String codicePraticaApPptr) {
		this.codicePraticaApPptr = codicePraticaApPptr;
	}

	public String getCodicePraticaEnteDelegato() {
		return codicePraticaEnteDelegato;
	}

	public void setCodicePraticaEnteDelegato(String codicePraticaEnteDelegato) {
		this.codicePraticaEnteDelegato = codicePraticaEnteDelegato;
	}

	public long getTipoProcedimentoId() {
		return tipoProcedimentoId;
	}

	public void setTipoProcedimentoId(long tipoProcedimentoId) {
		this.tipoProcedimentoId = tipoProcedimentoId;
	}

	public String gettPraticaDescrizione() {
		return tPraticaDescrizione;
	}

	public void settPraticaDescrizione(String tPraticaDescrizione) {
		this.tPraticaDescrizione = tPraticaDescrizione;
	}

	public String getEnteDelegato() {
		return enteDelegato;
	}

	public void setEnteDelegato(String enteDelegato) {
		this.enteDelegato = enteDelegato;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public long getSoloTrasmissione() {
		return soloTrasmissione;
	}

	public void setSoloTrasmissione(long soloTrasmissione) {
		this.soloTrasmissione = soloTrasmissione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getProvvedimentoInSanatoria() {
		return provvedimentoInSanatoria;
	}

	public void setProvvedimentoInSanatoria(String provvedimentoInSanatoria) {
		this.provvedimentoInSanatoria = provvedimentoInSanatoria;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	@Override
	public String toString() {
		return "AutPaesPptrPratica [jbpUname=" + jbpUname + ", jbpId=" + jbpId + ", codicePraticaApPptr="
				+ codicePraticaApPptr + ", codicePraticaEnteDelegato=" + codicePraticaEnteDelegato
				+ ", tipoProcedimentoId=" + tipoProcedimentoId + ", tPraticaDescrizione=" + tPraticaDescrizione
				+ ", enteDelegato=" + enteDelegato + ", ufficio=" + ufficio + ", active=" + active
				+ ", soloTrasmissione=" + soloTrasmissione + ", note=" + note + ", provvedimentoInSanatoria="
				+ provvedimentoInSanatoria + ", prog=" + prog + "]";
	}

}
