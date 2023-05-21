package it.eng.tz.puglia.servizi_esterni.jasper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(JasperServiceImpl.class.getName());

	public JasperPrint compilaReport(String path, List<?> lista, Map<String, Object> parameters) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try
		{
			Resource jasperRes = new ClassPathResource(path);
			if( !jasperRes.exists() ) {
				throw new IllegalArgumentException("NESSUN REPORT TROVATO COL PATH "+path+"."); 
			}
			final InputStream reportInputStream = jasperRes.getInputStream();
			// JasperReport jasperReport = loadTemplate(path);
			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(lista);
		
		/*	java.util.Locale locale = new Locale( "it", "IT" );
			if(parameters != null)
			   parameters.put( JRParameter.REPORT_LOCALE, locale );	*/
			
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportInputStream, null /*parameters*/, jrBeanCollectionDataSource);
			return jasperPrint;
		}
		catch (Exception e)
		{
			log.error(String.format("An error occured during PDF creation: %s", e));
			throw e;
		}
	}
	
	
	public JasperPrint compilaReport(String path, Map<String, Object> parameters) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try
		{
			Resource jasperRes = new ClassPathResource(path);
			if( !jasperRes.exists() ) {
				throw new IllegalArgumentException("NESSUN REPORT TROVATO COL PATH "+path+"."); 
			}
			final InputStream reportInputStream = jasperRes.getInputStream();
				// Fill the report
			//JasperPrint jasperPrint = JasperFillManager.fillReport(reportInputStream,parameters);
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(List.of(parameters));
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportInputStream, null , jrBeanCollectionDataSource);
			return jasperPrint;
		}
		catch (Exception e)
		{
			log.error(String.format("An error occured during PDF creation: %s", e));
			throw e;
		}
	}
	
}