package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.dto.FascicoloTipiProcedimentoDTO;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Applicativo;
import it.eng.tz.puglia.autpae.generated.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.autpae.rowmapper.FascicoloTipiProcedimentoRowMapper;
import it.eng.tz.puglia.autpae.rowmapper.TipoProcedimentoRowMapper;
import it.eng.tz.puglia.autpae.search.FascicoloTipiProcedimentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table autpae.fascicolo_tipi_procedimento
 */
@Repository
public class FascicoloTipiProcedimentoRepository extends GenericCrudDao<FascicoloTipiProcedimentoDTO, FascicoloTipiProcedimentoSearch>{

    private final FascicoloTipiProcedimentoRowMapper rowMapper = new FascicoloTipiProcedimentoRowMapper();
    private final TipoProcedimentoRowMapper tipoProcedimentoRowMapper = new TipoProcedimentoRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"id_fascicolo\"")
                                     .append(",\"codice_tipo_procedimento\"")
                                     .append(",\"inizio_validita\"")
                                     .append(",\"fine_validita\"")
                                     .append(" from  \"fascicolo_tipi_procedimento\"")
                                     .toString();
    @Override
    public List<FascicoloTipiProcedimentoDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * Metodo che restituisce la lista di tipi procedimento associati al fascicolo il cui id è ricevuto
     * come parametro. La lista contiene i tipi procedimento che erano presenti e non scaduti al momento 
     * della creazione del fascicolo
     * @author Giuseppe Canciello
     * @date 12 mag 2021
     * @param idFascicolo
     * @return
     */
    public List<TipoProcedimentoDTO> selectTipiProcedimentoByIdFascicolo(Long idFascicolo){
    	String sql= StringUtil.concateneString("SELECT tp.codice as ",TipoProcedimento.codice.columnName()
    										  ,", tp.descrizione as ",TipoProcedimento.descrizione.columnName()
    										  ,", ftp.inizio_validita as ",TipoProcedimento.inizio_validita.columnName()
    										  ,", ftp.fine_validita as ",TipoProcedimento.fine_validita.columnName()
    										  ,", tp.applicativo as ",TipoProcedimento.applicativo.columnName()
    										  ,", tp.invia_email as ",TipoProcedimento.invia_email.columnName()
    										  ,", tp.sanatoria as ",TipoProcedimento.sanatoria.columnName()
    										  ," FROM fascicolo_tipi_procedimento ftp"
    										  ," INNER JOIN tipo_procedimento tp"
    										  ," ON tp.codice=ftp.codice_tipo_procedimento"
    										  ," WHERE ftp.id_fascicolo=?");
    	List<Object> parameters= new ArrayList<Object>();
    	parameters.add(idFascicolo);
        return super.queryForList(sql, this.tipoProcedimentoRowMapper,parameters);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"fascicolo_tipi_procedimento\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public FascicoloTipiProcedimentoDTO find(FascicoloTipiProcedimentoDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getId());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<FascicoloTipiProcedimentoDTO> search(FascicoloTipiProcedimentoSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(StringUtil.isNotEmpty(search.getId())){
            sql.append(sep).append("lower(\"id\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getId()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getIdFascicolo())){
            sql.append(sep).append("lower(\"id_fascicolo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getIdFascicolo()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodiceTipoProcedimento())){
            sql.append(sep).append("lower(\"codice_tipo_procedimento\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCodiceTipoProcedimento()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getInizioValidita())){
            sql.append(sep).append("lower(\"inizio_validita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getInizioValidita()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getFineValidita())){
            sql.append(sep).append("lower(\"fine_validita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getFineValidita()));
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "id":
                    sql.append(" sort by \"id\" ").append(sortType);
            case "idFascicolo":
                    sql.append(" sort by \"id_fascicolo\" ").append(sortType);
            case "codiceTipoProcedimento":
                    sql.append(" sort by \"codice_tipo_procedimento\" ").append(sortType);
            case "inizioValidita":
                    sql.append(" sort by \"inizio_validita\" ").append(sortType);
            case "fineValidita":
                    sql.append(" sort by \"fine_validita\" ").append(sortType);
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
    public long insert(FascicoloTipiProcedimentoDTO entity){
        final String sql = new StringBuilder("insert into \"fascicolo_tipi_procedimento\"")
                                     .append("(\"id_fascicolo\"")
                                     .append(",\"codice_tipo_procedimento\"")
                                     .append(",\"inizio_validita\"")
                                     .append(",\"fine_validita\"")
                                     .append(") values ")
                                     .append("(?")
                                     .append(",?")
                                     .append(",?")
                                     .append(",?")
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdFascicolo());
        parameters.add(entity.getCodiceTipoProcedimento());
        parameters.add(entity.getInizioValidita());
        parameters.add(entity.getFineValidita());
        return super.insertAndGetAutoincrementValue(sql, parameters, "id");
    }
    
    
    /**
     * Metodo che dato un id fascicolo ed un codice applicazione inserisce nella tabella fascicolo_tipi_procedimento
     * tutti i tipi procedimento attualmente presenti in tabella tipo_procedimento con le relative scadenze. Questo per avere
     * una sorta di "storico" dei procedimenti validi al momento della creazione del fascicolo e poter quindi usare quegli stessi
     * tipi procedimento in una eventuale fase successiva di editing del fascicolo
     * @author Giuseppe Canciello
     * @date 10 mag 2021
     * @param idFascicolo
     * @param codiceApplicazione
     * @return
     */ 
    public int insertOnCreateFascicolo(Long idFascicolo, Applicativo codiceApplicazione){
        final String sql = new StringBuilder("insert into \"fascicolo_tipi_procedimento\"")
                                     .append("(\"id_fascicolo\"")
                                     .append(",\"codice_tipo_procedimento\"")
                                     .append(",\"inizio_validita\"")
                                     .append(",\"fine_validita\")")
                                     .append(" SELECT ?, tp.codice ,tp.inizio_validita, tp.fine_Validita ")
                                     .append(" FROM tipo_procedimento tp ")
                                     .append(" WHERE tp.applicativo=? and inizio_validita<= ? and fine_validita>=? ")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idFascicolo);
        parameters.add(codiceApplicazione.name());
        parameters.add(new Date());
        parameters.add(new Date());
        return super.update(sql, parameters);
    }
    
    /**
     * 
     * @autor Adriano Colaianni
     * @date 9 set 2021
     * @param idFascicolo
     * @param codiceApplicazione
     * @param dataCreazione
     * @param tipoProcedimentoStorico codice procedimento presente nella pratica migrata.
     * @return
     */
    public int insertOnMigrateFascicolo(Long idFascicolo, Applicativo codiceApplicazione,Date dataCreazione,String tipoProcedimentoStorico){
        final String sql = new StringBuilder("insert into \"fascicolo_tipi_procedimento\"")
                                     .append("(\"id_fascicolo\"")
                                     .append(",\"codice_tipo_procedimento\"")
                                     .append(",\"inizio_validita\"")
                                     .append(",\"fine_validita\")")
                                     .append(" SELECT ?, tp.codice ,tp.inizio_validita, tp.fine_Validita ")
                                     .append(" FROM tipo_procedimento tp ")
                                     .append(" WHERE tp.applicativo=? and ((inizio_validita<= ? and fine_validita>=? ) or tp.codice= ? )")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(idFascicolo);
        parameters.add(codiceApplicazione.name());
        parameters.add(dataCreazione);
        parameters.add(dataCreazione);
        parameters.add(tipoProcedimentoStorico);
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(FascicoloTipiProcedimentoDTO entity){
        final String sql = new StringBuilder("update \"fascicolo_tipi_procedimento\"")
                                     .append(" set \"id_fascicolo\" = ?")
                                     .append(", \"codice_tipo_procedimento\" = ?")
                                     .append(", \"inizio_validita\" = ?")
                                     .append(", \"fine_validita\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdFascicolo());
        parameters.add(entity.getCodiceTipoProcedimento());
        parameters.add(entity.getInizioValidita());
        parameters.add(entity.getFineValidita());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(FascicoloTipiProcedimentoDTO entity){
        final String sql = new StringBuilder("delete from \"fascicolo_tipi_procedimento\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

	/**
	 * Metodo che ritorna true se per un dato fascicolo, il tipo procedimento ricevuto in input è valido
	 * (cioè se era valido e non scaduto al momento della creazione del fascicolo, ed è quindi utilizzabile in fase di editing) 
	 * @author Giuseppe Canciello
	 * @date 14 mag 2021
	 * @param idFascicolo
	 * @return
	 */
    public boolean isTipoProcedimentoValidoForFascicolo(Long idFascicolo, String codiceTipoProcedimento){
    	String sql= StringUtil.concateneString("SELECT count(*)"
    										  ," FROM fascicolo_tipi_procedimento ftp"
    										  ," WHERE ftp.id_fascicolo=?"
    										  ," AND ftp.codice_tipo_procedimento=?");
    	List<Object> parameters= new ArrayList<Object>();
    	parameters.add(idFascicolo);
    	parameters.add(codiceTipoProcedimento);
        int result=super.queryForObjectTxRead(sql, Integer.class,parameters);
        if (result==1) {
        	return true;
        }else {
        	return false;
        }
    }

}
