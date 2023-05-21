package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.security.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.util.uuid.UuidUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteAttributeRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PraticaCdsListSettingsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.pratica_cds
 */
@Repository
public class PraticaCdsRepository extends GenericCrudDao<PraticaCdsDTO, PraticaCdsSearch> {

	private final PraticaCdsRowMapper rowMapper = new PraticaCdsRowMapper();
	private final CdsDtoRowMapper cdsDtoRowMapper = new CdsDtoRowMapper();
	private final PraticaCdsListSettingsRowMapper cdsListDtoRowMapper = new PraticaCdsListSettingsRowMapper();
	private final PlainStringValueLabelRowMapper valueLabelRowMapper = new PlainStringValueLabelRowMapper();
	private final ResourceBundleMessageSource bundle = new ResourceBundleMessageSource();

	@Autowired
	private IConfigurazioneService configurazioneSvc;
	
	@Autowired
	private PraticaCdsPartecipantiRepository partecipantiRepo;
	
	@Autowired
	private UtenteAttributeRepository utenteAttributeRepo;
	
	@Autowired
	private UtenteRepository utenteRepository;

	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"id_pratica\"").append(",\"tipo\"")
			.append(",\"attivita\"").append(",\"azione\"").append(",\"termine_richiesta_integrazione\"")
			.append(",\"termine_pareri\"").append(",\"prima_sessione\"").append(",\"data_termine\"")
			.append(",\"comune_pertinenza\"").append(",\"provincia_pertinenza\"").append(",\"indirizzo_pertinenza\"")
			.append(",\"modalita_incontro\"").append(",\"link\"").append(",\"comune\"").append(",\"provincia\"")
			.append(",\"cap\"").append(",\"indirizzo\"").append(",\"orario\"").append(",\"date_create\"")
			.append(",\"user_create\"").append(",\"date_update\"").append(",\"user_update\"").append(",\"completed\"")
			.append(",\"id_cds\"").append(",\"comitato\"").append(",\"username_creatore\"").append(",\"nome_creatore\"")
			.append(",\"cognome_creatore\"").append(",\"mail_creatore\"").append(",\"telefono_creatore\"")
			.append(",\"codice_fiscale_creatore\"").append(",\"username_responsabile\"")
			.append(",\"nome_responsabile\"").append(",\"cognome_responsabile\"")
			.append(",\"codice_fiscale_responsabile\"").append(",\"mail_responsabile\"")
			.append(",\"telefono_responsabile\"").append(" from \"presentazione_istanza\".\"pratica_cds\" cds")
			.toString();

	@Override
	public List<PraticaCdsDTO> select() {
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"pratica_cds\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public PraticaCdsDTO find(PraticaCdsDTO pk) {
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<PraticaCdsDTO> search(PraticaCdsSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getId())) {
			sql.append(sep).append("lower(\"id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getId()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdPratica())) {
			sql.append(sep).append("lower(\"id_pratica\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdPratica()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTipo())) {
			sql.append(sep).append("lower(\"tipo\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTipo()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getAttivita())) {
			sql.append(sep).append("lower(\"attivita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getAttivita()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getAzione())) {
			sql.append(sep).append("lower(\"azione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getAzione()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTermineRichiestaIntegrazione())) {
			sql.append(sep).append("lower(\"termine_richiesta_integrazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTermineRichiestaIntegrazione()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTerminePareri())) {
			sql.append(sep).append("lower(\"termine_pareri\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTerminePareri()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getPrimaSessione())) {
			sql.append(sep).append("lower(\"prima_sessione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPrimaSessione()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataTermine())) {
			sql.append(sep).append("lower(\"data_termine\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataTermine()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComunePertinenza())) {
			sql.append(sep).append("lower(\"comune_pertinenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComunePertinenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvinciaPertinenza())) {
			sql.append(sep).append("lower(\"provincia_pertinenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvinciaPertinenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIndirizzoPertinenza())) {
			sql.append(sep).append("lower(\"indirizzo_pertinenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIndirizzoPertinenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getModalitaIncontro())) {
			sql.append(sep).append("lower(\"modalita_incontro\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getModalitaIncontro()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getLink())) {
			sql.append(sep).append("lower(\"link\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getLink()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComune())) {
			sql.append(sep).append("lower(\"comune\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComune()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvincia())) {
			sql.append(sep).append("lower(\"provincia\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvincia()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCap())) {
			sql.append(sep).append("lower(\"cap\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCap()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIndirizzo())) {
			sql.append(sep).append("lower(\"indirizzo\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIndirizzo()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getOrario())) {
			sql.append(sep).append("lower(\"orario\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getOrario()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDateCreate())) {
			sql.append(sep).append("lower(\"date_create\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDateCreate()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUserCreate())) {
			sql.append(sep).append("lower(\"user_create\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUserCreate()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDateUpdate())) {
			sql.append(sep).append("lower(\"date_update\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDateUpdate()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUserUpdate())) {
			sql.append(sep).append("lower(\"user_update\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUserUpdate()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCompleted())) {
			sql.append(sep).append("lower(\"completed\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCompleted()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdCds())) {
			sql.append(sep).append("lower(\"id_cds\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdCds()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComitato())) {
			sql.append(sep).append("lower(\"comitato\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComitato()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUsernameCreatore())) {
			sql.append(sep).append("lower(\"username_creatore\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUsernameCreatore()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNomeCreatore())) {
			sql.append(sep).append("lower(\"nome_creatore\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNomeCreatore()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCognomeCreatore())) {
			sql.append(sep).append("lower(\"cognome_creatore\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCognomeCreatore()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMailCreatore())) {
			sql.append(sep).append("lower(\"mail_creatore\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMailCreatore()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTelefonoCreatore())) {
			sql.append(sep).append("lower(\"telefono_creatore\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTelefonoCreatore()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodiceFiscaleCreatore())) {
			sql.append(sep).append("lower(\"codice_fiscale_creatore\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCodiceFiscaleCreatore()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUsernameResponsabile())) {
			sql.append(sep).append("lower(\"username_responsabile\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUsernameResponsabile()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNomeResponsabile())) {
			sql.append(sep).append("lower(\"nome_responsabile\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNomeResponsabile()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCognomeResponsabile())) {
			sql.append(sep).append("lower(\"cognome_responsabile\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCognomeResponsabile()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodiceFiscaleResponsabile())) {
			sql.append(sep).append("lower(\"codice_fiscale_responsabile\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCodiceFiscaleResponsabile()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMailResponsabile())) {
			sql.append(sep).append("lower(\"mail_responsabile\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMailResponsabile()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTelefonoResponsabile())) {
			sql.append(sep).append("lower(\"telefono_responsabile\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTelefonoResponsabile()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy())) {
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy()) {
			case "id":
				sql.append(" order by \"id\" ").append(sortType);
				break;
			case "idPratica":
				sql.append(" order by \"id_pratica\" ").append(sortType);
				break;
			case "tipo":
				sql.append(" order by \"tipo\" ").append(sortType);
				break;
			case "attivita":
				sql.append(" order by \"attivita\" ").append(sortType);
				break;
			case "azione":
				sql.append(" order by \"azione\" ").append(sortType);
				break;
			case "termineRichiestaIntegrazione":
				sql.append(" order by \"termine_richiesta_integrazione\" ").append(sortType);
				break;
			case "terminePareri":
				sql.append(" order by \"termine_pareri\" ").append(sortType);
				break;
			case "primaSessione":
				sql.append(" order by \"prima_sessione\" ").append(sortType);
				break;
			case "dataTermine":
				sql.append(" order by \"data_termine\" ").append(sortType);
				break;
			case "comunePertinenza":
				sql.append(" order by \"comune_pertinenza\" ").append(sortType);
				break;
			case "provinciaPertinenza":
				sql.append(" order by \"provincia_pertinenza\" ").append(sortType);
				break;
			case "indirizzoPertinenza":
				sql.append(" order by \"indirizzo_pertinenza\" ").append(sortType);
				break;
			case "modalitaIncontro":
				sql.append(" order by \"modalita_incontro\" ").append(sortType);
				break;
			case "link":
				sql.append(" order by \"link\" ").append(sortType);
				break;
			case "comune":
				sql.append(" order by \"comune\" ").append(sortType);
				break;
			case "provincia":
				sql.append(" order by \"provincia\" ").append(sortType);
				break;
			case "cap":
				sql.append(" order by \"cap\" ").append(sortType);
				break;
			case "indirizzo":
				sql.append(" order by \"indirizzo\" ").append(sortType);
				break;
			case "orario":
				sql.append(" order by \"orario\" ").append(sortType);
				break;
			case "dateCreate":
				sql.append(" order by \"date_create\" ").append(sortType);
				break;
			case "userCreate":
				sql.append(" order by \"user_create\" ").append(sortType);
				break;
			case "dateUpdate":
				sql.append(" order by \"date_update\" ").append(sortType);
				break;
			case "userUpdate":
				sql.append(" order by \"user_update\" ").append(sortType);
				break;
			case "completed":
				sql.append(" order by \"completed\" ").append(sortType);
				break;
			case "idCds":
				sql.append(" order by \"id_cds\" ").append(sortType);
				break;
			case "comitato":
				sql.append(" order by \"comitato\" ").append(sortType);
				break;
			case "usernameCreatore":
				sql.append(" order by \"username_creatore\" ").append(sortType);
				break;
			case "nomeCreatore":
				sql.append(" order by \"nome_creatore\" ").append(sortType);
				break;
			case "cognomeCreatore":
				sql.append(" order by \"cognome_creatore\" ").append(sortType);
				break;
			case "mailCreatore":
				sql.append(" order by \"mail_creatore\" ").append(sortType);
				break;
			case "telefonoCreatore":
				sql.append(" order by \"telefono_creatore\" ").append(sortType);
				break;
			case "codiceFiscaleCreatore":
				sql.append(" order by \"codice_fiscale_creatore\" ").append(sortType);
				break;
			case "usernameResponsabile":
				sql.append(" order by \"username_responsabile\" ").append(sortType);
				break;
			case "nomeResponsabile":
				sql.append(" order by \"nome_responsabile\" ").append(sortType);
				break;
			case "cognomeResponsabile":
				sql.append(" order by \"cognome_responsabile\" ").append(sortType);
				break;
			case "codiceFiscaleResponsabile":
				sql.append(" order by \"codice_fiscale_responsabile\" ").append(sortType);
				break;
			case "mailResponsabile":
				sql.append(" order by \"mail_responsabile\" ").append(sortType);
				break;
			case "telefonoResponsabile":
				sql.append(" order by \"telefono_responsabile\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(PraticaCdsDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pratica_cds\"").append("(\"id\"")
				.append(",\"id_pratica\"").append(",\"tipo\"").append(",\"attivita\"").append(",\"azione\"")
				.append(",\"termine_richiesta_integrazione\"").append(",\"termine_pareri\"")
				.append(",\"prima_sessione\"").append(",\"data_termine\"").append(",\"comune_pertinenza\"")
				.append(",\"provincia_pertinenza\"").append(",\"indirizzo_pertinenza\"")
				.append(",\"modalita_incontro\"").append(",\"link\"").append(",\"comune\"").append(",\"provincia\"")
				.append(",\"cap\"").append(",\"indirizzo\"").append(",\"orario\"").append(",\"date_create\"")
				.append(",\"user_create\"").append(",\"date_update\"").append(",\"user_update\"")
				.append(",\"completed\"").append(",\"id_cds\"").append(",\"comitato\"").append(",\"username_creatore\"")
				.append(",\"nome_creatore\"").append(",\"cognome_creatore\"").append(",\"mail_creatore\"")
				.append(",\"telefono_creatore\"").append(",\"codice_fiscale_creatore\"")
				.append(",\"username_responsabile\"").append(",\"nome_responsabile\"")
				.append(",\"cognome_responsabile\"").append(",\"codice_fiscale_responsabile\"")
				.append(",\"mail_responsabile\"").append(",\"telefono_responsabile\"").append(") values ").append("(?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getTipo());
		parameters.add(entity.getAttivita());
		parameters.add(entity.getAzione());
		parameters.add(entity.getTermineRichiestaIntegrazione());
		parameters.add(entity.getTerminePareri());
		parameters.add(entity.getPrimaSessione());
		parameters.add(entity.getDataTermine());
		parameters.add(entity.getComunePertinenza());
		parameters.add(entity.getProvinciaPertinenza());
		parameters.add(entity.getIndirizzoPertinenza());
		parameters.add(entity.getModalitaIncontro());
		parameters.add(entity.getLink());
		parameters.add(entity.getComune());
		parameters.add(entity.getProvincia());
		parameters.add(entity.getCap());
		parameters.add(entity.getIndirizzo());
		parameters.add(entity.getOrario());
		parameters.add(entity.getDateCreate());
		parameters.add(entity.getUserCreate());
		parameters.add(entity.getDateUpdate());
		parameters.add(entity.getUserUpdate());
		parameters.add(entity.getCompleted());
		parameters.add(entity.getIdCds());
		parameters.add(entity.getComitato());
		parameters.add(entity.getUsernameCreatore());
		parameters.add(entity.getNomeCreatore());
		parameters.add(entity.getCognomeCreatore());
		parameters.add(entity.getMailCreatore());
		parameters.add(entity.getTelefonoCreatore());
		parameters.add(entity.getCodiceFiscaleCreatore());
		parameters.add(entity.getUsernameResponsabile());
		parameters.add(entity.getNomeResponsabile());
		parameters.add(entity.getCognomeResponsabile());
		parameters.add(entity.getCodiceFiscaleResponsabile());
		parameters.add(entity.getMailResponsabile());
		parameters.add(entity.getTelefonoResponsabile());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(PraticaCdsDTO entity) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_cds\"")
				.append(" set \"id_pratica\" = ?").append(", \"tipo\" = ?").append(", \"attivita\" = ?")
				.append(", \"azione\" = ?").append(", \"termine_richiesta_integrazione\" = ?")
				.append(", \"termine_pareri\" = ?").append(", \"prima_sessione\" = ?").append(", \"data_termine\" = ?")
				.append(", \"comune_pertinenza\" = ?").append(", \"provincia_pertinenza\" = ?")
				.append(", \"indirizzo_pertinenza\" = ?").append(", \"modalita_incontro\" = ?").append(", \"link\" = ?")
				.append(", \"comune\" = ?").append(", \"provincia\" = ?").append(", \"cap\" = ?")
				.append(", \"indirizzo\" = ?").append(", \"orario\" = ?").append(", \"date_create\" = ?")
				.append(", \"user_create\" = ?").append(", \"date_update\" = ?").append(", \"user_update\" = ?")
				.append(", \"completed\" = ?").append(", \"id_cds\" = ?").append(", \"comitato\" = ?")
				.append(", \"username_creatore\" = ?").append(", \"nome_creatore\" = ?")
				.append(", \"cognome_creatore\" = ?").append(", \"mail_creatore\" = ?")
				.append(", \"telefono_creatore\" = ?").append(", \"codice_fiscale_creatore\" = ?")
				.append(", \"username_responsabile\" = ?").append(", \"nome_responsabile\" = ?")
				.append(", \"cognome_responsabile\" = ?").append(", \"codice_fiscale_responsabile\" = ?")
				.append(", \"mail_responsabile\" = ?").append(", \"telefono_responsabile\" = ?")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getTipo());
		parameters.add(entity.getAttivita());
		parameters.add(entity.getAzione());
		parameters.add(entity.getTermineRichiestaIntegrazione());
		parameters.add(entity.getTerminePareri());
		parameters.add(entity.getPrimaSessione());
		parameters.add(entity.getDataTermine());
		parameters.add(entity.getComunePertinenza());
		parameters.add(entity.getProvinciaPertinenza());
		parameters.add(entity.getIndirizzoPertinenza());
		parameters.add(entity.getModalitaIncontro());
		parameters.add(entity.getLink());
		parameters.add(entity.getComune());
		parameters.add(entity.getProvincia());
		parameters.add(entity.getCap());
		parameters.add(entity.getIndirizzo());
		parameters.add(entity.getOrario());
		parameters.add(entity.getDateCreate());
		parameters.add(entity.getUserCreate());
		parameters.add(entity.getDateUpdate());
		parameters.add(entity.getUserUpdate());
		parameters.add(entity.getCompleted());
		parameters.add(entity.getIdCds());
		parameters.add(entity.getComitato());
		parameters.add(entity.getUsernameCreatore());
		parameters.add(entity.getNomeCreatore());
		parameters.add(entity.getCognomeCreatore());
		parameters.add(entity.getMailCreatore());
		parameters.add(entity.getTelefonoCreatore());
		parameters.add(entity.getCodiceFiscaleCreatore());
		parameters.add(entity.getUsernameResponsabile());
		parameters.add(entity.getNomeResponsabile());
		parameters.add(entity.getCognomeResponsabile());
		parameters.add(entity.getCodiceFiscaleResponsabile());
		parameters.add(entity.getMailResponsabile());
		parameters.add(entity.getTelefonoResponsabile());
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(PraticaCdsDTO entity) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pratica_cds\"")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	@PostConstruct
	public void init() {
		this.bundle.setBasename("bundle/cds");
		this.bundle.setDefaultEncoding(StringUtil.CHARSET_STRING);
		this.bundle.setFallbackToSystemLocale(false);
		this.bundle.setUseCodeAsDefaultMessage(true);
	}

	public PraticaCdsDTO getSettings(final String idPratica, final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("getSettings ", idPratica, " ", id);
		this.logger.info("Start getSettings {} {}", idPratica, id);
		try {
			String sql = StringUtil.concateneString("select cds.\"id\" ,\"id_pratica\", tipo.\"value\" as tipo",
					", attivita.\"value\" as attivita, azione.\"value\" as azione, \"termine_richiesta_integrazione\"",
					",\"termine_pareri\",\"prima_sessione\",\"data_termine\"",
					",comunePertinenza.\"nome\" as comune_pertinenza, provinciaPertinenza.\"nome\" as provincia_pertinenza,\"indirizzo_pertinenza\"",
					",\"modalita_incontro\",\"link\", comune.\"nome\" as comune, provincia.\"nome\" as provincia",
					",\"cap\",\"indirizzo\",\"orario\",cds.\"date_create\"",
					",cds.\"user_create\",cds.\"date_update\",cds.\"user_update\",\"completed\"",
					",\"id_cds\",\"comitato\",\"username_creatore\",\"nome_creatore\"",
					",\"cognome_creatore\",\"mail_creatore\",\"telefono_creatore\"",
					",\"codice_fiscale_creatore\",\"username_responsabile\"",
					",\"nome_responsabile\",\"cognome_responsabile\"",
					",\"codice_fiscale_responsabile\",\"mail_responsabile\"",
					",\"telefono_responsabile\" from \"presentazione_istanza\".\"pratica_cds\" cds",
					" join \"presentazione_istanza\".\"pratica\" p on",
					" p.\"id\" = cds.\"id_pratica\"", 
					" left join \"anagrafica\".\"comune\" comune on",
					" cds.\"comune\" = comune.\"cod_istat\"", 
					" left join \"anagrafica\".\"provincia\" provincia on",
					" cds.\"provincia\" = provincia.\"sigla\"", 
					" left join \"anagrafica\".\"comune\" comunePertinenza on",
					" cds.\"comune_pertinenza\" = comunePertinenza.\"cod_istat\"",
					" left join \"anagrafica\".\"provincia\" provinciaPertinenza on",
					" cds.\"provincia_pertinenza\" = provinciaPertinenza.\"sigla\"",
					" left join \"presentazione_istanza\".\"v_cds_attivita\" attivita on",
					" cds.\"attivita\" = attivita.\"value\"",
					" left join \"presentazione_istanza\".\"v_cds_azione\" azione on",
					" cds.\"azione\" = azione.\"value\"",
					" left join \"presentazione_istanza\".\"v_cds_modalita\" modalita on",
					" cds.\"modalita_incontro\" = modalita.\"value\"",
					" left join \"presentazione_istanza\".\"v_cds_tipo\" tipo on", " cds.\"tipo\" = tipo.\"value\"",
					" where cds.\"id\" = ? ", " and cds.\"id_pratica\" = ?;");
	        final List<Object> parameters = new ArrayList<Object>();
	        parameters.add(id);
	        parameters.add(UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			final PraticaCdsDTO response = super.queryForObjectTxRead(sql, this.rowMapper, parameters);
			if(response != null)
				response.setPartecipanti(this.listPartecipanti(id));
			return response;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

//	public boolean hasCds(final String idPratica) throws Exception {
//		final StopWatch sw = LogUtil.startLog("hasCds ", idPratica);
//		this.logger.info("Start hasCds {}", idPratica);
//		try {
//			final QPratica Pratica = QPratica.Pratica;
//			final QPratica pratica = QPratica.pratica;
//			final QTipoProcedimento tipoProcedimento = QTipoProcedimento.tipoProcedimento;
//			final QTipoProcedimentoConfigurazione tipoProcedimentoConfigurazione = QTipoProcedimentoConfigurazione.tipoProcedimentoConfigurazione;
//			
//			return super.from(tipoProcedimentoConfigurazione)
//					    .join(tipoProcedimento).on(tipoProcedimento.id.eq(tipoProcedimentoConfigurazione.tipoProcedimento.id).and(tipoProcedimentoConfigurazione.endDate.isNull()))
//					    .join(pratica).on(pratica.tipoProcedimentoBean.id.eq(tipoProcedimento.id))
//					    .join(Pratica).on(pratica.id.eq(Pratica.pratica.id))
//					    .where(Pratica.id.eq(idPratica))
//					    .select(tipoProcedimentoConfigurazione.comitato)
//					    .fetchOne();
//	        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
//		}finally {
//			this.logger.info(LogUtil.stopLog(sw));
//		}
//	}

	public void saveSettings(final String idPratica, final PraticaCdsDTO entity) throws Exception {
		final StopWatch sw = LogUtil.startLog("saveSettings ", idPratica);
		this.logger.info("Start saveSettings {}", idPratica);
		try {
	        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_cds\"")
                    .append(" set \"id_pratica\" = ?")
                    .append(", \"tipo\" = ?")
                    .append(", \"attivita\" = ?")
                    .append(", \"azione\" = ?")
                    .append(", \"termine_richiesta_integrazione\" = ?")
                    .append(", \"termine_pareri\" = ?")
                    .append(", \"prima_sessione\" = ?")
                    .append(", \"data_termine\" = ?")
                    .append(", \"comune_pertinenza\" = ?")
                    .append(", \"provincia_pertinenza\" = ?")
                    .append(", \"indirizzo_pertinenza\" = ?")
                    .append(", \"modalita_incontro\" = ?")
                    .append(", \"link\" = ?")
                    .append(", \"comune\" = ?")
                    .append(", \"provincia\" = ?")
                    .append(", \"cap\" = ?")
                    .append(", \"indirizzo\" = ?")
                    .append(", \"orario\" = ?")
                    .append(", \"date_create\" = current_timestamp")
                    .append(", \"user_create\" = ?")
                    .append(", \"date_update\" = ?")
                    .append(", \"user_update\" = ?")
                    .append(", \"completed\" = ?")
                    .append(", \"id_cds\" = ?")
                    .append(", \"comitato\" = ?")
                    .append(", \"username_responsabile\" = ?")
                    .append(", \"nome_responsabile\" = ?")
                    .append(", \"cognome_responsabile\" = ?")
                    .append(", \"codice_fiscale_responsabile\" = ?")
                    .append(", \"mail_responsabile\" = ?")
                    .append(", \"telefono_responsabile\" = ?")
                    .append(" where \"id\" = ?")
                    .toString();
	        final List<Object> parameters = new ArrayList<Object>();
	        parameters.add(UUID.fromString(idPratica));
	        parameters.add(entity.getTipo());
	        parameters.add(entity.getAttivita());
	        parameters.add(entity.getAzione());
	        parameters.add(entity.getTermineRichiestaIntegrazione());
	        parameters.add(entity.getTerminePareri());
	        parameters.add(entity.getPrimaSessione());
	        parameters.add(entity.getDataTermine());
	        parameters.add(entity.getComunePertinenza());
	        parameters.add(entity.getProvinciaPertinenza());
	        parameters.add(entity.getIndirizzoPertinenza());
	        parameters.add(entity.getModalitaIncontro());
	        parameters.add(entity.getLink());
	        parameters.add(entity.getComune());
	        parameters.add(entity.getProvincia());
	        parameters.add(entity.getCap());
	        parameters.add(entity.getIndirizzo());
	        parameters.add(entity.getOrario());
	        parameters.add(SecurityUtil.getUsername());
	        parameters.add(entity.getDateUpdate());
	        parameters.add(entity.getUserUpdate());
	        parameters.add(entity.getCompleted());
	        parameters.add(entity.getIdCds());
	        parameters.add(entity.getComitato());
	        parameters.add(entity.getUsernameResponsabile());
	        parameters.add(entity.getNomeResponsabile());
	        parameters.add(entity.getCognomeResponsabile());
	        parameters.add(entity.getCodiceFiscaleResponsabile());
	        parameters.add(entity.getMailResponsabile());
	        parameters.add(entity.getTelefonoResponsabile());
	        parameters.add(entity.getId());
	        super.update(sql, parameters);				
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public void avvia(final String idPratica, final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("avvia ", idPratica, " ", id);
		this.logger.info("Start avvia {} {}", idPratica, id);
		try {
	        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_cds\"")
                    .append(" set \"completed\" = true")
                    .append(",\"user_update\" = ?")
                    .append(",\"date_update\" = current_timestamp")
                    .append(" where \"id_pratica\" = ?")
                    .append(" and \"id\" = ?").toString();
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(SecurityUtil.getUsername());
			parameters.add(UUID.fromString(idPratica));
			parameters.add(id);
	        super.update(sql, parameters);				
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<PlainStringValueLabel> autocompleteProvince(final String query) {
		final StopWatch sw = LogUtil.startLog("autocompleteProvince ", query);
		this.logger.info("Start autocompleteProvince {}", query);
		try {
			String sql = StringUtil.concateneString(
					"select provincia.\"nome\" as label, provincia.\"sigla\" as value from \"anagrafica\".\"provincia\" provincia",
					" where lower(provincia.\"nome\") like lower('" + query + "%')",
					" order by provincia.\"nome\" ASC",
					" limit 25;");
			return namedJdbcTemplateRead.query(sql, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<PlainStringValueLabel> autocompleteComuni(final String siglaProvincia, final String query) {
		final StopWatch sw = LogUtil.startLog("autocompleteComuni ", query);
		this.logger.info("Start autocompleteComuni {}", query);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select comune.\"nome\" as label, comune.\"cod_istat\" as value from \"anagrafica\".\"comune\" comune",
					" join \"anagrafica\".\"provincia\" provincia on comune.\"id_provincia\" = provincia.\"id\"",
					" where lower(comune.\"nome\") like lower('" + query + "%')",
					" and provincia.\"sigla\" = :sigla",
					" order by comune.\"nome\" ASC",
					" limit 25;");
			parameters.put("sigla", siglaProvincia);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<PlainStringValueLabel> listaTipo(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("listaTipo ", idPratica);
		this.logger.info("Start listaTipo {}", idPratica);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select t.\"label\", t.\"value\" from \"presentazione_istanza\".\"v_cds_tipo\" t",
					" join \"presentazione_istanza\".\"tipo_cds_tipo\" cdsT on t.\"value\" = cdsT.\"tipo\"",
					" join \"presentazione_istanza\".\"pratica\" p on p.\"tipo_procedimento\" = cdsT.\"id_tipo_procedimento\"",
					" where p.\"id\" = :idPratica ",
					" and p.\"ente_delegato\" = cdsT.\"id_organizzazione\"::text",
					" and cdsT.\"start_date\" <= current_timestamp ",
					" and (cdsT.\"end_date\" >= current_timestamp ",
					" or cdsT.\"end_date\" is null)",
					" order by t.\"label\" ASC;");
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
//
//	private String tipoLabel(final String labelKey) {
//		return this.bundle.getMessage(labelKey, (Object[])null, Locale.getDefault());
//	}

	public List<PlainStringValueLabel> listaAttivita(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("listaAttivita ", idPratica);
		this.logger.info("Start listaAttivita {}", idPratica);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select a.\"label\", a.\"value\" from \"presentazione_istanza\".\"v_cds_attivita\" a",
					" join \"presentazione_istanza\".\"tipo_cds_attivita\" cdsA on a.\"value\" = cdsA.\"attivita\"",
					" join \"presentazione_istanza\".\"pratica\" p on p.\"tipo_procedimento\" = cdsA.\"id_tipo_procedimento\"",
					" where p.\"id\" = :idPratica ",
					" and p.\"ente_delegato\" = cdsA.\"id_organizzazione\"::text",
					" and cdsA.\"start_date\" <= current_timestamp ",
					" and (cdsA.\"end_date\" >= current_timestamp ",
					" or cdsA.\"end_date\" is null)",
					" order by a.\"label\" ASC;");
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<PlainStringValueLabel> listaAzione(final String attivita, final String idPratica) {
		final StopWatch sw = LogUtil.startLog("listaAzione ", attivita, " ", idPratica);
		this.logger.info("Start listaAzione {} {}", attivita, idPratica);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select a.\"label\", a.\"value\" from \"presentazione_istanza\".\"v_cds_azione\" a",
					" join \"presentazione_istanza\".\"tipo_cds_azione\" cdsA on a.\"value\" = cdsA.\"azione\"",
					" join \"presentazione_istanza\".\"pratica\" p on p.\"tipo_procedimento\" = cdsA.\"id_tipo_procedimento\"",
					" where p.\"id\" = :idPratica ",
					" and cdsA.\"start_date\" <= current_timestamp ",
					" and p.\"ente_delegato\" = cdsA.\"id_organizzazione\"::text",
					" and (cdsA.\"end_date\" >= current_timestamp ",
					" or cdsA.\"end_date\" is null)",
					" order by a.\"label\" ASC;");
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<PlainStringValueLabel> listaModalita() {
		final StopWatch sw = LogUtil.startLog("listaModalita");
		this.logger.info("Start listaModalita");
		try {
			String sql = StringUtil.concateneString(
					"select m.\"label\", m.\"value\" from \"presentazione_istanza\".\"v_cds_modalita\" m",
					" order by m.\"label\" ASC;");
			return namedJdbcTemplateRead.query(sql, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	public List<PlainStringValueLabel> listaResponsabili(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("listaResponsabili ", idPratica);
		this.logger.info("Start listaResponsabili {}", idPratica);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select rup.\"denominazione\" as label, rup.\"username\" as value from \"presentazione_istanza\".\"rup\" rup",
					" join \"presentazione_istanza\".\"pratica\" p on p.\"ente_delegato\" = rup.\"id_organizzazione\"::text",
					" where p.\"id\" = :idPratica ",
					" and (rup.\"data_scadenza\" >= current_date ",
					" or rup.\"data_scadenza\" is null)",
					" order by rup.\"denominazione\" ASC;");
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public String insertSettings(final String idPratica, final String modalita, final boolean comitato)
			throws Exception {
		final StopWatch sw = LogUtil.startLog("insertSettings ", idPratica);
		this.logger.info("Start insertSettings {}", idPratica);
		try {	
			final PraticaCdsDTO entity = new PraticaCdsDTO();
			entity.setId(UuidUtil.newValue());
			entity.setIdPratica(UUID.fromString(idPratica));
			entity.setUserCreate(SecurityUtil.getUsername());
			entity.setUsernameCreatore(SecurityUtil.getUsername());
			final UtenteDTO user = this.utenteRepository.findByUsername(SecurityUtil.getUsername());
			if(StringUtil.isBlank(user.getNome()))
				throw new CustomValidationException("L'utente creatore non possiede un nome registrato");
			entity.setNomeCreatore(user.getNome());
			if(StringUtil.isBlank(user.getCognome()))
				throw new CustomValidationException("L'utente creatore non possiede un cognome registrato");
			entity.setCognomeCreatore(user.getCognome());
			if(StringUtil.isBlank(user.getCf()))
				throw new CustomValidationException("L'utente creatore non possiede un codice fiscale registrato");
			entity.setCodiceFiscaleCreatore(user.getCf());
			final UtenteAttributeDTO attributi = this.utenteAttributeRepo.findByUsername(SecurityUtil.getUsername());
			if(attributi != null) {
				if(StringUtil.isNotBlank(attributi.getPec()))
					entity.setMailCreatore(attributi.getPec());
				if(StringUtil.isNotBlank(attributi.getMail()))
					entity.setMailCreatore(attributi.getMail());
				if(StringUtil.isNotBlank(attributi.getPhoneNumber()))
					entity.setTelefonoCreatore(attributi.getPhoneNumber());
			}
			entity.setDateCreate(new Timestamp(System.currentTimeMillis()));
			entity.setCompleted(false);
			entity.setModalitaIncontro(modalita);
			entity.setComitato(comitato);
			if (comitato) {
				final Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, 2999);
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DAY_OF_MONTH, 31);
				final java.sql.Date timestamp = new java.sql.Date(calendar.getTimeInMillis());
				entity.setPrimaSessione(timestamp);
				entity.setDataTermine(timestamp);
				entity.setTermineRichiestaIntegrazione(timestamp);
				entity.setTerminePareri(timestamp);
			}
			this.insert(entity);
			return entity.getId();
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public ResourceBundleMessageSource getMessage() {
		return this.bundle;
	}
//	/**
//	 * ordina lista select
//	 * @author Antonio La Gatta
//	 * @date 1 dic 2021
//	 * @param list
//	 */
//	private void orderSelectList(final List<PlainStringValueLabel> list) {
//		Collections.sort(list);
//	}

	public List<CdsDto> infoCds(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("infoCds ", idPratica);
		this.logger.info("Start infoCds {}", idPratica);
		try {
//			final QPraticaCds PraticaCds = QPraticaCds.PraticaCds;
//			final QVCds cds = QVCds.vCds;
//
//			return super.from(cds).join(PraticaCds).on(PraticaCds.idCds.eq(cds.id))
//					.where(PraticaCds.Pratica.id.eq(idPratica))
//					.select(Projections.fields(CdsDto.class, cds.id, cds.riferimentoIstanza, cds.stato)).fetch();
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select cds.\"id\", cds.\"riferimento_istanza\", cds.\"stato\" from \"presentazione_istanza\".\"v_cds\" cds",
					" join \"presentazione_istanza\".\"pratica_cds\" pcds on cds.\"id\" = pcds.\"id_cds\"",
					" where p.\"id\" = :idPratica;");
			parameters.put("idPratica", idPratica);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, cdsDtoRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public CdsDto infoCds(final String idPratica, final String id) {
		final StopWatch sw = LogUtil.startLog("infoCds ", idPratica);
		this.logger.info("Start infoCds {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select cds.\"id\", cds.\"riferimento_istanza\", cds.\"stato\" from \"presentazione_istanza\".\"v_cds\" cds",
					" join \"presentazione_istanza\".\"pratica_cds\" pcds on cds.\"id\" = pcds.\"id_cds\"",
					" where pcds.\"id_pratica\" = ?",
					" and pcds.\"id\" = ?;");
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(UUID.fromString(idPratica));
			parameters.add(id);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			List<CdsDto> response = super.queryForList(sql, cdsDtoRowMapper, parameters);
			if(response != null && response.size() > 0) {
				return response.get(0);
			}
			return null;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

//	public PraticaCdsDTO getSettingsComitato(final String idPratica) throws Exception {
//		final StopWatch sw = LogUtil.startLog("getSettingsComitato ", idPratica);
//		this.logger.info("Start getSettingsComitato {}", idPratica);
//		try {
//			final QPraticaCds cds = QPraticaCds.Prat	icaCds;
//			final QPratica Pratica = QPratica.Pratica;
//			final QComune comune = new QComune("comune");
//			final QComune comunePertinenza = new QComune("comuneP");
//			final QProvincia provincia = new QProvincia("provincia");
//			final QProvincia provinciaPertinenza = new QProvincia("provinciaP");
//			final QVCdsAttivita attivita = QVCdsAttivita.vCdsAttivita;
//			final QVCdsAzione azione = QVCdsAzione.vCdsAzione;
//			final QVCdsModalita modalita = QVCdsModalita.vCdsModalita;
//			final QVCdsTipo tipo = QVCdsTipo.vCdsTipo;
//
//			final List<PraticaCdsDTO> list = super.from(cds).join(Pratica).on(Pratica.id.eq(cds.Pratica.id))
//					.leftJoin(comune).on(comune.codIstat.eq(cds.comune)).leftJoin(provincia)
//					.on(provincia.sigla.eq(cds.provincia)).leftJoin(comunePertinenza)
//					.on(comunePertinenza.codIstat.eq(cds.comunePertinenza)).leftJoin(provinciaPertinenza)
//					.on(provinciaPertinenza.sigla.eq(cds.provinciaPertinenza)).leftJoin(attivita)
//					.on(cds.attivita.eq(attivita.value)).leftJoin(azione).on(cds.azione.eq(azione.value))
//					.leftJoin(modalita).on(cds.modalitaIncontro.eq(modalita.value)).leftJoin(tipo)
//					.on(cds.tipo.eq(tipo.value)).where(Pratica.id.eq(idPratica).and(cds.comitato.eq(true)))
//					.select(Projections.fields(PraticaCdsDTO.class, cds.id, cds.tipo, tipo.label.as("tipoLabel"),
//							cds.attivita, attivita.label.as("attivitaLabel"), cds.azione,
//							azione.label.as("azioneLabel"), cds.comunePertinenza,
//							comunePertinenza.nome.as("comunePertinenzaLabel"), cds.provinciaPertinenza,
//							provinciaPertinenza.nome.as("provinciaPertinenzaLabel"), cds.indirizzoPertinenza,
//							cds.modalitaIncontro, modalita.label.as("modalitaIncontroLabel"), cds.link, cds.comune,
//							comune.nome.as("comuneLabel"), cds.provincia, provincia.nome.as("provinciaLabel"), cds.cap,
//							cds.indirizzo, cds.orario, cds.completed, cds.idCds, cds.usernameCreatore,
//							cds.nomeCreatore.coalesce("").asString().concat(" ")
//									.concat(cds.cognomeCreatore.coalesce("")).trim().as("usernameCreatoreNominativo")))
//					.fetch();
//			if (ListUtil.isEmpty(list))
//				return null;
//			return list.get(0);
//		} finally {
//			this.logger.info(LogUtil.stopLog(sw));
//		}
//	}
//
	
	public boolean hasCds(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("hasCdsBozza ", idPratica);
		this.logger.info("Start hasCdsBozza {}", idPratica);
		try {
			final List<Object> parameters = new ArrayList<Object>();
			String sql = StringUtil.concateneString(
					"select count(*) from \"presentazione_istanza\".\"pratica_cds\" cds",
					" where cds.\"id_pratica\" = ? and cds.\"id_cds\" is not null");
			parameters.add(UUID.fromString(idPratica));
			Long rows = super.queryForObject(sql, Long.class, parameters);
			return rows > 0;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}	
	
	public boolean hasCdsBozza(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("hasCdsBozza ", idPratica);
		this.logger.info("Start hasCdsBozza {}", idPratica);
		try {
			String sql = StringUtil.concateneString(
					"select count(*) from \"presentazione_istanza\".\"pratica_cds\" cds",
					" where cds.\"id_pratica\" = ?");
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(UUID.fromString(idPratica));
			Long rows = super.queryForObject(sql, Long.class, parameters);
			return rows > 0;
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public List<PraticaCdsListSettingsDto> listSettings(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("listSettings ", idPratica);
		this.logger.info("Start listSettings {}", idPratica);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select cds.\"id\", cds.\"id_cds\", tipo.\"label\" as tipo, attivita.\"label\" as attivita, azione.\"label\" as azione, cds.\"completed\"",
					" from \"presentazione_istanza\".\"pratica_cds\" cds",
					" join \"presentazione_istanza\".\"pratica\" p on cds.\"id_pratica\" = p.\"id\"",
					" left join \"presentazione_istanza\".\"v_cds_tipo\" tipo on cds.\"tipo\" = tipo.\"value\"",
					" left join \"presentazione_istanza\".\"v_cds_azione\" azione on cds.\"azione\" = azione.\"value\"",
					" left join \"presentazione_istanza\".\"v_cds_attivita\" attivita on cds.\"attivita\" = attivita.\"value\"",
					" where p.\"id\" = :idPratica;");
			parameters.put("idPratica", UUID.fromString(idPratica));
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, cdsListDtoRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	public List<PlainStringValueLabel> listPartecipanti(final String id) {
		final StopWatch sw = LogUtil.startLog("listPartecipanti ", id);
		this.logger.info("Start listPartecipanti {}", id);
		try {
			final Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString(
					"select p.\"username\" as value, p.\"denominazione\" as label",
					" from \"presentazione_istanza\".\"pratica_cds\" cds",
					" join \"presentazione_istanza\".\"pratica_cds_partecipanti\" p on cds.\"id\" = p.\"id\"",
					" where p.\"id\" = :id;");
			parameters.put("id", id);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			return namedJdbcTemplateRead.query(sql, parameters, valueLabelRowMapper);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	public void insertPartecipanti(final String id, final List<PlainStringValueLabel> partecipanti)
			throws Exception, CustomValidationException {
		final StopWatch sw = LogUtil.startLog("insertPartecipanti ", id);
		this.logger.info("Start insertPartecipanti {}", id);
		try {
			if(partecipanti != null && partecipanti.size() > 0) {
				this.partecipantiRepo.delete(id);
				for(PlainStringValueLabel partecipante : partecipanti){
					final UtenteDTO user = this.utenteRepository.findByUsername(partecipante.getValue());
					if(StringUtil.isBlank(user.getNome()))
						throw new CustomValidationException("Un partecipante non possiede un nome registrato");
					if(StringUtil.isBlank(user.getCognome()))
						throw new CustomValidationException("Un partecipante non possiede un cognome registrato");
					if(StringUtil.isBlank(user.getCf()))
						throw new CustomValidationException("Un partecipante non possiede un codice fiscale registrato");
					final UtenteAttributeDTO attributi = this.utenteAttributeRepo.findByUsername(partecipante.getValue());
					if(attributi == null) {
						throw new CustomValidationException();
					}
					final PraticaCdsPartecipantiDTO entity = new PraticaCdsPartecipantiDTO();
					entity.setId(id);
					entity.setUsername(partecipante.getValue());
					entity.setDenominazione(partecipante.getDescription());
					this.partecipantiRepo.insert(entity);
				};
			}
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	public void deleteSettings(final String idPratica, final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("deleteSettings ", idPratica, " ", id);
		this.logger.info("Start deleteSettings {} {}", idPratica, id);
		try {
//			final QPraticaCds cds = QPraticaCds.PraticaCds;
//			final long rows = super.delete(cds)
//					.where(cds.id.eq(id).and(cds.Pratica.id.eq(idPratica)).and(cds.completed.eq(false))).execute();
//			this.logger.info("Righe eliminate {}", rows);
			String sql = StringUtil.concateneString(
					" delete from \"presentazione_istanza\".\"pratica_cds\" cds",
					" where cds.\"id\" = :id",
					" and cds.\"id_pratica\" = :idPratica",
					" where cds.\"completed\" = false;");
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("id", id);
			parameters.put("idPratica", idPratica);
			logger.info("SQL-> " + sql.toString() + " PARAMETERS-> " + parameters);
			super.update(sql, parameters);
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}

	}

}
