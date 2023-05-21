package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RuoloPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.IntegrazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PraticaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.RuoloPraticaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.IntegrazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.integrazione
 */
@Repository
public class RuoloPraticaRepository extends GenericDao
{
	private final RuoloPraticaRowMapper rowMapper = new RuoloPraticaRowMapper();
	
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select")
			.append(" \"id\"")
			.append(",\"descrizione\"")
			.append(",\"delegato\"")
			.append(" from \"presentazione_istanza\".\"ruolo_pratica\"").toString();

	
	public List<RuoloPraticaDTO> select()
	{
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	
}
