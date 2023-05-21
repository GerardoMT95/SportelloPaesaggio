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
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoRicevuta;
import it.eng.tz.puglia.autpae.repository.RicevutaFullRepository;
import it.eng.tz.puglia.autpae.repository.base.CorrispondenzaBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.DestinatarioBaseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RicevutaRepositoryTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RicevutaRepositoryTest.class);
	
	@Autowired
	private CorrispondenzaBaseRepository corrispondenzaRepository;
	
	@Autowired
	private DestinatarioBaseRepository destinatarioRepository;
	
	@Autowired
	private RicevutaFullRepository ricevutaRepository;

	@Test
	@Rollback
	@Transactional
	public void testInsertMultipla() {
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
			DestinatarioDTO d2 = new DestinatarioDTO();
			d2.setTipo(TipoDestinatario.TO);
			d2.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d2.setEmail("test@test.it");
			d2.setNome("denominazione 2");
			d2.setIdCorrispondenza(idCorrispondenza);
			Long idD2 = destinatarioRepository.insert(d2);
			DestinatarioDTO d3 = new DestinatarioDTO();
			d3.setTipo(TipoDestinatario.TO);
			d3.setStato(StatoCorrispondenza.ESITO_POSITIVO);
			d3.setEmail("pippo@baudo.tv");
			d3.setNome("denominazione 3");
			d3.setIdCorrispondenza(idCorrispondenza);
			Long idD3 = destinatarioRepository.insert(d3);
			List<RicevutaDTO> listRicevutaDTO = new ArrayList<RicevutaDTO>();
			RicevutaDTO r1 = new RicevutaDTO();
			r1.setIdCorrispondenza(idCorrispondenza);
			r1.setIdDestinatario(idD1);
			r1.setTipoRicevuta(TipoRicevuta.ACCETTAZIONE);
			listRicevutaDTO.add(r1);
			RicevutaDTO r2 = new RicevutaDTO();
			r2.setIdCorrispondenza(idCorrispondenza);
			r2.setIdDestinatario(idD2);
			r2.setTipoRicevuta(TipoRicevuta.ACCETTAZIONE);
			listRicevutaDTO.add(r2);
			RicevutaDTO r3 = new RicevutaDTO();
			r3.setIdCorrispondenza(idCorrispondenza);
			r3.setIdDestinatario(idD3);
			r3.setTipoRicevuta(TipoRicevuta.ACCETTAZIONE);
			listRicevutaDTO.add(r3);
			assertEquals(3, ricevutaRepository.insertMultipla(listRicevutaDTO));
		} catch (Exception e) {
			logger.error("Errore ", e);
		}
	}

}
