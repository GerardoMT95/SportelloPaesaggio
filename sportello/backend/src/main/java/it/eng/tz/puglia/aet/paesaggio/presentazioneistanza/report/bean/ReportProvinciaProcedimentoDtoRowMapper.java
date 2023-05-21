package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReportProvinciaProcedimentoDtoRowMapper implements RowMapper<ReportProvinciaProcedimentoDto> {
	public ReportProvinciaProcedimentoDto mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ReportProvinciaProcedimentoDto result = new ReportProvinciaProcedimentoDto();
        result.setNome(rs.getString("nome"));
        result.setProcedimento(rs.getString("procedimento"));
        result.setCount(rs.getLong("count"));
        return result;
    }
}
