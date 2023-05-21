package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;

/**
 * Row Mapper for table autpae.particelle_catastali
 */
public class ParticelleCatastaliRowMapper implements RowMapper<ParticelleCatastaliDTO>{

    public ParticelleCatastaliDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ParticelleCatastaliDTO result = new ParticelleCatastaliDTO();
        if(checkColumn("pratica_id", rs))
        	result.setPraticaId(rs.getLong("pratica_id"));
        if(checkColumn("comune_id", rs))
        	result.setComuneId (rs.getLong( "comune_id"));
        if(checkColumn("id", rs))
        	result.setId(rs.getInt("id"));
        if (checkColumn("livello", rs))
            result.setLivello(rs.getString("livello"));
        if (checkColumn("sezione", rs))
            result.setSezione(rs.getString("sezione"));
        if (checkColumn("foglio", rs))
            result.setFoglio(rs.getString("foglio"));
        if (checkColumn("particella", rs))
            result.setParticella(rs.getString("particella"));
        if (checkColumn("sub", rs))
            result.setSub(rs.getString("sub"));
        if (checkColumn("cod_cat", rs))
            result.setCodCat(rs.getString("cod_cat"));
        if (checkColumn("oid", rs))
            result.setOid(rs.getLong("oid"));
        if (checkColumn("note", rs))
            result.setNote(rs.getString("note"));
        if (checkColumn("descr_sezione", rs))
            result.setDescrSezione(rs.getString("descr_sezione"));
        if(checkColumn("tipo_selezione_localizzazione", rs))
        	result.setTipoSelezione(rs.getString("tipo_selezione_localizzazione"));
        if(checkColumn("stato", rs))
        	result.setStato(rs.getString("stato"));
        return result;
    }
    
    //Controllo se le due colonne sono presenti o meno nella chiamata
    public boolean checkColumn(String row, ResultSet rs){
    	try {
        	rs.getObject(row);
        	if(rs.getObject(row) != null)
        		return true;
        	else
        		return false;
    	} catch (Exception e) {
    		return false;
    	}
    }
}
