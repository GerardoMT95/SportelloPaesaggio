package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.TipologicaIntegerBooleanDto;

public class TipologicaIntegerBooleanRowMapper implements RowMapper<TipologicaIntegerBooleanDto>{

    public TipologicaIntegerBooleanDto mapRow(ResultSet rs, int rowNum) throws SQLException{
    	
        final TipologicaIntegerBooleanDto result = new TipologicaIntegerBooleanDto();
        
        if(rs.getObject("intero") != null)
        	result.setIntero(rs.getInt("intero"));
        
        if(rs.getObject("booleano") != null)
        	result.setBooleano(rs.getBoolean("booleano"));
        
        return result;
    }
}
