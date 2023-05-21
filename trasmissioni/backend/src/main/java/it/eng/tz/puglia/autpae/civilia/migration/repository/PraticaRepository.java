/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.civilia.migration.IDataSourceCiviliaConstants;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrLocalizInterv;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMail;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMailAllegati;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.Comuni_completo_cod_istat;
import it.eng.tz.puglia.autpae.civilia.migration.dto.EnteParcoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InteressePubblicoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PaesaggioRuraleAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCaratterizzazioneIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCodiceInterno;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrGruppoUfficio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrParticelleCatastali;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProvvedimento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrQualificIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrRicevutaIstanza;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrSoprintendenzaNoteSt;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrTipoIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PraticaLavorazione;
import it.eng.tz.puglia.autpae.civilia.migration.dto.ReferentiProgettoDto;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TkeDocAttr;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoMailEnti;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoSopMatriceTerritorio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.VtnoAllegatiPptr;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.LocalizzazionePuttBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.PuttDocBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.TnoProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.AutPaesPptrLocalizIntervRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.AutPaesPptrPraticaRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.CiviliaMailAllegatiRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.CiviliaMailRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.CiviliaPraticaRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.Comuni_completo_cod_istatRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.EnteParcoAssRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.InfoAutPaesPptrAlfaRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.InteressePubblicoAssRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PaesaggioRuraleAssRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrCaratterizzazioneInterventoRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrCodiceInternoRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrGruppoUfficioRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrParticelleCatastaliRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrProtocolloUscitaRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrProvvedimentoRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrQualificInterventoRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrRicevutaIstanzaRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrSoprintendenzaNoteStRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PptrTipoInterventoRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.PraticaLavorazioneRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.ReferentiProgettoDtoRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.TkeDocAttrRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.TnoMailEntiRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.TnoSopMatriceTerritorioRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.VtnoAllegatiPptrRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt.InfoAutPaesAlfaRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt.LocalizzazionePuttRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt.PuttDocRowMapper;
import it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt.TnoProtocolloUscitaRowMapper;
import it.eng.tz.puglia.autpae.generated.orm.repository.GenericDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
@Repository
public class PraticaRepository extends GenericDao {

	@Autowired(required = false)
	@Qualifier(IDataSourceCiviliaConstants.ORACLE_CIV_NAMEDPARAMETERJDBCTEMPLATE_SPRING_BEAN_NAME)
	protected NamedParameterJdbcTemplate namedJdbcTemplateCiv;

	final private String CIVILIA_CS = "CIVILIA_CS.";
	final private String PSITPIANI = "PSITPIANI.";
	
	final String queryPraticheInLavorazione=
			 StringUtil.concateneString(
				 "SELECT ",
			    " tppp.CODICE_PRATICA_ENTEDELEGATO,", 
				" tppp.T_PRATICA_DESCRIZIONE, ",
				" tppp.ID , ",
				" tppp.CODICE_PRATICA_APPPTR ,", 
				" tppp.PROG, ",
				" tp.T_PRATICA_ID, " ,
				" tp.DATAATTIVAZIONE, ",
				" tppp.UFFICIO ", 
				" FROM ",
				CIVILIA_CS+"TNO_PPTR_PROG tpg ,",
				CIVILIA_CS+"T_PRATICA tp, ",
				CIVILIA_CS+"TNO_PPTR_PRATICA tppp ",
				" LEFT JOIN ",
				PSITPIANI+"INFO_AUT_PAES_PPTR_ALFA info ",
				" on tppp.CODICE_PRATICA_APPPTR || tppp.PROG= info.CODICE_PRATICA_APPPTR ||info.PROG ",
				" where 1 = 1 ",
				" AND tp.CODICE = tppp.CODICE_PRATICA_ENTEDELEGATO ", 
				" AND tppp.CODICE_PRATICA_APPPTR || tppp.PROG  = tpg.CODICE_PRATICA_APPPTR || tpg.PROG_ENTEDELEGATO ", 
				" AND tppp.SOLOTRASMISSIONE = 1 ",
				" AND tppp.PROG = 0 ",
				" AND info.CODICE_PRATICA_APPPTR IS null ",
				//" AND tppp.CODICE_PRATICA_APPPTR || tppp.PROG  NOT IN ( SELECT INFO_AUT_PAES_PPTR_ALFA.CODICE_PRATICA_APPPTR || INFO_AUT_PAES_PPTR_ALFA.PROG FROM "+PSITPIANI+"INFO_AUT_PAES_PPTR_ALFA ) ", 
				" AND tppp.ACTIVE = '0' ");
			
	
	
	
	
	final String queryPratiche = "SELECT " + 
			"iappa.CODICE_PRATICA_APPPTR codicePraticaAppptr, " +
			"iappa.CODICE_PRATICA_ENTEDELEGATO codicePraticaEnteDelegato," +
			"iappa.OGGETTO oggetto, " +
			"iappa.RESPONSAB responsabileProvvedimento, " +
			"iappa.NUMERO_PR numeroProvvedimento, " +
			"iappa.DATA_PRO dataProvvedimento, " +
			"iappa.ESITO_RIC esitoProvvedimento, " +
			"iappa.DATA_TRASMISSIONE dataTrasmissione, " +
			"iappa.ISTAT_AMM istatAmm, " +
			"iappa.NOTE note, " +
			//"tp.T_PRATICA_ID tPraticaId, " +
			"tp2.T_PRATICA_ID tPraticaId, " + //mai usato
			"tp2.T_PRATICA_ID tPraticaAppptrId, " +
			"iappa.PROG progPubblicazione " +
			"FROM " +
			PSITPIANI+ "INFO_AUT_PAES_PPTR_ALFA iappa "+
			//","+CIVILIA_CS+"T_PRATICA tp " 
			","+CIVILIA_CS+"T_PRATICA tp2 "  
			+" WHERE "
			//+ "iappa.CODICE_PRATICA_ENTEDELEGATO=tp.CODICE "
			//+" AND "
			+ " not iappa.CODICE_PRATICA_APPPTR  IN (SELECT T_PRATICA_CODICE FROM  "+CIVILIA_CS+"VTNO_ATTIVITA_PPTR WHERE  SOLOTRASMISSIONE=0) "
			+" AND iappa.CODICE_PRATICA_APPPTR=tp2.CODICE "
			+" AND iappa.CODICE_PRATICA_ENTEDELEGATO  NOT IN (SELECT CODICE FROM "+CIVILIA_CS+"PPTR_IMPORTATE) ";

	private static final Logger log = LoggerFactory.getLogger(PraticaRepository.class);

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

	/**
	 * restituisce l'ultimo progressivo di pubblicazione
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param codicePraticaAppptr
	 * @return
	 */
	public Long getMaxProgPubblicazione(String codicePraticaAppptr) {
		final StringBuilder sql=new StringBuilder();
		sql
		.append("SELECT MAX(r.PROG_PUBBLICAZIONE) ") 
		.append("FROM ")
		.append(CIVILIA_CS+"TNO_PPTR_PROG r ")
		.append("WHERE r.CODICE_PRATICA_APPPTR = ? ");
		return super.jdbcTemplateRead.queryForObject(sql.toString(),Long.class,codicePraticaAppptr);
	}
	
		/**
	 * SELECT * FROM ( SELECT rownum rnum, a.* FROM(SELECT prat.T_PRATICA_ID
	 * tPraticaId, pratica.CODICE_PRATICA_APPPTR codicePraticaAppptr,
	 * pratica.CODICE_PRATICA_ENTEDELEGATO
	 * codicePraticaEnteDelegato,pratica.T_PRATICA_DESCRIZIONE tPraticaDescrizione,
	 * pratica.JBP_UNAME jbpUname, pratica.SOLOTRASMISSIONE
	 * soloTrasmissione,pratica.TNO_PPTR_TIPOPROCEDIMENTO_ID
	 * tnoPptrTipoProcedimentoId, tpproc.DESCRIZIONE tpprocdescrizione,
	 * pratica.PROVVEDIMENTO_SANATORIA provvedimentoSanatoria,
	 * des_int.DESCR_INTERVENTO FROM PSITPIANI.INFO_AUT_PAES_PPTR_ALFA iappa,
	 * CIVILIA_CS.TNO_PPTR_TIPOPROCEDIMENTO tpproc,CIVILIA_CS.TNO_PPTR_PRATICA
	 * pratica,CIVILIA_CS.T_PRATICA prat LEFT JOIN
	 * CIVILIA_CS.TNO_PPTR_DESCR_INTERVENTO des_int ON des_int.T_PRATICA_APPTR_ID =
	 * prat.T_PRATICA_ID WHERE iappa.CODICE_PRATICA_APPPTR =prat.CODICE AND
	 * prat.CODICE =pratica.CODICE_PRATICA_APPPTR AND
	 * pratica.TNO_PPTR_TIPOPROCEDIMENTO_ID=tpproc.TNO_PPTR_TIPOPROCEDIMENTO_ID AND
	 * pratica.SOLOTRASMISSIONE=1 ORDER BY prat.T_PRATICA_ID) a WHERE rownum <=10 )
	 * WHERE rnum >=0
	 * 
	 * @autor Adriano Colaianni
	 * @date 19 apr 2021
	 * @param fromRowNum
	 * @param toRowNum
	 * @return
	 */
	public List<InfoAutPaesPptrAlfa> getListaPratiche(int fromRowNum, int toRowNum) {

		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT * FROM ( SELECT rownum rnum, a.* FROM(", queryPratiche, ") a "));
		sql.append(" WHERE rownum <=? ) WHERE rnum >=?");
		parameters.add(toRowNum);
		parameters.add(fromRowNum);
		List<InfoAutPaesPptrAlfa> list = super.queryForList(sql.toString(), new InfoAutPaesPptrAlfaRowMapper(), parameters);
		return list;
	}
	
	public Long countPratichePptrTrasmesse() {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT count(*) FROM(", queryPratiche, ") a "));
		return super.queryForObject(sql.toString(),Long.class,parameters);
	}
	
	/**
	 * @autor Adriano Colaianni
	 * @date 28 apr 2021
	 * @param codiciEnteDelegato
	 * @return
	 */
	public List<InfoAutPaesPptrAlfa> getListaPratiche(List<String> codiciEnteDelegato) {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(queryPratiche); 
		sql.append(" AND iappa.CODICE_PRATICA_ENTEDELEGATO IN ("+
		codiciEnteDelegato.stream().map(el->"?").collect(Collectors.joining(","))
		+ ")");
		parameters.addAll(codiciEnteDelegato);
		List<InfoAutPaesPptrAlfa> list = super.queryForList(sql.toString(), new InfoAutPaesPptrAlfaRowMapper(), parameters);
		return list;
	}

	/**
	 * SELECT referenti.T_PRATICA_APPTR_ID tPraticaApptrId,referenti.NOME
	 * richiedenteNome,referenti.COGNOME richiedenteCognome,
	 * referenti.CODICE_FISCALE richiedenteCodiceFiscale, referenti.COMUNE_NASCITA
	 * comuneNascita, referenti.PROV_NASCITA provNascita, referenti.STATO_NASCITA
	 * statoNascita, referenti.DATA_NASCITA dataNascita, referenti.COMUNE_RESIDENZA
	 * comuneResidenza, referenti.PROV_RESIDENZA provResidenza,
	 * referenti.STATO_RESIDENZA statoResidenza, referenti.INDIRIZZO indirizzo,
	 * referenti.NUM_CIVICO numCivico, referenti.CAP cap, referenti.TEL_FISSO
	 * telFisso, referenti.TEL_FAX telFax, referenti.TEL_CELLULARE telCellulare,
	 * referenti.INDIRIZZO_EMAIL indirizzoMail, referenti.INDIRIZZO_PEC
	 * indirizzoPec, referenti.DITTA_RUOLO_RICH dittaRuoloRich,
	 * referenti.DITTA_RAGIONE_SOCIALE dittaRagioneSociale,
	 * referenti.DITTA_PARTITA_IVA dittaPartitaIva, referenti.DITTA_CODICE_FISCALE
	 * dittaCodiceFiscale FROM CIVILIA_CS.TNO_PPTR_REFERENTI_PROGETTO referenti
	 * WHERE referenti.T_PRATICA_APPTR_ID= ? AND referenti.TIPO_REFERENTE= ? ORDER
	 * BY referenti.REFERENTE_PROGETTO_ID DESC;
	 * 
	 * @autor Adriano Colaianni
	 * @date 19 apr 2021
	 * @param tipoReferente SD Soggetto Dichiarante (per trasmissioni è solo questo)
	 * @param tPraticaId
	 * @param prog nullable
	 * @return
	 */
	public List<ReferentiProgettoDto> getReferentiPratica(String tipoReferente, Long tPraticaId,Long prog) {

		List<Object> parameters = new ArrayList<>();
		String queryData = "SELECT " + 
				"referenti.T_PRATICA_APPTR_ID tPraticaApptrId,"+
				"referenti.NOME richiedenteNome," + "referenti.COGNOME richiedenteCognome, "
				+ "referenti.CODICE_FISCALE richiedenteCodiceFiscale, " + "referenti.COMUNE_NASCITA comuneNascita, "
				+ "referenti.PROV_NASCITA provNascita, " + "referenti.STATO_NASCITA statoNascita, "
				+ "referenti.DATA_NASCITA dataNascita, " + "referenti.COMUNE_RESIDENZA comuneResidenza, "
				+ "referenti.PROV_RESIDENZA provResidenza, " + "referenti.STATO_RESIDENZA statoResidenza, "
				+ "referenti.INDIRIZZO indirizzo, " + "referenti.NUM_CIVICO numCivico, " + "referenti.CAP cap, "
				+ "referenti.TEL_FISSO telFisso, " + "referenti.TEL_FAX telFax, "
				+ "referenti.TEL_CELLULARE telCellulare, " + "referenti.INDIRIZZO_EMAIL indirizzoMail, "
				+ "referenti.INDIRIZZO_PEC indirizzoPec, " + "referenti.DITTA_RUOLO_RICH dittaRuoloRich, "
				+ "referenti.DITTA_RAGIONE_SOCIALE dittaRagioneSociale, "
				+ "referenti.DITTA_PARTITA_IVA dittaPartitaIva, " + "referenti.DITTA_CODICE_FISCALE dittaCodiceFiscale "
				+ "FROM " + CIVILIA_CS + "TNO_PPTR_REFERENTI_PROGETTO referenti " + "WHERE referenti.T_PRATICA_APPTR_ID= ? "
				+ " AND referenti.TIPO_REFERENTE= ? ";
		parameters.add(tPraticaId);
		parameters.add(tipoReferente);
		StringBuilder sb=new StringBuilder(queryData);
		if(prog!=null) {
			sb.append(" AND referenti.PROG= ? " );
			parameters.add(prog);
		}
		sb.append("ORDER BY referenti.REFERENTE_PROGETTO_ID DESC ");
		
		List<ReferentiProgettoDto> list = super.queryForList(sb.toString(), new ReferentiProgettoDtoRowMapper(),
				parameters);

		return list;
	}

	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 20 apr 2021
	 * @param codicePraticaApPptr
	 * @param prog
	 * @return
	 */
	public AutPaesPptrPratica getAutPaesPptrPratica( String codicePraticaApPptr , long prog ) {
		AutPaesPptrPratica ret=null;
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT JBP_ID jbpId")
		.append(",CODICE_PRATICA_APPPTR codicePraticaAppptr")
		.append(",CODICE_PRATICA_ENTEDELEGATO codicePraticaEnteDelegato")
		.append(",TNO_PPTR_TIPOPROCEDIMENTO_ID tipoProcedimentoId")
		.append(",T_PRATICA_DESCRIZIONE tPraticaDescrizione")
		.append(",ENTEDELEGATO enteDelegato")
		.append(",UFFICIO ufficio")
		.append(",ACTIVE active")
		.append(",JBP_UNAME jbpUname")
		.append(",SOLOTRASMISSIONE soloTrasmissione")
		.append(",NOTE note")
		.append(",PROVVEDIMENTO_SANATORIA provvedimentoInSanatoria")
		.append(",PROG prog ")
		.append(" FROM ")
		.append(CIVILIA_CS+"TNO_PPTR_PRATICA ")
		.append(" WHERE ")
		.append("  CODICE_PRATICA_APPPTR = ? ")
		.append(" AND PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaApPptr);
		parameters.add(prog);
		List<AutPaesPptrPratica> list = super.queryForList(sb.toString(), new AutPaesPptrPraticaRowMapper(),
				parameters);
		if(ListUtil.isNotEmpty(list)) {
			ret=list.get(0);
		}
		return ret;
	}
	
	public List<AutPaesPptrLocalizInterv> getLocalizzazioneIntervento(String codicePraticaAppptr,long prog){
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_LOCALIZ_INTERV ")
		.append(" WHERE CODICE_PRATICA_APPPTR = ? ")
		.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new AutPaesPptrLocalizIntervRowMapper(),parameters);
	}
	
	
	public List<PptrRicevutaIstanza> getRicevutaIstanza(String codicePraticaAppptr,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_RICEVUTA_ISTANZA ")
		.append(" WHERE CODICE_PRATICA_APPPTR = ? ")
		.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrRicevutaIstanzaRowMapper(),parameters);
	}
	
	public List<PptrCodiceInterno> getDatiCodiceInterno(String codicePraticaAppptr,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_CODICE_INTERNO ")
		.append(" WHERE CODICE_PRATICA_APPPTR = ? ")
		.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrCodiceInternoRowMapper(),parameters);
	}
	
	public List<PptrTipoIntervento> getDatiTipoIntervento(long tPraticaId,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_TIPO_INTERVENTO ")
		.append(" WHERE T_PRATICA_APPTR_ID  = ? ")
		.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrTipoInterventoRowMapper(),parameters);
	}
	
	
	public List<PptrCaratterizzazioneIntervento> getDatiCaratterizzazioneIntervento(long tPraticaId,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_CARATT_INTERVENTO ")
		.append(" WHERE T_PRATICA_APPTR_ID  = ? ")
		.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrCaratterizzazioneInterventoRowMapper(),parameters);
	}
	
	public List<PptrQualificIntervento> getDatiQualificazioneIntervento(long tPraticaId,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PPTR_QUALIFIC_INTERVENTO ")
		.append(" WHERE T_PRATICA_APPTR_ID  = ? ")
		.append(" AND  PROG = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(tPraticaId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrQualificInterventoRowMapper(),parameters);
	}
	
	public List<Comuni_completo_cod_istat> getComuniCompletoCodIstat() {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"COMUNI_COMPLETO_COD_ISTAT ");
		List<Object> parameters = new ArrayList<>();
		return super.queryForList(sb.toString(), new Comuni_completo_cod_istatRowMapper(),parameters);
	}
	
	
	public List<PptrParticelleCatastali> getParticelleCatastaliLocalizInterv(long localizIntervId, long tPraticaApptrId , long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT * ")
		.append(" FROM "+CIVILIA_CS+"TNO_PARTICELLECATASTALI_PPTR ")
		.append(" WHERE  LOCALIZ_INTERV_ID= ? AND T_PRATICA_APPPTR_ID = ? AND PROG= ?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(localizIntervId);
		parameters.add(tPraticaApptrId);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PptrParticelleCatastaliRowMapper(),parameters);
	}
	
	
	public List<EnteParcoAss> getEnteParcoAss(String codicePratica, String codIstat,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT  a.PROG,a.TNO_ENTEPARCO_TIPOASS_ID,a.COMUNE,a.COD_CAT,m.COD_ISTAT,m.DESC_PARCO,m.MAIL,a.CODICE_PRATICA,a.T_PRATICA_ID,a.TNO_ENTEPARCO_MAP_ID ")
		.append(" FROM "+CIVILIA_CS+"TNO_ENTEPARCO_MAP m, ")
		.append(CIVILIA_CS+"TNO_ENTEPARCO_ASS a ")
		.append(" WHERE  m.TNO_ENTEPARCO_MAP_ID=a.TNO_ENTEPARCO_MAP_ID ")
		.append(" AND a.CODICE_PRATICA= ? AND a.COD_ISTAT= ? AND a.PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePratica);
		parameters.add(codIstat);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new EnteParcoAssRowMapper(),parameters);
	}
	
	
	public List<PaesaggioRuraleAss> getPaesaggioRuraleAss(String codicePraticaApptr, String codIstat,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT  a.PROG,a.TNO_PAESAGGIRURALI_TIPOASS_ID,a.COMUNE,a.COD_CAT,m.COD_ISTAT,m.DESC_PAESAGGIO_RURALE,a.CODICE_PRATICA,a.T_PRATICA_ID,a.TNO_PAESAGGIRURALI_MAP_ID ")
		.append(" FROM "+CIVILIA_CS+"TNO_PAESAGGIRURALI_MAP m, ")
		.append(CIVILIA_CS+"TNO_PAESAGGIRURALI_ASS a ")
		.append(" WHERE  m.OBJECTID=a.TNO_PAESAGGIRURALI_MAP_ID ")
		.append(" AND a.CODICE_PRATICA= ? AND a.COD_ISTAT= ? AND a.PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaApptr);
		parameters.add(codIstat);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new PaesaggioRuraleAssRowMapper(),parameters);
	}
	
	public List<InteressePubblicoAss> getInteressePubblicoAss(String codicePraticaApptr, String codIstat,long prog) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT  a.PROG,a.TNO_INTERESSEPUB_TIPOASS_ID,a.COMUNE,a.COD_CAT,m.COD_ISTAT,m.COD_VINCOLO,m.OGGETTO,a.CODICE_PRATICA,a.T_PRATICA_ID ")
		.append(" FROM "+CIVILIA_CS+"TNO_INTERESSEPUBBLICO_MAP m, ")
		.append(CIVILIA_CS+"TNO_INTERESSEPUBBLICO_ASS a ")
		.append(" WHERE  m.OBJECTID=a.TNO_INTERESSEPUB_MAP_ID ")
		.append(" AND a.CODICE_PRATICA= ? AND a.COD_ISTAT= ? AND a.PROG= ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaApptr);
		parameters.add(codIstat);
		parameters.add(prog);
		return super.queryForList(sb.toString(), new InteressePubblicoAssRowMapper(),parameters);
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
	
	/**
	 * distinguibili da campo pptrTipoAllegatoDescrizione che assume Parere Mibac o Provvedimento finale
	 * @autor Adriano Colaianni
	 * @date 22 apr 2021
	 * @param codicePraticaAppptr
	 * @param prog
	 * @return
	 */
	public List<VtnoAllegatiPptr> getListAllegatiProvvedimento(String codicePraticaAppptr,long prog){
		String queryString = "";
		/**
		 * query presa da Civilia, ma prendiamo solo i provvedimenti della versione pubblicata
		 */
		queryString = queryString + " SELECT * ";
		queryString = queryString + " FROM "+CIVILIA_CS+"VTNO_ALLEGATI_PPTR ";
		queryString = queryString + " WHERE CODICE = ? "
				//+ "and prog <= (SELECT MAX (PROG_PUBBLICAZIONE) FROM TNO_PPTR_PROG WHERE CODICE_PRATICA_APPPTR = ? ) ";
				+ "and PROG = ? ";
		queryString = queryString + "	AND PPTR_TIPOALLEGATO_CODICE "; 
		queryString = queryString + "	IN "; 
		queryString = queryString + "	(SELECT TIPOALLEGATO_ID from "+CIVILIA_CS+"TNO_PPTR_TIPOALLEGATO , "+CIVILIA_CS+"TNO_PPTR_PRATICA ";
		queryString = queryString + "	where TNO_PPTR_TIPOALLEGATO.TNO_PPTR_TIPOPROCEDIMENTO_ID = TNO_PPTR_PRATICA.TNO_PPTR_TIPOPROCEDIMENTO_ID ";
		queryString = queryString + "	AND CODICE_PRATICA_APPPTR = ? AND FASE_ID = 2 ";
		queryString = queryString + "	)";
		queryString = queryString + " ORDER BY DATAARRIVO DESC";
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(prog);
		parameters.add(codicePraticaAppptr);
		return super.queryForList(queryString, new VtnoAllegatiPptrRowMapper(),parameters);
	}
	
	public List<VtnoAllegatiPptr> getListAllegatiFascicolo(String codicePraticaAppptr){
		String queryString = "";
		/**
		 * presa da civilia, prendiamo anche allegati delle varie versioni di pubblicazione (progr<=...)
		 */
   		queryString = queryString + "	SELECT ID,CODICE,DESCRIZIONE,T_PRATICA_ID,DATAARRIVO,NOME,NOMEFILE,TIPO,T_TIPODOC_CODICE,T_TIPODOC_DESCRIZIONE,T_TIPODOC_MODCOMPILAZIONE,T_TIPODOC_ID,NAME,BIN_CONTENT,VERSION_NOTES,T_KE_DOC_ATTR_NAME,T_KE_DOC_ATTR_VALUE,N_T_KE_DOC_ST_ID,NUMEROPROTOCOLLO,DATAPROTOCOLLO,PPTR_TIPOALLEGATO_CODICE,PPTR_TIPOALLEGATO_DESCRIZIONE,FASE_ID,PPTR_TIPOALLEGATO_ORDINE,NOMEALLEGATO,PROG,ID_ALFRESCO,PROVV ";
		queryString = queryString + "	FROM "+CIVILIA_CS+"VTNO_ALLEGATI_PPTR ";
		queryString = queryString + "	WHERE CODICE = ? AND PROG <= (SELECT MAX (PROG_PUBBLICAZIONE) FROM "+CIVILIA_CS+
									" TNO_PPTR_PROG WHERE CODICE_PRATICA_APPPTR = ? ) ";
		queryString = queryString + "	AND PPTR_TIPOALLEGATO_CODICE "; 
		queryString = queryString + "	IN "; 
		queryString = queryString + "	(  SELECT TIPOALLEGATO_ID FROM "+CIVILIA_CS+"TNO_PPTR_TIPOALLEGATO , "+
									CIVILIA_CS+"TNO_PPTR_PRATICA ";
		queryString = queryString + "	WHERE TNO_PPTR_TIPOALLEGATO.TNO_PPTR_TIPOPROCEDIMENTO_ID = TNO_PPTR_PRATICA.TNO_PPTR_TIPOPROCEDIMENTO_ID ";
		queryString = queryString + "	AND CODICE_PRATICA_APPPTR = ? AND PROVV IS NULL )";
		List<Object> parameters = new ArrayList<>();
		parameters.add(codicePraticaAppptr);
		parameters.add(codicePraticaAppptr);
		parameters.add(codicePraticaAppptr);
		return super.queryForList(queryString, new VtnoAllegatiPptrRowMapper(),parameters);
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
	
	public List<CiviliaMail> getCiviliaEmail(long praticaId,boolean is016){
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT  * ")
		.append(" FROM "+civiliaSchema(is016)+"T_MAIL_INVIATE ")
		.append(" WHERE ")
		.append(" T_PRATICA_ID = ? ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(praticaId);
		return super.queryForList(sb.toString(), new CiviliaMailRowMapper(),parameters);
	}
	
	public List<CiviliaMailAllegati> getCiviliaEmailAllegati(long mailInviateId,boolean is016){
		StringBuilder sb = new StringBuilder();
		sb
		/**
		 * SELECT mall.T_MAIL_INVIATE_ALLEGATI_ID ,mall.T_KE_DOC_ID,mall.T_MAIL_INVIATE_ID ,mall.NOME_FILE,mall.CC_TIME_STAMP ,bin.BIN_CONTENT 
		   FROM T_MAIL_INVIATE_ALLEGATI mall,T_KE_DOC_ST bin WHERE mall.T_KE_DOC_ID =bin.T_KE_DOC_ID;
		 */
		.append("SELECT  mall.T_MAIL_INVIATE_ALLEGATI_ID ,mall.T_KE_DOC_ID,mall.T_MAIL_INVIATE_ID ,mall.NOME_FILE,mall.CC_TIME_STAMP ,bin.BIN_CONTENT ")
		.append(" FROM "+civiliaSchema(is016)+"T_MAIL_INVIATE_ALLEGATI mall, ")
		.append(civiliaSchema(is016)+"T_KE_DOC_ST bin ")
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

	/**
	 * SELECT DISTINCT T_KE_GRUPPO.T_KE_GRUPPO_ID gruppoId ,T_KE_GRUPPO.CODICE codiceGruppo ,T_KE_GRUPPO.DESCRIZIONE descrizioneGruppo,T_UFFICIO.CODICE codiceUfficio,T_UFFICIO.T_UFFICIO_ID tUfficioId,T_UFFICIO.DESCRIZIONE ufficioDescrizione FROM T_TIPOPRAT_UFF, T_TIPOPROC, T_TIPOPRATICA,
	 * T_UFFICIO, T_KE_GRUPPO WHERE ( (T_TIPOPROC.T_TIPOPROC_ID =
	 * T_TIPOPRATICA.TIPOPROCPRIMARIO_FK) AND (T_TIPOPRAT_UFF.T_TIPOPRATICA_ID =
	 * T_TIPOPRATICA.T_TIPOPRATICA_ID ) AND (T_UFFICIO.T_UFFICIO_ID =
	 * T_TIPOPRAT_UFF.T_UFFICIO_ID) AND (T_KE_GRUPPO.T_KE_GRUPPO_ID =
	 * T_UFFICIO.T_KE_GRUPPO_ID) AND ( T_TIPOPROC.CODICE LIKE 'AP%' ))
	 * 
	 * ;
	 * 
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 */
	public List<PptrGruppoUfficio> getAllUffici() {
		final String sql="SELECT " + 
				" DISTINCT T_KE_GRUPPO.T_KE_GRUPPO_ID gruppoId ,T_KE_GRUPPO.CODICE codiceGruppo ,"
				+ "T_KE_GRUPPO.DESCRIZIONE descrizioneGruppo,T_UFFICIO.CODICE codiceUfficio,T_UFFICIO.T_UFFICIO_ID tUfficioId,"
				+ "T_UFFICIO.DESCRIZIONE ufficioDescrizione "+
		" FROM "
		+ CIVILIA_CS+"T_TIPOPRAT_UFF, "+ 
		CIVILIA_CS+"T_TIPOPROC, "+
		CIVILIA_CS+"T_TIPOPRATICA, "+ 
		CIVILIA_CS+"T_UFFICIO, "+
		CIVILIA_CS+"T_KE_GRUPPO "+
		"WHERE (    "+
		       "(T_TIPOPROC.T_TIPOPROC_ID = T_TIPOPRATICA.TIPOPROCPRIMARIO_FK)  "+
		       "AND (T_TIPOPRAT_UFF.T_TIPOPRATICA_ID = T_TIPOPRATICA.T_TIPOPRATICA_ID ) "+ 
		       "AND (T_UFFICIO.T_UFFICIO_ID = T_TIPOPRAT_UFF.T_UFFICIO_ID)  "+
		       "AND (T_KE_GRUPPO.T_KE_GRUPPO_ID = T_UFFICIO.T_KE_GRUPPO_ID) "+ 
		       "AND ( T_TIPOPROC.CODICE LIKE 'AP%' ))";
		List<Object> parameters = new ArrayList<>();
		List<PptrGruppoUfficio> list = super.queryForList(sql, new PptrGruppoUfficioRowMapper(),parameters);
		return list;
	}
	
	//----- PUTT/p -------------------------------------------------------------------------------------------------------------------------------
	/**
	 * getElenchiAutPaeTra.jsp
	 * Prendo dettaglio pratica
	 * 
	 * " SELECT NVL( suap_dataattivazione , \' \' ) AS dsuap_dataattivazionepratica, 
	 * 			SUAP_CODEP AS suap_codepratica, SUAP_OGGPR AS suap_oggpratica, 
	 * 			NVL( note , \' \' ) AS Nnote, NVL( suap_codeinterno , \' \'  )  AS Ncodiceinterno, 
	 * 			TIPO_INTER AS descrizioneintervento, TIPOLOGIA AS tipologia_provvedimento, 
	 * 			NVL( RESPONSAB , \' \' ) AS Nresponsabile_procedimento, DATA_PRO AS ddata_provvedimento_finale, 
	 * 			NUMERO_PR AS numero_provvedimento_finale, ESITO_RIC AS esito_richiesta, NVL( RICHIEDEN , \' \' ) AS Nrichiedente, 
	 * 			NVL( SANATORIA , \' \' ) AS NSANATORIA, INFO_AUT_PAES_ALFA.t_pratica_id 
	 * 		if( true == protocollo )
	 * 			, NVL( tno_doc_st_id , 0 ) AS Ntno_doc_st_id 
	 * FROM INFO_AUT_PAES_ALFA 
	 * 		if( true == protocollo )
	 * 		, tno_doc_st 
	 * WHERE 1 = 1 AND INFO_AUT_PAES_ALFA.t_pratica_id = ? 
	 * 		if( true == protocollo )
	 * 			 AND INFO_AUT_PAES_ALFA.T_PRATICA_ID = tno_doc_st.T_PRATICA_ID(+) ";
	 * 
	 */
	public List<InfoAutPaesAlfaBean> getPuttPratiche(int from, int to) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<>();		
		String sqlP = StringUtil.concateneString("SELECT NVL( suap_dataattivazione , \' \' ) AS dsuap_dataattivazionepratica, " ,
												 "SUAP_CODEP AS suap_codepratica, SUAP_OGGPR AS suap_oggpratica, ",
									  			 "NVL( note , \' \' ) AS Nnote, NVL( suap_codeinterno , \' \'  )  AS Ncodiceinterno, ",
									  			 "TIPO_INTER AS descrizioneintervento, TIPOLOGIA AS tipologia_provvedimento, ",
									  			 "NVL( RESPONSAB , \' \' ) AS Nresponsabile_procedimento, DATA_PRO AS ddata_provvedimento_finale, ",
									  			 "NUMERO_PR AS numero_provvedimento_finale, ESITO_RIC AS esito_richiesta, NVL( RICHIEDEN , \' \' ) AS Nrichiedente, ",
									  			 //"NVL( SANATORIA , \' \' ) AS NSANATORIA, INFO_AUT_PAES_ALFA.t_pratica_id, INFO_AUT_PAES_ALFA.ISTAT_AMM,DATA_TRASMISSIONE ",
									  			"NVL( SANATORIA , \' \' ) AS NSANATORIA, INFO_AUT_PAES_ALFA.t_pratica_id, INFO_AUT_PAES_ALFA.ISTAT_AMM ",
									  			 "FROM ", PSITPIANI, "INFO_AUT_PAES_ALFA "
		);
		String sql = StringUtil.concateneString("SELECT * FROM ( SELECT rownum rnum, a.* FROM(",
												sqlP, ") a ",
												"WHERE rownum <=:to ) WHERE rnum >=:from");
		parameters.put("to", to);
		parameters.put("from", from);
		return namedJdbcTemplateRead.query(sql, parameters, new InfoAutPaesAlfaRowMapper());
	}
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 16 ago 2021
	 * @return
	 * @throws Exception
	 */
	public Long countPuttPraticheTrasmesse() throws Exception
	{
		final Map<String, Object> parameters = new HashMap<>();		
		String sql = StringUtil.concateneString("SELECT COUNT(*) ",
									  			 "FROM ", PSITPIANI, "INFO_AUT_PAES_ALFA "
		);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Long.class);
	}
	
	
	/**
	 * PUTT fetch di una lista di pratiche da lista di codici
	 * 
	 * @autor Adriano Colaianni
	 * @date 20 lug 2021
	 * @param listaCodici
	 * @return
	 * @throws Exception
	 */
	public List<InfoAutPaesAlfaBean> getPuttPraticheFromCodes(List<String> listaCodici) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<>();		
		String sqlP = StringUtil.concateneString("SELECT NVL( suap_dataattivazione , \' \' ) AS dsuap_dataattivazionepratica, " ,
												 "SUAP_CODEP AS suap_codepratica, SUAP_OGGPR AS suap_oggpratica, ",
									  			 "NVL( note , \' \' ) AS Nnote, NVL( suap_codeinterno , \' \'  )  AS Ncodiceinterno, ",
									  			 "TIPO_INTER AS descrizioneintervento, TIPOLOGIA AS tipologia_provvedimento, ",
									  			 "NVL( RESPONSAB , \' \' ) AS Nresponsabile_procedimento, DATA_PRO AS ddata_provvedimento_finale, ",
									  			 "NUMERO_PR AS numero_provvedimento_finale, ESITO_RIC AS esito_richiesta, NVL( RICHIEDEN , \' \' ) AS Nrichiedente, ",
									  			 //"NVL( SANATORIA , \' \' ) AS NSANATORIA, INFO_AUT_PAES_ALFA.t_pratica_id, INFO_AUT_PAES_ALFA.ISTAT_AMM, DATA_TRASMISSIONE ",
									  			"NVL( SANATORIA , \' \' ) AS NSANATORIA, INFO_AUT_PAES_ALFA.t_pratica_id, INFO_AUT_PAES_ALFA.ISTAT_AMM ",
									  			 "FROM ", PSITPIANI, "INFO_AUT_PAES_ALFA "
		);
		String sql = StringUtil.concateneString(sqlP,
												" WHERE SUAP_CODEP IN (:listaCodici)");
		
		parameters.put("listaCodici", listaCodici);
		return namedJdbcTemplateRead.query(sql, parameters, new InfoAutPaesAlfaRowMapper());
	}
	
	/**
	 * GetFile.jsp
	 * Prendo tutti i documenti di una specifica pratica:
	 * 
	 * "SELECT t_tipodoc.codice AS t_tipodoc_codice, t_tipodoc.descrizione AS t_tipodoc_descrizione, 
	 * 		   NVL( t_ke_doc.NAME , \'-\' ) AS NNAME, t_ke_doc_st.bin_content AS abin_content, 
	 * 		  t_ke_doc_attr.VALUE AS t_ke_doc_attr_value, NVL( t_ke_doc_st.t_ke_doc_st_ID , \'-\' ) AS N_t_ke_doc_st_ID 
	 * FROM t_pratica, t_documento, t_istanzadoc, t_tipodoc, t_ke_doc, t_ke_doc_st, t_ke_doc_attr 
	 * WHERE t_pratica.t_pratica_id = t_documento.t_pratica_id AND 
	 * 		 t_documento.t_documento_id = t_istanzadoc.t_documento_id(+) AND 
	 * 		 t_documento.t_tipodoc_id = t_tipodoc.t_tipodoc_id(+) AND 
	 * 		 t_ke_doc.t_ke_doc_id(+) = t_istanzadoc.t_ke_doc_id AND 
	 * 		 t_ke_doc_st.t_ke_doc_id(+) = t_ke_doc.t_ke_doc_id AND 
	 * 		 t_ke_doc_attr.t_ke_doc_id(+) = t_ke_doc.t_ke_doc_id AND
	 * 		t_ke_doc_st.T_KE_DOC_ST_ID = tno_putp_doc_alfresco.T_KE_DOC_ST_ID(+) AND 
	 * 	// POTREBBE AVER SENSO USARE IL CODICE PRATICA PER OTTENERE IL RECORD DI NOSTRO INTERESSE
	 * 		 T_PRATICA.T_PRATICA_ID = ?";
	 * 
	 * t_ke_doc_st.bin_content AS abin_content è la riga da cui viene ricavato il Blob con il contenuto effettivo del file
	 */
	public List<PuttDocBean> getPuttAllegati(String codicePratica,boolean is016) throws Exception
	{
		//List<String> doctabAllegati ) Arrays.asList("AUD0A", "AUD01", "AUD02", "AUD03", "AUD06", "AUD07", "AUD08", "AUD10", "AUD11", "AUD12", "AUD14");
		String civilia_schema = civiliaSchema(is016);
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = StringUtil.concateneString("SELECT t_tipodoc.codice AS t_tipodoc_codice, t_tipodoc.descrizione AS t_tipodoc_descrizione, ",
													  "NVL( t_ke_doc.NAME , \'-\' ) AS NNAME, t_ke_doc_st.bin_content AS abin_content, ",
													  "t_ke_doc_attr.VALUE AS t_ke_doc_attr_value, NVL( t_ke_doc_st.t_ke_doc_st_ID , \'-\' ) AS N_t_ke_doc_st_ID ",
													  " ,t_istanzadoc.nomefile as nomefile ",
													  " ,TNO_PUTP_DOC_ALFRESCO.ID_ALFRESCO as id_alfresco ",
													  "FROM ",
													  civilia_schema,"t_pratica, ",
													  civilia_schema,"t_documento, ",
													  civilia_schema,"t_istanzadoc,",
													  civilia_schema,"t_tipodoc, ",
													  civilia_schema,"t_ke_doc, ",
													  civilia_schema,"t_ke_doc_st, ",
													  civilia_schema,"t_ke_doc_attr, ",
													  civilia_schema,"TNO_PUTP_DOC_ALFRESCO ",
													  "WHERE t_pratica.t_pratica_id = t_documento.t_pratica_id AND ",
													  "t_documento.t_documento_id = t_istanzadoc.t_documento_id(+) AND ",
													  "t_documento.t_tipodoc_id = t_tipodoc.t_tipodoc_id(+) AND ",
//													  "t_ke_doc.t_ke_doc_id(+) = t_istanzadoc.t_ke_doc_id AND ",
													  "t_ke_doc.t_ke_doc_id = t_istanzadoc.t_ke_doc_id AND ",
													  "t_ke_doc_st.t_ke_doc_id(+) = t_ke_doc.t_ke_doc_id AND ",
													  "t_ke_doc_st.t_ke_doc_st_id = tno_putp_doc_alfresco.t_ke_doc_st_id(+) AND ",
													  "t_ke_doc_attr.t_ke_doc_id(+) = t_ke_doc.t_ke_doc_id AND ",
													  "T_PRATICA.CODICE = :codice");//AND t_tipodoc.codice in (:allegatitab)
		parameters.put("codice", codicePratica);
		return namedJdbcTemplateRead.query(sql, parameters, new PuttDocRowMapper());
	}

	private String civiliaSchema(boolean is016) {
		String civilia_schema=CIVILIA_CS;
		if(is016) {
			civilia_schema="CIVILIA_016.";
		}
		return civilia_schema;
	}
	
	/**
	 * getElenchiAutPaeTra.jsp
	 * Prendo particelle pratica
	 * 
	 * 	SELECT NVL(COMUNE  , \' \' ) as NCOMUNE, NVL(COD_CAT , \' \' ) as NCOD_CAT, 
	 * 		   NVL(LIVELLO , \' \' ) as NLIVELLO, NVL(SEZIONE , \' \' ) as NSEZIONE, 
	 * 		   NVL(FOGLIO  , \' \' ) as NFOGLIO, NVL(PARTICELLA  , \' \' ) as NPARTICELLA, 
	 * 		   NVL(SUB , \' \' ) as NSUB, NVL(DATA_RIFERIMENTO_CATASTALE , \' \' ) as NDATA_RIFERIMENTO_CATASTALE
	 * 		   CODICE_PRATICA, COD_ISTAT as NCOD_ISTAT, T_PRATICA_APPUTP_ID
	 * 	FROM TNO_PARTICELLECATASTALI 
	 *  WHERE CODICE_PRATICA = ( SELECT CODICE FROM T_PRATICA WHERE  t_pratica_id = ? ) 
	 */
	public List<LocalizzazionePuttBean> getPuttLocalizzazione(long praticaId,boolean is016) throws Exception
	{
		String civilia_schema = civiliaSchema(is016);
		final Map<String, Object> parameters = new HashMap<>();
		String sql = StringUtil.concateneString("SELECT NVL(COMUNE  , \' \' ) as NCOMUNE, NVL(COD_CAT , \' \' ) as NCOD_CAT, ",
											    "NVL(LIVELLO , \' \' ) as NLIVELLO, NVL(SEZIONE , \' \' ) as NSEZIONE, ",
											    "NVL(FOGLIO  , \' \' ) as NFOGLIO, NVL(PARTICELLA  , \' \' ) as NPARTICELLA, ",
											    "NVL(SUB , \' \' ) as NSUB, NVL(DATA_RIFERIMENTO_CATASTALE , \' \' ) as NDATA_RIFERIMENTO_CATASTALE, ",
											    "CODICE_PRATICA, COD_ISTAT as NCOD_ISTAT, T_PRATICA_ID ",
											    "FROM ", PSITPIANI, "TNO_PARTICELLECATASTALI ",
											 	"WHERE CODICE_PRATICA = ( SELECT CODICE FROM ", civilia_schema ,"T_PRATICA WHERE  t_pratica_id = :praticaId )" );
		parameters.put("praticaId", praticaId);
		return namedJdbcTemplateRead.query(sql, parameters, new LocalizzazionePuttRowMapper());
	}
	
	/**
	 * getFileProvvedimento.jsp
	 * Prendo file provvedimento finale	
	 * 
	 * 	SELECT t_tipodoc.codice AS t_tipodoc_codice, t_tipodoc.descrizione AS t_tipodoc_descrizione, 
	 * 		   NVL( t_ke_doc.NAME , \'-\' ) AS NNAME, t_ke_doc_st.bin_content AS abin_content, 
	 * 		   t_ke_doc_attr.VALUE AS t_ke_doc_attr_value, NVL( t_ke_doc_st.t_ke_doc_st_ID , \'-\' ) AS N_t_ke_doc_st_ID 
	 * 	FROM t_pratica, t_documento, t_istanzadoc, t_tipodoc, t_ke_doc, t_ke_doc_st, t_ke_doc_attr 
	 * 	WHERE t_pratica.t_pratica_id = t_documento.t_pratica_id AND 
	 * 		  t_documento.t_documento_id = t_istanzadoc.t_documento_id(+) AND 
	 * 		  t_documento.t_tipodoc_id = t_tipodoc.t_tipodoc_id(+) AND 
	 * 		  t_ke_doc.t_ke_doc_id(+) = t_istanzadoc.t_ke_doc_id AND 
	 * 		  t_ke_doc_st.t_ke_doc_id(+) = t_ke_doc.t_ke_doc_id AND 
	 * 		  t_ke_doc_attr.t_ke_doc_id(+) = t_ke_doc.t_ke_doc_id AND 
	 * 		  T_PRATICA.T_PRATICA_ID = ? AND 
	 * 		  T_PRATICA.CODICE = ? AND 
	 * 		  t_tipodoc.codice = 'AUD15' "; 
	 */
	//TODO: PRENDO DA get allegati
	
	/**
	 * getRicevuta.jsp
	 * Ricevuta di trasmissione 
	 * 
	 * "SELECT bin_pdfprot_content from TNO_PROTOCOLLO_USCITA 
	 *  WHERE 1 = 1 AND 
	 *  	  T_PRATICA_ID = ? AND 
	 *  	  CODICE_PRATICA = ? AND 
	 *  	  T_KE_DOC_ST_ID = ?";    
	 */
	public List<TnoProtocolloUscita> getPuttRicevutaTrasmissione(long tPraticaId) throws Exception
	{
		final String sql = StringUtil.concateneString("SELECT bin_pdfprot_content, bin_pdf_content, codice_pratica, esito_protocollazione, ",
													  "t_ke_doc_st_id, numero_protocollo, data_protocollo, titolario_protocollo ",
													  "FROM ", CIVILIA_CS, "TNO_PROTOCOLLO_USCITA ",
													  "WHERE T_PRATICA_ID = :t_pratica_id");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("t_pratica_id", tPraticaId);
		return namedJdbcTemplateRead.query(sql, parameters, new TnoProtocolloUscitaRowMapper());		
	}
	
	
	public List<CiviliaPratica> getCiviliaPratica(Long tPraticaId, boolean is016) throws Exception {
		String civilia_schema = civiliaSchema(is016);
		final String sql = StringUtil.concateneString(
				"SELECT CODICE,DATAATTIVAZIONE,DATAARRIVO,STATOPRATICA,T_PRATICA_ID,T_AGSU_TIPO_EVENTO_ID,CODICEINTERNO ,PRAT_COD_NUM1 ,PRAT_COD_NUM2 ,PRAT_COD_NUM3 ", 
				" FROM ", civilia_schema,"T_PRATICA ",
				" WHERE T_PRATICA_ID = :t_pratica_id ");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("t_pratica_id", tPraticaId);
		return namedJdbcTemplateRead.query(sql, parameters, new CiviliaPraticaRowMapper());
	}
	
	public List<PptrSoprintendenzaNoteSt> getTnoPptrSopNote(String codicePraticaAppptr,long progr) throws Exception {
		String civilia_schema = civiliaSchema(false);
		final String sql = StringUtil.concateneString(
				"SELECT SPUNTA,NOTE ", 
				" FROM ", civilia_schema,"TNO_PPTR_SOPNOTE_ST ",
				" WHERE CODICE_PRATICA_APPPTR = :codicePraticaAppptr AND PROG= :prog ");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("codicePraticaAppptr", codicePraticaAppptr);
		parameters.put("prog", progr);
		return namedJdbcTemplateRead.query(sql, parameters, new PptrSoprintendenzaNoteStRowMapper());
	}
	
	public List<PraticaLavorazione> getListaPraticheInLavorazione(int fromRowNum, int toRowNum) {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT * FROM ( SELECT rownum rnum, a.* FROM(", queryPraticheInLavorazione, ") a "));
		sql.append(" WHERE rownum <=? ) WHERE rnum >=?");
		parameters.add(toRowNum);
		parameters.add(fromRowNum);
		List<PraticaLavorazione> list = super.queryForList(sql.toString(), new PraticaLavorazioneRowMapper(), parameters);
		return list;
	}

	public Long countPratichePptrInLavorazione() {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT count(*) FROM(", queryPraticheInLavorazione, ") a "));
		return super.queryForObject(sql.toString(),Long.class,parameters);
	}
	
	public List<TnoMailEnti> getTnoMailEnti() {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT * FROM ",
						PSITPIANI,
						"TNO_MAIL_ENTI "));
		List<TnoMailEnti> list = super.queryForList(sql.toString(), new TnoMailEntiRowMapper(), parameters);
		return list;
	}
	
	public List<TnoSopMatriceTerritorio> getTnoSopMatriceTerritorio() {
		List<Object> parameters = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT ENTE_RIFERIMENTO,PEC_ENTE_RIFERIMENTO,PROV_RIFERIMENTO,MAIL_AGGIUNTIVA,COD_ISTAT,COMUNE FROM ",
						PSITPIANI,
						"TNO_SOP_MATRICE_TERRITORIO "));
		List<TnoSopMatriceTerritorio> list = super.queryForList(sql.toString(), new TnoSopMatriceTerritorioRowMapper(), parameters);
		return list;
	}
	
	
	/**
	 * recupera dell'iDCmis in documentale sit-puglia per le pratiche putt
	 *  utilizzato per sanare file a 0 byte su PUTT
	 * 
	 * @autor Adriano Colaianni
	 * @date 28 nov 2021
	 * @param tKeStDocId
	 * @return restituisce l'idCms a partire da tkestdocid (prende ultima versione), null altrimenti
	 */
	public String getIdCmsFromTkeStDocId(Integer tKeStDocId) {
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("SELECT tpda.ID_ALFRESCO FROM ",civiliaSchema(false),
						"TNO_PUTP_DOC_ALFRESCO tpda WHERE tpda.T_KE_DOC_ST_ID = :tKeStDocId ORDER BY tpda.ID_ALFRESCO DESC"));
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("tKeStDocId", tKeStDocId);
		List<String> list = namedJdbcTemplateRead.queryForList(sql.toString(), parameters, String.class);
		if(ListUtil.isNotEmpty(list)){
			return list.get(0);
		}else {
			return null;
		}
	}
}
