package it.eng.tz.puglia.autpae.testRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
import it.eng.tz.puglia.autpae.repository.base.RichiedenteBaseRepository;
import it.eng.tz.puglia.autpae.search.RichiedenteSearch;
import it.eng.tz.puglia.autpae.utility.DtoFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RichiedenteRepositoryTest {
	
	@Autowired
	private RichiedenteBaseRepository richiedenteRepository;
	
	@Autowired
	private FascicoloFullRepository fascicoloRepository;
	
	@Autowired
	private DtoFactory dtoFactory;

	@Test
	@Rollback
	@Transactional
	public void testSelect() throws Exception {
		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setNome("nome test");
		richiedenteDTO.setCognome("cognome test");
		richiedenteDTO.setCodiceFiscale("SPSPND43H53F083J");
		richiedenteDTO.setIdFascicolo(idFascicolo);
		richiedenteRepository.insert(richiedenteDTO);
		assertEquals(1, richiedenteRepository.select().size());
	}

	@Test
	@Rollback
	@Transactional
	public void testCount() throws Exception {
		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setNome("nome test");
		richiedenteDTO.setCognome("cognome test");
		richiedenteDTO.setCodiceFiscale("SPSPND43H53F083J");
		richiedenteDTO.setIdFascicolo(idFascicolo);
		richiedenteRepository.insert(richiedenteDTO);
		RichiedenteSearch search = new RichiedenteSearch();
		search.setCodiceFiscale("SPSPND43H53F083J");
		assertEquals(1, richiedenteRepository.count(search));
	}

	@Test
	@Rollback
	@Transactional
	public void testFind() throws Exception {
		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setNome("nome test");
		richiedenteDTO.setCognome("cognome test");
		richiedenteDTO.setCodiceFiscale("SPSPND43H53F083J");
		richiedenteDTO.setIdFascicolo(idFascicolo);
		Long idRichiedente = richiedenteRepository.insert(richiedenteDTO);
		richiedenteDTO.setId(idRichiedente);
		assertEquals(richiedenteDTO, richiedenteRepository.find(idRichiedente));
	}

	@Test
	@Rollback
	@Transactional
	public void testInsert() throws Exception {
		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setNome("nome test");
		richiedenteDTO.setCognome("cognome test");
		richiedenteDTO.setCodiceFiscale("SPSPND43H53F083J");
		richiedenteDTO.setIdFascicolo(idFascicolo);
		assertNotNull(richiedenteRepository.insert(richiedenteDTO));
	}

	@Test
	@Rollback
	@Transactional
	public void testUpdateRichiedenteDTO() throws Exception {
		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setNome("nome test");
		richiedenteDTO.setCognome("cognome test");
		richiedenteDTO.setCodiceFiscale("SPSPND43H53F083J");
		richiedenteDTO.setIdFascicolo(idFascicolo);
		Long idRichiedente = richiedenteRepository.insert(richiedenteDTO);
		richiedenteDTO.setId(idRichiedente);
		richiedenteDTO.setCodiceFiscale("QKTRWZ40E64H745M");
		assertNotNull(richiedenteRepository.update(richiedenteDTO));
	}

	@Test
	@Rollback
	@Transactional
	public void testDelete() throws Exception {
		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setNome("nome test");
		richiedenteDTO.setCognome("cognome test");
		richiedenteDTO.setCodiceFiscale("SPSPND43H53F083J");
		richiedenteDTO.setIdFascicolo(idFascicolo);
		richiedenteRepository.insert(richiedenteDTO);
		RichiedenteSearch search = new RichiedenteSearch();
		search.setCodiceFiscale("SPSPND43H53F083J");
		assertEquals(1, richiedenteRepository.delete(search));
	}

	@Test
	@Rollback
	@Transactional
	public void testSearch() throws Exception {
		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setNome("nome test");
		richiedenteDTO.setCognome("cognome test");
		richiedenteDTO.setCodiceFiscale("SPSPND43H53F083J");
		richiedenteDTO.setIdFascicolo(idFascicolo);
		Long idRichiedente = richiedenteRepository.insert(richiedenteDTO);
		RichiedenteSearch search = new RichiedenteSearch();
		search.setId(idRichiedente);
		assertEquals(1, richiedenteRepository.search(search).getList().size());
	}

}
