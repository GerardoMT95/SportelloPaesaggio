/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMail;



/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class CiviliaMailRowMapper implements RowMapper<CiviliaMail>{

	@Override
	public CiviliaMail mapRow(ResultSet rs, int rowNum) throws SQLException {
		CiviliaMail result=new CiviliaMail();
		if (rs.getObject("CC_TIME_STAMP") != null) result.setCcTimeStamp(rs.getLong("CC_TIME_STAMP"));
		if (rs.getObject("DATA_INVIO") != null) result.setDataInvio(rs.getDate("DATA_INVIO"));
		if (rs.getObject("DESTINATARIO") != null) result.setDestinatario(rs.getString("DESTINATARIO"));
		if (rs.getObject("FLG_ELIMINATO") != null) result.setFlgEliminato(rs.getLong("FLG_ELIMINATO"));
		if (rs.getObject("IS_PEC") != null) result.setIsPec(rs.getString("IS_PEC"));
		if (rs.getObject("MESSAGE_ID") != null) result.setMessageId(rs.getString("MESSAGE_ID"));
		if (rs.getObject("MITTENTE") != null) result.setMittente(rs.getString("MITTENTE"));
		if (rs.getObject("OGGETTO") != null) result.setOggetto(rs.getString("OGGETTO"));
		if (rs.getObject("TESTO") != null) result.setTesto(rs.getString("TESTO"));
		if (rs.getObject("T_MAIL_INVIATE_ID") != null) result.settMailInviateId(rs.getLong("T_MAIL_INVIATE_ID"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("T_PROTOCOLLO_ID") != null) result.settProtocolloId(rs.getLong("T_PROTOCOLLO_ID"));
		if (rs.getObject("T_TIPICOMUNICAZIONE_ID") != null) result.settTipoComunicazioneId(rs.getLong("T_TIPICOMUNICAZIONE_ID"));
		if (rs.getObject("VERSO") != null) result.setVerso(rs.getString("VERSO"));
		return result;
	}
	

}
