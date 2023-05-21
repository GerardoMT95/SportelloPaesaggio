/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;


/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public class InfoAutPaesPptrAlfaRowMapper implements RowMapper<InfoAutPaesPptrAlfa>{

    public InfoAutPaesPptrAlfa mapRow(ResultSet rs, int rowNum) throws SQLException{
        final InfoAutPaesPptrAlfa result = new InfoAutPaesPptrAlfa();
        if (rs.getObject("codicePraticaAppptr") != null) result.setCodicePraticaAppptr(rs.getString("codicePraticaAppptr"));
        if (rs.getObject("codicePraticaEnteDelegato") != null) result.setCodicePraticaEnteDelegato(rs.getString("codicePraticaEnteDelegato"));
        if (rs.getObject("oggetto") != null) result.setOggetto(rs.getString("oggetto"));
        if (rs.getObject("responsabileProvvedimento") != null) result.setResponsabileProvvedimento(rs.getString("responsabileProvvedimento"));
        if (rs.getObject("numeroProvvedimento") != null) result.setNumeroProvvedimento(rs.getString("numeroProvvedimento"));
        
        if (rs.getObject("dataProvvedimento") != null) {
        	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyy");
        	Date date=null;
			try {
				date = sdf.parse(rs.getString("dataProvvedimento"));
			} catch (ParseException e) {}
        	result.setDataProvvedimento(date);	
        }
        if (rs.getObject("esitoProvvedimento") != null) result.setEsitoProvvedimento(rs.getString("esitoProvvedimento"));
        if (rs.getObject("dataTrasmissione") != null) result.setDataTrasmissione(rs.getDate("dataTrasmissione"));
        if (rs.getObject("istatAmm") != null) result.setIstatAmm(rs.getString("istatAmm"));
        if (rs.getObject("note") != null) result.setNote(rs.getString("note"));
        if (rs.getObject("progPubblicazione") != null) result.setProgPubblicazione(rs.getLong("progPubblicazione"));
        if (rs.getObject("tPraticaId") != null) result.settPraticaId(rs.getLong("tPraticaId"));
        if (rs.getObject("tPraticaAppptrId") != null) result.settPraticaAppptrId(rs.getLong("tPraticaAppptrId"));
        return result;
    }
}
