/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrPratica;



/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class AutPaesPptrPraticaRowMapper implements RowMapper<AutPaesPptrPratica>{

	@Override
	public AutPaesPptrPratica mapRow(ResultSet rs, int rowNum) throws SQLException {
		AutPaesPptrPratica result=new AutPaesPptrPratica();
		if (rs.getObject("jbpId") != null) result.setJbpId(rs.getLong("jbpId"));
		if (rs.getObject("jbpUname") != null) result.setJbpUname(rs.getString("jbpUname"));
		if (rs.getObject("codicePraticaAppptr") != null) result.setCodicePraticaApPptr(rs.getString("codicePraticaAppptr"));
		if (rs.getObject("codicePraticaEnteDelegato") != null) result.setCodicePraticaEnteDelegato(rs.getString("codicePraticaEnteDelegato"));
		if (rs.getObject("tipoProcedimentoId") != null) result.setTipoProcedimentoId(rs.getLong("tipoProcedimentoId"));
		if (rs.getObject("tPraticaDescrizione") != null) result.settPraticaDescrizione(rs.getString("tPraticaDescrizione"));
		if (rs.getObject("enteDelegato") != null) result.setEnteDelegato(rs.getString("enteDelegato"));
		if (rs.getObject("ufficio") != null) result.setUfficio(rs.getString("ufficio"));
		if (rs.getObject("active") != null) result.setActive(rs.getString("active"));
		if (rs.getObject("soloTrasmissione") != null) result.setSoloTrasmissione(rs.getLong("soloTrasmissione"));
        if (rs.getObject("note") != null) result.setNote(rs.getString("note"));
        if (rs.getObject("provvedimentoInSanatoria") != null) result.setProvvedimentoInSanatoria(rs.getString("provvedimentoInSanatoria"));
        if (rs.getObject("prog") != null) result.setProg(rs.getLong("prog"));
		return result;
	}

}
