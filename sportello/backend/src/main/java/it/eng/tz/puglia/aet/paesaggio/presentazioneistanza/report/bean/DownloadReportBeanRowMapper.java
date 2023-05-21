package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DownloadReportBeanRowMapper implements RowMapper<DownloadReportBean>{
	public DownloadReportBean mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DownloadReportBean result = new DownloadReportBean();
        result.setNomeFile(rs.getString("file_name"));
        result.setPathFile(rs.getString("path_file"));
        return result;
    }
}
