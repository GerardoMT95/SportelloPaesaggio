package it.eng.tz.puglia.aet.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class junit to generate "orm" classes
 * 
 * @author Antonio La Gatta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-test.xml" })
@TestPropertySource(properties = {  "jdbc.driver=org.postgis.jts.JtsGisWrapper",
									//"jdbc.url=jdbc:postgresql_JTS://localhost:5433/sitr_puglia?searchpath=presentazione_istanza,public,topology", 
									"jdbc.url=jdbc:postgresql_JTS://192.168.11.60:5432/sitr_puglia?searchpath=presentazione_istanza,public,topology",
									"jdbc.username=presentazione_istanza",
									"jdbc.password=presentazione_istanza", 
									"jdbc.schema=presentazione_istanza", 
									"jdbc.tables=pratica_cds_partecipanti", 
									"onlyRepo=true",
									"pathFile=/home/simone/Documenti/Progetti/PaesaggioCDS/BE/PaesaggioPresentazioneIstanza/paesaggio-presentazione-istanza-be/src/main/java",
//									"pathFile=C://devel_data//git_repo//PaesaggioPresentazioneIstanza//paesaggio-presentazione-istanza-be//src//main//java//",
									"packageString=it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm"
									})
public class GenerateEntity extends GenerateCommonEntity {

	/** 
	 * Method junit to generate "orm" classes
	 * 
	 * @author Antonio La Gatta
	 * @throws Exception
	 */
	@Test
	public void generate() throws Exception {
		super.commonGenerate();
	}

}
