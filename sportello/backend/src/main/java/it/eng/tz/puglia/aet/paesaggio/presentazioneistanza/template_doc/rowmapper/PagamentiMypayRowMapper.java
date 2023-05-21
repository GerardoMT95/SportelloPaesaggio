package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.rowmapper;

import java.sql.*;
import java.math.*;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PagamentiMypayDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pagamenti_mypay
 */
public class PagamentiMypayRowMapper implements RowMapper<PagamentiMypayDTO>{

    public PagamentiMypayDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PagamentiMypayDTO result = new PagamentiMypayDTO();
        result.setIud(rs.getString("iud"));
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setEnteId(rs.getString("ente_id"));
        if (rs.getObject("causale") != null)
            result.setCausale(rs.getString("causale"));
        if (rs.getObject("cod_ipa_ente") != null)
            result.setCodIpaEnte(rs.getString("cod_ipa_ente"));
        if (rs.getObject("cf_pagatore") != null)
            result.setCfPagatore(rs.getString("cf_pagatore"));
        if (rs.getObject("email_pagatore") != null)
            result.setEmailPagatore(rs.getString("email_pagatore"));
        if (rs.getObject("ret_url") != null)
            result.setRetUrl(rs.getString("ret_url"));
        if (rs.getObject("sogg_paga") != null)
            result.setSoggPaga(rs.getString("sogg_paga"));
        if (rs.getObject("tipologia") != null)
            result.setTipologia(rs.getString("tipologia"));
        if (rs.getObject("tipo_riscossione") != null)
            result.setTipoRiscossione(rs.getString("tipo_riscossione"));
        if (rs.getObject("stato") != null)
            result.setStato(rs.getString("stato"));
        if (rs.getObject("data_inserimento") != null)
            result.setDataInserimento(rs.getTimestamp("data_inserimento"));
        if (rs.getObject("data_modifica") != null)
            result.setDataModifica(rs.getTimestamp("data_modifica"));
        if (rs.getObject("importo") != null)
            result.setImporto(rs.getBigDecimal("importo"));
        if (rs.getObject("idsession") != null)
            result.setIdsession(rs.getString("idsession"));
        if (rs.getObject("esito") != null)
            result.setEsito(rs.getString("esito"));
        if (rs.getObject("error") != null)
            result.setError(rs.getString("error"));
        if (rs.getObject("url_to_pay") != null)
            result.setUrlToPay(rs.getString("url_to_pay"));
        if (rs.getObject("cfg_endpoint_mypay") != null)
            result.setCfgEndpointMyPay(rs.getString("cfg_endpoint_mypay"));
        if (rs.getObject("cfg_password_mypay") != null)
            result.setCfgPasswordMyPay(rs.getString("cfg_password_mypay"));
        return result;
    }
}
