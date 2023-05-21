/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMailAllegati;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class CiviliaMailAllegatiRowMapper implements RowMapper<CiviliaMailAllegati>{

	@Override
	public CiviliaMailAllegati mapRow(ResultSet rs, int rowNum) throws SQLException {
		CiviliaMailAllegati result=new CiviliaMailAllegati();
		if (rs.getObject("T_MAIL_INVIATE_ID") != null) result.settMailInviateId(rs.getLong("T_MAIL_INVIATE_ID"));
		if (rs.getObject("BIN_CONTENT") != null) result.setBinContent(rs.getBlob("BIN_CONTENT"));
		if (rs.getObject("CC_TIME_STAMP") != null) result.setCcTimeStamp(rs.getLong("CC_TIME_STAMP"));
		if (rs.getObject("NOME_FILE") != null) result.setNomeFile(rs.getString("NOME_FILE"));
		if (rs.getObject("T_KE_DOC_ID") != null) result.settKeDocId(rs.getLong("T_KE_DOC_ID"));
		if (rs.getObject("T_MAIL_INVIATE_ALLEGATI_ID") != null) result.settMailInviateAllegatiId(rs.getLong("T_MAIL_INVIATE_ALLEGATI_ID"));
		return result;
	}

	
}
