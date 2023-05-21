package it.eng.tz.puglia.autpae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableFeignClients(basePackages = "it.eng.tz")
@ComponentScan("it.eng.tz")
@EnableScheduling
public class AutPaeBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutPaeBeApplication.class, args);
	}
}
