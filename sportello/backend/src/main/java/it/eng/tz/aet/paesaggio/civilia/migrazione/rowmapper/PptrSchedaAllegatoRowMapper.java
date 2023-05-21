package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrSchedaAllegato;

public class PptrSchedaAllegatoRowMapper implements RowMapper<PptrSchedaAllegato>{

	@Override
	public PptrSchedaAllegato mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrSchedaAllegato result=new PptrSchedaAllegato();
		//SCHEDA_ALLEGATO_ID,CODICE_PRATICA_APPPTR,T_PRATICA_APPPTR_ID,TITOLO,DESCRIZIONE,NOME_FILE,ALLEGATO,CONTENTTYPE,
		//MITTENTE,RUOLO_MITTENTE,VISIBILITA_ET,VISIBILITA_SOP,VISIBILITA_ED,VISIBILITA_RI,DESTINATARIO,DATA_INSERIMENTO,ALFRESCO_ID
		if (rs.getObject("SCHEDA_ALLEGATO_ID") != null) result.setSchedaAllegatoId(rs.getLong("SCHEDA_ALLEGATO_ID"));
		if (rs.getObject("TITOLO") != null) result.setTitolo(rs.getString("TITOLO"));
		if (rs.getObject("DESCRIZIONE") != null) result.setDescrizione(rs.getString("DESCRIZIONE"));
		if (rs.getObject("NOME_FILE") != null) result.setNomeFile(rs.getString("NOME_FILE"));
		if (rs.getObject("ALLEGATO") != null) result.setAllegato(rs.getBlob("ALLEGATO"));
		if (rs.getObject("CONTENTTYPE") != null) result.setContentType(rs.getString("CONTENTTYPE"));
		if (rs.getObject("MITTENTE") != null) result.setMittente(rs.getString("MITTENTE"));
		if (rs.getObject("RUOLO_MITTENTE") != null) result.setRuoloMittente(rs.getString("RUOLO_MITTENTE"));
		if (rs.getObject("VISIBILITA_ET") != null) result.setVisibilita_ET(rs.getLong("VISIBILITA_ET"));
		if (rs.getObject("VISIBILITA_SOP") != null) result.setVisibilita_SOP(rs.getLong("VISIBILITA_SOP"));
		if (rs.getObject("VISIBILITA_ED") != null) result.setVisibilita_ED(rs.getLong("VISIBILITA_ED"));
		if (rs.getObject("VISIBILITA_RI") != null) result.setVisibilita_RI(rs.getLong("VISIBILITA_RI"));
		if (rs.getObject("DESTINATARIO") != null) result.setDestinatario(rs.getString("DESTINATARIO"));
		if (rs.getObject("DATA_INSERIMENTO") != null) result.setDataInserimento(rs.getDate("DATA_INSERIMENTO"));
		if (rs.getObject("ALFRESCO_ID") != null) result.setAlfrescoId(rs.getString("ALFRESCO_ID"));
		return result;
	}

}
