package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReportOneriIstruttoriRowMapper implements RowMapper<ReportOneriIstruttoriDto>{

    public ReportOneriIstruttoriDto mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ReportOneriIstruttoriDto result = new ReportOneriIstruttoriDto();
        result.setTipoProcedimento(rs.getString("tipoProcedimento"));
        result.setOnere(rs.getDouble("onere"));
        return result;
    }
}