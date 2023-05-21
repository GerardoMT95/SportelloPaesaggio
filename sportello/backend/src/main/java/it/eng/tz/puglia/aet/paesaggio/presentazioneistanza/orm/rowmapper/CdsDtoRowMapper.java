package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsDto;

public class CdsDtoRowMapper implements RowMapper<CdsDto>{
    public CdsDto mapRow(ResultSet rs, int rowNum) throws SQLException{
    	final CdsDto result = new CdsDto();
        result.setId(rs.getLong("id"));
        if (rs.getObject("riferimento_istanza") != null)
            result.setRiferimentoIstanza(rs.getString("riferimento_istanza"));
        if (rs.getObject("stato") != null)
            result.setStato(rs.getString("stato"));
    	return result;
    }
}
