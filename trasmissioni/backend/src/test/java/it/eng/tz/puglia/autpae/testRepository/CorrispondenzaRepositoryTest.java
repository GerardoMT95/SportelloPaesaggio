package it.eng.tz.puglia.autpae.testRepository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.repository.CorrispondenzaFullRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CorrispondenzaRepositoryTest {
	
	private static final Logger logger = LoggerFactory.getLogger(CorrispondenzaRepositoryTest.class);
	
	@Autowired
	private CorrispondenzaFullRepository repo;

	@Test
	@Rollback
	@Transactional
	public void testUpdateMessageId() {
		try {
			CorrispondenzaDTO dto = new CorrispondenzaDTO();
			dto.setMittenteEmail("test");
			dto.setMittenteDenominazione("test");
			dto.setMittenteUsername("test");
			dto.setMittenteEnte("test");
			dto.setOggetto("test");
			dto.setStato(true);
			dto.setComunicazione(true);
			dto.setBozza(false);
			Long idCorrispondenza = repo.insert(dto);
			if (idCorrispondenza != null) {
				logger.info("Corrispondenza inserita con successo");
				String messageId = "<123456789@test.ciccio.it>";
				int resultUpdate = repo.updateMessageId(idCorrispondenza, messageId);
				assertEquals(1, resultUpdate);
			}
			else {
				logger.error("Errore durante l'inserimento della corrispondenza");
			}
		} catch (Exception e) {
			logger.error("Errore durante il test ", e);
		}
	}

}
