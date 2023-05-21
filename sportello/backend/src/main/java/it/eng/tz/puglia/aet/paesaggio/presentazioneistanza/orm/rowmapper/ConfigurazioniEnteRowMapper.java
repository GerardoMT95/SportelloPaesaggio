package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Row Mapper for table presentazione_istanza.configurazioni_ente
 */
public class ConfigurazioniEnteRowMapper implements RowMapper<ConfigurazioniEnteDTO>{

    public ConfigurazioniEnteDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ConfigurazioniEnteDTO result = new ConfigurazioniEnteDTO();
        
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setSistemaProtocollazione(rs.getBoolean("sistema_protocollazione"));
        if (rs.getObject("protocollazione_endpoint") != null)
            result.setProtocollazioneEndpoint(rs.getString("protocollazione_endpoint"));
        if (rs.getObject("protocollazione_administration") != null)
            result.setProtocollazioneAdministration(rs.getString("protocollazione_administration"));
        if (rs.getObject("protocollazione_aoo") != null)
            result.setProtocollazioneAoo(rs.getString("protocollazione_aoo"));
        if (rs.getObject("protocollazione_register") != null)
            result.setProtocollazioneRegister(rs.getString("protocollazione_register"));
        if (rs.getObject("protocollazione_user") != null)
            result.setProtocollazioneUser(rs.getString("protocollazione_user"));
        if (rs.getObject("protocollazione_password") != null)
            result.setProtocollazionePassword(rs.getString("protocollazione_password"));
        if (rs.getObject("protocollazione_hash_algorithm") != null)
            result.setProtocollazioneHashAlgorithm(rs.getString("protocollazione_hash_algorithm"));
        if (rs.getObject("protocollazione_indirizzopostale") != null)
            result.setProtocollazioneIndirizzoPostale(rs.getString("protocollazione_indirizzopostale"));
        if (rs.getObject("protocollazione_indirizzotelematico") != null)
            result.setProtocollazioneIndirizzoTelematico(rs.getString("protocollazione_indirizzotelematico")); 
        if (rs.getObject("protocollazione_tipoindirizzotelematico") != null)
            result.setProtocollazioneTipoIndirizzoTelematico(rs.getString("protocollazione_tipoindirizzotelematico")); 
        if (rs.getObject("protocollazione_aoo_denominazione") != null)
            result.setProtocollazioneAooDenominazione(rs.getString("protocollazione_aoo_denominazione"));
        if (rs.getObject("protocollazione_denominazione") != null)
            result.setProtocollazioneDenominazione(rs.getString("protocollazione_denominazione"));
        if (rs.getObject("protocollazione_data_registrazione") != null)
            result.setProtocollazioneDataRegistrazione(rs.getDate("protocollazione_data_registrazione"));
        if (rs.getObject("protocollazione_numero_registrazione") != null)
            result.setProtocollazioneNumeroRegistrazione(rs.getString("protocollazione_numero_registrazione"));
        result.setSistemaPagamento(rs.getBoolean("sistema_pagamento"));
        if (rs.getObject("pagamento_tipo_riscossione") != null)
            result.setPagamentoTipoRiscossione(rs.getString("pagamento_tipo_riscossione"));
        if (rs.getObject("pagamento_cod_ente") != null)
            result.setPagamentoCodEnte(rs.getString("pagamento_cod_ente"));
        if (rs.getObject("pagamento_tipologia") != null)
            result.setPagamentoTipologia(rs.getString("pagamento_tipologia"));
        if (rs.getObject("pagamento_endpoint") != null)
            result.setPagamentoEndPoint(rs.getString("pagamento_endpoint"));
        if (rs.getObject("pagamento_password") != null)
            result.setPagamentoPassword(rs.getString("pagamento_password"));
        if (rs.getObject("pagamento_commissione") != null)
            result.setPagamentoCommissione(rs.getDouble("pagamento_commissione"));
        if (rs.getObject("pagamento_regex_iud") != null)
            result.setPagamentoRegexIud(rs.getString("pagamento_regex_iud"));
        
        result.setSistemaPec(rs.getBoolean("sistema_pec"));
        if (rs.getObject("pec_indirizzo") != null)
            result.setPecIndirizzo(rs.getString("pec_indirizzo"));
        if (rs.getObject("pec_nome") != null)
            result.setPecNome(rs.getString("pec_nome"));
        if (rs.getObject("pec_username") != null)
            result.setPecUsername(rs.getString("pec_username"));
        if (rs.getObject("pec_password") != null)
            result.setPecPassword(rs.getString("pec_password"));
        if (rs.getObject("pec_host_in") != null)
            result.setPecHostIn(rs.getString("pec_host_in"));
        if (rs.getObject("pec_host_out") != null)
            result.setPecHostOut(rs.getString("pec_host_out"));
        if (rs.getObject("pec_protocollo_in") != null)
            result.setPecProtocolloIn(rs.getBoolean("pec_protocollo_in"));
        if (rs.getObject("pec_protocollo_out") != null)
            result.setPecProtocolloOut(rs.getBoolean("pec_protocollo_out"));
        if (rs.getObject("pec_tipo_protocollo_in") != null)
            result.setPecTipoProtocolloIn(rs.getString("pec_tipo_protocollo_in"));
        if (rs.getObject("pec_tipo_protocollo_out") != null)
            result.setPecTipoProtocolloOut(rs.getString("pec_tipo_protocollo_out"));
        if (rs.getObject("pec_ssl_in") != null)
            result.setPecSslIn(rs.getBoolean("pec_ssl_in"));
        if (rs.getObject("pec_ssl_out") != null)
            result.setPecSslOut(rs.getBoolean("pec_ssl_out"));
        if (rs.getObject("pec_tls_in") != null)
            result.setPecTlsIn(rs.getBoolean("pec_tls_in"));
        if (rs.getObject("pec_tls_out") != null)
            result.setPecTlsOut(rs.getBoolean("pec_tls_out"));
        if (rs.getObject("pec_porta_ssl_in") != null)
            result.setPecPortaSslIn(rs.getInt("pec_porta_ssl_in"));
        if (rs.getObject("pec_porta_ssl_out") != null)
            result.setPecPortaSslOut(rs.getInt("pec_porta_ssl_out"));
        if (rs.getObject("pec_porta_tls_in") != null)
            result.setPecPortaTlsIn(rs.getInt("pec_porta_tls_in"));
        if (rs.getObject("pec_porta_tls_out") != null)
            result.setPecPortaTlsOut(rs.getInt("pec_porta_tls_out"));
        if (rs.getObject("pec_start_tls_in") != null)
            result.setPecStartTlsIn(rs.getBoolean("pec_start_tls_in"));
        if (rs.getObject("pec_start_tls_out") != null)
            result.setPecStartTlsOut(rs.getBoolean("pec_start_tls_out"));
        if (rs.getObject("pec_autenticazione_in") != null)
            result.setPecAutenticazioneIn(rs.getBoolean("pec_autenticazione_in"));
        if (rs.getObject("pec_autenticazione_out") != null)
            result.setPecAutenticazioneOut(rs.getBoolean("pec_autenticazione_out"));
        if (rs.getObject("pubblicazione_stato_pratica") != null)
            result.setPubblicazioneStatoPratica(rs.getBoolean("pubblicazione_stato_pratica"));
        if (rs.getObject("can_create_conferenza") != null)
            result.setCanCreateConferenza(rs.getBoolean("can_create_conferenza"));
        if (rs.getObject("indirizzi_mail_default") != null)
            result.setIndirizziMailDefault(rs.getString("indirizzi_mail_default"));
        if(rs.getObject("tipo_pratica_seduta") != null)
        {
        	List<String> tmp = Arrays.asList(StringUtils.split(rs.getString("tipo_pratica_seduta"), ","));
        	result.setTipoPraticaSeduta(tmp.stream().filter(StringUtil::isNotEmpty).map(Integer::parseInt).collect(Collectors.toList()));
        }
        if (rs.getObject("bilancio") != null)
            result.setBilancio(rs.getString("bilancio"));
        //decript parti crypted
        result.setPecPassword(StringUtil.isNotEmpty(result.getPecPassword())?CryptUtil.decrypt(result.getPecPassword()):result.getPecPassword());
		result.setProtocollazionePassword(StringUtil.isNotEmpty(result.getProtocollazionePassword())?CryptUtil.decrypt(result.getProtocollazionePassword()):result.getProtocollazionePassword());
		result.setPagamentoPassword(StringUtil.isNotEmpty(result.getPagamentoPassword())?CryptUtil.decrypt(result.getPagamentoPassword()):result.getPagamentoPassword());
    
        return result;
    }
}