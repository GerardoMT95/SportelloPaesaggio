package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.AnagraficaDTO;

/**
 * Row Mapper for AnagraficaDTO class
 */
public class AnagraficaRowMapper implements RemoteRowMapper<AnagraficaDTO> {

    public AnagraficaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
    	AnagraficaDTO result = new AnagraficaDTO();
       	result.setId(rs.getInt("id"));
       	result.setIdRegione(rs.getInt("id_regione"));
       	result.setIdProvincia(rs.getInt("id_provincia"));
       	result.setNome(rs.getString("nome"));
       	result.setNumericIstatCode(rs.getString("cod_istat"));
       	result.setShapeLeng(rs.getLong("shape_leng"));
       	result.setShapeArea(rs.getLong("shape_area"));
       	result.setIstatCode(rs.getString("cod_catastale"));
        return result;
    }
}