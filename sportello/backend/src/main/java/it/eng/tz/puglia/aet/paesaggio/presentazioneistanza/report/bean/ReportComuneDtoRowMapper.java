package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReportComuneDtoRowMapper implements RowMapper<ReportComuneDto> {
	public ReportComuneDto mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ReportComuneDto result = new ReportComuneDto();
        result.setNome(rs.getString("nome"));
        result.setCount(rs.getLong("count"));
        return result;
    }
}
