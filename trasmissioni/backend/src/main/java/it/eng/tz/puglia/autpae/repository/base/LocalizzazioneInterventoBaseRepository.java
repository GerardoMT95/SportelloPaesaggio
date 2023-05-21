package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.LocalizzazioneInterventoPK;
import it.eng.tz.puglia.autpae.search.LocalizzazioneInterventoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;

/**
 * Base Repository for table autpae.localizzazione_intervento
 */

public class LocalizzazioneInterventoBaseRepository	extends GenericCrudDao<LocalizzazioneInterventoDTO, LocalizzazioneInterventoSearch, LocalizzazioneInterventoPK> {

	private static final Logger log = LoggerFactory.getLogger(LocalizzazioneInterventoBaseRepository.class);

	@Override
	public List<LocalizzazioneInterventoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(LocalizzazioneInterventoSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public LocalizzazioneInterventoDTO find(LocalizzazioneInterventoPK pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public PaginatedList<LocalizzazioneInterventoDTO> search(LocalizzazioneInterventoSearch bean) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public LocalizzazioneInterventoPK insert(LocalizzazioneInterventoDTO entity) throws Exception {
		final String sql = new StringBuilder("insert into \"localizzazione_intervento\"")
				.append("(\"pratica_id\"").append(",\"comune_id\"").append(",\"indirizzo\"").append(",\"num_civico\"")
				.append(",\"piano\"").append(",\"interno\"").append(",\"dest_uso_att\"").append(",\"dest_uso_prog\"")
				.append(",\"comune\"").append(",\"sigla_provincia\"").append(",\"data_riferimento_catastale\"")
				.append(",\"strade\"").append(") values ").append("(?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		parameters.add(entity.getIndirizzo());
		parameters.add(entity.getNumCivico());
		parameters.add(entity.getPiano());
		parameters.add(entity.getInterno());
		parameters.add(entity.getDestUsoAtt());
		parameters.add(entity.getDestUsoProg());
		parameters.add(entity.getComune());
		parameters.add(entity.getSiglaProvincia()!=null ? entity.getSiglaProvincia().toUpperCase() : null);
		parameters.add(entity.getDataRiferimentoCatastale());
		parameters.add(entity.getStrade());
		super.update(sql, parameters);
		return new LocalizzazioneInterventoPK(entity.getPraticaId(), entity.getComuneId());
	}

	@Override
	public int update(LocalizzazioneInterventoDTO entity) throws Exception {
		final String sql = new StringBuilder("update \"localizzazione_intervento\"")
				.append(" set \"indirizzo\" = ?").append(", \"num_civico\" = ?").append(", \"piano\" = ?")
				.append(", \"interno\" = ?").append(", \"dest_uso_att\" = ?").append(", \"dest_uso_prog\" = ?")
				.append(", \"comune\" = ?").append(", \"sigla_provincia\" = ?")
				.append(", \"data_riferimento_catastale\" = ?").append(", \"strade\" = ?")
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIndirizzo());
		parameters.add(entity.getNumCivico());
		parameters.add(entity.getPiano());
		parameters.add(entity.getInterno());
		parameters.add(entity.getDestUsoAtt());
		parameters.add(entity.getDestUsoProg());
		parameters.add(entity.getComune());
		parameters.add(entity.getSiglaProvincia()!=null ? entity.getSiglaProvincia().toUpperCase() : null);
		parameters.add(entity.getDataRiferimentoCatastale());
		parameters.add(entity.getStrade());
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		return super.update(sql, parameters);
	}

	@Override
	public int delete(LocalizzazioneInterventoSearch entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
}