/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.ReferentiProgettoDto;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public class ReferentiProgettoDtoRowMapper implements RowMapper<ReferentiProgettoDto> {
	public ReferentiProgettoDto mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ReferentiProgettoDto result = new ReferentiProgettoDto();
        
        if (rs.getObject("tPraticaApptrId") != null) result.settPraticaApptrId(rs.getLong("tPraticaApptrId"));
        if (rs.getObject("richiedenteNome") != null) result.setRichiedenteNome(rs.getString("richiedenteNome"));
        if (rs.getObject("richiedenteCognome") != null) result.setRichiedenteCognome(rs.getString("richiedenteCognome"));
        if (rs.getObject("richiedenteCodiceFiscale") != null) result.setRichiedenteCodiceFiscale(rs.getString("richiedenteCodiceFiscale"));
        if (rs.getObject("dittaRuoloRich") != null) result.setDittaRuoloRich(rs.getString("dittaRuoloRich"));
        if (rs.getObject("dittaRagioneSociale") != null) result.setDittaRagioneSociale(rs.getString("dittaRagioneSociale"));
        if (rs.getObject("dittaPartitaIva") != null) result.setDittaPartitaIva(rs.getString("dittaPartitaIva"));
        if (rs.getObject("dittaCodiceFiscale") != null) result.setDittaCodiceFiscale(rs.getString("dittaCodiceFiscale"));
        if (rs.getObject("comuneNascita") != null) result.setComuneNascita(rs.getString("comuneNascita"));
        if (rs.getObject("provNascita") != null) result.setProvNascita(rs.getString("provNascita"));
        if (rs.getObject("statoNascita") != null) result.setStatoNascita(rs.getString("statoNascita"));
        if (rs.getObject("dataNascita") != null) result.setDataNascita(rs.getDate("dataNascita"));
        if (rs.getObject("statoResidenza") != null) result.setStatoResidenza(rs.getString("statoResidenza"));
        if (rs.getObject("comuneResidenza") != null) result.setComuneResidenza(rs.getString("comuneResidenza"));
        if (rs.getObject("provResidenza") != null) result.setProvResidenza(rs.getString("provResidenza"));
        if (rs.getObject("indirizzo") != null) result.setIndirizzo(rs.getString("indirizzo"));
        if (rs.getObject("numCivico") != null) result.setNumCivico(rs.getString("numCivico"));
        if (rs.getObject("cap") != null) result.setCap(rs.getString("cap"));
        if (rs.getObject("telFisso") != null) result.setTelFisso(rs.getString("telFisso"));
        if (rs.getObject("telCellulare") != null) result.setTelCellulare(rs.getString("telCellulare"));
        if (rs.getObject("telFax") != null) result.setTelFax(rs.getString("telFax"));
        if (rs.getObject("indirizzoMail") != null) result.setIndirizzoMail(rs.getString("indirizzoMail"));
        if (rs.getObject("indirizzoPec") != null) result.setIndirizzoPec(rs.getString("indirizzoPec"));
        return result;
    }

}
