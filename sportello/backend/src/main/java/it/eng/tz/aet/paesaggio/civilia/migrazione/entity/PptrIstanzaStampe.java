package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;

/**
 * questa classe gestisce la persistenza della tabella TNO_PPTR_ISTANZA_STAMPE
 * 
 * nella quale vengono inserite le due stampe generate dal sistema
 * 1) Istanza
 * 2) Dichiarazione tecnica
 * 
 * inoltre in questa tabella vengono caricate le scansioni inserite dal cittadino
 * 
 */

/**
CREATE TABLE TNO_PPTR_ISTANZA_STAMPE
(
  TNO_PPTR_ISTANZA_STAMPE_ID    NUMBER,
  CODICE_PRATICA_APPPTR         VARCHAR2(255 BYTE),
  ISTANZA                       BLOB,
  DICHIARAZIONE_TECNICA         BLOB,
  ISTANZA_S                     BLOB,
  DICHIARAZIONE_TECNICA_S       BLOB,
  ISTANZA_S_NAME                VARCHAR2(255 BYTE),
  DICHIARAZIONE_TECNICA_S_NAME  VARCHAR2(255 BYTE)
)
*/
public class PptrIstanzaStampe {
	
	//@Column(name="TNO_PPTR_ISTANZA_STAMPE_ID")
	private long id;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApptr;
	
	// Istanza generata dal sistema
	//@Column(name="ISTANZA")
	private Blob istanza;
	
	// Dichiarazione tecnica generata dal sistema
	//@Column(name="DICHIARAZIONE_TECNICA")
	private Blob dichiarazioneTecnica;
	
	// Istanza caricata dal cittadino
	//@Column(name="ISTANZA_S")
	private Blob istanzaS;
	
	// Dichiarazione tecnica caricata dal cittadino
	//@Column(name="DICHIARAZIONE_TECNICA_S")
	private Blob dichiarazioneTecnicaS;
	
	//@Column(name="ISTANZA_S_NAME")
	private String istanzaSname;
		
	//@Column(name="DICHIARAZIONE_TECNICA_S_NAME")
	private String dichiarazioneTecnicaSname;
	
	//@Column( name="PROG" )
	private long prog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}

	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}

	public Blob getIstanza() {
		return istanza;
	}

	public void setIstanza(Blob istanza) {
		this.istanza = istanza;
	}

	public Blob getDichiarazioneTecnica() {
		return dichiarazioneTecnica;
	}

	public void setDichiarazioneTecnica(Blob dichiarazioneTecnica) {
		this.dichiarazioneTecnica = dichiarazioneTecnica;
	}

	public Blob getIstanzaS() {
		return istanzaS;
	}

	public void setIstanzaS(Blob istanzaS) {
		this.istanzaS = istanzaS;
	}

	public Blob getDichiarazioneTecnicaS() {
		return dichiarazioneTecnicaS;
	}

	public void setDichiarazioneTecnicaS(Blob dichiarazioneTecnicaS) {
		this.dichiarazioneTecnicaS = dichiarazioneTecnicaS;
	}

	public String getIstanzaSname() {
		return istanzaSname;
	}

	public void setIstanzaSname(String istanzaSname) {
		this.istanzaSname = istanzaSname;
	}

	public String getDichiarazioneTecnicaSname() {
		return dichiarazioneTecnicaSname;
	}

	public void setDichiarazioneTecnicaSname(String dichiarazioneTecnicaSname) {
		this.dichiarazioneTecnicaSname = dichiarazioneTecnicaSname;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

		
}
