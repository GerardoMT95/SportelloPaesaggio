package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsTipoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoCdsTipoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.util.uuid.UuidUtil;

@Repository
public class TipoCdsTipoRepository extends GenericCrudDao<TipoCdsTipoDTO, TipoCdsTipoSearch>{

	@Autowired
	private UserUtil userUtil;
	
	@Override
	public List<TipoCdsTipoDTO> select() {
		// TODO
		return null;
	}

	@Override
	public long count() {
		// TODO
		return 0;
	}

	@Override
	public TipoCdsTipoDTO find(TipoCdsTipoDTO pk) {
		// TODO
		return null;
	}

	@Override
	public long insert(TipoCdsTipoDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"tipo_cds_tipo\"")
				.append("(\"id\"")     
                .append(",\"id_tipo_procedimento\"")
                .append(",\"id_organizzazione\"")
                .append(",\"tipo\"")
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
		parameters.add(entity.getTipo());
		parameters.add(entity.getStartDate());
		parameters.add(entity.getUserCreate());
		return super.update(sql, parameters);
	}

	@Override
	public int update(TipoCdsTipoDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public int delete(TipoCdsTipoDTO entity) {
		// TODO
		return 0;
	}

	@Override
	public PaginatedList<TipoCdsTipoDTO> search(TipoCdsTipoSearch bean) {
		// TODO
		return null;
	}
	
	public void endConfigurazioniCdsTipo(final Integer tipoProcedimento){
        final String sql = new StringBuilder(
        		StringUtil.concateneString("update presentazione_istanza.tipo_cds_tipo t ",
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
