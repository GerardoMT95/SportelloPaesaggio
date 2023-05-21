package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.util.crypt.CryptUtil;

import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pratica_pagamenti
 */
public class PraticaPagamentiRowMapper implements RowMapper<PraticaPagamentiDTO>{

    public PraticaPagamentiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PraticaPagamentiDTO result = new PraticaPagamentiDTO();
        result.setId(rs.getLong("id"));
        result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        result.setIdTariffa(rs.getLong("id_tariffa"));
        result.setImportoProgetto(rs.getDouble("importo_progetto"));
        result.setDataScadenza(rs.getDate("data_scadenza"));
        result.setImportoPagamento(rs.getDouble("importo_pagamento"));
        result.setIud(CryptUtil.decrypt(rs.getString("iud")));
        result.setIuv(CryptUtil.decrypt(rs.getString("iuv")));
        result.setUrlPdf(CryptUtil.decrypt(rs.getString("url_pdf")));
        if (rs.getObject("url_mypay") != null)
            result.setUrlMypay(CryptUtil.decrypt(rs.getString("url_mypay")));
        result.setDeleted(rs.getBoolean("deleted"));
        result.setPagato(rs.getBoolean("pagato"));
        result.setCreateDate(rs.getTimestamp("create_date"));
        result.setCreateUser(rs.getString("create_user"));
        if (rs.getObject("update_date") != null)
            result.setUpdateDate(rs.getTimestamp("update_date"));
        if (rs.getObject("update_user") != null)
            result.setUpdateUser(rs.getString("update_user"));
        result.setCausale(rs.getString("causale"));
        if (rs.getObject("ricevuta") != null)
            result.setRicevuta(CryptUtil.decrypt(rs.getString("ricevuta")));
        return result;
    }
}
