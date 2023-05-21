package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;
/**
 * TNO_PPTR_RELAZIONE_ENTE
 * @author acolaianni
 *
 */
public class PptrRelazioneEnte {
	
	//@Column(name="TNO_PPTR_RELAZIONE_ENTE_ID")
	private long id;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApPptr;
	
	//@Column(name="T_PRATICA_APPTR_ID")
	private String tPraticaApptrId;

	//@Column(name="PROG")
	private long prog;
	
    //@Column(name="NUMERO_PROTOCOLLO_ENTE")
    private String numeroProtocolloEnte;
	
	//@Column(name="FLAG_ESITO")
	private String flagEsito;
	               
    //@Column(name="DETTAGLIO_RELAZIONE")
    private String dettaglioRelazione;
	
	//@Column(name="NOTE_ENTE")
	private String noteEnte;
	
	//@Column(name="NOME_ISTRUTTORE_ENTE")
	private String nomeIstruttoreEnte;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodicePraticaApPptr() {
		return codicePraticaApPptr;
	}

	public void setCodicePraticaApPptr(String codicePraticaApPptr) {
		this.codicePraticaApPptr = codicePraticaApPptr;
	}

	public String gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(String tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	public String getNumeroProtocolloEnte() {
		return numeroProtocolloEnte;
	}

	public void setNumeroProtocolloEnte(String numeroProtocolloEnte) {
		this.numeroProtocolloEnte = numeroProtocolloEnte;
	}

	public String getFlagEsito() {
		return flagEsito;
	}

	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}

	public String getDettaglioRelazione() {
		return dettaglioRelazione;
	}

	public void setDettaglioRelazione(String dettaglioRelazione) {
		this.dettaglioRelazione = dettaglioRelazione;
	}

	public String getNoteEnte() {
		return noteEnte;
	}

	public void setNoteEnte(String noteEnte) {
		this.noteEnte = noteEnte;
	}

	public String getNomeIstruttoreEnte() {
		return nomeIstruttoreEnte;
	}

	public void setNomeIstruttoreEnte(String nomeIstruttoreEnte) {
		this.nomeIstruttoreEnte = nomeIstruttoreEnte;
	}

	
}
