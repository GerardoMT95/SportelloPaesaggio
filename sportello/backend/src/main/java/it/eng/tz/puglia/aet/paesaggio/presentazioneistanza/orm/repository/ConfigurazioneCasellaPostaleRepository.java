package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.*;
import java.math.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.configurazione_casella_postale
 */
@Repository
public class ConfigurazioneCasellaPostaleRepository extends GenericCrudDao<ConfigurazioneCasellaPostaleDTO, ConfigurazioneCasellaPostaleSearch>{
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;
	
    private final ConfigurazioneCasellaPostaleRowMapper rowMapper = new ConfigurazioneCasellaPostaleRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"email\"")
                                     .append(",\"configurazione\"")
                                     .append(" from \"presentazione_istanza\".\"configurazione_casella_postale\"")
                                     .toString();
    @Override
    public List<ConfigurazioneCasellaPostaleDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"configurazione_casella_postale\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ConfigurazioneCasellaPostaleDTO find(ConfigurazioneCasellaPostaleDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"email\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getEmail());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<ConfigurazioneCasellaPostaleDTO> search(ConfigurazioneCasellaPostaleSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getEmail())){
            sql.append(sep).append("lower(\"email\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getEmail()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getConfigurazione())){
            sql.append(sep).append("lower(\"configurazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getConfigurazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "email":
                    sql.append(" order by \"email\" ").append(sortType);
                	   break;
            case "configurazione":
                    sql.append(" order by \"configurazione\" ").append(sortType);
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
    public long insert(ConfigurazioneCasellaPostaleDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"configurazione_casella_postale\"")
                                     .append("(\"email\"")
                                     .append(",\"configurazione\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getEmail());
        parameters.add(entity.getConfigurazione());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ConfigurazioneCasellaPostaleDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"configurazione_casella_postale\"")
                                     .append(" set \"configurazione\" = ?")
                                     .append(" where \"email\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getConfigurazione());
        parameters.add(entity.getEmail());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ConfigurazioneCasellaPostaleDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"configurazione_casella_postale\"")
                                     .append(" where \"email\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getEmail());
        return super.update(sql, parameters);
    }
    
    /**
     * Lista delle email presenti in congiurazione email che non sono presenti nelle configurazioni enti
     * e che non sono l'email principale
     *
     * @author G.Lavermicocca
     * @date 21 set 2020
     */
    
    public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaDisattivare(){
        final String sql = new StringBuilder()
                                     .append("select * " + 
                                     		"from  presentazione_istanza.configurazione_casella_postale ccp " + 
                                     		"where ccp.email not in( " + 
                                     		"	select ce.pec_indirizzo " + 
                                     		"	from presentazione_istanza.configurazioni_ente ce " + 
                                     		"	where ce.pec_indirizzo is not null " + 
                                     		") AND ccp.email not like ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(ccpd.getCasellaPostale().getIndirizzoMail());
        return super.queryForList(sql, this.rowMapper,parameters);
    }
    
    /**
     * Lista delle email attive compresa quella principale
     *
     * @author G.Lavermicocca
     * @date 21 set 2020
     */
	public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaAttivare() {
		 final String sql = new StringBuilder()
                 .append("select * " + 
                 		"from  presentazione_istanza.configurazione_casella_postale ccp " + 
                 		"where ccp.email in( " + 
                 		"	select ce.pec_indirizzo " + 
                 		"	from presentazione_istanza.configurazioni_ente ce " + 
                 		"	where ce.pec_indirizzo is not null " + 
                 		") OR ccp.email like ?")
                 .toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(ccpd.getCasellaPostale().getIndirizzoMail());
		return super.queryForListTxRead(sql, this.rowMapper,parameters);
	}

}
