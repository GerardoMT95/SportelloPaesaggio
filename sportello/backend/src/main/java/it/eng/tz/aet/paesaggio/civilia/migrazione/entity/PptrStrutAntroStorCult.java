package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

/**
 * @Table( name="TNO_PPTR_STRUT_ANTRO_STOR_CULT" )
 * @author acolaianni
 *
 */
public class PptrStrutAntroStorCult {
	
	//@Col( name="TNO_PPTR_STR_ANTRO_STOCULT_ID" )
	private long id;
	
	//@Col( name="CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;

	//@Col( name="CULT_BP_INTERESSE_ARCHEO" )
	private String cultBpInteresseArcheo;

	//@Col( name="CULT_BP_INTERESSE_PUBB" )
	private String cultBpInteressePubb;

	//@Col( name="CULT_BP_INTERESSE_PUBB_SPECIF" )
	private String cultBpInteressePubbSpecif;

	//@Col( name="CULT_BP_USI_CIVICI" )
	private String cultBpUsiCivici;

	//@Col( name="CULT_UCP__PAESAG_RURALI" )
	private String cultUcpPaesagRurali;

	//@Col( name="CULT_UCP_CITTA_CONSOLIDATA" )
	private String cultUcpCittaConsolidata;

	//@Col( name="CULT_UCP_CONI_VISUALI" )
	private String cultUcpConiVisuali;

	//@Col( name="CULT_UCP_PUNTI_PANORAMICI" )
	private String cultUcpPuntiPanoramici;

	//@Col( name="CULT_UCP_RISP_COMPON_INSEDIAT" )
	private String cultUcpRispComponInsediat;

	//@Col( name="CULT_UCP_STR_INS_RISK_ARC_SPEC" )
	private String cultUcpStrInsRiskArcSpec;

	//@Col( name="CULT_UCP_STRADE_PANORAMICHE" )
	private String cultUcpStradePanoramiche;

	//@Col( name="CULT_UCP_STRAT_INSED_ARC_SPEC" )
	private String cultUcpStratInsedArcSpec;

	//@Col( name="CULT_UCP_STRAT_INSED_ARCHEO" )
	private String cultUcpStratInsedArcheo;

	//@Col( name="CULT_UCP_STRAT_INSED_RISK_ARC" )
	private String cultUcpStratInsedRiskArc;

	//@Col( name="CULT_UCP_STRAT_RETE_TRATTURI" )
	private String cultUcpStratReteTratturi;

	//@Col( name="CULT_UCP_STRAT_TRATTURI_SPECIF" )
	private String cultUcpStratTratturiSpecif;

	//@Col( name="PERC_UCP_STRADE_VALENZA_PAESAG" )
	private String percUcpStradeValenzaPaesag;

	//@Col( name="T_PRATICA_APPTR_ID" )
	private long tPraticaApptrId;
	
	//@Col( name="PROG" )
	private long prog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getCultBpInteresseArcheo() {
		return cultBpInteresseArcheo;
	}

	public void setCultBpInteresseArcheo(String cultBpInteresseArcheo) {
		this.cultBpInteresseArcheo = cultBpInteresseArcheo;
	}

	public String getCultBpInteressePubb() {
		return cultBpInteressePubb;
	}

	public void setCultBpInteressePubb(String cultBpInteressePubb) {
		this.cultBpInteressePubb = cultBpInteressePubb;
	}

	public String getCultBpInteressePubbSpecif() {
		return cultBpInteressePubbSpecif;
	}

	public void setCultBpInteressePubbSpecif(String cultBpInteressePubbSpecif) {
		this.cultBpInteressePubbSpecif = cultBpInteressePubbSpecif;
	}

	public String getCultBpUsiCivici() {
		return cultBpUsiCivici;
	}

	public void setCultBpUsiCivici(String cultBpUsiCivici) {
		this.cultBpUsiCivici = cultBpUsiCivici;
	}

	public String getCultUcpPaesagRurali() {
		return cultUcpPaesagRurali;
	}

	public void setCultUcpPaesagRurali(String cultUcpPaesagRurali) {
		this.cultUcpPaesagRurali = cultUcpPaesagRurali;
	}

	public String getCultUcpCittaConsolidata() {
		return cultUcpCittaConsolidata;
	}

	public void setCultUcpCittaConsolidata(String cultUcpCittaConsolidata) {
		this.cultUcpCittaConsolidata = cultUcpCittaConsolidata;
	}

	public String getCultUcpConiVisuali() {
		return cultUcpConiVisuali;
	}

	public void setCultUcpConiVisuali(String cultUcpConiVisuali) {
		this.cultUcpConiVisuali = cultUcpConiVisuali;
	}

	public String getCultUcpPuntiPanoramici() {
		return cultUcpPuntiPanoramici;
	}

	public void setCultUcpPuntiPanoramici(String cultUcpPuntiPanoramici) {
		this.cultUcpPuntiPanoramici = cultUcpPuntiPanoramici;
	}

	public String getCultUcpRispComponInsediat() {
		return cultUcpRispComponInsediat;
	}

	public void setCultUcpRispComponInsediat(String cultUcpRispComponInsediat) {
		this.cultUcpRispComponInsediat = cultUcpRispComponInsediat;
	}

	public String getCultUcpStrInsRiskArcSpec() {
		return cultUcpStrInsRiskArcSpec;
	}

	public void setCultUcpStrInsRiskArcSpec(String cultUcpStrInsRiskArcSpec) {
		this.cultUcpStrInsRiskArcSpec = cultUcpStrInsRiskArcSpec;
	}

	public String getCultUcpStradePanoramiche() {
		return cultUcpStradePanoramiche;
	}

	public void setCultUcpStradePanoramiche(String cultUcpStradePanoramiche) {
		this.cultUcpStradePanoramiche = cultUcpStradePanoramiche;
	}

	public String getCultUcpStratInsedArcSpec() {
		return cultUcpStratInsedArcSpec;
	}

	public void setCultUcpStratInsedArcSpec(String cultUcpStratInsedArcSpec) {
		this.cultUcpStratInsedArcSpec = cultUcpStratInsedArcSpec;
	}

	public String getCultUcpStratInsedArcheo() {
		return cultUcpStratInsedArcheo;
	}

	public void setCultUcpStratInsedArcheo(String cultUcpStratInsedArcheo) {
		this.cultUcpStratInsedArcheo = cultUcpStratInsedArcheo;
	}

	public String getCultUcpStratInsedRiskArc() {
		return cultUcpStratInsedRiskArc;
	}

	public void setCultUcpStratInsedRiskArc(String cultUcpStratInsedRiskArc) {
		this.cultUcpStratInsedRiskArc = cultUcpStratInsedRiskArc;
	}

	public String getCultUcpStratReteTratturi() {
		return cultUcpStratReteTratturi;
	}

	public void setCultUcpStratReteTratturi(String cultUcpStratReteTratturi) {
		this.cultUcpStratReteTratturi = cultUcpStratReteTratturi;
	}

	public String getCultUcpStratTratturiSpecif() {
		return cultUcpStratTratturiSpecif;
	}

	public void setCultUcpStratTratturiSpecif(String cultUcpStratTratturiSpecif) {
		this.cultUcpStratTratturiSpecif = cultUcpStratTratturiSpecif;
	}

	public String getPercUcpStradeValenzaPaesag() {
		return percUcpStradeValenzaPaesag;
	}

	public void setPercUcpStradeValenzaPaesag(String percUcpStradeValenzaPaesag) {
		this.percUcpStradeValenzaPaesag = percUcpStradeValenzaPaesag;
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
