/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.EnteParcoAss;



/**
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class EnteParcoAssRowMapper  implements RowMapper<EnteParcoAss>{

	@Override
	public EnteParcoAss mapRow(ResultSet rs, int rowNum) throws SQLException {
		EnteParcoAss result=new EnteParcoAss();
		if (rs.getObject("COD_CAT") != null) result.setCodCat(rs.getString("COD_CAT"));
		if (rs.getObject("CODICE_PRATICA") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA"));
		if (rs.getObject("COD_ISTAT") != null) result.setCodIstat(rs.getString("COD_ISTAT"));
		if (rs.getObject("COMUNE") != null) result.setComune(rs.getString("COMUNE"));
		if (rs.getObject("DESC_PARCO") != null) result.setDescParco(rs.getString("DESC_PARCO"));
		if (rs.getObject("TNO_ENTEPARCO_TIPOASS_ID") != null) result.setEnteParcoTipoAssId(rs.getLong("TNO_ENTEPARCO_TIPOASS_ID"));
		if (rs.getObject("MAIL") != null) result.setMail(rs.getString("MAIL"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("TNO_ENTEPARCO_MAP_ID") != null) result.setTnoEnteParcoMapId(rs.getLong("TNO_ENTEPARCO_MAP_ID"));
		return result;
	}
	

}
