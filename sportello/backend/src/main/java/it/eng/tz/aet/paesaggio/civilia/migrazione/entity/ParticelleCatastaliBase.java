/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

/**
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class ParticelleCatastaliBase {

	//@Column( name = "CODICE_PRATICA" )
	protected String codicePratica;
	
	//@Column( name = "COD_ISTAT" )
	protected String codIstat;
	
	//@Column( name = "COMUNE" )
	protected String comune;
	
	//@Column( name = "LIVELLO" )
	protected String livello;
	
	//@Column( name = "SEZIONE" )
	protected String sezione;
	
	//@Column( name = "FOGLIO" )
	protected String foglio;
	
	//@Column( name = "PARTICELLA" )
	protected String particella;   
	
	//@Column( name = "SUB" )
	protected String sub;
	
	//@Column( name = "DATA_RIFERIMENTO_CATASTALE" )
	protected String dataRiferimentoCatastale;
	
	//@Column( name = "UTENTE" )
	protected String utente;
	
	//@Column( name = "ENTE" )
	protected String ente;
	
	//@Column( name = "COD_CAT" )
	protected String codCat;

	public String getCodicePratica() {
		return codicePratica;
	}

	public void setCodicePratica(String codicePratica) {
		this.codicePratica = codicePratica;
	}

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getLivello() {
		return livello;
	}

	public void setLivello(String livello) {
		this.livello = livello;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getDataRiferimentoCatastale() {
		return dataRiferimentoCatastale;
	}

	public void setDataRiferimentoCatastale(String dataRiferimentoCatastale) {
		this.dataRiferimentoCatastale = dataRiferimentoCatastale;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getCodCat() {
		return codCat;
	}

	public void setCodCat(String codCat) {
		this.codCat = codCat;
	}
	
	
}
