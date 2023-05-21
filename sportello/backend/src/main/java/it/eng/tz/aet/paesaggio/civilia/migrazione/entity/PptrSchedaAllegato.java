package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;
import java.util.Date;

/**
 * tabella degli allegati inviati e ricevuti
 *@Table( name = "TNO_PPTR_SCHEDA_ALLEGATO" )
 * @author acolaianni
 *
 */
public class PptrSchedaAllegato {
	//@Column( name = "SCHEDA_ALLEGATO_ID" )
	private long schedaAllegatoId;
	
	/** alfresco id. */
	//@Column( name = "ALFRESCO_ID" )
	private String alfrescoId;
	
	/** allegato. */
	//@Column( name = "ALLEGATO" )
	private Blob allegato;
	
	/** codice pratica appptr. */
	//@Column( name = "CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;
	
	/** data inserimento. */
	//@Column( name = "DATA_INSERIMENTO" )
	private Date dataInserimento;
	
	/** descrizione. */
	//@Column( name = "DESCRIZIONE" )
	private String descrizione;
	
	/** mittente. username */
	//@Column( name = "MITTENTE" )
	private String mittente;
	
	/** ruolo mittente. ED ET SOP RI=richiedente*/
	//@Column( name = "RUOLO_MITTENTE" )
	private String ruoloMittente;
	
	/** destinatario. lista email destinatari separati da ; */
	//@Column( name = "DESTINATARIO" )
	private String destinatario;
	
	/** visibilita_ ENTE TERRITORIALE 0=no 1=si*/
	//@Column( name = "VISIBILITA_ET" )
	private long visibilita_ET;
	
	/** visibilita_ SOPRINTENDENZA */
	//@Column( name = "VISIBILITA_SOP" )
	private long visibilita_SOP;
	
	/** visibilita_ ENTE DELEGATO. */
	//@Column( name = "VISIBILITA_ED" )
	private long visibilita_ED;
	
	/** visibilita_ RICHIEDENTE. */
	//@Column( name = "VISIBILITA_RI" )
	private long visibilita_RI;
	
	/** nome file. */
	//@Column( name = "NOME_FILE" )
	private String nomeFile;
	
	/** content type. */
	//@Column( name = "CONTENTTYPE" )
	private String contentType;
	
	/** t pratica appptr id. */
	//@Column( name = "T_PRATICA_APPPTR_ID" )
	private long tPraticaAppptrId;
	
	/** titolo. */
	//@Column( name = "TITOLO" )
	private String titolo;

	public long getSchedaAllegatoId() {
		return schedaAllegatoId;
	}

	public void setSchedaAllegatoId(long schedaAllegatoId) {
		this.schedaAllegatoId = schedaAllegatoId;
	}

	public String getAlfrescoId() {
		return alfrescoId;
	}

	public void setAlfrescoId(String alfrescoId) {
		this.alfrescoId = alfrescoId;
	}

	public Blob getAllegato() {
		return allegato;
	}

	public void setAllegato(Blob allegato) {
		this.allegato = allegato;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getRuoloMittente() {
		return ruoloMittente;
	}

	public void setRuoloMittente(String ruoloMittente) {
		this.ruoloMittente = ruoloMittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public long getVisibilita_ET() {
		return visibilita_ET;
	}

	public void setVisibilita_ET(long visibilita_ET) {
		this.visibilita_ET = visibilita_ET;
	}

	public long getVisibilita_SOP() {
		return visibilita_SOP;
	}

	public void setVisibilita_SOP(long visibilita_SOP) {
		this.visibilita_SOP = visibilita_SOP;
	}

	public long getVisibilita_ED() {
		return visibilita_ED;
	}

	public void setVisibilita_ED(long visibilita_ED) {
		this.visibilita_ED = visibilita_ED;
	}

	public long getVisibilita_RI() {
		return visibilita_RI;
	}

	public void setVisibilita_RI(long visibilita_RI) {
		this.visibilita_RI = visibilita_RI;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long gettPraticaAppptrId() {
		return tPraticaAppptrId;
	}

	public void settPraticaAppptrId(long tPraticaAppptrId) {
		this.tPraticaAppptrId = tPraticaAppptrId;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
}
