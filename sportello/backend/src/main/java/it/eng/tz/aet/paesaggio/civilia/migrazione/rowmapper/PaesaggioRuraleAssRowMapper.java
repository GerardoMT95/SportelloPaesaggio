/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PaesaggioRuraleAss;



/**
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class PaesaggioRuraleAssRowMapper implements RowMapper<PaesaggioRuraleAss>{

	@Override
	public PaesaggioRuraleAss mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaesaggioRuraleAss result=new PaesaggioRuraleAss();
		if (rs.getObject("COD_CAT") != null) result.setCodCat(rs.getString("COD_CAT"));
		if (rs.getObject("CODICE_PRATICA") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA"));
		if (rs.getObject("COD_ISTAT") != null) result.setCodIstat(rs.getString("COD_ISTAT"));
		if (rs.getObject("COMUNE") != null) result.setComune(rs.getString("COMUNE"));
		if (rs.getObject("DESC_PAESAGGIO_RURALE") != null) result.setDescPaesaggioRurale(rs.getString("DESC_PAESAGGIO_RURALE"));
		if (rs.getObject("TNO_PAESAGGIRURALI_TIPOASS_ID") != null) result.setPaesaggioRuraleTipoAssId(rs.getLong("TNO_PAESAGGIRURALI_TIPOASS_ID"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("TNO_PAESAGGIRURALI_MAP_ID") != null) result.setTnoPaesaggiRuraliMapId(rs.getLong("TNO_PAESAGGIRURALI_MAP_ID"));
		
		return result;
	}
	
	

}
