/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

/** CIVILIA_CS.TNO_PPTR_CARATT_INTERVENTO
 * logica del 1=true altrimenti false
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrCaratterizzazioneIntervento {

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;

	//@Column(name="NUOVI_INSED_CIVILI_RURALI")
	private String nuoviInsediamentiCiviliRurali;
	
	//@Column(name="RISTR_INSED_CIVILI_RURALI")
	private String ristrInsedCiviliRurali;

	//@Column(name="NUOVI_INSED_IND_COMMERC")
	private String nuoviInsedIndCommerc;
	  
	//@Column(name="RISTR_INSED_IND_COMMERC")
	private String ristrInsedIndCommerc;
	        
	//@Column(name="RECINZIONI")
	private String recinzioni;
	          
	//@Column(name="IMPIANTI_PRODUZIONE_ENERGIA")
	private String impiantiProduzioneEnergia;
	
	//@Column(name="LINEE_TELEF_ELETTRICHE")
	private String lineeTelefElettriche;	          
	                       
	//@Column(name="INFRASTUTTURE_PRIMARIE")
	private String infrastrutturePrimarie;
	  
	//@Column(name="MIGLIORAMENTI_FONDIARI")
	private String miglioramentiFondiari;
	
	//@Column(name="RIMESSA_IN_PRISTINO")
	private String rimessaInPristino;
	
	//@Column(name="RIMESSA_IN_PRISTINO_DETT")
	private String rimessaInPristinoDett;

	//@Column(name="DEMOLIZIONE")
	private String demolizione;	           
	
	//@Column(name="NUOVI_INSED_AREA_URB")
	private String nuoviInsedAreaUrb;
	
	//@Column(name="NUOVI_INSED_AREA_RURALI")
	private String nuoviInsedAreaRurali;


	//@Column(name="RISTR_MANUF_RURALI_SECCO")
	private String ristrManufRuraliSecco;
	
	//@Column(name="RISTR_MANUF_RURALI_NO_SECCO")
	private String ristrManufRuraliNoSecco;
	
	//@Column(name="ALTRO")
	private String altro;
	
	//@Column(name="ALTRO_SPECIFICARE")
	private String altroSpecificare;
	
	//@Column(name="TEMP_PERM") T=Temporaneo    F=Permanente Fisso    R=Permanente Rimovibile(laddove previsto)
	//QUESTO FLAG DEVE ESSERE VALORIZZATO A "T" PER INTERVENTI A CARATTERE TEMPORANEO/STAGIONALE, A "F" PER INTERVENTI A CARATTERE PERMANENTE DI TIPO FISSO OPPURE "R" PER INTERVENTI A CARATTERE PERMANENTE DI TIPO RIMOVIBILE
	private String tempPerm;
	
	//@Column(name="PROG")
	private long prog;

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getNuoviInsediamentiCiviliRurali() {
		return nuoviInsediamentiCiviliRurali;
	}

	public void setNuoviInsediamentiCiviliRurali(String nuoviInsediamentiCiviliRurali) {
		this.nuoviInsediamentiCiviliRurali = nuoviInsediamentiCiviliRurali;
	}

	public String getRistrInsedCiviliRurali() {
		return ristrInsedCiviliRurali;
	}

	public void setRistrInsedCiviliRurali(String ristrInsedCiviliRurali) {
		this.ristrInsedCiviliRurali = ristrInsedCiviliRurali;
	}

	public String getNuoviInsedIndCommerc() {
		return nuoviInsedIndCommerc;
	}

	public void setNuoviInsedIndCommerc(String nuoviInsedIndCommerc) {
		this.nuoviInsedIndCommerc = nuoviInsedIndCommerc;
	}

	public String getRistrInsedIndCommerc() {
		return ristrInsedIndCommerc;
	}

	public void setRistrInsedIndCommerc(String ristrInsedIndCommerc) {
		this.ristrInsedIndCommerc = ristrInsedIndCommerc;
	}

	public String getRecinzioni() {
		return recinzioni;
	}

	public void setRecinzioni(String recinzioni) {
		this.recinzioni = recinzioni;
	}

	public String getImpiantiProduzioneEnergia() {
		return impiantiProduzioneEnergia;
	}

	public void setImpiantiProduzioneEnergia(String impiantiProduzioneEnergia) {
		this.impiantiProduzioneEnergia = impiantiProduzioneEnergia;
	}

	public String getLineeTelefElettriche() {
		return lineeTelefElettriche;
	}

	public void setLineeTelefElettriche(String lineeTelefElettriche) {
		this.lineeTelefElettriche = lineeTelefElettriche;
	}

	public String getInfrastrutturePrimarie() {
		return infrastrutturePrimarie;
	}

	public void setInfrastrutturePrimarie(String infrastrutturePrimarie) {
		this.infrastrutturePrimarie = infrastrutturePrimarie;
	}

	public String getMiglioramentiFondiari() {
		return miglioramentiFondiari;
	}

	public void setMiglioramentiFondiari(String miglioramentiFondiari) {
		this.miglioramentiFondiari = miglioramentiFondiari;
	}

	public String getRimessaInPristino() {
		return rimessaInPristino;
	}

	public void setRimessaInPristino(String rimessaInPristino) {
		this.rimessaInPristino = rimessaInPristino;
	}

	public String getRimessaInPristinoDett() {
		return rimessaInPristinoDett;
	}

	public void setRimessaInPristinoDett(String rimessaInPristinoDett) {
		this.rimessaInPristinoDett = rimessaInPristinoDett;
	}

	public String getDemolizione() {
		return demolizione;
	}

	public void setDemolizione(String demolizione) {
		this.demolizione = demolizione;
	}

	public String getNuoviInsedAreaUrb() {
		return nuoviInsedAreaUrb;
	}

	public void setNuoviInsedAreaUrb(String nuoviInsedAreaUrb) {
		this.nuoviInsedAreaUrb = nuoviInsedAreaUrb;
	}

	public String getNuoviInsedAreaRurali() {
		return nuoviInsedAreaRurali;
	}

	public void setNuoviInsedAreaRurali(String nuoviInsedAreaRurali) {
		this.nuoviInsedAreaRurali = nuoviInsedAreaRurali;
	}

	public String getRistrManufRuraliSecco() {
		return ristrManufRuraliSecco;
	}

	public void setRistrManufRuraliSecco(String ristrManufRuraliSecco) {
		this.ristrManufRuraliSecco = ristrManufRuraliSecco;
	}

	public String getRistrManufRuraliNoSecco() {
		return ristrManufRuraliNoSecco;
	}

	public void setRistrManufRuraliNoSecco(String ristrManufRuraliNoSecco) {
		this.ristrManufRuraliNoSecco = ristrManufRuraliNoSecco;
	}

	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
	}

	public String getAltroSpecificare() {
		return altroSpecificare;
	}

	public void setAltroSpecificare(String altroSpecificare) {
		this.altroSpecificare = altroSpecificare;
	}

	/**
	 * T=Temporaneo    F=Permanente Fisso    R=Permanente Rimovibile(laddove previsto)
	 * @autor Adriano Colaianni
	 * @date 26 apr 2021
	 * @return
	 */
	public String getTempPerm() {
		return tempPerm;
	}

	public void setTempPerm(String tempPerm) {
		this.tempPerm = tempPerm;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	@Override
	public String toString() {
		return "PptrCaratterizzazioneIntervento [tPraticaApptrId=" + tPraticaApptrId
				+ ", nuoviInsediamentiCiviliRurali=" + nuoviInsediamentiCiviliRurali + ", ristrInsedCiviliRurali="
				+ ristrInsedCiviliRurali + ", nuoviInsedIndCommerc=" + nuoviInsedIndCommerc + ", ristrInsedIndCommerc="
				+ ristrInsedIndCommerc + ", recinzioni=" + recinzioni + ", impiantiProduzioneEnergia="
				+ impiantiProduzioneEnergia + ", lineeTelefElettriche=" + lineeTelefElettriche
				+ ", infrastrutturePrimarie=" + infrastrutturePrimarie + ", miglioramentiFondiari="
				+ miglioramentiFondiari + ", rimessaInPristino=" + rimessaInPristino + ", rimessaInPristinoDett="
				+ rimessaInPristinoDett + ", demolizione=" + demolizione + ", nuoviInsedAreaUrb=" + nuoviInsedAreaUrb
				+ ", nuoviInsedAreaRurali=" + nuoviInsedAreaRurali + ", ristrManufRuraliSecco=" + ristrManufRuraliSecco
				+ ", ristrManufRuraliNoSecco=" + ristrManufRuraliNoSecco + ", altro=" + altro + ", altroSpecificare="
				+ altroSpecificare + ", tempPerm=" + tempPerm + ", prog=" + prog + "]";
	}
	
	
}
