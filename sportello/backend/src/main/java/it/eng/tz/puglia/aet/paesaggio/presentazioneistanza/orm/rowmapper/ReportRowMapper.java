package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReportDTO;

/**
 * Row Mapper for table presentazione_istanza.report
 */
public class ReportRowMapper implements RowMapper<ReportDTO>{

    public ReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ReportDTO result = new ReportDTO();
        if (isColumnPresent(rs,"id"))
        	result.setId(rs.getString("id"));
        if (isColumnPresent(rs,"tipo"))
        	result.setTipo(rs.getString("tipo"));
        if (isColumnPresent(rs,"parametri"))
        	result.setParametri(rs.getString("parametri"));
        if (isColumnPresent(rs,"username"))
        	result.setUsername(rs.getString("username"));
        if (isColumnPresent(rs,"path_file"))
            result.setPathFile(rs.getString("path_file"));
        if (isColumnPresent(rs,"file_name"))
            result.setFileName(rs.getString("file_name"));
        if (isColumnPresent(rs,"stato"))
        	result.setStato(rs.getString("stato"));
        if (isColumnPresent(rs,"dimensione_file"))
            result.setDimensioneFile(rs.getLong("dimensione_file"));
        if (isColumnPresent(rs,"data_richiesta"))
        	result.setDataRichiesta(rs.getTimestamp("data_richiesta"));
        if (isColumnPresent(rs,"data_avvio"))
            result.setDataAvvio(rs.getTimestamp("data_avvio"));
        if (isColumnPresent(rs,"data_creazione"))
            result.setDataCreazione(rs.getTimestamp("data_creazione"));
        if (isColumnPresent(rs,"data_scadenza"))
            result.setDataScadenza(rs.getTimestamp("data_scadenza"));
        if (isColumnPresent(rs,"hash"))
            result.setHash(rs.getString("hash"));
        if (isColumnPresent(rs,"stato_originale"))
            result.setStatoOriginale(rs.getString("stato_originale"));
        if (isColumnPresent(rs,"ente_delegato"))
            result.setEnteDelegato(rs.getInt("ente_delegato"));
        if (isColumnPresent(rs,"descrizione_ente"))
            result.setDescrizioneEnte(rs.getString("descrizione_ente"));
        if (isColumnPresent(rs,"mail"))
            result.setMail(rs.getString("mail"));
        return result;
    }
    
    private boolean isColumnPresent(ResultSet rs, String column) throws SQLException {
    	boolean present = false;
    	try {
        	if(rs.getObject(column) != null) {
        		present = true;
        	}
        	return present;
    	} catch (Exception e) {
    		return false;
    	}

    }
}
