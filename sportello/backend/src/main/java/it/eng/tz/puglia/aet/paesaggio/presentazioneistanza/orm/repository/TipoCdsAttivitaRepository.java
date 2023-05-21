package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsAttivitaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoCdsAttivitaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.util.uuid.UuidUtil;

@Repository
public class TipoCdsAttivitaRepository extends GenericCrudDao<TipoCdsAttivitaDTO, TipoCdsAttivitaSearch> {

	@Autowired
	private UserUtil userUtil;
	
	@Override
	public List<TipoCdsAttivitaDTO> select() {
		// TODO
		return null;
	}

	@Override
	public long count() {
		// TODO
		return 0;
	}

	@Override
	public TipoCdsAttivitaDTO find(TipoCdsAttivitaDTO pk) {
		// TODO
		return null;
	}

	@Override
	public long insert(TipoCdsAttivitaDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"tipo_cds_attivita\"")
				.append("(\"id\"")     
                .append(",\"id_tipo_procedimento\"")
                .append(",\"id_organizzazione\"")
                .append(",\"attivita\"")
                .append(",\"start_date\"")
                .append(",\"user_create\"")
                .append(") values ")
                .append("(?")
                .append(",?")
                .append(",?")
                .append(",?")
                .append(",?")
                .append(",?")
                .append(")")
                .toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(String.valueOf(UuidUtil.newUUID()));
		parameters.add(entity.getIdTipoProcedimento());
		parameters.add(entity.getIdOrganizzazione());
		parameters.add(entity.getAttivita());
		parameters.add(entity.getStartDate());
		parameters.add(entity.getUserCreate());
		return super.update(sql, parameters);
	}

	@Override
	public int update(TipoCdsAttivitaDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public int delete(TipoCdsAttivitaDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public PaginatedList<TipoCdsAttivitaDTO> search(TipoCdsAttivitaSearch bean) {
		// TODO
		return null;
	}
	
	public void endConfigurazioniCdsAttivita(final Integer tipoProcedimento){
        final String sql = new StringBuilder(
        		StringUtil.concateneString("update presentazione_istanza.tipo_cds_attivita t ",
                "SET end_date=? ", 
        		"WHERE id_tipo_procedimento=? and ",
        		"id_organizzazione=? and ", 
        		"end_date is null ")).toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(new Timestamp(System.currentTimeMillis()));
        parameters.add(tipoProcedimento);
        parameters.add(userUtil.getIntegerId());
        super.update(sql, parameters);
    }

}
