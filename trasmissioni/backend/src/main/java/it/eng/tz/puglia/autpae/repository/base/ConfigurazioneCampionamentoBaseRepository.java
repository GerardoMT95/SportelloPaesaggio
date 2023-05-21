package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneCampionamento;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.rowmapper.ConfigurazioneCampionamentoRowMapper;
import it.eng.tz.puglia.autpae.search.ConfigurazioneCampionamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table configurazione_campionamento
 */
@Repository
public class ConfigurazioneCampionamentoBaseRepository extends GenericCrudDao<ConfigurazioneCampionamentoDTO, ConfigurazioneCampionamentoSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneCampionamentoBaseRepository.class);
	private ConfigurazioneCampionamentoRowMapper rowMapper = new ConfigurazioneCampionamentoRowMapper();
	
	@Override
	public List<ConfigurazioneCampionamentoDTO> select() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder("select * from ").append(ConfigurazioneCampionamento.getTableName());
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), null);
		List<ConfigurazioneCampionamentoDTO> lista = super.queryForList(sql.toString(), rowMapper);
		
		if (lista==null || lista.isEmpty()) {
			log.error("Errore. Nessuna configurazione per il Campionamento trovata!");
			throw new Exception("Errore. Nessuna configurazione per il Campionamento trovata!");
		}
		else if (lista.size()!=1) {
			log.error("Errore. Trovate "+lista.size()+" diverse configurazioni per il Campionamento!");
			throw new Exception("Errore. Trovate "+lista.size()+" diverse configurazioni per il Campionamento!");	
		}
		return lista;
	}

	@Override
	public long count(ConfigurazioneCampionamentoSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ConfigurazioneCampionamentoDTO find(Long pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public Long insert(ConfigurazioneCampionamentoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(ConfigurazioneCampionamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder("update ")
								.append(ConfigurazioneCampionamento.getTableName())
								.append(" set ")
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.campionamento_attivo	   		.columnName(), " = :", ConfigurazioneCampionamento.campionamento_attivo	  		.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.intervallo_campionamento		.columnName(), " = :", ConfigurazioneCampionamento.intervallo_campionamento		.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.tipo_campionamento_successivo.columnName(), " = :", ConfigurazioneCampionamento.tipo_campionamento_successivo.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.percentuale_istanze	   		.columnName(), " = :", ConfigurazioneCampionamento.percentuale_istanze	  		.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.giorni_notifica_campionamento.columnName(), " = :", ConfigurazioneCampionamento.giorni_notifica_campionamento.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.giorni_notifica_verifica	    .columnName(), " = :", ConfigurazioneCampionamento.giorni_notifica_verifica	  	.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.intervallo_verifica			.columnName(), " = :", ConfigurazioneCampionamento.intervallo_verifica	  		.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.applica_in_corso   	   		.columnName(), " = :", ConfigurazioneCampionamento.applica_in_corso         	.columnName(), ","))
								.append(StringUtil.concateneString(ConfigurazioneCampionamento.esito_pubblico          		.columnName(), " = :", ConfigurazioneCampionamento.esito_pubblico          		.columnName()     ));
					  //		.append(" where ")
					  //		.append(ConfigurazioneCampionamento.id.getCompleteName())
					  //		.append(" = :")
					  //		.append(ConfigurazioneCampionamento.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ConfigurazioneCampionamento.campionamento_attivo    		.columnName(), entity.isCampionamentoAttivo());
		parameters.put(ConfigurazioneCampionamento.intervallo_campionamento		.columnName(), entity.getIntervalloCampionamento());
		parameters.put(ConfigurazioneCampionamento.tipo_campionamento_successivo.columnName(), entity.getTipoCampionamentoSuccessivo().name());
		parameters.put(ConfigurazioneCampionamento.percentuale_istanze     		.columnName(), entity.getPercentualeIstanze());
		parameters.put(ConfigurazioneCampionamento.giorni_notifica_campionamento.columnName(), entity.getGiorniNotificaCampionamento());
		parameters.put(ConfigurazioneCampionamento.giorni_notifica_verifica     .columnName(), entity.getGiorniNotificaVerifica());
		parameters.put(ConfigurazioneCampionamento.intervallo_verifica     		.columnName(), entity.getIntervalloVerifica());
		parameters.put(ConfigurazioneCampionamento.applica_in_corso  	   		.columnName(), entity.isApplicaInCorso());
		parameters.put(ConfigurazioneCampionamento.esito_pubblico  		   		.columnName(), entity.isEsitoPubblico());
	//	parameters.put(ConfigurazioneCampionamento.id.columnName(), entity.getId());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), entity);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public int delete(ConfigurazioneCampionamentoSearch filters) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<ConfigurazioneCampionamentoDTO> search(ConfigurazioneCampionamentoSearch filters) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

}
