package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferenteFascicoloDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ReferenteFascicoloRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReferenteFascicoloSearch;
import it.eng.tz.puglia.bean.PaginatedList;

@Repository
public class ReferenteFascicoloRepository extends GenericCrudDao<ReferenteFascicoloDTO, ReferenteFascicoloSearch>{
	
	private final ReferenteFascicoloRowMapper rowMapper = new ReferenteFascicoloRowMapper();
	
    final String selectAll = new StringBuilder("select")
            .append(" \"id\"")
            .append(" ,\"nome\"")
            .append(" ,\"cognome\"")
            .append(" ,\"pec\"")
            .append(" ,\"mail\"")
            .append(" ,\"codice_fiscale\"")
            .append(" ,\"tipo_referente\"")
            .append(" from \"presentazione_istanza\".\"v_referente_fascicolo\"")
            .toString();

	@Override
	public List<ReferenteFascicoloDTO> select() {
        return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	@Override
	public long count() {
        final String sql = new StringBuilder("select count(*)")
                .append(" from \"presentazione_istanza\".\"v_referente_fascicolo\"")
                .toString();
        return super.queryForObjectTxRead(sql, Long.class);
	}

	@Override
	public ReferenteFascicoloDTO find(ReferenteFascicoloDTO pk) {
		final String sql = new StringBuilder(selectAll)
				.append(" where \"id\" = ?")
				.append(" limit 1")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public List<ReferenteFascicoloDTO> findByPratica(UUID idPratica) {
		final String sql = new StringBuilder(selectAll)
				.append(" where \"id\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return super.queryForListTxRead(sql, this.rowMapper, parameters);
	}


	@Override
	public long insert(ReferenteFascicoloDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public int update(ReferenteFascicoloDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public int delete(ReferenteFascicoloDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public PaginatedList<ReferenteFascicoloDTO> search(ReferenteFascicoloSearch bean) {
		// TODO
		return null;
	}

	/**
	 * Metodo che ritorna la lista dei referenti per una data pratica
	 * @author Giuseppe Canciello
	 * @date 30 giu 2022
	 * @param idPratica
	 * @return
	 */
	public List<ReferenteFascicoloDTO> searchByIdPRatica(UUID idPratica) {
		String sql="SELECT * FROM v_referente_fascicolo where"
				+ " id=?";
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return super.queryForListTxRead(sql, this.rowMapper, parameters);
	}

}
