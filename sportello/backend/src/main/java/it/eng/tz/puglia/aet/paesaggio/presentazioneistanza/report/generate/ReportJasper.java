package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.generate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOutputBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.util.uuid.UuidUtil;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class ReportJasper<T extends Serializable> implements IReportGenerateService{

	/**
	 * logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${report.path}")
	private String reportPath;
	@Value("${jasper.path}")
	private String jasperPath;

	private String jasperAbsuolutePath;
	
	@PostConstruct
	public void init() {
		this.jasperAbsuolutePath = StringUtil.concateneString(this.jasperPath, "/", this.getJasperFileName(), ".jasper");
	}
	
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @param parameters
	 * @param username
	 * @return list
	 * @throws Exception
	 */
	protected abstract List<T> getList(final ReportParameterBean parameters, final String username) throws Exception;
//	/**
//	 * @author Antonio La Gatta
//	 * @date 2 mag 2022
//	 * @return report File name
//	 */
//	protected abstract String getReportFileName();
	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 * @return titolo del report
	 */
	protected abstract String getTitolo();
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @return jasper file name
	 */
	protected abstract String getJasperFileName();
	/**
	 * Print file
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 * @param jasperPrint
	 * @param destPath
	 * @throws Exception
	 */
	protected abstract void print(JasperPrint jasperPrint, String destPath) throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 * @return estensione file
	 */
	
	
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.generate.IReportGenerateService#generateReport(it.eng.tz.aet.bean.ReportParameterBean, java.lang.String)
	 */
	@Override
	public ReportOutputBean generateReport(final ReportParameterBean parameters, final String username) throws Exception {
		final StopWatch sw = LogUtil.startLog("generateReport");
		this.logger.info("Start generateReport");
		try {
			final StopWatch swGetData = LogUtil.startLog("Get Data Report");
			final String fileName = UuidUtil.newValue() + "." + parameters.getEstensione();
			final String absolutePath = StringUtil.concateneString(this.reportPath, "/", fileName);
			this.logger.info("path {}", absolutePath);
			List<T> list = null;
			try{
				list = this.getList(parameters, username); 
			}finally {
				this.logger.info(LogUtil.stopLog(swGetData));
			}
			final JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
			final Map<String,Object> parametersMap = new HashMap<>();
			parametersMap.put("DATA", new java.util.Date());
			parametersMap.put("DATA_FROM", parameters.getDateFrom());
			parametersMap.put("DATA_TO", parameters.getDateTo());
			parametersMap.put("TITOLO", this.getTitolo());
			parametersMap.put("DESCRIZIONE_ENTE", parameters.getDescrizioneEnte());
			if(parameters.getEstensione().equals("xls"))
				parametersMap.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			final StopWatch swJP = LogUtil.startLog("Generate JasperPrint");
			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(this.jasperAbsuolutePath
				                                          ,parametersMap
				                                          ,jrBeanCollectionDataSource
				                                          );
			}finally {
				this.logger.info(LogUtil.stopLog(swJP));
			}
			final StopWatch swExport = LogUtil.startLog("Generate export");
			try {
				this.print(jasperPrint, absolutePath);
			}finally {
				this.logger.info(LogUtil.stopLog(swExport));
			}
//			final String fileName = new StringBuilder(this.getReportFileName())
//                                              .append(" - ")
//                                              .append(new SimpleDateFormat("yyyyMMddHHmmss").format(DateUtil.currentTimestamp()))
//                                              .append(".")
//                                              .append(this.getEstensione())
//                                              .toString();
			return new ReportOutputBean(fileName, absolutePath);
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	

}
