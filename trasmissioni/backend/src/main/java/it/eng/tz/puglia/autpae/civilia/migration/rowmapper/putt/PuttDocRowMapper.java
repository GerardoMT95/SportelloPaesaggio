package it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.PuttDocBean;

public class PuttDocRowMapper implements RowMapper<PuttDocBean>
{
	@Override
	public PuttDocBean mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		PuttDocBean bean = null;
		if(rs != null)
		{
			bean = new PuttDocBean();
			try
			{				
				bean.setnTKeDocStID(Long.parseLong(rs.getString("N_t_ke_doc_st_ID")));
			} catch(NumberFormatException e) {}
			bean.settTipodocCodice(rs.getString("t_tipodoc_codice"));
			bean.settTipodocDescrizione(rs.getString("t_tipodoc_descrizione"));
			bean.setnName(rs.getString("NNAME"));
			bean.setAbinContent(rs.getBlob("abin_content"));
			bean.settKeDocAttrValue(rs.getString("t_ke_doc_attr_value"));
			bean.setNomeFile(rs.getString("nomefile"));
			bean.setIdAlfresco(rs.getString("id_alfresco"));
		}
		return bean;
	}
}
