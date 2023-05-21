//package it.eng.tz.puglia.autpae.testRepository;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
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
//import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;
//import it.eng.tz.puglia.autpae.entity.composedKeys.FascicoloInterventoPK;
//import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
//import it.eng.tz.puglia.autpae.repository.FascicoloInterventoFullRepository;
//import it.eng.tz.puglia.autpae.repository.base.FascicoloInterventoBaseRepository;
//import it.eng.tz.puglia.autpae.search.FascicoloInterventoSearch;
//import it.eng.tz.puglia.autpae.utility.DtoFactory;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class FascicoloInterventoRepositoryTest {
//	
//	@Autowired
//	private FascicoloInterventoBaseRepository fascicoloInterventoRepository;
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
//	public void testSelect() {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		FascicoloInterventoDTO fascicoloInterventoDTO = new FascicoloInterventoDTO(idFascicolo, "qualDPCM_2005", "DPCM_2005_01");
//		fascicoloInterventoRepository.insert(fascicoloInterventoDTO);
//		assertEquals(1, fascicoloInterventoRepository.select());
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testCount() {
//		//FIXME non funge!!!
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		FascicoloInterventoDTO fascicoloInterventoDTO = new FascicoloInterventoDTO(idFascicolo, "qualDPCM_2005", "DPCM_2005_01");
//		fascicoloInterventoRepository.insert(fascicoloInterventoDTO);
//		FascicoloInterventoSearch search = new FascicoloInterventoSearch(idFascicolo, null);
//		assertEquals(1L, fascicoloInterventoRepository.count(search));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testFind() {
//		//FIXME non funge!!!
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		FascicoloInterventoDTO fascicoloInterventoDTO = new FascicoloInterventoDTO(idFascicolo, "qualDPCM_2005", "DPCM_2005_01");
//		fascicoloInterventoRepository.insert(fascicoloInterventoDTO);
//		FascicoloInterventoPK pk = new FascicoloInterventoPK(idFascicolo, "idTipiQualificazioni");
//		assertEquals(fascicoloInterventoDTO, fascicoloInterventoRepository.find(pk));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testInsert() {
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		FascicoloInterventoDTO fascicoloInterventoDTO = new FascicoloInterventoDTO(idFascicolo, "qualDPCM_2005", "DPCM_2005_01");
//		assertNull(fascicoloInterventoRepository.insert(fascicoloInterventoDTO));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testUpdateFascicoloInterventoDTO() {
//		
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testDelete() {
//		//FIXME non funge!!!
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		FascicoloInterventoDTO fascicoloInterventoDTO = new FascicoloInterventoDTO(idFascicolo, "qualDPCM_2005", "DPCM_2005_01");
//		fascicoloInterventoRepository.insert(fascicoloInterventoDTO);
//		FascicoloInterventoSearch search = new FascicoloInterventoSearch(idFascicolo, null);
//		assertEquals(1, fascicoloInterventoRepository.delete(search));
//	}
//
//	@Test
//	@Rollback
//	@Transactional
//	public void testSearch() {
//		//FIXME non funge!!!
//		FascicoloDTO fascicoloDTO = dtoFactory.creaFascicolo();
//		Long idFascicolo = fascicoloRepository.insert(fascicoloDTO);
//		FascicoloInterventoDTO fascicoloInterventoDTO = new FascicoloInterventoDTO(idFascicolo, "qualDPCM_2005", "DPCM_2005_01");
//		fascicoloInterventoRepository.insert(fascicoloInterventoDTO);
//		FascicoloInterventoSearch search = new FascicoloInterventoSearch(idFascicolo, null);
//		assertEquals(1, fascicoloInterventoRepository.search(search));
//	}
//
//}
