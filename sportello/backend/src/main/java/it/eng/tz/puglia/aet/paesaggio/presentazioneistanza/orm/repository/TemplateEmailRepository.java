package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TemplateEmailRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateEmailSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.template_email
 */
@Repository
public class TemplateEmailRepository extends GenericCrudDao<TemplateEmailDTO, TemplateEmailSearch>{

    private final TemplateEmailRowMapper rowMapper = new TemplateEmailRowMapper();
    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id_organizzazione\"")
                                     .append(",\"codice\"")
                                     .append(",\"nome\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"oggetto\"")
                                     .append(",\"testo\"")
                                     .append(",\"readonly_oggetto\"")
                                     .append(",\"readonly_testo\"")
                                     .append(",\"visibilita\"")
                                     .append(",\"sezione\"")
                                     .append(",\"tipi_procedimento_ammessi\"")
                                     .append(",\"protocollazione\"")
                                     .append(",\"placeholders\"")
                                     .append(",\"cancellabile\"")
                                     .append(",\"riservata\"")
                                     .append(",\"sotto_sezione\"")
                                     .append(" from \"presentazione_istanza\".\"template_email\"")
                                     .toString();
    @Override
    public List<TemplateEmailDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"template_email\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public TemplateEmailDTO find(TemplateEmailDTO pk){
        final String sql = new StringBuilder(selectAll)
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk.getIdOrganizzazione());
        parameters.add(pk.getCodice());
        return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
    }
    
    public TemplateEmailDTO find(Integer idOrganizzazione, String codice)
    {
    	TemplateEmailDTO pk = new TemplateEmailDTO();
    	pk.setIdOrganizzazione(idOrganizzazione);
    	pk.setCodice(codice);
    	return find(pk);
    }
    /**
     * search method
     */
    @Override
    public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch search){
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(selectAll);
        if(search.getIdOrganizzazione() != null){
            sql.append(sep).append("\"id_organizzazione\" = ?");
            parameters.add(search.getIdOrganizzazione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCodice())){
            sql.append(sep).append("\"codice\" = ?");
            parameters.add(search.getCodice());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getNome())){
            sql.append(sep).append("lower(\"nome\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getNome()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getDescrizione())){
            sql.append(sep).append("lower(\"descrizione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getDescrizione()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getOggetto())){
            sql.append(sep).append("lower(\"oggetto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getOggetto()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTesto())){
            sql.append(sep).append("lower(\"testo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getTesto()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getReadonlyOggetto())){
            sql.append(sep).append("lower(\"readonly_oggetto\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getReadonlyOggetto()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getReadonlyTesto())){
            sql.append(sep).append("lower(\"readonly_testo\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getReadonlyTesto()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getVisibilita())){
            sql.append(sep).append("lower(\"visibilita\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getVisibilita()).toLowerCase());
            sep = " and ";
        }
        if(search.getSezione()!=null){
            sql.append(sep).append("\"sezione\" = ?");
            parameters.add(search.getSezione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getTipiProcedimentoAmmessi())){
            sql.append(sep).append("(lower(\"tipi_procedimento_ammessi\"::text) like ?  or  tipi_procedimento_ammessi  is null or tipi_procedimento_ammessi='' )");
            parameters.add(StringUtil.convertLike(search.getTipiProcedimentoAmmessi()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getProtocollazione())){
            sql.append(sep).append("lower(\"protocollazione\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getProtocollazione()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getPlaceholders())){
            sql.append(sep).append("lower(\"placeholders\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getPlaceholders()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getCancellabile())){
            sql.append(sep).append("lower(\"cancellabile\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getCancellabile()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getRiservata())){
            sql.append(sep).append("lower(\"riservata\"::text) like ?");
            parameters.add(StringUtil.convertLike(search.getRiservata()).toLowerCase());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSottoSezione())){
            sql.append(sep).append("\"sotto_sezione\" = ?");
            parameters.add(search.getSottoSezione());
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "idOrganizzazione":
                    sql.append(" order by \"id_organizzazione\" ").append(sortType);
                	   break;
            case "codice":
                    sql.append(" order by \"codice\" ").append(sortType);
                	   break;
            case "nome":
                    sql.append(" order by \"nome\" ").append(sortType);
                	   break;
            case "descrizione":
                    sql.append(" order by \"descrizione\" ").append(sortType);
                	   break;
            case "oggetto":
                    sql.append(" order by \"oggetto\" ").append(sortType);
                	   break;
            case "testo":
                    sql.append(" order by \"testo\" ").append(sortType);
                	   break;
            case "readonlyOggetto":
                    sql.append(" order by \"readonly_oggetto\" ").append(sortType);
                	   break;
            case "readonlyTesto":
                    sql.append(" order by \"readonly_testo\" ").append(sortType);
                	   break;
            case "visibilita":
                    sql.append(" order by \"visibilita\" ").append(sortType);
                	   break;
            case "sezione":
                    sql.append(" order by \"sezione\" ").append(sortType);
                	   break;
            case "tipiProcedimentoAmmessi":
                    sql.append(" order by \"tipi_procedimento_ammessi\" ").append(sortType);
                	   break;
            case "protocollazione":
                    sql.append(" order by \"protocollazione\" ").append(sortType);
                	   break;
            case "placeholders":
                    sql.append(" order by \"placeholders\" ").append(sortType);
                	   break;
            case "cancellabile":
                    sql.append(" order by \"cancellabile\" ").append(sortType);
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
    public long insert(TemplateEmailDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"template_email\"")
                                     .append("(\"id_organizzazione\"")
                                     .append(",\"codice\"")
                                     .append(",\"nome\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"oggetto\"")
                                     .append(",\"testo\"")
                                     .append(",\"readonly_oggetto\"")
                                     .append(",\"readonly_testo\"")
                                     .append(",\"visibilita\"")
                                     .append(",\"sezione\"")
                                     .append(",\"tipi_procedimento_ammessi\"")
                                     .append(",\"protocollazione\"")
                                     .append(",\"placeholders\"")
                                     .append(",\"cancellabile\"")
                                     .append(",\"riservata\"")
                                     .append(",\"sotto_sezione\"")
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
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getCodice());
        parameters.add(entity.getNome());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getOggetto());
        parameters.add(entity.getTesto());
        parameters.add(entity.getReadonlyOggetto());
        parameters.add(entity.getReadonlyTesto());
        parameters.add(entity.getVisibilita());
        parameters.add(entity.getSezione());
        parameters.add(entity.getTipiProcedimentoAmmessi());
        parameters.add(entity.getProtocollazione());
        parameters.add(entity.getPlaceholders());
        parameters.add(entity.getCancellabile());
        parameters.add(entity.isRiservata());
        parameters.add(entity.getSottoSezione());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(TemplateEmailDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"template_email\"")
                                     .append(" set \"nome\" = ?")
                                     .append(", \"descrizione\" = ?")
                                     .append(", \"oggetto\" = ?")
                                     .append(", \"testo\" = ?")
                                     .append(", \"readonly_oggetto\" = ?")
                                     .append(", \"readonly_testo\" = ?")
                                     .append(", \"visibilita\" = ?")
                                     .append(", \"sezione\" = ?")
                                     .append(", \"tipi_procedimento_ammessi\" = ?")
                                     .append(", \"protocollazione\" = ?")
                                     .append(", \"placeholders\" = ?")
                                     .append(", \"cancellabile\" = ?")
                                     .append(", \"riservata\" = ?")
                                     .append(", \"sotto_sezione\" = ?")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getNome());
        parameters.add(entity.getDescrizione());
        parameters.add(entity.getOggetto());
        parameters.add(entity.getTesto());
        parameters.add(entity.getReadonlyOggetto());
        parameters.add(entity.getReadonlyTesto());
        parameters.add(entity.getVisibilita());
        parameters.add(entity.getSezione());
        parameters.add(entity.getTipiProcedimentoAmmessi());
        parameters.add(entity.getProtocollazione());
        parameters.add(entity.getPlaceholders());
        parameters.add(entity.getCancellabile());
        parameters.add(entity.isRiservata());
        parameters.add(entity.getSottoSezione());
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getCodice());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(TemplateEmailDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"template_email\"")
                                     .append(" where \"id_organizzazione\" = ?")
                                     .append(" and \"codice\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getIdOrganizzazione());
        parameters.add(entity.getCodice());
        return super.update(sql, parameters);
    }
    
    /**
     * restituisce la lista dei template a idOrganizzazione=0 che non sono presenti per me (idOrganizzazione=xxxx)
     * @author acolaianni
     *
     * @param idOrganizzazione
     * @param tipoOrganizzazione es. ED_
     * @return
     */
    public List<TemplateEmailDTO> getTemplateMancanti(Integer idOrganizzazione,String tipoOrganizzazione) {
    	final List<Object>  parameters = new ArrayList<Object>();
    	StringBuilder sql =new StringBuilder(); 
    			sql
    			.append("SELECT * from template_email where id_organizzazione=0 ")
    			.append(" and upper(\"visibilita\"::text) like ? ")
    			.append(" and codice not in (select codice from template_email where id_organizzazione= ? )");
    	parameters.add(StringUtil.convertLike(tipoOrganizzazione));
    	parameters.add(idOrganizzazione);
    	return super.queryForList(sql.toString(),new TemplateEmailRowMapper(),parameters);
    } 
    
    /**
     * utilizza una store funzion per generare un codice del tipo TEMPL_X 
     * @author acolaianni
     *
     * @return
     */
    public String getNewCodice() {
    	StringBuilder sql =new StringBuilder(); 
		sql
		.append("SELECT next_codice_template()");
		return super.queryForObject(sql.toString(),String.class,new Object[]{});
    }

}
