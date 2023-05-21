package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;
/**
 * TNO_PPTR_PARERE_SOP
 * @author acolaianni
 *
 */
public class PptrParereSop {
	//@Column( name = "TNO_PPTR_PARERE_SOP_ID" )
	private long id;
	
	//@Column( name = "CODICE_PRATICA_APPPTR" )
	private String codicePraticaApPptr;
	
	//@Column( name = "T_PRATICA_APPTR_ID" )
	private String tPraticaApptrId;
	
	//@Column( name = "PROG" )
	private long prog;
	
	//@Column( name = "NUMERO_PROTOCOLLO_SOP" )
	private String numeroProtocolloSop;
	
	//@Column( name = "FLAG_ESITO" )
	private String flagEsito;
	
	//@Column( name = "DETTAGLIO_PRESCRIZIONI" )
	private String dettaglioPrescrizioni;
	
	//@Column( name = "NOTE_PARERE_SOP" )
	private String noteParereSop;
	
	//@Column( name = "NOME_ISTRUTTORE_SOP" )
	private String nomeIstruttoreSop;
	
	// 0 SOPRINTENDENZA - 1 ENTE DELEGATO
	//@Column( name = "FLAG_PROVENIENZA" )
	private long flag_provenienza;
	
	// Dichiare se l'Ente Delegato ha messo il parere - S = si , vuoto no  
	//@Column( name = "ED_PARERE_SOTTOMESSO" )
	private String eD_ParereSottomesso;

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

	public String getNumeroProtocolloSop() {
		return numeroProtocolloSop;
	}

	public void setNumeroProtocolloSop(String numeroProtocolloSop) {
		this.numeroProtocolloSop = numeroProtocolloSop;
	}

	public String getFlagEsito() {
		return flagEsito;
	}

	public void setFlagEsito(String flagEsito) {
		this.flagEsito = flagEsito;
	}

	public String getDettaglioPrescrizioni() {
		return dettaglioPrescrizioni;
	}

	public void setDettaglioPrescrizioni(String dettaglioPrescrizioni) {
		this.dettaglioPrescrizioni = dettaglioPrescrizioni;
	}

	public String getNoteParereSop() {
		return noteParereSop;
	}

	public void setNoteParereSop(String noteParereSop) {
		this.noteParereSop = noteParereSop;
	}

	public String getNomeIstruttoreSop() {
		return nomeIstruttoreSop;
	}

	public void setNomeIstruttoreSop(String nomeIstruttoreSop) {
		this.nomeIstruttoreSop = nomeIstruttoreSop;
	}

	public long getFlag_provenienza() {
		return flag_provenienza;
	}

	public void setFlag_provenienza(long flag_provenienza) {
		this.flag_provenienza = flag_provenienza;
	}

	public String geteD_ParereSottomesso() {
		return eD_ParereSottomesso;
	}

	public void seteD_ParereSottomesso(String eD_ParereSottomesso) {
		this.eD_ParereSottomesso = eD_ParereSottomesso;
	}
	
}
