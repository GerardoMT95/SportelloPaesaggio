package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoObbligatorio;
import it.eng.tz.puglia.autpae.entity.AllegatoObbligatorioDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

/**
 * Row Mapper for table autpae.allegato_obbligatorio
 */
public class AllegatoObbligatorioRowMapper implements RowMapper<AllegatoObbligatorioDTO> {

	public AllegatoObbligatorioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AllegatoObbligatorioDTO result = new AllegatoObbligatorioDTO();

		
		result.setType(TipoAllegato.valueOf(rs.getString(AllegatoObbligatorio.type.columnName())));
		result.setCodiceTipoProcedimento(rs.getString(AllegatoObbligatorio.codice_tipo_procedimento.columnName()));
				
		return result;
	}
   
}
