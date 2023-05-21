package it.eng.tz.puglia.autpae.generated.orm.repository;

import java.util.List;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import it.eng.tz.puglia.autpae.generated.orm.search.*;
import it.eng.tz.puglia.autpae.generated.orm.rowmapper.*;

/**
 * Repository for table
 * procedimenti_ambientali.v_paesaggio_tipo_carica_documento
 */
@Repository
public class VPaesaggioTipoCaricaDocumentoRepository
		extends GenericCrudDao<VPaesaggioTipoCaricaDocumentoDTO, VPaesaggioTipoCaricaDocumentoSearch> {

	private final VPaesaggioTipoCaricaDocumentoRowMapper rowMapper = new VPaesaggioTipoCaricaDocumentoRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"nome\"")
			.append(",\"nome_famiglia\"").append(",\"nome_ente\"").append(",\"descrizione\"").append(",\"valore\"")
			.append(" from  \"procedimenti_ambientali\".\"v_paesaggio_tipo_carica_documento\"").toString();

	@Override
	public List<VPaesaggioTipoCaricaDocumentoDTO> select() {
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"procedimenti_ambientali\".\"v_paesaggio_tipo_carica_documento\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public VPaesaggioTipoCaricaDocumentoDTO find(VPaesaggioTipoCaricaDocumentoDTO pk) {
		final String sql = new StringBuilder(selectAll).toString();
		final List<Object> parameters = new ArrayList<Object>();
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<VPaesaggioTipoCaricaDocumentoDTO> search(VPaesaggioTipoCaricaDocumentoSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getId())) {
			sql.append(sep).append("lower(\"id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNome())) {
			sql.append(sep).append("lower(\"nome\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNome()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNomeFamiglia())) {
			sql.append(sep).append("lower(\"nome_famiglia\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNomeFamiglia()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNomeEnte())) {
			sql.append(sep).append("lower(\"nome_ente\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNomeEnte()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDescrizione())) {
			sql.append(sep).append("lower(\"descrizione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDescrizione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getValore())) {
			sql.append(sep).append("lower(\"valore\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getValore()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy())) {
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy()) {
			case "id":
				sql.append(" sort by \"id\" ").append(sortType);
			case "nome":
				sql.append(" sort by \"nome\" ").append(sortType);
			case "nomeFamiglia":
				sql.append(" sort by \"nome_famiglia\" ").append(sortType);
			case "nomeEnte":
				sql.append(" sort by \"nome_ente\" ").append(sortType);
			case "descrizione":
				sql.append(" sort by \"descrizione\" ").append(sortType);
			case "valore":
				sql.append(" sort by \"valore\" ").append(sortType);
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
	public long insert(VPaesaggioTipoCaricaDocumentoDTO entity) {
		throw new RuntimeException("Non implementato!!!");
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(VPaesaggioTipoCaricaDocumentoDTO entity) {
		throw new RuntimeException("Non implementato!!!");
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(VPaesaggioTipoCaricaDocumentoDTO entity) {
		throw new RuntimeException("Non implementato!!!");
	}

}
