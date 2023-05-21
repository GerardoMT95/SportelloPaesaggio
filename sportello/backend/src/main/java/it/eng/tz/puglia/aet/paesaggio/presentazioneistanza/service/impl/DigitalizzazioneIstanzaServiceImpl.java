package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaDichiarazioneTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaIstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter.JasperDichiarazioneIstanzaAdapter;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter.JasperDichiarazioneTecnicaAdapter;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.DigitalizzazioneIstanzaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Service
public class DigitalizzazioneIstanzaServiceImpl implements DigitalizzazioneIstanzaService {

	@Autowired
	FascicoloService fascicoloSvc;
	@Autowired
	AllegatoService allegatoSvc;
	@Autowired
	JasperDichiarazioneTecnicaAdapter adapter;

	@Autowired
	JasperDichiarazioneIstanzaAdapter istanzaAdapter;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public File generateDomandaIstanza(FascicoloDto fascicolo) throws Exception {
		File pdf = null;
		JasperDomandaIstanzaDto jasperDto = new JasperDomandaIstanzaDto();
		final String codice = fascicolo.getCodicePraticaAppptr();
		jasperDto = this.istanzaAdapter.adapt(fascicolo);
		pdf = this.generateReportIstanza(jasperDto, "Istanza_" + codice);
		return pdf;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public File generateDomandaSchedaTecnica(final FascicoloDto fascicolo) throws Exception {
		File pdf = null;
		JasperDomandaDichiarazioneTecnicaDto jasperDto = new JasperDomandaDichiarazioneTecnicaDto();
		final String codice = fascicolo.getCodicePraticaAppptr();
		jasperDto = this.adapter.adapt(fascicolo);
		pdf = this.generateReportDichiarazioneTecnica(jasperDto, "Dichiarazione_Tecnica_" + codice);
		return pdf;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public String generateJsonDomandaSchedaTecnica(final String codice) throws Exception {
		JasperDomandaDichiarazioneTecnicaDto jasperDto = new JasperDomandaDichiarazioneTecnicaDto();
		FascicoloDto fascicolo = new FascicoloDto();
		fascicolo.setCodicePraticaAppptr(codice);
		fascicolo = fascicoloSvc.findByCodicePraticaAppptr(codice, true);
		jasperDto = this.adapter.adapt(fascicolo);
		final ObjectMapper om = new ObjectMapper();
		om.canSerialize(jasperDto.getClass());
		return om.writeValueAsString(jasperDto);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public String generateJsonIstanza(final String codice) throws Exception {
		FascicoloDto fascicolo = new FascicoloDto();
		fascicolo.setCodicePraticaAppptr(codice);
		fascicolo = fascicoloSvc.findByCodicePraticaAppptr(codice, true);
		JasperDomandaIstanzaDto jasperDto = this.istanzaAdapter.adapt(fascicolo);
		final ObjectMapper om = new ObjectMapper();
		om.canSerialize(jasperDto.getClass());
		return om.writeValueAsString(jasperDto);
	}

	private File generateReportIstanza(final JasperDomandaIstanzaDto jasperDto, final String nomeFile)
			throws Exception {
		final Path pathTemp = Files.createTempFile(
				nomeFile.concat("_").concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())), ".PDF");
		final File generated = pathTemp.toFile();
		final Resource res = new ClassPathResource("/jasper/".concat("DomandaIstanza.jasper"));
		final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
				new JRBeanCollectionDataSource(Collections.singletonList(jasperDto)));
	//	JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
		
		final SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
		generated.getName();
		config.setMetadataKeywords(jasperDto.getIdFascicolo()+"/"+generated.getName());
		final JRPdfExporter exporter = new JRPdfExporter();
		exporter.setConfiguration(config);
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		File outputFile = new File(generated.getPath());
		FileOutputStream fos = new FileOutputStream(outputFile);
		OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);
		exporter.setExporterOutput(simpleOutputStreamExporterOutput);
		exporter.exportReport();
		
		return generated;
	}

	private File generateReportDichiarazioneTecnica(final JasperDomandaDichiarazioneTecnicaDto jasperDto,
			final String nomeFile) throws Exception {
		final Path pathTemp = Files
				.createTempFile(nomeFile.concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())), ".PDF");
		final File generated = pathTemp.toFile();
		final Resource res = new ClassPathResource("/jasper/".concat("DomandaDichiarazioneTecnica").concat(".jasper"));
		final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
				new JRBeanCollectionDataSource(Collections.singletonList(jasperDto)));
		//JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
		
		final SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
		config.setMetadataKeywords(jasperDto.getIdFascicolo()+"/"+generated.getName());
		final JRPdfExporter exporter = new JRPdfExporter();
		exporter.setConfiguration(config);
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		File outputFile = new File(generated.getPath());
		FileOutputStream fos = new FileOutputStream(outputFile);
		OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);
		exporter.setExporterOutput(simpleOutputStreamExporterOutput);
		exporter.exportReport();
		return generated;
	}

}
