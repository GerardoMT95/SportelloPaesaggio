/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
//CIVILIA_CS.TNO_PPTR_LOCALIZ_INTERV
public class AutPaesPptrLocalizInterv {
	
	//@Column(name="LOCALIZ_INTERV_COMUNE_ID")  //sembra un codice istat
	private long localizIntervComuneId;

	//@Column(name="LOCALIZ_INTERV_PROV")
	private String localizIntervProvincia;

	//@Column(name="LOCALIZ_INTERV_DEST_USO_ATT")
	private String localizIntervDestUsoAtt;

	//@Column(name="LOCALIZ_INTERV_DEST_USO_PROG")
	private String localizIntervDestUsoProg;

	//@Column(name="LOCALIZ_INTERV_INDIRIZZO")
	private String localizIntervIndirizzo;

	//@Column(name="LOCALIZ_INTERV_INTERNO")
	private String localizIntervInterno;

	//@Column(name="LOCALIZ_INTERV_NCIV")
	private String localizIntervNciv;

	//@Column(name="LOCALIZ_INTERV_PIANO")
	private String localizIntervPiano;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;

	//@Column(name="COMUNE")
	private String comune;

	//@Column(name="DATA_RIFERIMENTO_CATASTALE")
	private String dataRiferimentoCatastale;
	
	//@Column(name= "CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;
	
	//@Column(name= "STRADE")
	private String strade;
	
	//@Column(name="ELABORATO")
	private long elaborato;
	
	//@Column(name="PROG")
	private long prog;
	
	//@Column(name="LOCALIZ_INTERV_ID")
	private long localizIntervId;
	

	public long getLocalizIntervId() {
		return localizIntervId;
	}

	public void setLocalizIntervId(long localizIntervId) {
		this.localizIntervId = localizIntervId;
	}

	public long getLocalizIntervComuneId() {
		return localizIntervComuneId;
	}

	public void setLocalizIntervComuneId(long localizIntervComuneId) {
		this.localizIntervComuneId = localizIntervComuneId;
	}

	public String getLocalizIntervProvincia() {
		return localizIntervProvincia;
	}

	public void setLocalizIntervProvincia(String localizIntervProvincia) {
		this.localizIntervProvincia = localizIntervProvincia;
	}

	public String getLocalizIntervDestUsoAtt() {
		return localizIntervDestUsoAtt;
	}

	public void setLocalizIntervDestUsoAtt(String localizIntervDestUsoAtt) {
		this.localizIntervDestUsoAtt = localizIntervDestUsoAtt;
	}

	public String getLocalizIntervDestUsoProg() {
		return localizIntervDestUsoProg;
	}

	public void setLocalizIntervDestUsoProg(String localizIntervDestUsoProg) {
		this.localizIntervDestUsoProg = localizIntervDestUsoProg;
	}

	public String getLocalizIntervIndirizzo() {
		return localizIntervIndirizzo;
	}

	public void setLocalizIntervIndirizzo(String localizIntervIndirizzo) {
		this.localizIntervIndirizzo = localizIntervIndirizzo;
	}

	public String getLocalizIntervInterno() {
		return localizIntervInterno;
	}

	public void setLocalizIntervInterno(String localizIntervInterno) {
		this.localizIntervInterno = localizIntervInterno;
	}

	public String getLocalizIntervNciv() {
		return localizIntervNciv;
	}

	public void setLocalizIntervNciv(String localizIntervNciv) {
		this.localizIntervNciv = localizIntervNciv;
	}

	public String getLocalizIntervPiano() {
		return localizIntervPiano;
	}

	public void setLocalizIntervPiano(String localizIntervPiano) {
		this.localizIntervPiano = localizIntervPiano;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getDataRiferimentoCatastale() {
		return dataRiferimentoCatastale;
	}

	public void setDataRiferimentoCatastale(String dataRiferimentoCatastale) {
		this.dataRiferimentoCatastale = dataRiferimentoCatastale;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getStrade() {
		return strade;
	}

	public void setStrade(String strade) {
		this.strade = strade;
	}

	public long getElaborato() {
		return elaborato;
	}

	public void setElaborato(long elaborato) {
		this.elaborato = elaborato;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	@Override
	public String toString() {
		return "AutPaesPptrLocalizInterv [localizIntervComuneId=" + localizIntervComuneId + ", localizIntervProvincia="
				+ localizIntervProvincia + ", localizIntervDestUsoAtt=" + localizIntervDestUsoAtt
				+ ", localizIntervDestUsoProg=" + localizIntervDestUsoProg + ", localizIntervIndirizzo="
				+ localizIntervIndirizzo + ", localizIntervInterno=" + localizIntervInterno + ", localizIntervNciv="
				+ localizIntervNciv + ", localizIntervPiano=" + localizIntervPiano + ", tPraticaApptrId="
				+ tPraticaApptrId + ", comune=" + comune + ", dataRiferimentoCatastale=" + dataRiferimentoCatastale
				+ ", codicePraticaAppptr=" + codicePraticaAppptr + ", strade=" + strade + ", elaborato=" + elaborato
				+ ", prog=" + prog + "]";
	}

}
