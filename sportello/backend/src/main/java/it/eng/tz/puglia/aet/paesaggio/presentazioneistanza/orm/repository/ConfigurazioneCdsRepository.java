package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsAttivitaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsAzioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsTipoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.VCdsAttivitaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.VCdsAzioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.VCdsTipoRowMapper;

@Repository
public class ConfigurazioneCdsRepository extends GenericDao {
	
	@Autowired
	private UserUtil userUtil;
	private final static VCdsAttivitaRowMapper attivitaRowMapper = new VCdsAttivitaRowMapper();
	private final static VCdsTipoRowMapper tipoRowMapper = new VCdsTipoRowMapper();
	private final static VCdsAzioneRowMapper azioneRowMapper = new VCdsAzioneRowMapper();
	
	public List<SelectParentItemDto> getAttivitaList(String idProcedimento) {
		String sql = new StringBuilder("select")
			                .append(" \"v_cds_attivita\".\"value\"")
			                .append(",\"v_cds_attivita\".\"label\"")
			                .append("   from \"presentazione_istanza\".\"tipo_cds_attivita\"")
			                .append("   inner join \"presentazione_istanza\".\"v_cds_attivita\"")
			                .append("   on \"tipo_cds_attivita\".\"attivita\" = \"v_cds_attivita\".\"value\"")
			                .append("   where \"tipo_cds_attivita\".\"id_tipo_procedimento\" = :idProcedimento")
			                .append("   and \"tipo_cds_attivita\".\"start_date\" <= CURRENT_TIMESTAMP")
			                .append("   and (\"tipo_cds_attivita\".\"end_date\" >= CURRENT_TIMESTAMP OR \"tipo_cds_attivita\".\"end_date\" is null)")
			                .append("   and \"tipo_cds_attivita\".\"id_organizzazione\"=:idOrganizzazione")
			                .append("   order by \"v_cds_attivita\".\"label\" ASC")
			                .toString();
		
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("idProcedimento", Integer.parseInt(idProcedimento));
		parameters.put("idOrganizzazione", userUtil.getIntegerId());
		return super.namedJdbcTemplateRead.query(sql, parameters, attivitaRowMapper);
	}
	
	public List<SelectParentItemDto> getTipoList(String idProcedimento) {
		String sql = new StringBuilder("select")
                .append(" \"v_cds_tipo\".\"value\"")
                .append(",\"v_cds_tipo\".\"label\"")
                .append("   from \"presentazione_istanza\".\"tipo_cds_tipo\"")
                .append("   inner join \"presentazione_istanza\".\"v_cds_tipo\"")
                .append("   on \"tipo_cds_tipo\".\"tipo\" = \"v_cds_tipo\".\"value\"")
                .append("   where \"tipo_cds_tipo\".\"id_tipo_procedimento\" = :idProcedimento")
                .append("   and \"tipo_cds_tipo\".\"start_date\" <= CURRENT_TIMESTAMP")
                .append("   and (\"tipo_cds_tipo\".\"end_date\" >= CURRENT_TIMESTAMP OR \"tipo_cds_tipo\".\"end_date\" is null)")
                .append("   and \"tipo_cds_tipo\".\"id_organizzazione\"=:idOrganizzazione")
                .append("   order by \"v_cds_tipo\".\"label\" ASC")
                .toString();

		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("idProcedimento", Integer.parseInt(idProcedimento));
		parameters.put("idOrganizzazione", userUtil.getIntegerId());
		return super.namedJdbcTemplateRead.query(sql, parameters, tipoRowMapper);
		
	}
	
	public List<SelectParentItemDto> getAzioniList(String idProcedimento) {
		
		String sql = new StringBuilder("select")
                .append(" \"v_cds_azione\".\"value\"")
                .append(",CONCAT(\"v_cds_attivita\".\"label\", ' --- ', \"v_cds_azione\".\"label\") as label")
                .append(",\"v_cds_azione\".\"attivita\"")
                .append("   from \"presentazione_istanza\".\"tipo_cds_azione\"")
                .append("   inner join \"presentazione_istanza\".\"v_cds_azione\"")
                .append("   on \"tipo_cds_azione\".\"azione\" = \"v_cds_azione\".\"value\"")
                .append("   inner join \"presentazione_istanza\".\"v_cds_attivita\"")
                .append("   on \"v_cds_attivita\".\"value\" = \"v_cds_azione\".\"attivita\"")
                .append("   where \"tipo_cds_azione\".\"id_tipo_procedimento\" = :idProcedimento")
                .append("   and \"tipo_cds_azione\".\"start_date\" <= CURRENT_TIMESTAMP")
                .append("   and (\"tipo_cds_azione\".\"end_date\" >= CURRENT_TIMESTAMP OR \"tipo_cds_azione\".\"end_date\" is null)")
                .append("   and \"tipo_cds_azione\".\"id_organizzazione\"=:idOrganizzazione")
                .append("   order by \"v_cds_azione\".\"label\" ASC")
                .toString();

		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("idProcedimento", Integer.parseInt(idProcedimento));
		parameters.put("idOrganizzazione", userUtil.getIntegerId());
		return super.namedJdbcTemplateRead.query(sql, parameters, azioneRowMapper);
		
	}
	
}
