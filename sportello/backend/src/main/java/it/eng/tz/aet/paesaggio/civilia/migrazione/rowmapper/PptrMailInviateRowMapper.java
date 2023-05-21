package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrMailInviate;

public class PptrMailInviateRowMapper implements RowMapper<PptrMailInviate>{

	@Override
	public PptrMailInviate mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrMailInviate result=new PptrMailInviate();
		//TNO_PPTR_MAIL_INVIATE_ID,CODICE_PRATICA_APPPTR,CC_TIME_STAMP,DESTINATARIO,OGGETTO,
		//TESTO,DATA_INVIO,MITTENTE,VERSO,MESSAGE_ID,EML,TNO_TIPO_COMUNICAZIONE_ID,TITOLO
		if (rs.getObject("TNO_PPTR_MAIL_INVIATE_ID") != null) result.setMailInviateId(rs.getLong("TNO_PPTR_MAIL_INVIATE_ID"));
		if (rs.getObject("CC_TIME_STAMP") != null) result.setCcTimeStamp(rs.getLong("CC_TIME_STAMP"));
		if (rs.getObject("DESTINATARIO") != null) result.setDestinatario(rs.getString("DESTINATARIO"));
		if (rs.getObject("OGGETTO") != null) result.setOggetto(rs.getString("OGGETTO"));
		if (rs.getObject("TESTO") != null) result.setTesto(rs.getString("TESTO"));
		if (rs.getObject("DATA_INVIO") != null) result.setDataInvio(rs.getDate("DATA_INVIO"));
		if (rs.getObject("MITTENTE") != null) result.setMittente(rs.getString("MITTENTE"));
		if (rs.getObject("VERSO") != null) result.setVerso(rs.getString("VERSO"));
		if (rs.getObject("MESSAGE_ID") != null) result.setMessageId(rs.getString("MESSAGE_ID"));
		if (rs.getObject("EML") != null) result.setEml(rs.getBlob("EML"));
		if (rs.getObject("TITOLO") != null) result.setTitolo(rs.getString("TITOLO"));
		return result;
	}
	

}
