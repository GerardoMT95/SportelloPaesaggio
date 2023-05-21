/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.AutPaesPptrLocalizInterv;




/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class AutPaesPptrLocalizIntervRowMapper implements RowMapper<AutPaesPptrLocalizInterv>{

	@Override
	public AutPaesPptrLocalizInterv mapRow(ResultSet rs, int rowNum) throws SQLException {
		AutPaesPptrLocalizInterv result=new AutPaesPptrLocalizInterv();
		 if (rs.getObject("LOCALIZ_INTERV_COMUNE_ID") != null) result.setLocalizIntervComuneId(rs.getLong("LOCALIZ_INTERV_COMUNE_ID"));
		 if (rs.getObject("LOCALIZ_INTERV_ID") != null) result.setLocalizIntervId(rs.getLong("LOCALIZ_INTERV_ID"));
		 if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		 if (rs.getObject("COMUNE") != null) result.setComune(rs.getString("COMUNE"));
		 if (rs.getObject("DATA_RIFERIMENTO_CATASTALE") != null) result.setDataRiferimentoCatastale(rs.getString("DATA_RIFERIMENTO_CATASTALE"));
		 if (rs.getObject("ELABORATO") != null) result.setElaborato(rs.getLong("ELABORATO"));
		 if (rs.getObject("LOCALIZ_INTERV_DEST_USO_ATT") != null) result.setLocalizIntervDestUsoAtt(rs.getString("LOCALIZ_INTERV_DEST_USO_ATT"));
		 if (rs.getObject("LOCALIZ_INTERV_DEST_USO_PROG") != null) result.setLocalizIntervDestUsoProg(rs.getString("LOCALIZ_INTERV_DEST_USO_PROG"));
		 if (rs.getObject("LOCALIZ_INTERV_INDIRIZZO") != null) result.setLocalizIntervIndirizzo(rs.getString("LOCALIZ_INTERV_INDIRIZZO"));
		 if (rs.getObject("LOCALIZ_INTERV_INTERNO") != null) result.setLocalizIntervInterno(rs.getString("LOCALIZ_INTERV_INTERNO"));
		 if (rs.getObject("LOCALIZ_INTERV_NCIV") != null) result.setLocalizIntervNciv(rs.getString("LOCALIZ_INTERV_NCIV"));
		 if (rs.getObject("LOCALIZ_INTERV_PIANO") != null) result.setLocalizIntervPiano(rs.getString("LOCALIZ_INTERV_PIANO"));
		 if (rs.getObject("LOCALIZ_INTERV_PROV") != null) result.setLocalizIntervProvincia(rs.getString("LOCALIZ_INTERV_PROV"));
		 if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		 if (rs.getObject("STRADE") != null) result.setStrade(rs.getString("STRADE"));
		 if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaApptrId(rs.getLong("T_PRATICA_APPTR_ID"));
		return result;
	}

}
