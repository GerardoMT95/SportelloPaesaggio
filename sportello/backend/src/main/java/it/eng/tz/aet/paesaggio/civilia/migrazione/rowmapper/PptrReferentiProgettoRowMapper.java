package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiProgetto;

public class PptrReferentiProgettoRowMapper implements RowMapper<PptrReferentiProgetto>{

	@Override
	public PptrReferentiProgetto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrReferentiProgetto result=new PptrReferentiProgetto();
		//REFERENTE_PROGETTO_ID	TIPO_REFERENTE	COGNOME	NOME	CODICE_FISCALE	DITTA_RUOLO_RICH	DITTA_RAGIONE_SOCIALE	
		//DITTA_PARTITA_IVA	DITTA_CODICE_FISCALE	COMUNE_NASCITA	PROV_NASCITA	STATO_NASCITA	DATA_NASCITA	COMUNE_RESIDENZA	
		//PROV_RESIDENZA	INDIRIZZO	NUM_CIVICO	CAP	TEL_FISSO	TEL_CELLULARE	TEL_FAX	INDIRIZZO_EMAIL	INDIRIZZO_PEC	
		//TECNICO_COMUNE_STUDIO	TECNICO_PROV_STUDIO	TECNICO_STATO_STUDIO	TECNICO_INDIRIZZO_STUDIO	
		//TECNICO_NUM_CIV_STUDIO	TECNICO_CAP_STUDIO	TECNICO_ORD_COLLEGIO	TECNICO_ORD_COLLEGIO_SEDE	
		//TECNICO_ORD_COLLEGIO_N_ISCR	T_PRATICA_APPTR_ID	STATO_RESIDENZA	SOGGETTO_RAPPRESENTATO	CODICE_PRATICA_APPPTR	PROG
		if (rs.getObject("REFERENTE_PROGETTO_ID") != null) result.setReferenteProgettoId(rs.getLong("REFERENTE_PROGETTO_ID"));
		if (rs.getObject("TIPO_REFERENTE") != null) result.setTipoReferente(rs.getString("TIPO_REFERENTE"));
		if (rs.getObject("COGNOME") != null) result.setCognome(rs.getString("COGNOME"));
		if (rs.getObject("NOME") != null) result.setNome(rs.getString("NOME"));
		if (rs.getObject("CODICE_FISCALE") != null) result.setCodiceFiscale(rs.getString("CODICE_FISCALE"));
		if (rs.getObject("DITTA_RUOLO_RICH") != null) result.setDittaRuoloRich(rs.getString("DITTA_RUOLO_RICH"));
		if (rs.getObject("DITTA_RAGIONE_SOCIALE") != null) result.setDittaRuoloRich(rs.getString("DITTA_RAGIONE_SOCIALE"));
		if (rs.getObject("DITTA_PARTITA_IVA") != null) result.setDittaPartitaIva(rs.getString("DITTA_PARTITA_IVA"));
		if (rs.getObject("DITTA_CODICE_FISCALE") != null) result.setDittaCodiceFiscale(rs.getString("DITTA_CODICE_FISCALE"));
		if (rs.getObject("COMUNE_NASCITA") != null) result.setComuneNascita(rs.getString("COMUNE_NASCITA"));
		if (rs.getObject("PROV_NASCITA") != null) result.setProvNascita(rs.getString("PROV_NASCITA"));
		if (rs.getObject("STATO_NASCITA") != null) result.setStatoNascita(rs.getString("STATO_NASCITA"));
		if (rs.getObject("DATA_NASCITA") != null) result.setDataNascita(rs.getDate("DATA_NASCITA"));
		if (rs.getObject("COMUNE_RESIDENZA") != null) result.setComuneResidenza(rs.getString("COMUNE_RESIDENZA"));
		if (rs.getObject("PROV_RESIDENZA") != null) result.setProvResidenza(rs.getString("PROV_RESIDENZA"));
		if (rs.getObject("INDIRIZZO") != null) result.setIndirizzo(rs.getString("INDIRIZZO"));
		if (rs.getObject("NUM_CIVICO") != null) result.setNumCivico(rs.getString("NUM_CIVICO"));
		if (rs.getObject("CAP") != null) result.setCap(rs.getString("CAP"));
		if (rs.getObject("TEL_FISSO") != null) result.setTelFisso(rs.getString("TEL_FISSO"));
		if (rs.getObject("TEL_CELLULARE") != null) result.setTelCellulare(rs.getString("TEL_CELLULARE"));
		if (rs.getObject("TEL_FAX") != null) result.setTelFax(rs.getString("TEL_FAX"));
		if (rs.getObject("INDIRIZZO_EMAIL") != null) result.setIndirizzoEmail(rs.getString("INDIRIZZO_EMAIL"));
		if (rs.getObject("INDIRIZZO_PEC") != null) result.setIndirizzoPec(rs.getString("INDIRIZZO_PEC"));
		if (rs.getObject("TECNICO_COMUNE_STUDIO") != null) result.setTecnicoComuneStudio(rs.getString("TECNICO_COMUNE_STUDIO"));
		if (rs.getObject("TECNICO_PROV_STUDIO") != null) result.setTecnicoProvStudio(rs.getString("TECNICO_PROV_STUDIO"));
		if (rs.getObject("TECNICO_STATO_STUDIO") != null) result.setTecnicoStatoStudio(rs.getString("TECNICO_STATO_STUDIO"));
		if (rs.getObject("TECNICO_INDIRIZZO_STUDIO") != null) result.setTecnicoIndirizzoStudio(rs.getString("TECNICO_INDIRIZZO_STUDIO"));
		if (rs.getObject("TECNICO_NUM_CIV_STUDIO") != null) result.setTecnicoNumCivStudio(rs.getString("TECNICO_NUM_CIV_STUDIO"));
		if (rs.getObject("TECNICO_CAP_STUDIO") != null) result.setTecnicoCapStudio(rs.getString("TECNICO_CAP_STUDIO"));
		if (rs.getObject("TECNICO_ORD_COLLEGIO") != null) result.setTecnicoOrdCollegio(rs.getString("TECNICO_ORD_COLLEGIO"));
		if (rs.getObject("TECNICO_ORD_COLLEGIO_SEDE") != null) result.setTecnicoOrdCollegioSede(rs.getString("TECNICO_ORD_COLLEGIO_SEDE"));
		if (rs.getObject("TECNICO_ORD_COLLEGIO_N_ISCR") != null) result.setTecnicoOrdCollegioNIscr(rs.getString("TECNICO_ORD_COLLEGIO_N_ISCR"));
		if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_APPTR_ID"));
		if (rs.getObject("STATO_RESIDENZA") != null) result.setStatoResidenza(rs.getString("STATO_RESIDENZA"));
		if (rs.getObject("SOGGETTO_RAPPRESENTATO") != null) result.setSoggettoRappresentato(rs.getString("SOGGETTO_RAPPRESENTATO"));
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		return result;
	}

}
