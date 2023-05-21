package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Starter
 * @author Antonio La Gatta
 * @date 21 apr 2020
 */
@SpringBootApplication(exclude = {FlywayAutoConfiguration.class})
@EnableResourceServer
@EnableFeignClients(basePackages = "it.eng.tz")
@ComponentScan("it.eng.tz")
@EnableScheduling
public class PresentazioneIstanzaApplication {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PresentazioneIstanzaApplication.class);
	/**
	 * Main di tutte le applicazioni
	 * @author Antonio La Gatta
	 * @date 21 apr 2020
	 * @param args
	 */
	public static void main(String[] args) {
		LOGGER.info("Start main");
		try {
			SpringApplication.run(PresentazioneIstanzaApplication.class, args);
		}catch(Exception e) {
			LOGGER.error("Error in main", e);
		}
		LOGGER.info("End main");
	}
}