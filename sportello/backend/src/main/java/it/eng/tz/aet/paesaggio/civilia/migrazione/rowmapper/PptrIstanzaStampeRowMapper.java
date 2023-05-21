package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrIstanzaStampe;

public class PptrIstanzaStampeRowMapper implements RowMapper<PptrIstanzaStampe>{

	@Override
	public PptrIstanzaStampe mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrIstanzaStampe result = new PptrIstanzaStampe();
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaApptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("DICHIARAZIONE_TECNICA") != null) result.setDichiarazioneTecnica(rs.getBlob("DICHIARAZIONE_TECNICA"));
		if (rs.getObject("DICHIARAZIONE_TECNICA_S") != null) result.setDichiarazioneTecnicaS(rs.getBlob("DICHIARAZIONE_TECNICA_S"));
		if (rs.getObject("ISTANZA") != null) result.setIstanza(rs.getBlob("ISTANZA"));
		if (rs.getObject("ISTANZA_S") != null) result.setIstanzaS(rs.getBlob("ISTANZA_S"));
		if (rs.getObject("DICHIARAZIONE_TECNICA_S_NAME") != null) result.setDichiarazioneTecnicaSname(rs.getString("DICHIARAZIONE_TECNICA_S_NAME"));
		if (rs.getObject("ISTANZA_S_NAME") != null) result.setIstanzaSname(rs.getString("ISTANZA_S_NAME"));
		return result;
	}

}
