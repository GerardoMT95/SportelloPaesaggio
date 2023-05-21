/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrRicevutaIstanza;

/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrRicevutaIstanzaRowMapper implements RowMapper<PptrRicevutaIstanza> {

	@Override
	public PptrRicevutaIstanza mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrRicevutaIstanza result=new PptrRicevutaIstanza();
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("DATA_INVIO_PEC") != null) result.setDataInvioPec(rs.getDate("DATA_INVIO_PEC"));
		if (rs.getObject("DATA_SUBMIT") != null) result.setDataSubmit(rs.getDate("DATA_SUBMIT"));
		if (rs.getObject("ESITO") != null) result.setEsito(rs.getString("ESITO"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("TNO_PPTR_PROTOCOLLO_ID") != null) result.setTnoPptrProtocolloId(rs.getLong("TNO_PPTR_PROTOCOLLO_ID"));
		return result;
	}

}
