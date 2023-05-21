package it.eng.tz.puglia.autpae.rowmapper.custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dbMapping.Richiedente;
import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.dto.AllegatoDiogeneInfoDTO;


public class AllegatoDiogeneInfoRowMapper implements RowMapper<AllegatoDiogeneInfoDTO>
{

	@Override
	public AllegatoDiogeneInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		AllegatoDiogeneInfoDTO dto = new AllegatoDiogeneInfoDTO();
		if(rs != null)
		{
			dto.setId(rs.getObject(Allegato.id.columnName()) != null ? rs.getLong(Allegato.id.columnName()) : null);
			dto.setNome(rs.getObject(Allegato.nome.columnName()) != null ? rs.getString(Allegato.nome.columnName()) : null);
			dto.setContenuto(rs.getObject(Allegato.contenuto.columnName()) != null ? rs.getString(Allegato.contenuto.columnName()) : null);
			dto.setNumeroProtocolloIn(rs.getObject(Allegato.numero_protocollo_in.columnName()) != null ? rs.getString(Allegato.numero_protocollo_in.columnName()) : null);
			dto.setDataProtocolloIn(rs.getObject(Allegato.data_protocollo_in.columnName()) != null ? rs.getDate(Allegato.data_protocollo_in.columnName()) : null);
			dto.setNumeroProtocolloout(rs.getObject(Allegato.numero_protocollo_out.columnName()) != null ? rs.getString(Allegato.numero_protocollo_out.columnName()) : null);
			dto.setDataProtocolloOut(rs.getObject(Allegato.data_protocollo_out.columnName()) != null ? rs.getDate(Allegato.data_protocollo_out.columnName()) : null);
			dto.setCodiceFascicolo(rs.getObject(Fascicolo.codice.columnName()) != null ? rs.getString(Fascicolo.codice.columnName()) : null);
			dto.setDataTrasmissione(rs.getObject(Fascicolo.data_trasmissione.columnName()) != null ? rs.getDate(Fascicolo.data_trasmissione.columnName()) : null);
			dto.setTypes(rs.getObject("types") != null ? rs.getString("types") : null);
			dto.setOrgCreazione(rs.getObject(Fascicolo.org_creazione.columnName()) != null ? rs.getString(Fascicolo.org_creazione.columnName()) : null);
		}
		return dto;
	}

}
