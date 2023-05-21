package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.NumeroPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.NumeroPraticaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.NumeroPraticaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.pratica
 */
@Repository
public class NumeroPraticaRepository extends GenericCrudDao<NumeroPraticaDTO, NumeroPraticaSearch> {

	private final NumeroPraticaRowMapper rowMapper = new NumeroPraticaRowMapper();

	/**
	 * select all method
	 */
	@Override
	public List<NumeroPraticaDTO> select() {
		final String sql = StringUtil.concateneString("select", " \"id\"", ",\"anno\"", ",\"numero\"",
				" from \"presentazione_istanza\".\"numero_pratica\"");
		return super.queryForListTxRead(sql, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = StringUtil.concateneString("select count(*)",
				" from \"presentazione_istanza\".\"numero_pratica\"");
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public NumeroPraticaDTO find(NumeroPraticaDTO pk) {
		final String sql = StringUtil.concateneString("select", " \"id\"", ",\"anno\"", ",\"numero\"",
				" from \"presentazione_istanza\".\"numero_pratica\"");
		final List<Object> parameters = new ArrayList<Object>();
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<NumeroPraticaDTO> search(NumeroPraticaSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(StringUtil.concateneString("select", " \"id\"", ",\"anno\"",
				",\"numero\"", " from \"presentazione_istanza\".\"numero_pratica\""));
		if (StringUtil.isNotEmpty(search.getId())) {
			sql.append(sep).append("lower(\"id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getAnno())) {
			sql.append(sep).append("lower(\"anno\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getAnno()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNumero())) {
			sql.append(sep).append("lower(\"numero\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNumero()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy())) {
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy()) {
			case "id":
				sql.append(" sort by \"id\" ").append(sortType);
			case "anno":
				sql.append(" sort by \"anno\" ").append(sortType);
			case "numero":
				sql.append(" sort by \"numero\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return super.paginatedList(sql.toString(), this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(NumeroPraticaDTO entity) {
		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"numero_pratica\"",
				"(\"anno\"", ",\"numero\"", ") values ", "(?", ",?", ")");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getAnno());
		parameters.add(entity.getNumero());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(NumeroPraticaDTO entity) {
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"numero_pratica\"",
				" set \"id\" = ?", ", \"anno\" = ?", ", \"numero\" = ?");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		parameters.add(entity.getAnno());
		parameters.add(entity.getNumero());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(NumeroPraticaDTO entity) {
		final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"numero_pratica\"");
		final List<Object> parameters = new ArrayList<Object>();
		return super.update(sql, parameters);
	}

	public long checkNumPraticaAnnoCorrente() {

		final String sql = StringUtil.concateneString("select count(*)" + " from presentazione_istanza.numero_pratica"
				+ " where anno = (SELECT EXTRACT(YEAR FROM current_date))");
		return super.queryForObject(sql, Long.class);

	}

	public long initNumPraticaAnnoCorrente() {
		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"numero_pratica\"",
				"(\"anno\"", ",\"numero\"", ") values ", "((SELECT EXTRACT(YEAR FROM current_date))", ",0", ")");
		return super.update(sql);

	}

	public NumeroPraticaDTO getNumeroPratica() {
		final String sql = StringUtil.concateneString("select" + " id,anno,numero, " + " (numero+1) as next_numero,"
				+ " CONCAT  ((numero+1) , '-', anno) AS numero_pratica "
				+ " from presentazione_istanza.numero_pratica ORDER BY anno DESC,numero DESC limit 1"

		);
		final List<Object> parameters = new ArrayList<Object>();
		return super.queryForObject(sql, this.rowMapper, parameters);
	}

	public long insertNumeroPratica(Long next_num) {

		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"numero_pratica\"",
				"(\"anno\"", ",\"numero\"", ") values ", "((SELECT EXTRACT(YEAR FROM current_date))", ",?", ")");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(next_num);
		return super.update(sql, parameters);
	}

	/**
	 * durante la migrazione delle pratiche devo aggiornare i contatori
	 * 
	 * @author acolaianni
	 *
	 * @param anno
	 * @param numero
	 * @return
	 */
	public long avanzaNumPraticaAnno(int anno, int numero) {
		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"numero_pratica\"",
				" (\"anno\"", ",\"numero\"", ") values ", "(?", ",?", ")");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(anno);
		parameters.add(numero);
		return super.update(sql, parameters);
	}

	public NumeroPraticaDTO esisteNumeroPraticaAnno(int anno, int numero) {
		final String sql = StringUtil.concateneString("select id,anno,numero, ", " (numero+1) as next_numero,",
				" CONCAT  ((numero+1) , '-', anno) AS numero_pratica ",
				" FROM \"presentazione_istanza\".\"numero_pratica\" ", " WHERE \"anno\"=? AND \"numero\"=? ");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(anno);
		parameters.add(numero);
		return super.queryForObject(sql, this.rowMapper, parameters);
	}

}
