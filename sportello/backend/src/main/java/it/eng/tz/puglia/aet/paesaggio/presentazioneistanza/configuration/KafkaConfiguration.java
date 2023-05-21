package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;

@Configuration
public class KafkaConfiguration {
	
	@Value("${abilita.scheduling}")
	private String abilitaScheduling;
	
	@Value("${casella.postale.config.webmail}")
	private String jsonConfCasella;
	
	@Autowired
	private ObjectMapper obj;

	
	@Bean(name = "casellaMittenteApplicazione") 
	public ConfigurazioneCasellaPostaleDto getCasellaMittenteApplicazione() throws Exception {
		ConfigurazioneCasellaPostaleDto ccpd=null;
		Resource res = new ClassPathResource("/configCasellaPostale/" +jsonConfCasella );
		try(InputStream inputStream = res.getInputStream()){
			ccpd = obj.readValue(inputStream, ConfigurazioneCasellaPostaleDto.class);	
			ccpd.getCasellaPostale().setPassword(
					CryptUtil.decrypt(ccpd.getCasellaPostale().getPassword())
					);
		}
		return ccpd;
	}

	
}
