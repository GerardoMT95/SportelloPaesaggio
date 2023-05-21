package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReportProvinciaDtoRowMapper implements RowMapper<ReportProvinciaDto> {
	public ReportProvinciaDto mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ReportProvinciaDto result = new ReportProvinciaDto();
        result.setNome(rs.getString("nome"));
        result.setCount(rs.getLong("count"));
        return result;
    }
}
