package it.eng.tz.puglia.servizi_esterni.remote.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.CommonRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.EnteRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table common.ente
 */
@Repository
public class EnteRepository extends RemoteRepository<EnteDTO> 
{
	private final Logger logger = LoggerFactory.getLogger(EnteRepository.class);
	
	@Value("${ente_attribute.codice.applicazione}")
	private String codicaApplicazioneRubricaIstituzionale;
	
	@Autowired
	@Qualifier("jdbcTemplate_common")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	protected CommonRowMapper<EnteDTO> getRowMapper() {
		return new EnteRowMapper();
	}

	@Override
	protected String getQueryForSelectAll() {
		return StringUtil.concateneString("SELECT ea.\"pec\" pec, ea.\"mail\" mail, e.\"codice\" codice, ",
				"e.* ",
				"FROM \"ente\" e ",
				"INNER JOIN \"ente_attribute\" ea ON ea.\"ente_id\" = e.\"id\" ",
				"INNER JOIN \"applicazione\" a ON a.\"id\" = ea.\"applicazione_id\" ",
				"WHERE a.\"codice\" = '"+codicaApplicazioneRubricaIstituzionale.toUpperCase()+"' ");
	}

	/**
	 * find by pk method
	 */
	@Override
	protected String getQueryForFind(String codice) {
		return StringUtil.concateneString("SELECT ea.\"pec\" pec, ea.\"mail\" mail, e.\"codice\" codice, ",
				"e.* ", "FROM \"ente\" e ",
				"INNER JOIN \"ente_attribute\" ea ON ea.\"ente_id\" = e.\"id\" ",
				"INNER JOIN \"applicazione\" a ON a.\"id\" = ea.\"applicazione_id\" ",
				"WHERE a.\"codice\" = 'AUTPAE' AND e.\"codice\" = ? ");
	}

	/**
	 * findAll by pks method
	 */
	@Override
	protected String getQueryForFindAll(List<String> codices) {
		return StringUtil.concateneString("SELECT ea.\"pec\" pec, ea.\"mail\" mail, e.\"codice\" codice, ",
				"e.* ", "FROM \"ente\" e ",
				"INNER JOIN \"ente_attribute\" ea ON ea.\"ente_id\" = e.\"id\" ",
				"INNER JOIN \"applicazione\" a ON a.\"id\" = ea.\"applicazione_id\" ",
				"WHERE a.\"codice\" = '"+codicaApplicazioneRubricaIstituzionale+"' AND e.\"codice\" IN (",
				codices.stream().collect(Collectors.joining("', '", "'", "'")), ") ");
	}
	
//	private String getQueryForInsertEnteAttribute() {
//		return StringUtil.concateneString("INSERT INTO \"ente_attribute\"(\"applicazione_id\",\"ente_id\",\"pec\") " + 
//				"SELECT * FROM " ,
//				"(SELECT " , 
//				"(SELECT a.\"id\" FROM \"applicazione\" a WHERE a.\"codice\"='"+props.getCodiceApplicazioneProfile()+"') a_id," , 
//				"(SELECT e.\"id\" FROM \"ente\" e WHERE e.\"codice\"=?) e_id, ",
//				"(SELECT e.\"pec\" FROM \"ente\" e WHERE e.\"codice\"=?) e_pec ", 
//				") AS " ,
//				"\"PIPPO\" " ,
//				" WHERE NOT EXISTS ", 
//				" (SELECT 1 FROM \"ente_attribute\" ea WHERE " , 
//				" ea.\"applicazione_id\"=\"PIPPO\".\"a_id\" AND ea.\"ente_id\"=\"PIPPO\".\"e_id\")");
//	}
	
//	public int insertEnteAttribute(String codiceEnte) {
//		int ret=0;
//		if(!getQueryForInsertEnteAttribute().isEmpty()) {
//			ret=this.jdbcTemplate.update(getQueryForInsertEnteAttribute(),codiceEnte,codiceEnte);
//		}
//		return ret;
//	}
	
	/**
	 * Metodo che resituisce tutti i comuni e le province della regione puglia
	 * @return lista di province e comuni della regione puglia
	 * @throws Exception
	 */
	public List<EnteDTO> findComuniEProvince() throws Exception
	{
		/**
		 * Query riconrsiva per ottenere tutte le province ed i comuni appartenenti alla regione puglia
		 * 	with recursive pugliaP_CO as 
			(
				select * 
				from "common"."ente"
				where "ente"."tipo" in ('CO', 'P')
					and "ente"."parent_id" = 0
				
				union
				
				select "common"."ente".*
				from "common"."ente"
				join pugliaP_CO 
					on pugliaP_CO."id" = "ente"."parent_id"
				where "ente"."tipo" in ('CO', 'P')
			)
			select * 
			from pugliaP_CO
		 */
		String sql = "with recursive pugliaP_CO as ( select * from \"common\".\"ente\" where \"ente\".\"tipo\" in (:tipo) and \"ente\".\"parent_id\" = :parent union select \"common\".\"ente\".* from \"common\".\"ente\" join pugliaP_CO on pugliaP_CO.\"id\" = \"ente\".\"parent_id\" where \"ente\".\"tipo\" in ('CO', 'P') ) select * from pugliaP_CO";
		//String sql = "with recursive pugliaP_CO as ( select * from \"ente\" where \"ente\".\"tipo\" in (:tipo) and \"ente\".\"parent_id\" = :parent union select \"ente\".* from \"ente\" join pugliaP_CO on pugliaP_CO.\"id\" = \"ente\".\"parent_id\" where \"ente\".\"tipo\" in ('CO', 'P') ) select * from pugliaP_CO";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo", Arrays.asList("CO", "P"));
		parameters.put("parent", 0);
		logger.info("Eseguo la query {} con i seguenti paraemtri {}", sql, parameters);
		List<EnteDTO> ret = namedJdbcTemplate.query(sql, parameters, getRowMapper());
		return ret;
	}
	
	public List<EnteDTO> getFromCodici(List<String> codici) throws Exception
	{
		StringBuilder sql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<String, Object>();
		sql.append("select * from ente where codice in (:codici)");
		parameters.put("codici", (codici!=null && codici.isEmpty()) ? null : codici);
		return namedJdbcTemplate.query(sql.toString(), parameters, new EnteRowMapper());
	}
		
	@Override
	protected JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}
}