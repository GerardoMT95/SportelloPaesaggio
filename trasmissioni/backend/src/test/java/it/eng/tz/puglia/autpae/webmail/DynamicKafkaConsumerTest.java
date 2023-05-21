package it.eng.tz.puglia.autpae.webmail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.webmail.DynamicKafkaConsumer;
import it.eng.tz.puglia.servizi_esterni.webmail.feign.Webmail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicKafkaConsumerTest {
	
//	@Autowired
//	private DynamicKafkaConsumer kafka;
	
	@Autowired
	private Webmail webmailFeign;
	
	@Autowired 
	private IHttpClientService cmsService;
	
	@Test
	public void test() {

	}
	
	
	@Test
	public void getRicevuta() throws IOException, CustomCmisException {
		final String idRisposta="adb1d971-e058-4e21-a22e-79f1ec67acd2";
		ResponseEntity<Resource> response = webmailFeign.retrieveEml(idRisposta); // capire se questo servizio oppure l'altro
		if (response.getStatusCode().is2xxSuccessful()) {
			Resource eml = response.getBody();
			String nomeFileEml = idRisposta.replaceAll("[<>]", "") + ".eml";
			Path tmpDir = Files.createTempDirectory("uploadEml");
			Path tmpFile = Files.createTempFile(tmpDir, "",nomeFileEml);
			File fileEml = tmpFile.toFile();
			InputStream emlInputStream = eml.getInputStream();
			Files.copy(emlInputStream, fileEml.toPath(), StandardCopyOption.REPLACE_EXISTING);
			IOUtils.closeQuietly(emlInputStream);
			String idCms=cmsService.uploadCms(fileEml, "http://cms/cms/uploadStream.pjson", "/autpae/Corrispondenza/test", "autpae",true);
			System.out.println(idCms);
		}
	}

}
