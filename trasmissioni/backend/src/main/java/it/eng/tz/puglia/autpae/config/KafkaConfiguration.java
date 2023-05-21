package it.eng.tz.puglia.autpae.config;

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
	

	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
	@Value("${casella.postale.config.webmail}")
	private String jsonConfAutpae;
	@Value("${casella.postale.config.webmail.pareri}")
	private String jsonConfPareri;
	@Value("${casella.postale.config.webmail.putt}")
	private String jsonConfPutt;
	
	@Autowired
	private ObjectMapper obj;
	
	
	@Bean(name = "casellaMittenteApplicazione") 
	public ConfigurazioneCasellaPostaleDto getCasellaMittenteApplicazioneDefault() throws Exception {
		ConfigurazioneCasellaPostaleDto ccpd=null;
		String nomeJsonDiConfigurazione="";
		switch (codiceApplicazione) {
		case "autpae":
			nomeJsonDiConfigurazione=jsonConfAutpae;
			break;
		case "pareri":
			nomeJsonDiConfigurazione=jsonConfPareri;
			break;
		case "putt":
			nomeJsonDiConfigurazione=jsonConfPutt;
			break;	
		default:
			throw new Exception("Nome Applicazione non valido " +codiceApplicazione);
		}
		Resource res = new ClassPathResource("/configCasellaPostale/" +nomeJsonDiConfigurazione );
		try(InputStream inputStream = res.getInputStream()){
			ccpd = obj.readValue(inputStream, ConfigurazioneCasellaPostaleDto.class);	
			ccpd.getCasellaPostale().setPassword(
					CryptUtil.decrypt(ccpd.getCasellaPostale().getPassword())
					);
		}
		return ccpd;
	}
	
	
}
