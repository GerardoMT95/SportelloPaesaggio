package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReportDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ReportRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReportSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.DownloadReportBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.DownloadReportBeanRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportComuneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportComuneDtoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportComuneProcedimentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportComuneProcedimentoDtoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOneriIstruttoriDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOneriIstruttoriRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOutputBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportProvinciaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportProvinciaDtoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportProvinciaProcedimentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportProvinciaProcedimentoDtoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CheckSumUtil;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.report
 */
@Repository
public class ReportRepository extends GenericCrudDao<ReportDTO, ReportSearch>{
	


    private final ReportRowMapper rowMapper = new ReportRowMapper();
    private final ReportOneriIstruttoriRowMapper OneriRowMapper = new ReportOneriIstruttoriRowMapper();
    private final DownloadReportBeanRowMapper DownloadRowMapper = new DownloadReportBeanRowMapper();
    private final ReportComuneDtoRowMapper ComuneRowMapper = new ReportComuneDtoRowMapper();
    private final ReportComuneProcedimentoDtoRowMapper ComuneProcedimentoRowMapper = new ReportComuneProcedimentoDtoRowMapper();
    private final ReportProvinciaDtoRowMapper ProvinciaRowMapper = new ReportProvinciaDtoRowMapper();
    private final ReportProvinciaProcedimentoDtoRowMapper ProvinciaProcedimentoRowMapper = new ReportProvinciaProcedimentoDtoRowMapper();

    /**
     * select all method
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"id\"")
                                     .append(",\"tipo\"")
                                     .append(",\"parametri\"")
                                     .append(",\"username\"")
                                     .append(",\"path_file\"")
                                     .append(",\"file_name\"")
                                     .append(",\"stato\"")
                                     .append(",\"dimensione_file\"")
                                     .append(",\"data_richiesta\"")
                                     .append(",\"data_avvio\"")
                                     .append(",\"data_creazione\"")
                                     .append(",\"data_scadenza\"")
                                     .append(",\"hash\"")
                                     .append(",\"stato_originale\"")
                                     .append(",\"ente_delegato\"")
                                     .append(",\"descrizione_ente\"")
                                     .append(",\"mail\"")
                                     .append(" from \"presentazione_istanza\".\"report\"")
                                     .toString();
    @Override
    public List<ReportDTO> select(){
        return super.queryForListTxRead(selectAll, this.rowMapper);
    }

    /**
     * count all method
     */
    @Override
    public long count(){
        final String sql = new StringBuilder("select count(*)")
                                     .append(" from \"presentazione_istanza\".\"report\"")
                                     .toString();
        return super.queryForObjectTxRead(sql, Long.class);
    }

    /**
     * find by pk method
     */
    @Override
    public ReportDTO find(ReportDTO pk){
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
    public PaginatedList<ReportDTO> search(final ReportSearch search){
        final String select = new StringBuilder("select")
                .append(" \"id\"")
                .append(",\"stato\"")
                .append(",\"file_name\"")
                .append(",\"data_creazione\"")
                .append(",\"hash\"")
                .append(",\"ente_delegato\"")
                .append(",\"descrizione_ente\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .toString();
        final List<Object>  parameters = new ArrayList<Object>();
        String              sep        = " where ";
        final StringBuilder sql        = new StringBuilder(select);
        sql.append(sep).append("(\"stato\" = 'C' or \"stato\" = 'E' or \"stato\" = 'A' or \"stato\" = 'I') and ente_delegato = ?");
        parameters.add(Integer.valueOf(search.getEnteDelegato()));
        sep = " and ";
        if(search.getDataFrom() != null){
            sql.append(sep).append("\"data_creazione\"::timestamp >= " + "'" + search.getDataFrom() + "'" + "::timestamp");
            sep = " and ";
        }
        if(search.getDataTo() != null){
            sql.append(sep).append("\"data_creazione\"::timestamp <= " + "'" + search.getDataTo() + "'" + "::timestamp");
            sep = " and ";
        }
        if(StringUtil.isNotEmpty(search.getSortBy())){
            String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
            switch (search.getSortBy()) {
            case "fileName":
                    sql.append(" order by \"file_name\" ").append(sortType);
                	   break;
            case "dataCreazione":
                    sql.append(" order by \"data_creazione\" ").append(sortType);
                	   break;
            case "hash":
                    sql.append(" order by \"hash\" ").append(sortType);
                	   break;
            default:
            	    logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
            	    break;
            }
        } else {
            sql.append(" order by \"data_creazione\" DESC");
        }
        return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
    }

    /**
     * insert all method
     */
    @Override
    public long insert(ReportDTO entity){
        final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"report\"")
                                     .append("(\"id\"")
                                     .append(",\"tipo\"")
                                     .append(",\"parametri\"")
                                     .append(",\"username\"")
                                     .append(",\"path_file\"")
                                     .append(",\"file_name\"")
                                     .append(",\"stato\"")
                                     .append(",\"dimensione_file\"")
                                     .append(",\"data_richiesta\"")
                                     .append(",\"data_avvio\"")
                                     .append(",\"data_creazione\"")
                                     .append(",\"data_scadenza\"")
                                     .append(",\"hash\"")
                                     .append(",\"stato_originale\"")
                                     .append(",\"ente_delegato\"")
                                     .append(",\"descrizione_ente\"")
                                     .append(",\"mail\"")
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
                                     .append(")")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        parameters.add(entity.getTipo());
        parameters.add(entity.getParametri());
        parameters.add(entity.getUsername());
        parameters.add(entity.getPathFile());
        parameters.add(entity.getFileName());
        parameters.add(entity.getStato());
        parameters.add(entity.getDimensioneFile());
        parameters.add(entity.getDataRichiesta());
        parameters.add(entity.getDataAvvio());
        parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataScadenza());
        parameters.add(entity.getHash());
        parameters.add(entity.getStatoOriginale());
        parameters.add(entity.getEnteDelegato());
        parameters.add(entity.getDescrizioneEnte());
        parameters.add(entity.getMail());
        return super.update(sql, parameters);
    }

    /**
     * update by pk method
     */
    @Override
    public int update(ReportDTO entity){
        final String sql = new StringBuilder("update \"presentazione_istanza\".\"report\"")
                                     .append(" set \"tipo\" = ?")
                                     .append(", \"parametri\" = ?")
                                     .append(", \"username\" = ?")
                                     .append(", \"path_file\" = ?")
                                     .append(", \"file_name\" = ?")
                                     .append(", \"stato\" = ?")
                                     .append(", \"dimensione_file\" = ?")
                                     .append(", \"data_richiesta\" = ?")
                                     .append(", \"data_avvio\" = ?")
                                     .append(", \"data_creazione\" = ?")
                                     .append(", \"data_scadenza\" = ?")
                                     .append(", \"hash\" = ?")
                                     .append(", \"stato_originale\" = ?")
                                     .append(", \"ente_delegato\" = ?")
                                     .append(", \"descrizione_ente\" = ?")
                                     .append(", \"mail\" = ?")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getTipo());
        parameters.add(entity.getParametri());
        parameters.add(entity.getUsername());
        parameters.add(entity.getPathFile());
        parameters.add(entity.getFileName());
        parameters.add(entity.getStato());
        parameters.add(entity.getDimensioneFile());
        parameters.add(entity.getDataRichiesta());
        parameters.add(entity.getDataAvvio());
        parameters.add(entity.getDataCreazione());
        parameters.add(entity.getDataScadenza());
        parameters.add(entity.getHash());
        parameters.add(entity.getStatoOriginale());
        parameters.add(entity.getEnteDelegato());
        parameters.add(entity.getDescrizioneEnte());
        parameters.add(entity.getMail());
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }

    /**
     * delete by pk method
     */
    @Override
    public int delete(ReportDTO entity){
        final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"report\"")
                                     .append(" where \"id\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(entity.getId());
        return super.update(sql, parameters);
    }
    
    public List<String> idTerminati() {
    	final StopWatch sw = LogUtil.startLog("idTerminati");
		this.logger.info("Start idTerminati");
		try {
			final String select = new StringBuilder("select")
                    .append(" \"id\"")
                    .append(" from \"presentazione_istanza\".\"report\"")
                    .append(" where (\"stato\" = 'C'")
                    .append(" or \"stato\" = 'E'")
                    .append(" or \"stato\" = 'A')")
//                    .append(" order by order coalesce data creazione e data avvio!!!")
                    .toString();
			return super.queryForList(select, String.class);
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
    }
    
    public void eliminaReport(final String id) {
    	final String selectStato = new StringBuilder("select")
                .append(" \"stato\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        final ReportDTO statoDTO = super.queryForObjectTxRead(selectStato, this.rowMapper, parameters);
		final String stato = statoDTO.getStato();		
		parameters.clear();
				
    	final String sql = new StringBuilder("update \"presentazione_istanza\".\"report\"")
                .append(" set \"stato\" = ?")
                .append(", \"data_scadenza\" = ?")
                .append(", \"stato_originale\" = ?")
                .append(" where \"id\" = ?")
                .toString();
        parameters.add("S");
        parameters.add(new Date());
        parameters.add(stato);
        parameters.add(id);
    	super.update(sql, parameters);
    }
    
    public String pathFile(final String id) {
    	final String select = new StringBuilder("select")
                .append(" \"path_file\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        final ReportDTO path = super.queryForObjectTxRead(select, this.rowMapper, parameters);
		return path.getPathFile();	
    }
    
    public Timestamp dataCreazione(final String id) {
    	final String select = new StringBuilder("select")
                .append(" \"data_creazione\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        final ReportDTO dataCreazione = super.queryForObjectTxRead(select, this.rowMapper, parameters);
		return dataCreazione.getDataCreazione();
    }
    
    public Timestamp dataScadenza(final String id) {
    	final String select = new StringBuilder("select")
                .append(" \"data_scadenza\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        final ReportDTO dataScadenza = super.queryForObjectTxRead(select, this.rowMapper, parameters);
		return dataScadenza.getDataScadenza();
    }
    
    public Timestamp dataRichiesta(final String id) {
    	final String select = new StringBuilder("select")
                .append(" \"data_richiesta\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        final ReportDTO dataRichiesta = super.queryForObjectTxRead(select, this.rowMapper, parameters);
		return dataRichiesta.getDataRichiesta();
    }
    
    public String getUsername(final String id) {
    	final String select = new StringBuilder("select")
                .append(" \"username\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        final ReportDTO username = super.queryForObjectTxRead(select, this.rowMapper, parameters);
		return username.getUsername();
    }
    
    public String getMail(final String id) {
    	final String select = new StringBuilder("select")
                .append(" \"mail\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
        final ReportDTO mail = super.queryForObjectTxRead(select, this.rowMapper, parameters);
		return mail.getMail();
    }
    
    public String getNextId() {
    	final String select = new StringBuilder("select")
                .append(" \"id\"")
                .append(" from \"presentazione_istanza\".\"report\"")
                .append(" where \"stato\" = 'I'")
                .append("order by \"data_richiesta\" ASC")
                .toString();
		List<String> ids = super.queryForList(select, String.class);
		if(ids != null && ids.size() > 0) {
			return ids.get(0);
		}
		return null;
    }
    
    public void avviaReport(final String id) {
    	final String sql = new StringBuilder("update \"presentazione_istanza\".\"report\"")
                .append(" set \"stato\" = 'A'")
                .append(", \"data_avvio\" = ?")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(new Date());
        parameters.add(id);
    	super.update(sql, parameters);
    }
    
	public void concludiReport(final String id, final ReportOutputBean bean) throws Exception {
		final StopWatch sw = LogUtil.startLog("concludiReport ", id);
		this.logger.info("Start concludiReport {}", id);
		try {
			final String sql = new StringBuilder("update \"presentazione_istanza\".\"report\"")
	                .append(" set \"stato\" = 'C'")
	                .append(", \"data_creazione\" = ?")
	                .append(", \"path_file\" = ?")
	                .append(", \"dimensione_file\" = ?")
	                .append(", \"hash\" = ?")
	                .append(" where \"id\" = ?")
	                .toString();
	        final List<Object> parameters = new ArrayList<Object>();
	        parameters.add(new Date());
	        parameters.add(bean.getPathFile());
	        parameters.add(new File(bean.getPathFile()).length());
	        parameters.add(CheckSumUtil.getCheckSum(new File(bean.getPathFile())));
	        parameters.add(id);
	    	super.update(sql, parameters);
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
		
	}

	public void erroreReport(final String id) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"report\"")
                .append(" set \"stato\" = 'E'")
                .append(" where \"id\" = ?")
                .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(id);
    	super.update(sql, parameters);
	}
	
	public List<ReportOneriIstruttoriDto> listaOneriIstruttori(final ReportParameterBean parameters, final String username) {
		final StringBuilder sql = new StringBuilder();
        final List<Object> parametersList = new ArrayList<Object>();
		String sep = " where ";
		sql.append("select")
		   .append(" nome_tipo_procedimento as \"tipoProcedimento\"") 
		   .append(",sum(importo_progetto) as onere")
		   .append(" from v_report_oneri_istruttori")
		   ;
        sql.append(sep).append("ente_delegato = ?");
        parametersList.add(String.valueOf(parameters.getEnteDelegato()));
        sep = " and ";
		if(parameters.getDateFrom() != null) {
            sql.append(sep).append("\"data_presentazione\"::timestamp >= " + "'" + parameters.getDateFrom() + "'" + "::timestamp");
			sep = " and ";
		}
		if(parameters.getDateTo() != null) {
            sql.append(sep).append("\"data_presentazione\"::timestamp <= " + "'" + parameters.getDateTo() + "'" + "::timestamp");
			sep = " and ";
		}
		if(ListUtil.isNotEmpty(parameters.getIdProcedimento())) {
			sql.append(sep).append("id_tipo_procedimento in (" + "?, ".repeat(parameters.getIdProcedimento().size() - 1) + "?)");
			for(String tipo : parameters.getIdProcedimento()) {
				parametersList.add(Integer.valueOf(tipo));
			}
			sep = " and ";
		}
		if(parameters.isDirigente() == false) {
			sql.append(sep).append("id_istruttoria in (select id from istruttoria where rdp = ? union select id_istruttoria from istruttoria_istruttori where istruttore = ? and deleted <> true)");
			parametersList.add(username);
			parametersList.add(username);
			sep = " and ";
		}
		sql.append(" group by nome_tipo_procedimento") 
		.append(" order by 1")
		;
        return super.queryForListTxRead(sql.toString(), this.OneriRowMapper, parametersList);
	}
	
	public List<ReportComuneDto> listaComune(final ReportParameterBean parameters, final String username) {
		final StopWatch sw = LogUtil.startLog("listaComune");
		this.logger.info("Start listaComune");
		try {
			final StringBuilder sql = new StringBuilder();
	        final List<Object> parametersList = new ArrayList<Object>();
			String sep = " where ";
			sql.append("select")
			   .append(" nome_comune as nome") 
			   .append(",count(*) as count")
			   .append(" from v_report_comune")
			   ;
	        sql.append(sep).append("ente_delegato = ?");
	        parametersList.add(String.valueOf(parameters.getEnteDelegato()));
	        sep = " and ";
			if(parameters.getDateFrom() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp >= " + "'" + parameters.getDateFrom() + "'" + "::timestamp");
				sep = " and ";
			}
			if(parameters.getDateTo() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp <= " + "'" + parameters.getDateTo() + "'" + "::timestamp");
				sep = " and ";
			}
			if(ListUtil.isNotEmpty(parameters.getIdProcedimento())) {
				sql.append(sep).append("id_tipo_procedimento in (" + "?, ".repeat(parameters.getIdProcedimento().size() - 1) + "?)");
				for(String tipo : parameters.getIdProcedimento()) {
					parametersList.add(Integer.valueOf(tipo));
				}
				sep = " and ";
			}
			if(parameters.isDirigente() == false) {
				sql.append(sep).append("id_istruttoria in (select id from istruttoria where rdp = ? union select id_istruttoria from istruttoria_istruttori where istruttore = ? and deleted <> true)");
				parametersList.add(username);
				parametersList.add(username);
				sep = " and ";
			}
			sql.append(" group by nome_comune") 
			   .append(" order by 2 desc, 1 asc")
			   ;
	        return super.queryForListTxRead(sql.toString(), this.ComuneRowMapper, parametersList);

		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	public List<ReportComuneProcedimentoDto> listaComuneProcedimento(final ReportParameterBean parameters, final String username) {
		final StopWatch sw = LogUtil.startLog("listaComuneProcedimento");
		this.logger.info("Start listaComuneProcedimento");
		try {
			final StringBuilder sql = new StringBuilder();
	        final List<Object> parametersList = new ArrayList<Object>();
			String sep = " where ";
			sql.append("select")
			   .append(" nome_comune as nome")
			   .append(",nome_tipo_procedimento as procedimento") 
			   .append(",count(*) as count")
			   .append(" from v_report_comune")
			   ;
	        sql.append(sep).append("ente_delegato = ?");
	        parametersList.add(String.valueOf(parameters.getEnteDelegato()));
	        sep = " and ";
			if(parameters.getDateFrom() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp >= " + "'" + parameters.getDateFrom() + "'" + "::timestamp");
				sep = " and ";
			}
			if(parameters.getDateTo() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp <= " + "'" + parameters.getDateTo() + "'" + "::timestamp");
				sep = " and ";
			}
			if(ListUtil.isNotEmpty(parameters.getIdProcedimento())) {
				sql.append(sep).append("id_tipo_procedimento in (" + "?, ".repeat(parameters.getIdProcedimento().size() - 1) + "?)");
				for(String tipo : parameters.getIdProcedimento()) {
					parametersList.add(Integer.valueOf(tipo));
				}
				sep = " and ";
			}
			if(parameters.isDirigente() == false) {
				sql.append(sep).append("id_istruttoria in (select id from istruttoria where rdp = ? union select id_istruttoria from istruttoria_istruttori where istruttore = ? and deleted <> true)");
				parametersList.add(username);
				parametersList.add(username);
				sep = " and ";
			}
			sql.append(" group by nome_comune, nome_tipo_procedimento") 
			   .append(" order by 3 desc, 2 asc, 1 asc")
			   ;
	        return super.queryForListTxRead(sql.toString(), this.ComuneProcedimentoRowMapper, parametersList);

		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	public List<ReportProvinciaDto> listaProvincia(final ReportParameterBean parameters, final String username) {
		final StopWatch sw = LogUtil.startLog("listaProvincia");
		this.logger.info("Start listaProvincia");
		try {
			final StringBuilder sql = new StringBuilder();
	        final List<Object> parametersList = new ArrayList<Object>();
			String sep = " where ";
			sql.append("select")
			   .append(" nome_provincia as nome") 
			   .append(",count(*) as count")
			   .append(" from v_report_provincia")
			   ;
	        sql.append(sep).append("ente_delegato = ?");
	        parametersList.add(String.valueOf(parameters.getEnteDelegato()));
	        sep = " and ";
			if(parameters.getDateFrom() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp >= " + "'" + parameters.getDateFrom() + "'" + "::timestamp");
				sep = " and ";
			}
			if(parameters.getDateTo() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp <= " + "'" + parameters.getDateTo() + "'" + "::timestamp");
				sep = " and ";
			}
			if(ListUtil.isNotEmpty(parameters.getIdProcedimento())) {
				sql.append(sep).append("id_tipo_procedimento in (" + "?, ".repeat(parameters.getIdProcedimento().size() - 1) + "?)");
				for(String tipo : parameters.getIdProcedimento()) {
					parametersList.add(Integer.valueOf(tipo));
				}
				sep = " and ";
			}
			if(parameters.isDirigente() == false) {
				sql.append(sep).append("id_istruttoria in (select id from istruttoria where rdp = ? union select id_istruttoria from istruttoria_istruttori where istruttore = ? and deleted <> true)");
				parametersList.add(username);
				parametersList.add(username);
				sep = " and ";
			}
			sql.append(" group by nome_provincia") 
			   .append(" order by 2 desc, 1 asc")
			   ;
	        return super.queryForListTxRead(sql.toString(), this.ProvinciaRowMapper, parametersList);

		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	public List<ReportProvinciaProcedimentoDto> listaProvinciaProcedimento(final ReportParameterBean parameters, final String username) {
		final StopWatch sw = LogUtil.startLog("listaProvinciaProcedimento");
		this.logger.info("Start listaProvinciaProcedimento");
		try {
			final StringBuilder sql = new StringBuilder();
	        final List<Object> parametersList = new ArrayList<Object>();
			String sep = " where ";
			sql.append("select")
			   .append(" nome_provincia as nome") 
			   .append(",nome_tipo_procedimento as procedimento") 
			   .append(",count(*) as count")
			   .append(" from v_report_provincia")
			   ;
	        sql.append(sep).append("ente_delegato = ?");
	        parametersList.add(String.valueOf(parameters.getEnteDelegato()));
	        sep = " and ";
			if(parameters.getDateFrom() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp >= " + "'" + parameters.getDateFrom() + "'" + "::timestamp");
				sep = " and ";
			}
			if(parameters.getDateTo() != null) {
	            sql.append(sep).append("\"data_presentazione\"::timestamp <= " + "'" + parameters.getDateTo() + "'" + "::timestamp");
				sep = " and ";
			}
			if(ListUtil.isNotEmpty(parameters.getIdProcedimento())) {
				sql.append(sep).append("id_tipo_procedimento in (" + "?, ".repeat(parameters.getIdProcedimento().size() - 1) + "?)");
				for(String tipo : parameters.getIdProcedimento()) {
					parametersList.add(Integer.valueOf(tipo));
				}
				sep = " and ";
			}
			if(parameters.isDirigente() == false) {
				sql.append(sep).append("id_istruttoria in (select id from istruttoria where rdp = ? union select id_istruttoria from istruttoria_istruttori where istruttore = ? and deleted <> true)");
				parametersList.add(username);
				parametersList.add(username);
				sep = " and ";
			}
			sql.append(" group by nome_provincia, nome_tipo_procedimento") 
			   .append(" order by 3 desc, 2 asc, 1 asc")
			   ;
	        return super.queryForListTxRead(sql.toString(), this.ProvinciaProcedimentoRowMapper, parametersList);

		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	public DownloadReportBean downloadReport(final String id, final String username) {
		final StringBuilder sql = new StringBuilder();
        final List<Object> parameters = new ArrayList<Object>();
		sql.append("select ")
        	.append("\"path_file\"")
        	.append(",\"file_name\"")
            .append(" from \"presentazione_istanza\".\"report\"")
            .append(" where \"id\" = ? and \"username\" = ? and \"stato\" = 'C'")
		   ;
		parameters.add(id);
		parameters.add(username);
        return super.queryForObjectTxRead(sql.toString(), this.DownloadRowMapper, parameters);
	}
}
