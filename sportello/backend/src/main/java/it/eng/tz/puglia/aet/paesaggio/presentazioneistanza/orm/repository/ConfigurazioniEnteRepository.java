package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.TipologicaIntegerBooleanDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ConfigurazioniEnteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TipologicaIntegerBooleanRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ConfigurazioniEnteSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.configurazioni_ente
 */
@Repository
public class ConfigurazioniEnteRepository extends GenericCrudDao<ConfigurazioniEnteDTO, ConfigurazioniEnteSearch>{

    private final ConfigurazioniEnteRowMapper rowMapper = new ConfigurazioniEnteRowMapper();
    
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_organizzazione\"")
                                     .append(",\"sistema_protocollazione\"")
                                     .append(",\"protocollazione_endpoint\"")
                                     .append(",\"protocollazione_administration\"")
                                     .append(",\"protocollazione_aoo\"")
                                     .append(",\"protocollazione_register\"")
                                     .append(",\"protocollazione_user\"")
                                     .append(",\"protocollazione_password\"")
                                     .append(",\"protocollazione_hash_algorithm\"")
                                     .append(",\"sistema_pagamento\"")
                                     .append(",\"pagamento_tipo_riscossione\"")
                                     .append(",\"pagamento_cod_ente\"")
                                     .append(",\"pagamento_tipologia\"")
                                     .append(",\"pagamento_endpoint\"")
                                     .append(",\"pagamento_password\"")
                                     .append(",\"pagamento_commissione\"")
                                     .append(",\"pagamento_regex_iud\"")
                                     .append(",\"sistema_pec\"")
                                     .append(",\"pec_indirizzo\"")
                                     .append(",\"pec_nome\"")
                                     .append(",\"pec_username\"")
                                     .append(",\"pec_password\"")
                                     .append(",\"pec_host_in\"")
                                     .append(",\"pec_host_out\"")
                                     .append(",\"pec_protocollo_in\"")
                                     .append(",\"pec_protocollo_out\"")
                                     .append(",\"pec_tipo_protocollo_in\"")
                                     .append(",\"pec_tipo_protocollo_out\"")
                                     .append(",\"pec_ssl_in\"")
                                     .append(",\"pec_ssl_out\"")
                                     .append(",\"pec_tls_in\"")
                                     .append(",\"pec_tls_out\"")
                                     .append(",\"pec_porta_ssl_in\"")
                                     .append(",\"pec_porta_ssl_out\"")
                                     .append(",\"pec_porta_tls_in\"")
                                     .append(",\"pec_porta_tls_out\"")
                                     .append(",\"pec_start_tls_in\"")
                                     .append(",\"pec_start_tls_out\"")
                                     .append(",\"pec_autenticazione_in\"")
                                     .append(",\"pec_autenticazione_out\"")
                                     .append(",\"pubblicazione_stato_pratica\"")
                                     .append(",\"tipo_pratica_seduta\"")
                                     .append(",\"protocollazione_indirizzopostale\"")
                                     .append(",\"protocollazione_indirizzotelematico\"")
                                     .append(",\"protocollazione_aoo_denominazione\"")
                                     .append(",\"protocollazione_denominazione\"")
                                     .append(",\"protocollazione_tipoindirizzotelematico\"")
                                     .append(",\"protocollazione_data_registrazione\"")
                                     .append(",\"protocollazione_numero_registrazione\"")
                                     .append(",\"can_create_conferenza\"")
                                     .append(",\"indirizzi_mail_default\"")
                                     .append(",\"bilancio\"")
                                     .append(" from \"presentazione_istanza\".\"configurazioni_ente\"")
                                     .toString();
    
    /**
     * select all method
     */
    @Override
    public List<ConfigurazioniEnteDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"configurazioni_ente\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ConfigurazioniEnteDTO find(ConfigurazioniEnteDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_organizzazione\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdOrganizzazione());
        ConfigurazioniEnteDTO dto = super.queryForObjectTxRead(sql, this.rowMapper, parameters);
        return dto;
    }
    
    /**
     * search method
     */
    @Override
    public PaginatedList<ConfigurazioniEnteDTO> search(ConfigurazioniEnteSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdOrganizzazione())){
            sql.append(sep).append("lower(\"id_organizzazione\"::text) = ?");
            parameters.add(search.getIdOrganizzazione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSistemaProtocollazione())){
            sql.append(sep).append("lower(\"sistema_protocollazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSistemaProtocollazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazioneEndpoint())){
            sql.append(sep).append("lower(\"protocollazione_endpoint\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazioneEndpoint()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazioneAdministration())){
            sql.append(sep).append("lower(\"protocollazione_administration\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazioneAdministration()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazioneAoo())){
            sql.append(sep).append("lower(\"protocollazione_aoo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazioneAoo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazioneRegister())){
            sql.append(sep).append("lower(\"protocollazione_register\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazioneRegister()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazioneUser())){
            sql.append(sep).append("lower(\"protocollazione_user\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazioneUser()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazionePassword())){
            sql.append(sep).append("lower(\"protocollazione_password\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazionePassword()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazioneHashAlgorithm())){
            sql.append(sep).append("lower(\"protocollazione_hash_algorithm\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazioneHashAlgorithm()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSistemaPagamento())){
            sql.append(sep).append("lower(\"sistema_pagamento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSistemaPagamento()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPagamentoIntestatario())){
            sql.append(sep).append("lower(\"pagamento_intestatario\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPagamentoIntestatario()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPagamentoIban())){
            sql.append(sep).append("lower(\"pagamento_iban\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPagamentoIban()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPagamentoCausale())){
            sql.append(sep).append("lower(\"pagamento_causale\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPagamentoCausale()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSistemaPec())){
            sql.append(sep).append("lower(\"sistema_pec\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSistemaPec()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecIndirizzo())){
            sql.append(sep).append("lower(\"pec_indirizzo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecIndirizzo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecNome())){
            sql.append(sep).append("lower(\"pec_nome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecNome()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecUsername())){
            sql.append(sep).append("lower(\"pec_username\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecUsername()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecPassword())){
            sql.append(sep).append("lower(\"pec_password\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecPassword()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecHostIn())){
            sql.append(sep).append("lower(\"pec_host_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecHostIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecHostOut())){
            sql.append(sep).append("lower(\"pec_host_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecHostOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecProtocolloIn())){
            sql.append(sep).append("lower(\"pec_protocollo_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecProtocolloIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecProtocolloOut())){
            sql.append(sep).append("lower(\"pec_protocollo_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecProtocolloOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecTipoProtocolloIn())){
            sql.append(sep).append("lower(\"pec_tipo_protocollo_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecTipoProtocolloIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecTipoProtocolloOut())){
            sql.append(sep).append("lower(\"pec_tipo_protocollo_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecTipoProtocolloOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecSslIn())){
            sql.append(sep).append("lower(\"pec_ssl_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecSslIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecSslOut())){
            sql.append(sep).append("lower(\"pec_ssl_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecSslOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecTlsIn())){
            sql.append(sep).append("lower(\"pec_tls_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecTlsIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecTlsOut())){
            sql.append(sep).append("lower(\"pec_tls_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecTlsOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecPortaSslIn())){
            sql.append(sep).append("lower(\"pec_porta_ssl_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecPortaSslIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecPortaSslOut())){
            sql.append(sep).append("lower(\"pec_porta_ssl_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecPortaSslOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecPortaTlsIn())){
            sql.append(sep).append("lower(\"pec_porta_tls_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecPortaTlsIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecPortaTlsOut())){
            sql.append(sep).append("lower(\"pec_porta_tls_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecPortaTlsOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecStartTlsIn())){
            sql.append(sep).append("lower(\"pec_start_tls_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecStartTlsIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecStartTlsOut())){
            sql.append(sep).append("lower(\"pec_start_tls_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecStartTlsOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecAutenticazioneIn())){
            sql.append(sep).append("lower(\"pec_autenticazione_in\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecAutenticazioneIn()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPecAutenticazioneOut())){
            sql.append(sep).append("lower(\"pec_autenticazione_out\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPecAutenticazioneOut()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPubblicazioneStatoPratica())){
            sql.append(sep).append("lower(\"pubblicazione_stato_pratica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPubblicazioneStatoPratica()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idOrganizzazione":
                    sql.append(" order by \"id_organizzazione\" ").append(sortType);
                	   break;
            case "sistemaProtocollazione":
                    sql.append(" order by \"sistema_protocollazione\" ").append(sortType);
                	   break;
            case "protocollazioneEndpoint":
                    sql.append(" order by \"protocollazione_endpoint\" ").append(sortType);
                	   break;
            case "protocollazioneAdministration":
                    sql.append(" order by \"protocollazione_administration\" ").append(sortType);
                	   break;
            case "protocollazioneAoo":
                    sql.append(" order by \"protocollazione_aoo\" ").append(sortType);
                	   break;
            case "protocollazioneRegister":
                    sql.append(" order by \"protocollazione_register\" ").append(sortType);
                	   break;
            case "protocollazioneUser":
                    sql.append(" order by \"protocollazione_user\" ").append(sortType);
                	   break;
            case "protocollazionePassword":
                    sql.append(" order by \"protocollazione_password\" ").append(sortType);
                	   break;
            case "protocollazioneHashAlgorithm":
                    sql.append(" order by \"protocollazione_hash_algorithm\" ").append(sortType);
                	   break;
            case "sistemaPagamento":
                    sql.append(" order by \"sistema_pagamento\" ").append(sortType);
                	   break;
            case "pagamentoIntestatario":
                    sql.append(" order by \"pagamento_intestatario\" ").append(sortType);
                	   break;
            case "pagamentoIban":
                    sql.append(" order by \"pagamento_iban\" ").append(sortType);
                	   break;
            case "pagamentoCausale":
                    sql.append(" order by \"pagamento_causale\" ").append(sortType);
                	   break;
            case "sistemaPec":
                    sql.append(" order by \"sistema_pec\" ").append(sortType);
                	   break;
            case "pecIndirizzo":
                    sql.append(" order by \"pec_indirizzo\" ").append(sortType);
                	   break;
            case "pecNome":
                    sql.append(" order by \"pec_nome\" ").append(sortType);
                	   break;
            case "pecUsername":
                    sql.append(" order by \"pec_username\" ").append(sortType);
                	   break;
            case "pecPassword":
                    sql.append(" order by \"pec_password\" ").append(sortType);
                	   break;
            case "pecHostIn":
                    sql.append(" order by \"pec_host_in\" ").append(sortType);
                	   break;
            case "pecHostOut":
                    sql.append(" order by \"pec_host_out\" ").append(sortType);
                	   break;
            case "pecProtocolloIn":
                    sql.append(" order by \"pec_protocollo_in\" ").append(sortType);
                	   break;
            case "pecProtocolloOut":
                    sql.append(" order by \"pec_protocollo_out\" ").append(sortType);
                	   break;
            case "pecTipoProtocolloIn":
                    sql.append(" order by \"pec_tipo_protocollo_in\" ").append(sortType);
                	   break;
            case "pecTipoProtocolloOut":
                    sql.append(" order by \"pec_tipo_protocollo_out\" ").append(sortType);
                	   break;
            case "pecSslIn":
                    sql.append(" order by \"pec_ssl_in\" ").append(sortType);
                	   break;
            case "pecSslOut":
                    sql.append(" order by \"pec_ssl_out\" ").append(sortType);
                	   break;
            case "pecTlsIn":
                    sql.append(" order by \"pec_tls_in\" ").append(sortType);
                	   break;
            case "pecTlsOut":
                    sql.append(" order by \"pec_tls_out\" ").append(sortType);
                	   break;
            case "pecPortaSslIn":
                    sql.append(" order by \"pec_porta_ssl_in\" ").append(sortType);
                	   break;
            case "pecPortaSslOut":
                    sql.append(" order by \"pec_porta_ssl_out\" ").append(sortType);
                	   break;
            case "pecPortaTlsIn":
                    sql.append(" order by \"pec_porta_tls_in\" ").append(sortType);
                	   break;
            case "pecPortaTlsOut":
                    sql.append(" order by \"pec_porta_tls_out\" ").append(sortType);
                	   break;
            case "pecStartTlsIn":
                    sql.append(" order by \"pec_start_tls_in\" ").append(sortType);
                	   break;
            case "pecStartTlsOut":
                    sql.append(" order by \"pec_start_tls_out\" ").append(sortType);
                	   break;
            case "pecAutenticazioneIn":
                    sql.append(" order by \"pec_autenticazione_in\" ").append(sortType);
                	   break;
            case "pecAutenticazioneOut":
                    sql.append(" order by \"pec_autenticazione_out\" ").append(sortType);
                	   break;
            case "pubblicazioneStatoPratica":
                    sql.append(" order by \"pubblicazione_stato_pratica\" ").append(sortType);
                	   break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        }
        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(ConfigurazioniEnteDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"configurazioni_ente\"")
                                     .append("(\"id_organizzazione\"")
                                     .append(",\"sistema_protocollazione\"")
                                     .append(",\"protocollazione_endpoint\"")
                                     .append(",\"protocollazione_administration\"")
                                     .append(",\"protocollazione_aoo\"")
                                     .append(",\"protocollazione_register\"")
                                     .append(",\"protocollazione_user\"")
                                     .append(",\"protocollazione_password\"")
                                     .append(",\"protocollazione_hash_algorithm\"")
                                     .append(",\"sistema_pagamento\"")
                                     .append(",\"pagamento_tipo_riscossione\"")
                                     .append(",\"pagamento_cod_ente\"")
                                     .append(",\"pagamento_tipologia\"")
                                     .append(",\"pagamento_endpoint\"")
                                     .append(",\"pagamento_password\"")
                                     .append(",\"pagamento_commissione\"")
                                     .append(",\"pagamento_regex_iud\"")
                                     .append(",\"sistema_pec\"")
                                     .append(",\"pec_indirizzo\"")
                                     .append(",\"pec_nome\"")
                                     .append(",\"pec_username\"")
                                     .append(",\"pec_password\"")
                                     .append(",\"pec_host_in\"")
                                     .append(",\"pec_host_out\"")
                                     .append(",\"pec_protocollo_in\"")
                                     .append(",\"pec_protocollo_out\"")
                                     .append(",\"pec_tipo_protocollo_in\"")
                                     .append(",\"pec_tipo_protocollo_out\"")
                                     .append(",\"pec_ssl_in\"")
                                     .append(",\"pec_ssl_out\"")
                                     .append(",\"pec_tls_in\"")
                                     .append(",\"pec_tls_out\"")
                                     .append(",\"pec_porta_ssl_in\"")
                                     .append(",\"pec_porta_ssl_out\"")
                                     .append(",\"pec_porta_tls_in\"")
                                     .append(",\"pec_porta_tls_out\"")
                                     .append(",\"pec_start_tls_in\"")
                                     .append(",\"pec_start_tls_out\"")
                                     .append(",\"pec_autenticazione_in\"")
                                     .append(",\"pec_autenticazione_out\"")
                                     .append(",\"pubblicazione_stato_pratica\"")
                                     .append(",\"tipo_pratica_seduta\"")
                                     .append(",\"protocollazione_indirizzopostale\"")
                                     .append(",\"protocollazione_indirizzotelematico\"")
                                     .append(",\"protocollazione_aoo_denominazione\"")
                                     .append(",\"protocollazione_denominazione\"")
                                     .append(",\"protocollazione_tipoindirizzotelematico\"")
                                     .append(",\"protocollazione_data_registrazione\"")
                                     .append(",\"protocollazione_numero_registrazione\"")
                                     .append(",\"can_create_conferenza\"")
                                     .append(",\"indirizzi_mail_default\"")
                                     .append(",\"bilancio\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.isSistemaProtocollazione());
        parameters.add(entity.getProtocollazioneEndpoint());
        parameters.add(entity.getProtocollazioneAdministration());
        parameters.add(entity.getProtocollazioneAoo());
        parameters.add(entity.getProtocollazioneRegister());
        parameters.add(entity.getProtocollazioneUser());
        parameters.add(entity.getProtocollazionePassword());
        parameters.add(entity.getProtocollazioneHashAlgorithm());
        parameters.add(entity.isSistemaPagamento());
        parameters.add(entity.getPagamentoTipoRiscossione());
        parameters.add(entity.getPagamentoCodEnte());
        parameters.add(entity.getPagamentoTipologia());
        parameters.add(entity.getPagamentoEndPoint());
        parameters.add(entity.getPagamentoPassword());
        parameters.add(entity.getPagamentoCommissione());
        parameters.add(entity.getPagamentoRegexIud());
        parameters.add(entity.isSistemaPec());
        parameters.add(entity.getPecIndirizzo());
        parameters.add(entity.getPecNome());
        parameters.add(entity.getPecUsername());
        parameters.add(entity.getPecPassword());
        parameters.add(entity.getPecHostIn());
        parameters.add(entity.getPecHostOut());
        parameters.add(entity.getPecProtocolloIn());
        parameters.add(entity.getPecProtocolloOut());
        parameters.add(entity.getPecTipoProtocolloIn());
        parameters.add(entity.getPecTipoProtocolloOut());
        parameters.add(entity.getPecSslIn());
        parameters.add(entity.getPecSslOut());
        parameters.add(entity.getPecTlsIn());
        parameters.add(entity.getPecTlsOut());
        parameters.add(entity.getPecPortaSslIn());
        parameters.add(entity.getPecPortaSslOut());
        parameters.add(entity.getPecPortaTlsIn());
        parameters.add(entity.getPecPortaTlsOut());
        parameters.add(entity.getPecStartTlsIn());
        parameters.add(entity.getPecStartTlsOut());
        parameters.add(entity.getPecAutenticazioneIn());
        parameters.add(entity.getPecAutenticazioneOut());
        parameters.add(entity.getPubblicazioneStatoPratica());
        parameters.add(fromTpsToString(entity.getTipoPraticaSeduta()));
        parameters.add(entity.getProtocollazioneIndirizzoPostale());
        parameters.add(entity.getProtocollazioneIndirizzoTelematico());
        parameters.add(entity.getProtocollazioneAooDenominazione());
        parameters.add(entity.getProtocollazioneDenominazione());
        parameters.add(entity.getProtocollazioneTipoIndirizzoTelematico());
        parameters.add(entity.getProtocollazioneDataRegistrazione());
        parameters.add(entity.getProtocollazioneNumeroRegistrazione());
        parameters.add(entity.getCanCreateConferenza() != null ? entity.getCanCreateConferenza() : false);
        parameters.add(entity.getIndirizziMailDefault() != null && !entity.getIndirizziMailDefault().isEmpty() ? entity.getIndirizziMailDefault() : null);
        parameters.add(entity.getBilancio());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ConfigurazioniEnteDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"configurazioni_ente\"")
                                     .append(" set \"sistema_protocollazione\" = ?")
                                     .append(", \"protocollazione_endpoint\" = ?")
                                     .append(", \"protocollazione_administration\" = ?")
                                     .append(", \"protocollazione_aoo\" = ?")
                                     .append(", \"protocollazione_register\" = ?")
                                     .append(", \"protocollazione_user\" = ?")
                                     .append(", \"protocollazione_password\" = ?")
                                     .append(", \"protocollazione_hash_algorithm\" = ?")
                                     .append(", \"sistema_pagamento\" = ?")
                                     .append(", \"pagamento_tipo_riscossione\" = ?")
                                     .append(", \"pagamento_cod_ente\" = ?")
                                     .append(", \"pagamento_tipologia\" = ?")
                                     .append(", \"pagamento_endpoint\" = ?")
                                     .append(", \"pagamento_password\" = ?")
                                     .append(", \"pagamento_commissione\" = ?")
                                     .append(", \"pagamento_regex_iud\" = ?")
                                     .append(", \"sistema_pec\" = ?")
                                     .append(", \"pec_indirizzo\" = ?")
                                     .append(", \"pec_nome\" = ?")
                                     .append(", \"pec_username\" = ?")
                                     .append(", \"pec_password\" = ?")
                                     .append(", \"pec_host_in\" = ?")
                                     .append(", \"pec_host_out\" = ?")
                                     .append(", \"pec_protocollo_in\" = ?")
                                     .append(", \"pec_protocollo_out\" = ?")
                                     .append(", \"pec_tipo_protocollo_in\" = ?")
                                     .append(", \"pec_tipo_protocollo_out\" = ?")
                                     .append(", \"pec_ssl_in\" = ?")
                                     .append(", \"pec_ssl_out\" = ?")
                                     .append(", \"pec_tls_in\" = ?")
                                     .append(", \"pec_tls_out\" = ?")
                                     .append(", \"pec_porta_ssl_in\" = ?")
                                     .append(", \"pec_porta_ssl_out\" = ?")
                                     .append(", \"pec_porta_tls_in\" = ?")
                                     .append(", \"pec_porta_tls_out\" = ?")
                                     .append(", \"pec_start_tls_in\" = ?")
                                     .append(", \"pec_start_tls_out\" = ?")
                                     .append(", \"pec_autenticazione_in\" = ?")
                                     .append(", \"pec_autenticazione_out\" = ?")
                                     .append(", \"pubblicazione_stato_pratica\" = ?")
                                     .append(", \"tipo_pratica_seduta\" = ?")
                                     .append(", \"protocollazione_indirizzopostale\" = ?")
                                     .append(", \"protocollazione_indirizzotelematico\" = ?")
                                     .append(", \"protocollazione_aoo_denominazione\" = ?")
                                     .append(", \"protocollazione_denominazione\" = ?")
                                     .append(",\"protocollazione_tipoindirizzotelematico\" = ?")
                                     .append(",\"protocollazione_data_registrazione\" = ?")
                                     .append(",\"protocollazione_numero_registrazione\" = ?")
                                     .append(",\"can_create_conferenza\" = ?")
                                     .append(",\"indirizzi_mail_default\" = ?")
                                     .append(",\"bilancio\" = ?")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.isSistemaProtocollazione());
        parameters.add(entity.getProtocollazioneEndpoint());
        parameters.add(entity.getProtocollazioneAdministration());
        parameters.add(entity.getProtocollazioneAoo());
        parameters.add(entity.getProtocollazioneRegister());
        parameters.add(entity.getProtocollazioneUser());
        parameters.add(entity.getProtocollazionePassword());
        parameters.add(entity.getProtocollazioneHashAlgorithm());
        parameters.add(entity.isSistemaPagamento());
        parameters.add(entity.getPagamentoTipoRiscossione());
        parameters.add(entity.getPagamentoCodEnte());
        parameters.add(entity.getPagamentoTipologia());
        parameters.add(entity.getPagamentoEndPoint());
        parameters.add(entity.getPagamentoPassword());
        parameters.add(entity.getPagamentoCommissione());
        parameters.add(entity.getPagamentoRegexIud());
        parameters.add(entity.isSistemaPec());
        parameters.add(entity.getPecIndirizzo());
        parameters.add(entity.getPecNome());
        parameters.add(entity.getPecUsername());
        parameters.add(entity.getPecPassword());
        parameters.add(entity.getPecHostIn());
        parameters.add(entity.getPecHostOut());
        parameters.add(entity.getPecProtocolloIn());
        parameters.add(entity.getPecProtocolloOut());
        parameters.add(entity.getPecTipoProtocolloIn());
        parameters.add(entity.getPecTipoProtocolloOut());
        parameters.add(entity.getPecSslIn());
        parameters.add(entity.getPecSslOut());
        parameters.add(entity.getPecTlsIn());
        parameters.add(entity.getPecTlsOut());
        parameters.add(entity.getPecPortaSslIn());
        parameters.add(entity.getPecPortaSslOut());
        parameters.add(entity.getPecPortaTlsIn());
        parameters.add(entity.getPecPortaTlsOut());
        parameters.add(entity.getPecStartTlsIn());
        parameters.add(entity.getPecStartTlsOut());
        parameters.add(entity.getPecAutenticazioneIn());
        parameters.add(entity.getPecAutenticazioneOut());
        parameters.add(entity.getPubblicazioneStatoPratica());
        parameters.add(fromTpsToString(entity.getTipoPraticaSeduta()));
        parameters.add(entity.getProtocollazioneIndirizzoPostale());
        parameters.add(entity.getProtocollazioneIndirizzoTelematico());
        parameters.add(entity.getProtocollazioneAooDenominazione());
        parameters.add(entity.getProtocollazioneDenominazione());
        parameters.add(entity.getProtocollazioneTipoIndirizzoTelematico());
        parameters.add(entity.getProtocollazioneDataRegistrazione());
        parameters.add(entity.getProtocollazioneNumeroRegistrazione());
        parameters.add(entity.getCanCreateConferenza() != null ? entity.getCanCreateConferenza() : false);
        parameters.add(entity.getIndirizziMailDefault() != null && !entity.getIndirizziMailDefault().isEmpty() ? entity.getIndirizziMailDefault() : null);
        parameters.add(entity.getBilancio());
        parameters.add(entity.getIdOrganizzazione());
        return super.update(sql, parameters);
    }
    
    public List<Integer> getTPConVerbale(Long enteDelegato) throws Exception
    {
    	String sql = StringUtil.concateneString("select \"tipo_pratica_seduta\" ",
    											"from \"presentazione_istanza\".\"configurazioni_ente\" ",
    											"where \"id_organizzazione\" = :ente_delegato");
    	Map<String,Object> parameters = new HashMap<String, Object>();
    	parameters.put("ente_delegato", enteDelegato);
    	String listString = namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
    	List<Integer> value = new ArrayList<Integer>();
    	List<String> tmp = Arrays.asList(StringUtils.split(listString, ","));
    	if(tmp != null)
    		value = tmp.stream().filter(StringUtil::isNotEmpty).map(Integer::parseInt).collect(Collectors.toList());
    	return value;
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ConfigurazioniEnteDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"configurazioni_ente\"")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        return super.update(sql, parameters);
    }
    
    private String fromTpsToString(List<Integer> tipoProceduraSeduta)
    {
    	String s = null;
    	if(tipoProceduraSeduta != null && !tipoProceduraSeduta.isEmpty())
    		s = ","+tipoProceduraSeduta.stream().map(m -> m.toString()).collect(Collectors.joining(","))+",";
    	return s;
    }

    public List<TipologicaIntegerBooleanDto> visibilitaStatoAvanzamento(HashSet<Integer> idOrganizzazioni) 
    {
    	String sql = StringUtil.concateneString("select \"id_organizzazione\" AS \"intero\", \"pubblicazione_stato_pratica\" AS \"booleano\" ",
												"from \"presentazione_istanza\".\"configurazioni_ente\" ",
												"where \"id_organizzazione\" IN (:idOrganizzazioni)");
    	Map<String,Object> parameters = new HashMap<String, Object>();
    	parameters.put("idOrganizzazioni", idOrganizzazioni);
    //	final List<Object> parameters = new ArrayList<Object>(1);
    //  parameters.add(0, (ArrayList<Integer>) new ArrayList<Integer>(idOrganizzazioni));
    	logger.info("Eseguo questa query: ["+sql+"] con questi parametri: [?="+idOrganizzazioni+"]");
    	return namedJdbcTemplateRead.query(sql, parameters, new TipologicaIntegerBooleanRowMapper());
    // 	return super.queryForListTxRead(sql, new TipologicaIntegerBooleanRowMapper(), parameters);
    }
    
    public Boolean canCreateConferenza(final int idOrganizzazione) {
    	final String sql = new StringBuilder("select cfg.\"can_create_conferenza\" from \"presentazione_istanza\".\"configurazioni_ente\" cfg")
    			.append(" where \"id_organizzazione\" = ?")
    			.toString();
    	final List<Object> parameters = new ArrayList<Object>();
    	parameters.add(idOrganizzazione);
    	return super.queryForObjectTxRead(sql, Boolean.class, parameters);
    }
    
    public String getIndirizziMailDefault(final int id) {
    	final String sql = new StringBuilder("select cfg.\"indirizzi_mail_default\" from \"presentazione_istanza\".\"configurazioni_ente\" cfg")
    			.append(" where\"id_organizzazione\" = ?")
    			.toString();
    	final List<Object> parameters = new ArrayList<Object>();
    	parameters.add(id);
    	return super.queryForObjectTxRead(sql, String.class, parameters);
    }
    
}