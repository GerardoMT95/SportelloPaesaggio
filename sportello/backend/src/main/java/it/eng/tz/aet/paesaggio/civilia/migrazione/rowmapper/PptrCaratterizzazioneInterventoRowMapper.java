/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrCaratterizzazioneIntervento;


/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrCaratterizzazioneInterventoRowMapper implements RowMapper<PptrCaratterizzazioneIntervento>{

	@Override
	public PptrCaratterizzazioneIntervento mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrCaratterizzazioneIntervento result=new PptrCaratterizzazioneIntervento();
		if (rs.getObject("ALTRO") != null) result.setAltro(rs.getString("ALTRO"));
		if (rs.getObject("ALTRO_SPECIFICARE") != null) result.setAltroSpecificare(rs.getString("ALTRO_SPECIFICARE"));
		if (rs.getObject("DEMOLIZIONE") != null) result.setDemolizione(rs.getString("DEMOLIZIONE"));
		if (rs.getObject("IMPIANTI_PRODUZIONE_ENERGIA") != null) result.setImpiantiProduzioneEnergia(rs.getString("IMPIANTI_PRODUZIONE_ENERGIA"));
		if (rs.getObject("INFRASTUTTURE_PRIMARIE") != null) result.setInfrastrutturePrimarie(rs.getString("INFRASTUTTURE_PRIMARIE"));
		if (rs.getObject("LINEE_TELEF_ELETTRICHE") != null) result.setLineeTelefElettriche(rs.getString("LINEE_TELEF_ELETTRICHE"));
		if (rs.getObject("MIGLIORAMENTI_FONDIARI") != null) result.setMiglioramentiFondiari(rs.getString("MIGLIORAMENTI_FONDIARI"));
		if (rs.getObject("NUOVI_INSED_AREA_RURALI") != null) result.setNuoviInsedAreaRurali(rs.getString("NUOVI_INSED_AREA_RURALI"));
		if (rs.getObject("NUOVI_INSED_AREA_URB") != null) result.setNuoviInsedAreaUrb(rs.getString("NUOVI_INSED_AREA_URB"));
		if (rs.getObject("NUOVI_INSED_CIVILI_RURALI") != null) result.setNuoviInsediamentiCiviliRurali(rs.getString("NUOVI_INSED_CIVILI_RURALI"));
		if (rs.getObject("NUOVI_INSED_IND_COMMERC") != null) result.setNuoviInsedIndCommerc(rs.getString("NUOVI_INSED_IND_COMMERC"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("RECINZIONI") != null) result.setRecinzioni(rs.getString("RECINZIONI"));
		if (rs.getObject("RIMESSA_IN_PRISTINO") != null) result.setRimessaInPristino(rs.getString("RIMESSA_IN_PRISTINO"));
		if (rs.getObject("RIMESSA_IN_PRISTINO_DETT") != null) result.setRimessaInPristinoDett(rs.getString("RIMESSA_IN_PRISTINO_DETT"));
		if (rs.getObject("RISTR_INSED_CIVILI_RURALI") != null) result.setRistrInsedCiviliRurali(rs.getString("RISTR_INSED_CIVILI_RURALI"));
		if (rs.getObject("RISTR_INSED_IND_COMMERC") != null) result.setRistrInsedIndCommerc(rs.getString("RISTR_INSED_IND_COMMERC"));
		if (rs.getObject("RISTR_MANUF_RURALI_NO_SECCO") != null) result.setRistrManufRuraliNoSecco(rs.getString("RISTR_MANUF_RURALI_NO_SECCO"));
		if (rs.getObject("RISTR_MANUF_RURALI_SECCO") != null) result.setRistrManufRuraliSecco(rs.getString("RISTR_MANUF_RURALI_SECCO"));
		if (rs.getObject("TEMP_PERM") != null) result.setTempPerm(rs.getString("TEMP_PERM"));
		if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaApptrId(rs.getLong("T_PRATICA_APPTR_ID"));
		return result;
	}

}
