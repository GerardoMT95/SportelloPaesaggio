//package it.eng.tz.puglia.autpae.testRepository;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
//import it.eng.tz.puglia.autpae.entity.LocalizzazioneDTO;
//import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
//import it.eng.tz.puglia.autpae.repository.LocalizzazioneFullRepository;
//import it.eng.tz.puglia.autpae.search.LocalizzazioneSearch;
//import it.eng.tz.puglia.autpae.utility.DtoFactory;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class LocalizzazioneRepositoryTest {
//	
//	@Autowired
//	private LocalizzazioneFullRepository localizzazioneRepository;
//	
//	@Autowired
//	private FascicoloFullRepository fascicoloRepository;
//	
//	@Autowired
//	private DtoFactory dtoFactory;
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testSelect() throws Exception {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO(/*null, idFascicolo, "A662", "111", "111", "111", "111", TipoLocalizzazione.PTC*/);
//		localizzazioneRepository.insert(localizzazioneDTO);
//		assertEquals(1, localizzazioneRepository.select().size());
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testCount() throws Exception {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO(/*null, idFascicolo, "A662", "111", "111", "111", "111", TipoLocalizzazione.PTC*/);
//		localizzazioneRepository.insert(localizzazioneDTO);
//		LocalizzazioneSearch search = new LocalizzazioneSearch();
//		search.setComuneId("A662");
//		assertEquals(1, localizzazioneRepository.count(search));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testFind() throws Exception {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO(/*null, idFascicolo, "A662", "111", "111", "111", "111", TipoLocalizzazione.PTC*/);
//		Long idLocalizzazione = localizzazioneRepository.insert(localizzazioneDTO);
//		localizzazioneDTO.setId(idLocalizzazione);
//		assertEquals(localizzazioneDTO, localizzazioneRepository.find(idLocalizzazione));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testInsert() throws Exception {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO(/*null, idFascicolo, "A662", "111", "111", "111", "111", TipoLocalizzazione.PTC*/);
//		assertNotNull(localizzazioneRepository.insert(localizzazioneDTO));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testUpdateLocalizzazioneDTO() throws Exception {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO(/*null, idFascicolo, "A662", "111", "111", "111", "111", TipoLocalizzazione.PTC*/);
//		Long idLocalizzazione = localizzazioneRepository.insert(localizzazioneDTO);
//		localizzazioneDTO.setId(idLocalizzazione);
//		localizzazioneDTO.setFoglio("222");
//		assertNotNull(localizzazioneRepository.update(localizzazioneDTO));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testDelete() throws Exception {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO(/*null, idFascicolo, "A662", "111", "111", "111", "111", TipoLocalizzazione.PTC*/);
//		Long idLocalizzazione = localizzazioneRepository.insert(localizzazioneDTO);
//		LocalizzazioneSearch search = new LocalizzazioneSearch();
//		search.setId(idLocalizzazione);
//		assertEquals(1, localizzazioneRepository.delete(search));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testSearch() throws Exception {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO(/*null, idFascicolo, "A662", "111", "111", "111", "111", TipoLocalizzazione.PTC*/);
//		Long idLocalizzazione = localizzazioneRepository.insert(localizzazioneDTO);
//		LocalizzazioneSearch search = new LocalizzazioneSearch();
//		search.setId(idLocalizzazione);
//		assertEquals(1, localizzazioneRepository.search(search).getList().size());
//	}
//
//}
