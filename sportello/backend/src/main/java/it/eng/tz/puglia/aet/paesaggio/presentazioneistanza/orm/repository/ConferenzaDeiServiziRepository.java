package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.ConferenzaApiPartecipantiExtendedDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.EnteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConferenzaApiCreatoreRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConferenzaApiDocumentazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConferenzaApiImpresaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConferenzaApiIstanzaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConferenzaApiPartecipanteExtendedRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConferenzaApiResponsabileRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConferenzaApiRichiedenteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.EnteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaCdsSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioreDocSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.UlterioriDocService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.cds.bean.ConferenzaApiCreatoreDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiDocumentazioneDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiImpresaDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiIncontroDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiIstanzaDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiLocalizzazioneDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiResponsabileDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiRichiedenteDto;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaAttivitaEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaAzioneEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaCategoriaDocumentoEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaFormaGiuridicaEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaModalitaEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaTipoEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaTipologiaDocumentazioneEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaTipologiaPraticaEnum;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Repository
public class ConferenzaDeiServiziRepository extends GenericCrudDao<PraticaCdsDTO, PraticaCdsSearch> {
	
	private final ConferenzaApiRichiedenteRowMapper richiedenteRowMapper = new ConferenzaApiRichiedenteRowMapper();
	private final ConferenzaApiCreatoreRowMapper creatoreRowMapper = new ConferenzaApiCreatoreRowMapper();
	private final ConferenzaApiResponsabileRowMapper responsabileRowMapper = new ConferenzaApiResponsabileRowMapper();
	private final ConferenzaApiIstanzaRowMapper istanzaRowMapper = new ConferenzaApiIstanzaRowMapper();
	private final ConferenzaApiImpresaRowMapper impresaRowMapper = new ConferenzaApiImpresaRowMapper();
	private final ConferenzaApiDocumentazioneRowMapper documentazioneRowMapper = new ConferenzaApiDocumentazioneRowMapper();
	private final EnteRowMapper enteRowMapper = new EnteRowMapper();
	private final ConferenzaApiPartecipanteExtendedRowMapper partecipanteExtendedRowMapper = new ConferenzaApiPartecipanteExtendedRowMapper();


	public enum StatoConferenza{
		Compilazione,
		Bozza,
		Valutazione,
		Chiusa,
		Archiviata;
	}

	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
	@Value("${rubrica.riferimento.applicazione}")
	private String codiceApplicazioneAutorita;


	@Autowired
	private PraticaCdsRepository cdsRepository;
	
	@Autowired
	private UlterioriDocService ulterioriDocSvc;

	public ConferenzaTipoEnum codiceTipoConferenza(final String id) {
		final StopWatch sw = LogUtil.startLog("codiceTipoConferenza ", id);
		this.logger.info("Start codiceTipoConferenza {}", id);
		try {
			return ConferenzaTipoEnum.fromCodice(this.getSettings(id).getTipo());
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

//	public String cfRdp(final String idPratica) {
//		final StopWatch sw = LogUtil.startLog("cfRdp ", idPratica);
//		this.logger.info("Start cfRdp {}", idPratica);
//		try {
//			final QPratica Pratica = QPratica.Pratica;
//			return new JPAQuery<>(this.entityManager).from(Pratica).where(Pratica.id.eq(idPratica))
//					.select(Pratica.rdpCodiceFiscale).fetchOne();
//		} finally {
//			this.logger.info(LogUtil.stopLog(sw));
//		}
//	}
	
	public ConferenzaApiRichiedenteDto richiedente(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("richiedente ", idPratica);
		this.logger.info("Start richiedente {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select ref.\"nome\", ref.\"cognome\", ref.\"codice_fiscale\", ref.\"mail\", ref.\"telefono\" from \"presentazione_istanza\".\"referenti\" ref",
					" join \"presentazione_istanza\".\"pratica\" p on ref.\"pratica_id\" = p.\"id\"",
					" where p.\"id\" = ? and ref.\"tipo_referente\" = 'SD'");
	        final List<Object> parameters = new ArrayList<Object>();
	        parameters.add(UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return super.queryForObjectTxRead(sql, this.richiedenteRowMapper, parameters);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public ConferenzaApiResponsabileDto responsabile(final String id) {
		final StopWatch sw = LogUtil.startLog("responsabile ", id);
		this.logger.info("Start responsabile {}", id);
		try {
			String sql = StringUtil.concateneString(
					"select cds.\"nome_responsabile\" as nome, cds.\"cognome_responsabile\" as cognome, cds.\"codice_fiscale_responsabile\" as codice_fiscale,",
					" cds.\"mail_responsabile\" as mail from \"presentazione_istanza\".\"pratica_cds\" cds",
					" where cds.\"id\" = ?");
	        final List<Object> parameters = new ArrayList<Object>();
	        parameters.add(id);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return super.queryForObjectTxRead(sql, this.responsabileRowMapper, parameters);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public ConferenzaApiLocalizzazioneDto localizzazione(final String id) {
		final StopWatch sw = LogUtil.startLog("localizzazione ", id);
		this.logger.info("Start localizzazione {}", id);
		try {
			final ConferenzaApiLocalizzazioneDto result = new ConferenzaApiLocalizzazioneDto();
			final PraticaCdsDTO settings = this.getSettings(id);

			result.setComune(settings.getComunePertinenza());
			result.setIndirizzo(settings.getIndirizzoPertinenza());
			result.setProvincia(settings.getProvinciaPertinenza());

			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public ConferenzaApiImpresaDto impresa(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("impresa ", idPratica);
		this.logger.info("Start impresa {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select ref.\"ditta_cf\" as codice_fiscale, ref.\"ditta_partita_iva\" as partita_iva, ref.\"ditta_ente\" as denominazione from \"presentazione_istanza\".\"referenti\" ref",
					" join \"presentazione_istanza\".\"pratica\" p on ref.\"pratica_id\" = p.\"id\"",
					" where p.\"id\" = :idPratica and ref.\"tipo_referente\" = 'SD' and ref.\"ditta\" = true");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiImpresaDto> list = namedJdbcTemplateRead.query(sql, parameters, this.impresaRowMapper);
			if (ListUtil.size(list) > 0) {
				final ConferenzaApiImpresaDto azienda = list.get(0);
				azienda.setFormaGiuridica(ConferenzaFormaGiuridicaEnum.ALTRE_FORME);
				return azienda;
			}
			return null;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public ConferenzaApiIstanzaDto istanza(final String idPratica, final String id) {
		final StopWatch sw = LogUtil.startLog("istanza ", idPratica, " ", id);
		this.logger.info("Start istanza {} {}", idPratica, id);
		try {
			String sql = StringUtil.concateneString(
					"select p.\"codice_pratica_appptr\" as codice, 	concat(p.\"oggetto\", ' - ', tp.\"descrizione\") as oggetto",
					", p.\"data_presentazione\" as data_creazione from \"presentazione_istanza\".\"pratica\" p",
					" join \"presentazione_istanza\".\"tipo_procedimento\" tp on p.\"tipo_procedimento\" = tp.\"id\" ",
					" where p.\"id\" = ?");
	        final List<Object> parameters = new ArrayList<Object>();
	        parameters.add(UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final ConferenzaApiIstanzaDto result = super.queryForObjectTxRead(sql, istanzaRowMapper, parameters);
			result.setTipologia(ConferenzaTipologiaPraticaEnum.PAESAGGIO);
			final PraticaCdsDTO settings = this.getSettings(id);
			result.setTermineRichiestaIntegrazione(settings.getTermineRichiestaIntegrazione());
			result.setTerminePareri(settings.getTerminePareri());
			result.setPrimaSessione(settings.getPrimaSessione());
			result.setDataTermine(settings.getDataTermine());
			result.setTipologiaAttivita(ConferenzaAttivitaEnum.fromCodice(settings.getAttivita()));
			result.setAzioneAttivita(ConferenzaAzioneEnum.fromCodice(settings.getAzione()));
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public ConferenzaApiIncontroDto incontro(final String id) {
		final StopWatch sw = LogUtil.startLog("incontro ", id);
		this.logger.info("Start incontro {}", id);
		try {
			final ConferenzaApiIncontroDto result = new ConferenzaApiIncontroDto();
			final PraticaCdsDTO settings = this.getSettings(id);

			result.setCap(settings.getCap());
			result.setComune(settings.getComune());
			result.setIndirizzo(settings.getIndirizzo());
			result.setLink(settings.getLink());
			result.setModalita(ConferenzaModalitaEnum.fromCodice(settings.getModalitaIncontro()));
			result.setOrario(settings.getOrario());
			result.setProvincia(settings.getProvincia());

			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<ConferenzaApiPartecipantiExtendedDto> partecipanti(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("partecipanti ", idPratica);
		this.logger.info("Start partecipanti {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select e.\"codice_fiscale\", e.\"descrizione\" as nome_ente, coalesce(ea.\"pec\", ea.\"mail\") as pec, e.\"codice\", e.\"tipo\" ",
					" from \"common\".\"ente\" e join \"common\".\"ente_attribute\" ea on e.\"id\" = ea.\"ente_id\"",
					" join \"common\".\"applicazione\" app on app.\"id\" = ea.\"applicazione_id\"",
					" join \"common\".\"paesaggio_organizzazione\" po on e.\"id\" = po.\"ente_id\"",
					" join \"presentazione_istanza\".\"pratica\" p on p.\"ente_delegato\" = po.\"id\"::text",
					" where p.\"id\" = :idPratica and app.\"codice\" = :appId");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			parameters.put("appId", this.codiceApplicazioneAutorita);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, this.partecipanteExtendedRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<ConferenzaApiDocumentazioneDto> documentazione(final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("documentazione ", idPratica);
		this.logger.info("Start documentazione {}", idPratica);
		try {
			final List<ConferenzaApiDocumentazioneDto> result = new ArrayList<>();
			result.addAll(this.documentazioneCaricata(idPratica));
			result.addAll(this.documentazioneAmministrativa(idPratica));
			result.addAll(this.documentazioneAmministrativaIntegrazione(idPratica));
			result.addAll(this.documentazioneProgettuale(idPratica));
			result.addAll(this.documentazioneProgettualeIntegrazione(idPratica));
//			result.addAll(this.comunicazioneAllegati(idPratica));
//			result.addAll(this.comunicazioni(idPratica));
			result.addAll(this.documentazioneIstruttoria(idPratica));
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	private List<ConferenzaApiDocumentazioneDto> documentazioneAmministrativa(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("documentazioneAmministrativa ", idPratica);
		this.logger.info("Start documentazioneAmministrativa {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select a.\"nome_file\", a.\"id_cms\" from \"presentazione_istanza\".\"allegati\" a",
					" join \"presentazione_istanza\".\"allegati_tipo_contenuto\" at on a.\"id\" = at.\"allegati_id\"",
					" join \"presentazione_istanza\".\"tipo_contenuto\" t on at.\"tipo_contenuto_id\" = t.\"id\"",
					" where a.\"pratica_id\" = :idPratica and a.\"integrazione_id\" is null and t.\"sezione\" like 'DOC_AMMINISTRATIVA%' ");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiDocumentazioneDto> result = namedJdbcTemplateRead.query(sql, parameters, this.documentazioneRowMapper);
			this.setDocumentazioneEnum(result, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA,
					ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_DOCUMENTAZIONE_CORREDO_ISTANZA);
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	private void setDocumentazioneEnum(final List<? extends ConferenzaApiDocumentazioneDto> list,
			final ConferenzaTipologiaDocumentazioneEnum tipo, final ConferenzaCategoriaDocumentoEnum categoria) {
		list.forEach(documento -> {
			documento.setTipo(tipo);
			documento.setCategoria(categoria);
		});
	}

	private List<ConferenzaApiDocumentazioneDto> documentazioneAmministrativaIntegrazione(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("documentazioneAmministrativaIntegrazione ", idPratica);
		this.logger.info("Start documentazioneAmministrativaIntegrazione {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select a.\"nome_file\", a.\"id_cms\" from \"presentazione_istanza\".\"allegati\" a",
					" join \"presentazione_istanza\".\"allegati_tipo_contenuto\" at on a.\"id\" = at.\"allegati_id\"",
					" join \"presentazione_istanza\".\"tipo_contenuto\" t on at.\"tipo_contenuto_id\" = t.\"id\"",
					" where a.\"pratica_id\" = :idPratica and a.\"integrazione_id\" is not null and t.\"sezione\" like 'DOC_AMMINISTRATIVA%' ");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiDocumentazioneDto> result = namedJdbcTemplateRead.query(sql, parameters, this.documentazioneRowMapper);
			this.setDocumentazioneEnum(result, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA,
					ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_DOCUMENTAZIONE_CORREDO_ISTANZA);
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	private List<ConferenzaApiDocumentazioneDto> documentazioneProgettuale(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("documentazioneProgettuale ", idPratica);
		this.logger.info("Start documentazioneProgettuale {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select a.\"nome_file\", a.\"id_cms\" from \"presentazione_istanza\".\"allegati\" a",
					" join \"presentazione_istanza\".\"allegati_tipo_contenuto\" at on a.\"id\" = at.\"allegati_id\"",
					" join \"presentazione_istanza\".\"tipo_contenuto\" t on at.\"tipo_contenuto_id\" = t.\"id\"",
					" where a.\"pratica_id\" = :idPratica and a.\"integrazione_id\" is null and t.\"sezione\" like 'DOC_TECNICA' ");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiDocumentazioneDto> result = namedJdbcTemplateRead.query(sql, parameters, this.documentazioneRowMapper);
			this.setDocumentazioneEnum(result, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA,
					ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_DOCUMENTAZIONE_CORREDO_ISTANZA);
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	private List<ConferenzaApiDocumentazioneDto> documentazioneProgettualeIntegrazione(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("documentazioneProgettualeIntegrazione ", idPratica);
		this.logger.info("Start documentazioneProgettualeIntegrazione {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select a.\"nome_file\", a.\"id_cms\" from \"presentazione_istanza\".\"allegati\" a",
					" join \"presentazione_istanza\".\"allegati_tipo_contenuto\" at on a.\"id\" = at.\"allegati_id\"",
					" join \"presentazione_istanza\".\"tipo_contenuto\" t on at.\"tipo_contenuto_id\" = t.\"id\"",
					" where a.\"pratica_id\" = :idPratica and a.\"integrazione_id\" is not null and t.\"sezione\" like 'DOC_TECNICA' ");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiDocumentazioneDto> result = namedJdbcTemplateRead.query(sql, parameters, this.documentazioneRowMapper);
			this.setDocumentazioneEnum(result, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA,
					ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_DOCUMENTO_INTEGRATIVO);
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

//	private List<ConferenzaApiDocumentazioneExtendedDto> comunicazioni(final String idPratica) {
//		final StopWatch sw = LogUtil.startLog("comunicazioni ", idPratica);
//		this.logger.info("Start comunicazioni {}", idPratica);
//		try {
//			final QIstruttoria istruttoria = QIstruttoria.istruttoria;
//			final QPratica pratica = QPratica.pratica;
//			final QComunicazione comunicazione = QComunicazione.comunicazione1;
//			final QComunicazioneStato comunicazioneStato = QComunicazioneStato.comunicazioneStato;
//			final QComunicazioneTipo comunicazioneTipo = QComunicazioneTipo.comunicazioneTipo;
//			final List<ConferenzaApiDocumentazioneExtendedDto> result = new JPAQuery<>(this.entityManager)
//					.from(comunicazione).innerJoin(comunicazioneStato)
//					.on(comunicazioneStato.id.eq(comunicazione.comunicazioneStato.id)).innerJoin(comunicazioneTipo)
//					.on(comunicazioneTipo.id.eq(comunicazione.comunicazioneTipo.id)).innerJoin(pratica)
//					.on(comunicazione.pratica.id.eq(pratica.id)).innerJoin(istruttoria)
//					.on(istruttoria.pratica.id.eq(pratica.id))
//					.where(istruttoria.id.eq(idPratica).and(comunicazioneTipo.showInList.eq(true))
//							.and(comunicazione.cmisIdModelloCaricato.isNotNull())
//							.and(comunicazione.protocollo.isNotNull())
//							.and(comunicazione.comunicazioneStato.id.eq(ComunicazioneStatoEnum.TRASMESSA.name())))
//					.select(Projections.constructor(ConferenzaApiDocumentazioneExtendedDto.class,
//							comunicazione.fileNameModelloCaricato.as("nomeFile"),
//							comunicazione.cmisIdModelloCaricato.as("cmisId"),
//							comunicazione.protocollo.as("numeroProtocollo"),
//							comunicazione.dataProtocollo.as("dataProtocollo")))
//					.fetch();
//			this.setDocumentazioneEnum(result, ConferenzaTipologiaDocumentazioneEnum.INTERAZIONI,
//					ConferenzaCategoriaDocumentoEnum.INTERAZIONI_DOCUMENTAZIONE_INTEGRATIVA);
//			return result;
//		} finally {
//			this.logger.info(LogUtil.stopLog(sw));
//		}
//	}

//	private List<ConferenzaApiDocumentazioneDto> comunicazioneAllegati(final String idPratica) {
//		final StopWatch sw = LogUtil.startLog("comunicazioneAllegati ", idPratica);
//		this.logger.info("Start comunicazioneAllegati {}", idPratica);
//		try {
//			final QIstruttoria istruttoria = QIstruttoria.istruttoria;
//			final QPratica pratica = QPratica.pratica;
//			final QComunicazione comunicazione = QComunicazione.comunicazione1;
//			final QComunicazioneAllegato allegato = QComunicazioneAllegato.comunicazioneAllegato;
//			final QComunicazioneTipo comunicazioneTipo = QComunicazioneTipo.comunicazioneTipo;
//			final List<ConferenzaApiDocumentazioneDto> result = new JPAQuery<>(this.entityManager).from(allegato)
//					.innerJoin(comunicazione).on(allegato.comunicazione.id.eq(comunicazione.id))
//					.innerJoin(comunicazioneTipo).on(comunicazioneTipo.id.eq(comunicazione.comunicazioneTipo.id))
//					.innerJoin(pratica).on(comunicazione.pratica.id.eq(pratica.id)).innerJoin(istruttoria)
//					.on(istruttoria.pratica.id.eq(pratica.id))
//					.where(istruttoria.id.eq(idPratica).and(comunicazioneTipo.showInList.eq(true))
//							.and(allegato.cmisIdDocumentazioneAmministrativa.isNull())
//							.and(allegato.cmisIdDocumentazioneProgettuale.isNull()).and(allegato.cmisId.isNotNull()))
//					.select(Projections.fields(ConferenzaApiDocumentazioneDto.class, allegato.fileName.as("nomeFile"),
//							allegato.cmisId.as("cmisId")))
//					.fetch();
//			this.setDocumentazioneEnum(result, ConferenzaTipologiaDocumentazioneEnum.INTERAZIONI,
//					ConferenzaCategoriaDocumentoEnum.INTERAZIONI_DOCUMENTAZIONE_INTEGRATIVA);
//			return result;
//		} finally {
//			this.logger.info(LogUtil.stopLog(sw));
//		}
//	}

	private List<ConferenzaApiDocumentazioneDto> documentazioneIstruttoria(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("documentazioneIstruttoria ", idPratica);
		this.logger.info("Start documentazioneIstruttoria {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select a.\"nome_file\", a.\"id_cms\" from \"presentazione_istanza\".\"allegati\" a",
					" join \"presentazione_istanza\".\"allegati_relazione_ente\" r on a.\"id\" = r.\"id_allegato\"",
					" where a.\"pratica_id\" = :idPratica ");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiDocumentazioneDto> relazioniEnte = namedJdbcTemplateRead.query(sql, parameters, this.documentazioneRowMapper);
			this.setDocumentazioneEnum(relazioniEnte, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTO_PRE_ISTRUTTORIA,
					ConferenzaCategoriaDocumentoEnum.INTERAZIONI_DOCUMENTAZIONE_INTEGRATIVA);
			
			sql = StringUtil.concateneString(
					"select a.\"nome_file\", a.\"id_cms\" from \"presentazione_istanza\".\"allegati\" a",
					" join \"presentazione_istanza\".\"parere_soprintendenza_allegato\" p on a.\"id\" = p.\"id_allegato\"",
					" where a.\"pratica_id\" = :idPratica ");
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiDocumentazioneDto> pareriSoprintendenza = namedJdbcTemplateRead.query(sql, parameters, this.documentazioneRowMapper);
			this.setDocumentazioneEnum(pareriSoprintendenza, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTO_PRE_ISTRUTTORIA,
					ConferenzaCategoriaDocumentoEnum.INTERAZIONI_DOCUMENTAZIONE_INTEGRATIVA);
			
			final UlterioreDocSearch search = new UlterioreDocSearch();
			search.setIdFascicolo(UUID.fromString(idPratica));
			PaginatedList<AllegatiDTO> ulterioriDocAll = this.ulterioriDocSvc.search(search);
			final List<ConferenzaApiDocumentazioneDto> ulterioreDoc = new ArrayList<ConferenzaApiDocumentazioneDto>();
			if(ulterioriDocAll != null && ulterioriDocAll.getList() != null && ulterioriDocAll.getList().size() > 0) {
				for(AllegatiDTO allegato : ulterioriDocAll.getList()) {
					final ConferenzaApiDocumentazioneDto doc = new ConferenzaApiDocumentazioneDto();
					doc.setNomeFile(allegato.getNomeFile());
					doc.setCmisId(allegato.getIdCms());
					ulterioreDoc.add(doc);
				}
			}
			if(ulterioreDoc.size() > 0) {
				this.setDocumentazioneEnum(ulterioreDoc, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTO_PRE_ISTRUTTORIA,
						ConferenzaCategoriaDocumentoEnum.INTERAZIONI_DOCUMENTAZIONE_INTEGRATIVA);
			}
			final List<ConferenzaApiDocumentazioneDto> result = new ArrayList<ConferenzaApiDocumentazioneDto>();
			result.addAll(relazioniEnte);
			result.addAll(pareriSoprintendenza);
			result.addAll(ulterioreDoc);
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	private List<ConferenzaApiDocumentazioneDto> documentazioneCaricata(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("documentazioneCaricata ", idPratica);
		this.logger.info("Start documentazioneCaricata {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select a.\"nome_file\", a.\"id_cms\" from \"presentazione_istanza\".\"allegati\" a",
					" join \"presentazione_istanza\".\"allegati_tipo_contenuto\" at on a.\"id\" = at.\"allegati_id\"",
					" join \"presentazione_istanza\".\"tipo_contenuto\" t on at.\"tipo_contenuto_id\" = t.\"id\"",
					" where a.\"pratica_id\" = :idPratica and t.\"sezione\" like 'GENERA_STAMPA_PREVIEW' ");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final List<ConferenzaApiDocumentazioneDto> result = namedJdbcTemplateRead.query(sql, parameters, this.documentazioneRowMapper);
			this.setDocumentazioneEnum(result, ConferenzaTipologiaDocumentazioneEnum.DOCUMENTO_PRE_ISTRUTTORIA,
					ConferenzaCategoriaDocumentoEnum.INTERAZIONI_DOCUMENTAZIONE_INTEGRATIVA);
			return result;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	private PraticaCdsDTO getSettings(final String id) {
		final PraticaCdsDTO pk = new PraticaCdsDTO();
		pk.setId(id);
		return this.cdsRepository.find(pk);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#updateIdCds(String,
	 *      long)
	 */
	
	public void updateIdCds(final String id, final long idCds) {
		final StopWatch sw = LogUtil.startLog("updateIdCds ", id, " ", idCds);
		this.logger.info("Start updateIdCds {} {}", id, idCds);
		try {
			final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_cds\"")
                    .append(" set \"id_cds\" = ?")
                    .append(" where \"id\" = ?")
                    .append(" and \"id_cds\" is null").toString();
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(idCds);
			parameters.add(id);
	        super.update(sql, parameters);	
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public ConferenzaApiCreatoreDto creatore(final String id) {
		final StopWatch sw = LogUtil.startLog("creatore ", id);
		this.logger.info("Start creatore {}", id);
		try {
			String sql = StringUtil.concateneString(
					"select p.\"nome_creatore\" as nome, p.\"cognome_creatore\" as cognome, p.\"codice_fiscale_creatore\" as codice_fiscale, p.\"mail_creatore\" as mail, "
					,"p.\"telefono_creatore\" as telefono from \"presentazione_istanza\".\"pratica_cds\" p",
					" where p.\"id\" = ?");
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(id);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return super.queryForObjectTxRead(sql, this.creatoreRowMapper, parameters);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#idPratica(java.lang.String)
	 */
	
	public String idPratica(final String id) {
		final StopWatch sw = LogUtil.startLog("idPratica ", id);
		this.logger.info("Start idPratica {}", id);
		try {
			String sql = StringUtil.concateneString(
					"select cds.\"id_pratica\" from \"presentazione_istanza\".\"pratica_cds\" cds",
					" where cds.\"id\" = ?");
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(id);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return super.queryForObject(sql, String.class, parameters);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#idCds(java.lang.String)
	 */
	
	public List<Long> idCds(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("idCds ", idPratica);
		this.logger.info("Start idCds {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select cds.\"id_cds\" from \"presentazione_istanza\".\"pratica_cds\" cds",
					" where cds.\"id_pratica\" = :idPratica and cds.\"id_cds\" is not null");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", idPratica);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.queryForList(sql, parameters, Long.class);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#getPartecipante(java.lang.String,
	 *      int)
	 */
	
//	public ConferenzaApiPartecipantiExtendedDto getPartecipante(final String idPratica, final int idEnte) {
//		final StopWatch sw = LogUtil.startLog("getPartecipante ", idPratica, " ", idEnte);
//		this.logger.info("Start getPartecipante {} {}", idPratica, idEnte);
//		try {
//			final QIstruttoria istruttoria = QIstruttoria.istruttoria;
//			final QIstruttoriaEnte istruttoriaEnte = QIstruttoriaEnte.istruttoriaEnte;
//			final QEnte ente = QEnte.ente1;
//
//			return new JPAQuery<>(this.entityManager).from(istruttoriaEnte).innerJoin(istruttoria)
//					.on(istruttoriaEnte.istruttoria.id.eq(istruttoria.id)).innerJoin(ente)
//					.on(istruttoriaEnte.ente.id.eq(ente.id))
//					.where(istruttoria.id.eq(idPratica).and(istruttoriaEnte.ente.id.eq(idEnte)))
//					.select(this.projetionPartecipante(istruttoriaEnte, ente)).fetchOne();
//		} finally {
//			this.logger.info(LogUtil.stopLog(sw));
//		}
//	}

	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#creatoreComitato(java.lang.String)
	 */
	
	public ConferenzaApiCreatoreDto creatoreComitato(final String id) {
		final StopWatch sw = LogUtil.startLog("creatoreComitato ", id);
		this.logger.info("Start creatoreComitato {}", id);
		try {
//			final QIstruttoriaCds PraticaCdsDTO = QIstruttoriaCds.PraticaCdsDTO;
//
//			final List<ConferenzaApiCreatoreDto> creatori = new JPAQuery<>(this.entityManager).from(PraticaCdsDTO)
//					.where(PraticaCdsDTO.id.eq(id).and(PraticaCdsDTO.comitato.eq(true))
//							.and(PraticaCdsDTO.codiceFiscaleCreatore.isNotNull()))
//					.select(Projections.fields(ConferenzaApiCreatoreDto.class, PraticaCdsDTO.nomeCreatore.as("nome"),
//							PraticaCdsDTO.cognomeCreatore.as("cognome"),
//							PraticaCdsDTO.codiceFiscaleCreatore.as("codiceFiscale"),
//							PraticaCdsDTO.telefonoCreatore.as("telefono"), PraticaCdsDTO.mailCreatore.as("mail")))
//					.fetch();
//			if (ListUtil.isNotEmpty(creatori))
//				return creatori.get(0);
			return null;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#codiceGruppoComitato(java.lang.String)
	 */
	
	public String codiceGruppoComitato(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("codiceGruppoComitato ", idPratica);
		this.logger.info("Start codiceGruppoComitato {}", idPratica);
		try {
//			final QIstruttoriaEnte istruttoriaEnte = QIstruttoriaEnte.istruttoriaEnte;
//			return super.from(istruttoriaEnte)
//					.where(istruttoriaEnte.istruttoria.id.eq(idPratica).and(istruttoriaEnte.comitato.eq(true)))
//					.select(Expressions.asString(IUserService.ROLE_START_ENTE)
//							.concat(istruttoriaEnte.ente.tipoEnte.codice).concat("-")
//							.concat(istruttoriaEnte.ente.codice))
//					.fetchOne();
			return null;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#codiceFiscaleComitato(java.lang.String)
	 */
	
	public String codiceFiscaleComitato(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("codiceFiscaleComitato ", idPratica);
		this.logger.info("Start codiceFiscaleComitato {}", idPratica);
		try {
//			final QIstruttoriaEnte istruttoriaEnte = QIstruttoriaEnte.istruttoriaEnte;
//			return super.from(istruttoriaEnte)
//					.where(istruttoriaEnte.istruttoria.id.eq(idPratica).and(istruttoriaEnte.comitato.eq(true)))
//					.select(istruttoriaEnte.ente.codiceFiscale).fetchOne();
			return null;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#codiceUfficioComitato(java.lang.String)
	 */
	
	public String codiceUfficioComitato(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("codiceUfficioComitato ", idPratica);
		this.logger.info("Start codiceUfficioComitato {}", idPratica);
		try {
//			final QIstruttoriaEnte istruttoriaEnte = QIstruttoriaEnte.istruttoriaEnte;
//			final List<String> uffici = super.from(istruttoriaEnte)
//					.where(istruttoriaEnte.istruttoria.id.eq(idPratica).and(istruttoriaEnte.comitato.eq(true)).and(
//							istruttoriaEnte.ente.tipoEnte.codice.eq(IConferenzaDeiServiziService.UNITA_ORGANIZZATIVA)))
//					.select(istruttoriaEnte.ente.codiceFiscale).fetch();
//			if (ListUtil.isNotEmpty(uffici))
//				return uffici.get(0);
			return null;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#autoritaCompetente(java.lang.String)
	 */
	
	public EnteDto autoritaCompetente(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("codiceUfficioAutoritaCompetente ", idPratica);
		this.logger.info("Start codiceUfficioAutoritaCompetente {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select e.\"nome\", e.\"codice\", e.\"codice_fiscale\", e.\"tipo\", coalesce(ea.\"pec\", ea.\"mail\") as pec from \"common\".\"ente\" e",
					" join \"common\".\"paesaggio_organizzazione\" po on po.\"ente_id\" = e.\"id\"",
					" join \"presentazione_istanza\".\"pratica\" p on p.\"ente_delegato\" = po.\"id\"::text",
					" join \"common\".\"ente_attribute\" ea on ea.\"ente_id\" = e.\"id\"",
					" join \"common\".\"applicazione\" app on ea.\"applicazione_id\" = app.\"id\"",
					" where p.\"id\" = ? and app.\"codice\" = ?");
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(UUID.fromString(idPratica));
			parameters.add(this.codiceApplicazioneAutorita);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return super.queryForObjectTxRead(sql, this.enteRowMapper, parameters);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#comitato(java.lang.String)
	 */
	
	public boolean comitato(final String id) {
		final StopWatch sw = LogUtil.startLog("comitato ", id);
		this.logger.info("Start comitato {}", id);
		try {
//			final QIstruttoriaCds PraticaCdsDTO = QIstruttoriaCds.PraticaCdsDTO;
//			return new JPAQuery<>(this.entityManager).from(PraticaCdsDTO).where(PraticaCdsDTO.id.eq(id))
//					.select(PraticaCdsDTO.comitato).fetchOne();
			return false;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 21 mar 2022
	 * @see it.eng.tz.aet.istruttoria.scheduler.service.orm.IConferenzaDeiServiziOrmService#idCdsNoComitato(java.lang.String)
	 */
	
	public List<Long> idCdsNoComitato(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("idCdsNoComitato ", idPratica);
		this.logger.info("Start idCdsNoComitato {}", idPratica);
		try {
//			final QIstruttoriaCds PraticaCdsDTO = QIstruttoriaCds.PraticaCdsDTO;
//
//			return new JPAQuery<>(this.entityManager)
//					.from(PraticaCdsDTO).where(PraticaCdsDTO.istruttoria.id.eq(idPratica)
//							.and(PraticaCdsDTO.idCds.isNotNull()).and(PraticaCdsDTO.comitato.ne(true)))
//					.select(PraticaCdsDTO.idCds).fetch();
			String sql = StringUtil.concateneString(
					"select cds.\"id_cds\" from \"presentazione_istanza\".\"pratica_cds\" cds",
					" where cds.\"id_pratica\" = :idPratica");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idPratica", idPratica);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.queryForList(sql, parameters, Long.class);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public List<PraticaCdsDTO> select() {
		// TODO
		return null;
	}

	@Override
	public long count() {
		// TODO
		return 0;
	}

	@Override
	public PraticaCdsDTO find(PraticaCdsDTO pk) {
		// TODO
		return null;
	}

	@Override
	public long insert(PraticaCdsDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public int update(PraticaCdsDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public int delete(PraticaCdsDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public PaginatedList<PraticaCdsDTO> search(PraticaCdsSearch bean) {
		// TODO
		return null;
	}
	
	/**
	 * id delle cds per pratica e stati
	 * (Compilazione,Bozza,Valutazione,Chiusa,Archiviata)
	 * 
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param statiAmmessi
	 * @return
	 */
	public List<Long> idCdsByPraticaEStati(final String idPratica, List<StatoConferenza> statiAmmessi) {
		String sql = StringUtil.concateneString("select cds.\"id_cds\" ",
				" from \"presentazione_istanza\".\"pratica_cds\" cds",
				" join \"presentazione_istanza\".\"v_cds\" v_cds ", " on cds.id_cds=v_cds.id ",
				" where cds.\"id_pratica\"::text = :idPratica and cds.\"id_cds\" is not null ",
				" AND v_cds.\"stato\" in (:statiAmmessi) ");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idPratica", idPratica);
		parameters.put("statiAmmessi", statiAmmessi.stream().map(el -> el.name()).collect(Collectors.toList()));
		logger.trace("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
		List<Long> retList = namedJdbcTemplateRead.queryForList(sql, parameters, Long.class);
		return retList;
	}

	/**
	 * marco come inviato allo scheduler l'allegato in questione.
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @param allegatoId
	 * @param idCds
	 * @param username
	 * @return
	 */
	public long insertCdsAllegatiInviati(UUID praticaId,UUID allegatoId,Long idCds,String username){
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"cds_allegati_inviati\"")
                                     .append("(\"id_pratica\"")
                                     .append(",\"id_cds\"")
                                     .append(",\"id_allegato\"")
                                     .append(",\"user_create\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(idCds);
        parameters.add(allegatoId);
        parameters.add(username);
        return super.update(sql, parameters);
    }

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @param allegatoId
	 * @param idCds
	 * @return true se ci sono record
	 */
	public boolean hasCdsAllegatiInviati(UUID praticaId, UUID allegatoId, Long idCds) {
		String sql = StringUtil.concateneString("select ca.\"id_allegato\" ",
				" from \"presentazione_istanza\".\"cds_allegati_inviati\" ca",
				" where ca.\"id_pratica\" = :praticaId ",
				" and ca.\"id_allegato\" = :allegatoId ",
				" and ca.\"id_cds\" = :idCds ");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("praticaId", praticaId);
		parameters.put("allegatoId", allegatoId);
		parameters.put("idCds", idCds);
		logger.trace("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
		List<UUID> retList = namedJdbcTemplateRead.queryForList(sql, parameters, UUID.class);
		if(ListUtil.isNotEmpty(retList)) {
			return true;
		}
		return false;
	}	

}
