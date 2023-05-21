package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class TipoProcedimentoRowMapper implements RowMapper<TipoProcedimentoDTO> {

	public TipoProcedimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TipoProcedimentoDTO result = new TipoProcedimentoDTO();
		
		result.setCodice	 (rs.getObject(TipoProcedimento.codice	   .columnName()) != null ? rs.getString (TipoProcedimento.codice	  .columnName()) : null);
		result.setDescrizione(rs.getObject(TipoProcedimento.descrizione.columnName()) != null ? rs.getString (TipoProcedimento.descrizione.columnName()) : null);
		result.setInviaMail	 (rs.getObject(TipoProcedimento.invia_email.columnName()) != null ? rs.getBoolean(TipoProcedimento.invia_email.columnName()) : null);
		result.setSanatoria	 (rs.getObject(TipoProcedimento.sanatoria  .columnName()) != null ? rs.getBoolean(TipoProcedimento.sanatoria  .columnName()) : null);
		result.setInizioValidita(rs.getObject(TipoProcedimento.inizio_validita  .columnName()) != null ? rs.getDate(TipoProcedimento.inizio_validita  .columnName()) : null);
		result.setFineValidita(rs.getObject(TipoProcedimento.fine_validita  .columnName()) != null ? rs.getDate(TipoProcedimento.fine_validita  .columnName()) : null);
		result.setApplicativo(rs.getObject(TipoProcedimento.applicativo  .columnName()) != null ? rs.getString(TipoProcedimento.applicativo  .columnName()) : null);
		return result;
	}
   
}