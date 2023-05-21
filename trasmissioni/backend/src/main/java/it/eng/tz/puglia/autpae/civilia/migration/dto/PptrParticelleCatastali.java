/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * CIVILIA_CS.TNO_PARTICELLECATASTALI_PPTR
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class PptrParticelleCatastali extends ParticelleCatastaliBase{

	
	/** The localiz interv id. */
	// Kiave della tabella TNO_PPTR_LOCALIZ_INTERV
	//@Column(name="LOCALIZ_INTERV_ID")
	private long localizIntervId;	

	/** The elaborato. */
	//@Column(name="ELABORATO")
	private long elaborato;
	
	/** The codice pratica apptr. */
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaApptr;
	
	/** The t pratica apptr id. */
	//@Column(name="T_PRATICA_APPPTR_ID")
	private long tPraticaApptrId;
	
	/** The prog. */
	//@Column(name="PROG")
	private long prog;
	
	//qui viene tradotta eventuale nome della sezione.
	private String nomeSezione;
	
	

	public String getNomeSezione() {
		return nomeSezione;
	}

	public void setNomeSezione(String nomeSezione) {
		this.nomeSezione = nomeSezione;
	}

	public long getLocalizIntervId() {
		return localizIntervId;
	}

	public void setLocalizIntervId(long localizIntervId) {
		this.localizIntervId = localizIntervId;
	}

	public long getElaborato() {
		return elaborato;
	}

	public void setElaborato(long elaborato) {
		this.elaborato = elaborato;
	}

	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}

	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
}
