package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;

/**
 * Row Mapper for table autpae.comuni_competenza
 */
public class ComuniCompetenzaRowMapper implements RowMapper<ComuniCompetenzaDTO>{

    public ComuniCompetenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
    	
        final ComuniCompetenzaDTO result = new ComuniCompetenzaDTO();
        
        result.setPraticaId(rs.getLong("pratica_id"));
        
        result.setEnteId (rs.getLong( "ente_id"));
        
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        
        if (rs.getObject("cod_cat") != null)
            result.setCodCat(rs.getString("cod_cat"));
        
        if (rs.getObject("cod_istat") != null)
            result.setCodIstat(rs.getString("cod_istat"));
        
        if (rs.getObject("data_inserimento") != null)
            result.setDataInserimento(Date.from((rs.getObject("data_inserimento", LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()));
        
        if (rs.getObject("creazione") != null)
            result.setCreazione(rs.getBoolean("creazione"));
        
        if (rs.getObject("effettivo") != null)
            result.setEffettivo(rs.getBoolean("effettivo"));
        
        return result;
    }
}