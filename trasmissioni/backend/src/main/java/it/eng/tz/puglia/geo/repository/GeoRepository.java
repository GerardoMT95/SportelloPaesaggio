package it.eng.tz.puglia.geo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.postgis.PGbox2d;
import org.postgis.jts.JtsGeometry;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.geo.dto.GeoDTO;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.geo.util.esri.EsriGeometryWithAttribute;
import it.eng.tz.puglia.geo.util.esri.EsriQueryResponse;
import it.eng.tz.puglia.geo.util.esri.SimplifiedSpatialReference;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table pev.layer_geo
 */
@Repository
public class GeoRepository extends GenericDao{
	
	@Value("${geo.layer.max_geom_retrieved:300}")
	private Integer maxGeomRetrieved;
	
	final private String tblName="\"layer_geo\"";
	private EsriGeometryWithAttributeRowMapper MAPPER_PUBLISHED=null;
	private EsriGeometryWithAttributeRowMapper MAPPER_EDITING=null;
	private static final Logger log = LoggerFactory.getLogger(GeoRepository.class);
	public static final int SRID = 32633;
	
	private String tableName() {
		return " \""+props.getCodiceApplicazione()+
		       "\"."+tblName+" ";
	}
	
//	/**
//	 * Jdbc template
//	 */
//	protected JdbcTemplate jdbcTemplate;
//	/**
//	 * NAmed jdbc template
//	 */
	
	@Autowired
	@Qualifier("named_jdbctemplate_geo")
	protected NamedParameterJdbcTemplate namedJdbcTemplateGeo;
	
	@Autowired
	ApplicationProperties props;
	
	/**
	 * Post constructor
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 */
	@PostConstruct
	private void postConstruct() {
		this.namedJdbcTemplate=this.namedJdbcTemplateGeo;
		this.jdbcTemplate = this.namedJdbcTemplate.getJdbcTemplate();
		MAPPER_PUBLISHED=new EsriGeometryWithAttributeRowMapper(EnumNomeVista.PUBLISHED,props);
		MAPPER_EDITING=new EsriGeometryWithAttributeRowMapper(EnumNomeVista.EDITING,props);
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
				ret.setIdFascicolo(rs.getLong("id_fascicolo")); 
			}catch(Exception e) {
				log.error("Errore in mapRow",e);
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
	 * find by pk method
	 */
	public GeoDTO find(GeoDTO pk) {
		String sql = StringUtil.concateneString("select", " \"oid\" ", ",\"geometry\""," ,\"id_fascicolo\" ", " from ",tableName(),
				" where \"oid\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getOid());
		return this.queryForObject(sql, this.rowMapper, parameters);
	}

	public int insert(GeoDTO entity) {
		long oid = this.insertAndGetKey(entity);
		int ret = 0;
		if (oid > 0) {
			ret++;
		}
		return ret;
	}

	public long insertAndGetKey(GeoDTO entity) {
		String sql = StringUtil.concateneString("insert into ",tableName(), 
				" (\"geometry\"", 
				" ,\"id_fascicolo\"",
				" ,\"is_custom\"" ,
				" ,\"user_id\"" ,
				" ,\"date_created\"",
				 ") values "	, "(?,?,?,?,current_date"	, ")");

		List<Object> parameters = new ArrayList<>();
		parameters.add(entity.getGeometry());
		parameters.add(entity.getIdFascicolo());
		parameters.add(entity.getIsCustom());
		parameters.add(entity.getUserId());
		Long key = this.insertAndGetKey(sql, parameters, "oid");
		return key;
	}

	/**
	 * SRID non settato, inteso come quello Puglia... (4326) 
	 * @autor Adriano Colaianni
	 * @date 11 mag 2021
	 * @param entity
	 * @param wkt
	 * @return
	 */
	public long insertFromWktAndGetKey(GeoDTO entity,String wkt) {
		String sql = StringUtil.concateneString("insert into ",tableName(), 
				" (\"geometry\"", 
				" ,\"id_fascicolo\"",
				" ,\"is_custom\"" ,
				" ,\"user_id\"" ,
				" ,\"date_created\"",
				 ") values "	, "(ST_Force3D(ST_GeomFromText(?,32633)),?,?,?,current_date"	, ")");

		List<Object> parameters = new ArrayList<>();
		parameters.add(wkt);
		parameters.add(entity.getIdFascicolo());
		parameters.add(entity.getIsCustom());
		parameters.add(entity.getUserId());
		Long key = this.insertAndGetKey(sql, parameters, "oid");
		return key;
	}
	
	private long insertAndGetKey(String sql, List<Object> parameters, String idColumn) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
	/*	final int rows = */ this.jdbcTemplate.update(new InsertPreparedStatementCreator(sql, parameters, idColumn),keyHolder);
		final long id = keyHolder.getKey().longValue();
		logger.info(StringUtil.concateneString("New Id: ", id));
		return id;
	}
	
	/**
	 * update by pk method
	 */
	public int update(GeoDTO entity) {
		String sql = StringUtil.concateneString("update ",tableName(), 
				" set \"geometry\" = ?",
				" , \"date_updated\" = current_date ",
				" , \"update_user_id\" = ? ",
				" where \"oid\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getGeometry());
		parameters.add(entity.getUpdateUserId());
		parameters.add(entity.getOid());
		return this.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	public int delete(GeoDTO entity) {
		String sql = StringUtil.concateneString("delete from ",tableName(), " where \"oid\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getOid());
		return this.update(sql, parameters);
	}
	
	public int deleteByIdFascicolo(Long idFascicolo) {
		String sql = StringUtil.concateneString("delete from ",tableName(), " where \"id_fascicolo\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(idFascicolo);
		return this.update(sql, parameters);
	}
	
	public List<Long> findByIdFascicolo(Long idFascicolo) {
		String sql = StringUtil.concateneString("SELECT oid from ",tableName(), " where \"id_fascicolo\" = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(idFascicolo);
		return this.queryForList(sql, Long.class, parameters);
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
		query
		.append("select g.\"id_fascicolo\"")
		//qui i campi che dipendono dalla vista....
		.append(" ,g.\"oid\" ")
		.append(" ,g.\"codice_pratica\" ")
		.append(" ,g.\"geometry\" ")
		.append(" ,g.\"comuni\" ")
		.append(" ,g.\"oggetto_intervento\" ")
		.append(" ,g.\"note\" ")
		.append(" ,g.\"tipo_intervento\" ")
		.append(" ,g.\"tipologia_autorizzazione\" ")
		.append(" ,g.\"responsabile\" ")
		.append(" ,g.\"numero_procedimento\" ")
		.append(" ,g.\"data_procedimento\" ")
		.append(" ,g.\"esito_richiesta\" ")
		.append(" ,g.\"data_trasmissione\" ")
		.append(" ,g.\"istat_amm_competente\" ")
		.append(" ,g.\"sanatoria\" ")
		.append(" ,g.\"is_custom\" ")
		.append(" ,g.\"is_particella\" ")
		.append(" ,g.\"user_id\" ")
		.append(" ,g.\"date_created\" ")
		.append(" ,ST_Area(g.\"geometry\") as area_shape");
		query.append(" from ")
		.append("\""+props.getCodiceApplicazione())
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
			String wherebbox = "  geometry && ST_MakeEnvelope (" + "            " + bbox.getXmin() + "," + bbox.getYmin()
			+ "," + "			 " + bbox.getXmax() + "," + bbox.getYmax() + "," + "        "
			+ bbox.getSpatialReference().getWkid() + ") ";
			query.append(wherebbox);
		}
		if(geom!=null) {
			query.append(sep);
			query.append("  (geometry && ? ) ");
		}
		//query.append(" limit 1000");
		query
		.append(" order by area_shape desc ")
		.append(" limit ")
		.append(maxGeomRetrieved);
		return query.toString();
	}
	
	public EsriQueryResponse queryLayer(String where, EsriBBox bbox,EnumNomeVista nomeVista) throws CustomValidationException {
		EsriQueryResponse respone = new EsriQueryResponse();
		StringBuilder sql=new StringBuilder(this.makeQuery(where, bbox, null,nomeVista));
		List<EsriGeometryWithAttribute> results = this.jdbcTemplate.query(sql.toString(),getRowMapper(nomeVista));
		respone.setObjectIdFieldName("oid");
		respone.setSpatialReference(new SimplifiedSpatialReference(32633, 32633));
		respone.setFeatures(results);
		return respone;
	}
	
	
	public EsriQueryResponse intersect(JtsGeometry geom,EnumNomeVista nomeVista) throws CustomValidationException {
		EsriQueryResponse respone = new EsriQueryResponse();
		StringBuilder sql = new StringBuilder(this.makeQuery("", null, geom, nomeVista));
		List<EsriGeometryWithAttribute> results = this.jdbcTemplate.query(sql.toString(),getRowMapper(nomeVista),geom);
		respone.setObjectIdFieldName("oid");
		respone.setSpatialReference(new SimplifiedSpatialReference(32633, 32633));
		respone.setFeatures(results);
		return respone;
	}
	
	
	public EsriQueryResponse queryExtent(String where) throws CustomValidationException {
		StringUtil.checkSqlInjection(where);
		EsriQueryResponse respone = new EsriQueryResponse();
		StringBuilder sql = new StringBuilder(
				StringUtil.concateneString("select", "  ST_Extent(geometry) as bextent   ", " from  ",tableName()));
		String whereId = StringUtil.concateneString(" where ",where) ;
		sql.append(whereId);
		List<PGobject>results = this.jdbcTemplate.query(sql.toString(), 
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
	
	/**
	 * inserisce le geometrie da una lista di WKT
	 * 
	 * @param wkt
	 * @param planId
	 * @param isCustom
	 * @param userId
	 */
	public List<Long> insertWkt(final List<String> wkt, final Long idFascicolo, final int isCustom, final String userId) {
		final List<Long> result = new ArrayList<>();
		final String sql = StringUtil.concateneString("insert into ",tableName(), 
				" (\"geometry\"", 
				" ,\"id_fascicolo\"",
				" ,\"is_custom\"" ,
				" ,\"user_id\"" ,
				" ,\"date_created\"",
				 ") values (ST_Force3D(ST_GeomFromText(?,?)),?,?,?,current_date)");
		wkt.forEach(item -> {
			final List<Object> params = new ArrayList<>();
			params.add(item);
			params.add(SRID);
			params.add(idFascicolo);
			params.add(isCustom);
			params.add(userId);
			final Long oid = this.insertAndGetKey(sql, params, "oid");
			this.logger.info("Add oid {}", oid);
			result.add(oid);
		});
		return result;
	}


}