package it.eng.tz.aet.paesaggio.civilia.migrazione.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.aet.paesaggio.civilia.migrazione.IDataSourceCiviliaConstants;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.AutPaesPptrLocalizInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMail;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMailAllegati;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.Comuni_completo_cod_istat;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.DocAmmVPratiche;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.EnteParcoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.InteressePubblicoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PaesaggioRuraleAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAltreDichiarRich;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrApprovato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAssoggProcedEdil;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAttiAcquisiti;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrCaratterizzazioneIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDescrIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDestUrbInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDisclaimerXPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrInquadramentoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrIstanzaStampe;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittProvved;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittTitoli;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittimita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrMailInviate;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParereSop;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParticelleCatastali;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProcedimentiContenzioso;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocollo;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloIstanza;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloUscita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProvvedimento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrQualificIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiDoc;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiProgetto;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrRelazioneEnte;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrSchedaAllegato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStatoEffMitigaz;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrumentiUrbanistici;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutAntroStorCult;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutEcosistemica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutIdrogeomorf;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTipoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTitolarita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrVincoliArchArch;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.TPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.TkeDocAttr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAllegatiPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAttivitaPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.AutPaesPptrLocalizIntervRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.CiviliaMailAllegatiRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.CiviliaMailRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.Comuni_completo_cod_istatRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.DocAmmVPraticheRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.EnteParcoAssRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.InteressePubblicoAssRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PaesaggioRuraleAssRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrAltreDichiarRichRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrApprovatoCiviliaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrAssoggProcedEdilRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrAttiAcquisitiRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrCaratterizzazioneInterventoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrDescrInterventoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrDestUrbIntervRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrDisclaimerXPraticaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrInquadramentoInterventoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrIstanzaStampeRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrLegittProvvedRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrLegittTitoliRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrLegittimitaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrMailInviateRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrParereSopRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrParticelleCatastaliRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrProcedimentiContenziosoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrProtocolloIstanzaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrProtocolloRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrProtocolloUscitaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrProvvedimentoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrQualificInterventoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrReferentiDocRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrReferentiProgettoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrRelazioneEnteRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrSchedaAllegatoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrStatoEffMitigazRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrStrumentiUrbanisticiRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrStrutAntroStorCultRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrStrutEcosistemicaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrStrutIdrogeomorfRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrTipoInterventoRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrTitolaritaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.PptrVincoliArchArchRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.TPraticaRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.TkeDocAttrRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.VtnoAllegatiPptrRowMapper;
import it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper.VtnoAttivitaPptrRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.GenericDao;
import it.eng.tz.puglia.util.list.ListUtil;

@Repository
public class PraticaCiviliaRepository extends GenericDao {
	@Autowired(required = false)
	@Qualifier(IDataSourceCiviliaConstants.ORACLE_CIV_NAMEDPARAMETERJDBCTEMPLATE_SPRING_BEAN_NAME)
	protected NamedParameterJdbcTemplate namedJdbcTemplateCiv;

	final private String CIVILIA_CS = "CIVILIA_CS.";
	final private String PSITPIANI = "PSITPIANI.";

	private static final Logger log = LoggerFactory.getLogger(PraticaCiviliaRepository.class);

	@PostConstruct
	private void postConstruct() {
		if (namedJdbcTemplateCiv == null) {
			log.info("datasource migration civilia non impostato!");
			return;
		}
		this.namedJdbcTemplateWrite = this.namedJdbcTemplateCiv;
		this.namedJdbcTemplateRead = this.namedJdbcTemplateCiv;
		this.jdbcTemplateWrite = this.namedJdbcTemplateWrite.getJdbcTemplate();
		this.jdbcTemplateRead = this.namedJdbcTemplateRead.getJdbcTemplate();
	}

	// lato cittadino sportello
	// elenco pratiche nella dashboard del cittadino
	// String queryString = "Select p from VtnoAttivitaPptr p where p.jbpUname =
	// :jbpUname and p.soloTrasmissione = '0' ";
	public List<VtnoAttivitaPptr> getListVtnoAttivitaPptr_Cittadino(String jbpUname) {
		final String sql = "SELECT * FROM " + CIVILIA_CS + "VTNO_ATTIVITA_PPTR WHERE "
				+ " JBP_UNAME= ?  AND SOLO_TRASMISSIONE=0 ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(jbpUname);
		List<VtnoAttivitaPptr> list = super.queryForList(sql.toString(), new VtnoAttivitaPptrRowMapper(), parameters);
		return list;

	}

	// lato cittadino sportello
	// elenco pratiche nella dashboard del cittadino
	// String queryString = "Select p from VtnoAttivitaPptr p where
	// p.soloTrasmissione = '0' ";
	public List<VtnoAttivitaPptr> getListVtnoAttivitaPptr() {
		final String sql = "SELECT * FROM " + CIVILIA_CS + "VTNO_ATTIVITA_PPTR WHERE " + " SOLOTRASMISSIONE=0 ";
		List<Object> parameters = new ArrayList<>();
		List<VtnoAttivitaPptr> list = super.queryForList(sql.toString(), new VtnoAttivitaPptrRowMapper(), parameters);
		return list;
	}

	public List<Comuni_completo_cod_istat> getComuniCompletoCodIstat() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "COMUNI_COMPLETO_COD_ISTAT ");
		List<Object> parameters = new ArrayList<>();
		return super.queryForList(sb.toString(), new Comuni_completo_cod_istatRowMapper(), parameters);
	}

	public Long getMaxProgCittadino(String codicePraticaAppptr) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MAX(PROG_CITTADINO)  ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_PROG WHERE CODICE_PRATICA_APPPTR=? ");
		return super.queryForObject(sb.toString(), Long.class, codicePraticaAppptr);
	}

	public Long getProvvedimentoSanatoria(String codicePraticaAppptr, Long progr) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT PROVVEDIMENTO_SANATORIA  ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_PRATICA WHERE CODICE_PRATICA_APPPTR=?  AND PROG=? ");
		return super.queryForObject(sb.toString(), Long.class, codicePraticaAppptr, progr);
	}

	public TPratica getTPratica(String codiceAppptr) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "T_PRATICA WHERE CODICE=? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codiceAppptr);
		List<TPratica> ret = super.queryForList(sb.toString(), new TPraticaRowMapper(), parameters);
		if (ListUtil.isNotEmpty(ret)) {
			return ret.get(0);
		}
		return null;
	}

	public PptrIstanzaStampe getPptrIstanzaStampe(String codiceAppptr, Long progr) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_ISTANZA_STAMPE  WHERE CODICE_PRATICA_APPPTR=?  AND PROG=?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codiceAppptr);
		parameters.add(progr);
		List<PptrIstanzaStampe> ret = super.queryForList(sb.toString(), new PptrIstanzaStampeRowMapper(), parameters);
		if (ListUtil.isNotEmpty(ret)) {
			return ret.get(0);
		}
		return null;
	}

//	SELECT NUMERO_PROTOCOLLO,DATA_PROTOCOLLO,TITOLARIO_PROTOCOLLO,DATA_INVIO_PEC,ESITO 
//	FROM CIVILIA_CS.TNO_PPTR_RICEVUTA_ISTANZA ri,CIVILIA_CS.TNO_PPTR_PROTOCOLLO prot WHERE ;

	public PptrProtocolloIstanza getPptrProtocolloIstanza(String codiceAppptr, Long progr) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT NUMERO_PROTOCOLLO,DATA_PROTOCOLLO,TITOLARIO_PROTOCOLLO,DATA_INVIO_PEC,ESITO ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_RICEVUTA_ISTANZA ri, ")
				.append(CIVILIA_CS + "TNO_PPTR_PROTOCOLLO  prot ").append(" WHERE ")
				.append(" prot.TNO_PPTR_PROTOCOLLO_ID =ri.TNO_PPTR_PROTOCOLLO_ID ")
				.append(" AND prot.CODICE_PRATICA_APPPTR=?  AND prot.PROG=? AND ri.ESITO='S' ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codiceAppptr);
		parameters.add(progr);
		List<PptrProtocolloIstanza> ret = super.queryForList(sb.toString(), new PptrProtocolloIstanzaRowMapper(),
				parameters);
		if (ListUtil.isNotEmpty(ret)) {
			return ret.get(0);
		}
		return null;
	}

	public List<PptrReferentiProgetto> getReferenti(Long tPraticaId, Long progr) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "TNO_PPTR_REFERENTI_PROGETTO ri ").append(" WHERE ")
				.append(" T_PRATICA_APPTR_ID=?  AND PROG=?  ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaId);
		parameters.add(progr);
		List<PptrReferentiProgetto> ret = super.queryForList(sb.toString(), new PptrReferentiProgettoRowMapper(),
				parameters);
		return ret;
	}

	// NOME_FILE FORMATO_FILE CONTENUTO_FILE DATA_CARICAMENTO_FILE TIPO_DOC_IDENT_ID
	// NUM_DOC_IDENT DATA_DOC_IDENT ENTE_RIL_DOC_IDENT
	public List<PptrReferentiDoc> getDocumento(Long treferenteId) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT NOME_FILE, FORMATO_FILE, CONTENUTO_FILE, DATA_CARICAMENTO_FILE, TIPO_DOC_IDENT_ID, NUM_DOC_IDENT, DATA_DOC_IDENT, ENTE_RIL_DOC_IDENT ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_REFERENTI_DOC ").append(" WHERE ")
				.append(" REFERENTE_PROGETTO_ID=? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(treferenteId);
		List<PptrReferentiDoc> ret = super.queryForList(sb.toString(), new PptrReferentiDocRowMapper(), parameters);
		return ret;
	}

	public List<PptrTitolarita> getTitolaritaReferente(String codicePraticaApptr, Long treferenteId, Long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT TNO_PPTR_TITOLARITA_ID, TIT_RUOLO_ID, TIT_RUOLO_ALTRO, TIT_ESCLUSIVO, FILE_DICH_ASSENSO, REFERENTE_PROGETTO_ID, CODICE_PRATICA_APPPTR, NOME_FILE, DATA_CARICAMENTO_FILE, FORMATO_FILE, PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_TITOLARITA ").append(" WHERE ")
				.append(" REFERENTE_PROGETTO_ID=? AND ").append(" CODICE_PRATICA_APPPTR=? AND ").append(" PROG=? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(treferenteId);
		parameters.add(codicePraticaApptr);
		parameters.add(prog);
		List<PptrTitolarita> ret = super.queryForList(sb.toString(), new PptrTitolaritaRowMapper(), parameters);
		return ret;
	}

	public List<PptrDisclaimerXPratica> getDisclaimerXPratica(String codicePraticaApptr, Long treferenteId, Long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT TNO_PPTR_DISCL_X_PRATICA_ID,T_PRATICA_APPTR_ID,FLG_DISCL_X_PRATICA,CODICE_PRATICA_APPPTR,REFERENTE_PROGETTO_ID,TNO_PPTR_DISCLAIMER_ID,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_DISCLAIMER_X_PRATICA ").append(" WHERE ")
				.append(" REFERENTE_PROGETTO_ID=? AND ").append(" CODICE_PRATICA_APPPTR=? AND ").append(" PROG=? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(treferenteId);
		parameters.add(codicePraticaApptr);
		parameters.add(prog);
		List<PptrDisclaimerXPratica> ret = super.queryForList(sb.toString(), new PptrDisclaimerXPraticaRowMapper(),
				parameters);
		return ret;
	}

	public List<PptrAltreDichiarRich> getAltreDichRich(Long tPraticaApptrId) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT ID,CODICE_PRATICA_APPPTR,PROG,T_PRATICA_APPPTR_ID,CHECK_136,CHECK_142,CHECK_142_PARCHI,CHECK_134,ENTE_RILASCIO,DESCR_AUT_RILASCIATA,DATA_RILASCIO,CHECK_136A,CHECK_136B,CHECK_136C,CHECK_136D ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_ALTRE_DICHIAR_RICH ").append(" WHERE ")
				.append(" T_PRATICA_APPPTR_ID=?  ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaApptrId);
		List<PptrAltreDichiarRich> ret = super.queryForList(sb.toString(), new PptrAltreDichiarRichRowMapper(),
				parameters);
		return ret;
	}

	public List<AutPaesPptrLocalizInterv> getLocalizzazioneIntervento(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "TNO_PPTR_LOCALIZ_INTERV ")
				.append(" WHERE CODICE_PRATICA_APPPTR = ? ").append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new AutPaesPptrLocalizIntervRowMapper(), parameters);
	}

	public List<PptrParticelleCatastali> getParticelleCatastaliLocalizInterv(long localizIntervId, long tPraticaApptrId,
			long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "TNO_PARTICELLECATASTALI_PPTR ")
				.append(" WHERE  LOCALIZ_INTERV_ID= ? AND T_PRATICA_APPPTR_ID = ? AND PROG= ?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(localizIntervId);
		parameters.add(tPraticaApptrId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrParticelleCatastaliRowMapper(), parameters);
	}

	public List<EnteParcoAss> getEnteParcoAss(String codicePratica, String codIstat, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT  a.PROG,a.TNO_ENTEPARCO_TIPOASS_ID,a.COMUNE,a.COD_CAT,m.COD_ISTAT,m.DESC_PARCO,m.MAIL,a.CODICE_PRATICA,a.T_PRATICA_ID,a.TNO_ENTEPARCO_MAP_ID ")
				.append(" FROM " + CIVILIA_CS + "TNO_ENTEPARCO_MAP m, ").append(CIVILIA_CS + "TNO_ENTEPARCO_ASS a ")
				.append(" WHERE  m.TNO_ENTEPARCO_MAP_ID=a.TNO_ENTEPARCO_MAP_ID ")
				.append(" AND a.CODICE_PRATICA= ? AND a.COD_ISTAT= ? AND a.PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePratica);
		parameters.add(codIstat);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new EnteParcoAssRowMapper(), parameters);
	}

	public List<PaesaggioRuraleAss> getPaesaggioRuraleAss(String codicePraticaApptr, String codIstat, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT  a.PROG,a.TNO_PAESAGGIRURALI_TIPOASS_ID,a.COMUNE,a.COD_CAT,m.COD_ISTAT,m.DESC_PAESAGGIO_RURALE,a.CODICE_PRATICA,a.T_PRATICA_ID,a.TNO_PAESAGGIRURALI_MAP_ID ")
				.append(" FROM " + CIVILIA_CS + "TNO_PAESAGGIRURALI_MAP m, ")
				.append(CIVILIA_CS + "TNO_PAESAGGIRURALI_ASS a ")
				.append(" WHERE  m.OBJECTID=a.TNO_PAESAGGIRURALI_MAP_ID ")
				.append(" AND a.CODICE_PRATICA= ? AND a.COD_ISTAT= ? AND a.PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaApptr);
		parameters.add(codIstat);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PaesaggioRuraleAssRowMapper(), parameters);
	}

	public List<InteressePubblicoAss> getInteressePubblicoAss(String codicePraticaApptr, String codIstat, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT  a.PROG,a.TNO_INTERESSEPUB_TIPOASS_ID,a.COMUNE,a.COD_CAT,m.COD_ISTAT,m.COD_VINCOLO,m.OGGETTO,a.CODICE_PRATICA,a.T_PRATICA_ID ")
				.append(" FROM " + CIVILIA_CS + "TNO_INTERESSEPUBBLICO_MAP m, ")
				.append(CIVILIA_CS + "TNO_INTERESSEPUBBLICO_ASS a ")
				.append(" WHERE  m.OBJECTID=a.TNO_INTERESSEPUB_MAP_ID ")
				.append(" AND a.CODICE_PRATICA= ? AND a.COD_ISTAT= ? AND a.PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaApptr);
		parameters.add(codIstat);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new InteressePubblicoAssRowMapper(), parameters);
	}

	public List<PptrTipoIntervento> getDatiTipoIntervento(long tPraticaId, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "TNO_PPTR_TIPO_INTERVENTO ")
				.append(" WHERE T_PRATICA_APPTR_ID  = ? ").append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrTipoInterventoRowMapper(), parameters);
	}

	public List<PptrCaratterizzazioneIntervento> getDatiCaratterizzazioneIntervento(long tPraticaId, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "TNO_PPTR_CARATT_INTERVENTO ")
				.append(" WHERE T_PRATICA_APPTR_ID  = ? ").append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrCaratterizzazioneInterventoRowMapper(), parameters);
	}

	public List<PptrQualificIntervento> getDatiQualificazioneIntervento(long tPraticaId, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "TNO_PPTR_QUALIFIC_INTERVENTO ")
				.append(" WHERE T_PRATICA_APPTR_ID  = ? ").append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrQualificInterventoRowMapper(), parameters);
	}

	public List<PptrDescrIntervento> getDescrIntervento(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ").append(" FROM " + CIVILIA_CS + "TNO_PPTR_DESCR_INTERVENTO ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ").append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrDescrInterventoRowMapper(), parameters);
	}

	public List<PptrDestUrbInterv> getDestinazioneUrbanisticaOldMode(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ").append(
				"TNO_PPTR_DEST_URB_INTERV_ID,T_PRATICA_APPTR_ID,STRUM_URB_APPROVATO,STRUM_URB_APPROVATO_DATA,STRUM_URB_APPROVATO_ATTO,DESTIN_AREA_STR_VIG,STRUM_APPROV_ULT_TUTELE,STRUM_URB_ADOTTATO,STRUM_URB_ADOTTATO_DATA,STRUM_URB_ADOTTATO_ATTO,DESTIN_AREA_STR_ADOTT,STRUM_ADOTT_ULT_TUTELE,CONFORME_DISCIPL_URB_VIG,CODICE_PRATICA_APPPTR,PROG,LOCALIZ_INTERV_ID,ID_STRUM_URBAN_ART100,CHECK_PRESA_VISIONE,ID_STRUM_URBAN_ART38 ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_DEST_URB_INTERV ").append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrDestUrbIntervRowMapper(), parameters);
	}

	public List<PptrDestUrbInterv> getDestinazioneUrbanistica(Long localizzazioneInterventoId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ").append(
				"TNO_PPTR_DEST_URB_INTERV_ID,T_PRATICA_APPTR_ID,STRUM_URB_APPROVATO,STRUM_URB_APPROVATO_DATA,STRUM_URB_APPROVATO_ATTO,DESTIN_AREA_STR_VIG,STRUM_APPROV_ULT_TUTELE,STRUM_URB_ADOTTATO,STRUM_URB_ADOTTATO_DATA,STRUM_URB_ADOTTATO_ATTO,DESTIN_AREA_STR_ADOTT,STRUM_ADOTT_ULT_TUTELE,CONFORME_DISCIPL_URB_VIG,CODICE_PRATICA_APPPTR,PROG,LOCALIZ_INTERV_ID,ID_STRUM_URBAN_ART100,CHECK_PRESA_VISIONE,ID_STRUM_URBAN_ART38 ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_DEST_URB_INTERV ").append(" WHERE LOCALIZ_INTERV_ID = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(localizzazioneInterventoId);
		return super.queryForList(sb.toString(), new PptrDestUrbIntervRowMapper(), parameters);
	}
	
	public List<PptrStrumentiUrbanistici> getPptrStrumentiUrbanistici(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ").append(
				" ID,ISTAT_6_PROV,TIPO_STRUMENTO,STATO,ATTO,DATA_ATTO,UTENTE_MODIFICA,DATA_MODIFICA ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_STRUMENTI_URBANISTICI ").append(" WHERE ID = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		return super.queryForList(sb.toString(), new PptrStrumentiUrbanisticiRowMapper(), parameters);
	}
	
	//LEGITTIMITA_ID,T_PRATICA_APPTR_ID,LEG_URB_TIT_EDILIZIO,LEG_URB_PRIVO_SPECIFICA,LEG_PAESAG_IMMOBILE,LEG_PAE_TIPO_VINCOLO,LEG_PAE_DATA_INTERVENTO,LEG_PAE_DATA_VINCOLO,CODICE_PRATICA_APPPTR,PROG
	public List<PptrLegittimita> getLeggittimita(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" LEGITTIMITA_ID,T_PRATICA_APPTR_ID,LEG_URB_TIT_EDILIZIO,LEG_URB_PRIVO_SPECIFICA,LEG_PAESAG_IMMOBILE,LEG_PAE_TIPO_VINCOLO,LEG_PAE_DATA_INTERVENTO,LEG_PAE_DATA_VINCOLO,CODICE_PRATICA_APPPTR,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_LEGITTIMITA ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrLegittimitaRowMapper(), parameters);
	}
	
	
	public List<PptrLegittProvved> getLeggittimitaProvv(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" LEGITT_PROVVED_ID,LEGITTIMITA_ID,LEG_PROVV_DENOMINAZIONE,LEG_PROVV_RILASCIATO_DA,LEG_PROVV_NUM_PROTOCOLLO,LEG_PROVV_DATA_RILASCIO,LEG_PROVV_INTESTATARIO,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_LEGITT_PROVVED ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrLegittProvvedRowMapper(), parameters);
	}
	
	public List<PptrLegittTitoli> getLeggittimitaTitoli(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" LEGITT_TITOLI_ID,LEGITTIMITA_ID,LEG_TIT_DENOMINAZIONE,LEG_TIT_RILASCIATO_DA,LEG_TIT_NUM_PROTOCOLLO,LEG_TIT_DATA_RILASCIO,LEG_TIT_INTESTATARIO,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_LEGITT_TITOLI ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrLegittTitoliRowMapper(), parameters);
	}
	
	public List<PptrAssoggProcedEdil> getProcedureEdilizie(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" TNO_PPTR_ASSOGG_PROCED_EDIL_ID,FLAG_ASSOGGETTATO,NO_ASSOGG_SPECIFICARE,ASSOGG_FLAG_PRATICA_IN_CORSO,ASSOGG_ENTE_PRATICA_IN_CORSO,ASSOGG_DATAPR_PRATICA_IN_CORSO,ASSOGG_FLAG_PARERE_URB_ESPR,ASSOGG_DATA_APPROV_PRATICA,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_ASSOGG_PROCED_EDIL ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrAssoggProcedEdilRowMapper(), parameters);
	}
	
	public List<PptrInquadramentoIntervento> getInquadramentoIntervento(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" INQUADRAM_INTERV_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,INQ_OPERA_CORRELATA_A,INQ_CARATTERE_INTERV,INQ_DEST_USO_INTERV,INQ_DEST_USO_INTERV_ALTRO,INQ_USO_SUOLO,INQ_USO_SUOLO_ALTRO,INQ_CONTESTO_PAESAG,INQ_MORFOLOGIA_PAESAG,PROG,INQ_CONTESTO_PAESAG_ALTRO,INQ_MORFOLOGIA_PAESAG_ALTRO ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_INQUADRAM_INTERV ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrInquadramentoInterventoRowMapper(), parameters);
	}
	
	public List<PptrStatoEffMitigaz> getStatoEffmitigazione(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" STATO_EFF_MITIGAZ_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,DESCR_STATO_ATTUALE,EFFETTI_REALIZ_OPERA,MITIGAZIONE_IMP_INTERV,PROG,INDICAZ_CONTENUTI_PERCETTIVI ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_STATO_EFF_MITIGAZ ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrStatoEffMitigazRowMapper(), parameters);
	}
	
	public List<PptrProcedimentiContenzioso> getProcedimentiContenzioso(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" TNO_PPTR_PROCED_CONTENZIOSO_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,FLAG_CONTENZIOSO_IN_ATTO,DESCRIZIONE,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_PROCED_CONTENZIOSO ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrProcedimentiContenziosoRowMapper(), parameters);
	}
	
	public List<PptrAttiAcquisiti> getPareriAtti(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" ATTO_ACQUISITO_ID,TIPOLOGIA_ATTO,AUTORITA_RILASCIO,PROTOCOLLO,DATA_RILASCIO,CODICE_PRATICA_APPPTR,FLAG_ATTO_PARERE,INTESTATARIO_ATTO,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_ATTI_ACQUISITI ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrAttiAcquisitiRowMapper(), parameters);
	}
	
	public List<PptrApprovato> getPptrApprovato(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" PPTR_APPROVATO_ID,T_PRATICA_APPTR,CODICE_PRATICA_APPTR,AMBITO_PAESAGGISTICO,FIGURE_AMBITO_PAESAGGISTICO,RICADE_TERR_ART_1_03_CO_5_6,RICADE_TERR_ART_142_CO_2,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_APPROVATO ")
				.append(" WHERE CODICE_PRATICA_APPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrApprovatoCiviliaRowMapper(), parameters);
	}
	
	public List<PptrStrutAntroStorCult> getPptrAntroStorCult(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" TNO_PPTR_STR_ANTRO_STOCULT_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,CULT_BP_INTERESSE_PUBB,CULT_BP_INTERESSE_PUBB_SPECIF,CULT_BP_USI_CIVICI,CULT_BP_INTERESSE_ARCHEO,CULT_UCP_CITTA_CONSOLIDATA,CULT_UCP_STRAT_INSED_ARCHEO,CULT_UCP_STRAT_INSED_ARC_SPEC,CULT_UCP_STRAT_RETE_TRATTURI,CULT_UCP_STRAT_TRATTURI_SPECIF,CULT_UCP_STRAT_INSED_RISK_ARC,CULT_UCP_STR_INS_RISK_ARC_SPEC,CULT_UCP_RISP_COMPON_INSEDIAT,CULT_UCP__PAESAG_RURALI,PERC_UCP_STRADE_VALENZA_PAESAG,CULT_UCP_STRADE_PANORAMICHE,CULT_UCP_PUNTI_PANORAMICI,CULT_UCP_CONI_VISUALI,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_STRUT_ANTRO_STOR_CULT ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrStrutAntroStorCultRowMapper(), parameters);
	}
	
	public List<PptrStrutEcosistemica> getPptrEcosistemica(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" STRUT_ECOSISTEMICA_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,BOT_BP_BOSCHI,BOT_BP_ZONE_UMIDE_RAMSAR,BOT_BP_Z_UMIDE_RAMSAR_SPECIF,BOT_UCP_AREE_UMIDE,BOT_UCP_AREE_UMIDE_SPECIF,BOT_UCP_PRATI_PASCOLI,BOT_UCP_FORM_ARBUSTIVE,BOT_UCP_AREE_RISP_BOSCHI,NAT_UCP_AREA_RISP_PARCHI,NAT_UCP_SITI_RIL_NATURAL,NAT_UCP_SITI_RIL_NATUR_SPECIF,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_STRUT_ECOSISTEMICA ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrStrutEcosistemicaRowMapper(), parameters);
	}
	
	
	public List<PptrStrutIdrogeomorf> getPptrIdrogeomorf(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" STRUT_IDROGEOMORF_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,GEO_UCP_VERSANTI,GEO_UCP_LAME_GRAVINE,GEO_UCP_DOLINE,GEO_UCP_GROTTE,GEO_UCP_GEOSITI,GEO_UCP_INGHIOTTITOI,GEO_UCP_CORDONI_DUNARI,IDRO_BP_TERRIT_COSTIERI,IDRO_BP_TERRIT_CONTERM_LAGHI,IDRO_BP_CORSI_ACQUA,IDRO_BP_CORSI_ACQUA_SPECIF,IDRO_UCP_RETICOLO_IDROGRAFICO,IDRO_UCP_SORGENTI,IDRO_UCP_AREE_SOGG_VINC,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_STRUT_IDROGEOMORF ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrStrutIdrogeomorfRowMapper(), parameters);
	}

	public List<PptrVincoliArchArch> getPptrVincolistica(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" VINC_ARCH_ARCH_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,VINC_ARC_NO_TUTELA,VINC_ARC_MONUM_DIRETTO,VINC_ARC_MONUM_INDIRETTO,VINC_ARC_ARCHEO_DIRETTO,VINC_ARC_ARCHEO_INDIRETTO,ALTRI_VINCOLI,PROG ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_VINC_ARCH_ARCH ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrVincoliArchArchRowMapper(), parameters);
	}
	
	
	public List<DocAmmVPratiche> getDocumentazioneAmministrativa(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" TNO_PPTR_DOC_AMM_BLOB_ID,TNO_PPTR_TIPOPROCEDIMENTO_ID,TNO_PPTR_DOC_AMM_TIPO_ID,TIPOPROCEDIMENTO,GRUPPO,LETTERA_STAMPA_LABEL,TIPO_DOCUMENTO,CARICATO_IN_ISTANZA,NOME_FILE,FORMATO_FILE,T_PRATICA_APPTR_ID,BIN_CONTENT,CODICE_PRATICA_APPPTR,PROG ")
				.append(" FROM " + CIVILIA_CS + "V_TNO_PPTR_DOC_AMM_PRATICHE ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new DocAmmVPraticheRowMapper(), parameters);
	}
	
	public List<VtnoAllegatiPptr> getDocumentazioneTecnica(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT ID,CODICE,DESCRIZIONE,T_PRATICA_ID,DATAARRIVO,NOME,NOMEFILE,TIPO,T_TIPODOC_CODICE,T_TIPODOC_DESCRIZIONE,T_TIPODOC_MODCOMPILAZIONE,T_TIPODOC_ID,NAME,BIN_CONTENT,VERSION_NOTES,T_KE_DOC_ATTR_NAME,T_KE_DOC_ATTR_VALUE,N_T_KE_DOC_ST_ID,NUMEROPROTOCOLLO,DATAPROTOCOLLO,PPTR_TIPOALLEGATO_CODICE,PPTR_TIPOALLEGATO_DESCRIZIONE,FASE_ID,PPTR_TIPOALLEGATO_ORDINE,NOMEALLEGATO,PROG,ID_ALFRESCO,PROVV ")
		.append("	FROM "+CIVILIA_CS+"VTNO_ALLEGATI_PPTR ")
		.append("	WHERE CODICE = ? AND PROG = ? ")
		.append( "	AND PPTR_TIPOALLEGATO_CODICE ")
		.append(  "	IN ")
		.append( "	(  SELECT TIPOALLEGATO_ID FROM "+CIVILIA_CS+"TNO_PPTR_TIPOALLEGATO , ")
		.append(	CIVILIA_CS+"TNO_PPTR_PRATICA ")
		.append(  "	WHERE TNO_PPTR_TIPOALLEGATO.TNO_PPTR_TIPOPROCEDIMENTO_ID = TNO_PPTR_PRATICA.TNO_PPTR_TIPOPROCEDIMENTO_ID ")
		.append( "	AND CODICE_PRATICA_APPPTR = ? AND PROVV IS NULL )");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		parameters.add(codicePraticaAppptr);
		return super.queryForList(sb.toString(), new VtnoAllegatiPptrRowMapper(), parameters);
	}
	
	public List<PptrRelazioneEnte> getRelazioneEnte(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" TNO_PPTR_RELAZIONE_ENTE_ID,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG,NUMERO_PROTOCOLLO_ENTE,FLAG_ESITO,DETTAGLIO_RELAZIONE,NOTE_ENTE,NOME_ISTRUTTORE_ENTE ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_RELAZIONE_ENTE ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrRelazioneEnteRowMapper(), parameters);
	}
	
	
	public List<VtnoAllegatiPptr> getAllegatiRelazioneEnte(String codicePraticaAppptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT ID,CODICE,DESCRIZIONE,T_PRATICA_ID,DATAARRIVO,NOME,NOMEFILE,TIPO,T_TIPODOC_CODICE,T_TIPODOC_DESCRIZIONE,T_TIPODOC_MODCOMPILAZIONE,T_TIPODOC_ID,NAME,BIN_CONTENT,VERSION_NOTES,T_KE_DOC_ATTR_NAME,T_KE_DOC_ATTR_VALUE,N_T_KE_DOC_ST_ID,NUMEROPROTOCOLLO,DATAPROTOCOLLO,PPTR_TIPOALLEGATO_CODICE,PPTR_TIPOALLEGATO_DESCRIZIONE,FASE_ID,PPTR_TIPOALLEGATO_ORDINE,NOMEALLEGATO,PROG,ID_ALFRESCO,PROVV ")
		.append("	FROM "+CIVILIA_CS+"VTNO_ALLEGATI_PPTR ")
		.append("	WHERE CODICE = ? AND PROG = ? ")
		.append( "	AND (TRIM( PROVV ) = 'RI_PA' OR TRIM( PROVV ) = 'RI_AL') ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new VtnoAllegatiPptrRowMapper(), parameters);
	}
	
	
	public List<CiviliaMail> getCiviliaEmail(long praticaId){
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT  * ")
		.append(" FROM "+CIVILIA_CS+"T_MAIL_INVIATE ")
		.append(" WHERE ")
		.append(" T_PRATICA_ID = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(praticaId);
		return super.queryForList(sb.toString(), new CiviliaMailRowMapper(),parameters);
	}
	
	public List<CiviliaMailAllegati> getCiviliaEmailAllegati(long mailInviateId){
		StringBuilder sb = new StringBuilder();
		sb
		/**
		 * SELECT mall.T_MAIL_INVIATE_ALLEGATI_ID ,mall.T_KE_DOC_ID,mall.T_MAIL_INVIATE_ID ,mall.NOME_FILE,mall.CC_TIME_STAMP ,bin.BIN_CONTENT 
FROM T_MAIL_INVIATE_ALLEGATI mall,T_KE_DOC_ST bin WHERE mall.T_KE_DOC_ID =bin.T_KE_DOC_ID;
		 */
		.append("SELECT  mall.T_MAIL_INVIATE_ALLEGATI_ID ,mall.T_KE_DOC_ID,mall.T_MAIL_INVIATE_ID ,mall.NOME_FILE,mall.CC_TIME_STAMP ,bin.BIN_CONTENT ")
		.append(" FROM "+CIVILIA_CS+"T_MAIL_INVIATE_ALLEGATI mall, ")
		.append(CIVILIA_CS+"T_KE_DOC_ST bin ")
		.append(" WHERE ")
		.append(" mall.T_KE_DOC_ID =bin.T_KE_DOC_ID and mall.T_MAIL_INVIATE_ID = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(mailInviateId);
		List<CiviliaMailAllegati> list = super.queryForList(sb.toString(), new CiviliaMailAllegatiRowMapper(),parameters);
		if(ListUtil.isNotEmpty(list)) {
			for(CiviliaMailAllegati mailAll:list) {
				mailAll.setAttributi(this.getCiviliaEmailAttr(mailAll.gettKeDocId()+""));	
			}
		}
		return list;
	}
	
	public List<TkeDocAttr> getCiviliaEmailAttr(String tKeDocId){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ")
		.append(CIVILIA_CS+"T_KE_DOC_ATTR ")
		.append("  WHERE T_KE_DOC_ID = ? ")
		;
		List<Object> parameters = new ArrayList<>();
		parameters.add(tKeDocId);
		 List<TkeDocAttr> list = super.queryForList(sb.toString(), new TkeDocAttrRowMapper(),parameters);
		return list;
	}
	
	public List<PptrMailInviate> getPptrMailInviate_by_codicePraticaApPptr_tnoGruppoComCodice_tnoTipoComVisibilita(String codicePraticaAppptr , String codice , String visibilita){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tpmi.*, ttc.TITOLO as TITOLO")
		.append(" FROM ")
		.append(CIVILIA_CS+"TNO_PPTR_MAIL_INVIATE tpmi,")
		.append(CIVILIA_CS+"TNO_TIPO_COMUNICAZIONE ttc, ")
		.append(CIVILIA_CS+"TNO_GRUPPO_TIPO_COMUNICAZIONE tgtc ")
		.append("  WHERE  ")
		.append(" tpmi.TNO_TIPO_COMUNICAZIONE_ID =ttc.TNO_TIPO_COMUNICAZIONE_ID ")
		.append(" AND ttc.TNO_GRUPPOTIPOCOMUNICAZIONE_ID =tgtc.TNO_GRUPPOTIPOCOMUNICAZIONE_ID ")
		.append(" AND tgtc.CODICE = ? ")
		.append(" AND ttc.VISIBILITA = ? ")
		.append(" AND tpmi.CODICE_PRATICA_APPPTR = ? ")
		;
		List<Object> parameters = new ArrayList<>();
		parameters.add(codice);
		parameters.add(visibilita);
		parameters.add(codicePraticaAppptr);
		List<PptrMailInviate> list = super.queryForList(sb.toString(), new PptrMailInviateRowMapper(),parameters);
		return list;
	}
	
	public List<PptrParereSop> getParereSOP(String codicePraticaAppptr,long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ")
		.append(
				" TNO_PPTR_PARERE_SOP_ID,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG,NUMERO_PROTOCOLLO_SOP,FLAG_ESITO,DETTAGLIO_PRESCRIZIONI,NOTE_PARERE_SOP,NOME_ISTRUTTORE_SOP,FLAG_PROVENIENZA,ED_PARERE_SOTTOMESSO ")
				.append(" FROM " + CIVILIA_CS + "TNO_PPTR_PARERE_SOP ")
				.append(" WHERE CODICE_PRATICA_APPPTR  = ? ")
				.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrParereSopRowMapper(), parameters);
	}
	
	public List<VtnoAllegatiPptr> getListParereSopVtnoAllegatiPptr(String codicePraticaAppptr) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT ID,CODICE,DESCRIZIONE,T_PRATICA_ID,DATAARRIVO,NOME,NOMEFILE,TIPO,T_TIPODOC_CODICE,T_TIPODOC_DESCRIZIONE,T_TIPODOC_MODCOMPILAZIONE,T_TIPODOC_ID,NAME,BIN_CONTENT,VERSION_NOTES,T_KE_DOC_ATTR_NAME,T_KE_DOC_ATTR_VALUE,N_T_KE_DOC_ST_ID,NUMEROPROTOCOLLO,DATAPROTOCOLLO,PPTR_TIPOALLEGATO_CODICE,PPTR_TIPOALLEGATO_DESCRIZIONE,FASE_ID,PPTR_TIPOALLEGATO_ORDINE,NOMEALLEGATO,PROG,ID_ALFRESCO,PROVV ")
		.append("	FROM "+CIVILIA_CS+"VTNO_ALLEGATI_PPTR ")
		.append("	WHERE CODICE = ? ")
		.append(" AND PPTR_TIPOALLEGATO_CODICE IN ")
		.append(" (  SELECT TIPOALLEGATO_ID FROM TNO_PPTR_TIPOALLEGATO , TNO_PPTR_PRATICA " ) 
		.append(" WHERE TNO_PPTR_TIPOALLEGATO.TNO_PPTR_TIPOPROCEDIMENTO_ID = TNO_PPTR_PRATICA.TNO_PPTR_TIPOPROCEDIMENTO_ID " ) 
		.append("	AND CODICE_PRATICA_APPPTR = ? AND FASE_ID = 20 " )
		.append(" AND PROVV = 'MI_SP' )");
		
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(codicePraticaAppptr);
		return super.queryForList(sb.toString(), new VtnoAllegatiPptrRowMapper(), parameters);
	}
	
	public List<VtnoAllegatiPptr> getListParereSopDaEnteDelegato(String codicePraticaAppptr,long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT ID,CODICE,DESCRIZIONE,T_PRATICA_ID,DATAARRIVO,NOME,NOMEFILE,TIPO,T_TIPODOC_CODICE,T_TIPODOC_DESCRIZIONE,T_TIPODOC_MODCOMPILAZIONE,T_TIPODOC_ID,NAME,BIN_CONTENT,VERSION_NOTES,T_KE_DOC_ATTR_NAME,T_KE_DOC_ATTR_VALUE,N_T_KE_DOC_ST_ID,NUMEROPROTOCOLLO,DATAPROTOCOLLO,PPTR_TIPOALLEGATO_CODICE,PPTR_TIPOALLEGATO_DESCRIZIONE,FASE_ID,PPTR_TIPOALLEGATO_ORDINE,NOMEALLEGATO,PROG,ID_ALFRESCO,PROVV ")
		.append("	FROM "+CIVILIA_CS+"VTNO_ALLEGATI_PPTR ")
		.append("	WHERE CODICE = ? AND PROG = ? ")
		.append( "	AND TRIM( PROVV ) = 'MI_ED'  ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new VtnoAllegatiPptrRowMapper(), parameters);
	}
	
	public List<PptrSchedaAllegato> getListaUlterioreDocumentazione(String codicePraticaAppptr) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT  ")
		.append(" SCHEDA_ALLEGATO_ID,CODICE_PRATICA_APPPTR,T_PRATICA_APPPTR_ID,TITOLO,DESCRIZIONE,NOME_FILE,ALLEGATO,CONTENTTYPE,MITTENTE,RUOLO_MITTENTE,VISIBILITA_ET,VISIBILITA_SOP,VISIBILITA_ED,VISIBILITA_RI,DESTINATARIO,DATA_INSERIMENTO,ALFRESCO_ID ")
		.append("	FROM "+CIVILIA_CS+"TNO_PPTR_SCHEDA_ALLEGATO ")
		.append("	WHERE CODICE_PRATICA_APPPTR = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		return super.queryForList(sb.toString(), new PptrSchedaAllegatoRowMapper(), parameters);
	}
	
	
	public List<PptrMailInviate> getPptrMailInviate(String codicePraticaAppptr){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TNO_PPTR_MAIL_INVIATE_ID,CODICE_PRATICA_APPPTR,CC_TIME_STAMP,DESTINATARIO,OGGETTO,TESTO,DATA_INVIO,MITTENTE,VERSO,MESSAGE_ID,EML,TNO_TIPO_COMUNICAZIONE_ID,null as TITOLO ")
		.append(" FROM ")
		.append(CIVILIA_CS+"TNO_PPTR_MAIL_INVIATE ")
		.append("  WHERE  ")
		.append(" CODICE_PRATICA_APPPTR = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		List<PptrMailInviate> list = super.queryForList(sb.toString(), new PptrMailInviateRowMapper(),parameters);
		return list;
	}
	
	public List<PptrProtocollo> getProtocolloByCodice(String codicePraticaAppptr,String codiceTipoProtocollo){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT TNO_PPTR_PROTOCOLLO_ID,VERSO,TNO_PPTR_TIPOPROTOCOLLO_ID,CODICE_TIPOPROTOCOLLO,CODICE_PRATICA_APPPTR,CODICE_PRATICA_ENTEDELEGATO,T_PRATICA_ID_APPPTR,T_PRATICA_ID_ENTEDELEGATO,BIN,BIN_TIMBRATO,T_KE_DOC_ST_ID,ESITO_PROTOCOLLAZIONE,MOTIVO_ERRATA_PROTOCOLLAZIONE,NUMERO_PROTOCOLLO,DATA_PROTOCOLLO,TITOLARIO_PROTOCOLLO,DOC_SEGNATO,ALGORITMO,CODIFICA,IMPRONTA,NOTE,SEGNATURA_BLOB,BIN_PDF_CONTENT,BIN_PDFPROT_CONTENT,PROG ")
		.append(" FROM ")
		.append(CIVILIA_CS+"TNO_PPTR_PROTOCOLLO ")
		.append("  WHERE  ")
		.append(" CODICE_PRATICA_APPPTR = ? ")
		.append(" AND CODICE_TIPOPROTOCOLLO = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(codiceTipoProtocollo);
		List<PptrProtocollo> list = super.queryForList(sb.toString(), new PptrProtocolloRowMapper(),parameters);
		return list;
	}
	
	public List<PptrProvvedimento> getProvvedimento(String codicePraticaApptr, long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_PROVVEDIMENTO  ")
		.append(" WHERE  ")
		.append(" CODICE_PRATICA_APPPTR = ? AND PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaApptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrProvvedimentoRowMapper(),parameters);
	}
	
	public List<VtnoAllegatiPptr> getProvvedimentoAllegati(String codicePraticaAppptr,long prog) {
		StringBuilder sb = new StringBuilder();
		sb.append("	SELECT ID,CODICE,DESCRIZIONE,T_PRATICA_ID,DATAARRIVO,NOME,NOMEFILE,TIPO,T_TIPODOC_CODICE,T_TIPODOC_DESCRIZIONE,T_TIPODOC_MODCOMPILAZIONE,T_TIPODOC_ID,NAME,BIN_CONTENT,VERSION_NOTES,T_KE_DOC_ATTR_NAME,T_KE_DOC_ATTR_VALUE,N_T_KE_DOC_ST_ID,NUMEROPROTOCOLLO,DATAPROTOCOLLO,PPTR_TIPOALLEGATO_CODICE,PPTR_TIPOALLEGATO_DESCRIZIONE,FASE_ID,PPTR_TIPOALLEGATO_ORDINE,NOMEALLEGATO,PROG,ID_ALFRESCO,PROVV ")
		.append("	FROM "+CIVILIA_CS+"VTNO_ALLEGATI_PPTR ")
		.append("	WHERE CODICE = ? AND PROG = ? ")
		.append( "	AND TRIM( PROVV ) = 'PF'  ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new VtnoAllegatiPptrRowMapper(), parameters);
	}
	
	
	public List<PptrProtocolloUscita> getRicevutaTrasmissione(String codicePraticaAppptr,long prog){
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT  * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_PROTOCOLLO_USCITA ")
		.append(" WHERE ")
		.append(" CODICE_PRATICA_APPPTR = ? AND PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrProtocolloUscitaRowMapper(),parameters);
	}
	
}

