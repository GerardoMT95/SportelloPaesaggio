package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.IpaEnteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoAziendaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ReferentiRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReferentiSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.registroimprese.bean.RegistroImpreseBean;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.referenti
 */
@Repository
public class ReferentiRepository extends GenericCrudDao<ReferentiDTO, ReferentiSearch> {

	private final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"tipo_referente\"")
			.append(",\"pratica_id\"").append(",\"cognome\"").append(",\"nome\"").append(",\"codice_fiscale\"")
			.append(",\"comune_nascita\"").append(",\"provincia_nascita\"").append(",\"nazionalita_nascita\"")
			.append(",\"comune_nascita_estero\"").append(",\"provincia_nascita_estero\"").append(",\"data_nascita\"")
			.append(",\"sesso\"").append(",\"indirizzo_residenza\"").append(",\"civico_residenza\"")
			.append(",\"cap_residenza\"").append(",\"comune_residenza\"").append(",\"provincia_residenza\"")
			.append(",\"nazionalita_residenza\"").append(",\"comune_residenza_estero\"")
			.append(",\"provincia_residenza_estero\"").append(",\"pec\"").append(",\"mail\"").append(",\"telefono\"")
			.append(",\"cellulare\"").append(",\"fax\"").append(",\"ditta\"").append(",\"ditta_ente\"")
			.append(",\"ditta_cf\"").append(",\"ditta_partita_iva\"").append(",\"ditta_qualita_di\"")
			.append(",\"ditta_qualita_altro\"").append(",\"tecnico_studio_indirizzo\"")
			.append(",\"tecnico_studio_civico\"").append(",\"tecnico_studio_cap\"").append(",\"tecnico_studio_comune\"")
			.append(",\"tecnico_studio_provincia\"").append(",\"tecnico_studio_nazionalita\"")
			.append(",\"tecnico_studio_comune_estero\"").append(",\"tecnico_studio_provincia_estero\"")
			.append(",\"tecnico_ord_collegio\"").append(",\"tecnico_collegio_sede\"")
			.append(",\"tecnico_collegio_n_iscr\"").append(",\"nazionalita_residenza_name\"")
			.append(",\"provincia_residenza_name\"").append(",\"comune_residenza_name\"")
			.append(",\"nazionalita_nascita_name\"").append(",\"provincia_nascita_name\"")
			.append(",\"comune_nascita_name\"").append(",\"tecnico_studio_nazionalita_name\"")
			.append(",\"tecnico_studio_provincia_name\"").append(",\"tecnico_studio_comune_name\"")
			.append(",\"titolarita_id\"").append(",\"specifica_titolarita\"")
			.append(",titolarita_esclusiva")
			.append(",codice_ipa")
			.append(",id_tipo_azienda")
			.append(",tipo_azienda, ditta_codice_uo")
			.append(" from \"presentazione_istanza\".\"referenti\"").toString();
	private final ReferentiRowMapper rowMapper = new ReferentiRowMapper();

	/**
	 * select all method
	 */
	@Override
	public List<ReferentiDTO> select() {
		final String sql= this.selectAll;
		/*= new StringBuilder("select").append(" \"id\"").append(",\"tipo_referente\"")
				.append(",\"pratica_id\"").append(",\"cognome\"").append(",\"nome\"").append(",\"codice_fiscale\"")
				.append(",\"comune_nascita\"").append(",\"provincia_nascita\"").append(",\"nazionalita_nascita\"")
				.append(",\"comune_nascita_estero\"").append(",\"provincia_nascita_estero\"")
				.append(",\"data_nascita\"").append(",\"sesso\"").append(",\"indirizzo_residenza\"")
				.append(",\"civico_residenza\"").append(",\"cap_residenza\"").append(",\"comune_residenza\"")
				.append(",\"provincia_residenza\"").append(",\"nazionalita_residenza\"")
				.append(",\"comune_residenza_estero\"").append(",\"provincia_residenza_estero\"").append(",\"pec\"")
				.append(",\"mail\"").append(",\"telefono\"").append(",\"cellulare\"").append(",\"fax\"")
				.append(",\"ditta\"").append(",\"ditta_ente\"").append(",\"ditta_cf\"").append(",\"ditta_partita_iva\"")
				.append(",\"ditta_qualita_di\"").append(",\"ditta_qualita_altro\"")
				.append(",\"tecnico_studio_indirizzo\"").append(",\"tecnico_studio_civico\"")
				.append(",\"tecnico_studio_cap\"").append(",\"tecnico_studio_comune\"")
				.append(",\"tecnico_studio_provincia\"").append(",\"tecnico_studio_nazionalita\"")
				.append(",\"tecnico_studio_comune_estero\"").append(",\"tecnico_studio_provincia_estero\"")
				.append(",\"tecnico_ord_collegio\"").append(",\"tecnico_collegio_sede\"")
				.append(",\"tecnico_collegio_n_iscr\"").append(",\"nazionalita_residenza_name\"")
				.append(",\"provincia_residenza_name\"").append(",\"comune_residenza_name\"")
				.append(",\"nazionalita_nascita_name\"").append(",\"provincia_nascita_name\"")
				.append(",\"comune_nascita_name\"").append(",\"tecnico_studio_nazionalita_name\"")
				.append(",\"tecnico_studio_provincia_name\"").append(",\"tecnico_studio_comune_name\"")
				.append(" from \"presentazione_istanza\".\"referenti\"").toString();*/
		return super.queryForListTxRead(sql, this.rowMapper);
	}
	
	private String generateWhereClause(final String baseSql, final List<Object> parameters, final ReferentiSearch search)
	{
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(baseSql);
		if (StringUtil.isNotEmpty(search.getId())) {
			sql.append(sep).append("lower(\"id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTipoReferente())) {
			sql.append(sep).append("lower(\"tipo_referente\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTipoReferente()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getPraticaId())) {
			sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPraticaId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCognome())) {
			sql.append(sep).append("lower(\"cognome\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCognome()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNome())) {
			sql.append(sep).append("lower(\"nome\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNome()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodiceFiscale())) {
			sql.append(sep).append("\"codice_fiscale\" = ?");
			parameters.add(search.getCodiceFiscale());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneNascita())) {
			sql.append(sep).append("lower(\"comune_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneNascita()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvinciaNascita())) {
			sql.append(sep).append("lower(\"provincia_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvinciaNascita()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNazionalitaNascita())) {
			sql.append(sep).append("lower(\"nazionalita_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNazionalitaNascita()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneNascitaEstero())) {
			sql.append(sep).append("lower(\"comune_nascita_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneNascitaEstero()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvinciaNascitaEstero())) {
			sql.append(sep).append("lower(\"provincia_nascita_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvinciaNascitaEstero()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataNascita())) {
			sql.append(sep).append("lower(\"data_nascita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataNascita()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSesso())) {
			sql.append(sep).append("lower(\"sesso\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getSesso()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIndirizzoResidenza())) {
			sql.append(sep).append("lower(\"indirizzo_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIndirizzoResidenza()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCivicoResidenza())) {
			sql.append(sep).append("lower(\"civico_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCivicoResidenza()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCapResidenza())) {
			sql.append(sep).append("lower(\"cap_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCapResidenza()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneResidenza())) {
			sql.append(sep).append("lower(\"comune_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneResidenza()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvinciaResidenza())) {
			sql.append(sep).append("lower(\"provincia_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvinciaResidenza()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNazionalitaResidenza())) {
			sql.append(sep).append("lower(\"nazionalita_residenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNazionalitaResidenza()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneResidenzaEstero())) {
			sql.append(sep).append("lower(\"comune_residenza_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneResidenzaEstero()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvinciaResidenzaEstero())) {
			sql.append(sep).append("lower(\"provincia_residenza_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvinciaResidenzaEstero()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getPec())) {
			sql.append(sep).append("lower(\"pec\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPec()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMail())) {
			sql.append(sep).append("lower(\"mail\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMail()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTelefono())) {
			sql.append(sep).append("lower(\"telefono\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTelefono()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCellulare())) {
			sql.append(sep).append("lower(\"cellulare\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCellulare()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getFax())) {
			sql.append(sep).append("lower(\"fax\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getFax()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDitta())) {
			sql.append(sep).append("lower(\"ditta\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDitta()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDittaEnte())) {
			sql.append(sep).append("lower(\"ditta_ente\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDittaEnte()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDittaCf())) {
			sql.append(sep).append("lower(\"ditta_cf\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDittaCf()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDittaPartitaIva())) {
			sql.append(sep).append("lower(\"ditta_partita_iva\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDittaPartitaIva()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDittaQualitaDi())) {
			sql.append(sep).append("lower(\"ditta_qualita_di\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDittaQualitaDi()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDittaQualitaAltro())) {
			sql.append(sep).append("lower(\"ditta_qualita_altro\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDittaQualitaAltro()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioIndirizzo())) {
			sql.append(sep).append("lower(\"tecnico_studio_indirizzo\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioIndirizzo()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioCivico())) {
			sql.append(sep).append("lower(\"tecnico_studio_civico\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioCivico()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioCap())) {
			sql.append(sep).append("lower(\"tecnico_studio_cap\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioCap()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioComune())) {
			sql.append(sep).append("lower(\"tecnico_studio_comune\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioComune()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioProvincia())) {
			sql.append(sep).append("lower(\"tecnico_studio_provincia\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioProvincia()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioNazionalita())) {
			sql.append(sep).append("lower(\"tecnico_studio_nazionalita\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioNazionalita()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioComuneEstero())) {
			sql.append(sep).append("lower(\"tecnico_studio_comune_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioComuneEstero()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioProvinciaEstero())) {
			sql.append(sep).append("lower(\"tecnico_studio_provincia_estero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioProvinciaEstero()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoOrdCollegio())) {
			sql.append(sep).append("lower(\"tecnico_ord_collegio\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoOrdCollegio()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoCollegioSede())) {
			sql.append(sep).append("lower(\"tecnico_collegio_sede\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoCollegioSede()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoCollegioNIscr())) {
			sql.append(sep).append("lower(\"tecnico_collegio_n_iscr\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoCollegioNIscr()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNazionalitaResidenzaName())) {
			sql.append(sep).append("lower(\"nazionalita_residenza_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNazionalitaResidenzaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvinciaResidenzaName())) {
			sql.append(sep).append("lower(\"provincia_residenza_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvinciaResidenzaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneResidenzaName())) {
			sql.append(sep).append("lower(\"comune_residenza_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneResidenzaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNazionalitaNascitaName())) {
			sql.append(sep).append("lower(\"nazionalita_nascita_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNazionalitaNascitaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getProvinciaNascitaName())) {
			sql.append(sep).append("lower(\"provincia_nascita_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getProvinciaNascitaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneNascitaName())) {
			sql.append(sep).append("lower(\"comune_nascita_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneNascitaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioNazionalitaName())) {
			sql.append(sep).append("lower(\"tecnico_studio_nazionalita_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioNazionalitaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioProvinciaName())) {
			sql.append(sep).append("lower(\"tecnico_studio_provincia_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioProvinciaName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTecnicoStudioComuneName())) {
			sql.append(sep).append("lower(\"tecnico_studio_comune_name\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTecnicoStudioComuneName()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy())) {
			final String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy()) {
			case "id":
				sql.append(" sort by \"id\" ").append(sortType);
			case "tipoReferente":
				sql.append(" sort by \"tipo_referente\" ").append(sortType);
			case "praticaId":
				sql.append(" sort by \"pratica_id\" ").append(sortType);
			case "cognome":
				sql.append(" sort by \"cognome\" ").append(sortType);
			case "nome":
				sql.append(" sort by \"nome\" ").append(sortType);
			case "codiceFiscale":
				sql.append(" sort by \"codice_fiscale\" ").append(sortType);
			case "comuneNascita":
				sql.append(" sort by \"comune_nascita\" ").append(sortType);
			case "provinciaNascita":
				sql.append(" sort by \"provincia_nascita\" ").append(sortType);
			case "nazionalitaNascita":
				sql.append(" sort by \"nazionalita_nascita\" ").append(sortType);
			case "comuneNascitaEstero":
				sql.append(" sort by \"comune_nascita_estero\" ").append(sortType);
			case "provinciaNascitaEstero":
				sql.append(" sort by \"provincia_nascita_estero\" ").append(sortType);
			case "dataNascita":
				sql.append(" sort by \"data_nascita\" ").append(sortType);
			case "sesso":
				sql.append(" sort by \"sesso\" ").append(sortType);
			case "indirizzoResidenza":
				sql.append(" sort by \"indirizzo_residenza\" ").append(sortType);
			case "civicoResidenza":
				sql.append(" sort by \"civico_residenza\" ").append(sortType);
			case "capResidenza":
				sql.append(" sort by \"cap_residenza\" ").append(sortType);
			case "comuneResidenza":
				sql.append(" sort by \"comune_residenza\" ").append(sortType);
			case "provinciaResidenza":
				sql.append(" sort by \"provincia_residenza\" ").append(sortType);
			case "nazionalitaResidenza":
				sql.append(" sort by \"nazionalita_residenza\" ").append(sortType);
			case "comuneResidenzaEstero":
				sql.append(" sort by \"comune_residenza_estero\" ").append(sortType);
			case "provinciaResidenzaEstero":
				sql.append(" sort by \"provincia_residenza_estero\" ").append(sortType);
			case "pec":
				sql.append(" sort by \"pec\" ").append(sortType);
			case "mail":
				sql.append(" sort by \"mail\" ").append(sortType);
			case "telefono":
				sql.append(" sort by \"telefono\" ").append(sortType);
			case "cellulare":
				sql.append(" sort by \"cellulare\" ").append(sortType);
			case "fax":
				sql.append(" sort by \"fax\" ").append(sortType);
			case "ditta":
				sql.append(" sort by \"ditta\" ").append(sortType);
			case "dittaEnte":
				sql.append(" sort by \"ditta_ente\" ").append(sortType);
			case "dittaCf":
				sql.append(" sort by \"ditta_cf\" ").append(sortType);
			case "dittaPartitaIva":
				sql.append(" sort by \"ditta_partita_iva\" ").append(sortType);
			case "dittaQualitaDi":
				sql.append(" sort by \"ditta_qualita_di\" ").append(sortType);
			case "dittaQualitaAltro":
				sql.append(" sort by \"ditta_qualita_altro\" ").append(sortType);
			case "tecnicoStudioIndirizzo":
				sql.append(" sort by \"tecnico_studio_indirizzo\" ").append(sortType);
			case "tecnicoStudioCivico":
				sql.append(" sort by \"tecnico_studio_civico\" ").append(sortType);
			case "tecnicoStudioCap":
				sql.append(" sort by \"tecnico_studio_cap\" ").append(sortType);
			case "tecnicoStudioComune":
				sql.append(" sort by \"tecnico_studio_comune\" ").append(sortType);
			case "tecnicoStudioProvincia":
				sql.append(" sort by \"tecnico_studio_provincia\" ").append(sortType);
			case "tecnicoStudioNazionalita":
				sql.append(" sort by \"tecnico_studio_nazionalita\" ").append(sortType);
			case "tecnicoStudioComuneEstero":
				sql.append(" sort by \"tecnico_studio_comune_estero\" ").append(sortType);
			case "tecnicoStudioProvinciaEstero":
				sql.append(" sort by \"tecnico_studio_provincia_estero\" ").append(sortType);
			case "tecnicoOrdCollegio":
				sql.append(" sort by \"tecnico_ord_collegio\" ").append(sortType);
			case "tecnicoCollegioSede":
				sql.append(" sort by \"tecnico_collegio_sede\" ").append(sortType);
			case "tecnicoCollegioNIscr":
				sql.append(" sort by \"tecnico_collegio_n_iscr\" ").append(sortType);
			case "nazionalitaResidenzaName":
				sql.append(" sort by \"nazionalita_residenza_name\" ").append(sortType);
			case "provinciaResidenzaName":
				sql.append(" sort by \"provincia_residenza_name\" ").append(sortType);
			case "comuneResidenzaName":
				sql.append(" sort by \"comune_residenza_name\" ").append(sortType);
			case "nazionalitaNascitaName":
				sql.append(" sort by \"nazionalita_nascita_name\" ").append(sortType);
			case "provinciaNascitaName":
				sql.append(" sort by \"provincia_nascita_name\" ").append(sortType);
			case "comuneNascitaName":
				sql.append(" sort by \"comune_nascita_name\" ").append(sortType);
			case "tecnicoStudioNazionalitaName":
				sql.append(" sort by \"tecnico_studio_nazionalita_name\" ").append(sortType);
			case "tecnicoStudioProvinciaName":
				sql.append(" sort by \"tecnico_studio_provincia_name\" ").append(sortType);
			case "tecnicoStudioComuneName":
				sql.append(" sort by \"tecnico_studio_comune_name\" ").append(sortType);
				break;
			default:
				this.logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = new StringBuilder("select count(*)").append(" from \"presentazione_istanza\".\"referenti\"")
				.toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}
	
	public long count(final ReferentiSearch search) 
	{
		final List<Object> parameters = new LinkedList<Object>();
		String sql = "select count(*) from \"presentazione_istanza\".\"referenti\"";
		sql = this.generateWhereClause(sql, parameters, search);
		return super.queryForObjectTxRead(sql, Long.class, parameters);
	}

	/**
	 * find by pk method
	 */
	@Override
	public ReferentiDTO find(final ReferentiDTO pk) {
		return this.find(pk,false);
		
	}
	
	public ReferentiDTO find(final ReferentiDTO pk,final boolean txWrite) {
		final String sql = new StringBuilder(this.selectAll) 
				/*new StringBuilder("select").append(" \"id\"").append(",\"tipo_referente\"")
				.append(",\"pratica_id\"").append(",\"cognome\"").append(",\"nome\"").append(",\"codice_fiscale\"")
				.append(",\"comune_nascita\"").append(",\"provincia_nascita\"").append(",\"nazionalita_nascita\"")
				.append(",\"comune_nascita_estero\"").append(",\"provincia_nascita_estero\"")
				.append(",\"data_nascita\"").append(",\"sesso\"").append(",\"indirizzo_residenza\"")
				.append(",\"civico_residenza\"").append(",\"cap_residenza\"").append(",\"comune_residenza\"")
				.append(",\"provincia_residenza\"").append(",\"nazionalita_residenza\"")
				.append(",\"comune_residenza_estero\"").append(",\"provincia_residenza_estero\"").append(",\"pec\"")
				.append(",\"mail\"").append(",\"telefono\"").append(",\"cellulare\"").append(",\"fax\"")
				.append(",\"ditta\"").append(",\"ditta_ente\"").append(",\"ditta_cf\"").append(",\"ditta_partita_iva\"")
				.append(",\"ditta_qualita_di\"").append(",\"ditta_qualita_altro\"")
				.append(",\"tecnico_studio_indirizzo\"").append(",\"tecnico_studio_civico\"")
				.append(",\"tecnico_studio_cap\"").append(",\"tecnico_studio_comune\"")
				.append(",\"tecnico_studio_provincia\"").append(",\"tecnico_studio_nazionalita\"")
				.append(",\"tecnico_studio_comune_estero\"").append(",\"tecnico_studio_provincia_estero\"")
				.append(",\"tecnico_ord_collegio\"").append(",\"tecnico_collegio_sede\"")
				.append(",\"tecnico_collegio_n_iscr\"").append(",\"nazionalita_residenza_name\"")
				.append(",\"provincia_residenza_name\"").append(",\"comune_residenza_name\"")
				.append(",\"nazionalita_nascita_name\"").append(",\"provincia_nascita_name\"")
				.append(",\"comune_nascita_name\"").append(",\"tecnico_studio_nazionalita_name\"")
				.append(",\"tecnico_studio_provincia_name\"").append(",\"tecnico_studio_comune_name\"")
				.append(" from \"presentazione_istanza\".\"referenti\"")*/
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		if(txWrite) {
			return super.queryForObject(sql, this.rowMapper, parameters);
		}else {
			return super.queryForObjectTxRead(sql, this.rowMapper, parameters);	
		}
		
	}
	
	
	/**
	 * search method
	 */
	@Override
	public PaginatedList<ReferentiDTO> search(final ReferentiSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		final String sql = this.generateWhereClause(this.selectAll, parameters, search);
		return super.paginatedList(sql.toString(), this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(final ReferentiDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"referenti\"")
				.append("(\"tipo_referente\"").append(",\"pratica_id\"").append(",\"cognome\"").append(",\"nome\"")
				.append(",\"codice_fiscale\"").append(",\"comune_nascita\"").append(",\"provincia_nascita\"")
				.append(",\"nazionalita_nascita\"").append(",\"comune_nascita_estero\"")
				.append(",\"provincia_nascita_estero\"").append(",\"data_nascita\"").append(",\"sesso\"")
				.append(",\"indirizzo_residenza\"").append(",\"civico_residenza\"").append(",\"cap_residenza\"")
				.append(",\"comune_residenza\"").append(",\"provincia_residenza\"").append(",\"nazionalita_residenza\"")
				.append(",\"comune_residenza_estero\"").append(",\"provincia_residenza_estero\"").append(",\"pec\"")
				.append(",\"mail\"").append(",\"telefono\"").append(",\"cellulare\"").append(",\"fax\"")
				.append(",\"ditta\"").append(",\"ditta_ente\"").append(",\"ditta_cf\"").append(",\"ditta_partita_iva\"")
				.append(",\"ditta_qualita_di\"").append(",\"ditta_qualita_altro\"")
				.append(",\"tecnico_studio_indirizzo\"").append(",\"tecnico_studio_civico\"")
				.append(",\"tecnico_studio_cap\"").append(",\"tecnico_studio_comune\"")
				.append(",\"tecnico_studio_provincia\"").append(",\"tecnico_studio_nazionalita\"")
				.append(",\"tecnico_studio_comune_estero\"").append(",\"tecnico_studio_provincia_estero\"")
				.append(",\"tecnico_ord_collegio\"").append(",\"tecnico_collegio_sede\"")
				.append(",\"tecnico_collegio_n_iscr\"").append(",\"nazionalita_residenza_name\"")
				.append(",\"provincia_residenza_name\"").append(",\"comune_residenza_name\"")
				.append(",\"nazionalita_nascita_name\"").append(",\"provincia_nascita_name\"")
				.append(",\"comune_nascita_name\"").append(",\"tecnico_studio_nazionalita_name\"")
				.append(",\"tecnico_studio_provincia_name\"").append(",\"tecnico_studio_comune_name\"")
				.append(",\"titolarita_id\"").append(",\"specifica_titolarita\"")
				.append(",\"titolarita_esclusiva\", \"ditta_codice_uo\"")
				.append(") values ")
				.append("(?")
				.append(",?").append(",?").append(",?").append(",?")
				.append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?")
				.append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		//parameters.add(entity.getId());
		parameters.add(entity.getTipoReferente());
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getCognome());
		parameters.add(entity.getNome());
		parameters.add(entity.getCodiceFiscale());
		parameters.add(entity.getComuneNascita());
		parameters.add(entity.getProvinciaNascita());
		parameters.add(entity.getNazionalitaNascita());
		parameters.add(entity.getComuneNascitaEstero());
		parameters.add(entity.getProvinciaNascitaEstero());
		parameters.add(entity.getDataNascita());
		parameters.add(entity.getSesso());
		parameters.add(entity.getIndirizzoResidenza());
		parameters.add(entity.getCivicoResidenza());
		parameters.add(entity.getCapResidenza());
		parameters.add(entity.getComuneResidenza());
		parameters.add(entity.getProvinciaResidenza());
		parameters.add(entity.getNazionalitaResidenza());
		parameters.add(entity.getComuneResidenzaEstero());
		parameters.add(entity.getProvinciaResidenzaEstero());
		parameters.add(entity.getPec());
		parameters.add(entity.getMail());
		parameters.add(entity.getTelefono());
		parameters.add(entity.getCellulare());
		parameters.add(entity.getFax());
		parameters.add(entity.getDitta());
		parameters.add(entity.getDittaEnte());
		parameters.add(entity.getDittaCf());
		parameters.add(entity.getDittaPartitaIva());
		parameters.add(entity.getDittaQualitaDi());
		parameters.add(entity.getDittaQualitaAltro());
		parameters.add(entity.getTecnicoStudioIndirizzo());
		parameters.add(entity.getTecnicoStudioCivico());
		parameters.add(entity.getTecnicoStudioCap());
		parameters.add(entity.getTecnicoStudioComune());
		parameters.add(entity.getTecnicoStudioProvincia());
		parameters.add(entity.getTecnicoStudioNazionalita());
		parameters.add(entity.getTecnicoStudioComuneEstero());
		parameters.add(entity.getTecnicoStudioProvinciaEstero());
		parameters.add(entity.getTecnicoOrdCollegio());
		parameters.add(entity.getTecnicoCollegioSede());
		parameters.add(entity.getTecnicoCollegioNIscr());
		parameters.add(entity.getNazionalitaResidenzaName());
		parameters.add(entity.getProvinciaResidenzaName());
		parameters.add(entity.getComuneResidenzaName());
		parameters.add(entity.getNazionalitaNascitaName());
		parameters.add(entity.getProvinciaNascitaName());
		parameters.add(entity.getComuneNascitaName());
		parameters.add(entity.getTecnicoStudioNazionalitaName());
		parameters.add(entity.getTecnicoStudioProvinciaName());
		parameters.add(entity.getTecnicoStudioComuneName());
		parameters.add(entity.getTitolaritaId());
		parameters.add(entity.getSpecificaTitolarita());
		parameters.add(entity.getTitolaritaEsclusiva());
		parameters.add(entity.getDittaCodiceUO());
		return super.insertAndGetAutoincrementValue(sql, parameters, "id");
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(final ReferentiDTO entity) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"referenti\"")
				.append(" set \"tipo_referente\" = ?").append(", \"pratica_id\" = ?").append(", \"cognome\" = ?")
				.append(", \"nome\" = ?").append(", \"codice_fiscale\" = ?").append(", \"comune_nascita\" = ?")
				.append(", \"provincia_nascita\" = ?").append(", \"nazionalita_nascita\" = ?")
				.append(", \"comune_nascita_estero\" = ?").append(", \"provincia_nascita_estero\" = ?")
				.append(", \"data_nascita\" = ?").append(", \"sesso\" = ?").append(", \"indirizzo_residenza\" = ?")
				.append(", \"civico_residenza\" = ?").append(", \"cap_residenza\" = ?")
				.append(", \"comune_residenza\" = ?").append(", \"provincia_residenza\" = ?")
				.append(", \"nazionalita_residenza\" = ?").append(", \"comune_residenza_estero\" = ?")
				.append(", \"provincia_residenza_estero\" = ?").append(", \"pec\" = ?").append(", \"mail\" = ?")
				.append(", \"telefono\" = ?").append(", \"cellulare\" = ?").append(", \"fax\" = ?")
				.append(", \"ditta\" = ?")
				.append(", \"ditta_cf\" = ?")
				.append(", \"ditta_qualita_di\" = ?")
				.append(", \"ditta_qualita_altro\" = ?").append(", \"tecnico_studio_indirizzo\" = ?")
				.append(", \"tecnico_studio_civico\" = ?").append(", \"tecnico_studio_cap\" = ?")
				.append(", \"tecnico_studio_comune\" = ?").append(", \"tecnico_studio_provincia\" = ?")
				.append(", \"tecnico_studio_nazionalita\" = ?").append(", \"tecnico_studio_comune_estero\" = ?")
				.append(", \"tecnico_studio_provincia_estero\" = ?").append(", \"tecnico_ord_collegio\" = ?")
				.append(", \"tecnico_collegio_sede\" = ?").append(", \"tecnico_collegio_n_iscr\" = ?")
				.append(", \"nazionalita_residenza_name\" = ?").append(", \"provincia_residenza_name\" = ?")
				.append(", \"comune_residenza_name\" = ?").append(", \"nazionalita_nascita_name\" = ?")
				.append(", \"provincia_nascita_name\" = ?").append(", \"comune_nascita_name\" = ?")
				.append(", \"tecnico_studio_nazionalita_name\" = ?").append(", \"tecnico_studio_provincia_name\" = ?")
				.append(", \"tecnico_studio_comune_name\" = ?")
				.append(", \"titolarita_id\" = ? ")
				.append(",\"specifica_titolarita\" = ? ")
				.append(", \"titolarita_esclusiva\" = ? ")
				.append(", codice_ipa = (case when ? is true then ? else null end)")
				.append(", id_tipo_azienda = (case when ? is true then ? else null end)")
				.append(", tipo_azienda = (case when ? is true then ? else null end)")
				.append(", \"ditta_ente\" = (case when ? is true then ? else null end)")
				.append(", \"ditta_partita_iva\" = (case when ? is true then ? else null end)")
				.append(", \"ditta_codice_uo\" = (case when ? is true then ? else null end)")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getTipoReferente());
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getCognome());
		parameters.add(entity.getNome());
		parameters.add(entity.getCodiceFiscale());
		parameters.add(entity.getComuneNascita());
		parameters.add(entity.getProvinciaNascita());
		parameters.add(entity.getNazionalitaNascita());
		parameters.add(entity.getComuneNascitaEstero());
		parameters.add(entity.getProvinciaNascitaEstero());
		parameters.add(entity.getDataNascita());
		parameters.add(entity.getSesso());
		parameters.add(entity.getIndirizzoResidenza());
		parameters.add(entity.getCivicoResidenza());
		parameters.add(entity.getCapResidenza());
		parameters.add(entity.getComuneResidenza());
		parameters.add(entity.getProvinciaResidenza());
		parameters.add(entity.getNazionalitaResidenza());
		parameters.add(entity.getComuneResidenzaEstero());
		parameters.add(entity.getProvinciaResidenzaEstero());
		parameters.add(entity.getPec());
		parameters.add(entity.getMail());
		parameters.add(entity.getTelefono());
		parameters.add(entity.getCellulare());
		parameters.add(entity.getFax());
		parameters.add(entity.getDitta());
		parameters.add(entity.getDittaCf());
		parameters.add(entity.getDittaQualitaDi());
		parameters.add(entity.getDittaQualitaAltro());
		parameters.add(entity.getTecnicoStudioIndirizzo());
		parameters.add(entity.getTecnicoStudioCivico());
		parameters.add(entity.getTecnicoStudioCap());
		parameters.add(entity.getTecnicoStudioComune());
		parameters.add(entity.getTecnicoStudioProvincia());
		parameters.add(entity.getTecnicoStudioNazionalita());
		parameters.add(entity.getTecnicoStudioComuneEstero());
		parameters.add(entity.getTecnicoStudioProvinciaEstero());
		parameters.add(entity.getTecnicoOrdCollegio());
		parameters.add(entity.getTecnicoCollegioSede());
		parameters.add(entity.getTecnicoCollegioNIscr());
		parameters.add(entity.getNazionalitaResidenzaName());
		parameters.add(entity.getProvinciaResidenzaName());
		parameters.add(entity.getComuneResidenzaName());
		parameters.add(entity.getNazionalitaNascitaName());
		parameters.add(entity.getProvinciaNascitaName());
		parameters.add(entity.getComuneNascitaName());
		parameters.add(entity.getTecnicoStudioNazionalitaName());
		parameters.add(entity.getTecnicoStudioProvinciaName());
		parameters.add(entity.getTecnicoStudioComuneName());
		parameters.add(entity.getTitolaritaId());
		parameters.add(entity.getSpecificaTitolarita());
		parameters.add(entity.getTitolaritaEsclusiva());
  		parameters.add(entity.getDitta());//parameters.add(entity.getDittaEnte());
  		parameters.add(entity.getCodiceIpa()); 		
  		parameters.add(entity.getDitta());//parameters.add(entity.getDittaPartitaIva());
  		parameters.add(entity.getIdTipoAzienda());
  		parameters.add(entity.getDitta());//parameters.add(entity.getCodiceIpa());
  		parameters.add(entity.getTipoAzienda());
  		parameters.add(entity.getDitta());//parameters.add(entity.getIdTipoAzienda());
  		parameters.add(entity.getDittaEnte());
  		parameters.add(entity.getDitta());//parameters.add(entity.getTipoAzienda());
  		parameters.add(entity.getDittaPartitaIva());
  		parameters.add(entity.getDitta());//parameters.add(entity.getTipoAzienda());
  		parameters.add(entity.getDittaCodiceUO());
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(final ReferentiDTO entity) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"referenti\"")
				.append(" where \"id\" = ?  ").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	public ReferentiDTO selectRichiedente(final UUID idPratica, final String tipo) {
		return this.selectRichiedente(idPratica, tipo, false);
	}
	
	public ReferentiDTO selectRichiedente(final UUID idPratica, final String tipo,final boolean txWrite) {
		final String sql = new StringBuilder(this.selectAll).append(" where \"pratica_id\" = ? AND \"tipo_referente\"=? ")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		parameters.add(tipo);
		if(txWrite) {
			return super.queryForObject(sql, this.rowMapper, parameters);	
		}else {
			return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
		}
		
	}
	
	public List<ReferentiDTO> selectAltriTitolari(final UUID idPratica, final String tipo,final boolean txWrite) {
		final String sql = new StringBuilder(this.selectAll).append(" where \"pratica_id\" = ? AND \"tipo_referente\"=? ")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		parameters.add(tipo);
		if(txWrite) {
			return super.queryForList(sql, this.rowMapper, parameters);
		}else {
			return super.queryForListTxRead(sql, this.rowMapper, parameters);	
		}
		
	}
	
	public int deleteByPraticaEtipo(final UUID idPratica, final String tipo) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"referenti\"")
				.append(" where \"pratica_id\" = ? AND \"tipo_referente\"=?  ").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		parameters.add(tipo);
		return super.update(sql, parameters);
	}
	
	public List<ReferentiDTO> findReferentiPratica(final UUID idPratica) throws Exception
	{
		final String sql = StringUtil.concateneString(this.selectAll, "where \"pratica_id\" = :id_pratica");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", idPratica);
		this.logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return this.namedJdbcTemplateRead.query(sql, parameters, this.rowMapper);
	}

	public int impostaAzienda(final String praticaId, final Long id, final RegistroImpreseBean bean, final TipoAziendaDTO tipoAzienda) {
		final String sql = "update presentazione_istanza.referenti set ditta = :ditta, id_tipo_azienda = :idTipo , tipo_azienda = :tipo, ditta_cf = :cf, ditta_ente = :nome, ditta_partita_iva = :iva, codice_ipa = null where id = :id and pratica_id = :pratica_id";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("pratica_id", UUID.fromString(praticaId));
		parameters.put("cf", bean.getCodiceFiscale());
		parameters.put("iva", bean.getPartitaIva());
		parameters.put("nome", bean.getRagioneSociale());
		parameters.put("idTipo", tipoAzienda.getId());
		parameters.put("tipo", tipoAzienda.getNome());
		parameters.put("ditta", true);
		return super.namedJdbcTemplateWrite.update(sql, parameters);
	}

	public int impostaEnte(final String praticaId, final Long id, final IpaEnteDto ipa, final TipoAziendaDTO tipoAzienda) {
		final String sql = "update presentazione_istanza.referenti set ditta = :ditta, id_tipo_azienda = :idTipo , tipo_azienda = :tipo, ditta_cf = :cf, ditta_ente = :nome, codice_ipa = :ipa, ditta_partita_iva = null where id = :id and pratica_id = :pratica_id";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("pratica_id", UUID.fromString(praticaId));
		parameters.put("cf", ipa.getCodiceFiscale());
		parameters.put("ipa", ipa.getCodiceIpa());
		parameters.put("nome", ipa.getNome());
		parameters.put("idTipo", tipoAzienda.getId());
		parameters.put("tipo", tipoAzienda.getNome());
		parameters.put("ditta", true);
		return super.namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	public ReferentiDTO getRichiedente(final String praticaId) throws Exception
	{
	    final String sql = new StringBuilder(this.selectAll)
		    			 .append(" where pratica_id = :pratica_id")
		    			 .append(" and tipo_referente = 'SD'")
		    			 .toString();
	    final Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("pratica_id", UUID.fromString(praticaId));
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    return namedJdbcTemplateRead.queryForObject(sql, parameters, rowMapper);
	}
	
	public String getCfRichiedente(final String praticaId) throws Exception
	{
	    final String sql = new StringBuilder("SELECT codice_fiscale")
	    				 .append(" FROM referenti")
		    			 .append(" WHERE pratica_id = :pratica_id")
		    			 .append(" AND tipo_referente = 'SD'")
		    			 .toString();
	    final Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("pratica_id", UUID.fromString(praticaId));
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
	}
	
	
	public int updateDichiarazioniTitolarita(final ReferentiDTO entity) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"referenti\"")
				.append(" set ")
				.append("\"titolarita_id\" = ? ")
				.append(",\"specifica_titolarita\" = ? ")
				.append(", \"titolarita_esclusiva\" = ? ")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getTitolaritaId());
		parameters.add(entity.getSpecificaTitolarita());
		parameters.add(entity.getTitolaritaEsclusiva());
  		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}
	
	public boolean isRichiedente(UUID praticaId, String cf)
	{
	    final String sql = "select count(*) from presentazione_istanza.referenti where tipo_referente = 'SD' and pratica_id = :pratica_id and codice_fiscale = :cf";
	    final Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("pratica_id", praticaId);
	    parameters.put("cf", cf);
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    Short n = namedJdbcTemplateRead.queryForObject(sql, parameters, Short.class);
	    return n == 1;
	}
}
