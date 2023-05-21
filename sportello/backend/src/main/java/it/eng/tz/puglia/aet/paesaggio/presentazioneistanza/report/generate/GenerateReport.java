package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.generate;

import java.io.Serializable;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 * Classe madre per report pdf
 * @author Antonio La Gatta
 * @date 2 mag 2022
 * @param <T>
 */
public abstract class GenerateReport<T extends Serializable> extends ReportJasper<T>{

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 * @see it.eng.tz.aet.report.generate.ReportJasper#print(net.sf.jasperreports.engine.JasperPrint, java.lang.String)
	 */
	@Override
	protected void print(final JasperPrint jasperPrint, final String destPath) throws Exception {
		if(destPath != null && destPath.length() > 0) {
			final String estensione = destPath.substring(destPath.length() - 3);
			switch(estensione) {
				case "pdf": 
					JasperExportManager.exportReportToPdfFile(jasperPrint, destPath);
					break;
				case "rtf":
					final JRRtfExporter exporter = new JRRtfExporter();
					exporter.setExporterInput (new SimpleExporterInput(jasperPrint));
					exporter.setExporterOutput(new SimpleWriterExporterOutput(destPath));
					exporter.exportReport();
					break;
				case "xls":
					jasperPrint.setProperty("net.sf.jasperreports.export.xls.white.page.background", "false");
					jasperPrint.setProperty("net.sf.jasperreports.export.xls.one.page.per.sheet", "true");

					final JRXlsExporter                exporterXLS   = new JRXlsExporter();
					final SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
					
					configuration.setOnePagePerSheet(true);
					exporterXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
					exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(destPath));
					exporterXLS.setConfiguration(configuration);			
					
					exporterXLS.exportReport();	
					break;
				default: 
					break;
			}
		}
	}
}
