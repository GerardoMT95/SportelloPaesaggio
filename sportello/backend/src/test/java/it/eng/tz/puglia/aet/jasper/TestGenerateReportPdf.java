package it.eng.tz.puglia.aet.jasper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperCheckBox;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaDichiarazioneTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaIstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperIntegrazioneDocumentaleDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;


public class TestGenerateReportPdf {
	
	final String pathJasperOut="c:/tmp/jasperOut";
	final String pathObjSerialized="c:/tmp/objSerialized";
	
	@Test
	public void creaDomanda() throws JsonParseException, JsonMappingException, IOException, JRException {
		ObjectMapper om=new ObjectMapper();
		JasperDomandaIstanzaDto ret = om.readValue(new File(pathObjSerialized.concat("/jasperDomanda.json")),JasperDomandaIstanzaDto.class );
		 
		 Path pathTemp=Paths.get(pathJasperOut.concat("/Domanda").concat("_").concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".PDF"));
		File generated=pathTemp.toFile();
		Resource res = new ClassPathResource("/jasper/".concat("DomandaIstanza.jasper"));
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
				new JRBeanCollectionDataSource(Collections.singletonList(ret)));
		JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
	}
	
	
	@Test
	public void creaSchedaTecnica() throws JsonParseException, JsonMappingException, IOException, JRException {
		ObjectMapper om=new ObjectMapper();
		JasperDomandaDichiarazioneTecnicaDto ret = om.readValue(new File(pathObjSerialized.concat("/jasperSchedaTecnica.json")),JasperDomandaDichiarazioneTecnicaDto.class );
		 
		 Path pathTemp=Paths.get(pathJasperOut.concat("/SchedaTecnica").concat("_").concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".PDF"));
		File generated=pathTemp.toFile();
		Resource res = new ClassPathResource("/jasper/".concat("DomandaDichiarazioneTecnica.jasper"));
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
				new JRBeanCollectionDataSource(Collections.singletonList(ret)));
		JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
	}
	
	
	@Test
	public void creaPdfIntegrazione() throws JsonParseException, JsonMappingException, IOException, JRException {
		ObjectMapper om=new ObjectMapper();
		JasperIntegrazioneDocumentaleDto ret = om.readValue(new File(pathObjSerialized.concat("/jasperIntegrazione.json")),JasperIntegrazioneDocumentaleDto.class );
		 
		 Path pathTemp=Paths.get(pathJasperOut.concat("/IntegrazioneDocumentale").concat("_").concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".PDF"));
		File generated=pathTemp.toFile();
		Resource res = new ClassPathResource("/jasper/".concat("integrazioneDocumentale.jasper"));
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
				new JRBeanCollectionDataSource(Collections.singletonList(ret)));
		JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
	}
	
	@Test
	public void testSubreport() throws JsonParseException, JsonMappingException, IOException, JRException {
		List<JasperCheckBox> dummyCheck=new ArrayList<>();
		for(int i=1;i<=10;i++) {
			JasperCheckBox item=new JasperCheckBox();
			item.setIsChecked(i % 2==0);
			item.setLabel("etichetta numero  numero etichetta numero"+i);
			dummyCheck.add(item);
		}
		Resource res = new ClassPathResource("/jasper/".concat("subreportClausole.jasper"));
		Path pathTemp=Paths.get(pathJasperOut.concat("/subreportClausole").concat("_").concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).concat(".PDF"));
		File generated=pathTemp.toFile();
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(res.getInputStream());
		Map<String,Object> beanjasper=new HashMap<>();
		beanjasper.put("clausole", dummyCheck);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
				new JRBeanCollectionDataSource(Collections.singletonList(beanjasper)));
		JasperExportManager.exportReportToPdfFile(jasperPrint, generated.getPath());
	}
	
	
	private String readableFileSize(long size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	@Test
	public void testaHumanRaeadable() {
		System.out.println("1  "+ readableFileSize(1));
		System.out.println("1025  "+ readableFileSize(1025));
		System.out.println("120000  "+ readableFileSize(120000));
		System.out.println("1_000_000  "+ readableFileSize(1_000_000));
		System.out.println("1_200_000  "+ readableFileSize(1_200_000));
		System.out.println("10_200_000  "+ readableFileSize(10_200_000));
		System.out.println("1100_200_000  "+ readableFileSize(1100_200_000));
		
	}
}
