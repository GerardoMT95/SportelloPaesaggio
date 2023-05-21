package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DescrizioneSchedaTecnicaValuesDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.DescrizioneSchedaTecnicaValuesRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.DescrizioneSchedaTecnicaValuesSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.descrizione_scheda_tecnica_values
 */
@Repository
public class DescrizioneSchedaTecnicaValuesRepository
		extends GenericCrudDao<DescrizioneSchedaTecnicaValuesDTO, DescrizioneSchedaTecnicaValuesSearch>
{

	private final DescrizioneSchedaTecnicaValuesRowMapper rowMapper = new DescrizioneSchedaTecnicaValuesRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"pratica_id\"").append(",\"codice\"")
			.append(",\"text\"").append(",\"sezione\"")
			.append(" from \"presentazione_istanza\".\"descrizione_scheda_tecnica_values\"").toString();

	@Override
	public List<DescrizioneSchedaTecnicaValuesDTO> select()
	{
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count()
	{
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"descrizione_scheda_tecnica_values\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public DescrizioneSchedaTecnicaValuesDTO find(DescrizioneSchedaTecnicaValuesDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"pratica_id\" = ?")
				.append(" and \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getPraticaId());
		parameters.add(pk.getCodice());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	public List<DescrizioneSchedaTecnicaValuesDTO> find(UUID idPratica) throws Exception
	{
		final String sql = new StringBuilder(selectAll).append(" where \"pratica_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return super.queryForList(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<DescrizioneSchedaTecnicaValuesDTO> search(DescrizioneSchedaTecnicaValuesSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getPraticaId()))
		{
			sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPraticaId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodice()))
		{
			sql.append(sep).append("lower(\"codice\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCodice()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getText()))
		{
			sql.append(sep).append("lower(\"text\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getText()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSezione()))
		{
			sql.append(sep).append("lower(\"sezione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getSezione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "praticaId":
				sql.append(" sort by \"pratica_id\" ").append(sortType);
			case "codice":
				sql.append(" sort by \"codice\" ").append(sortType);
			case "text":
				sql.append(" sort by \"text\" ").append(sortType);
			case "sezione":
				sql.append(" sort by \"sezione\" ").append(sortType);
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
	public long insert(DescrizioneSchedaTecnicaValuesDTO entity)
	{
		final String sql = new StringBuilder(
				"insert into \"presentazione_istanza\".\"descrizione_scheda_tecnica_values\"").append("(\"pratica_id\"")
						.append(",\"codice\"").append(",\"text\"").append(",\"sezione\"").append(") values ")
						.append("(?").append(",?").append(",?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getCodice());
		parameters.add(entity.getText());
		parameters.add(entity.getSezione().toString());
		return super.update(sql, parameters);
	}

	public long insert(List<DescrizioneSchedaTecnicaValuesDTO> entity)
	{
		int counter = 0;
		for (DescrizioneSchedaTecnicaValuesDTO e : entity)
			counter += insert(e);

		return counter;
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(DescrizioneSchedaTecnicaValuesDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"descrizione_scheda_tecnica_values\"")
				.append(" set \"text\" = ?").append(", \"sezione\" = ?").append(" where \"pratica_id\" = ?")
				.append(" and \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getText());
		parameters.add(entity.getSezione().toString());
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(DescrizioneSchedaTecnicaValuesDTO entity)
	{
		final String sql = new StringBuilder(
				"delete from \"presentazione_istanza\".\"descrizione_scheda_tecnica_values\"")
						.append(" where \"pratica_id\" = ?").append(" and \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}

	/**
	 * delete by id pratica
	 */
	public int delete(UUID idPratica)
	{
		final String sql = new StringBuilder(
				"delete from \"presentazione_istanza\".\"descrizione_scheda_tecnica_values\"")
						.append(" where \"pratica_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return super.update(sql, parameters);
	}
	
	public int deleteValueNonAmmessi(UUID praticaId,int tipoProcedimento) {
		StringBuilder sql =  new StringBuilder();
		sql.append(StringUtil.concateneString(
				"delete from ", " descrizione_scheda_tecnica_values val where ", 
				" val.pratica_id = ? and ",
				" val.codice not in ", 
				"(select pq.codice_tipi_e_qualificazioni ", " FROM ",
				" procedimento_qualificazioni pq left join ", " tipi_e_qualificazioni tq on ",
				" pq.codice_tipi_e_qualificazioni =tq.codice ", " where ",
				" pq.id_tipo_procedimento = ? ) ",
				" and val.codice <>'DESCR' "));	
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		parameters.add(tipoProcedimento);
		return super.update(sql.toString(), parameters);
	}
	
	
	public int deleteBySezioneAndPratica(String sezione,UUID idPratica)
	{
		final String sql = new StringBuilder(
				"delete from \"presentazione_istanza\".\"descrizione_scheda_tecnica_values\"")
						.append(" where \"pratica_id\" = ? and sezione = ? ").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		parameters.add(sezione);
		return super.update(sql, parameters);
	}
	

}
