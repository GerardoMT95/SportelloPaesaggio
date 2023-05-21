package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.*;
import java.math.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;

import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.pratica_pagamenti
 */
@Repository
public class PraticaPagamentiRepository extends GenericCrudDao<PraticaPagamentiDTO, PraticaPagamentiSearch> {

	private final PraticaPagamentiRowMapper rowMapper = new PraticaPagamentiRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"id_pratica\"")
			.append(",\"id_tariffa\"").append(",\"importo_progetto\"").append(",\"data_scadenza\"")
			.append(",\"importo_pagamento\"").append(",\"iud\"").append(",\"iuv\"").append(",\"url_pdf\"")
			.append(",\"url_mypay\"").append(",\"deleted\"").append(",\"pagato\"").append(",\"create_date\"")
			.append(",\"create_user\"").append(",\"update_date\"").append(",\"update_user\"").append(",\"causale\"")
			.append(",\"ricevuta\"").append(" from \"presentazione_istanza\".\"pratica_pagamenti\"").toString();

	@Override
	public List<PraticaPagamentiDTO> select() {
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"pratica_pagamenti\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public PraticaPagamentiDTO find(PraticaPagamentiDTO pk) {
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<PraticaPagamentiDTO> search(PraticaPagamentiSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getId())) {
			sql.append(sep).append("lower(\"id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getId()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdPratica())) {
			sql.append(sep).append("\"id_pratica\"::text = ?");
			parameters.add(search.getIdPratica());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdTariffa())) {
			sql.append(sep).append("lower(\"id_tariffa\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdTariffa()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getImportoProgetto())) {
			sql.append(sep).append("lower(\"importo_progetto\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getImportoProgetto()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataScadenza())) {
			sql.append(sep).append("lower(\"data_scadenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataScadenza()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getImportoPagamento())) {
			sql.append(sep).append("lower(\"importo_pagamento\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getImportoPagamento()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIud())) {
			sql.append(sep).append("lower(\"iud\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIud()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIuv())) {
			sql.append(sep).append("lower(\"iuv\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIuv()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUrlPdf())) {
			sql.append(sep).append("lower(\"url_pdf\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUrlPdf()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUrlMypay())) {
			sql.append(sep).append("lower(\"url_mypay\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUrlMypay()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDeleted())) {
			sql.append(sep).append("lower(\"deleted\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDeleted()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getPagato())) {
			sql.append(sep).append("lower(\"pagato\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPagato()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCreateDate())) {
			sql.append(sep).append("lower(\"create_date\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCreateDate()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCreateUser())) {
			sql.append(sep).append("lower(\"create_user\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCreateUser()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUpdateDate())) {
			sql.append(sep).append("lower(\"update_date\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUpdateDate()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUpdateUser())) {
			sql.append(sep).append("lower(\"update_user\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUpdateUser()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCausale())) {
			sql.append(sep).append("lower(\"causale\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCausale()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getRicevuta())) {
			sql.append(sep).append("lower(\"ricevuta\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getRicevuta()).toLowerCase());
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
			case "idTariffa":
				sql.append(" order by \"id_tariffa\" ").append(sortType);
				break;
			case "importoProgetto":
				sql.append(" order by \"importo_progetto\" ").append(sortType);
				break;
			case "dataScadenza":
				sql.append(" order by \"data_scadenza\" ").append(sortType);
				break;
			case "importoPagamento":
				sql.append(" order by \"importo_pagamento\" ").append(sortType);
				break;
			case "iud":
				sql.append(" order by \"iud\" ").append(sortType);
				break;
			case "iuv":
				sql.append(" order by \"iuv\" ").append(sortType);
				break;
			case "urlPdf":
				sql.append(" order by \"url_pdf\" ").append(sortType);
				break;
			case "urlMypay":
				sql.append(" order by \"url_mypay\" ").append(sortType);
				break;
			case "deleted":
				sql.append(" order by \"deleted\" ").append(sortType);
				break;
			case "pagato":
				sql.append(" order by \"pagato\" ").append(sortType);
				break;
			case "createDate":
				sql.append(" order by \"create_date\" ").append(sortType);
				break;
			case "createUser":
				sql.append(" order by \"create_user\" ").append(sortType);
				break;
			case "updateDate":
				sql.append(" order by \"update_date\" ").append(sortType);
				break;
			case "updateUser":
				sql.append(" order by \"update_user\" ").append(sortType);
				break;
			case "causale":
				sql.append(" order by \"causale\" ").append(sortType);
				break;
			case "ricevuta":
				sql.append(" order by \"ricevuta\" ").append(sortType);
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
	public long insert(PraticaPagamentiDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pratica_pagamenti\"")
				.append("(\"id_pratica\"")
				.append(",\"id_tariffa\"")
				.append(",\"importo_progetto\"")
				.append(",\"data_scadenza\"")
				.append(",\"importo_pagamento\"")
				.append(",\"iud\"")
				.append(",\"iuv\"")
				.append(",\"url_pdf\"")
				.append(",\"url_mypay\"")
				.append(",\"deleted\"")
				.append(",\"pagato\"")
				.append(",\"create_date\"")
				.append(",\"create_user\"")
				.append(",\"causale\"")
				.append(",\"ricevuta\"")
				.append(") values ")
				.append("(?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getIdTariffa());
		parameters.add(entity.getImportoProgetto());
		parameters.add(entity.getDataScadenza());
		parameters.add(entity.getImportoPagamento());
		parameters.add(CryptUtil.encrypt(entity.getIud()));
		parameters.add(CryptUtil.encrypt(entity.getIuv()));
		parameters.add(CryptUtil.encrypt(entity.getUrlPdf()));
		parameters.add(CryptUtil.encrypt(entity.getUrlMypay()));
		parameters.add(entity.getDeleted());
		parameters.add(entity.getPagato());
		parameters.add(new java.util.Date());
		parameters.add(SecurityUtil.getUsername());
		parameters.add(entity.getCausale());
		parameters.add(CryptUtil.encrypt(entity.getRicevuta()));
		return super.insertAndGetAutoincrementValue(sql, parameters, "id");
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(PraticaPagamentiDTO entity) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_pagamenti\"")
				.append(" set \"id_pratica\" = ?").append(", \"id_tariffa\" = ?").append(", \"importo_progetto\" = ?")
				.append(", \"data_scadenza\" = ?").append(", \"importo_pagamento\" = ?").append(", \"iud\" = ?")
				.append(", \"iuv\" = ?").append(", \"url_pdf\" = ?").append(", \"url_mypay\" = ?")
				.append(", \"deleted\" = ?").append(", \"pagato\" = ?")
				.append(", \"update_date\" = now() ").append(", \"update_user\" = ?")
				.append(", \"causale\" = ?").append(", \"ricevuta\" = ?").append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getIdTariffa());
		parameters.add(entity.getImportoProgetto());
		parameters.add(entity.getDataScadenza());
		parameters.add(entity.getImportoPagamento());
		parameters.add(CryptUtil.encrypt(entity.getIud()));
		parameters.add(CryptUtil.encrypt(entity.getIuv()));
		parameters.add(CryptUtil.encrypt(entity.getUrlPdf()));
		parameters.add(CryptUtil.encrypt(entity.getUrlMypay()));
		parameters.add(entity.getDeleted());
		parameters.add(entity.getPagato());
		parameters.add(SecurityUtil.getUsername());
		parameters.add(entity.getCausale());
		parameters.add(CryptUtil.encrypt(entity.getRicevuta()));
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(PraticaPagamentiDTO entity) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pratica_pagamenti\"")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	/**
	 * aggiorna pagato=true, ricevuta update_user update_date
	 * @author acolaianni
	 *
	 * @param id
	 * @param ricevuta
	 * @return
	 */
	public int aggiornaPagamentoATrue(long id, String ricevuta) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_pagamenti\" ")
				.append(" set ricevuta = ? ")
				.append(" ,update_user = ? ")
				.append(" ,pagato = true ")
				.append(" ,update_date = now() ")
				.append(" where \"id\" = ? ").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(CryptUtil.encrypt(ricevuta));
		parameters.add(SecurityUtil.getUsername());
		parameters.add(id);
		return super.update(sql, parameters);
	}
	
	
	public int annullaPagamento(long id) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_pagamenti\" ")
				.append(" set ")
				.append(" deleted = true ")
				.append(" ,update_user = ? ")
				.append(" ,update_date = now() ")
				.append(" where \"id\" = ? ").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(SecurityUtil.getUsername());
		parameters.add(id);
		return super.update(sql, parameters);
	}
	
	/**
	 * Aggiorna url per pagare su mypay
	 * @author acolaianni
	 *
	 * @param id
	 * @param url
	 * @return
	 */
	public int salvaUrlMyPay(long id,final String url) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pratica_pagamenti\" ")
				.append(" set ")
				.append(" url_mypay = ? ")
				.append(" ,update_user = ? ")
				.append(" ,update_date = now() ")
				.append(" where \"id\" = ? and deleted = false ").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(CryptUtil.encrypt(url));
		parameters.add(SecurityUtil.getUsername());
		parameters.add(id);
		return super.update(sql, parameters);
	}

}
