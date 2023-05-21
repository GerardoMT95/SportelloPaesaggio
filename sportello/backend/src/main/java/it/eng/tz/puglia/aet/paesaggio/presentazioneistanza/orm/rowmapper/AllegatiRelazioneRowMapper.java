package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
public class AllegatiRelazioneRowMapper implements RowMapper<AllegatiDTO>{

	final static AllegatiRowMapper allegatoRowMapper =new AllegatiRowMapper();
	
    public AllegatiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AllegatiDTO result = allegatoRowMapper.mapRow(rs, rowNum);;
        if (rs.getObject("type") != null)
    	{
    		List<String> tipi = new ArrayList<String>();
    		tipi.add(rs.getString("type"));
    		result.setTipiContenuto(tipi);
    	}
        return result;
    }
}
