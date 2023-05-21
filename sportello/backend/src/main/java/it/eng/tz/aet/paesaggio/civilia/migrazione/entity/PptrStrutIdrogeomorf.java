package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;
/**
 * @Table( name="TNO_PPTR_STRUT_IDROGEOMORF" )
 * @author acolaianni
 *
 */
public class PptrStrutIdrogeomorf {
	//@Column( name="STRUT_IDROGEOMORF_ID" )
	private long id;
	
	/** The codice pratica appptr. */
	//@Column( name="CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;

	/** The geo ucp cordoni dunari. */
	//@Column( name="GEO_UCP_CORDONI_DUNARI" )
	private String geoUcpCordoniDunari;

	/** The geo ucp doline. */
	//@Column( name="GEO_UCP_DOLINE" )
	private String geoUcpDoline;

	/** The geo ucp geositi. */
	//@Column( name="GEO_UCP_GEOSITI" )
	private String geoUcpGeositi;

	/** The geo ucp grotte. */
	//@Column( name="GEO_UCP_GROTTE" )
	private String geoUcpGrotte;

	/** The geo ucp inghiottitoi. */
	//@Column( name="GEO_UCP_INGHIOTTITOI" )
	private String geoUcpInghiottitoi;

	/** The geo ucp lame gravine. */
	//@Column( name="GEO_UCP_LAME_GRAVINE" )
	private String geoUcpLameGravine;

	/** The geo ucp versanti. */
	//@Column( name="GEO_UCP_VERSANTI" )
	private String geoUcpVersanti;

	/** The idro bp corsi acqua. */
	//@Column( name="IDRO_BP_CORSI_ACQUA" )
	private String idroBpCorsiAcqua;

	/** The idro bp corsi acqua specif. */
	//@Column( name="IDRO_BP_CORSI_ACQUA_SPECIF" )
	private String idroBpCorsiAcquaSpecif;

	/** The idro bp territ conterm laghi. */
	//@Column( name="IDRO_BP_TERRIT_CONTERM_LAGHI" )
	private String idroBpTerritContermLaghi;

	/** The idro bp territ costieri. */
	//@Column( name="IDRO_BP_TERRIT_COSTIERI" )
	private String idroBpTerritCostieri;

	/** The idro ucp aree sogg vinc. */
	//@Column( name="IDRO_UCP_AREE_SOGG_VINC" )
	private String idroUcpAreeSoggVinc;

	/** The idro ucp reticolo idrografico. */
	//@Column( name="IDRO_UCP_RETICOLO_IDROGRAFICO" )
	private String idroUcpReticoloIdrografico;

	/** The idro ucp sorgenti. */
	//@Column( name="IDRO_UCP_SORGENTI" )
	private String idroUcpSorgenti;

	/** The t pratica apptr id. */
	//@Column( name="T_PRATICA_APPTR_ID" )
	private long tPraticaApptrId;
	
	/** The prog. */
	//@Column( name="PROG" )
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

	public String getGeoUcpCordoniDunari() {
		return geoUcpCordoniDunari;
	}

	public void setGeoUcpCordoniDunari(String geoUcpCordoniDunari) {
		this.geoUcpCordoniDunari = geoUcpCordoniDunari;
	}

	public String getGeoUcpDoline() {
		return geoUcpDoline;
	}

	public void setGeoUcpDoline(String geoUcpDoline) {
		this.geoUcpDoline = geoUcpDoline;
	}

	public String getGeoUcpGeositi() {
		return geoUcpGeositi;
	}

	public void setGeoUcpGeositi(String geoUcpGeositi) {
		this.geoUcpGeositi = geoUcpGeositi;
	}

	public String getGeoUcpGrotte() {
		return geoUcpGrotte;
	}

	public void setGeoUcpGrotte(String geoUcpGrotte) {
		this.geoUcpGrotte = geoUcpGrotte;
	}

	public String getGeoUcpInghiottitoi() {
		return geoUcpInghiottitoi;
	}

	public void setGeoUcpInghiottitoi(String geoUcpInghiottitoi) {
		this.geoUcpInghiottitoi = geoUcpInghiottitoi;
	}

	public String getGeoUcpLameGravine() {
		return geoUcpLameGravine;
	}

	public void setGeoUcpLameGravine(String geoUcpLameGravine) {
		this.geoUcpLameGravine = geoUcpLameGravine;
	}

	public String getGeoUcpVersanti() {
		return geoUcpVersanti;
	}

	public void setGeoUcpVersanti(String geoUcpVersanti) {
		this.geoUcpVersanti = geoUcpVersanti;
	}

	public String getIdroBpCorsiAcqua() {
		return idroBpCorsiAcqua;
	}

	public void setIdroBpCorsiAcqua(String idroBpCorsiAcqua) {
		this.idroBpCorsiAcqua = idroBpCorsiAcqua;
	}

	public String getIdroBpCorsiAcquaSpecif() {
		return idroBpCorsiAcquaSpecif;
	}

	public void setIdroBpCorsiAcquaSpecif(String idroBpCorsiAcquaSpecif) {
		this.idroBpCorsiAcquaSpecif = idroBpCorsiAcquaSpecif;
	}

	public String getIdroBpTerritContermLaghi() {
		return idroBpTerritContermLaghi;
	}

	public void setIdroBpTerritContermLaghi(String idroBpTerritContermLaghi) {
		this.idroBpTerritContermLaghi = idroBpTerritContermLaghi;
	}

	public String getIdroBpTerritCostieri() {
		return idroBpTerritCostieri;
	}

	public void setIdroBpTerritCostieri(String idroBpTerritCostieri) {
		this.idroBpTerritCostieri = idroBpTerritCostieri;
	}

	public String getIdroUcpAreeSoggVinc() {
		return idroUcpAreeSoggVinc;
	}

	public void setIdroUcpAreeSoggVinc(String idroUcpAreeSoggVinc) {
		this.idroUcpAreeSoggVinc = idroUcpAreeSoggVinc;
	}

	public String getIdroUcpReticoloIdrografico() {
		return idroUcpReticoloIdrografico;
	}

	public void setIdroUcpReticoloIdrografico(String idroUcpReticoloIdrografico) {
		this.idroUcpReticoloIdrografico = idroUcpReticoloIdrografico;
	}

	public String getIdroUcpSorgenti() {
		return idroUcpSorgenti;
	}

	public void setIdroUcpSorgenti(String idroUcpSorgenti) {
		this.idroUcpSorgenti = idroUcpSorgenti;
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
