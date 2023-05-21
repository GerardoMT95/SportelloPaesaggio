/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaPratica;

/**
 * @author Adriano Colaianni
 * @date 30 lug 2021
 */
public class CiviliaPraticaRowMapper implements RowMapper<CiviliaPratica> {

	@Override
	public CiviliaPratica mapRow(ResultSet rs, int rowNum) throws SQLException {
		CiviliaPratica result=new CiviliaPratica();
		//CODICE,DATAATTIVAZIONE,DATAARRIVO,STATOPRATICA,T_PRATICA_ID,
		//T_AGSU_TIPO_EVENTO_ID,CODICEINTERNO ,PRAT_COD_NUM1 ,PRAT_COD_NUM2 ,PRAT_COD_NUM3
		if (rs.getObject("CODICE") != null) result.setCodice(rs.getString("CODICE"));
		if (rs.getObject("DATAATTIVAZIONE") != null) result.setDataAttivazione(rs.getDate("DATAATTIVAZIONE"));
		if (rs.getObject("DATAARRIVO") != null) result.setDataArrivo(rs.getDate("DATAARRIVO"));
		if (rs.getObject("STATOPRATICA") != null) result.setStatoPratica(rs.getInt("STATOPRATICA"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("T_AGSU_TIPO_EVENTO_ID") != null) result.settAgsuTipoInterventoId(rs.getLong("T_AGSU_TIPO_EVENTO_ID"));
		if (rs.getObject("CODICEINTERNO") != null) result.setCodiceInterno(rs.getString("CODICEINTERNO"));
		if (rs.getObject("PRAT_COD_NUM1") != null) result.setPratCodNum1(rs.getString("PRAT_COD_NUM1"));
		if (rs.getObject("PRAT_COD_NUM2") != null) result.setPratCodNum2(rs.getLong("PRAT_COD_NUM2"));
		if (rs.getObject("PRAT_COD_NUM3") != null) result.setPratCodNum3(rs.getLong("PRAT_COD_NUM3"));
		return result;
	}
}
