package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.postgis.PGbox2d;
import org.postgis.jts.JtsGeometry;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.esri.core.geometry.MapGeometry;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.dto.GeoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.geo.util.GeometryUtils;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.geo.util.esri.EsriGeometryWithAttribute;
import it.eng.tz.puglia.geo.util.esri.EsriQueryResponse;
import it.eng.tz.puglia.geo.util.esri.SimplifiedSpatialReference;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table layer_geo
 */
@Repository
public class GeoRepository extends GenericCrudDao<GeoDTO, GeoDTO> {
	final private String schemaName="presentazione_istanza";
	final private String tblName=" \""+schemaName+"\".\"layer_geo\" ";
	final private EsriGeometryWithAttributeRowMapper MAPPER_PUBLISHED=new EsriGeometryWithAttributeRowMapper(EnumNomeVista.PUBLISHED);
	final private EsriGeometryWithAttributeRowMapper MAPPER_EDITING=new EsriGeometryWithAttributeRowMapper(EnumNomeVista.EDITING);
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoRepository.class);

	private String tableName() {
		return tblName;
	}
	
	private final RowMapper<GeoDTO> rowMapper = new RowMapper<GeoDTO>() {

		@Override
		public GeoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			GeoDTO ret = new GeoDTO();
			ret.setOid(rs.getInt(1));
			org.postgis.jts.JtsGeometry geom = (org.postgis.jts.JtsGeometry) rs.getObject(2);
			// converto in geometry
			ret.setGeometry(geom);
			try {
				ret.setIdFascicolo((java.util.UUID)rs.getObject("id_fascicolo")); 
			}catch(Exception e) {
				LOGGER.error("Errore in get uuid fascicolo",e);
			}
			return ret;
		}
	};

	EsriGeometryWithAttributeRowMapper getRowMapper(EnumNomeVista vista) {
		switch (vista) {
		case EDITING:
			return MAPPER_EDITING;
		default:
			break;
		}
		return MAPPER_PUBLISHED;
	}
	
	/**
	 * select all method (NOT USED)
	 */
	@Override
	public List<GeoDTO> select() {
		String sql = StringUtil.concateneString("select", " \"oid\"", ",\"geom\"", " from ",tableName() );
		return super.queryForList(sql, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		String sql = StringUtil.concateneString("select count(*)", " from ",tableName());
		return super.queryForObject(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public GeoDTO find(GeoDTO pk) {
		String sql = StringUtil.concateneString("select", " \"oid\" ", ",\"geom\""," ,\"id_fascicolo\" ", " from ",tableName(),
				" where \"oid\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getOid());
		return super.queryForObject(sql, this.rowMapper, parameters);
	}

	/**
	 * search method  (NOT USED)
	 */
	@Override
	public PaginatedList<GeoDTO> search(GeoDTO search) {
		List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("select", " \"oid\"", ",\"geom\"", " from ",tableName()));
		if (search.getOid() > 0) {
			sql.append(sep).append("lower(\"oid\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getOid()));
			sep = " and ";
		}
		return super.paginatedList(sql.toString(), this.rowMapper, search.getPage(), search.getLimit());
	}

	@Override
	public long insert(GeoDTO entity) {
		long oid = this.insertAndGetKey(entity);
		int ret = 0;
		if (oid > 0) {
			ret++;
		}
		return ret;
	}

	public long insertAndGetKey(GeoDTO entity) {
		String sql = StringUtil.concateneString("insert into ",tableName(), " (\"geom\"", 
				" ,\"id_fascicolo\"",
				" ,\"is_custom\"",
				" ,\"user_id\"" ,
				" ,\"date_created\"",
				 ") values "	, "(?,?,?,?,current_date"	, ")");

		List<Object> parameters = new ArrayList<>();
		parameters.add(entity.getGeometry());
		parameters.add(entity.getIdFascicolo());
		parameters.add(entity.getIsCustom());
		parameters.add(entity.getUserId());
		Long key = super.insertAndGetAutoincrementValue(sql, parameters, "oid");
		return key;
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(GeoDTO entity) {
		String sql = StringUtil.concateneString("update ",tableName(), " set \"geom\" = ?", 
				" , \"date_updated\" = current_date ",
				" , \"update_user_id\" = ? ",
				" where \"oid\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getGeometry());
		parameters.add(entity.getUpdateUserId());
		parameters.add(entity.getOid());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(GeoDTO entity) {
		String sql = StringUtil.concateneString("delete from ",tableName(), " where \"oid\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getOid());
		return super.update(sql, parameters);
	}

	public int deleteByPraticaId(UUID praticaId, List<Long> oidsToRemove) {
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("delete from ",tableName(), " where \"id_fascicolo\" = ? "));
		sql.append("and (is_custom =  0  ");
		if(oidsToRemove != null && oidsToRemove.size()>0) {
			sql.append(" or  oid in (");
			sql.append(oidsToRemove.stream().map(x->x.toString()).collect(Collectors.joining(",")));
			sql.append(" ))");
		}else {
			sql.append(") ");
		}
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		return super.update(sql.toString(), parameters);
	}
	
	
	/**
	 * 
	 * @param where
	 * @param bbox nullable
	 * @param geom nullable
	 * @param onlyPublished
	 * @return
	 * @throws CustomValidationException 
	 */
	private String makeQuery(String where, EsriBBox bbox,JtsGeometry geom, EnumNomeVista nomeVista) throws CustomValidationException {
		StringUtil.checkSqlInjection(where);
		StringBuilder query = new StringBuilder();
		String sep = " where ";
		if(nomeVista.equals(EnumNomeVista.EDITING)) {
			query
			.append("select g.\"oid\"")
			.append(",g.\"geom\"")
			.append(" ,g.\"id_fascicolo\" ")
			.append(" ,g.\"user_id\" ")
			.append(" ,g.\"date_created\" ")
			.append(" ,g.\"codice\" ");
			//qui i campi che dipendono dalla vista....
			query
			.append(",g.\"oggetto_intervento\" ");	
		}else {
			//published
			query
			.append("select id_fascicolo,oid,codice_pratica,geom,comuni,oggetto_intervento,note,"
					+ "tipo_intervento,tipologia_autorizzazione,responsabile,numero_procedimento,"
					+ "data_procedimento,esito_richiesta,data_trasmissione,"
					+ "istat_amm_competente,sanatoria,is_custom,is_particella,user_id,date_created ");
		}
		query.append(" from ")
		.append("\""+schemaName)
		.append("\".\"")
		.append(nomeVista.getNomeVista()+"\" ")
		.append(" g  ");
		if(where!=null && !StringUtil.isEmpty(where)) {
			query.append(sep);
			query.append(where);
			sep=" AND ";
		}
		// cerca tutte le geometri che ricadono nella bbox richiesta
		if(bbox!=null) {
			query.append(sep);
			String wherebbox = "  geom && ST_MakeEnvelope (" + "            " + bbox.getXmin() + "," + bbox.getYmin()
			+ "," + "			 " + bbox.getXmax() + "," + bbox.getYmax() + "," + "        "
			+ bbox.getSpatialReference().getWkid() + ") ";
			query.append(wherebbox);
		}
		if(geom!=null) {
			query.append(sep);
			query.append("  (geom && ? ) ");
		}
		query.append(" limit 1000");
		return query.toString();
	}
	
	public EsriQueryResponse queryLayer(String where, EsriBBox bbox,EnumNomeVista nomeVista) throws CustomValidationException {
		EsriQueryResponse respone = new EsriQueryResponse();
		StringBuilder sql=new StringBuilder(this.makeQuery(where, bbox, null,nomeVista));
		List<EsriGeometryWithAttribute> results = this.jdbcTemplateRead.query(sql.toString(),getRowMapper(nomeVista));
		respone.setObjectIdFieldName("oid");
		respone.setSpatialReference(new SimplifiedSpatialReference(32633, 32633));
		respone.setFeatures(results);
		return respone;
	}
	
	public EsriQueryResponse intersect(JtsGeometry geom,EnumNomeVista nomeVista) throws CustomValidationException {
		EsriQueryResponse respone = new EsriQueryResponse();
		StringBuilder sql = new StringBuilder(this.makeQuery("", null, geom, nomeVista));
		List<EsriGeometryWithAttribute> results = this.jdbcTemplateRead.query(sql.toString(),getRowMapper(nomeVista),geom);
		respone.setObjectIdFieldName("oid");
		respone.setSpatialReference(new SimplifiedSpatialReference(32633, 32633));
		respone.setFeatures(results);
		return respone;
	}
	
	
	public EsriQueryResponse queryExtent(String where) throws CustomValidationException {
		StringUtil.checkSqlInjection(where);
		EsriQueryResponse respone = new EsriQueryResponse();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("select", "  ST_Extent(geom) as bextent   ", " from  ",tableName()));
		String whereId = StringUtil.concateneString(" where ",where) ;
		sql.append(whereId);
		List<PGobject>results = this.jdbcTemplateRead.query(sql.toString(), 
				new RowMapper<PGobject>() {
			@Override
			public PGobject mapRow(ResultSet rs, int rowNum) throws SQLException {
				PGobject ret=(PGobject) rs.getObject(1);
				return ret;
			}
		});
		if(results!=null && results.size()>0 && results.get(0) instanceof PGbox2d ) {
			PGbox2d pgBox2d = (PGbox2d) results.get(0);
			EsriBBox ebox=new EsriBBox();
			//converto il PGbox2D in EsriBBox
			ebox.setXmin(pgBox2d.getLLB().getX());
			ebox.setXmax(pgBox2d.getURT().getX());
			ebox.setYmin(pgBox2d.getLLB().getY());
			ebox.setYmax(pgBox2d.getURT().getY());
			ebox.setSpatialReference(new SimplifiedSpatialReference(32633, 32633));
			respone.setCount(1);
			respone.setExtent(ebox);
		}else {
			respone.setCount(1);
			respone.setExtent(new EsriBBox());	
		}
		return respone;
	}
	
	
	
}
