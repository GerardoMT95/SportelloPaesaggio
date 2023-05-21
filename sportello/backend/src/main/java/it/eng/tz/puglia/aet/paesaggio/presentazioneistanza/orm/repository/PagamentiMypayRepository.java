package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.*;
import java.math.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PagamentiMypayDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PagamentiMypaySearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.rowmapper.*;

/**
 * Repository for table presentazione_istanza.pagamenti_mypay
 */
@Repository
public class PagamentiMypayRepository extends GenericCrudDao<PagamentiMypayDTO, PagamentiMypaySearch>{

    private final PagamentiMypayRowMapper rowMapper = new PagamentiMypayRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"iud\"")
                                     .append(",\"pratica_id\"")
                                     .append(",\"ente_id\"")
                                     .append(",\"causale\"")
                                     .append(",\"cod_ipa_ente\"")
                                     .append(",\"cf_pagatore\"")
                                     .append(",\"email_pagatore\"")
                                     .append(",\"ret_url\"")
                                     .append(",\"sogg_paga\"")
                                     .append(",\"tipologia\"")
                                     .append(",\"tipo_riscossione\"")
                                     .append(",\"stato\"")
                                     .append(",\"data_inserimento\"")
                                     .append(",\"data_modifica\"")
                                     .append(",\"importo\"")
                                     .append(",\"idsession\"")
                                     .append(",\"esito\"")
                                     .append(",\"error\"")
                                     .append(",\"url_to_pay\"")
                                     .append(",\"cfg_endpoint_mypay\"")
                                     .append(",\"cfg_password_mypay\"")
                                     .append(" from \"presentazione_istanza\".\"pagamenti_mypay\"")
                                     .toString();
    @Override
    public List<PagamentiMypayDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"pagamenti_mypay\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public PagamentiMypayDTO find(PagamentiMypayDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"iud\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIud());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<PagamentiMypayDTO> search(PagamentiMypaySearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIud())){
            sql.append(sep).append("lower(\"iud\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIud()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPraticaId())){
            sql.append(sep).append("\"pratica_id\"::text  = ?");
            parameters.add(search.getPraticaId());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEnteId())){
            sql.append(sep).append("lower(\"ente_id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEnteId()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCausale())){
            sql.append(sep).append("lower(\"causale\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCausale()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodIpaEnte())){
            sql.append(sep).append("lower(\"cod_ipa_ente\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodIpaEnte()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCfPagatore())){
            sql.append(sep).append("lower(\"cf_pagatore\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCfPagatore()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEmailPagatore())){
            sql.append(sep).append("lower(\"email_pagatore\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEmailPagatore()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getRetUrl())){
            sql.append(sep).append("lower(\"ret_url\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getRetUrl()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSoggPaga())){
            sql.append(sep).append("lower(\"sogg_paga\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getSoggPaga()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipologia())){
            sql.append(sep).append("lower(\"tipologia\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipologia()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipoRiscossione())){
            sql.append(sep).append("lower(\"tipo_riscossione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTipoRiscossione()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getStato())){
            sql.append(sep).append("lower(\"stato\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getStato()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataInserimento())){
            sql.append(sep).append("lower(\"data_inserimento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataInserimento()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataModifica())){
            sql.append(sep).append("lower(\"data_modifica\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataModifica()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getImporto())){
            sql.append(sep).append("lower(\"importo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getImporto()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdsession())){
            sql.append(sep).append("lower(\"idsession\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdsession()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getEsito())){
            sql.append(sep).append("lower(\"esito\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEsito()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getError())){
            sql.append(sep).append("lower(\"error\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getError()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "iud":
                    sql.append(" order by \"iud\" ").append(sortType);
                	   break;
            case "praticaId":
                    sql.append(" order by \"pratica_id\" ").append(sortType);
                	   break;
            case "enteId":
                    sql.append(" order by \"ente_id\" ").append(sortType);
                	   break;
            case "causale":
                    sql.append(" order by \"causale\" ").append(sortType);
                	   break;
            case "codIpaEnte":
                    sql.append(" order by \"cod_ipa_ente\" ").append(sortType);
                	   break;
            case "cfPagatore":
                    sql.append(" order by \"cf_pagatore\" ").append(sortType);
                	   break;
            case "emailPagatore":
                    sql.append(" order by \"email_pagatore\" ").append(sortType);
                	   break;
            case "retUrl":
                    sql.append(" order by \"ret_url\" ").append(sortType);
                	   break;
            case "soggPaga":
                    sql.append(" order by \"sogg_paga\" ").append(sortType);
                	   break;
            case "tipologia":
                    sql.append(" order by \"tipologia\" ").append(sortType);
                	   break;
            case "tipoRiscossione":
                    sql.append(" order by \"tipo_riscossione\" ").append(sortType);
                	   break;
            case "stato":
                    sql.append(" order by \"stato\" ").append(sortType);
                	   break;
            case "dataInserimento":
                    sql.append(" order by \"data_inserimento\" ").append(sortType);
                	   break;
            case "dataModifica":
                    sql.append(" order by \"data_modifica\" ").append(sortType);
                	   break;
            case "importo":
                    sql.append(" order by \"importo\" ").append(sortType);
                	   break;
            case "idsession":
                    sql.append(" order by \"idsession\" ").append(sortType);
                	   break;
            case "esito":
                    sql.append(" order by \"esito\" ").append(sortType);
                	   break;
            case "error":
                    sql.append(" order by \"error\" ").append(sortType);
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
    public long insert(PagamentiMypayDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pagamenti_mypay\"")
                                     .append("(\"iud\"")
                                     .append(",\"pratica_id\"")
                                     .append(",\"ente_id\"")
                                     .append(",\"causale\"")
                                     .append(",\"cod_ipa_ente\"")
                                     .append(",\"cf_pagatore\"")
                                     .append(",\"email_pagatore\"")
                                     .append(",\"ret_url\"")
                                     .append(",\"sogg_paga\"")
                                     .append(",\"tipologia\"")
                                     .append(",\"tipo_riscossione\"")
                                     .append(",\"stato\"")
                                     .append(",\"data_modifica\"")
                                     .append(",\"importo\"")
                                     .append(",\"idsession\"")
                                     .append(",\"esito\"")
                                     .append(",\"error\"")
                                     .append(",\"url_to_pay\"")
                                     .append(",\"cfg_endpoint_mypay\"")
                                     .append(",\"cfg_password_mypay\"")
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
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIud());
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getEnteId());
        parameters.add(entity.getCausale());
        parameters.add(entity.getCodIpaEnte());
        parameters.add(entity.getCfPagatore());
        parameters.add(entity.getEmailPagatore());
        parameters.add(entity.getRetUrl());
        parameters.add(entity.getSoggPaga());
        parameters.add(entity.getTipologia());
        parameters.add(entity.getTipoRiscossione());
        parameters.add(entity.getStato());
        parameters.add(entity.getDataInserimento());
        parameters.add(entity.getImporto());
        parameters.add(entity.getIdsession());
        parameters.add(entity.getEsito());
        parameters.add(entity.getError());
        parameters.add(entity.getUrlToPay());
        parameters.add(entity.getCfgEndpointMyPay());
        parameters.add(entity.getCfgPasswordMyPay());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(PagamentiMypayDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"pagamenti_mypay\"")
                                     .append(" set \"pratica_id\" = ?")
                                     .append(", \"ente_id\" = ?")
                                     .append(", \"causale\" = ?")
                                     .append(", \"cod_ipa_ente\" = ?")
                                     .append(", \"cf_pagatore\" = ?")
                                     .append(", \"email_pagatore\" = ?")
                                     .append(", \"ret_url\" = ?")
                                     .append(", \"sogg_paga\" = ?")
                                     .append(", \"tipologia\" = ?")
                                     .append(", \"tipo_riscossione\" = ?")
                                     .append(", \"stato\" = ?")
                                     .append(", \"data_modifica\" = ?")
                                     .append(", \"importo\" = ?")
                                     .append(", \"idsession\" = ?")
                                     .append(", \"esito\" = ?")
                                     .append(", \"error\" = ?")
                                     .append(", \"url_to_pay\" = ?")
                                     .append(" where \"iud\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getPraticaId());
        parameters.add(entity.getEnteId());
        parameters.add(entity.getCausale());
        parameters.add(entity.getCodIpaEnte());
        parameters.add(entity.getCfPagatore());
        parameters.add(entity.getEmailPagatore());
        parameters.add(entity.getRetUrl());
        parameters.add(entity.getSoggPaga());
        parameters.add(entity.getTipologia());
        parameters.add(entity.getTipoRiscossione());
        parameters.add(entity.getStato());
        parameters.add(entity.getDataModifica());
        parameters.add(entity.getImporto());
        parameters.add(entity.getIdsession());
        parameters.add(entity.getEsito());
        parameters.add(entity.getError());
        parameters.add(entity.getUrlToPay());
        parameters.add(entity.getIud());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(PagamentiMypayDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pagamenti_mypay\"")
                                     .append(" where \"iud\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIud());
        return super.update(sql, parameters);
    }
    
    public Map<String, BigDecimal> getTotaliPerStato(UUID praticaId){
        final String sql = new StringBuilder("select sum(\"importo\") as imp, \"stato\" from \"presentazione_istanza\".\"pagamenti_mypay\"")
                                     .append(" where \"pratica_id\" = ?  group by \"stato\" ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        List<Map<String, Object>> ret = super.queryForListTxRead(sql,parameters);
        Map<String,BigDecimal> mapOut=new HashMap<>();
        if(ret!=null) {
        	ret.forEach(mapRs->{
        		mapOut.put((String) mapRs.get("stato"),(BigDecimal)mapRs.get("imp"));
        	});
        }
        return mapOut;
    }

}
