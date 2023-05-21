package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrInquadramentoIntervento;

public class PptrInquadramentoInterventoRowMapper implements RowMapper<PptrInquadramentoIntervento>{

	@Override
	public PptrInquadramentoIntervento mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrInquadramentoIntervento result = new PptrInquadramentoIntervento();
		// INQUADRAM_INTERV_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,INQ_OPERA_CORRELATA_A,INQ_CARATTERE_INTERV,
		//INQ_DEST_USO_INTERV,INQ_DEST_USO_INTERV_ALTRO,INQ_USO_SUOLO,INQ_USO_SUOLO_ALTRO,INQ_CONTESTO_PAESAG,
		//INQ_MORFOLOGIA_PAESAG,PROG,INQ_CONTESTO_PAESAG_ALTRO,INQ_MORFOLOGIA_PAESAG_ALTRO 
		if (rs.getObject("INQ_OPERA_CORRELATA_A") != null) result.setInqOperaCorrelataA(rs.getString("INQ_OPERA_CORRELATA_A"));		
		if (rs.getObject("INQ_CARATTERE_INTERV") != null) result.setInqCarattereInterv(rs.getString("INQ_CARATTERE_INTERV"));
		if (rs.getObject("INQ_DEST_USO_INTERV") != null) result.setInqDestUsoInterv(rs.getString("INQ_DEST_USO_INTERV"));
		if (rs.getObject("INQ_DEST_USO_INTERV_ALTRO") != null) result.setInqDestUsoIntervAltro(rs.getString("INQ_DEST_USO_INTERV_ALTRO"));
		if (rs.getObject("INQ_USO_SUOLO") != null) result.setInqUsoSuolo(rs.getString("INQ_USO_SUOLO"));
		if (rs.getObject("INQ_USO_SUOLO_ALTRO") != null) result.setInqUsoSuolo(rs.getString("INQ_USO_SUOLO_ALTRO"));
		if (rs.getObject("INQ_CONTESTO_PAESAG") != null) result.setInqContestoPaesag(rs.getString("INQ_CONTESTO_PAESAG"));
		if (rs.getObject("INQ_CONTESTO_PAESAG_ALTRO") != null) result.setInqContestoPaesagAltro(rs.getString("INQ_CONTESTO_PAESAG_ALTRO"));
		if (rs.getObject("INQ_MORFOLOGIA_PAESAG") != null) result.setInqMorfologiaPaesag(rs.getString("INQ_MORFOLOGIA_PAESAG"));
		if (rs.getObject("INQ_MORFOLOGIA_PAESAG_ALTRO") != null) result.setInqMorfologiaPaesag(rs.getString("INQ_MORFOLOGIA_PAESAG_ALTRO"));
		return result;
	}
	

}
