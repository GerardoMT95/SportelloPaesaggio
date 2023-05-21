/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrParticelleCatastali;
import it.eng.tz.puglia.autpae.civilia.migration.repository.PraticaRepository;

/**
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class PptrParticelleCatastaliRowMapper implements RowMapper<PptrParticelleCatastali>{
	
	private static final Logger log = LoggerFactory.getLogger(PptrParticelleCatastaliRowMapper.class);
	
	final String[] sezioniCatastali={"A662;A;Bari",
			"A662;B;Carbonara",
			"A662;C;Ceglie del Campo",
			"A662;D;Loseto",
			"A662;E;Palese",
			"A662;F;Santo Spirito",
			"A662;G;Torre a Mare",
			"L049;A;Taranto",
			"L049;B;San Demetrio",
			"L049;C;Morroni",
			"M428;A;Presicce",
			"M428;B;Acquarica del capo"};

	@Override
	public PptrParticelleCatastali mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrParticelleCatastali result = new PptrParticelleCatastali();
		if (rs.getObject("COD_CAT") != null) result.setCodCat(rs.getString("COD_CAT"));
		if (rs.getObject("CODICE_PRATICA") != null) result.setCodicePratica(rs.getString("CODICE_PRATICA"));
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaApptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("COD_ISTAT") != null) result.setCodIstat(rs.getString("COD_ISTAT"));
		if (rs.getObject("COMUNE") != null) result.setComune(rs.getString("COMUNE"));
		if (rs.getObject("DATA_RIFERIMENTO_CATASTALE") != null) result.setDataRiferimentoCatastale(rs.getString("DATA_RIFERIMENTO_CATASTALE"));
		if (rs.getObject("ELABORATO") != null) result.setElaborato(rs.getLong("ELABORATO"));
		if (rs.getObject("ENTE") != null) result.setEnte(rs.getString("ENTE"));
		if (rs.getObject("FOGLIO") != null) result.setFoglio(rs.getString("FOGLIO"));
		if (rs.getObject("LIVELLO") != null) result.setLivello(rs.getString("LIVELLO"));
		if (rs.getObject("LOCALIZ_INTERV_ID") != null) result.setLocalizIntervId(rs.getLong("LOCALIZ_INTERV_ID"));
		if (rs.getObject("PARTICELLA") != null) result.setParticella(rs.getString("PARTICELLA"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("SEZIONE") != null) result.setSezione(rs.getString("SEZIONE"));
		if (rs.getObject("SUB") != null) result.setSub(rs.getString("SUB"));
		if (rs.getObject("T_PRATICA_APPPTR_ID") != null) result.settPraticaApptrId(rs.getLong("T_PRATICA_APPPTR_ID"));
		if (rs.getObject("UTENTE") != null) result.setUtente(rs.getString("UTENTE"));
		//converto la sezione catastale che in SEZIONE a volte si trova in chiaro e non codificata
		try {
			convertiSezione(result);
		}catch (Exception e) {
			log.error("errore in  convertiSezione: " + result.getSezione() + " " + result.getCodCat(),e);
		}
		return result;
	}

	private void convertiSezione(PptrParticelleCatastali result) {
		if(StringUtil.isNotEmpty(result.getSezione()) && StringUtil.isNotEmpty(result.getCodCat())) {
			if(result.getSezione().length()>1) {
				for(String sezione:sezioniCatastali) {
					String[] tokens=sezione.split(";");
					if(tokens[0].equals(result.getCodCat()) && tokens[2].equals(result.getSezione())) {
						result.setSezione(tokens[1]);
						result.setNomeSezione(tokens[2]);
					}
				}
			}
			//se invece è codice es. A B C inserisco in nomeSezione il valore...
			if(result.getSezione().length()==1) { 
				for(String sezione:sezioniCatastali) {
					String[] tokens=sezione.split(";");
					if(tokens[0].equals(result.getCodCat()) && tokens[1].equals(result.getSezione())) {
						result.setNomeSezione(tokens[2]);
					}
				}
			}
		}
	}

}
