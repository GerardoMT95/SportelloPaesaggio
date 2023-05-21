package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

/**
 * @Table( name="TNO_PPTR_STRUT_ECOSISTEMICA" )
 * @author acolaianni
 *
 */
public class PptrStrutEcosistemica {
	
	//@Col( name="STRUT_ECOSISTEMICA_ID" )
	private long id;
	
	/** The bot bp boschi. */
	//@Col( name="BOT_BP_BOSCHI" )
	private String botBpBoschi;

	/** The bot bp z umide ramsar specif. */
	//@Col( name="BOT_BP_Z_UMIDE_RAMSAR_SPECIF" )
	private String botBpZUmideRamsarSpecif;

	/** The bot bp zone umide ramsar. */
	//@Col( name="BOT_BP_ZONE_UMIDE_RAMSAR" )
	private String botBpZoneUmideRamsar;

	/** The bot ucp aree risp boschi. */
	//@Col( name="BOT_UCP_AREE_RISP_BOSCHI" )
	private String botUcpAreeRispBoschi;

	/** The bot ucp aree umide. */
	//@Col( name="BOT_UCP_AREE_UMIDE" )
	private String botUcpAreeUmide;

	/** The bot ucp aree umide specif. */
	//@Col( name="BOT_UCP_AREE_UMIDE_SPECIF" )
	private String botUcpAreeUmideSpecif;

	/** The bot ucp form arbustive. */
	//@Col( name="BOT_UCP_FORM_ARBUSTIVE" )
	private String botUcpFormArbustive;

	/** The bot ucp prati pascoli. */
	//@Col( name="BOT_UCP_PRATI_PASCOLI" )
	private String botUcpPratiPascoli;

	/** The codice pratica appptr. */
	//@Col( name="CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;

	/** The nat ucp area risp parchi. */
	//@Col( name="NAT_UCP_AREA_RISP_PARCHI" )
	private String natUcpAreaRispParchi;

	/** The nat ucp siti ril natur specif. */
	//@Col( name="NAT_UCP_SITI_RIL_NATUR_SPECIF" )
	private String natUcpSitiRilNaturSpecif;

	/** The nat ucp siti ril natural. */
	//@Col( name="NAT_UCP_SITI_RIL_NATURAL" )
	private String natUcpSitiRilNatural;

	/** The t pratica apptr id. */
	//@Col( name="T_PRATICA_APPTR_ID" )
	private long tPraticaApptrId;
	
	/** The prog. */
	//@Col( name="PROG" )
	private long prog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBotBpBoschi() {
		return botBpBoschi;
	}

	public void setBotBpBoschi(String botBpBoschi) {
		this.botBpBoschi = botBpBoschi;
	}

	public String getBotBpZUmideRamsarSpecif() {
		return botBpZUmideRamsarSpecif;
	}

	public void setBotBpZUmideRamsarSpecif(String botBpZUmideRamsarSpecif) {
		this.botBpZUmideRamsarSpecif = botBpZUmideRamsarSpecif;
	}

	public String getBotBpZoneUmideRamsar() {
		return botBpZoneUmideRamsar;
	}

	public void setBotBpZoneUmideRamsar(String botBpZoneUmideRamsar) {
		this.botBpZoneUmideRamsar = botBpZoneUmideRamsar;
	}

	public String getBotUcpAreeRispBoschi() {
		return botUcpAreeRispBoschi;
	}

	public void setBotUcpAreeRispBoschi(String botUcpAreeRispBoschi) {
		this.botUcpAreeRispBoschi = botUcpAreeRispBoschi;
	}

	public String getBotUcpAreeUmide() {
		return botUcpAreeUmide;
	}

	public void setBotUcpAreeUmide(String botUcpAreeUmide) {
		this.botUcpAreeUmide = botUcpAreeUmide;
	}

	public String getBotUcpAreeUmideSpecif() {
		return botUcpAreeUmideSpecif;
	}

	public void setBotUcpAreeUmideSpecif(String botUcpAreeUmideSpecif) {
		this.botUcpAreeUmideSpecif = botUcpAreeUmideSpecif;
	}

	public String getBotUcpFormArbustive() {
		return botUcpFormArbustive;
	}

	public void setBotUcpFormArbustive(String botUcpFormArbustive) {
		this.botUcpFormArbustive = botUcpFormArbustive;
	}

	public String getBotUcpPratiPascoli() {
		return botUcpPratiPascoli;
	}

	public void setBotUcpPratiPascoli(String botUcpPratiPascoli) {
		this.botUcpPratiPascoli = botUcpPratiPascoli;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getNatUcpAreaRispParchi() {
		return natUcpAreaRispParchi;
	}

	public void setNatUcpAreaRispParchi(String natUcpAreaRispParchi) {
		this.natUcpAreaRispParchi = natUcpAreaRispParchi;
	}

	public String getNatUcpSitiRilNaturSpecif() {
		return natUcpSitiRilNaturSpecif;
	}

	public void setNatUcpSitiRilNaturSpecif(String natUcpSitiRilNaturSpecif) {
		this.natUcpSitiRilNaturSpecif = natUcpSitiRilNaturSpecif;
	}

	public String getNatUcpSitiRilNatural() {
		return natUcpSitiRilNatural;
	}

	public void setNatUcpSitiRilNatural(String natUcpSitiRilNatural) {
		this.natUcpSitiRilNatural = natUcpSitiRilNatural;
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
