package it.eng.tz.puglia.aet.jasper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.MDC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.PresentazioneIstanzaApplication;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.DigitalizzazioneIstanzaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IntegrazioneDocumentale;
import it.eng.tz.puglia.security.util.SecurityUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentazioneIstanzaApplication.class)
public class TestGenerateBeanReportSpring {

	@Autowired DigitalizzazioneIstanzaService digitalizzazioneSvc;
	@Autowired IntegrazioneDocumentale integrazioneSvc;
	
	//final String codice="APPPTR-122-2022";
	final String codice="APPPTR-39-2022";
	final String localPathSchTec="C:\\tmp\\objSerialized\\jasperSchedaTecnica.json";
	final String localPathInteg="C:\\tmp\\objSerialized\\jasperIntegrazione.json";
	final String localPathDom="C:\\tmp\\objSerialized\\jasperDomanda.json";
	final String usernameMocked="sportello1";
	//integrazione
	final String codiceInteg="APPPTR-136-2022";
	final UUID uuidInteg=UUID.fromString("153f6f73-9f1f-4c3b-9e7f-70a70780deb4");
	final int idInteg=13;
	
	@Test
	public void generateJsonSchedaTecnica() throws Exception {
		MDC.put(UserUtil.MDC_GRUPPO_MOCKED, Gruppi.RICHIEDENTI.name());
		MDC.put(UserUtil.MDC_USERNAME_MOCKED, usernameMocked);
		String json = digitalizzazioneSvc.generateJsonDomandaSchedaTecnica(codice);
		File f=new File(localPathSchTec);
		try(FileWriter fw=new FileWriter(f)){
			IOUtils.write(json, fw);	
		}
		
	}
	
	@Test
	public void generateJsonDomanda() throws Exception {
		MDC.put(UserUtil.MDC_GRUPPO_MOCKED, Gruppi.RICHIEDENTI.name());
		MDC.put(UserUtil.MDC_USERNAME_MOCKED, usernameMocked);
		String json = digitalizzazioneSvc.generateJsonIstanza(codice);
		File f=new File(localPathDom);
		try(FileWriter fw=new FileWriter(f)){
			IOUtils.write(json, fw);	
		}
	}
	
	@Test
	public void generateJsonIntegrazione() throws Exception {
		MDC.put(UserUtil.MDC_GRUPPO_MOCKED, Gruppi.RICHIEDENTI.name());
		MDC.put(UserUtil.MDC_USERNAME_MOCKED, "sportello2");
		String json = integrazioneSvc.generateJsonJasperIntegrazioneDocumentaleDto(idInteg, uuidInteg, codiceInteg);
		File f=new File(localPathInteg);
		try(FileWriter fw=new FileWriter(f)){
			IOUtils.write(json, fw);	
		}
		
	}
}
