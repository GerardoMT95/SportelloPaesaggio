package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloIstanza;

public class PptrProtocolloIstanzaRowMapper implements RowMapper<PptrProtocolloIstanza>{

	@Override
	public PptrProtocolloIstanza mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrProtocolloIstanza result = new PptrProtocolloIstanza();
		//NUMERO_PROTOCOLLO,DATA_PROTOCOLLO,TITOLARIO_PROTOCOLLO,DATA_INVIO_PEC,ESITO 
		if (rs.getObject("NUMERO_PROTOCOLLO") != null) result.setNumeroProtocollo(rs.getString("NUMERO_PROTOCOLLO"));
		if (rs.getObject("DATA_PROTOCOLLO") != null) result.setDataProtocollo(rs.getDate("DATA_PROTOCOLLO"));
		if (rs.getObject("TITOLARIO_PROTOCOLLO") != null) result.setTitolarioProtocollo(rs.getString("TITOLARIO_PROTOCOLLO"));
		if (rs.getObject("DATA_INVIO_PEC") != null) result.setDataInvioPec(rs.getDate("DATA_INVIO_PEC"));
		if (rs.getObject("ESITO") != null) result.setEsito(rs.getString("ESITO"));
		return result;
	}
	

}
