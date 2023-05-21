package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.*;
import java.math.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import it.eng.tz.puglia.bean.PaginatedList;
import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.*;

/**
 * Repository for table presentazione_istanza.protocollo
 */
@Repository
public class ProtocolloRepository extends GenericCrudDao<ProtocolloDTO, ProtocolloSearch>{

    private final ProtocolloRowMapper rowMapper = new ProtocolloRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_protocollo\"")
                                     .append(",\"codiceAmministrazione\"")
                                     .append(",\"codiceAOO\"")
                                     .append(",\"codiceRegistro\"")
                                     .append(",\"dataRegistrazione\"")
                                     .append(",\"numeroProtocollo\"")
                                     .append(",\"hostProxy\"")
                                     .append(",\"portProxy\"")
                                     .append(",\"usernameProxy\"")
                                     .append(",\"passwordProxy\"")
                                     .append(",\"urlNotProxy\"")
                                     .append(",\"clientProtocolloEndpoint\"")
                                     .append(",\"clientProtocolloAdministration\"")
                                     .append(",\"clientProtocolloAOO\"")
                                     .append(",\"clientProtocolloRegister\"")
                                     .append(",\"clientProtocolloUser\"")
                                     .append(",\"clientProtocolloPassword\"")
                                     .append(",\"clientProtocolloHashAlgorihtm\"")
                                     .append(",\"protocolObject\"")
                                     .append(",\"request\"")
                                     .append(",\"response\"")
                                     .append(",\"protocollo\"")
                                     .append(",\"verso\"")
                                     .append(",\"data_protocollo\"")
                                     .append(",\"data_esecuzione\"")
                                     .append(",\"id_allegato\"")
                                     .append(",\"id_pratica\"")
                                     .append(" from \"presentazione_istanza\".\"protocollo\"")
                                     .toString();
    @Override
    public List<ProtocolloDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"protocollo\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ProtocolloDTO find(ProtocolloDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_protocollo\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdProtocollo());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<ProtocolloDTO> search(ProtocolloSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getIdProtocollo())){
            sql.append(sep).append("lower(\"id_protocollo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdProtocollo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceamministrazione())){
            sql.append(sep).append("lower(\"codiceAmministrazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodiceamministrazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceaoo())){
            sql.append(sep).append("lower(\"codiceAOO\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodiceaoo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceregistro())){
            sql.append(sep).append("lower(\"codiceRegistro\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodiceregistro()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDataregistrazione())){
            sql.append(sep).append("lower(\"dataRegistrazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDataregistrazione()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNumeroprotocollo())){
            sql.append(sep).append("lower(\"numeroProtocollo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNumeroprotocollo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getHostproxy())){
            sql.append(sep).append("lower(\"hostProxy\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getHostproxy()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPortproxy())){
            sql.append(sep).append("lower(\"portProxy\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPortproxy()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUsernameproxy())){
            sql.append(sep).append("lower(\"usernameProxy\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUsernameproxy()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPasswordproxy())){
            sql.append(sep).append("lower(\"passwordProxy\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPasswordproxy()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getUrlnotproxy())){
            sql.append(sep).append("lower(\"urlNotProxy\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getUrlnotproxy()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getClientprotocolloendpoint())){
            sql.append(sep).append("lower(\"clientProtocolloEndpoint\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getClientprotocolloendpoint()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getClientprotocolloadministration())){
            sql.append(sep).append("lower(\"clientProtocolloAdministration\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getClientprotocolloadministration()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getClientprotocolloaoo())){
            sql.append(sep).append("lower(\"clientProtocolloAOO\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getClientprotocolloaoo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getClientprotocolloregister())){
            sql.append(sep).append("lower(\"clientProtocolloRegister\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getClientprotocolloregister()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getClientprotocollouser())){
            sql.append(sep).append("lower(\"clientProtocolloUser\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getClientprotocollouser()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getClientprotocollopassword())){
            sql.append(sep).append("lower(\"clientProtocolloPassword\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getClientprotocollopassword()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getClientprotocollohashalgorihtm())){
            sql.append(sep).append("lower(\"clientProtocolloHashAlgorihtm\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getClientprotocollohashalgorihtm()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocolobject())){
            sql.append(sep).append("lower(\"protocolObject\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocolobject()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idProtocollo":
                    sql.append(" order by \"id_protocollo\" ").append(sortType);
                	   break;
            case "codiceamministrazione":
                    sql.append(" order by \"codiceAmministrazione\" ").append(sortType);
                	   break;
            case "codiceaoo":
                    sql.append(" order by \"codiceAOO\" ").append(sortType);
                	   break;
            case "codiceregistro":
                    sql.append(" order by \"codiceRegistro\" ").append(sortType);
                	   break;
            case "dataregistrazione":
                    sql.append(" order by \"dataRegistrazione\" ").append(sortType);
                	   break;
            case "numeroprotocollo":
                    sql.append(" order by \"numeroProtocollo\" ").append(sortType);
                	   break;
            case "hostproxy":
                    sql.append(" order by \"hostProxy\" ").append(sortType);
                	   break;
            case "portproxy":
                    sql.append(" order by \"portProxy\" ").append(sortType);
                	   break;
            case "usernameproxy":
                    sql.append(" order by \"usernameProxy\" ").append(sortType);
                	   break;
            case "passwordproxy":
                    sql.append(" order by \"passwordProxy\" ").append(sortType);
                	   break;
            case "urlnotproxy":
                    sql.append(" order by \"urlNotProxy\" ").append(sortType);
                	   break;
            case "clientprotocolloendpoint":
                    sql.append(" order by \"clientProtocolloEndpoint\" ").append(sortType);
                	   break;
            case "clientprotocolloadministration":
                    sql.append(" order by \"clientProtocolloAdministration\" ").append(sortType);
                	   break;
            case "clientprotocolloaoo":
                    sql.append(" order by \"clientProtocolloAOO\" ").append(sortType);
                	   break;
            case "clientprotocolloregister":
                    sql.append(" order by \"clientProtocolloRegister\" ").append(sortType);
                	   break;
            case "clientprotocollouser":
                    sql.append(" order by \"clientProtocolloUser\" ").append(sortType);
                	   break;
            case "clientprotocollopassword":
                    sql.append(" order by \"clientProtocolloPassword\" ").append(sortType);
                	   break;
            case "clientprotocollohashalgorihtm":
                    sql.append(" order by \"clientProtocolloHashAlgorihtm\" ").append(sortType);
                	   break;
            case "protocolobject":
                    sql.append(" order by \"protocolObject\" ").append(sortType);
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
    public long insert(ProtocolloDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"protocollo\"")
                                     .append("(\"id_protocollo\"")
                                     .append(",\"codiceAmministrazione\"")
                                     .append(",\"codiceAOO\"")
                                     .append(",\"codiceRegistro\"")
                                     .append(",\"dataRegistrazione\"")
                                     .append(",\"numeroProtocollo\"")
                                     .append(",\"hostProxy\"")
                                     .append(",\"portProxy\"")
                                     .append(",\"usernameProxy\"")
                                     .append(",\"passwordProxy\"")
                                     .append(",\"urlNotProxy\"")
                                     .append(",\"clientProtocolloEndpoint\"")
                                     .append(",\"clientProtocolloAdministration\"")
                                     .append(",\"clientProtocolloAOO\"")
                                     .append(",\"clientProtocolloRegister\"")
                                     .append(",\"clientProtocolloUser\"")
                                     .append(",\"clientProtocolloPassword\"")
                                     .append(",\"clientProtocolloHashAlgorihtm\"")
                                     .append(",\"protocolObject\"")
                                     .append(",\"request\"")
                                     .append(",\"response\"")
                                     .append(",\"protocollo\"")
                                     .append(",\"verso\"")
                                     .append(",\"data_esecuzione\"")
                                     .append(",\"id_allegato\"")
                                     .append(",\"id_pratica\"")
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
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdProtocollo());
        parameters.add(entity.getCodiceamministrazione());
        parameters.add(entity.getCodiceaoo());
        parameters.add(entity.getCodiceregistro());
        parameters.add(entity.getDataregistrazione());
        parameters.add(entity.getNumeroprotocollo());
        parameters.add(entity.getHostproxy());
        parameters.add(entity.getPortproxy());
        parameters.add(entity.getUsernameproxy());
        parameters.add(entity.getPasswordproxy());
        parameters.add(entity.getUrlnotproxy());
        parameters.add(entity.getClientprotocolloendpoint());
        parameters.add(entity.getClientprotocolloadministration());
        parameters.add(entity.getClientprotocolloaoo());
        parameters.add(entity.getClientprotocolloregister());
        parameters.add(entity.getClientprotocollouser());
        parameters.add(entity.getClientprotocollopassword());
        parameters.add(entity.getClientprotocollohashalgorihtm());
        parameters.add(entity.getProtocolobject());
        parameters.add(entity.getRequest());
        parameters.add(entity.getResponse());
        parameters.add(entity.getProtocollo());
        parameters.add(entity.getVerso());
        parameters.add(entity.getDataEsecuzione());
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getIdPratica());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ProtocolloDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"protocollo\"")
                                     .append(" set \"codiceAmministrazione\" = ?")
                                     .append(", \"codiceAOO\" = ?")
                                     .append(", \"codiceRegistro\" = ?")
                                     .append(", \"dataRegistrazione\" = ?")
                                     .append(", \"numeroProtocollo\" = ?")
                                     .append(", \"hostProxy\" = ?")
                                     .append(", \"portProxy\" = ?")
                                     .append(", \"usernameProxy\" = ?")
                                     .append(", \"passwordProxy\" = ?")
                                     .append(", \"urlNotProxy\" = ?")
                                     .append(", \"clientProtocolloEndpoint\" = ?")
                                     .append(", \"clientProtocolloAdministration\" = ?")
                                     .append(", \"clientProtocolloAOO\" = ?")
                                     .append(", \"clientProtocolloRegister\" = ?")
                                     .append(", \"clientProtocolloUser\" = ?")
                                     .append(", \"clientProtocolloPassword\" = ?")
                                     .append(", \"clientProtocolloHashAlgorihtm\" = ?")
                                     .append(", \"protocolObject\" = ?")
                                     .append("  \"request\" = ?")
                                     .append(", \"response\" = ?")
                                     .append(", \"protocollo\" = ?")
                                     .append(", \"verso\" = ?")
                                     .append(", \"data_esecuzione\" = ?")
                                     .append(", \"id_allegato\" = ?")
                                     .append(", \"id_pratica\" = ?")
                                     .append(" where \"id_protocollo\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getCodiceamministrazione());
        parameters.add(entity.getCodiceaoo());
        parameters.add(entity.getCodiceregistro());
        parameters.add(entity.getDataregistrazione());
        parameters.add(entity.getNumeroprotocollo());
        parameters.add(entity.getHostproxy());
        parameters.add(entity.getPortproxy());
        parameters.add(entity.getUsernameproxy());
        parameters.add(entity.getPasswordproxy());
        parameters.add(entity.getUrlnotproxy());
        parameters.add(entity.getClientprotocolloendpoint());
        parameters.add(entity.getClientprotocolloadministration());
        parameters.add(entity.getClientprotocolloaoo());
        parameters.add(entity.getClientprotocolloregister());
        parameters.add(entity.getClientprotocollouser());
        parameters.add(entity.getClientprotocollopassword());
        parameters.add(entity.getClientprotocollohashalgorihtm());
        parameters.add(entity.getProtocolobject());
        parameters.add(entity.getRequest());
        parameters.add(entity.getResponse());
        parameters.add(entity.getProtocollo());
        parameters.add(entity.getVerso());
        parameters.add(entity.getDataEsecuzione());
        parameters.add(entity.getIdAllegato());
        parameters.add(entity.getIdPratica());
        parameters.add(entity.getIdProtocollo());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ProtocolloDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"protocollo\"")
                                     .append(" where \"id_protocollo\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdProtocollo());
        return super.update(sql, parameters);
    }

}
