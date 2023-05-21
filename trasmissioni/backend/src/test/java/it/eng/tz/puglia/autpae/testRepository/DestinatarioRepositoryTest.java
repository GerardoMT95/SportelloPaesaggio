package it.eng.tz.puglia.autpae.testRepository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.repository.DestinatarioFullRepository;
import it.eng.tz.puglia.autpae.repository.base.CorrispondenzaBaseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DestinatarioRepositoryTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DestinatarioRepositoryTest.class);
	
	@Autowired
	private CorrispondenzaBaseRepository corrispondenzaRepository;
	
	@Autowired
	private DestinatarioFullRepository destinatarioRepository;

	@Test
	@Rollback
	@Transactional
	public void testSearchByIdCorrispondenzaAndEmails() {
		try {
			CorrispondenzaDTO corrispondenzaDTO = new CorrispondenzaDTO();
			corrispondenzaDTO.setMittenteEmail("test");
			corrispondenzaDTO.setMittenteDenominazione("test");
			corrispondenzaDTO.setMittenteUsername("test");
			corrispondenzaDTO.setMittenteEnte("test");
			corrispondenzaDTO.setOggetto("test");
			corrispondenzaDTO.setStato(true);
			corrispondenzaDTO.setComunicazione(true);
			corrispondenzaDTO.setBozza(false);
			Long idCorrispondenza = corrispondenzaRepository.insert(corrispondenzaDTO);
			DestinatarioDTO d1 = new DestinatarioDTO();
			d1.setTipo(TipoDestinatario.TO);
			d1.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d1.setEmail("email@email.com");
			d1.setNome("denominazione 1");
			d1.setIdCorrispondenza(idCorrispondenza);
			Long idD1 = destinatarioRepository.insert(d1);
			d1.setId(idD1);
			DestinatarioDTO d2 = new DestinatarioDTO();
			d2.setTipo(TipoDestinatario.TO);
			d2.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d2.setEmail("test@test.it");
			d2.setNome("denominazione 2");
			d2.setIdCorrispondenza(idCorrispondenza);
			Long idD2 = destinatarioRepository.insert(d2);
			d2.setId(idD2);
			DestinatarioDTO d3 = new DestinatarioDTO();
			d3.setTipo(TipoDestinatario.TO);
			d3.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d3.setEmail("pippo@baudo.tv");
			d3.setNome("denominazione 3");
			d3.setIdCorrispondenza(idCorrispondenza);
			Long idD3 = destinatarioRepository.insert(d3);
			d3.setId(idD3);
			List<DestinatarioDTO> listDestinatarioDTO = new ArrayList<DestinatarioDTO>();
			listDestinatarioDTO.add(d2);
			listDestinatarioDTO.add(d3);
			List<String> emails = new ArrayList<String>();
			emails.add("pippo@baudo.tv");
			emails.add("test@test.it");
			List<DestinatarioDTO> listDestinatarioDTOFromRepo = destinatarioRepository.searchByIdCorrispondenzaAndEmails(idCorrispondenza, emails);
			assertEquals(listDestinatarioDTO, listDestinatarioDTOFromRepo);
		} catch (Exception e) {
			logger.error("Errore ", e);
		}
	}
	
	@Test
	@Rollback
	@Transactional
	public void testUpdateFieldPec() {
		try {
			CorrispondenzaDTO corrispondenzaDTO = new CorrispondenzaDTO();
			corrispondenzaDTO.setMittenteEmail("test");
			corrispondenzaDTO.setMittenteDenominazione("test");
			corrispondenzaDTO.setMittenteUsername("test");
			corrispondenzaDTO.setMittenteEnte("test");
			corrispondenzaDTO.setOggetto("test");
			corrispondenzaDTO.setStato(true);
			corrispondenzaDTO.setComunicazione(true);
			corrispondenzaDTO.setBozza(false);
			Long idCorrispondenza = corrispondenzaRepository.insert(corrispondenzaDTO);
			DestinatarioDTO d1 = new DestinatarioDTO();
			d1.setTipo(TipoDestinatario.TO);
			d1.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d1.setEmail("email@email.com");
			d1.setNome("denominazione 1");
			d1.setIdCorrispondenza(idCorrispondenza);
			Long idD1 = destinatarioRepository.insert(d1);
			d1.setId(idD1);
			DestinatarioDTO d2 = new DestinatarioDTO();
			d2.setTipo(TipoDestinatario.TO);
			d2.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d2.setEmail("test@test.it");
			d2.setNome("denominazione 2");
			d2.setIdCorrispondenza(idCorrispondenza);
			Long idD2 = destinatarioRepository.insert(d2);
			d2.setId(idD2);
			DestinatarioDTO d3 = new DestinatarioDTO();
			d3.setTipo(TipoDestinatario.TO);
			d3.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d3.setEmail("pippo@baudo.tv");
			d3.setNome("denominazione 3");
			d3.setIdCorrispondenza(idCorrispondenza);
			Long idD3 = destinatarioRepository.insert(d3);
			d3.setId(idD3);
			List<Long> listId = new ArrayList<Long>();
			listId.add(idD1);
			listId.add(idD3);
			assertEquals(2, destinatarioRepository.updateFieldPec(listId, true));
		} catch (Exception e) {
			logger.error("Errore ", e);
		}
	}

}
