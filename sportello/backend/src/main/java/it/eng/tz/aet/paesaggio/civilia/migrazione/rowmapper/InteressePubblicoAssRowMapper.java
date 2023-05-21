/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.InteressePubblicoAss;



/**
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class InteressePubblicoAssRowMapper implements RowMapper<InteressePubblicoAss>{

	@Override
	public InteressePubblicoAss mapRow(ResultSet rs, int rowNum) throws SQLException {
		InteressePubblicoAss result=new InteressePubblicoAss();
		if (rs.getObject("COD_CAT") != null) result.setCodCat(rs.getString("COD_CAT"));
		if (rs.getObject("CODICE_PRATICA") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA"));
		if (rs.getObject("COD_ISTAT") != null) result.setCodIstat(rs.getString("COD_ISTAT"));
		if (rs.getObject("COMUNE") != null) result.setComune(rs.getString("COMUNE"));
		if (rs.getObject("COD_VINCOLO") != null) result.setCodiceVincolo(rs.getString("COD_VINCOLO"));
		if (rs.getObject("OGGETTO") != null) result.setOggetto(rs.getString("OGGETTO"));
		if (rs.getObject("TNO_INTERESSEPUB_TIPOASS_ID") != null) result.setInteressePubbTipoAssId(rs.getLong("TNO_INTERESSEPUB_TIPOASS_ID"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		return null;
	}

	
	
}
