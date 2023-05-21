package it.eng.tz.puglia.autpae.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dto.SportelloAmbienteParticelleDTO;
import it.eng.tz.puglia.autpae.dto.SportelloAmbienteShapeDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.GenericDao;
import it.eng.tz.puglia.geo.repository.InsertPreparedStatementCreator;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table of procedimenti_ambientali schema
 * @author Adriano Colaianni
 * @date 26 lug 2022
 */
@Repository
public class ProcedimentiAmbientaliRepository extends GenericDao{
	private static final Logger log = LoggerFactory.getLogger(ProcedimentiAmbientaliRepository.class);
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param idPratica
	 * @return Edit Particelle Shape
	 */
	public String selectTipoLocalizzazione(UUID idPratica) {
		StringBuilder sql=new StringBuilder();
		List<Object> param=new ArrayList<>(); 
		sql.append("SELECT modalita_inserimento  FROM ")
		.append(" \"procedimenti_ambientali\".\"pratica_localizzazione\" ")
		.append(" WHERE ")
		.append(" id::text = ? ");
		param.add(idPratica.toString());
		return super.queryForObject(sql.toString(),String.class,param);
	}
	
	/**
	 * retrieve comuni intervento (somma dei comuni e comuni delle particelle )
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param idPratica
	 * @return
	 */
	public List<Long> selectComuniParticellePratica(UUID idPratica,Long idFascicolo) {
		StringBuilder sql=new StringBuilder();
		List<Object> param=new ArrayList<>(); 
		sql
		.append(" SELECT DISTINCT id_comune FROM ")
		.append("(")
		.append("(SELECT id_comune  FROM ")
		.append(" \"procedimenti_ambientali\".\"pratica_localizzazione_particelle\" ")
		.append(" WHERE ")
		.append(" id_pratica::text = ? )")
		.append(" UNION ")
		.append("(SELECT id_comune  FROM ")
		.append(" \"procedimenti_ambientali\".\"pratica_localizzazione_comune\" ")
		.append(" WHERE ")
		.append(" id::text = ? )")
		.append(") as comuni_ambiente")
		.append(" WHERE ")
		.append(" id_comune in (SELECT ente_id from \"comuni_competenza\" c where c.pratica_id = ?) ");
		param.add(idPratica.toString());
		param.add(idPratica.toString());
		param.add(idFascicolo);
		List<Long> ret = super.queryForList(sql.toString(),new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("id_comune");
			}
			
		},param);
		return ret;
	}
	
	public List<SportelloAmbienteParticelleDTO> selectParticellePratica(UUID idPratica,Long idComune) {
		StringBuilder sql=new StringBuilder();
		List<Object> param=new ArrayList<>(); 
		sql.append("SELECT ")
		.append(" id_comune,nome_comune,sezione,nome_sezione,foglio,particella,note,\"oid\",sub ")
		.append("  FROM ")
		.append(" \"procedimenti_ambientali\".\"pratica_localizzazione_particelle\" ")
		.append(" WHERE ")
		.append(" id_pratica::text = ? ")
		.append(" AND id_comune = ? ");
		param.add(idPratica.toString());
		param.add(idComune);
		List<SportelloAmbienteParticelleDTO> ret = super.queryForList(sql.toString(),new RowMapper<SportelloAmbienteParticelleDTO>() {

			@Override
			public SportelloAmbienteParticelleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SportelloAmbienteParticelleDTO ret=new SportelloAmbienteParticelleDTO();
				ret.setIdComune(rs.getLong("id_comune"));
				ret.setNomeComune(rs.getString("nome_comune"));
				ret.setSezione(rs.getString("sezione"));
				ret.setNomeSezione(rs.getString("nome_sezione"));
				ret.setFoglio(rs.getString("foglio"));
				ret.setParticella(rs.getString("particella"));
				ret.setNote(rs.getString("note"));
				ret.setSub(rs.getString("sub"));
				ret.setOid(rs.getLong("oid"));
				return ret;
			}
			
		},param);
		return ret;
	}

	/**
	 * effettua migrazione delle geometrie dalla procedimenti_ambientali.layer_geo verso la layer_geo e inserisce in una mappa oid,newOid 
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param codicePraticaAmbiente
	 * @param idFascicolo
	 * @param username
	 * @return mappa oldOid,newOid
	 */
	public Map<Long,Long> migrateLayerGeo(String codicePraticaAmbiente,Long idFascicolo,String username) {
		StringBuilder sql=new StringBuilder();
		Map<Long,Long> mappaOid=new HashMap<>();
		List<Object> param=new ArrayList<>(); 
		sql.append("SELECT ")
		.append(" l.\"oid\" ")
		.append("  FROM ")
		.append(" \"procedimenti_ambientali\".\"layer_geo\" l, \"procedimenti_ambientali\".\"pratica\" p ")
		.append(" WHERE ")
		.append(" l.codice_pratica = p.codice AND p.codice_trasmissione = ? ");
		param.add(codicePraticaAmbiente);
		List<Long> ret = super.queryForList(sql.toString(),new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("oid");
			}
			
		},param);
		if(ListUtil.isNotEmpty(ret)) {
			//effettuo insert in mia layer_geo
			StringBuilder sqlInsert =new StringBuilder();
			sqlInsert.append("INSERT INTO layer_geo")
			.append(" (id_fascicolo,user_id,date_created,is_custom,is_particella,geometry) ")
			.append(" SELECT ?,?,lg.create_date,null,null,public.st_force3d(lg.geometry)  ")
			.append("  FROM ")
			.append(" \"procedimenti_ambientali\".\"layer_geo\" lg ")
			.append(" WHERE ")
			.append("  lg.\"oid\" = ? ");
			for(Long oid:ret) {
				List<Object> paramInsert=new ArrayList<>();
				paramInsert.add(idFascicolo);
				paramInsert.add(username);
				paramInsert.add(oid);
				long oidRet = this.insertAndGetKey(sqlInsert.toString(), paramInsert, "oid");
				mappaOid.put(oid,oidRet);
			}
		}
		return mappaOid;
	}
	
		
	private long insertAndGetKey(String sql, List<Object> parameters, String idColumn) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		super.jdbcTemplateWrite.update(new InsertPreparedStatementCreator(sql, parameters, idColumn),keyHolder);
		final long id = keyHolder.getKey().longValue();
		logger.info(StringUtil.concateneString("New Id: ", id));
		return id;
	}
	
	public List<SportelloAmbienteShapeDTO> selectShapeSportelloAmbiente(UUID idPratica) {
		StringBuilder sql=new StringBuilder();
		List<Object> param=new ArrayList<>();
		sql.append("SELECT pdp.id")
		.append(",pdp.nome_file name_file ")
		.append(",pdp.cmis_id ")
		.append(",pdp.upload_time date_create ")
		.append(",pdp.hash_file hash ") 
		.append(" FROM \"procedimenti_ambientali\".\"pratica_documentazione_progettuale\" pdp ," ) 
		.append(" \"procedimenti_ambientali\".\"tipo_documentazione_progettuale\" tdp " ) 
		.append(" WHERE pdp.id_tipo_documentazione =tdp.id " ) 
		.append(" AND tdp.shape =true " ) 
		.append(" AND pdp.id_pratica = ? ");
		param.add(idPratica.toString());
		List<SportelloAmbienteShapeDTO> ret = super.queryForList(sql.toString(),new RowMapper<SportelloAmbienteShapeDTO>() {

			@Override
			public SportelloAmbienteShapeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				SportelloAmbienteShapeDTO ret=new SportelloAmbienteShapeDTO();
				ret.setCmisId(rs.getString("cmis_id"));
				ret.setNameFile(rs.getString("name_file"));
				ret.setDateCreate(rs.getDate("date_create"));
				ret.setId(UUID.fromString(rs.getString("id")));
				ret.setHash(rs.getString("hash"));
				return ret;
			}
			
		},param);
		return ret;
	}

		
}