package it.eng.tz.puglia.autpae.rowmapper.custom;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dbMapping.Richiedente;
import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;

public class LineaTemporaleRowMapper implements RowMapper<LineaTemporaleDTO>
{

	@Override
	public LineaTemporaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		LineaTemporaleDTO dto = new LineaTemporaleDTO();
		if(rs != null)
		{
			dto.setDataCreazione(rs.getObject(Fascicolo.data_creazione.columnName()) != null ? rs.getDate(Fascicolo.data_creazione.columnName()) : null);
			dto.setDataTrasmissione(rs.getObject(Fascicolo.data_trasmissione.columnName()) != null ? rs.getDate(Fascicolo.data_trasmissione.columnName()) : null);
			dto.setDataCampionamento(rs.getObject(Fascicolo.data_campionamento.columnName()) != null ? rs.getDate(Fascicolo.data_campionamento.columnName()) : null);
			dto.setDataVerifica(rs.getObject(Fascicolo.data_verifica.columnName()) != null ? rs.getDate(Fascicolo.data_verifica.columnName()) : null);
			dto.setRichiedenteNome(rs.getObject(Richiedente.nome.columnName()) != null ? rs.getString(Richiedente.nome.columnName()) : null);
			dto.setRichiedenteCognome(rs.getObject(Richiedente.cognome.columnName()) != null ? rs.getString(Richiedente.cognome.columnName()) : null);
			dto.setEnte(rs.getObject(Fascicolo.ufficio.columnName()) != null ? rs.getString(Fascicolo.ufficio.columnName()) : null);
			dto.setTipoProcedimento(rs.getObject(TipoProcedimento.descrizione.columnName()) != null ? rs.getString(TipoProcedimento.descrizione.columnName()) : null);
		}
		return dto;
	}

}
