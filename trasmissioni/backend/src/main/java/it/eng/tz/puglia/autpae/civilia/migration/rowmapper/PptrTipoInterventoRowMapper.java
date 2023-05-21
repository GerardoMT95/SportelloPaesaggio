/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrTipoIntervento;

/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrTipoInterventoRowMapper implements RowMapper<PptrTipoIntervento> {

	@Override
	public PptrTipoIntervento mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrTipoIntervento result=new PptrTipoIntervento();
		if (rs.getObject("CONFORM_DATA_APPROV_REG_EDIL") != null) result.setConformDataApprovRegEdil(rs.getDate("CONFORM_DATA_APPROV_REG_EDIL"));
		if (rs.getObject("CONFORM_INTERV_ARTT_REG_EDIL") != null) result.setConformIntervArttRegEdil(rs.getString("CONFORM_INTERV_ARTT_REG_EDIL"));
		if (rs.getObject("INTERVENTI_NO_EDIL") != null) result.setInterventiNoEdil(rs.getString("INTERVENTI_NO_EDIL"));
		if (rs.getObject("MANUTENZIONE") != null) result.setManutenzione(rs.getString("MANUTENZIONE"));
		if (rs.getObject("NUOVA_COSTRUZIONE") != null) result.setNuovaCostruzione(rs.getString("NUOVA_COSTRUZIONE"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("RIST_EDILIZIA") != null) result.setRistEdilizia(rs.getString("RIST_EDILIZIA"));
		if (rs.getObject("RIST_URBANISTICA") != null) result.setRistUrbanistica(rs.getString("RIST_URBANISTICA"));
		if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaApptrId(rs.getLong("T_PRATICA_APPTR_ID"));
		if (rs.getObject("CONFORM_ATTO_APPROV_REG_EDIL") != null) result.setConformAttoApprovRegEdil(rs.getString("CONFORM_ATTO_APPROV_REG_EDIL"));
        return result;
	}

}
