package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittimita;

public class PptrLegittimitaRowMapper implements RowMapper<PptrLegittimita>{

	@Override
	public PptrLegittimita mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrLegittimita result=new PptrLegittimita();
		//LEGITTIMITA_ID,T_PRATICA_APPTR_ID,LEG_URB_TIT_EDILIZIO,LEG_URB_PRIVO_SPECIFICA,LEG_PAESAG_IMMOBILE,
		//LEG_PAE_TIPO_VINCOLO,LEG_PAE_DATA_INTERVENTO,LEG_PAE_DATA_VINCOLO,CODICE_PRATICA_APPPTR,PROG
		if (rs.getObject("LEG_URB_TIT_EDILIZIO") != null) result.setLegUrbTitEdilizio(rs.getString("LEG_URB_TIT_EDILIZIO"));
		if (rs.getObject("LEG_URB_PRIVO_SPECIFICA") != null) result.setLegUrbPrivoSpecifica(rs.getString("LEG_URB_PRIVO_SPECIFICA"));
		if (rs.getObject("LEG_PAESAG_IMMOBILE") != null) result.setLegPaesagImmobile(rs.getString("LEG_PAESAG_IMMOBILE"));
		if (rs.getObject("LEG_PAE_TIPO_VINCOLO") != null) result.setLegPaeTipoVincolo(rs.getString("LEG_PAE_TIPO_VINCOLO"));
		if (rs.getObject("LEG_PAE_DATA_INTERVENTO") != null) result.setLegPaeDataIntervento(rs.getDate("LEG_PAE_DATA_INTERVENTO"));
		if (rs.getObject("LEG_PAE_DATA_VINCOLO") != null) result.setLegPaeDataVincolo(rs.getDate("LEG_PAE_DATA_VINCOLO"));
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("LEGITTIMITA_ID") != null) result.setLegittimitaId(rs.getLong("LEGITTIMITA_ID"));
		return result;
	}

}
