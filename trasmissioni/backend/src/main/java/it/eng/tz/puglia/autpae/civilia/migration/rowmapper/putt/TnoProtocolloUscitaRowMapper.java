package it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.TnoProtocolloUscita;

public class TnoProtocolloUscitaRowMapper implements RowMapper<TnoProtocolloUscita>
{

	@Override
	public TnoProtocolloUscita mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		TnoProtocolloUscita result = null;
		if(rs != null)
		{
			result = new TnoProtocolloUscita();
			result.setBinPdfProtContent(rs.getBlob("bin_pdfprot_content"));
			result.setBinPdfContent(rs.getBlob("bin_pdf_content"));
			result.setCodicePratica(rs.getString("codice_pratica"));
			result.setDataProtocollo(rs.getDate("data_protocollo"));
			result.setNumeroProtocollo(rs.getString("numero_protocollo"));
			result.setTitolarioProtocollo(rs.getString("titolario_protocollo"));
			result.settKeDocStId(rs.getString("t_ke_doc_st_id"));
			result.setEsitoProtocollazione(rs.getString("esito_protocollazione"));
		}
		return result;
	}

}
